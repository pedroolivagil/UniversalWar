package cat.olivadevelop.universalwar.actors.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import cat.olivadevelop.universalwar.screens.GameHistoryScreen;
import cat.olivadevelop.universalwar.tools.GeneralScreen;
import cat.olivadevelop.universalwar.tools.LabelGame;

import static cat.olivadevelop.universalwar.tools.GameLogic.getBotonMenu2Drawable;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScreenWidth;
import static cat.olivadevelop.universalwar.tools.GameLogic.getSkin;

/**
 * Created by Oliva on 10/01/2016.
 */
public class HUDHistory extends HUD {
    private Table tBottom;
    private GameHistoryScreen scn;

    public HUDHistory(GeneralScreen screen) {
        super(screen);
        this.scn = (GameHistoryScreen) screen;
        settBottom();
    }

    public void settBottom() {
        this.tBottom = new Table(getSkin());
        this.tBottom.background(getBotonMenu2Drawable());
        this.tBottom.setHeight(140);
        this.tBottom.setWidth(getScreenWidth());
        this.tBottom.setY(-7);
        this.tBottom.add(new LabelGame("Hola", .7f)).expandX();
        screen.getStage().addActor(this.tBottom);
    }
}
