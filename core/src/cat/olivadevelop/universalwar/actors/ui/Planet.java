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
            "cloudy1", "cloudy2", "cloudy3", "cloudy4", "cloudy5", "cloudy6", "cloudy7", "cloudy8",
            "cloudy9", "cloudy10", "cloudy11", "cloudy12", "cloudy13", "cloudy14", "cloudy15", "cloudy16",
            "leaky1", "leaky2", "leaky3", "leaky4",
            "rings1", "rings2", "rings3", "rings4", "rings5", "rings6", "rings7", "rings8", "rings9",
            "rings10", "rings11", "rings12", "rings13",
            "rock1", "rock2", "rock3", "rock4", "rock5",
            "sun"
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
        //rotateBy(degrees * delta * 100);

        if (getY() < -getHeight()) {
            remove();
        }
    }
}
