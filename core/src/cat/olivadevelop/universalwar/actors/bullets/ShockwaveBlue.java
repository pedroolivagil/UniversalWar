package cat.olivadevelop.universalwar.actors.bullets;

import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by OlivaDevelop on 25/08/2015.
 */
public class ShockwaveBlue extends Shockwave {

    public ShockwaveBlue(GeneralScreen screen, float x, float y) {
        super(screen, x, y, .5f);
        setColor(ColorGame.BLUE_CYAN);
        setSpeed(4);
    }
}
