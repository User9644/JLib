package de.f.sgl;

/**
 * <p>A simple rectangle class</p>
 * Similar to {@link java.awt.Rectangle}
 *
 * @author Finlay
 * @since 1.0
 * @version 1.0
 */
public class Rectangle {
    protected int x, y, width, height;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle(Point p1, Point p2) {
        this.x = p1.getX();
        this.y = p1.getY();

        this.width = p2.getX() - p1.getX();
        this.height = p2.getY() - p1.getY();
    }

    public Rectangle() {}

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void setFirstPosition(Point p) {
        this.width += this.x - p.getX();
        this.height += this.y - p.getY();

        this.x = p.getX();
        this.y = p.getY();
    }
    public Point getFirstPosition() {
        return new Point(this.x, this.y);
    }
    public void setSecondPosition(Point p) {
        this.width = p.getX() - this.x;
        this.height = p.getY() - this.y;
    }
    public Point getSecondPosition() {
        return new Point(this.x + this.width, this.y + this.height);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rectangle rectangle = (Rectangle) o;

        if (x != rectangle.x) return false;
        if (y != rectangle.y) return false;
        if (width != rectangle.width) return false;
        return height == rectangle.height;
    }
    @Override
    public Rectangle clone() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
    @Override
    public String toString() {
        return this.getClass().getName() + "[" + getFirstPosition() + ", " + getSecondPosition() + "]";
    }
}