package cat.olivadevelop.universalwar.actors.explosions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.GameLogic;

/**
 * Created by Oliva on 15/04/2015.
 */
public class ExplosionMini extends Actor {

    private final Animation animation;
    private float elapsedTime;
    private Texture texture;

    public ExplosionMini(float x, float y, float rotation) {
        texture = GameLogic.getExplosionMini();
        animation = new Animation(1 / 30f, getSprites(5, 5));
        setPosition(x, y);
        setRotation(rotation);
        setHeight(texture.getHeight() / 5);
        setWidth(texture.getWidth() / 5);
        setOrigin(getWidth() / 2, getHeight() / 2);
        setScale(1);
        if (GameLogic.isAudioOn()) {
            GameLogic.getSoundExplode().play();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(ColorGame.WHITE);
        super.draw(batch, parentAlpha);
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(elapsedTime, false), getX() - getWidth() / 2, getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        if (animation.isAnimationFinished(elapsedTime)) {
            remove();
        }
    }

    private TextureRegion[] getSprites(int cols, int rows) {
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / cols, texture.getHeight() / rows);
        TextureRegion[] Frames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Frames[index++] = tmp[i][j];
            }
        }
        return Frames;
    }
}
