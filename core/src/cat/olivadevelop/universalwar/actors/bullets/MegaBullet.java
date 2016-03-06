package cat.olivadevelop.universalwar.actors.bullets;

import com.badlogic.gdx.scenes.scene2d.Group;

import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by OlivaDevelop on 25/08/2015.
 */
public class MegaBullet extends Bullet {
    Group[] enemy;

    public MegaBullet(GeneralScreen screen, float x, float y, float angle) {
        // constructor usado para el arcade
        super(screen, x, y, 800);
        setRotation(angle);
        setColor(ColorGame.BLUE_CYAN);
        setScale(1.2f);
        this.enemy[0] = screen._groupEnemy;
    }

    public MegaBullet(GeneralScreen screen, Group[] enemy, float x, float y, float angle) {
        super(screen, x, y, 800);
        setRotation(angle);
        setColor(ColorGame.BLUE_CYAN);
        setScale(1.2f);
        this.enemy = enemy;
    }

    @Override
    public void removeBullet() {
        screen.getStage().addActor(new ShockwaveBlue(screen, enemy, getX() + (getWidth() / 2), (getRotation() < 180) ? getY() + getHeight() + 20 : getY() + 10));
        super.removeBullet();
    }
}
