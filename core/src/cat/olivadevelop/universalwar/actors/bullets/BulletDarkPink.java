package cat.olivadevelop.universalwar.actors.bullets;

import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by Oliva on 15/04/2015.
 */
public class BulletDarkPink extends Bullet {

    public BulletDarkPink(GeneralScreen screen, float x, float y, float angle) {
        super(screen, x, y, 500);
        setRotation(angle);
        setColor(ColorGame.DARK_PINK);
    }
}