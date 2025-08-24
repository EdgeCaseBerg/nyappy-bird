package spare.peetseater.nb;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.LinkedList;
import java.util.List;

/** First screen of the application. Displayed after the application is created. */
public class FirstScreen implements Screen {

    private final OrthographicCamera camera;
    private final FitViewport viewport;
    NyappyBirdGame game;

    Texture playerTexture;
    Texture tunnelTexture;
    Player player;
    List<KillPlane> killPlanes;
    private boolean playerMustDie;

    public FirstScreen(NyappyBirdGame game) {
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(game.worldWidth, game.worldHeight, camera);
        this.camera.setToOrtho(false, game.worldWidth, game.worldHeight);
        this.game = game;

        playerTexture = NyappyAssets.makeTexture(Color.BLUE);
        player = new Player(4,3, 1, 1);

        tunnelTexture = NyappyAssets.makeTexture(Color.RED);
        killPlanes = new LinkedList<KillPlane>();
        // The floor and ceiling
        killPlanes.add(new KillPlane(0, 0, game.worldWidth, 1));
        killPlanes.add(new KillPlane(0, game.worldHeight - 1, game.worldWidth, 1));

        KillPlane jaw1Bottom = new KillPlane(6, 0, 0.5f, 3);
        KillPlane jaw1Top = new KillPlane(6, 5, 0.5f, 3);
        Vector2 obstacleSpeed = new Vector2(1, 0);
        jaw1Bottom.setVelocity(obstacleSpeed);
        jaw1Top.setVelocity(obstacleSpeed);
        killPlanes.add(jaw1Bottom);
        killPlanes.add(jaw1Top);
    }

    @Override
    public void show() {
        // Prepare your screen here.
    }

    private void update(float delta) {
        this.camera.update();
        this.game.batch.setProjectionMatrix(camera.combined);
        this.player.update(delta, 0.5f);
        playerMustDie = false;
        for (KillPlane killPlane : killPlanes) {
            killPlane.update(delta);
            playerMustDie = playerMustDie || killPlane.intersects(player);
        }
    }

    private void draw(float delta) {
        ScreenUtils.clear(Color.YELLOW);
        if (playerMustDie) {
            ScreenUtils.clear(Color.GREEN);
        }
        this.game.batch.begin();
        this.game.batch.draw(playerTexture, player.getX(),player.getY(), 1, 1);
        for (KillPlane killPlane : killPlanes) {
            this.game.batch.draw(
                tunnelTexture,
                killPlane.getLeftCornerX(), killPlane.getLeftCornerY(),
                killPlane.getWidth(), killPlane.getHeight()
            );
        }
        this.game.batch.end();
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
