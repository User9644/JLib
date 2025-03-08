package de.f.sgl;

import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;

public class Font {
    public static final int PLAIN = 0;
    public static final int ITALIC = 1;
    public static final int BOLD = 2;

    private String name;
    private int style;
    private int size;

    public Font(@NotNull String name, @MagicConstant(flags = {PLAIN, ITALIC, BOLD, ITALIC | BOLD}) int style, int size) {
        this.name = name;
        this.style = style;
        this.size = size;
    }

    // TODO: ☺☺☻☻‼‼‼‼‼‼‼‼
}