package spare.peetseater.nb;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class ScrollingStars {
    private final float maxX;
    private final float maxY;
    List<Vector2> starPositions;
    float accum;
    float xSpeed;

    public ScrollingStars(float howManyStars, float maxX, float maxY, float xSpeed) {
        this.maxX = maxX;
        this.maxY = maxY;
        starPositions = new ArrayList<>();
        this.xSpeed = xSpeed * -1;
        for (int i = 0; i < howManyStars; i++) {
            float x = MathUtils.random(0, maxX);
            float y = MathUtils.random(0, maxY);
            starPositions.add(new Vector2(x, y));
        }
    }

    void update(float delta) {
        accum += delta;
        for (Vector2 pos : starPositions) {
            pos.add(xSpeed * delta, 0);
            if (pos.x < 0) {
                pos.set(maxX + MathUtils.random(), MathUtils.random(0, maxY));
            }
        }
    }

    void draw(NyappyBirdGame game) {
        for (int i = 0; i < starPositions.size(); i++) {
            Vector2 pos = starPositions.get(i);
            game.batch.draw(
                game.starSprite.getKeyFrame(i + accum, true),
                pos.x,
                pos.y,
                0.25f,
                0.25f
            );
        }
    }
}
