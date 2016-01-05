package cat.olivadevelop.universalwar.screens;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
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

import static cat.olivadevelop.universalwar.tools.GameLogic.getBotonMenu2Drawable;
import static cat.olivadevelop.universalwar.tools.GameLogic.getNumberFormated;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScreenHeight;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScreenWidth;
import static cat.olivadevelop.universalwar.tools.GameLogic.getSkin;
import static cat.olivadevelop.universalwar.tools.GameLogic.getSkin_mini;
import static cat.olivadevelop.universalwar.tools.GameLogic.getString;
import static cat.olivadevelop.universalwar.tools.GameLogic.getUi;
import static cat.olivadevelop.universalwar.tools.GameLogic.getUserID;

/**
 * Created by OlivaDevelop on 26/05/2015.
 */
public class ScoreScreen extends GeneralScreen {

    int x;
    private ResultSet scores;
    private Table tableScore;
    private Table scoresTable;
    private Dialog dialog;
    private ImageGame ipauseBG;

    public ScoreScreen(UniversalWarGame game) {
        super(game);
    }

    @Override
    public void actionBackButton() {
        game.setScreen(game._mainMenuScreen);
    }

    @Override
    public void show() {
        super.show();
        setDialog();
        showDialog();
        AsyncGame async = new AsyncGame(this);
        async.submit(new AsyncTask<Object>() {
            @Override
            public Object call() throws Exception {
                contentScreen();
                listeners();
                hideDialog();
                return true;
            }
        });
    }

    private void setDialog() {
        dialog = new Dialog("", getSkin_mini());
        dialog.setBackground(new NinePatchDrawable(new NinePatch(getUi("bg_bar_blue"), 9, 9, 9, 9)));
        dialog.text(getString("lLoad"));
        ipauseBG = new ImageGame(getUi("black"), 0, 0, getScreenWidth(), getScreenHeight());
        _stage.addActor(ipauseBG);
        hideDialog();
    }

    public void showDialog() {
        ipauseBG.setVisible(true);
        dialog.show(_stage);
    }

    public void hideDialog() {
        ipauseBG.setVisible(false);
        dialog.hide();
    }

    public void contentScreen() {
        tbBack = new ButtonGame(getString("windowTbBack"), .5f);
        tbBack.setWidth(700);

        Label title = new Label(getString("lHighScore").toUpperCase(), getSkin());
        Label subTitle = new Label("", getSkin());
        Label otherScores = new Label(getString("lSubTitle"), getSkin());
        otherScores.setFontScale(.4f);
        title.setColor(ColorGame.ORANGE);

        tableScore = new Table(getSkin());
        tableScore.setFillParent(true);
        tableScore.add(title);
        tableScore.row();
        tableScore.add(subTitle);

        scoresTable = new Table();
        scoresTable.setBackground(getBotonMenu2Drawable());
        ConnectDB conn = new ConnectDB();
        scores = conn.query("SELECT points FROM uw_points WHERE id_customer = " + getUserID() + " ORDER BY points DESC Limit 6;");
        x = 2;
        try {
            if (!scores.next()) {
                scoresTable.row();
                scoresTable.add(new LabelGame(getString("lNoScore"), .7f));
            } else {
                tableScore.row().padTop(60);
                tableScore.add(new LabelGame(getString("lTitle"), .4f)).height(30);
                tableScore.row();
                tableScore.add(new LabelGame(getNumberFormated(Integer.parseInt(scores.getString("points"))), 1f, ColorGame.RED));
                scoresTable.row().expandX();
                scoresTable.add(new LabelGame(getString("lSubTitle"), .4f)).colspan(2);
                while (scores.next()) {
                    scoresTable.row().expandX();
                    scoresTable.add(new LabelGame(x + "ª", .3f, ColorGame.BLUE_CYAN)).width(50);
                    scoresTable.add(new LabelGame(getNumberFormated(Integer.parseInt(scores.getString("points"))), .5f).center()).width(600).padRight(25);
                    x++;
                }
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableScore.row().expand();
        tableScore.add(scoresTable).fillX().center();
        tableScore.row().padBottom(50);
        tableScore.add(tbBack).height(tbBack.getHeight() * tbBack.getScale());
        _stage.addActor(tableScore);
    }

    public void listeners() {
        tbBack.addListener(new Listener() {
            @Override
            public void action() {
                game.setScreen(game._mainMenuScreen);
            }
        });
    }
}
