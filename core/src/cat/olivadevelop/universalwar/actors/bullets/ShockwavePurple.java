package cat.olivadevelop.universalwar.actors.bullets;

import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by OlivaDevelop on 25/08/2015.
 */
public class ShockwavePurple extends Shockwave {

    public ShockwavePurple(GeneralScreen screen, float x, float y) {
        super(screen, x, y);
        setColor(ColorGame.PURPLE);
    }
}
