package spare.peetseater.nb;

public class LevelSettings {
    public float gravity = 2.2f;
    public float flapLift = 2.75f;
    public float decayRate = 0.75f;
    public float spawnObstacleEveryNSeconds = 4.5f;
    public float obstacleXSpeed = 1.05f;
    public float clickDelay = 0.04f;

    // We can make other types of levels with other factory methods later.
    public static LevelSettings createStandardLevelSettings() {
        LevelSettings settings = new LevelSettings();
        settings.gravity = 2.2f;
        settings.flapLift = 2.75f;
        settings.decayRate = 0.75f;
        settings.spawnObstacleEveryNSeconds = 4.5f;
        settings.obstacleXSpeed = 1.05f;
        settings.clickDelay = 0.04f;
        return settings;
    }

    @Override
    public String toString() {
        return "g: " + gravity + " f: " + flapLift + " d: " + decayRate + " s: " + spawnObstacleEveryNSeconds + " o: " + obstacleXSpeed + " c: " + clickDelay ;
    }
}
