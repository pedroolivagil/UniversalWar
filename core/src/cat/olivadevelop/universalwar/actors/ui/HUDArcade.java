package cat.olivadevelop.universalwar.actors.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.async.AsyncTask;

import java.sql.ResultSet;

import cat.olivadevelop.universalwar.actors.allied.Allied;
import cat.olivadevelop.universalwar.actors.allied.Genesis;
import cat.olivadevelop.universalwar.actors.allied.SpaceShipOne;
import cat.olivadevelop.universalwar.actors.bullets.Bullet;
import cat.olivadevelop.universalwar.actors.bullets.BulletOrange;
import cat.olivadevelop.universalwar.actors.bullets.MegaBullet;
import cat.olivadevelop.universalwar.actors.bullets.SuperBullet;
import cat.olivadevelop.universalwar.actors.enemies.Enemy;
import cat.olivadevelop.universalwar.actors.shields.Shield;
import cat.olivadevelop.universalwar.actors.shields.ShieldBronze;
import cat.olivadevelop.universalwar.screens.GameArcadeScreen;
import cat.olivadevelop.universalwar.screens.MainMenuScreen;
import cat.olivadevelop.universalwar.tools.ActorGame;
import cat.olivadevelop.universalwar.tools.AsyncGame;
import cat.olivadevelop.universalwar.tools.ButtonGame;
import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.ConnectDB;
import cat.olivadevelop.universalwar.tools.GeneralScreen;
import cat.olivadevelop.universalwar.tools.ImageGame;
import cat.olivadevelop.universalwar.tools.LabelGame;
import cat.olivadevelop.universalwar.tools.Listener;
import cat.olivadevelop.universalwar.tools.WindowGame;

import static cat.olivadevelop.universalwar.tools.GameLogic.getAnimHealth;
import static cat.olivadevelop.universalwar.tools.GameLogic.getBotonMenu2Drawable;
import static cat.olivadevelop.universalwar.tools.GameLogic.getButtons;
import static cat.olivadevelop.universalwar.tools.GameLogic.getCountEnemiesDispached;
import static cat.olivadevelop.universalwar.tools.GameLogic.getEnemy;
import static cat.olivadevelop.universalwar.tools.GameLogic.getId_obj_health;
import static cat.olivadevelop.universalwar.tools.GameLogic.getNumberFormated;
import static cat.olivadevelop.universalwar.tools.GameLogic.getPowers;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScoreGame;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScreenHeight;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScreenWidth;
import static cat.olivadevelop.universalwar.tools.GameLogic.getSkin;
import static cat.olivadevelop.universalwar.tools.GameLogic.getSprites;
import static cat.olivadevelop.universalwar.tools.GameLogic.getString;
import static cat.olivadevelop.universalwar.tools.GameLogic.getTimeGame;
import static cat.olivadevelop.universalwar.tools.GameLogic.getTimeMin;
import static cat.olivadevelop.universalwar.tools.GameLogic.getTimeSec;
import static cat.olivadevelop.universalwar.tools.GameLogic.getTimer;
import static cat.olivadevelop.universalwar.tools.GameLogic.getUi;
import static cat.olivadevelop.universalwar.tools.GameLogic.getUserID;
import static cat.olivadevelop.universalwar.tools.GameLogic.setPauseGame;
import static cat.olivadevelop.universalwar.tools.GameLogic.setTimeDefault;

/**
 * Created by OlivaDevelop on 18/04/2015.
 */
public class HUDArcade extends ActorGame {

    private final ImageGame ipauseBG;
    private int time1 = 30;
    private int time2 = 60;
    private int time3 = 90;
    private int time4 = 120;
    private int time5 = 150;
    private int barsWidth = 270;
    private int barsHeight = 50;
    private int divisor = 10;
    private int temporal_score;
    private float elapsedTime;
    private double timeShield;
    private double timeHealth;
    private double timeMisile;
    private double timeSuperMisile;
    private double timeHelp;
    private double timeBurst;
    private boolean boolShield;
    private boolean boolHealth;
    private boolean boolMisile;
    private boolean boolSuperMisile;
    private boolean boolHelp;
    private boolean boolBurst;
    private Table tTop;
    private Table tBottom;
    private Table tbBars;
    private Table tbBtns;
    private Table tShoot;
    private Group gHealth;
    private Group gShield;
    private LabelGame lblTime;
    private LabelGame lblScore;
    private LabelGame hLbl;
    private LabelGame sLbl;
    private ImageGame hBarBg;
    private ImageGame hBar;
    private ImageGame sBarBg;
    private ImageGame sBar;
    private ImageGame iMissil;      // disparar misil
    private ImageGame iHelp;        // pedir ayuda
    private ImageGame iShield;      // recargar o cargar escudo
    private ImageGame iSuperMissil; // disparar supermisil
    private ImageGame iHealth;      // recargar salud
    private ImageGame iBurst;  // disparar ráfaga de balas
    private Animation anim;
    private LabelGame lblCounter;
    private Animation healthAnimation;
    private ButtonGame btnContinue;
    private ButtonGame btnExitGame;
    private WindowGame wExit;
    private GameArcadeScreen scn;
    private ButtonGame tbBack;
    private ButtonGame tbExit;
    private Table tablePause;
    private WindowGame windowPause;
    private ImageGame iFinalLivesAnim;
    private LabelGame lblFinalLives;
    private ResultSet query;
    private int quantityExtraLives;
    private AsyncGame async;

    public HUDArcade(final GeneralScreen screen) {
        super(screen);
        this.scn = (GameArcadeScreen) screen;
        healthAnimation = new Animation(1 / 15f, getSprites(6, 4, getAnimHealth()));
        timeMisile = 0;
        timeBurst = 0;
        timeHealth = 0;
        timeHelp = 0;
        timeShield = 0;
        timeSuperMisile = 0;
        setTimeDefault();
        setBars();
        setBtns();
        setBoolBurst(false);
        setBoolShield(false);
        setBoolMisile(false);
        setBoolSuperMisile(false);
        setBoolHelp(false);
        setBoolHealth(false);
        settTop();
        settBottom();
        setListeners();
        screen._stage.addActor(tTop);
        screen._stage.addActor(tBottom);

        ImageGame i = new ImageGame(getUi("blank"));
        i.addListener(new Listener() {
            @Override
            public void action() {
                if (screen._groupAllied.hasChildren()) {
                    Allied a = (Allied) screen._groupAllied.getChildren().first();
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
        screen._stage.addActor(tShoot);

        Group gDefeated = new Group();
        lblCounter = new LabelGame("0", .4f, ColorGame.GREEN);
        lblCounter.setX(38);
        gDefeated.addActor(lblCounter);
        gDefeated.addActor(new ImageGame(getEnemy(Enemy.BASIC[5]), 0, 47, 28, 28));
        gDefeated.setPosition(10, 65);
        screen._stage.addActor(gDefeated);
        setWindowGameOver();
        setWindowPause();
        screen._stage.addActor(windowPause);
        ipauseBG = new ImageGame(getUi("black"), 0, 0, getScreenWidth(), getScreenHeight());
        ipauseBG.setVisible(false);
        screen._stage.addActor(ipauseBG);
        async = new AsyncGame(screen);
        setExtraLives();
    }

    public void setExtraLives() {
        if (getUserID() > 0) {
            async.submit(new AsyncTask<Object>() {
                @Override
                public Object call() throws Exception {
                    ConnectDB conn = new ConnectDB();
                    query = conn.query("SELECT quantity FROM uw_userbag WHERE id_object = " + getId_obj_health() + " AND id_customer = " + getUserID());
                    if (query.next()) {
                        quantityExtraLives = query.getInt("quantity");
                    }
                    conn.close();
                    return true;
                }
            });
        }
    }

    public void updateExtraLives() {
        if (getUserID() > 0) {
            async.submit(new AsyncTask<Object>() {
                @Override
                public Object call() throws Exception {
                    ConnectDB conn = new ConnectDB();
                    conn.insert("UPDATE uw_userbag SET quantity = (quantity-1) WHERE id_object = " + getId_obj_health() + " AND id_customer = " + getUserID());
                    query = conn.query("SELECT quantity FROM uw_userbag WHERE id_object = " + getId_obj_health() + " AND id_customer = " + getUserID());
                    if (query.next()) {
                        quantityExtraLives = query.getInt("quantity");
                    }
                    conn.close();
                    return true;
                }
            });
        }
    }

    public void settTop() {
        lblScore = new LabelGame("0", .7f, ColorGame.GREEN_POINTS);
        this.tTop = new Table(getSkin());
        this.tTop.background(getBotonMenu2Drawable());
        this.tTop.setHeight(80);
        this.tTop.setWidth(getScreenWidth());
        this.tTop.setY(getScreenHeight() - 73);
        this.tTop.add(lblScore).padBottom(10).expandX().center();
    }

    public void settBottom() {
        this.tBottom = new Table(getSkin());
        this.tBottom.background(getBotonMenu2Drawable());
        this.tBottom.setHeight(140);
        this.tBottom.setWidth(getScreenWidth());
        this.tBottom.setY(-7);
        this.tBottom.add(tbBtns).expandX();
        this.tBottom.row().padTop(10);
        this.tBottom.add(tbBars).expandX();
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

    public void setBtns() {
        iMissil = new ImageGame(getPowers("powerupRed_bomb"));
        iSuperMissil = new ImageGame(getPowers("powerupRed_multibomb"));
        iShield = new ImageGame(getPowers("powerupRed_shield"));
        iHealth = new ImageGame(getPowers("powerupRed_bolt"));
        iHelp = new ImageGame(getPowers("powerupRed_help"));
        iBurst = new ImageGame(getPowers("powerupRed_star"));

        int pad = 10;
        tbBtns = new Table(getSkin());
        tbBtns.row().height(barsHeight);
        tbBtns.add(iHelp).width(50).height(50).padLeft(pad).padRight(pad);
        tbBtns.add(iHealth).width(50).height(50).padLeft(pad).padRight(pad);
        tbBtns.add(iMissil).width(50).height(50).padLeft(pad).padRight(pad);
        tbBtns.add(iSuperMissil).width(50).height(50).padLeft(pad).padRight(pad);
        tbBtns.add(iShield).width(50).height(50).padLeft(pad).padRight(pad);
        tbBtns.add(iBurst).width(50).height(50).padLeft(pad).padRight(pad);

        anim = new Animation(1 / 13f, getSprites(1, 8, getButtons()));
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

    public LabelGame getLblTime() {
        return lblTime;
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

    public void actLblExtraLives() {
        lblFinalLives.setText("x" + quantityExtraLives);
        if (quantityExtraLives <= 0) {
            btnContinue.setColor(ColorGame.DARK_BLUE);
        } else {
            btnContinue.setColor(ColorGame.WHITE);
        }
    }

    public void actCounter() {
        lblCounter.setText(getNumberFormated(getCountEnemiesDispached()));
    }

    public boolean isBoolShield() {
        return boolShield;
    }

    public void setBoolShield(boolean boolShield) {
        this.boolShield = boolShield;
        if (!boolShield) {
            iShield.setColor(1, 1, 1, .6f);
        } else {
            iShield.setColor(1, 1, 1, 1);
        }
    }

    public boolean isBoolHealth() {
        return boolHealth;
    }

    public void setBoolHealth(boolean boolHealth) {
        this.boolHealth = boolHealth;
        if (!boolHealth) {
            iHealth.setColor(1, 1, 1, .6f);
        } else {
            iHealth.setColor(1, 1, 1, 1);
        }
    }

    public boolean isBoolMisile() {
        return boolMisile;
    }

    public void setBoolMisile(boolean boolMisile) {
        this.boolMisile = boolMisile;
        if (!boolMisile) {
            iMissil.setColor(1, 1, 1, .6f);
        } else {
            iMissil.setColor(1, 1, 1, 1);
        }
    }

    public boolean isBoolSuperMisile() {
        return boolSuperMisile;
    }

    public void setBoolSuperMisile(boolean boolSuperMisile) {
        this.boolSuperMisile = boolSuperMisile;
        if (!boolSuperMisile) {
            iSuperMissil.setColor(1, 1, 1, .6f);
        } else {
            iSuperMissil.setColor(1, 1, 1, 1);
        }
    }

    public boolean isBoolHelp() {
        return boolHelp;
    }

    public void setBoolHelp(boolean boolHelp) {
        this.boolHelp = boolHelp;
        if (!boolHelp) {
            iHelp.setColor(1, 1, 1, .6f);
        } else {
            iHelp.setColor(1, 1, 1, 1);
        }
    }

    public boolean isBoolBurst() {
        return boolBurst;
    }

    public void setBoolBurst(boolean boolBurst) {
        this.boolBurst = boolBurst;
        if (!boolBurst) {
            iBurst.setColor(1, 1, 1, .6f);
        } else {
            iBurst.setColor(1, 1, 1, 1);
        }
    }

    private void setListeners() {
        iMissil.addListener(new Listener() {
            @Override
            public void action() {
                actionMisile();
            }
        });
        iSuperMissil.addListener(new Listener() {
            @Override
            public void action() {
                actionSuperMisile();
            }
        });
        iShield.addListener(new Listener() {
            @Override
            public void action() {
                actionShield();
            }
        });
        iHealth.addListener(new Listener() {
            @Override
            public void action() {
                actionHealth();
            }
        });
        iHelp.addListener(new Listener() {
            @Override
            public void action() {
                actionHelp();
            }
        });
        iBurst.addListener(new Listener() {
            @Override
            public void action() {
                actionBurst();
            }
        });
    }

    private void actionMisile() {
        if (screen._groupAllied.hasChildren()) {
            if (isBoolMisile()) {
                screen._stage.addActor(new SuperBullet(screen,
                                screen._groupAllied.getChildren().first().getX() + (screen._groupAllied.getChildren().first().getWidth() / 2) + getWidth() / 2 - 30,
                                screen._groupAllied.getChildren().first().getY() + 2,
                                Bullet.BULLET_UP)
                );
                screen._groupAllied.toFront();
                setBoolMisile(false);
                timeMisile = getTimeGame();
            }
        }
    }

    private void actionSuperMisile() {
        if (screen._groupAllied.hasChildren()) {
            if (isBoolSuperMisile()) {
                screen._stage.addActor(new MegaBullet(screen,
                                screen._groupAllied.getChildren().first().getX() + (screen._groupAllied.getChildren().first().getWidth() / 2) + getWidth() / 2 + 20,
                                screen._groupAllied.getChildren().first().getY() + 2,
                                Bullet.BULLET_UP)
                );
                screen._groupAllied.toFront();
                setBoolSuperMisile(false);
                timeSuperMisile = getTimeGame();
            }
        }
    }

    private void actionHealth() {
        if (screen._groupAllied.hasChildren()) {
            if (isBoolHealth()) {
                if (Allied.getHealth() < Allied.getMaxHealth()) {
                    Allied.addLiveGame(5);
                    setBoolHealth(false);
                    timeHealth = getTimeGame();
                }
            }
        }
    }

    private void actionShield() {
        if (screen._groupAllied.hasChildren()) {
            if (isBoolShield()) {
                if (Shield.getImpacts() < Shield.getMaxImpacts()) {
                    if (screen._groupShields.hasChildren()) {
                        Shield.addImpacts(5);
                    } else {
                        screen._groupShields.addActor(new ShieldBronze(screen));
                    }
                    setBoolShield(false);
                    timeShield = getTimeGame();
                }
            }
        }
    }

    private void actionBurst() {
        if (screen._groupAllied.hasChildren()) {
            if (isBoolBurst()) {
                Timer tmr = new Timer();
                tmr.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        screen._stage.addActor(new BulletOrange(screen,
                                        screen._groupAllied.getChildren().first().getX() + (screen._groupAllied.getChildren().first().getWidth() / 2) + getWidth() / 2 - 5,
                                        screen._groupAllied.getChildren().first().getY() + 2,
                                        Bullet.BULLET_UP)
                        );
                        screen._groupAllied.toFront();
                    }
                }, 0, .2f, 50);
                tmr.start();
                setBoolBurst(false);
                timeBurst = getTimeGame();
            }
        }
    }

    private void actionHelp() {
        if (screen._groupAllied.hasChildren()) {
            if (isBoolHelp()) {
                if (screen._groupAllied.getChildren().first().getName().equals("SpaceShipOne")) {
                    final Genesis g1 = new Genesis(screen);
                    final Genesis g2 = new Genesis(screen);
                    g1.setActive(false);
                    g2.setActive(false);
                    g1.enter(60, 60);
                    g2.enter(getScreenWidth() - 60 - g2.getWidth(), 60);
                    screen._stage.addActor(g1);
                    screen._stage.addActor(g2);
                    Timer tmr = new Timer();
                    tmr.scheduleTask(new Timer.Task() {
                        @Override
                        public void run() {
                            g1.shoot();
                            g2.shoot();
                            g1.toFront();
                            g2.toFront();
                            screen._groupAllied.toFront();
                        }
                    }, 0, .2f, 40);
                    tmr.scheduleTask(new Timer.Task() {
                        @Override
                        public void run() {
                            g1.exit();
                            g2.exit();
                        }
                    }, .2f * 40);
                    tmr.start();
                    g1.addAction(
                            Actions.sequence(
                                    Actions.delay(2),
                                    Actions.moveTo(getScreenWidth() - 60 - g2.getWidth(), 60, .2f * 45)
                            )
                    );
                    g2.addAction(
                            Actions.sequence(
                                    Actions.delay(2),
                                    Actions.moveTo(60, 60, .2f * 45)
                            )
                    );
                    setBoolHelp(false);
                    timeHelp = getTimeGame();
                }
            }
        }
    }

    private void checkButtons() {
        if (timeShield == getTimeGame() - time1) {
            setBoolShield(true);
        }
        if (timeHealth == getTimeGame() - time2) {
            setBoolHealth(true);
        }
        if (timeMisile == getTimeGame() - time2) {
            setBoolMisile(true);
        }
        if (timeHelp == getTimeGame() - time3) {
            setBoolHelp(true);
        }
        if (timeBurst == getTimeGame() - time4) {
            setBoolBurst(true);
        }
        if (timeSuperMisile == getTimeGame() - time4) {
            setBoolSuperMisile(true);
        }
    }

    private void setWindowGameOver() {
        iFinalLivesAnim = new ImageGame(healthAnimation.getKeyFrame(elapsedTime, true));
        lblFinalLives = new LabelGame("", .7f, ColorGame.GREEN_POINTS);
        btnContinue = new ButtonGame(getString("windowBtnCont"), .5f);
        btnExitGame = new ButtonGame(getString("windowBtnExit"), .5f);
        btnContinue.addListener(new Listener() {
            @Override
            public void action() {
                if (quantityExtraLives > 0) {
                    hideWindowGameOver();
                    updateExtraLives();
                    GameArcadeScreen.ship1 = new SpaceShipOne(screen);
                    GameArcadeScreen.ship2 = new Genesis(screen);
                    scn.addAllieds();
                    scn.addShields();
                }
            }
        });
        btnExitGame.addListener(new Listener() {
            @Override
            public void action() {
                exitGame();
            }
        });
        Table tGameOver = new Table();
        tGameOver.row().pad(10);
        tGameOver.add(btnContinue).height(btnContinue.getHeight() * btnContinue.getScale()).width(btnContinue.getWidth() * btnContinue.getScale()).pad(15);
        tGameOver.add(btnExitGame).height(btnExitGame.getHeight() * btnExitGame.getScale()).width(btnExitGame.getWidth() * btnExitGame.getScale()).pad(15);
        wExit = new WindowGame(screen, getString("windowGOverTtl").toUpperCase());
        wExit.setBackground(getBotonMenu2Drawable());
        wExit.setWidth(getScreenWidth());
        wExit.setHeight(240);
        wExit.setOrigin(wExit.getWidth() / 2, wExit.getHeight() / 2);
        wExit.setPosition(getScreenWidth() / 2 - wExit.getWidth() / 2, getScreenHeight() / 2 - wExit.getHeight() / 2);
        wExit.setVisible(false);
        wExit.setResizable(false);
        wExit.row().padTop(50);
        wExit.add().width(150);
        wExit.add(iFinalLivesAnim).height(70).width(70).padRight(20);
        wExit.add(lblFinalLives);
        wExit.add().width(150);
        wExit.row();
        wExit.add(tGameOver).padTop(-10).colspan(4);
        screen._stage.addActor(wExit);
    }

    public void showWindowGameOver() {
        setPauseGame(true);
        getTimer().stop();
        ipauseBG.setVisible(true);
        ipauseBG.toFront();
        wExit.setVisible(true);
        wExit.toFront();
    }

    public void hideWindowGameOver() {
        setPauseGame(false);
        getTimer().start();
        ipauseBG.setVisible(false);
        wExit.setVisible(false);
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
                screen.game.setScreen(screen.game._gameOverScreen);
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
        if (!wExit.isVisible()) {
            ipauseBG.setVisible(true);
            ipauseBG.toFront();
            windowPause.setVisible(true);
            windowPause.toFront();
            getTimer().stop();
            setPauseGame(true);
        }
    }

    public void hideWindowPause() {
        ipauseBG.setVisible(false);
        windowPause.setVisible(false);
        getTimer().start();
        setPauseGame(false);
    }

    public final void exitGame() {
        scn.clearGroups();
        screen.game.setScreen(screen.game._gameOverScreen);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        actLabelTime();
        actLabelScore();
        actCounter();
        toFront();
        actHealthBar();
        actShieldBar();
        checkButtons();
        actLblExtraLives();
        tTop.toFront();
        tBottom.toFront();
        tShoot.toFront();
        tbBtns.toFront();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(ColorGame.WHITE);
        batch.enableBlending();
        super.draw(batch, parentAlpha);
        elapsedTime += Gdx.graphics.getDeltaTime();
        tbBtns.setBackground(new TextureRegionDrawable(anim.getKeyFrame(elapsedTime, true)));
        iFinalLivesAnim.setDrawable(new TextureRegionDrawable(healthAnimation.getKeyFrame(elapsedTime, true)));
    }
}
