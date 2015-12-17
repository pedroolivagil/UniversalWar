package cat.olivadevelop.universalwar.actors.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import cat.olivadevelop.universalwar.tools.ActorGame;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

import static cat.olivadevelop.universalwar.tools.GameLogic.getPlanets;

/**
 * Created by Oliva on 15/04/2015.
 */
public class Planet extends ActorGame {

    public static String[] planets = new String[]{
            "venus", "star", "mars", "luna", "jupiter", "acid", "earth", "green", "pluton", "purple", "tankard", "morado", "saturno", "west", "pipiolo", "ginteky"
    };
    public static float[] speed_planets = new float[]{
            -3.8f, -3.2f, -2.3f
    };
    private float degrees;
    private float vel;
    private TextureRegion region;

    public Planet(GeneralScreen screen, String planet, float vel, float size) {
        super(screen);
        this.region = getPlanets(planet);
        this.vel = vel;
        float x = MathUtils.random(-20, GameLogic.getScreenWidth() - 200);
        float y = GameLogic.getScreenHeight();
        degrees = (MathUtils.random(0, 1) == 0) ? .1f : -.1f;
        setPosition(x, y);
        setWidth(this.region.getRegionWidth());
        setHeight(this.region.getRegionHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);
        setScale(size);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(region,
                getX(),
                getY(),
                getOriginX(),
                getOriginY(),
                getWidth(),
                getHeight(),
                getScaleX(),
                getScaleY(),
                getRotation()
        );
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        moveBy(0, vel / 2);
        rotateBy(degrees * delta * 100);

        if (getY() < -getHeight()) {
            remove();
        }
    }
}
