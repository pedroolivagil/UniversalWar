package cat.olivadevelop.universalwar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Timer;

import cat.olivadevelop.universalwar.UniversalWarGame;
import cat.olivadevelop.universalwar.actors.allied.Genesis;
import cat.olivadevelop.universalwar.actors.allied.SpaceShipOne;
import cat.olivadevelop.universalwar.actors.enemies.AdvancedEnemy;
import cat.olivadevelop.universalwar.actors.enemies.BasicEnemy;
import cat.olivadevelop.universalwar.actors.enemies.Boss;
import cat.olivadevelop.universalwar.actors.enemies.Enemy;
import cat.olivadevelop.universalwar.actors.enemies.MegaBoss;
import cat.olivadevelop.universalwar.actors.enemies.SuperBoss;
import cat.olivadevelop.universalwar.actors.shields.ShieldBronze;
import cat.olivadevelop.universalwar.actors.ui.HUDArcade;
import cat.olivadevelop.universalwar.actors.ui.Planet;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

import static cat.olivadevelop.universalwar.tools.GameLogic.getCountEnemiesDispached;
import static cat.olivadevelop.universalwar.tools.GameLogic.getTimeGame;
import static cat.olivadevelop.universalwar.tools.GameLogic.setCountEnemiesDispached;
import static cat.olivadevelop.universalwar.tools.GameLogic.setPauseGame;
import static cat.olivadevelop.universalwar.tools.GameLogic.setScoreGame;
import static cat.olivadevelop.universalwar.tools.GameLogic.setTimeGame;

/**
 * Created by OlivaDevelop on 26/05/2015.
 */
public class GameArcadeScreen extends GeneralScreen {

    public static SpaceShipOne ship1;
    public static Genesis ship2;
    private static float enemyRound;

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

    public void addAllieds() {
        _groupAllied.addActor(getShip1());
    }

    public void addShields() {
        _groupShields.addActor(new ShieldBronze(this));
    }

    public void clearGroups() {
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
        _hud.showWindowPause();
        if (GameLogic.isShowADS()) {
            getGame().actionResolver.showOrLoadInterstital();
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
        ship1 = new SpaceShipOne(this);
        ship2 = new Genesis(this);
        getStage().addActor(_groupPlanets);
        getStage().addActor(_groupAllied);
        getStage().addActor(_groupEnemy);
        getStage().addActor(_groupShields);
        _hud = new HUDArcade(this);
        getStage().addActor(_hud);
        _hud.toFront();
        addAllieds();
        addShields();
        addEnemies();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(26f / 255, 26f / 255, 37f / 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glDisable(GL20.GL_BLEND);
        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        getStage().draw();
        if (!GameLogic.isPause()) {
            getStage().act(delta);
        }
        addActors();
        MainMenuScreen.checkAudio();
        if (!_groupAllied.hasChildren()) {
            _hud.showWindowGameOver();
        }
    }

    private void addActors() {
        addEnemies();
        addPlanets();
        if (getCountEnemiesDispached() % 150 == 0 && getCountEnemiesDispached() != 0) {
            _groupEnemy.addActor(new Boss(this));
            GameLogic.addToScore(10000);
            setCountEnemiesDispached(getCountEnemiesDispached() + 1);
        }
        if (getCountEnemiesDispached() % 550 == 0 && getCountEnemiesDispached() != 0) {
            _groupEnemy.addActor(new SuperBoss(this));
            GameLogic.addToScore(100000);
            setCountEnemiesDispached(getCountEnemiesDispached() + 1);
        }
        if (getCountEnemiesDispached() % 2500 == 0 && getCountEnemiesDispached() != 0) {
            _groupEnemy.addActor(new MegaBoss(this));
            GameLogic.addToScore(1000000);
            setCountEnemiesDispached(getCountEnemiesDispached() + 1);
        }
    }

    @Override
    public void pause() {
        Timer.instance().stop();
        _hud.showWindowPause();
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
        _hud.showWindowPause();
    }
}
