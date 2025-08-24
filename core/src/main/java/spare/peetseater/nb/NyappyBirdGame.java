package spare.peetseater.nb;

import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class NyappyBirdGame extends Game {
    public float worldWidth = 8;
    public float worldHeight = 6;

    @Override
    public void create() {
        setScreen(new FirstScreen(this));
    }
}
