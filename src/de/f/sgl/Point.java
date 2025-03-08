package de.f.sgl;

/**
 * <p>A simple point class</p>
 * Similar to {@link java.awt.Point}
 * @see Vector
 *
 * @author Finlay
 * @since 1.0
 * @version 1.0
 */
public class Point {
    protected int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Point() {}

    public int getX() {
        return this.x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return this.y;
    }
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (this.x != point.x) return false;
        return this.y == point.y;
    }

    @Override
    public Point clone() {
        return new Point(this.x, this.y);
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "[" + this.x + ", " + this.y + "]";
    }
}