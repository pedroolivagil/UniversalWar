package cat.olivadevelop.universalwar.actors.enemies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import cat.olivadevelop.universalwar.actors.allied.Allied;
import cat.olivadevelop.universalwar.actors.explosions.ExplosionMedium;
import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.GameActor;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

import static cat.olivadevelop.universalwar.tools.GameLogic.getCountEnemiesDispached;

/**
 * Created by Oliva on 15/04/2015.
 */
public class Enemy extends GameActor {
    // Basic
    public static final String[] BASIC = new String[]{
            "Valquiria", "Dark Cloud", "Bealus", "Imperial", "Skylab", "Soyuz Spacecraft", "X-256", "Smart-X", "Cluster", "Surveyor"
    };
    // Advanced
    public static final String[] ADVANCED = new String[]{
            "Galileo", "Genesis", "Boeing X-35", "Hopper", "Hermes", "Orion", "Discovery", "State-14", "RST-120", "Raknarok"
    };
    // Boss
    public static final String[] BOSS = new String[]{
            "ufoGreen", "ufoBlue", "ufoRed", "ufoYellow"
    };
    public int maxHealth;
    public int health;
    public GeneralScreen screen;
    protected float time;
    int score;
    float newX;
    float newY;
    private boolean showLive;
    private float width;

    public Enemy(GeneralScreen screen, TextureRegion tRegion) {
        super(tRegion);
        this.screen = screen;
        health = 0;
        time = MathUtils.random(2, 3);
        setPosition(MathUtils.random(0, GameLogic.getScreenWidth()), GameLogic.getScreenHeight() + 50);
        setScale(1);
        width = getWidth() * getScaleX();
        showLive = false;
    }

    private float calcDegree(float newX, float newY) {
        // A(newX, newY), B(getX(), getY())
        double finalDeg = Math.atan2(newY - getY(), newX - getX());
        return (float) Math.toDegrees(finalDeg) + 90;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (isShowLive()) {
            batch.end();
            shape.setProjectionMatrix(batch.getProjectionMatrix());
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(ColorGame.DARK_GREEN);
            shape.rect(polygon.getX(), polygon.getY() - (10 + (12 * getScaleX())), ((width) / 2), 8 / 2f, width, 8, getScaleX(), getScaleY(), 0);
            shape.setColor(ColorGame.GREEN);
            for (int z = 0; z < getHealth(); z++) {
                shape.rect(-10 + polygon.getX() + ((width / getMaxHealth() + 2) * z), polygon.getY() - (11 + (10 * getScaleX())), (width / getMaxHealth() / 2), 5 / 2f, width / getMaxHealth(), 5, getScaleX(), getScaleY(), 0);
            }
            shape.end();
            batch.begin();
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        polygon.setPosition(getX(), getY());
        if (getActions().size == 0) { // si no hay acciones
            newX = MathUtils.random(0, GameLogic.getScreenWidth() - 15);
            newY = MathUtils.random(GameLogic.getScreenHeight() / 3, GameLogic.getScreenHeight());
            addAction(Actions.sequence(Actions.parallel(
                            Actions.rotateTo(calcDegree(newX, newY), .5f),
                            Actions.moveTo(newX, newY, time))
            ));
            if (alive) {
                if (screen._groupAllied.hasChildren()) {
                    Allied a = (Allied) screen._groupAllied.getChildren().first();
                    if (a.alive) {
                        if (MathUtils.random(0, 2) == 1) {
                            shoot();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void kicked() {
        super.kicked();
        health--;
        if (health <= 0) {
            death();
        }
    }

    @Override
    public void death() {
        super.death();
        GameLogic.addToScore(score);
        GameLogic.addCountEnemiesDispached();
        if ((getCountEnemiesDispached() % 40 == 0 && getCountEnemiesDispached() != 0) && (GameLogic.getMaxEnemiesIntoGroup() < 70)) {
            GameLogic.addMaxEnemiesIntoGroup();
        }
        screen._stage.addActor(new ExplosionMedium(this));
        addAction(Actions.delay(.3f, Actions.removeActor()));
        drop();
    }

    public float calcDrop() {
        return (float) MathUtils.random(1, 100);
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        this.maxHealth = health;
    }

    public boolean isShowLive() {
        return showLive;
    }

    public void setShowLive(boolean showLive) {
        this.showLive = showLive;
    }
}
