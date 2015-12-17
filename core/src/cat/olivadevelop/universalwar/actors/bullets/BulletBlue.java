package cat.olivadevelop.universalwar.actors.bullets;

import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by Oliva on 15/04/2015.
 */
public class BulletBlue extends Bullet {

    public BulletBlue(GeneralScreen screen, float x, float y, float angle) {
        super(screen, x, y, 400);
        setRotation(angle);
        setColor(ColorGame.BLUE_CYAN);
    }
}
