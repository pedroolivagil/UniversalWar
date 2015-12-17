package cat.olivadevelop.universalwar.actors.bullets;

import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by OlivaDevelop on 25/08/2015.
 */
public class SuperBullet extends Bullet {

    public SuperBullet(GeneralScreen screen, float x, float y, float angle) {
        super(screen, x, y, 300);
        setRotation(angle);
        setColor(ColorGame.PURPLE_BULLET);
        setScale(1.2f);
    }

    @Override
    public void removeBullet() {
        super.removeBullet();
        screen._stage.addActor(new ShockwavePurple(screen, getX() + (getWidth() / 2), (getRotation() < 180) ? getY() + getHeight() + 20 : getY() + 10));
    }
}
