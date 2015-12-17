package cat.olivadevelop.universalwar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Timer;

import cat.olivadevelop.universalwar.UniversalWarGame;
import cat.olivadevelop.universalwar.actors.allied.Allied;
import cat.olivadevelop.universalwar.actors.allied.Genesis;
import cat.olivadevelop.universalwar.actors.allied.SpaceShipOne;
import cat.olivadevelop.universalwar.actors.enemies.AdvancedEnemy;
import cat.olivadevelop.universalwar.actors.enemies.BasicEnemy;
import cat.olivadevelop.universalwar.actors.enemies.Boss;
import cat.olivadevelop.universalwar.actors.enemies.Enemy;
import cat.olivadevelop.universalwar.actors.enemies.SuperBoss;
import cat.olivadevelop.universalwar.actors.shields.ShieldBronze;
import cat.olivadevelop.universalwar.actors.ui.HUD;
import cat.olivadevelop.universalwar.actors.ui.Planet;
import cat.olivadevelop.universalwar.tools.ButtonGame;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;
import cat.olivadevelop.universalwar.tools.Listener;
import cat.olivadevelop.universalwar.tools.WindowGame;

import static cat.olivadevelop.universalwar.tools.GameLogic.getBotonMenu2Drawable;
import static cat.olivadevelop.universalwar.tools.GameLogic.getCountEnemiesDispached;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScreenHeight;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScreenWidth;
import static cat.olivadevelop.universalwar.tools.GameLogic.getString;
import static cat.olivadevelop.universalwar.tools.GameLogic.getTimeGame;
import static cat.olivadevelop.universalwar.tools.GameLogic.setCountEnemiesDispached;
import static cat.olivadevelop.universalwar.tools.GameLogic.setPauseGame;
import static cat.olivadevelop.universalwar.tools.GameLogic.setScoreGame;
import static cat.olivadevelop.universalwar.tools.GameLogic.setTimeGame;

/**
 * Created by OlivaDevelop on 26/05/2015.
 */
public class GameArcadeScreen extends GeneralScreen {

    private static float enemyRound;
    private static SpaceShipOne ship1;
    private static Genesis ship2;
    ButtonGame btnContinue;
    ButtonGame btnExitGame;
    private WindowGame wExit;

    public GameArcadeScreen(UniversalWarGame game) {
        super(game);
        _groupEnemy = new Group();
        _groupAllied = new Group();
        _groupPlanets = new Group();
        _groupShields = new Group();
    }

    public static float getEnemyRound() {
        return enemyRound;
    }

    public static void setEnemyRound(float enemys) {
        enemyRound = enemys;
    }

    public static SpaceShipOne getShip1() {
        return ship1;
    }

    public static Genesis getShip2() {
        return ship2;
    }

    public void showWindowPause() {
        windowPause.setVisible(true);
        windowPause.toFront();
        _hud.getTimer().stop();
        setPauseGame(true);
    }

    public void hideWindowPause() {
        _hud.getTimer().start();
        windowPause.setVisible(false);
        setPauseGame(false);
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

    private void addEnemies() {
        if ((getTimeGame() % 5 == 0)) {
            if (_groupAllied.hasChildren()) {
                for (int z = 0; z < getEnemyRound(); z++) {
                    if (_groupEnemy.getChildren().size < GameLogic.getMaxEnemiesIntoGroup()) {
                        _groupEnemy.addActor(
                                (MathUtils.random(1, 4) != 1) ?
                                        new BasicEnemy(this, Enemy.BASIC[MathUtils.random(0, Enemy.BASIC.length - 1)]) :
                                        new AdvancedEnemy(this, Enemy.ADVANCED[MathUtils.random(0, Enemy.ADVANCED.length - 1)])
                        );
                    }
                }
            }
        }
    }

    private void addAllieds() {
        _groupAllied.addActor(getShip1());
    }

    private void addShields() {
        _groupShields.addActor(new ShieldBronze(this));
    }

    public void setWindowPause() {
        tbBack = new ButtonGame(getString("windowTbBack"), .5f);
        tbExit = new ButtonGame(getString("windowTbExit"), .5f);
        tbBack.addListener(new Listener() {
            @Override
            public void action() {
                hideWindowPause();
            }
        });
        tbExit.addListener(new Listener() {
            @Override
            public void action() {
                /**
                 * Preguntar si realmente quiere salir o no
                 */
                game.setScreen(game._gameOverScreen);
            }
        });
        tablePause = new Table();
        tablePause.row().pad(20).padTop(40);
        tablePause.add(MainMenuScreen._groupSound).height(MainMenuScreen.imageOff.getHeight() * MainMenuScreen._groupSound.getScaleX()).width(MainMenuScreen.imageOff.getWidth() * MainMenuScreen._groupSound.getScaleX()).colspan(2);
        tablePause.row().pad(20);
        tablePause.add(tbBack).height(tbBack.getHeight() * tbBack.getScale()).width(tbBack.getWidth() * tbBack.getScale()).pad(15);
        tablePause.add(tbExit).height(tbExit.getHeight() * tbExit.getScale()).width(tbExit.getWidth() * tbExit.getScale()).pad(15);
        windowPause = new WindowGame(this, getString("windowTitle").toUpperCase());
        windowPause.setBackground(getBotonMenu2Drawable());
        windowPause.setWidth(getScreenWidth());
        windowPause.setHeight(280);
        windowPause.setOrigin(windowPause.getWidth() / 2, windowPause.getHeight() / 2);
        windowPause.setPosition(getScreenWidth() / 2 - windowPause.getWidth() / 2, getScreenHeight() / 2 - windowPause.getHeight() / 2);
        windowPause.setVisible(false);
        windowPause.setResizable(false);
        windowPause.add(tablePause);
    }

    private void clearGroups() {
        if (_groupAllied.hasChildren()) {
            _groupAllied.clearChildren();
        }
        if (_groupEnemy.hasChildren()) {
            _groupEnemy.clearChildren();
        }
        if (_groupShields.hasChildren()) {
            _groupShields.clearChildren();
        }
        if (_groupPlanets.hasChildren()) {
            _groupPlanets.clearChildren();
        }
    }

    @Override
    public void actionBackButton() {
        super.actionBackButton();
        showWindowPause();
        if (GameLogic.isShowADS()) {
            game.actionResolver.showOrLoadInterstital();
        }
    }

    @Override
    public void actionOtherButton(InputEvent event, int keycode) {
        if (keycode == Input.Keys.P) {
            Allied a = _groupAllied.findActor("Genesis");
            if (a == null) {
                _groupAllied.addActor(getShip2().enter(45));
                a = _groupAllied.findActor("SpaceShipOne");
                if (a != null) {
                    a.exit();
                }
            }
        } else if (keycode == Input.Keys.O) {
            Allied a = _groupAllied.findActor("SpaceShipOne");
            if (a == null) {
                _groupAllied.addActor(getShip1().enter(45));
                a = _groupAllied.findActor("Genesis");
                if (a != null) {
                    a.exit();
                }
            }
        }
    }

    @Override
    public void show() {
        super.show();
        setPauseGame(false);
        Timer.instance().clear();
        Timer.instance().start();
        setCountEnemiesDispached(0);
        GameLogic.setMaxEnemiesIntoGroup(10);
        setEnemyRound(5);
        setScoreGame(0);
        setTimeGame(0);
        clearGroups();
        setWindowPause();
        setWindowGameOver();
        ship1 = new SpaceShipOne(this);
        ship2 = new Genesis(this);
        _stage.addActor(_groupPlanets);
        _stage.addActor(_groupAllied);
        _stage.addActor(_groupEnemy);
        _stage.addActor(_groupShields);
        _stage.addActor(windowPause);
        _hud = new HUD(this);
        _stage.addActor(_hud);
        _hud.toFront();
        addAllieds();
        addShields();
        addEnemies();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(26f / 255, 26f / 255, 37f / 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        _stage.draw();
        if (!GameLogic.isPause()) {
            _stage.act(delta);
        }
        addActors();
        MainMenuScreen.checkAudio();
        if (!_groupAllied.hasChildren()) {
            showWindowGameOver();
        }
    }

    private void setWindowGameOver() {
        btnContinue = new ButtonGame(getString("windowBtnCont"), .5f);
        btnExitGame = new ButtonGame(getString("windowBtnExit"), .5f);
        btnContinue.addListener(new Listener() {
            @Override
            public void action() {
                hideWindowGameOver();
                ship1 = new SpaceShipOne(GameArcadeScreen.this);
                ship2 = new Genesis(GameArcadeScreen.this);
                addAllieds();
                addShields();
            }
        });
        btnExitGame.addListener(new Listener() {
            @Override
            public void action() {
                exitGame();
            }
        });
        Table tGameOver = new Table();
        tGameOver.row().pad(10).padTop(60);
        tGameOver.add(btnContinue).height(btnContinue.getHeight() * btnContinue.getScale()).width(btnContinue.getWidth() * btnContinue.getScale()).pad(15);
        tGameOver.add(btnExitGame).height(btnExitGame.getHeight() * btnExitGame.getScale()).width(btnExitGame.getWidth() * btnExitGame.getScale()).pad(15);
        wExit = new WindowGame(this, getString("windowGOverTtl").toUpperCase());
        wExit.setBackground(getBotonMenu2Drawable());
        wExit.setWidth(getScreenWidth());
        wExit.setHeight(220);
        wExit.setOrigin(wExit.getWidth() / 2, wExit.getHeight() / 2);
        wExit.setPosition(getScreenWidth() / 2 - wExit.getWidth() / 2, getScreenHeight() / 2 - wExit.getHeight() / 2);
        wExit.setVisible(false);
        wExit.setResizable(false);
        wExit.add(tGameOver);
        _stage.addActor(wExit);
    }

    private void showWindowGameOver() {
        setPauseGame(true);
        wExit.setVisible(true);
        wExit.toFront();
    }

    private void hideWindowGameOver() {
        setPauseGame(false);
        wExit.setVisible(false);
        wExit.toBack();
    }

    private void addActors() {
        addEnemies();
        addPlanets();
        if (getCountEnemiesDispached() % 100 == 0 && getCountEnemiesDispached() != 0) {
            _groupEnemy.addActor(new Boss(this, Enemy.BASIC[MathUtils.random(0, Enemy.BASIC.length - 1)]));
            GameLogic.addToScore(10000);
            setCountEnemiesDispached(getCountEnemiesDispached() + 1);
        }
        if (getCountEnemiesDispached() % 501 == 0 && getCountEnemiesDispached() != 0) {
            _groupEnemy.addActor(new SuperBoss(this, Enemy.BOSS[MathUtils.random(0, Enemy.BOSS.length - 1)]));
            GameLogic.addToScore(100000);
            setCountEnemiesDispached(getCountEnemiesDispached() + 1);
        }
    }

    public final void exitGame() {
        /**
         * Crear un menu tipo pausa para elejir si quiere continuar usando un supercorazon o finalizar la partida
         */
        game.setScreen(game._gameOverScreen);
        clearGroups();
    }

    @Override
    public void pause() {
        Timer.instance().stop();
        showWindowPause();
        if (GameLogic.isShowADS()) {
            game.actionResolver.showOrLoadInterstital();
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
        showWindowPause();
    }
}
