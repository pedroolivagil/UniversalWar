package cat.olivadevelop.universalwar.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.async.AsyncTask;

import cat.olivadevelop.universalwar.UniversalWarGame;
import cat.olivadevelop.universalwar.tools.AsyncGame;
import cat.olivadevelop.universalwar.tools.ButtonGame;
import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.ConnectDB;
import cat.olivadevelop.universalwar.tools.GeneralScreen;
import cat.olivadevelop.universalwar.tools.LabelGame;
import cat.olivadevelop.universalwar.tools.Listener;

import static cat.olivadevelop.universalwar.tools.GameLogic.getBotonMenu2Drawable;
import static cat.olivadevelop.universalwar.tools.GameLogic.getNumberFormated;
import static cat.olivadevelop.universalwar.tools.GameLogic.getPrefs;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScoreGame;
import static cat.olivadevelop.universalwar.tools.GameLogic.getString;
import static cat.olivadevelop.universalwar.tools.GameLogic.getTimeGame;

/**
 * Created by OlivaDevelop on 22/04/2015.
 */
public class GameOverScreen extends GeneralScreen {

    public GameOverScreen(UniversalWarGame game) {
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
        AsyncGame async = new AsyncGame(this);
        async.submit(new AsyncTask<Object>() {
            @Override
            public Object call() throws Exception {
                ConnectDB conn = new ConnectDB();
                conn.insert("INSERT INTO scores(username,score,time,id_device) VALUES('" + getPrefs().getString("userName", "UW_Anonymous") + "'," + getScoreGame() + "," + getTimeGame() + ",'" + game.getIdDevice() + "')");
                return true;
            }
        });
        addTableGameOver();
        _stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    private void addTableGameOver() {
        LabelGame title = new LabelGame(getString("GO_lTitle"));
        title.setColor(ColorGame.ORANGE);
        title.setAlignment(Align.top, Align.center);

        ButtonGame btTryAgain = new ButtonGame(getString("GO_btTryAgain"), .5f);
        ButtonGame btBackMenu = new ButtonGame(getString("GO_btBackMenu"), .5f);

        btTryAgain.setWidth(700);
        btBackMenu.setWidth(700);

        btTryAgain.addListener(new Listener() {
            @Override
            public void action() {
                game.setScreen(game._gameArcadeScreen);
            }
        });

        btBackMenu.addListener(new Listener() {
            @Override
            public void action() {
                game.setScreen(game._mainMenuScreen);
            }
        });
        Table tablePuntuacion = new Table();
        tablePuntuacion.setBackground(getBotonMenu2Drawable());
        // puntuacion
        tablePuntuacion.add(new LabelGame(getString("lScore"), .5f));
        tablePuntuacion.row();
        tablePuntuacion.add(new LabelGame("" + getNumberFormated(getScoreGame()), 1, ColorGame.RED));
        // tiempo
        tablePuntuacion.row();
        tablePuntuacion.add(new LabelGame(getString("lTime"), .5f));
        tablePuntuacion.row();
        tablePuntuacion.add(new LabelGame("" + GameArcadeScreen._hud.getLblTime().getText(), 1, ColorGame.RED));

        table = new Table();
        table.setFillParent(true);
        table.setOrigin(table.getWidth() / 2, table.getHeight() / 2);
        // titulo
        table.add(title);
        table.row().expand();
        table.add(tablePuntuacion).fillX();
        // botones
        table.row().padTop(50);
        table.add(btTryAgain).height(btTryAgain.getHeight() * btTryAgain.getScale()).padLeft(-10);
        table.row().padTop(10).padBottom(50);
        table.add(btBackMenu).height(btBackMenu.getHeight() * btBackMenu.getScale()).padLeft(-10);
    }

    @Override
    public void dispose() {
        game._gameOverScreen.dispose();
        super.dispose();
    }
}