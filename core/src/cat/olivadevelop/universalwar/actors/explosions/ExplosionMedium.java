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
public class ExplosionMedium extends Actor {

    private final Animation animation;
    private final Actor actor;
    private float elapsedTime;
    private Texture texture;


    public ExplosionMedium(Actor actor) {
        //actorAllied = GameScreen._groupAllied.getChildren().first();
        this.actor = actor;
        texture = GameLogic.getExplosionMedium();
        animation = new Animation(1 / 40f, getSprites(8, 6));
        setWidth(texture.getWidth() / 8);
        setHeight(texture.getHeight() / 6);
        setOrigin(getWidth() / 2, getHeight() / 2);
        if (GameLogic.isAudioOn()) {
            GameLogic.getSoundExplode().play();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(ColorGame.WHITE);
        super.draw(batch, parentAlpha);
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(elapsedTime, false), actor.getX() - (getWidth() / 2) + (getWidth() / 8), actor.getY() - getHeight() / 6);
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