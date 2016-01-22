package cat.olivadevelop.universalwar.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import cat.olivadevelop.universalwar.UniversalWarGame;
import cat.olivadevelop.universalwar.tools.ButtonGame;
import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.GeneralScreen;
import cat.olivadevelop.universalwar.tools.LabelGame;
import cat.olivadevelop.universalwar.tools.Listener;

import static cat.olivadevelop.universalwar.tools.GameLogic.encrypt;
import static cat.olivadevelop.universalwar.tools.GameLogic.getSkin;
import static cat.olivadevelop.universalwar.tools.GameLogic.getString;

/**
 * Created by Oliva on 14/12/2015.
 */
public class SignUpScreen extends GeneralScreen {

    public SignUpScreen(UniversalWarGame game) {
        super(game);
    }

    @Override
    public void actionBackButton() {
        super.actionBackButton();
        getGame().setScreen(getGame()._settingsScreen);
    }

    @Override
    public void show() {
        super.show();
        tbBack = new ButtonGame(getString("windowTbBack"), .5f);
        tbBack.addListener(new Listener() {
            @Override
            public void action() {
                actionBackButton();
            }
        });
        tbBack.setWidth(700);
        LabelGame title = new LabelGame(getString("lOptions").toUpperCase(), 1, ColorGame.GREEN_POINTS);
        Table tableSettings = new Table(getSkin());
        tableSettings.setFillParent(true);
        tableSettings.add(title);
        tableSettings.row();
        tableSettings.add(new LabelGame(encrypt("20081991"), .3f));
        tableSettings.row().padBottom(50).expand();
        tableSettings.add(tbBack).height(tbBack.getHeight() * tbBack.getScale());

        getStage().addActor(tableSettings);
    }
}
