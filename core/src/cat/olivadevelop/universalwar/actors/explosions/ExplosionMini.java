package cat.olivadevelop.universalwar.actors.explosions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.GameLogic;

import static cat.olivadevelop.universalwar.tools.GameLogic.VOLUME_5;
import static cat.olivadevelop.universalwar.tools.GameLogic.getSoundExplode;
import static cat.olivadevelop.universalwar.tools.GameLogic.getSprites;

/**
 * Created by Oliva on 15/04/2015.
 */
public class ExplosionMini extends Actor {

    private final Animation animation;
    private float elapsedTime;
    private Texture texture;

    public ExplosionMini(float x, float y, float rotation) {
        texture = GameLogic.getExplosionMedium();
        animation = new Animation(1 / 40f, getSprites(8, 6, texture));
        setPosition(x, y);
        setRotation(rotation);
        setHeight(texture.getHeight() / 6);
        setWidth(texture.getWidth() / 8);
        setOrigin(getWidth() / 2, getHeight() / 2);
        setScale(.2f);
        if (GameLogic.isAudioOn()) {
            getSoundExplode().play(VOLUME_5);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(ColorGame.WHITE);
        super.draw(batch, parentAlpha);
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(elapsedTime, false), getX() - getWidth() / 2, getY() - (getHeight() * .2f) * 2, getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        if (animation.isAnimationFinished(elapsedTime)) {
            remove();
        }
    }
}
