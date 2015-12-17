package cat.olivadevelop.universalwar.actors.shields;

import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by Oliva on 15/04/2015.
 */
public class ShieldBronze extends Shield {

    public ShieldBronze(GeneralScreen screen) {
        //  Parametros: Tiempo del escudo
        super(screen, GameLogic.getUi("shield1"));
        setImpacts(10);
        setColor(ColorGame.RED);
    }
}
