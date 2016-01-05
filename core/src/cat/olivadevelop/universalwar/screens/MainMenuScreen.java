package cat.olivadevelop.universalwar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import cat.olivadevelop.universalwar.UniversalWarGame;
import cat.olivadevelop.universalwar.tools.ButtonGame;
import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;
import cat.olivadevelop.universalwar.tools.ImageGame;
import cat.olivadevelop.universalwar.tools.Listener;

import static cat.olivadevelop.universalwar.tools.GameLogic.getPlanets;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScreenHeight;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScreenWidth;
import static cat.olivadevelop.universalwar.tools.GameLogic.getString;
import static cat.olivadevelop.universalwar.tools.GameLogic.getUi;

/**
 * Created by OlivaDevelop on 26/05/2015.
 */
public class MainMenuScreen extends GeneralScreen {

    public static ImageGame imageOn;
    public static ImageGame imageOff;
    public static Group _groupSound;

    public MainMenuScreen(UniversalWarGame game) {
        super(game);
    }

    public static void checkAudio() {
        if (GameLogic.isAudioOn()) {
            if (_groupSound.hasChildren()) {
                _groupSound.clearChildren();
            }
            _groupSound.addActor(imageOff);
        } else {
            if (_groupSound.hasChildren()) {
                _groupSound.clearChildren();
            }
            _groupSound.addActor(imageOn);
        }
    }

    @Override
    public void show() {
        super.show();
        Group title = new Group();
        ImageGame subTitle = new ImageGame(getUi("title"));
        ImageGame planet = new ImageGame(getPlanets("jupiter"));
        //planet.setScale(.5f);

        title.setWidth(subTitle.getWidth());
        title.setHeight(planet.getHeight());
        title.setPosition(
                getScreenWidth() / 2 - title.getWidth() / 2,
                getScreenHeight() + 500
        );
        subTitle.setPosition(
                title.getWidth() / 2 - subTitle.getOriginX(),
                -80
        );
        planet.setPosition(
                0,
                -150
        );
        title.addActor(planet);
        title.addActor(subTitle);
        title.toFront();

        tableMenu();

        // Stage Adds
        _stage.addActor(title);
        _stage.addActor(table);

        // actions
        title.addAction(Actions.moveTo(getScreenWidth() / 2 - title.getWidth() / 2, getScreenHeight() - title.getHeight() / 2 - 100, 1.2f, Interpolation.bounce));
        //subTitle.addAction(Actions.forever(Actions.sequence(Actions.scaleBy(.1f, .1f, .5f), Actions.scaleTo(1f, 1f, .5f))));
        planet.addAction(Actions.forever(Actions.rotateBy(-10f, .9f)));

        /*if (GameLogic.isAudioOn()) {
            getSoundAmbient().loop();
        }*/
        buttonMute();
        game.setScreen(game._gameArcadeScreen);
    }

    private void buttonMute() {
        _groupSound = new Group();
        _groupSound.setPosition(getScreenWidth() - 70, getScreenHeight() - 70);
        _stage.addActor(_groupSound);

        imageOff = new ImageGame(getUi("mute_off"));
        imageOn = new ImageGame(getUi("mute_on"));

        imageOff.addListener(new Listener() {
            @Override
            public void action() {
                GameLogic.setAudioOn(false);
            }
        });
        imageOn.addListener(new Listener() {
            @Override
            public void action() {
                GameLogic.setAudioOn(true);
            }
        });
        _groupSound.setScale(.4f);
    }

    private void tableMenu() {
        int widthButtons = 700;
        start = new ButtonGame(getString("lStart"), .5f);
        misiones = new ButtonGame(getString("lMiss"), .5f);
        score = new ButtonGame(getString("lHighScore"), .5f);
        options = new ButtonGame(getString("lOptions"), .5f);
        exit = new ButtonGame(getString("lExit"), .5f);

        start.setWidth(widthButtons);
        misiones.setWidth(widthButtons);
        score.setWidth(widthButtons);
        options.setWidth(widthButtons);
        exit.setWidth(widthButtons);

        misiones.setColor(ColorGame.DARK_BLUE);

        start.addListener(new Listener() {
            @Override
            public void action() {
                game.setScreen(game._gameArcadeScreen);
            }
        });
        misiones.addListener(new Listener() {
            @Override
            public void action() {
                //game.setScreen(game._gameArcadeScreen);
            }
        });
        score.addListener(new Listener() {
            @Override
            public void action() {
                game.setScreen(game._highScoreScreen);
            }
        });
        options.addListener(new Listener() {
            @Override
            public void action() {
                game.setScreen(game._settingsScreen);
            }
        });
        exit.addListener(new Listener() {
            @Override
            public void action() {
                Gdx.app.exit();
            }
        });

        table = new Table();
        table.setY(-getScreenHeight());
        table.setWidth(getScreenWidth());
        table.setHeight(getScreenHeight());
        table.setOrigin(getScreenWidth() / 2, getScreenHeight() / 2);

        table.row().padBottom(10);
        table.add(start).height(start.getHeight() * start.getScale());
        table.row().padBottom(10);
        table.add(misiones).height(misiones.getHeight() * misiones.getScale());
        table.row().padBottom(10);
        table.add(score).height(score.getHeight() * score.getScale());
        table.row().padBottom(10);
        table.add(options).height(options.getHeight() * options.getScale());
        table.row().padBottom(10);
        table.add(exit).height(exit.getHeight() * exit.getScale());
        table.addAction(
                Actions.moveTo(
                        0,
                        -getScreenHeight() / 4,
                        2f,
                        Interpolation.bounce
                )
        );
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        checkAudio();
    }
}
