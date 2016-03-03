package cat.olivadevelop.universalwar.screens.history;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.async.AsyncTask;

import java.sql.ResultSet;
import java.sql.SQLException;

import cat.olivadevelop.universalwar.UniversalWarGame;
import cat.olivadevelop.universalwar.actors.allied.Allied;
import cat.olivadevelop.universalwar.actors.allied.Hurricane;
import cat.olivadevelop.universalwar.actors.enemies.AdvancedEnemy;
import cat.olivadevelop.universalwar.actors.enemies.BasicEnemy;
import cat.olivadevelop.universalwar.actors.enemies.Enemy;
import cat.olivadevelop.universalwar.actors.shields.ShieldBronze;
import cat.olivadevelop.universalwar.actors.shields.ShieldGold;
import cat.olivadevelop.universalwar.actors.shields.ShieldSilver;
import cat.olivadevelop.universalwar.actors.ui.HUDHistory;
import cat.olivadevelop.universalwar.actors.ui.Planet;
import cat.olivadevelop.universalwar.screens.MainMenuScreen;
import cat.olivadevelop.universalwar.tools.AsyncGame;
import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.ConnectDB;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;
import cat.olivadevelop.universalwar.tools.ImageGame;
import cat.olivadevelop.universalwar.tools.LabelGame;

import static cat.olivadevelop.universalwar.tools.GameLogic.VOLUME_5;
import static cat.olivadevelop.universalwar.tools.GameLogic.getCountEnemiesDispached;
import static cat.olivadevelop.universalwar.tools.GameLogic.getEnviromentQuiet;
import static cat.olivadevelop.universalwar.tools.GameLogic.getLevelManager;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScoreGame;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScreenHeight;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScreenWidth;
import static cat.olivadevelop.universalwar.tools.GameLogic.getSkin_mini;
import static cat.olivadevelop.universalwar.tools.GameLogic.getSoundLevelUp;
import static cat.olivadevelop.universalwar.tools.GameLogic.getString;
import static cat.olivadevelop.universalwar.tools.GameLogic.getTimeGame;
import static cat.olivadevelop.universalwar.tools.GameLogic.getUi;
import static cat.olivadevelop.universalwar.tools.GameLogic.getUserID;
import static cat.olivadevelop.universalwar.tools.GameLogic.setPauseGame;
import static cat.olivadevelop.universalwar.tools.GameLogic.setScoreGame;
import static cat.olivadevelop.universalwar.tools.GameLogic.setTimeGame;

/**
 * Created by onion on 29/02/2016.
 */
public class LevelScreen extends GeneralScreen {
    private static Hurricane ship;
    private PreferenceStory storyPrefs;
    private ResultSet current_level;
    private Dialog dialog;
    private ImageGame ipauseBG;

    private boolean killEnemy;
    private boolean killBoss;
    private boolean killMBoss;
    private boolean killSBoss;
    private boolean survive;
    private boolean xtremsurvive;

    public LevelScreen(UniversalWarGame game) {
        super(game);
    }

    public static Hurricane getShip() {
        return ship;
    }

    @Override
    public void actionBackButton() {
        super.actionBackButton();
        _hudHistory.showWindowPause();
        if (GameLogic.isShowADS()) {
            getGame().actionResolver.showOrLoadInterstital();
        }
    }

    @Override
    public void show() {
        super.show();
        init();
        setDialog(getString("lLoadLevel"));
        showDialog();
        AsyncGame async = new AsyncGame(this);
        async.submit(new AsyncTask<Object>() {
            @Override
            public Object call() throws Exception {
                if (getUserID() > 0) {
                    ConnectDB conn = new ConnectDB();
                    current_level = conn.query("SELECT * FROM uw_levels_customer WHERE state = 0 AND id_customer = " + getUserID());
                    if (current_level.next()) {
                        Gdx.app.log("USER WORLD", "" + current_level.getInt("world"));
                        Gdx.app.log("USER LEVEL", "" + current_level.getInt("level"));
                        LevelManager.WORLD = current_level.getInt("world");
                        hideDialog();
                        content(current_level.getInt("level"));
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                _hudHistory.showWindowPause();
                            }
                        }, 1.1f);
                    }
                }
                return true;
            }
        });
    }

    private void setTarget() {
        JsonValue tmp;
        String key;
        for (int x = 0; x < storyPrefs.getTargets().size; x++) {
            tmp = storyPrefs.getTargets().get(x);
            key = tmp.name();
            if (key.equals(storyPrefs.KILLENEMY)) {
                killEnemy = false;
            }
            if (key.equals(storyPrefs.KILLBOSS)) {
                killBoss = false;
            }
            if (key.equals(storyPrefs.KILLMEGABOSS)) {
                killMBoss = false;
            }
            if (key.equals(storyPrefs.KILLSUPERBOSS)) {
                killSBoss = false;
            }
            if (key.equals(storyPrefs.SURVIVE)) {
                survive = false;
            }
            if (key.equals(storyPrefs.XTREMSURVIVE)) {
                xtremsurvive = false;
            }
        }
    }

    private void checkIfUserWin() {
        if (killEnemy && killMBoss && killSBoss && killBoss && survive && xtremsurvive) {
            /*creamos window de WIN y procedemos al regidtro en la base de datos*/
            Enemy.setCanShoot(false);
            if (GameLogic.isAudioOn()) {
                getEnviromentQuiet().pause();
                getSoundLevelUp().play(GameLogic.VOLUME_10);
            }
            try {
                int id_level = current_level.getInt("id_level_customer");
                int level = current_level.getInt("level");
                int world = current_level.getInt("world");
                ConnectDB conn = new ConnectDB();
                conn.insert("UPDATE uw_levels_customer SET state = 1, total_points = " + getScoreGame() + ", date_update = NOW(), time_game = " + getTimeGame() + "  WHERE id_level_customer = " + id_level);
                conn.insert("INSERT INTO uw_levels_customer(id_customer,level,world,state,total_points,date_update,time_game) VALUES(" + getUserID() + ", " + (level + 1) + ", " + world + ", 0, 0, NOW(),0)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    _hudHistory.showWindowWinner();
                }
            }, 1.5f);
        }
    }

    public void checkTargets() {
        int value;
        JsonValue tmp;
        String key;
        if (!killEnemy) {
            tmp = storyPrefs.getTargets().get(storyPrefs.KILLENEMY);
            value = storyPrefs.getData().get("main_target").getInt(tmp.name());
            if (value == getCountEnemiesDispached()) {
                killEnemy = true;
            }
        }
        if (!killBoss) {
            tmp = storyPrefs.getTargets().get(storyPrefs.KILLBOSS);
            value = storyPrefs.getData().get("main_target").getInt(tmp.name());
            /*if (value == getCountEnemiesDispached()) {
                killEnemy = true;
            }*/
        }
        if (!killMBoss) {
            tmp = storyPrefs.getTargets().get(storyPrefs.KILLMEGABOSS);
            value = storyPrefs.getData().get("main_target").getInt(tmp.name());
            /*if (value == getCountEnemiesDispached()) {
                killEnemy = true;
            }*/
        }
        if (!killSBoss) {
            tmp = storyPrefs.getTargets().get(storyPrefs.KILLSUPERBOSS);
            value = storyPrefs.getData().get("main_target").getInt(tmp.name());
            /*if (value == getCountEnemiesDispached()) {
                killEnemy = true;
            }*/
        }
        if (!survive) {
            tmp = storyPrefs.getTargets().get(storyPrefs.SURVIVE);
            value = storyPrefs.getData().get("main_target").getInt(tmp.name());
            if (value == getTimeGame()) {
                survive = true;
            }
        }
        if (!xtremsurvive) {
            tmp = storyPrefs.getTargets().get(storyPrefs.XTREMSURVIVE);
            value = storyPrefs.getData().get("main_target").getInt(tmp.name());
            if (value == getTimeGame()) {
                xtremsurvive = true;
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(26f / 255, 26f / 255, 37f / 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getStage().draw();
        if (!GameLogic.isPause()) {
            getStage().act(delta);
        }
        if (storyPrefs != null) {
            if (!_groupAllied.hasChildren()) {
                // el jugador muere
                // mostramos un window con opciones de volver al inicio o reiniciar el nivel
                _hudHistory.showWindowGameOver();
            }
            addPlanets();
            addEnemies();
            checkTargets();
            checkIfUserWin();
        }
        MainMenuScreen.checkAudio();
        if (!GameLogic.isAudioOn()) {
            getEnviromentQuiet().pause();
        } else {
            if (!getEnviromentQuiet().isPlaying()) {
                getEnviromentQuiet().play();
            }
        }
    }

    @Override
    public void pause() {
        Timer.instance().stop();
        _hudHistory.showWindowPause();
        if (GameLogic.isShowADS()) {
            getGame().actionResolver.showOrLoadInterstital();
        }
        Gdx.app.log("PAUSE", "TRUE");
    }

    @Override
    public void resume() {
        Timer.instance().start();
        Gdx.app.log("PAUSE", "FALSE");
    }

    @Override
    public void hide() {
        Timer.instance().stop();
        _hudHistory.showWindowPause();
    }

    public void init() {
        Timer.instance().clear();
        Timer.instance().start();
        setPauseGame(false);
        setScoreGame(0);
        setTimeGame(0);
        clearGroups();
        _hudHistory = new HUDHistory(this);
        ship = new Hurricane(this);
        getStage().addActor(_hudHistory);
        killEnemy = true;
        killBoss = true;
        killMBoss = true;
        killSBoss = true;
        survive = true;
        xtremsurvive = true;
    }

    public void content(int idLevel) {
        storyPrefs = new PreferenceStory(getLevelManager().getCurrentLevel(idLevel));
        setTarget();
        getStage().addActor(_groupPlanets);
        getStage().addActor(_groupAllied);
        getStage().addActor(_groupEnemyAdv);
        getStage().addActor(_groupEnemyBas);
        getStage().addActor(_groupShields);
        addAllieds();
        addShields();
        if (GameLogic.isAudioOn()) {
            getEnviromentQuiet().setLooping(true);
            getEnviromentQuiet().setVolume(VOLUME_5);
            getEnviromentQuiet().play();
        }
        ship.setHealth(storyPrefs.getShip_health());
        Gdx.app.log("SHIP", "" + Allied.getHealth());
        _hudHistory.setStoryPrefs(storyPrefs);
        int value;
        JsonValue tmp;
        String key;
        String[] current_str_target;
        for (int x = 0; x < storyPrefs.getTargets().size; x++) {
            tmp = storyPrefs.getTargets().get(x);
            key = tmp.name();
            value = storyPrefs.getData().get("main_target").getInt(key);
            current_str_target = storyPrefs.getStringTarget(key, value);
            _hudHistory.windowTarget.add(new ImageGame(getUi("target"))).width(28).height(28).padTop(-15).padBottom(-15).left();
            _hudHistory.windowTarget.add(new LabelGame(current_str_target[0], .4f, ColorGame.GREEN_POINTS)).padTop(-15).padBottom(-15).padLeft(10).left();
            _hudHistory.windowTarget.add(new LabelGame(current_str_target[1], .4f, ColorGame.GREEN_POINTS)).padTop(-15).padBottom(-15).padLeft(30).left();
            _hudHistory.windowTarget.row();
        }
    }

    public void setDialog(String str) {
        dialog = new Dialog("", getSkin_mini());
        dialog.setBackground(new NinePatchDrawable(new NinePatch(getUi("bg_bar_marine"), 9, 9, 9, 9)));
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

    private void addEnemies() {
        if (getTimeGame() != 0) {
            if (_groupEnemyAdv.getChildren().size < storyPrefs.getMax_adv_into_group()) {
                if (getTimeGame() % storyPrefs.getTimerate_advanced() == 0) {
                    _groupEnemyAdv.addActor(new AdvancedEnemy(this, Enemy.ADVANCED[MathUtils.random(0, Enemy.ADVANCED.length - 1)]));
                }
            }
            if (_groupEnemyBas.getChildren().size < storyPrefs.getMax_bas_into_group()) {
                if (getTimeGame() % storyPrefs.getTimerate_basic() == 0) {
                    _groupEnemyBas.addActor(new BasicEnemy(this, Enemy.ADVANCED[MathUtils.random(0, Enemy.ADVANCED.length - 1)]));
                }
            }
        }
    }

    private void addPlanets() {
        if (getTimeGame() % 30.0f == 0.0f) {
            _groupPlanets.clearChildren();
            _groupPlanets.addActor(
                    new Planet(
                            this,
                            Planet.planets[MathUtils.random(0, Planet.planets.length - 1)],
                            Planet.speed_planets[MathUtils.random(0, 2)],
                            MathUtils.random(.3f, 1f)
                    )
            );
        }
    }

    public void addAllieds() {
        _groupAllied.addActor(getShip());
    }

    public void addShields() {
        switch (storyPrefs.getShip_shield()) {
            case 1:
                _groupShields.addActor(new ShieldBronze(this));
                break;
            case 2:
                _groupShields.addActor(new ShieldSilver(this));
                break;
            case 3:
                _groupShields.addActor(new ShieldGold(this));
                break;
            default:
                break;
        }
    }
}
