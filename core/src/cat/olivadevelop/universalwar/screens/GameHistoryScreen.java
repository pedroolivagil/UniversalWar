package cat.olivadevelop.universalwar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Timer;

import cat.olivadevelop.universalwar.UniversalWarGame;
import cat.olivadevelop.universalwar.actors.allied.Hurricane;
import cat.olivadevelop.universalwar.actors.shields.ShieldBronze;
import cat.olivadevelop.universalwar.actors.ui.HUDHistory;
import cat.olivadevelop.universalwar.actors.ui.Planet;
import cat.olivadevelop.universalwar.screens.history.PreferenceStory;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

import static cat.olivadevelop.universalwar.tools.GameLogic.getTimeGame;
import static cat.olivadevelop.universalwar.tools.GameLogic.setScoreGame;
import static cat.olivadevelop.universalwar.tools.GameLogic.setTimeGame;

/**
 * Created by Oliva on 12/12/2015.
 */
public class GameHistoryScreen extends GeneralScreen {

    private static Hurricane ship;
    private PreferenceStory storyPrefs;

    public GameHistoryScreen(UniversalWarGame game) {
        super(game);
        _groupEnemy = new Group();
        _groupAllied = new Group();
        _groupPlanets = new Group();
        _groupShields = new Group();
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
        Timer.instance().clear();
        Timer.instance().start();
        // instanciamos el generador de niveles, segun el nivel del usuario
        // data_level contiene todos los datos del nivel
        storyPrefs = new PreferenceStory(GameLogic.getLevelManager().getCurrentLevel(1));

        _hudHistory = new HUDHistory(this, storyPrefs);
        setScoreGame(0);
        setTimeGame(0);
        getStage().addActor(_groupPlanets);
        getStage().addActor(_hudHistory);
        getStage().addActor(_groupAllied);
        getStage().addActor(_groupEnemy);
        getStage().addActor(_groupShields);
        ship = new Hurricane(this);
        addAllieds();
        addShields();
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
        _groupShields.addActor(new ShieldBronze(this));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(26f / 255, 26f / 255, 37f / 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getStage().draw();
        if (!GameLogic.isPause()) {
            getStage().act(delta);
        }
        MainMenuScreen.checkAudio();
        if (!_groupAllied.hasChildren()) {
            _hudArcade.showWindowGameOver();
        }
        addPlanets();
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
}
