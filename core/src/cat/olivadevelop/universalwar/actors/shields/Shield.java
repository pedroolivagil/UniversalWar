package cat.olivadevelop.universalwar.actors.shields;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import cat.olivadevelop.universalwar.actors.allied.Allied;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by Oliva on 15/04/2015.
 */
public class Shield extends Image {

    private static int impacts;
    private static int maxImpacts;
    public Circle circle;
    GeneralScreen screen;
    private Actor actorAllied;
    private float dir;
    private float vel;
    private float extraSize = 50;

    public Shield(GeneralScreen screen, TextureRegion region) {
        super(region);
        this.screen = screen;
        actorAllied = screen._groupAllied.getChildren().first();
        vel = Allied.vel;

        setWidth(actorAllied.getWidth() + extraSize);
        setHeight(actorAllied.getHeight() + extraSize);

        setOrigin(getWidth() / 2, getHeight() / 2);

        circle = new Circle(getX(), getY(), (getWidth() - 10) / 2);

        setPosition(actorAllied.getX() - (extraSize / 2), actorAllied.getY() - (extraSize / 2));
        aparecer();
    }

    public static int getImpacts() {
        return impacts;
    }

    public static void setImpacts(int impacts) {
        Shield.impacts = impacts;
        Shield.maxImpacts = impacts;
    }

    public static void reset() {
        Shield.impacts = maxImpacts;
    }

    public static void addImpacts(int impacts) {
        for (int z = 0; z < impacts; z++) {
            if (Shield.impacts < maxImpacts) {
                Shield.impacts += 1;
            }
        }
    }

    public static int getMaxImpacts() {
        return maxImpacts;
    }

    public void aparecer() {
        setScale(0);
        addAction(
                Actions.sequence(
                        Actions.scaleTo(1f, 1f, 1f, Interpolation.pow2)
                        , Actions.forever(
                                Actions.sequence(
                                        Actions.scaleBy(.03f, .03f, .1f),
                                        Actions.scaleTo(1f, 1f, .1f)
                                )
                        )
                )
        );
    }

    public void desaparecer() {
        addAction(
                Actions.sequence(
                        Actions.parallel(
                                Actions.fadeOut(.3f),
                                Actions.scaleTo(0, 0, 1f)
                        ),
                        Actions.removeActor()
                )
        );
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setPosition(Allied.x - (extraSize / 2), Allied.y - (extraSize / 2));
        circle.setPosition(getX() + getWidth() / 2, getY() + 50);
        if (impacts <= 0) {
            desaparecer();
        }
    }

    public void hitShield() {
        impacts--;
    }
}
