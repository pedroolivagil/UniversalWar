package cat.olivadevelop.universalwar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.async.AsyncTask;

import java.sql.ResultSet;
import java.sql.SQLException;

import cat.olivadevelop.universalwar.UniversalWarGame;
import cat.olivadevelop.universalwar.tools.AsyncGame;
import cat.olivadevelop.universalwar.tools.ButtonGame;
import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.ConnectDB;
import cat.olivadevelop.universalwar.tools.GeneralScreen;
import cat.olivadevelop.universalwar.tools.ImageGame;
import cat.olivadevelop.universalwar.tools.LabelGame;
import cat.olivadevelop.universalwar.tools.Listener;
import cat.olivadevelop.universalwar.tools.TextFieldGame;

import static cat.olivadevelop.universalwar.tools.GameLogic.encrypt;
import static cat.olivadevelop.universalwar.tools.GameLogic.getBotonMenu2Drawable;
import static cat.olivadevelop.universalwar.tools.GameLogic.getPrefs;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScreenHeight;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScreenWidth;
import static cat.olivadevelop.universalwar.tools.GameLogic.getSkin;
import static cat.olivadevelop.universalwar.tools.GameLogic.getSkin_mini;
import static cat.olivadevelop.universalwar.tools.GameLogic.getString;
import static cat.olivadevelop.universalwar.tools.GameLogic.getUi;
import static cat.olivadevelop.universalwar.tools.GameLogic.getUserLast;
import static cat.olivadevelop.universalwar.tools.GameLogic.getUserName;
import static cat.olivadevelop.universalwar.tools.GameLogic.setUserID;
import static cat.olivadevelop.universalwar.tools.GameLogic.setUserLast;
import static cat.olivadevelop.universalwar.tools.GameLogic.setUserMail;
import static cat.olivadevelop.universalwar.tools.GameLogic.setUserName;

/**
 * Created by OlivaDevelop on 25/08/2015.
 */
public class SettingsScreen extends GeneralScreen {

    Table tableContent;
    private TextFieldGame tfPass;
    private TextFieldGame tfMail;
    private ButtonGame btnSignIn;
    private ButtonGame btnSignOut;
    private ButtonGame tbSignUp;
    private ResultSet query;
    private Dialog dialog;
    private ImageGame ipauseBG;

    public SettingsScreen(UniversalWarGame game) {
        super(game);
    }

    @Override
    public void actionBackButton() {
        super.actionBackButton();
        getGame().setScreen(getGame()._mainMenuScreen);
    }

    @Override
    public void show() {
        super.show();
        setDialog();
        tbBack = new ButtonGame(getString("windowTbBack"), .5f);
        tbBack.addListener(new Listener() {
            @Override
            public void action() {
                getGame().setScreen(getGame()._mainMenuScreen);
            }
        });
        tbBack.setWidth(700);
        tbSignUp = new ButtonGame(getString("btnSignUp"), .5f);
        tbSignUp.addListener(new Listener() {
            @Override
            public void action() {
                //getGame().setScreen(getGame()._signUpScreen);
                Gdx.net.openURI("http://codeduo.cat/index.php?controller=authentication");
            }
        });
        tbSignUp.setWidth(700);

        LabelGame title = new LabelGame(getString("lOptions").toUpperCase(), 1, ColorGame.RED);

        tfMail = new TextFieldGame(getString("lMail"));
        tfMail.setMaxLength(125);

        tfPass = new TextFieldGame(getString("lPass"));
        tfPass.setPasswordMode(true);
        tfPass.setPasswordCharacter('*');

        btnSignIn = new ButtonGame(getString("btnSign"), .5f);
        btnSignIn.setWidth(700);
        btnSignIn.addListener(new Listener() {
            @Override
            public void action() {
                signIn();
            }
        });
        btnSignOut = new ButtonGame(getString("btnSignOut"), .5f);
        btnSignOut.setWidth(700);
        btnSignOut.addListener(new Listener() {
            @Override
            public void action() {
                signOut();
            }
        });

        Gdx.app.log("ID_USER", "" + getPrefs().getInteger("userID"));
        if (getPrefs().getInteger("userID") > 0) {
            setTabSignInTrue();
        } else {
            setTabSign();
        }

        Table tableSettings = new Table(getSkin());
        tableSettings.setFillParent(true);
        tableSettings.add(title);
        tableSettings.row().height(20);
        tableSettings.add();
        tableSettings.row().expandX();
        tableSettings.add(tableContent).expand().fillX().padTop(70);
        tableSettings.row().height(60).padTop(30);
        tableSettings.add(new LabelGame(getString("lNewTitle"), .5f)).center();
        tableSettings.row().height(110);
        tableSettings.add(new LabelGame(getString("lNewAccount"), .3f)).center();
        tableSettings.row().expandX().padTop(10);
        tableSettings.add(tbSignUp).height(tbSignUp.getHeight() * tbSignUp.getScale()).padRight(31);
        tableSettings.row().height(50).expand();
        tableSettings.add(tbBack).height(tbBack.getHeight() * tbBack.getScale()).padRight(31);
        getStage().addActor(tableSettings);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ipauseBG.toFront();
        dialog.toFront();
    }

    public void setDialog() {
        dialog = new Dialog("", getSkin_mini());
        dialog.setBackground(new NinePatchDrawable(new NinePatch(getUi("bg_bar_blue"), 9, 9, 9, 9)));
        dialog.text(getString("lSignIn"));
        ipauseBG = new ImageGame(getUi("black"), 0, 0, getScreenWidth(), getScreenHeight());
        getStage().addActor(ipauseBG);
        hideDialog();
    }

    public void showDialog() {
        ipauseBG.setVisible(true);
        dialog.show(getStage());
    }

    public void hideDialog() {
        ipauseBG.setVisible(false);
        dialog.hide();
    }

    private void setTabSign() {
        tableContent = new Table(getSkin());
        tableContent.setBackground(getBotonMenu2Drawable());
        tableContent.row().expandX().pad(10);
        tableContent.add(new LabelGame(getString("btnSign"), .5f)).center();
        tableContent.row().height(80).width(600);
        tableContent.add(tfMail).expand();
        tableContent.row().height(80).width(600);
        tableContent.add(tfPass).expand();
        tableContent.row().height(80).expandX().padBottom(50);
        tableContent.add(btnSignIn).center().padRight(31);
    }

    private void setTabSignInTrue() {
        tableContent = new Table(getSkin());
        tableContent.setBackground(getBotonMenu2Drawable());
        tableContent.row().expandX().pad(10);
        tableContent.add(new LabelGame(getString("btnSign"), .5f)).center();
        tableContent.row().height(80).width(600);
        tableContent.add(new LabelGame(getString("welcome") + ", " + getUserName() + " " + getUserLast(), .5f, ColorGame.ORANGE)).expand().center().padRight(10);
        tableContent.row().height(80).expandX().padBottom(50);
        tableContent.add(btnSignOut).center().padRight(20);
    }

    private void signIn() {
        showDialog();
        AsyncGame async = new AsyncGame(this);
        async.submit(new AsyncTask<Object>() {
            @Override
            public Object call() throws Exception {
                String mail = tfMail.getText();
                String pass = encrypt(tfPass.getText());
                String bd_pass;
                try {
                    ConnectDB conn = new ConnectDB();
                    query = conn.query("SELECT id_customer, firstname, lastname, email, passwd FROM ps_customer WHERE email LIKE '" + mail + "'");
                    if (query.next()) {
                        bd_pass = query.getString("passwd");
                        if (pass.equals(bd_pass)) {
                            setUserID(query.getInt("id_customer"));
                            setUserName(query.getString("firstname"));
                            setUserLast(query.getString("lastname"));
                            setUserMail(query.getString("email"));
                            Gdx.app.log("SignIn", "TRUE");
                            Timer t = new Timer();
                            t.scheduleTask(new Timer.Task() {
                                @Override
                                public void run() {
                                    hideDialog();
                                    getGame().setScreen(getGame()._settingsScreen);
                                }
                            }, 2, 0, 0);
                            t.start();
                        } else {
                            Gdx.app.log("SignIn", "FALSE");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    public void signOut() {
        setUserID(-1);
        getGame().setScreen(getGame()._settingsScreen);
    }
}
