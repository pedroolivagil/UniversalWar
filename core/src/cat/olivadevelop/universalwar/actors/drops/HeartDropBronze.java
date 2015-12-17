package cat.olivadevelop.universalwar.actors.drops;

import cat.olivadevelop.universalwar.actors.allied.Allied;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by Oliva on 05/05/2015.
 */
public class HeartDropBronze extends Drops {

    public HeartDropBronze(GeneralScreen screen, float x, float y) {
        super(screen, GameLogic.getPowers("bolt_bronze"), x, y, 150, false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void actionsObject() {
        super.actionsObject();
        GameLogic.addToScore(100);
        if (Allied.getHealth() < Allied.getMaxHealth()) {
            Allied.addLiveGame(1);
        }
    }
}
