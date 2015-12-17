package cat.olivadevelop.universalwar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.sql.ResultSet;
import java.sql.SQLException;

import cat.olivadevelop.universalwar.UniversalWarGame;
import cat.olivadevelop.universalwar.tools.ButtonGame;
import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.ConnectDB;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;
import cat.olivadevelop.universalwar.tools.LabelGame;
import cat.olivadevelop.universalwar.tools.Listener;
import cat.olivadevelop.universalwar.tools.SelectBoxGame;
import cat.olivadevelop.universalwar.tools.TextFieldGame;

import static cat.olivadevelop.universalwar.tools.GameLogic.getBotonMenu2Drawable;
import static cat.olivadevelop.universalwar.tools.GameLogic.getPrefs;
import static cat.olivadevelop.universalwar.tools.GameLogic.getSkin;
import static cat.olivadevelop.universalwar.tools.GameLogic.getSkin_mini;
import static cat.olivadevelop.universalwar.tools.GameLogic.getString;

/**
 * Created by OlivaDevelop on 25/08/2015.
 */
public class SettingsScreen extends GeneralScreen {

    Table tableContent;
    private TextFieldGame tfPass;
    private TextFieldGame tfMail;
    private ButtonGame btnSignIn;
    private ButtonGame tbSignUp;

    public SettingsScreen(UniversalWarGame game) {
        super(game);
    }

    @Override
    public void actionBackButton() {
        super.actionBackButton();
        game.setScreen(game._mainMenuScreen);
    }

    @Override
    public void show() {
        super.show();
        tbBack = new ButtonGame(getString("windowTbBack"), .5f);
        tbBack.addListener(new Listener() {
            @Override
            public void action() {
                game.setScreen(game._mainMenuScreen);
            }
        });
        tbBack.setWidth(700);
        tbSignUp = new ButtonGame(getString("btnSignUp"), .5f);
        tbSignUp.addListener(new Listener() {
            @Override
            public void action() {
                game.setScreen(game._signUpScreen);
            }
        });
        tbSignUp.setWidth(700);

        LabelGame title = new LabelGame(getString("lOptions").toUpperCase(), 1, ColorGame.ORANGE);

        tfMail = new TextFieldGame(getString("lMail"));
        tfMail.setMaxLength(125);

        tfPass = new TextFieldGame(getString("lPass"));
        tfPass.setPasswordMode(true);
        tfPass.setPasswordCharacter('*');

        btnSignIn = new ButtonGame(getString("btnSign"), .5f);
        btnSignIn.setWidth(700);

        tableContent = new Table(getSkin());
        tableContent.setBackground(getBotonMenu2Drawable());
        tableContent.row().expandX().pad(10);
        tableContent.add(new LabelGame(getString("btnSign"), .5f)).center();
        tableContent.row().height(80).width(600);
        tableContent.add(tfMail).expand();
        tableContent.row().height(80).width(600);
        tableContent.add(tfPass).expand();
        tableContent.row().height(80).expandX().padBottom(50);
        tableContent.add(btnSignIn).center();

        LabelGame lTheme = new LabelGame(getString("lTheme"), .5f);
        lTheme.setWidth(20);

        /** añadir lack and white solo si se ha comprado la app **/
        String[] items = new String[]{"  Default"/*, "  Black & White"*/};
        final SelectBoxGame select = new SelectBoxGame(getSkin_mini());
        select.setItems(items);
        select.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("Selected", select.getSelected());
                if (select.getSelected().trim().equals("Default")) {
                    GameLogic.setTheme("basic");
                } else {
                    GameLogic.setTheme("blackandwhite");
                }
            }
        });

        Table tableSettings = new Table(getSkin());
        tableSettings.setFillParent(true);
        tableSettings.add(title).colspan(2);
        tableSettings.row().height(100);
        tableSettings.add();
        tableSettings.row().height(80);
        tableSettings.add(lTheme).width(200).right();
        tableSettings.add(select).width(400).left();
        tableSettings.row().expandX();
        tableSettings.add(tableContent).colspan(2).fillX().top().padTop(70);
        tableSettings.row().height(60).padTop(30);
        tableSettings.add(new LabelGame(getString("lNewTitle"), .5f)).colspan(2).center();
        tableSettings.row().height(80);
        tableSettings.add(new LabelGame(getString("lNewAccount"), .3f, ColorGame.DARK_BLUE)).colspan(2).center();
        tableSettings.row().padBottom(50).expandX();
        tableSettings.add(tbSignUp).colspan(2).height(tbSignUp.getHeight() * tbSignUp.getScale());
        tableSettings.row().padBottom(50).expand();
        tableSettings.add(tbBack).colspan(2).height(tbBack.getHeight() * tbBack.getScale());

        _stage.addActor(tableSettings);
    }

    private void reset() {
        Dialog diag = new Dialog("", getSkin()) {
            protected void result(Object object) {
                if (object.equals(true)) {
                    getPrefs().remove("user");
                    getPrefs().remove("localReg");
                    getPrefs().flush();
                    game.setScreen(game._settingsScreen);
                }
            }
        };
        diag.pad(20);
        diag.setResizable(false);
        diag.setMovable(false);
        diag.button(getString("lbuttonYes"), true);
        diag.button(getString("lbuttonNo"), false);
        diag.show(_stage);
    }

    private void signUp() {
        if (!tfPass.getText().equals("")) {
            Dialog diag = new Dialog("", getSkin()) {
                protected void result(Object object) {
                    System.out.println("Chosen: " + object);
                }
            };
            diag.pad(20);
            diag.setResizable(false);
            diag.setMovable(false);
            diag.button("OK");
            ConnectDB conn = new ConnectDB();
            ResultSet userConfig = conn.query("SELECT registered FROM registros WHERE email LIKE '" + tfPass.getText() + "';");
            try {
                if (userConfig.next()) {
                    if (userConfig.getBoolean("registered")) {
                        getPrefs().putString("user", tfPass.getText());
                        getPrefs().putBoolean("localReg", false);
                        getPrefs().flush();
                        diag.text(new LabelGame(getString("okActivate"), .4f));
                        diag.show(_stage);
                    } else {
                        diag.text(new LabelGame(getString("failActivate"), .4f));
                        diag.show(_stage);
                    }
                } else {
                    diag.text(new LabelGame(getString("errorActivate"), .4f));
                    diag.show(_stage);
                }
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            tableContent.row();
            tableContent.add(new LabelGame(getString("fieldRequired"), .7f, ColorGame.RED));
        }
        Gdx.app.log("ADS", "" + getPrefs().getBoolean("localReg"));
        Gdx.app.log("User", "" + getPrefs().getString("user", ""));
        Gdx.app.log("UserName", "" + getPrefs().getString("tfMail", ""));
    }

    @Override
    public void dispose() {
        super.dispose();
        game._settingsScreen.dispose();
    }
}
