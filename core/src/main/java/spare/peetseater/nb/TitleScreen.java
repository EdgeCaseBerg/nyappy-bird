package spare.peetseater.nb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class TitleScreen implements Screen {

    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private final ScrollingStars stars;
    NyappyBirdGame game;

    public TitleScreen(NyappyBirdGame game) {
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, game.worldWidth, game.worldHeight);
        this.viewport = new FitViewport(game.worldWidth, game.worldHeight, camera);
        this.stars = new ScrollingStars(25, game.worldWidth, game.worldHeight, 0.1f);
        this.game = game;
    }

    @Override
    public void show() {
    }

    private void update(float delta) {
        this.camera.update();
        this.stars.update(delta);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.showPlayScreen();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void draw(float delta) {
        ScreenUtils.clear(Color.BLACK);
        game.batch.begin();
        this.game.batch.setProjectionMatrix(camera.combined);
        game.batch.draw(
            game.background,
            0,0, game.worldWidth, game.worldHeight
        );
        this.stars.draw(game);
        game.visitorFont.draw(
            game.batch,
            "NYAPPY CAT",
            0,
            game.worldHeight - 2,
            game.worldWidth,
            Align.center,
            false
        );
        game.batch.draw(
            game.playerSprite.getKeyFrame(0, false),
            game.worldWidth / 2, game.worldHeight - 4,
            1, 1
        );
        game.visitorFont.draw(
            game.batch,
            "SPACE to start",
            0,
            game.worldHeight - 6,
            game.worldWidth,
            Align.center,
            false
        );
        game.visitorFont.draw(
            game.batch,
            "ESC to quit",
            0,
            game.worldHeight - 8,
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
