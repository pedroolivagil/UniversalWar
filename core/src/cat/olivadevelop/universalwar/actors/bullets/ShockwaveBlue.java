package cat.olivadevelop.universalwar.actors.bullets;

import com.badlogic.gdx.scenes.scene2d.Group;

import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by OlivaDevelop on 25/08/2015.
 */
public class ShockwaveBlue extends Shockwave {

    public ShockwaveBlue(GeneralScreen screen, Group[] enemy, float x, float y) {
        super(screen, enemy, x, y, .5f);
        setColor(ColorGame.BLUE_CYAN);
        setSpeed(3);
    }
}
