package cat.olivadevelop.universalwar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;

import cat.olivadevelop.universalwar.UniversalWarGame;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

import static cat.olivadevelop.universalwar.tools.GameLogic.getUi;
import static cat.olivadevelop.universalwar.tools.GameLogic.loadUI;

/**
 * Created by Oliva on 12/05/2015.
 * <p/>
 * Pantalla Splash
 */
public class SplashScreen extends GeneralScreen {

    public SplashScreen(UniversalWarGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        loadUI();
        Image logo = new Image(getUi("logoSplash"));
        logo.setPosition(GameLogic.getScreenWidth() / 2 - logo.getWidth() / 2, GameLogic.getScreenHeight() / 2 - logo.getHeight() / 2);
        _stage.addActor(logo);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(game._mainMenuScreen);
            }
        }, 2.6f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _stage.act(delta);
        _stage.draw();
    }

    @Override
    public void dispose() {
        game._splashScreen.dispose();
        super.dispose();
    }
}
