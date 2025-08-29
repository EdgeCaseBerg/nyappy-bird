package spare.peetseater.nb;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class NyappyBirdGame extends Game {
    public float worldWidth = 12f;
    public float worldHeight = 9f;

    SpriteBatch batch;
    BitmapFont visitorFont;

    AssetManager assetManager;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.visitorFont = this.loadFont(1f);

        LevelSettings settings = LevelSettings.createStandardLevelSettings();
        setScreen(new FirstScreen(this, settings));
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
}
