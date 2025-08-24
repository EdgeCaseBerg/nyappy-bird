package spare.peetseater.nb;

public class Player {
    private float y;
    private float x;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update(float delta, float gravity) {
        this.y -= gravity * delta;
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
}
