package cat.olivadevelop.universalwar.actors.drops;

import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by Oliva on 05/05/2015.
 */
public class PointsUpGold extends Drops {

    public PointsUpGold(GeneralScreen screen, float x, float y) {
        super(screen, GameLogic.getPowers("star_gold"), x, y, 150, false);
        setRotation(getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void actionsObject() {
        super.actionsObject();
        GameLogic.addToScore(2000);
    }
}
