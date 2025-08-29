package spare.peetseater.nb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameOverScreen implements Screen {

    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private final int score;
    NyappyBirdGame game;

    public GameOverScreen(NyappyBirdGame game, int score) {
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(game.worldWidth, game.worldHeight, camera);
        this.camera.setToOrtho(false, game.worldWidth, game.worldHeight);
        this.game = game;
        this.score = score;
    }

    @Override
    public void show() {
    }

    private void update(float delta) {
        this.camera.update();
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            // TODO: deal with this nicely.
            game.setScreen(new FirstScreen(game, LevelSettings.createStandardLevelSettings()));
        }
    }

    private void draw(float delta) {
        ScreenUtils.clear(Color.BLACK);
        game.batch.begin();
        game.visitorFont.draw(
            game.batch,
            "Game over!",
            0,
            game.worldHeight - 2,
            game.worldWidth,
            Align.center,
            false
        );
        game.visitorFont.draw(
            game.batch,
            "Score: " + score,
            0,
            game.worldHeight - 4,
            game.worldWidth,
            Align.center,
            false
        );
        game.visitorFont.draw(
            game.batch,
            "SPACE to restart",
            0,
            game.worldHeight - 6,
            game.worldWidth,
            Align.center,
            false
        );
        game.batch.end();
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw(delta);
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

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
