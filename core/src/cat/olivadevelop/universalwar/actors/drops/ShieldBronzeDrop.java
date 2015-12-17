package cat.olivadevelop.universalwar.actors.drops;

import cat.olivadevelop.universalwar.actors.shields.Shield;
import cat.olivadevelop.universalwar.actors.shields.ShieldBronze;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by Oliva on 05/05/2015.
 */
public class ShieldBronzeDrop extends Drops {

    public ShieldBronzeDrop(GeneralScreen screen, float x, float y) {
        super(screen, GameLogic.getPowers("shield_bronze"), x, y, 150, false);
    }

    @Override
    public void actionsObject() {
        super.actionsObject();
        GameLogic.addToScore(50);
        if (!screen._groupShields.hasChildren()) {
            screen._groupShields.addActor(new ShieldBronze(screen));
        } else {
            Shield.addImpacts(2);
        }
    }
}
