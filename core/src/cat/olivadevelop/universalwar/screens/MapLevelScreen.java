package cat.olivadevelop.universalwar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.async.AsyncTask;

import java.sql.ResultSet;
import java.sql.SQLException;

import cat.olivadevelop.universalwar.UniversalWarGame;
import cat.olivadevelop.universalwar.actors.ui.Planet;
import cat.olivadevelop.universalwar.screens.history.LevelManager;
import cat.olivadevelop.universalwar.screens.history.PreferenceStory;
import cat.olivadevelop.universalwar.tools.AsyncGame;
import cat.olivadevelop.universalwar.tools.ButtonGame;
import cat.olivadevelop.universalwar.tools.ConnectDB;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;
import cat.olivadevelop.universalwar.tools.ImageGame;
import cat.olivadevelop.universalwar.tools.LabelGame;
import cat.olivadevelop.universalwar.tools.Listener;
import cat.olivadevelop.universalwar.tools.TextFieldGame;

import static cat.olivadevelop.universalwar.screens.history.PreferenceStory.getWorld;
import static cat.olivadevelop.universalwar.tools.GameLogic.encrypt;
import static cat.olivadevelop.universalwar.tools.GameLogic.getBotonMenu2Drawable;
import static cat.olivadevelop.universalwar.tools.GameLogic.getEnviromentQuiet;
import static cat.olivadevelop.universalwar.tools.GameLogic.getNumberFormated;
import static cat.olivadevelop.universalwar.tools.GameLogic.getPlanets;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScreenHeight;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScreenWidth;
import static cat.olivadevelop.universalwar.tools.GameLogic.getSkin;
import static cat.olivadevelop.universalwar.tools.GameLogic.getSkin_mini;
import static cat.olivadevelop.universalwar.tools.GameLogic.getString;
import static cat.olivadevelop.universalwar.tools.GameLogic.getUi;
import static cat.olivadevelop.universalwar.tools.GameLogic.getUserID;
import static cat.olivadevelop.universalwar.tools.GameLogic.setUserID;
import static cat.olivadevelop.universalwar.tools.GameLogic.setUserLast;
import static cat.olivadevelop.universalwar.tools.GameLogic.setUserMail;
import static cat.olivadevelop.universalwar.tools.GameLogic.setUserName;

/**
 * Created by onion on 25/02/2016.
 */
public class MapLevelScreen extends GeneralScreen {
    private PreferenceStory levelProperties;
    private ResultSet current_level;
    private ResultSet user_points;
    private Dialog dialog;
    private ImageGame ipauseBG;
    private Table tSup;
    private Table tCentral;
    private Table tBottm;
    private TextFieldGame tfMail;
    private TextFieldGame tfPass;

    public MapLevelScreen(UniversalWarGame game) {
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
        setDialog(getString("lLoad"));
        showDialog();
        setHudMenu();
        AsyncGame async = new AsyncGame(this);
        async.submit(new AsyncTask<Object>() {
            @Override
            public Object call() throws Exception {
                if (getUserID() > 0) {
                    ConnectDB conn = new ConnectDB();
                    user_points = conn.query("SELECT SUM(total_points) FROM uw_levels_customer WHERE state != 0 AND id_customer = " + getUserID());
                    current_level = conn.query("SELECT * FROM uw_levels_customer WHERE state = 0 AND id_customer = " + getUserID());
                    try {
                        if (current_level.next()) {
                            Gdx.app.log("USER WORLD", "" + current_level.getInt("world"));
                            Gdx.app.log("USER LEVEL", "" + current_level.getInt("level"));
                            LevelManager.WORLD = current_level.getInt("world");
                            levelProperties = new PreferenceStory(GameLogic.getLevelManager().getCurrentLevel(current_level.getInt("level")));
                            Gdx.app.log("WORLD ID", "" + getWorld().get(LevelManager.WORLD - 1).getInt("world_id"));
                            hideDialog();
                            int p;
                            if (user_points.next()) {
                                p = user_points.getInt(1);
                            } else {
                                p = 0;
                            }
                            showHudMenu(p, getWorld().get(LevelManager.WORLD - 1).getInt("world_id"), current_level.getInt("level"));
                        } else {
                            setDialog(getString("lFailLoad"));
                            // cambiamos texto del dialog y mostramos boton de volver al inicio
                            ButtonGame back = new ButtonGame(getString("windowTbBack"), .5f);
                            ButtonGame retry = new ButtonGame(getString("GO_btTryAgain"), .5f);
                            retry.addListener(new Listener() {
                                @Override
                                public void action() {
                                    getGame().setScreen(getGame()._mapLevelScreen);
                                }
                            });
                            back.addListener(new Listener() {
                                @Override
                                public void action() {
                                    getGame().setScreen(getGame()._mainMenuScreen);
                                }
                            });
                            retry.setX(280);
                            back.setX(15);
                            back.setY(15);
                            retry.setY(15);

                            dialog.addActor(retry);
                            dialog.addActor(back);
                            dialog.setHeight(220);
                            dialog.setWidth(600);
                            dialog.setX(getScreenWidth() / 2 - dialog.getWidth() / 2);
                            dialog.setY(getScreenHeight() / 2 - dialog.getHeight() / 2);
                            showDialog();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    hideDialog();
                    setDialog(getString("lFirstLogin"));
                    tfMail = new TextFieldGame(getString("lMail"));
                    tfMail.setMaxLength(125);

                    tfPass = new TextFieldGame(getString("lPass"));
                    tfPass.setPasswordMode(true);
                    tfPass.setPasswordCharacter('*');

                    ButtonGame btnSignIn = new ButtonGame(getString("btnSign"), .5f);
                    btnSignIn.setWidth(700);
                    btnSignIn.addListener(new Listener() {
                        @Override
                        public void action() {
                            if (!tfMail.getText().equals("") && !tfPass.getText().equals("")) {
                                //SettingsScreen.backScreen = getGame()._mapLevelScreen;
                                signIn();
                            }
                        }
                    });

                    Table tLogin = new Table(getSkin());
                    tLogin.setWidth(getScreenWidth());
                    tLogin.setHeight(450);
                    tLogin.setY(getScreenHeight() / 2 - tLogin.getHeight() / 2);
                    tLogin.setBackground(getBotonMenu2Drawable());
                    tLogin.row().expandX().pad(10);
                    tLogin.add(new LabelGame(getString("btnSign"), .5f)).center();
                    tLogin.row().height(80).width(600);
                    tLogin.add(tfMail).expand();
                    tLogin.row().height(80).width(600);
                    tLogin.add(tfPass).expand();
                    tLogin.row().height(80).expandX().padBottom(50);
                    tLogin.add(btnSignIn).center().padRight(31);
                    getStage().addActor(tLogin);
                }
                return true;
            }
        });
    }

    public void setHudMenu() {
        tSup = new Table();
        tSup.setBackground(getBotonMenu2Drawable());
        tSup.setWidth(getScreenWidth());
        tSup.setHeight(80);
        tSup.setY(getScreenHeight());

        tBottm = new Table();
        tBottm.setBackground(getBotonMenu2Drawable());
        tBottm.setWidth(getScreenWidth());
        tBottm.setHeight(420);
        tBottm.setY(-tBottm.getHeight());

        tCentral = new Table();
        tCentral.setHeight(getScreenHeight() - tBottm.getHeight() - tSup.getHeight());
        tCentral.setWidth(getScreenWidth());
        tCentral.setY(tBottm.getHeight());
        tCentral.setX(-getScreenWidth());
        /*cloudy2 -> 1
        cloudy5 -> 4
        cloudy8 -> 7
        cloudy12 -> 11: level 1
        cloudy15 -> 14: level 2
        leaky1 -> 16
        rings5 -> 24
        rings4 -> 23
        rock1 -> 33
        rock2 -> 34*/

        getStage().addActor(tSup);
        getStage().addActor(tCentral);
        getStage().addActor(tBottm);
    }

    public Table[] generateTables(int userlvl) {
        JsonValue totl_lvls = LevelManager.getArr_levels();
        JsonValue curr_lvl;
        Table[] tables = new Table[6];
        for (int x = 0; x < totl_lvls.size; x++) {
            curr_lvl = totl_lvls.get(x);
            String str = (curr_lvl.getInt("id_level") < userlvl) ? "tick" : (curr_lvl.getInt("id_level") == userlvl) ? "lock2" : "lock";
            String bg = (curr_lvl.getInt("id_level") < userlvl) ? "bg_bar_green" : (curr_lvl.getInt("id_level") == userlvl) ? "bg_bar_purple" : "bg_bar_marine";
            ImageGame i = new ImageGame(getUi(str));
            Table t1 = new Table();
            t1.background(new TextureRegionDrawable(new TextureRegion(getUi(bg))));
            t1.row().padTop(20);
            t1.add(i).padBottom(-20);
            t1.row();
            t1.add(new LabelGame(getNumberFormated(curr_lvl.getInt("reward_points")), .3f)).pad(10).padBottom(-10);
            if (curr_lvl.getInt("id_level") == userlvl) {
                t1.addListener(new Listener() {
                    @Override
                    public void action() {
                        super.action();
                        getGame().setScreen(getGame()._levelScreen);
                    }
                });
            }
            tables[x] = t1;
        }
        return tables;
    }

    public void showHudMenu(int totalpoints, int world_id, int lvl_user) throws SQLException {
        tSup.addAction(Actions.moveTo(0, getScreenHeight() - 73, 1f, Interpolation.elasticOut));
        tBottm.addAction(Actions.moveTo(0, -10, 1f, Interpolation.elasticOut));
        tCentral.addAction(Actions.moveTo(0, tCentral.getY(), 1f, Interpolation.elasticOut));

        ImageGame back = new ImageGame(getUi("back"));
        back.addListener(new Listener() {
            @Override
            public void action() {
                getGame().setScreen(getGame()._mainMenuScreen);
            }
        });

        tSup.add(back).width(55).pad(10).padBottom(10).padTop(20);
        tSup.add(new LabelGame("Mundo: " + current_level.getInt("level"), .4f).center()).width((getScreenWidth() / 2) - 200);
        tSup.add(new LabelGame(GameLogic.getNumberFormated(totalpoints) + " puntos", .4f).center()).width((getScreenWidth() / 2) + 100);

        ImageGame i = new ImageGame(getPlanets(Planet.planets[world_id]));
        int pad = 0;
        float scale = 1;
        if (i.getWidth() > 700) {
            pad = 20;
            scale = .8f;
        }
        i.addAction(
                Actions.forever(
                        Actions.sequence(
                                Actions.scaleTo(scale, scale, 1f),
                                Actions.scaleBy(.1f, .1f, .4f)
                        )
                )
        );
        tCentral.add(i).pad(pad);

        Table[] tables = generateTables(lvl_user);

        Table tIntern = new Table();
        //tIntern.background(new TextureRegionDrawable(new TextureRegion(getUi("black"))));
        tIntern.setWidth(tBottm.getWidth());
        tIntern.setHeight(tBottm.getHeight() - 20);
        tIntern.padTop(10);
        tIntern.add(tables[0]).width(tBottm.getWidth() / 3.7f).padLeft(5).padRight(5);
        tIntern.add(tables[1]).width(tBottm.getWidth() / 3.7f).padLeft(5).padRight(5);
        tIntern.add(tables[2]).width(tBottm.getWidth() / 3.7f).padLeft(5).padRight(5);
        tIntern.row().padTop(10).padBottom(10);
        tIntern.add(tables[3]).width(tBottm.getWidth() / 3.7f).padLeft(5).padRight(5);
        tIntern.add(tables[4]).width(tBottm.getWidth() / 3.7f).padLeft(5).padRight(5);
        tIntern.add(tables[5]).width(tBottm.getWidth() / 3.7f).padLeft(5).padRight(5);

        tBottm.add(tIntern);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        MainMenuScreen.checkAudio();
        if (!GameLogic.isAudioOn()) {
            getEnviromentQuiet().pause();
        } else {
            if (!getEnviromentQuiet().isPlaying()) {
                getEnviromentQuiet().play();
            }
        }
    }

    public void setDialog(String str) {
        dialog = new Dialog("", getSkin_mini());
        dialog.setBackground(new NinePatchDrawable(new NinePatch(getUi("bg_bar_blue"), 9, 9, 9, 9)));
        dialog.text(str);
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

    public void signIn() {
        setDialog(getString("lSignIn"));
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
                    ResultSet query = conn.query("SELECT id_customer, firstname, lastname, email, passwd FROM ps_customer WHERE email LIKE '" + mail + "'");
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
                                    getGame().setScreen(getGame()._mapLevelScreen);
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

}
