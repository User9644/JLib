package de.f.sgl;

/**
 * <p>A simple vector class</p>
 * Similar to {@link java.awt.Point} but using {@code double}
 * @see Point
 *
 * @author Finlay
 * @since 1.0
 * @version 1.0
 */
public class Vector {
    protected double x, y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Vector() {}

    public double getX() {
        return this.x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return this.y;
    }
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector vector = (Vector) o;

        if (this.x != vector.x) return false;
        return this.y == vector.y;
    }

    @Override
    public Vector clone() {
        return new Vector(this.x, this.y);
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "[" + this.x + ", " + this.y + "]";
    }
}