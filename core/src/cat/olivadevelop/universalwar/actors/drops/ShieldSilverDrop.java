package cat.olivadevelop.universalwar.actors.drops;

import cat.olivadevelop.universalwar.actors.shields.Shield;
import cat.olivadevelop.universalwar.actors.shields.ShieldSilver;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by Oliva on 05/05/2015.
 */
public class ShieldSilverDrop extends Drops {

    public ShieldSilverDrop(GeneralScreen screen, float x, float y) {
        super(screen, GameLogic.getPowers("shield_silver"), x, y, 150, false);
    }

    @Override
    public void actionsObject() {
        super.actionsObject();
        GameLogic.addToScore(250);
        if (!screen._groupShields.hasChildren()) {
            screen._groupShields.addActor(new ShieldSilver(screen));
        } else {
            Shield.addImpacts(5);
        }
    }
}
