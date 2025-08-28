package spare.peetseater.nb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

public class LevelTuningInputAdapter extends InputAdapter {
    private final LevelSettings levelSettings;

    public LevelTuningInputAdapter(LevelSettings levelSettings) {
        this.levelSettings = levelSettings;
    }
    @Override
    public boolean keyTyped(char character) {

        switch (character) {
            case 'g':  levelSettings.gravity -= 0.05f; break;
            case 'G':  levelSettings.gravity += 0.05f; break;
            case 'f': levelSettings.flapLift -= 0.05f; break;
            case 'F': levelSettings.flapLift += 0.05f; break;
            case 'd': levelSettings.decayRate -= 0.05f; break;
            case 'D': levelSettings.decayRate += 0.05f; break;
            case 's': levelSettings.spawnObstacleEveryNSeconds -= 0.05f; break;
            case 'S': levelSettings.spawnObstacleEveryNSeconds += 0.05f; break;
            case 'o': levelSettings.obstacleXSpeed -= 0.05f; break;
            case 'O': levelSettings.obstacleXSpeed += 0.05f; break;
            case 'c': levelSettings.clickDelay -= 0.05f; break;
            case 'C': levelSettings.clickDelay += 0.05f; break;
            default:
                return false;
        }
        Gdx.app.log("SETTINGS", "New settings: ".concat(levelSettings.toString()));
        return true;
    }
}
