package cat.olivadevelop.universalwar.actors.bullets;

import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by OlivaDevelop on 25/08/2015.
 */
public class MegaBullet extends Bullet {

    public MegaBullet(GeneralScreen screen, float x, float y, float angle) {
        super(screen, x, y, 800);
        setRotation(angle);
        setColor(ColorGame.BLUE_CYAN);
        setScale(1.2f);
    }

    @Override
    public void removeBullet() {
        screen.getStage().addActor(new ShockwaveBlue(screen, getX() + (getWidth() / 2), (getRotation() < 180) ? getY() + getHeight() + 20 : getY() + 10));
        super.removeBullet();
    }
}
