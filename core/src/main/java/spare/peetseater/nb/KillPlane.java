package spare.peetseater.nb;

public class KillPlane {
    private float leftCornerX;
    private float leftCornerY;
    private float width;
    private float height;

    public KillPlane(float leftCornerX, float leftCornerY, float width, float height) {
        this.leftCornerX = leftCornerX;
        this.leftCornerY = leftCornerY;
        this.width = width;
        this.height = height;
    }

    public boolean intersects(Player player) {
        float px = player.getX();
        float py = player.getY();
        boolean withinXRange = leftCornerX <= px && px <= leftCornerX + width;
        boolean withinYRange = leftCornerY <= py && py <= leftCornerY + height;
        return withinXRange && withinYRange;
    }

    public void setLeftCornerX(float leftCornerX) {
        this.leftCornerX = leftCornerX;
    }

    public void setLeftCornerY(float leftCornerY) {
        this.leftCornerY = leftCornerY;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public float getLeftCornerX() {
        return leftCornerX;
    }

    public float getLeftCornerY() {
        return leftCornerY;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setWidth(float width) {
        this.width = width;
    }
}
