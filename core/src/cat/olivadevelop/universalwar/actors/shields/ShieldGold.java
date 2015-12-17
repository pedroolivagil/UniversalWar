package cat.olivadevelop.universalwar.actors.shields;

import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by Oliva on 15/04/2015.
 */
public class ShieldGold extends Shield {

    public ShieldGold(GeneralScreen screen) {
        //  Parametros: Tiempo del escudo
        super(screen, GameLogic.getUi("shield3"));
        setImpacts(60);
        setColor(ColorGame.DARK_PINK);
    }
}
