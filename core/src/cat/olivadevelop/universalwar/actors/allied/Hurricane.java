package cat.olivadevelop.universalwar.actors.allied;

import com.badlogic.gdx.math.Polygon;

import cat.olivadevelop.universalwar.actors.bullets.Bullet;
import cat.olivadevelop.universalwar.actors.bullets.BulletDarkPink;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by Oliva on 15/04/2015.
 */
public class Hurricane extends Allied {

    public Hurricane(GeneralScreen screen) {
        super(screen, GameLogic.getPlayer("playerShip3_red"));
        setHealth(5);
        polygon = new Polygon(new float[]{
                // ala izquierda
                getX() + 35, getY() + 20,
                getX() - 3, getY() + 30,
                // centro
                getX() + 45, getY() + getHeight() + 20,
                getX() + 50, getY() + getHeight() + 20,
                // ala derecha
                getX() + getWidth(), getY() + 30,
                getX() + getWidth() - 40, getY() + 20
        });
        fire = new Fire(screen, getX(), getY(), 0);
        screen.getStage().addActor(fire);
        setName("Hurricane");
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
        screen.getStage().addActor(new BulletDarkPink(screen, getX() + getWidth() / 2 - 7, getY() + 2, Bullet.BULLET_UP));
        if (GameLogic.isAudioOn()) {
            GameLogic.getSoundShootNormal().play();
        }
    }
}
