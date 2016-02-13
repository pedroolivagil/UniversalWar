package cat.olivadevelop.universalwar.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

import cat.olivadevelop.universalwar.UniversalWarGame;
import cat.olivadevelop.universalwar.actors.ui.HUDArcade;
import cat.olivadevelop.universalwar.actors.ui.HUDHistory;
import cat.olivadevelop.universalwar.actors.ui.InfiniteBackground;

import static cat.olivadevelop.universalwar.tools.GameLogic.getScreenHeight;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScreenWidth;

/**
 * Created by OlivaDevelop on 26/05/2015.
 */
public class GeneralScreen implements Screen {

    public static HUDArcade _hudArcade;
    public static HUDHistory _hudHistory;
    public static Table tablePause;
    public ButtonGame start;
    public ButtonGame misiones;
    public ButtonGame score;
    public ButtonGame exit;
    public ButtonGame tbBack;
    public ButtonGame tbExit;
    public ButtonGame options;
    public Group _groupEnemy;
    public Group _groupAllied;
    public Group _groupPlanets;
    public Group _groupShields;
    public Group _groupStars;
    public Group _groupMeteors;
    public Table table;
    public WindowGame windowPause;
    private UniversalWarGame _game;
    private Stage _stage;

    public GeneralScreen(UniversalWarGame game) {
        this._game = game;
        _groupStars = new Group();
        _groupMeteors = new Group();
    }

    public void actionBackButton() {

    }

    public void actionOtherButton(InputEvent event, int keycode) {

    }

    public Stage getStage() {
        return _stage;
    }

    public UniversalWarGame getGame() {
        return _game;
    }

    @Override
    public void show() {
        _stage = new Stage(new FitViewport(getScreenWidth(), getScreenHeight()));
        Gdx.input.setInputProcessor(getStage());
        Gdx.input.setCatchBackKey(true);
        getStage().addListener(new Listener() {
            @Override
            public void action(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) {
                    actionBackButton();
                }
                actionOtherButton(event, keycode);
            }
        });
        if (this != getGame()._splashScreen) {
            getStage().addActor(new InfiniteBackground(this));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(26f / 255, 26f / 255, 37f / 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getStage().act(delta);
        getStage().draw();
    }

    @Override
    public void resize(int width, int height) {
        getStage().getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        getStage().dispose();
    }
}
