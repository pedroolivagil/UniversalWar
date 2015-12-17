package cat.olivadevelop.universalwar.actors.drops;

import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

public class PointsUpSilver extends Drops {

    public PointsUpSilver(GeneralScreen screen, float x, float y) {
        super(screen, GameLogic.getPowers("star_silver"), x, y, 150, false);
        setRotation(getRotation());
        setScale(.8f);
    }

    @Override
    public void actionsObject() {
        super.actionsObject();
        GameLogic.addToScore(1000);
    }
}
