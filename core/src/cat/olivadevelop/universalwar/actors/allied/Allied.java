package cat.olivadevelop.universalwar.actors.allied;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import cat.olivadevelop.universalwar.actors.explosions.ExplosionMedium;
import cat.olivadevelop.universalwar.actors.ui.HUD;
import cat.olivadevelop.universalwar.tools.GameActor;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by Oliva on 15/04/2015.
 */
public abstract class Allied extends GameActor {

    public static float vel; // velocidad de la nave
    public static float x;
    public static float y;
    protected static int health = 0;
    protected static int maxHealth = 0;
    public Fire fire;
    float dir; // direccion de la nave
    GeneralScreen screen;
    private int posY = HUD.tableBottomHeight + 60;

    public Allied(GeneralScreen screen, TextureRegion tRegion) {
        super(tRegion);
        this.screen = screen;
        // colocamos la pantalla en el centro inferior de la pantalla
        //setPosition((GameLogic.getScreenWidth() - getWidth()) / 2, posY);
        vel = 500;
        setPosition((GameLogic.getScreenWidth() - getWidth()) / 2, -getHeight());
        enter();
    }

    public static int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        Allied.health = health;
        maxHealth = health;
    }

    public static int getMaxHealth() {
        return maxHealth;
    }

    public static void addLiveGame(int lives) {
        health += lives;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        dir = -Gdx.input.getAccelerometerX() / 4;

        if (dir == 0) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
                dir = 1;
            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
                dir = -1;
            }
        }

        if (alive) {
            moveBy(dir * vel * delta, 0);
            x = getX();
            y = getY();
            polygon.setPosition(x - 310, posY + getHeight() - 20);
            // comprobamos que la nave no de salga de la pantalla
            if (getX() < 5) {
                setX(10.1f);
            } else if (getX() > (GameLogic.getScreenWidth() - (getWidth() + 5))) {
                setX(GameLogic.getScreenWidth() - (getWidth() + 10));
            }
            if (Gdx.input.justTouched() | Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                shoot();
            }
        }
    }

    @Override
    public void kicked() {
        super.kicked();
        try {
            if (GameLogic.getLivesGame() == 0) {
                Gdx.input.vibrate(1000);
            } else {
                Gdx.input.vibrate(200);
            }
        } catch (Exception e) {
            Gdx.app.log("VIBRATE", " --ERROR-- Can't vibrate");
        }
        lostLiveGame();
        if (getHealth() <= 0) {
            this.death();
        }
    }

    public void death() {
        super.death();
        screen._stage.addActor(new ExplosionMedium(this));
        addAction(
                Actions.sequence(
                        Actions.delay(1f),
                        Actions.scaleTo(0f, 0f, 0f),
                        Actions.delay(2f,
                                Actions.removeActor()
                        )
                )
        );
    }

    private void lostLiveGame() {
        health--;
    }

    public void exit() {
        clearActions();
        addAction(
                Actions.sequence(
                        Actions.moveTo(getX(), -200, .8f),
                        Actions.removeActor()
                )
        );
    }

    public Allied enter() {
        clearActions();
        addAction(
                Actions.moveBy(0, posY + getHeight(), .8f)
        );
        return this;
    }

    public Allied enter(int posY) {
        clearActions();
        addAction(
                Actions.moveTo(getX(), posY + getHeight(), .8f)
        );
        return this;
    }
}
