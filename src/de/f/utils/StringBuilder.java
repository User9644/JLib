package de.f.utils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * An alternative to {@link java.lang.StringBuilder} containing methods like {@code add(int, *)} and {@link #clear()}
 *
 * @author Finlay
 * @since 1.0
 * @version 1.0
 */
public class StringBuilder implements CharSequence {
    private char[] values;
    private int size;
    
    private static final int DEFAULT_SIZE = 1 << 4;

    /**
     * Generates a new {@code StringBuilder} with the starting capacity of {@value DEFAULT_SIZE}
     */
    public StringBuilder() {
        this.values = new char[DEFAULT_SIZE];
        this.size = 0;
    }

    /**
     * Generates a new {@code StringBuilder} with the given starting capacity
     * @param startCapacity Starting capacity of the {@code StringBuilder}
     */
    public StringBuilder(int startCapacity) {
        this.values = new char[startCapacity];
        this.size = 0;
    }

    /**
     * Generates a new {@code StringBuilder} containing the given {@link CharSequence}
     * @param cs Starting content
     */
    public StringBuilder(CharSequence cs) {
        this.values = new char[Math.max(cs.length(), DEFAULT_SIZE)];
        this.size = cs.length();
        for(int i = 0; i < cs.length(); i ++)
            this.values[i] = cs.charAt(i);
    }

    /**
     * Clears the {@code StringBuilder} and sets the size to 0
     */
    public void clear() {
        this.values = new char[DEFAULT_SIZE];
        this.size = 0;
    }

    /**
     * @return the size of the {@code StringBuilder}
     */
    @Override
    public int length() {
        return this.size;
    }

    /**
     * @param index the index of the {@code char} value to be returned
     *
     * @return The character at the given index
     */
    @Override
    public char charAt(int index) {
        return this.values[index];
    }

    private void checkSubSeqIndices(int start, int end) throws StringIndexOutOfBoundsException, IllegalArgumentException {
        if(start >= end)
            throw new IllegalArgumentException("End must be greater then Start\n\tStart: " + start + ", End: " + end);
        if(end >= this.size)
            throw new StringIndexOutOfBoundsException("End is out of bounds\n\tEnd: " + end + ", Length: " + this.size);
        if(start < 0)
            throw new StringIndexOutOfBoundsException("Start must not be negative\n\tStart: " + start);
    }

    /**
     * @param start   the start index, inclusive
     * @param end     the end index, exclusive
     *
     * @return a {@link CharSequence} of the {@code StringBuilder}
     */
    @Override
    public CharSequence subSequence(int start, int end) {
        checkSubSeqIndices(start, end);

        char[] seqChars = new char[end - start];
        System.arraycopy(this.values, start, seqChars, 0, seqChars.length);
        return new String(seqChars);
    }

    /**
     * @return the content of the {@code StringBuilder} as a {@link String}
     */
    @Override
    public String toString() {
        return new String(this.values);
    }

    /**
     * @return {@code true} if the {@code StringBuilder} is empty, otherwise {@code false}
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    private void resize() {
        if(this.size != this.values.length)
            return;

        char[] newValues = new char[this.values.length << 1];
        System.arraycopy(this.values, 0, newValues, 0, this.values.length);
        this.values = newValues;
    }

    private void resize(int length) {
        int minSize = this.values.length + length;
        if(this.size >= minSize)
            return;

        int newSize = this.values.length << 1;
        while(newSize < minSize)
            newSize <<= 1;

        char[] newValues = new char[newSize];
        System.arraycopy(this.values, 0, newValues, 0, this.values.length);
        this.values = newValues;
    }



    // ---- remove(), indexOf(), add(), append(), @Override ----

    // ---- remove() ----

    /**
     * Removes a {@code Character} from the {@code StringBuilder}
     * @param index Index of the {@code Character}
     * @return The removed {@code Character}
     */
    public char remove(int index) {
        if(index >= this.size)
            throw new StringIndexOutOfBoundsException("Index is out of bounds\n\tIndex: " + index + ", Length: " + this.size);
        if(index < 0)
            throw new StringIndexOutOfBoundsException("Index must not be negative\n\tIndex: " + index);

        char c = this.values[index];

        char[] newValues = new char[this.size - 1];
        System.arraycopy(this.values, 0, newValues, 0, index);
        System.arraycopy(this.values, index, newValues, index - 1, this.size - 1);
        this.values = newValues;

        return c;
    }

    /**
     * Removes a {@link String} from the {@code StringBuilder}
     * @param start Start index of the {@link String}
     * @param end End index of the {@link String}
     * @return The removed {@link String}
     */
    public String remove(int start, int end) {
        checkSubSeqIndices(start, end);

        int length = end - start;

        StringBuilder sb = new StringBuilder(length);

        for(int i = start; i < end; i ++)
            sb.append(this.values[i]);

        char[] newValues = new char[this.size - length];
        System.arraycopy(this.values, 0, newValues, 0, start);
        System.arraycopy(this.values, end, newValues, start, this.size - end);
        this.values = newValues;

        return sb.toString();
    }

    // ---- indexOf() ----

    /**
     * Gets the first index of the {@link String} in the {@code StringBuilder}
     * @param s The substring to search for
     * @param start The start index
     * @param end The end index
     * @return The first occurrence of the substring in this {@code StringBuilder}
     * @see #indicesOf(String, int, int)
     */
    public int indexOf(String s, int start, int end) {
        checkSubSeqIndices(start, end);

        int length = end - start;

        if(s.isEmpty())
            return 0;
        if(this.size == 0)
            return -1;

        char firstChar = s.charAt(0);
        for(int index = start; index < length; index ++) {
            if(this.values[index] == firstChar) {
                for(int i = 0; i < s.length(); i ++)
                    if(this.values[index + i] != s.charAt(i))
                        break;
                return index;
            }
        }

        return -1;
    }
    
    /**
     * Gets the first index of the {@link String} in the {@code StringBuilder}
     * @param cs The substring to search for
     * @param start The start index
     * @param end The end index
     * @return The first occurrence of the substring in this {@code StringBuilder}
     * @see #indicesOf(CharSequence, int, int)
     */
    public int indexOf(CharSequence cs, int start, int end) {
        return indexOf(cs.toString(), start, end);
    }
    
    /**
     * Gets the first index of the {@link String} in the {@code StringBuilder}
     * @param s The substring to search for
     * @return The first occurrence of the substring in this {@code StringBuilder}
     * @see #indicesOf(String)
     */
    public int indexOf(String s) {
        return indexOf(s, 0, this.size);
    }
    
    /**
     * Gets the first index of the {@link String} in the {@code StringBuilder}
     * @param cs The substring to search for
     * @return The first occurrence of the substring in this {@code StringBuilder}
     * @see #indicesOf(CharSequence)
     */
    public int indexOf(CharSequence cs) {
        return indexOf(cs, 0, this.size);
    }
    
    // ---- indicesOf() ----
    /**
     * Gets the all indices of the {@link String} in the {@code StringBuilder}
     * @param s The substring to search for
     * @param start The start index
     * @param end The end index
     * @return All occurrences of the substring in this {@code StringBuilder}
     * @see #indexOf(String, int, int)
     */
    public int[] indicesOf(String s, int start, int end) {
        checkSubSeqIndices(start, end);

        ArrayList<Integer> indices = new ArrayList<>();

        int length = end - start;

        if(s.isEmpty()) {
            int[] array = new int[length];
            for (int i = start; i < end; i++)
                array[i] = i;
            return array;
        }
        if(this.size == 0)
            return new int[]{-1};

        char firstChar = s.charAt(0);
        for(int index = start; index < length; index ++) {
            if(this.values[index] == firstChar) {
                for(int i = 0; i < s.length(); i ++)
                    if(this.values[index + i] != s.charAt(i))
                        break;
                indices.add(index);
            }
        }

        int[] array = new int[indices.size()];
        for(int i = 0; i < array.length; i ++)
            array[i] = indices.get(i);
        return array;
    }
    
    /**
     * Gets the all indices of the {@link String} in the {@code StringBuilder}
     * @param cs The substring to search for
     * @param start The start index
     * @param end The end index
     * @return All occurrences of the substring in this {@code StringBuilder}
     * @see #indexOf(CharSequence, int, int) 
     */
    public int[] indicesOf(CharSequence cs, int start, int end) {
        return indicesOf(cs.toString(), start, end);
    }
    
    /**
     * Gets the all indices of the {@link String} in the {@code StringBuilder}
     * @param s The substring to search for
     * @return All occurrences of the substring in this {@code StringBuilder}
     * @see #indexOf(String) 
     */
    public int[] indicesOf(String s) {
        return indicesOf(s, 0, this.size);
    }

    /**
     * Gets the all indices of the {@link String} in the {@code StringBuilder}
     * @param cs The substring to search for
     * @return All occurrences of the substring in this {@code StringBuilder}
     * @see #indexOf(CharSequence)
     */
    public int[] indicesOf(CharSequence cs) {
        return indicesOf(cs, 0, this.size);
    }

    // ---- add() ----

    private void checkAddIndex(int index) {
        if(index > this.size)
            throw new StringIndexOutOfBoundsException("Index is out of bounds\n\tIndex: " + index + ", Length: " + this.size);
        if(index < 0)
            throw new StringIndexOutOfBoundsException("Index must not be negative\n\tIndex: " + index);
    }

    /**
     * Adds the {@link String} of the given value to the given index
     * @param index Position of the given value
     * @param o Value
     * @return {@code this}
     */
    public StringBuilder add(int index, Object o) {
        return add(index, o.toString());
    }

    /**
     * Adds the {@link String} of the given value to the given index
     * @param index Position of the given value
     * @param o Value
     * @return {@code this}
     */
    public StringBuilder add(int index, CharSequence o) {
        return add(index, o.toString());
    }

    /**
     * Adds the given {@link String} to the given index
     * @param index Position of the given value
     * @param o Value
     * @return {@code this}
     */
    public StringBuilder add(int index, String o) {
        checkAddIndex(index);

        resize(o.length());

        for(int i = 0; i < o.length(); i ++)
            this.values[index + i] = o.charAt(i);

        return this;
    }

    /**
     * Adds the given {@code char} to the given index
     * @param index Position of the given {@code char}
     * @param c {@code char}
     * @return {@code this}
     */
    public StringBuilder add(int index, char c) {
        checkAddIndex(index);
        if(this.size == this.values.length)
            resize();

        System.arraycopy(this.values, index, this.values, index + 1, size - index);

        this.values[index] = c;
        this.size ++;

        return this;
    }

    /**
     * Adds the {@link String} of the given value to the given index
     * @param index Position of the given value
     * @param o Value
     * @return {@code this}
     */
    public StringBuilder add(int index, boolean o) {
        return add(index, o ? "true" : "false");
    }

    /**
     * Adds the {@link String} of the given value to the given index
     * @param index Position of the given value
     * @param o Value
     * @return {@code this}
     */
    public StringBuilder add(int index, byte o) {
        return add(index, Byte.toString(o));
    }

    /**
     * Adds the {@link String} of the given value to the given index
     * @param index Position of the given value
     * @param o Value
     * @return {@code this}
     */
    public StringBuilder add(int index, short o) {
        return add(index, Short.toString(o));
    }

    /**
     * Adds the {@link String} of the given value to the given index
     * @param index Position of the given value
     * @param o Value
     * @return {@code this}
     */
    public StringBuilder add(int index, int o) {
        return add(index, Integer.toString(o));
    }

    /**
     * Adds the {@link String} of the given value to the given index
     * @param index Position of the given value
     * @param o Value
     * @return {@code this}
     */
    public StringBuilder add(int index, long o) {
        return add(index, Long.toString(o));
    }

    /**
     * Adds the {@link String} of the given value to the given index
     * @param index Position of the given value
     * @param o Value
     * @return {@code this}
     */
    public StringBuilder add(int index, float o) {
        return add(index, Float.toString(o));
    }

    /**
     * Adds the {@link String} of the given value to the given index
     * @param index Position of the given value
     * @param o Value
     * @return {@code this}
     */
    public StringBuilder add(int index, double o) {
        return add(index, Double.toString(o));
    }

    // ---- append() ----

    /**
     * Appends the {@link String} of the given value to the end of the {@code StringBuilder}
     * @param o Value
     * @return {@code this}
     */
    public StringBuilder append(Object o) {
        return append(o.toString());
    }

    /**
     * Appends the {@link String} of the given value to the end of the {@code StringBuilder}
     * @param o Value
     * @return {@code this}
     */
    public StringBuilder append(CharSequence o) {
        return append(o.toString());
    }

    /**
     * Appends the given {@link String} to the end of the {@code StringBuilder}
     * @param o {@code String}
     * @return {@code this}
     */
    public StringBuilder append(String o) {
        for(char c: o.toCharArray())
            append(c);
        return this;
    }

    /**
     * Appends the given {@code char} to the end of the {@code StringBuilder}
     * @param c {@code char}
     * @return {@code this}
     */
    public StringBuilder append(char c) {
        if(this.size == this.values.length)
            resize();

        this.values[this.size ++] = c;

        return this;
    }

    /**
     * Appends the {@link String} of the given value to the end of the {@code StringBuilder}
     * @param o Value
     * @return {@code this}
     */
    public StringBuilder append(boolean o) {
        return append(o ? "true" : "false");
    }

    /**
     * Appends the {@link String} of the given value to the end of the {@code StringBuilder}
     * @param o Value
     * @return {@code this}
     */
    public StringBuilder append(byte o) {
        return append(Byte.toString(o));
    }

    /**
     * Appends the {@link String} of the given value to the end of the {@code StringBuilder}
     * @param o Value
     * @return {@code this}
     */
    public StringBuilder append(short o) {
        return append(Short.toString(o));
    }

    /**
     * Appends the {@link String} of the given value to the end of the {@code StringBuilder}
     * @param o Value
     * @return {@code this}
     */
    public StringBuilder append(int o) {
        return append(Integer.toString(o));
    }

    /**
     * Appends the {@link String} of the given value to the end of the {@code StringBuilder}
     * @param o Value
     * @return {@code this}
     */
    public StringBuilder append(long o) {
        return append(Long.toString(o));
    }

    /**
     * Appends the {@link String} of the given value to the end of the {@code StringBuilder}
     * @param o Value
     * @return {@code this}
     */
    public StringBuilder append(float o) {
        return append(Float.toString(o));
    }

    /**
     * Appends the {@link String} of the given value to the end of the {@code StringBuilder}
     * @param o Value
     * @return {@code this}
     */
    public StringBuilder append(double o) {
        return append(Double.toString(o));
    }

    // ---- Override ----

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringBuilder that = (StringBuilder) o;

        if (size != that.size) return false;
        return Arrays.equals(values, that.values);
    }

    @Override
    public StringBuilder clone() {
        return new StringBuilder(toString());
    }
}