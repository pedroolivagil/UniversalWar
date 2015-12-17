package cat.olivadevelop.universalwar.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.sql.ResultSet;
import java.sql.SQLException;

import cat.olivadevelop.universalwar.UniversalWarGame;
import cat.olivadevelop.universalwar.tools.ButtonGame;
import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.ConnectDB;
import cat.olivadevelop.universalwar.tools.GeneralScreen;
import cat.olivadevelop.universalwar.tools.LabelGame;
import cat.olivadevelop.universalwar.tools.Listener;

import static cat.olivadevelop.universalwar.tools.GameLogic.getBotonMenu2Drawable;
import static cat.olivadevelop.universalwar.tools.GameLogic.getNumberFormated;
import static cat.olivadevelop.universalwar.tools.GameLogic.getSkin;
import static cat.olivadevelop.universalwar.tools.GameLogic.getString;

/**
 * Created by OlivaDevelop on 26/05/2015.
 */
public class ScoreScreen extends GeneralScreen {

    int x;
    private ResultSet scores;
    private Table tableScore;
    private Table scoresTable;

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
        contentScreen();
        listeners();
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
        scores = conn.query("SELECT score FROM scores WHERE id_device LIKE '" + game.getIdDevice() + "' ORDER BY score DESC Limit 6;");
        x = 2;
        try {
            if (!scores.next()) {
                scoresTable.row();
                scoresTable.add(new LabelGame(getString("lNoScore"), .7f));
            } else {
                tableScore.row();
                tableScore.add(new LabelGame(getString("lTitle"), .4f)).height(30);
                tableScore.row();
                tableScore.add(new LabelGame(getNumberFormated(Integer.parseInt(scores.getString("score"))), 1f, ColorGame.RED));
                scoresTable.row().expandX();
                scoresTable.add(new LabelGame(getString("lSubTitle"), .4f)).colspan(2);
                while (scores.next()) {
                    scoresTable.row().expandX();
                    scoresTable.add(new LabelGame(x + "a", .3f, ColorGame.BLUE_CYAN)).width(50);
                    scoresTable.add(new LabelGame(getNumberFormated(Integer.parseInt(scores.getString("score"))), .5f).center()).width(600).padRight(25);
                    x++;
                }
            }
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

    @Override
    public void dispose() {
        game._highScoreScreen.dispose();
        super.dispose();
    }
}
