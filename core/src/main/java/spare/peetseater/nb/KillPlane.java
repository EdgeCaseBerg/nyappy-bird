package spare.peetseater.nb;

import com.badlogic.gdx.math.Vector2;

public class KillPlane {
    private float leftCornerX;
    private float leftCornerY;
    private float width;
    private float height;
    private Vector2 velocity;

    public KillPlane(float leftCornerX, float leftCornerY, float width, float height) {
        this.leftCornerX = leftCornerX;
        this.leftCornerY = leftCornerY;
        this.width = width;
        this.height = height;
        velocity = Vector2.Zero;
    }

    public boolean intersects(Player player) {
        float pLeftX = player.getX();
        float pLeftY = player.getY();
        float pRightX = pLeftX + player.getWidth();
        float pRightY = pLeftY + player.getHeight();

        boolean overlapsXProjection = pRightX >= leftCornerX && (leftCornerX + width) >= pLeftX;
        boolean overlapsYProjection = pRightY >= leftCornerY && (leftCornerY + height) >= pLeftY;
        return overlapsXProjection && overlapsYProjection;
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

    public void update(float delta) {
        this.leftCornerX -= this.velocity.x * delta;
        this.leftCornerY -= this.velocity.y * delta;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
}
