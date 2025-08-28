package spare.peetseater.nb;

public class Player {
    private float y;
    private final float width;
    private final float height;
    private float x;
    private float lift;

    public Player(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.lift = 0;
    }

    public void update(float delta, float gravity) {
        this.y -= gravity * delta;
        this.y += lift * delta;
        float decay = 0.25f * delta;
        this.lift -= decay;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setLift(float lift) {
        this.lift = lift;
    }

    public float getLift() {
        return lift;
    }
}
