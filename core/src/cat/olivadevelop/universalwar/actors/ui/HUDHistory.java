package cat.olivadevelop.universalwar.actors.ui;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import cat.olivadevelop.universalwar.actors.allied.Allied;
import cat.olivadevelop.universalwar.actors.shields.Shield;
import cat.olivadevelop.universalwar.screens.MainMenuScreen;
import cat.olivadevelop.universalwar.screens.history.LevelScreen;
import cat.olivadevelop.universalwar.screens.history.PreferenceStory;
import cat.olivadevelop.universalwar.tools.ButtonGame;
import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.GeneralScreen;
import cat.olivadevelop.universalwar.tools.ImageGame;
import cat.olivadevelop.universalwar.tools.LabelGame;
import cat.olivadevelop.universalwar.tools.Listener;
import cat.olivadevelop.universalwar.tools.WindowGame;

import static cat.olivadevelop.universalwar.tools.GameLogic.getBotonMenu2Drawable;
import static cat.olivadevelop.universalwar.tools.GameLogic.getNumberFormated;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScoreGame;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScreenHeight;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScreenWidth;
import static cat.olivadevelop.universalwar.tools.GameLogic.getSkin;
import static cat.olivadevelop.universalwar.tools.GameLogic.getString;
import static cat.olivadevelop.universalwar.tools.GameLogic.getTimeMin;
import static cat.olivadevelop.universalwar.tools.GameLogic.getTimeSec;
import static cat.olivadevelop.universalwar.tools.GameLogic.getTimer;
import static cat.olivadevelop.universalwar.tools.GameLogic.getUi;
import static cat.olivadevelop.universalwar.tools.GameLogic.setPauseGame;
import static cat.olivadevelop.universalwar.tools.GameLogic.setTimeDefault;

/**
 * Created by Oliva on 10/01/2016.
 */
public class HUDHistory extends HUD {
    private final ImageGame ipauseBG;
    private PreferenceStory storyPrefs;
    private Table tBottom;
    private LevelScreen scn;
    private LabelGame lblTime;
    private Group gHealth;
    private ImageGame hBarBg;
    private ImageGame hBar;
    private LabelGame hLbl;
    private Group gShield;
    private ImageGame sBarBg;
    private ImageGame sBar;
    private LabelGame sLbl;
    private Table tbBars;
    private int barsWidth = 270;
    private int barsHeight = 50;
    private int divisor = 10;
    private int temporal_score;
    private LabelGame lblScore;
    private Table tTop;
    private Table tShoot;
    private ButtonGame tbBack;
    private ButtonGame tbExit;
    private Table tablePause;
    private WindowGame windowPause;

    public HUDHistory(GeneralScreen screen, PreferenceStory storyPrefs) {
        super(screen);
        this.scn = (LevelScreen) screen;
        this.storyPrefs = storyPrefs;
        setTimeDefault();
        settBottom();
        settTop();

        ImageGame i = new ImageGame(getUi("blank"));
        i.addListener(new Listener() {
            @Override
            public void action() {
                if (scn._groupAllied.hasChildren()) {
                    Allied a = (Allied) scn._groupAllied.getChildren().first();
                    if (a.alive) {
                        a.shoot();
                    }
                }
            }
        });

        tShoot = new Table(getSkin());
        tShoot.setWidth(getScreenWidth());
        tShoot.setHeight(getScreenHeight() - (tTop.getHeight() + tBottom.getHeight()));
        tShoot.setY(tBottom.getX() + tBottom.getHeight() - 10);
        tShoot.add(i).width(getScreenWidth()).height(getScreenHeight() - (tTop.getHeight() + tBottom.getHeight()));
        screen.getStage().addActor(tShoot);

        setWindowPause();
        screen.getStage().addActor(windowPause);
        ipauseBG = new ImageGame(getUi("black"), 0, 0, getScreenWidth(), getScreenHeight());
        ipauseBG.setVisible(false);
        screen.getStage().addActor(ipauseBG);
    }

    public void setBars() {
        lblTime = new LabelGame("", .4f);
        lblTime.center();

        gHealth = new Group();
        gHealth.setWidth(barsWidth);
        gHealth.setHeight(barsHeight);

        hBarBg = new ImageGame(new NinePatch(getUi("bg_bar_blue"), 18, 18, 18, 18));
        hBar = new ImageGame(new NinePatch(getUi("bar_blue"), 18, 18, 18, 18));
        hLbl = new LabelGame(getString("lLives"), .5f);
        hLbl.setPosition(90, -32);
        hBarBg.setWidth(barsWidth);
        hBarBg.setHeight(barsHeight);
        hBar.setHeight(barsHeight - 8);
        hBar.setX(4);
        hBar.setY(4);

        gHealth.addActor(hBarBg);
        gHealth.addActor(hBar);
        gHealth.addActor(hLbl);

        gShield = new Group();
        gShield.setWidth(barsWidth);
        gShield.setHeight(barsHeight);

        sBarBg = new ImageGame(new NinePatch(getUi("bg_bar_red"), 9, 9, 9, 9));
        sBar = new ImageGame(new NinePatch(getUi("bar_red"), 9, 9, 9, 9));
        sLbl = new LabelGame(getString("lShield"), .5f);
        sLbl.setPosition(70, -32);
        sBarBg.setWidth(barsWidth);
        sBarBg.setHeight(barsHeight);
        sBar.setHeight(barsHeight - 8);
        sBar.setX(4);
        sBar.setY(4);

        gShield.addActor(sBarBg);
        gShield.addActor(sBar);
        gShield.addActor(sLbl);

        tbBars = new Table(getSkin());
        tbBars.setWidth(getScreenWidth());
        tbBars.row().expandX().height(barsHeight);
        tbBars.add().width(10);
        tbBars.add(gHealth);
        tbBars.add(lblTime).width(getScreenWidth() - (barsWidth * 2) - 20);
        tbBars.add(gShield);
        tbBars.add().width(10);
    }

    public void settBottom() {
        setBars();
        this.tBottom = new Table(getSkin());
        this.tBottom.background(getBotonMenu2Drawable());
        this.tBottom.setHeight(140);
        this.tBottom.setWidth(getScreenWidth());
        this.tBottom.setY(-7);
        this.tBottom.add().expandX();
        this.tBottom.row().padTop(10);
        this.tBottom.add(tbBars).expandX();
        screen.getStage().addActor(this.tBottom);
    }

    public void settTop() {
        lblScore = new LabelGame("0", .7f, ColorGame.GREEN_POINTS);
        this.tTop = new Table(getSkin());
        this.tTop.background(getBotonMenu2Drawable());
        this.tTop.setHeight(80);
        this.tTop.setWidth(getScreenWidth());
        this.tTop.setY(getScreenHeight() - 73);
        this.tTop.add(lblScore).padBottom(10).expandX().center();
        screen.getStage().addActor(this.tTop);
    }

    public void actLabelTime() {
        lblTime.setText(((getTimeMin() < 10) ? "0" + getTimeMin() : "" + getTimeMin()) + ":" + ((getTimeSec() < 10) ? "0" + getTimeSec() : "" + getTimeSec()));
    }

    public void actLabelScore() {
        temporal_score = temporal_score + (getScoreGame() - temporal_score) / divisor;
        if (((getScoreGame() - temporal_score) < divisor) && ((getScoreGame() - temporal_score) > 0)) {
            temporal_score++;
        }
        lblScore.setText(getNumberFormated(temporal_score));
    }

    public void actShieldBar() {
        if (Shield.getImpacts() <= 0) {
            sBar.toBack();
        } else {
            sBar.toFront();
            sLbl.toFront();
            sBar.setWidth((barsWidth / Shield.getMaxImpacts()) * Shield.getImpacts() - 8);
        }
    }

    public void actHealthBar() {
        if (Allied.getHealth() <= 0) {
            hBar.toBack();
        } else {
            hBar.toFront();
            hLbl.toFront();
            hBar.setWidth((barsWidth / Allied.getMaxHealth()) * Allied.getHealth() - 8);
        }
    }

    public LabelGame getLblTime() {
        return lblTime;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        actLabelTime();
        actLabelScore();
        toFront();
        actHealthBar();
        actShieldBar();
        tTop.toFront();
        tBottom.toFront();
        tShoot.toFront();
    }

    private void setWindowPause() {
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
                //screen.getGame().setScreen(screen.getGame()._gameOverScreen);
                hideWindowPause();
            }
        });
        tablePause = new Table();
        tablePause.row().pad(20).padTop(40);
        tablePause.add(MainMenuScreen._groupSound).height(MainMenuScreen.imageOff.getHeight() * MainMenuScreen._groupSound.getScaleX()).width(MainMenuScreen.imageOff.getWidth() * MainMenuScreen._groupSound.getScaleX()).colspan(2);
        tablePause.row().pad(20);
        tablePause.add(tbBack).height(tbBack.getHeight() * tbBack.getScale()).width(tbBack.getWidth() * tbBack.getScale()).pad(15);
        tablePause.add(tbExit).height(tbExit.getHeight() * tbExit.getScale()).width(tbExit.getWidth() * tbExit.getScale()).pad(15);
        windowPause = new WindowGame(screen, getString("windowTitle").toUpperCase());
        windowPause.setBackground(getBotonMenu2Drawable());
        windowPause.setWidth(getScreenWidth());
        windowPause.setHeight(280);
        windowPause.setOrigin(windowPause.getWidth() / 2, windowPause.getHeight() / 2);
        windowPause.setPosition(getScreenWidth() / 2 - windowPause.getWidth() / 2, getScreenHeight() / 2 - windowPause.getHeight() / 2);
        windowPause.setVisible(false);
        windowPause.setResizable(false);
        windowPause.add(tablePause);
    }

    public void showWindowPause() {
        //if (!wExit.isVisible()) {
        ipauseBG.setVisible(true);
        ipauseBG.toFront();
        windowPause.setVisible(true);
        windowPause.toFront();
        getTimer().stop();
        setPauseGame(true);
        // }
    }

    public void hideWindowPause() {
        ipauseBG.setVisible(false);
        windowPause.setVisible(false);
        getTimer().start();
        setPauseGame(false);
    }
}
