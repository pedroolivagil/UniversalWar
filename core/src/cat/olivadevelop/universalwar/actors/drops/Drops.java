package cat.olivadevelop.universalwar.actors.drops;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import cat.olivadevelop.universalwar.actors.allied.Allied;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;
import cat.olivadevelop.universalwar.tools.IntersectorGame;

import static cat.olivadevelop.universalwar.tools.GameLogic.VOLUME_10;
import static cat.olivadevelop.universalwar.tools.GameLogic.getSoundPowerUp;

/**
 * Created by Oliva on 15/04/2015.
 */
public class Drops extends Image {

    private final float vel;
    public Rectangle rectangle;
    GeneralScreen screen;
    private Color color;
    private boolean staticObj;

    public Drops(GeneralScreen screen, TextureRegion tRegion, float x, float y, float vel, boolean staticObj) {
        super(tRegion);
        this.screen = screen;
        setOrigin(getWidth() / 2, getHeight() / 2);
        setPosition(x, y);
        rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
        this.vel = -vel;
        this.staticObj = staticObj;
        addAction(
                Actions.forever(
                        Actions.delay(.5f,
                                Actions.repeat(2,
                                        Actions.parallel(
                                                Actions.scaleBy(.2f, .2f, .3f),
                                                Actions.scaleTo(.7f, .7f, .1f)
                                        )
                                )
                        )
                )
        );
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        rectangle.setPosition(getX(), getY());
        addAction(
                Actions.moveBy(0, -3f, vel * delta)
        );

        if (this.getY() < -getHeight()) {
            remove();
        }

        if (!this.staticObj) {
            Allied allied;
            for (Actor a : screen._groupAllied.getChildren()) {
                allied = (Allied) a;
                //if (allied.alive && rectangle.overlaps(allied.rectangle)) {
                if (allied.alive && IntersectorGame.overlaps(allied.polygon, rectangle)) {
                    actionsObject();
                    remove();
                    if (GameLogic.isAudioOn()) {
                        getSoundPowerUp().play(VOLUME_10);
                    }
                }
            }
        }
    }

    public void actionsObject() {

    }
}
