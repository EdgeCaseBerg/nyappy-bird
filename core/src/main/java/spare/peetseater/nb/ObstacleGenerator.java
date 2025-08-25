package spare.peetseater.nb;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;
import java.util.List;

public class ObstacleGenerator {
    float lastOpeningX;
    float lastOpeningY;
    float minGapSize;
    private final float maxHeight;

    public ObstacleGenerator(
        float initialOpeningX,
        float initialOpeningY,
        float minGapSize,
        float maxHeight
    ) {
        this.lastOpeningX = initialOpeningX;
        this.lastOpeningY = initialOpeningY;
        this.minGapSize = minGapSize;
        this.maxHeight = maxHeight;
    }

    public List<KillPlane> next(float gravity, float maxUpSpeed, float xVelocity) {
        float x1 = lastOpeningX;
        float y1 = lastOpeningY;

        boolean generateAbove = MathUtils.randomBoolean();
        float maxDeltaY = generateAbove ? maxUpSpeed : gravity;
        float dx = 1 + xVelocity; // + 1 for at least one unit of space between

        float dy = MathUtils.lerp(0, maxDeltaY, MathUtils.random());
        float x2 = x1 + dx;
        float y2 = (dy / dx) * x2 + y1;

        float gapSize= MathUtils.lerp(minGapSize, minGapSize * 1.5f, MathUtils.random());
        float combinedHeight = (maxHeight - gapSize);
        float bottomHeight = MathUtils.lerp(0, combinedHeight, MathUtils.random());
        float topHeight = combinedHeight - bottomHeight;

        KillPlane bottom = new KillPlane(
            x2, 0, 0.5f, bottomHeight
        );
        Vector2 obstacleVelocity = new Vector2(xVelocity, 0);
        bottom.setVelocity(obstacleVelocity);
        KillPlane top = new KillPlane(
            x2, bottomHeight + gapSize, 0.5f, topHeight
        );
        top.setVelocity(obstacleVelocity);

        List<KillPlane> newPlanes = new LinkedList<>();
        newPlanes.add(bottom);
        newPlanes.add(top);
        this.lastOpeningY = y2;
        this.lastOpeningX = x1; // this is on purpose.
        return newPlanes;
    }
}
