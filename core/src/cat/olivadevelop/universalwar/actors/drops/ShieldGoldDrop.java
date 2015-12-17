package cat.olivadevelop.universalwar.actors.drops;

import cat.olivadevelop.universalwar.actors.shields.Shield;
import cat.olivadevelop.universalwar.actors.shields.ShieldGold;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by Oliva on 05/05/2015.
 */
public class ShieldGoldDrop extends Drops {

    public ShieldGoldDrop(GeneralScreen screen, float x, float y) {
        super(screen, GameLogic.getPowers("shield_gold"), x, y, 150, false);
    }

    @Override
    public void actionsObject() {
        super.actionsObject();
        GameLogic.addToScore(500);
        if (!screen._groupShields.hasChildren()) {
            screen._groupShields.addActor(new ShieldGold(screen));
        } else {
            Shield.reset();
        }
    }
}
