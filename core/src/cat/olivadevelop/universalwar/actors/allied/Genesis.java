package cat.olivadevelop.universalwar.actors.allied;

import com.badlogic.gdx.math.Polygon;

import cat.olivadevelop.universalwar.actors.bullets.Bullet;
import cat.olivadevelop.universalwar.actors.bullets.BulletDarkPink;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;
import cat.olivadevelop.universalwar.tools.Listener;

/**
 * Created by Oliva on 15/04/2015.
 */
public class Genesis extends Allied {

    public Genesis(GeneralScreen screen) {
        super(screen, GameLogic.getPlayer("playerShip2_orange"));
        setHealth(5);
        polygon = new Polygon(new float[]{
                // ala izquierda
                getX() + 3, getY() + 17,
                getX(), getY() + 46,
                getX() + 6, getY() + 46,
                getX() + 12, getY() + 30,
                getX() + 37, getY() + 50,
                // centro
                getX() + 41, getY() + getHeight(),
                getX() + 57, getY() + getHeight(),
                // ala derecha
                getX() + getWidth() - 37, getY() + 50,
                getX() + getWidth() - 12, getY() + 30,
                getX() + getWidth() - 6, getY() + 46,
                getX() + getWidth(), getY() + 46,
                getX() + getWidth() - 3, getY() + 17
        });
        setName("Genesis");
        fire = new Fire(screen, getX(), getY(), 0);
        screen.getStage().addActor(fire);
        addListener(new Listener() {
            @Override
            public void action() {
                shoot();
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        fire.setPosition(getX() + getOriginX() - fire.getOriginX() + 7, getY() - fire.getHeight() + 2);
    }

    @Override
    public void death() {
        fire.remove();
        super.death();
    }

    @Override
    public void shoot() {
        super.shoot();
        screen.getStage().addActor(new BulletDarkPink(screen, getX() + getWidth() / 2 - 35, getY() + 2, Bullet.BULLET_UP));
        screen.getStage().addActor(new BulletDarkPink(screen, getX() + getWidth() / 2 + 20, getY() + 2, Bullet.BULLET_UP));
        if (GameLogic.isAudioOn()) {
            GameLogic.getSoundShootNormal().play();
        }
    }
}
