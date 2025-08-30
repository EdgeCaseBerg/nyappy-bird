package spare.peetseater.nb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/** First screen of the application. Displayed after the application is created. */
public class FirstScreen implements Screen {

    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private final FlapInputAdapter flapInputAdapter;
    NyappyBirdGame game;
    LevelSettings levelSettings;

    Texture playerTexture;
    Texture tunnelTexture;
    Player player;
    List<KillPlane> killPlanes;
    HashSet<KillPlane> passedPlanes;
    private boolean playerMustDie;
    ObstacleGenerator obstacleGenerator;
    private int score;

    public FirstScreen(NyappyBirdGame game, LevelSettings levelSettings) {
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(game.worldWidth, game.worldHeight, camera);
        this.camera.setToOrtho(false, game.worldWidth, game.worldHeight);
        this.game = game;
        this.score = 0;
        this.levelSettings = levelSettings;

        playerTexture = NyappyAssets.makeTexture(Color.BLUE);
        player = new Player(4,3, 1, 1);

        tunnelTexture = NyappyAssets.makeTexture(Color.RED);
        passedPlanes = new HashSet<>();
        killPlanes = new LinkedList<KillPlane>();
        // The floor and ceiling
        killPlanes.add(new KillPlane(0, 0, game.worldWidth, 1));
        killPlanes.add(new KillPlane(0, game.worldHeight - 1, game.worldWidth, 1));

         FlapInputSubscriber subscriber = new FlapInputSubscriber() {
             @Override
             public void onFlapInput() {
                 player.setLift(levelSettings.flapLift);
             }
         };
         flapInputAdapter = new FlapInputAdapter(levelSettings);
         flapInputAdapter.addSubscriber(subscriber);
         InputMultiplexer inputMultiplexer = new InputMultiplexer();
         inputMultiplexer.addProcessor(new LevelTuningInputAdapter(levelSettings));
         inputMultiplexer.addProcessor(flapInputAdapter);
         Gdx.input.setInputProcessor(inputMultiplexer);


        obstacleGenerator = new ObstacleGenerator(game.worldWidth - 1,game.worldHeight / 2, 2, game.worldHeight);
        generateNewObstacle();
    }

    private void generateNewObstacle() {
        Vector2 obstacleSpeed = new Vector2(levelSettings.obstacleXSpeed, 0);
        List<KillPlane> obstacles = obstacleGenerator.next(
            levelSettings.gravity,
            levelSettings.flapLift,
            obstacleSpeed.x
        );
        killPlanes.addAll(obstacles);
    }

    @Override
    public void show() {
        // Prepare your screen here.
    }

    private void update(float delta) {
        this.player.update(delta, levelSettings.gravity, levelSettings.decayRate);
        this.flapInputAdapter.update(delta);
        this.camera.update();
        this.game.batch.setProjectionMatrix(camera.combined);
        playerMustDie = false;
        float obstaclesPassed = 0;
        List<KillPlane> toRemove = new ArrayList<>();
        for (KillPlane killPlane : killPlanes) {
            if (killPlane.isOutOfSight()) {
                toRemove.add(killPlane);
            }
            killPlane.update(delta);
            playerMustDie = playerMustDie || killPlane.intersects(player);

            // Only count the planes we pass once.
            if (passedPlanes.contains(killPlane)) {
                continue;
            }

            boolean isFloorOrWall = killPlane.getLeftCornerX() == 0;
            if (isFloorOrWall) {
                continue;
            }

            float rightHandSide = killPlane.getLeftCornerX() + killPlane.getWidth();
            if (player.getX() > rightHandSide && !killPlane.isOutOfSight()) {
                passedPlanes.add(killPlane);
                obstaclesPassed += 1;
            }
        }

        score += (int) (obstaclesPassed / 2);

        if (playerMustDie) {
            game.setScreen(new GameOverScreen(game, score));
        }

        // clean up memory references.
        passedPlanes.removeAll(toRemove);
        killPlanes.removeAll(toRemove);
    }

    private void draw(float delta) {
        ScreenUtils.clear(Color.YELLOW);
        this.game.batch.begin();
        this.game.batch.draw(playerTexture, player.getX(), player.getY(), 1, 1);
        for (KillPlane killPlane : killPlanes) {
            this.game.batch.draw(
                tunnelTexture,
                killPlane.getLeftCornerX(), killPlane.getLeftCornerY(),
                killPlane.getWidth(), killPlane.getHeight()
            );
        }
        this.game.visitorFont.draw(
            this.game.batch,
            "Score: " + score,
            0.25f,
            0.75f,
            game.worldWidth,
            Align.left,
            false
        );
        this.game.batch.end();
    }

    // This is tmp until we create a proper algo
    float accum = 0;
    @Override
    public void render(float delta) {
        accum += delta;
        if (accum > levelSettings.spawnObstacleEveryNSeconds) {
            generateNewObstacle();
            accum = 0;
        }
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
