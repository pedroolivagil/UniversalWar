package cat.olivadevelop.universalwar.tools;

import com.badlogic.gdx.scenes.scene2d.ui.Window;

/**
 * Created by OlivaDevelop on 24/05/2015.
 */
public class WindowGame extends Window {

    GeneralScreen screen;

    public WindowGame(GeneralScreen screen, String title) {
        super(title, GameLogic.getSkin());
        this.screen = screen;
        config();
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);
        setPosition(GameLogic.getScreenWidth() / 2 - getWidth() / 2, GameLogic.getScreenHeight() / 2 - getHeight() / 2);
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        setPosition(GameLogic.getScreenWidth() / 2 - getWidth() / 2, GameLogic.getScreenHeight() / 2 - getHeight() / 2);
    }

    private void config() {
        setWidth(660);
        setHeight(500);
        setOrigin(getWidth() / 2, getHeight() / 2);
        setPosition(GameLogic.getScreenWidth() / 2 - getWidth() / 2, GameLogic.getScreenHeight() / 2 - getHeight() / 2);
        setVisible(false);
        setResizable(false);
        setMovable(false);
    }
}
