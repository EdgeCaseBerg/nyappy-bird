package spare.peetseater.nb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;
import java.util.List;

public class ObstacleGenerator {
    private float xSpawnPoint;
    private float lastY;
    private float minGapSize;
    private float remainingSecondBeforeNextObstacle;
    private final float ceilingHeight;
    private final float minSecondsBetweenObstacles;
    private final float maxSecondsBetweenObstacles;
    private final LevelSettings levelSettings;
    private final static List<KillPlane> EMPTY_LIST = new LinkedList<>();

    public ObstacleGenerator(
        float xSpawnPoint,
        float ySpawnPointInitial,
        float minGapSize,
        float ceilingHeight,
        float minSecondsBetweenObstacles,
        float maxSecondsBetweenObstacles,
        LevelSettings levelSettings
    ) {
        this.xSpawnPoint = xSpawnPoint;
        this.lastY = ySpawnPointInitial;
        this.minGapSize = minGapSize;
        this.ceilingHeight = ceilingHeight;
        this.minSecondsBetweenObstacles = minSecondsBetweenObstacles;
        this.maxSecondsBetweenObstacles = maxSecondsBetweenObstacles;
        this.levelSettings = levelSettings;
        this.remainingSecondBeforeNextObstacle = 0; // MathUtils.random(minSecondsBetweenObstacles, maxSecondsBetweenObstacles);
    }

    public List<KillPlane> update(float delta) {
        remainingSecondBeforeNextObstacle = Math.max(0, remainingSecondBeforeNextObstacle - delta);
        if (remainingSecondBeforeNextObstacle == 0) {
            return next();
        }
        return EMPTY_LIST;
    }

    public List<KillPlane> next() {
        // Desired displacement
        float width = 0.5f;
        float y1 = this.lastY;
        float t2 = MathUtils.random(minSecondsBetweenObstacles, maxSecondsBetweenObstacles);
        float gapSize = MathUtils.lerp(minGapSize, minGapSize * 1.75f, MathUtils.random());
        this.remainingSecondBeforeNextObstacle = t2;

        // Now calculate a reasonable x2, y2 based on the amount of time t2 it takes to get there.
        float x2 = levelSettings.obstacleXSpeed * t2 - width;
        // We can either generate one above us, or below us, which gives us different slopes since
        // when rising we have to flap and counteract gravity, and for falling we have gravity itself.
        float slopeDown = levelSettings.gravity / levelSettings.obstacleXSpeed;
        float slopeUp = (levelSettings.flapLift - levelSettings.gravity) / levelSettings.obstacleXSpeed * -1;
        float slope = MathUtils.randomBoolean() ? slopeUp : slopeDown;
        float y2 = y1 - slope * x2;
        y2 = Math.max(0, Math.min(y2, ceilingHeight - gapSize));
        this.lastY = y2;

        KillPlane top = new KillPlane(
          xSpawnPoint,
          y2 + gapSize,
          width,
          ceilingHeight
        );
        top.setVelocity(new Vector2(levelSettings.obstacleXSpeed, 0));
        KillPlane bottom = new KillPlane(
          xSpawnPoint,
            0,
          width,
          y2 - gapSize
        );
        bottom.setVelocity(new Vector2(levelSettings.obstacleXSpeed, 0));

        List<KillPlane> newPlanes = new LinkedList<>();
        newPlanes.add(top);
        newPlanes.add(bottom);
        return newPlanes;
    }
}
