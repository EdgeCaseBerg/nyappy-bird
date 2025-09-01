package spare.peetseater.nb;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class NyappyBirdGame extends Game {
    public float worldWidth = 12f;
    public float worldHeight = 9f;

    SpriteBatch batch;
    BitmapFont visitorFont;
    Animation<TextureRegion> playerSprite;
    Animation<TextureRegion> rainbowSprite;
    NinePatch obstacleNinePatch;
    Animation<TextureRegion> starSprite;
    Texture background;
    Music bgm;
    private PlayScreen playScreen;
    private TitleScreen titleScreen;
    private LevelSettings settings;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.visitorFont = this.loadFont(1f);
        this.playerSprite = this.loadNyanCat();
        this.rainbowSprite = this.loadRainbow();
        this.obstacleNinePatch = this.loadObstacleNinePatch();
        this.starSprite = this.loadStar();
        this.background = this.loadBackground();
        this.bgm = this.loadBGM();

        settings = LevelSettings.createStandardLevelSettings();
        playScreen = new PlayScreen(this, settings);
        titleScreen = new TitleScreen(this);
        showTitleScreen();
    }

    public void showTitleScreen() {
        setScreen(titleScreen);
    }

    public void showPlayScreen() {
        if (!this.bgm.isPlaying()) {
            this.bgm.play();
        }
        playScreen = new PlayScreen(this, settings);
        setScreen(playScreen);
    }

    private BitmapFont loadFont(float ratioTo1WorldUnit) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("visitor/visitor1.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = (int)((1 / worldHeight) * Gdx.graphics.getHeight() * ratioTo1WorldUnit);
        parameter.color = Color.WHITE;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;

        BitmapFont font = generator.generateFont(parameter);
        font.getData().setScale(worldWidth / (float)Gdx.graphics.getWidth(), worldHeight / (float)Gdx.graphics.getHeight());
        font.setUseIntegerPositions(false);
        return font;
    }

    private Animation<TextureRegion> loadNyanCat() {
        Texture sheet = new Texture(Gdx.files.internal("sprite/nyankitty.png"));
        TextureRegion[][] frames = TextureRegion.split(sheet, 32, 24);
        Animation<TextureRegion> animation = new Animation<>(0.025f, frames[0]);
        return animation;
    }

    private Animation<TextureRegion> loadRainbow() {
        Texture sheet = new Texture(Gdx.files.internal("sprite/nyanbow.png"));
        TextureRegion[][] frames = TextureRegion.split(sheet, 32, 24);
        Animation<TextureRegion> animation = new Animation<>(0.025f, frames[0]);
        return animation;
    }

    private NinePatch loadObstacleNinePatch() {
        Texture texture = new Texture(Gdx.files.internal("sprite/obstacle.png"));
        NinePatch ninePatch = new NinePatch(texture, 4, 4, 4, 4);
        ninePatch.scale(worldWidth / (float)Gdx.graphics.getWidth(), worldHeight / (float)Gdx.graphics.getHeight());
        return ninePatch;
    }

    private Animation<TextureRegion> loadStar() {
        Texture sheet = new Texture(Gdx.files.internal("sprite/star.png"));
        TextureRegion[][] frames = TextureRegion.split(sheet, 8, 8);
        Animation<TextureRegion> animation = new Animation<>(0.15f, frames[0]);
        return animation;
    }

    private Texture loadBackground() {
        Texture texture = new Texture(Gdx.files.internal("sprite/bg.png"));
        return texture;
    }

    private Music loadBGM() {
        Music music = Gdx.audio.newMusic(Gdx.files.internal("music/nyancat.mp3"));
        music.setVolume(0.5f);;
        music.setLooping(true);
        return music;
    }
}
