package de.f.utils;

/**
 * A Class containing two values
 *
 * @param <A> First Class
 * @param <B> Second Class
 *
 * @author Finlay
 * @since 1.0
 * @version 1.0
 */
public class DoubleValue<A, B> {
    private A a = null;
    private B b = null;

    public DoubleValue() {

    }
    public DoubleValue(A a) {
        this.a = a;
    }
    public DoubleValue(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getA() {
        return a;
    }
    public void setA(A a) {
        this.a = a;
    }
    public B getB() {
        return b;
    }
    public void setB(B b) {
        this.b = b;
    }

    /**
     * Checks if both values are non-{@code null}
     * @return {@code boolean}
     */
    public boolean hasValues() {
        return a != null && b != null;
    }

    /**
     * String Format: {@code { a | b }}
     * @return {@link String}
     */
    @Override
    public String toString() {
        return "{ " + a.toString() + " | " + b.toString() + " }";
    }

    /**
     * @return {@link DoubleValue}
     */
    @Override
    public DoubleValue<A, B> clone() {
        return new DoubleValue<>(this.a, this.b);
    }
}