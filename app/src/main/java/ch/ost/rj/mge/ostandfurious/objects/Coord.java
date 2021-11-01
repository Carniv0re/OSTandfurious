package ch.ost.rj.mge.ostandfurious.objects;

public class Coord implements Comparable<Coord> {
    private int x, y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Coord coord) {
        return this.x == coord.x && this.y == coord.y ? 1 : 0;
    }

    public int getX() {
        return x;
    }

    public void moveX(int x) {
        this.x += x;
    }

    public int getY() {
        return y;
    }

    public void moveY(int y) {
        this.y += y;
    }
}
