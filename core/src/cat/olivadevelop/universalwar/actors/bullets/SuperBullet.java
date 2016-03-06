package cat.olivadevelop.universalwar.actors.bullets;

import com.badlogic.gdx.scenes.scene2d.Group;

import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by OlivaDevelop on 25/08/2015.
 */
public class SuperBullet extends Bullet {

    private Group[] enemy;

    public SuperBullet(GeneralScreen screen, float x, float y, float angle) {
        super(screen, x, y, 300);
        setRotation(angle);
        setColor(ColorGame.PURPLE_BULLET);
        setScale(1.2f);
        this.enemy[0] = screen._groupEnemy;
    }

    public SuperBullet(GeneralScreen screen, Group[] enemy, float x, float y, float angle) {
        super(screen, x, y, 300);
        setRotation(angle);
        setColor(ColorGame.PURPLE_BULLET);
        setScale(1.2f);
        this.enemy = enemy;
    }

    @Override
    public void removeBullet() {
        super.removeBullet();
        screen.getStage().addActor(new ShockwavePurple(screen, enemy, getX() + (getWidth() / 2), (getRotation() < 180) ? getY() + getHeight() + 20 : getY() + 10));
    }
}
