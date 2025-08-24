package spare.peetseater.nb;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class NyappyBirdGame extends Game {
    public float worldWidth = 12f;
    public float worldHeight = 9f;

    SpriteBatch batch;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        setScreen(new FirstScreen(this));
    }
}
