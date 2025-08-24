package spare.peetseater.nb;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** First screen of the application. Displayed after the application is created. */
public class FirstScreen implements Screen {

    private final OrthographicCamera camera;
    private final FitViewport viewport;
    NyappyBirdGame game;

    Texture playerTexture;

    public FirstScreen(NyappyBirdGame game) {
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(game.worldWidth, game.worldHeight, camera);
        this.camera.setToOrtho(false, game.worldWidth, game.worldHeight);
        this.game = game;

        playerTexture = NyappyAssets.makeTexture(Color.BLUE);
    }

    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
        this.camera.update();
        this.game.batch.setProjectionMatrix(camera.combined);
        ScreenUtils.clear(Color.YELLOW);
        this.game.batch.begin();
        this.game.batch.draw(playerTexture, 4,3, 1, 1);
        this.game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }
}
