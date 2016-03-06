package cat.olivadevelop.universalwar.actors.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;

import cat.olivadevelop.universalwar.actors.allied.Allied;
import cat.olivadevelop.universalwar.actors.allied.Genesis;
import cat.olivadevelop.universalwar.actors.bullets.Bullet;
import cat.olivadevelop.universalwar.actors.bullets.BulletOrange;
import cat.olivadevelop.universalwar.actors.bullets.MegaBullet;
import cat.olivadevelop.universalwar.actors.bullets.SuperBullet;
import cat.olivadevelop.universalwar.actors.enemies.Enemy;
import cat.olivadevelop.universalwar.actors.shields.Shield;
import cat.olivadevelop.universalwar.actors.shields.ShieldBronze;
import cat.olivadevelop.universalwar.actors.shields.ShieldGold;
import cat.olivadevelop.universalwar.actors.shields.ShieldSilver;
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
import static cat.olivadevelop.universalwar.tools.GameLogic.getButtons;
import static cat.olivadevelop.universalwar.tools.GameLogic.getCountEnemiesDispached;
import static cat.olivadevelop.universalwar.tools.GameLogic.getEnemy;
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
import static cat.olivadevelop.universalwar.tools.GameLogic.setPauseGame;
import static cat.olivadevelop.universalwar.tools.GameLogic.setTimeDefault;

/**
 * Created by Oliva on 10/01/2016.
 */
public class HUDHistory extends HUD {
    public WindowGame windowTarget;
    private ImageGame ipauseBG;
    private LabelGame lblCounter;
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
    private Table tbBtns;
    private ButtonGame tbBack;
    private ButtonGame tbExit;
    private Table tablePause;
    private WindowGame windowPause;
    private ButtonGame btnReset;
    private ButtonGame btnExitGame;
    private WindowGame wExit;
    private WindowGame wWin;
    private ButtonGame btnNextLevel;
    private ImageGame iMissil;      // disparar misil
    private ImageGame iHelp;        // pedir ayuda
    private ImageGame iShield;      // recargar o cargar escudo
    private ImageGame iSuperMissil; // disparar supermisil
    private ImageGame iHealth;      // recargar salud
    private ImageGame iBurst;  // disparar ráfaga de balas
    private Animation anim;
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
    private int time1 = 30;
    private int time2 = 60;
    private int time3 = 90;
    private int time4 = 120;
    //private int time5 = 150;
    private boolean imgDefined;

    public HUDHistory(GeneralScreen screen) {
        super(screen);
        this.scn = (LevelScreen) screen;
        this.imgDefined = false;
        setTimeDefault();
        setBars();
        setBtns();
        settBottom();
        settTop();
        setBoolBurst(false);
        setBoolShield(false);
        setBoolMisile(false);
        setBoolSuperMisile(false);
        setBoolHelp(false);
        setBoolHealth(false);
        setListeners();
        ImageGame i = new ImageGame(getUi("blank"));
        i.addListener(new Listener() {
            @Override
            public void action() {
                if (scn._groupAllied.hasChildren()) {
                    Allied a = (Allied) scn._groupAllied.getChildren().first();
                    if (a.alive) {
                        if (Allied.isCanShoot()) {
                            a.shoot();
                        }
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
        setWindowGameOver();
        setWindowWinner();
        screen.getStage().addActor(windowPause);
        screen.getStage().addActor(windowTarget);
        ipauseBG = new ImageGame(getUi("black"), 0, 0, getScreenWidth(), getScreenHeight());
        ipauseBG.setVisible(false);
        screen.getStage().addActor(ipauseBG);

        Group gDefeated = new Group();
        lblCounter = new LabelGame("0", .4f, ColorGame.GREEN);
        lblCounter.setX(38);
        gDefeated.addActor(lblCounter);
        gDefeated.addActor(new ImageGame(getEnemy(Enemy.BASIC[5]), 0, 47, 28, 28));
        gDefeated.setPosition(10, 65);
        screen.getStage().addActor(gDefeated);
    }

    public void setStoryPrefs(PreferenceStory storyPrefs) {
        this.storyPrefs = storyPrefs;
        if (windowTarget != null) {
            if (!storyPrefs.isShipCanShoot()) {
                windowTarget.add(new LabelGame(getString("lnotCanShoot"), .4f, ColorGame.RED)).colspan(3);
                windowTarget.row();
            }
        }
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
        this.tBottom = new Table(getSkin());
        this.tBottom.background(getBotonMenu2Drawable());
        this.tBottom.setHeight(140);
        this.tBottom.setWidth(getScreenWidth());
        this.tBottom.setY(-7);
        this.tBottom.add(tbBtns).expandX();
        this.tBottom.row().padTop(10);
        this.tBottom.add(tbBars).expandX();
        screen.getStage().addActor(this.tBottom);
    }

    public void settTop() {
        lblScore = new LabelGame("0", .4f, ColorGame.GREEN_POINTS);
        this.tTop = new Table(getSkin());
        this.tTop.background(getBotonMenu2Drawable());
        this.tTop.setHeight(55);
        this.tTop.setWidth(getScreenWidth());
        this.tTop.setY(getScreenHeight() - 48);
        this.tTop.add(lblScore).padBottom(0).expandX().center();
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

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        elapsedTime += Gdx.graphics.getDeltaTime();
        tbBtns.setBackground(new TextureRegionDrawable(anim.getKeyFrame(elapsedTime, true)));
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
        tTop.toFront();
        tBottom.toFront();
        tShoot.toFront();
        tbBtns.toFront();
        if (tbBtns.hasChildren()) {
            for (int x = 0; x < tbBtns.getChildren().size; x++) {
                tbBtns.getChildren().get(x).toFront();
            }
        }
        if (storyPrefs != null) {
            checkButtons();
            checkBtnImages();
        }
    }

    private void checkBtnImages() {
        if (!imgDefined) {
            if (storyPrefs.isPowerup_missile()) {
                iMissil.setDrawable(new TextureRegionDrawable(getPowers("powerupRed_bomb")));
            }
            if (storyPrefs.isPowerup_supermissile()) {
                iSuperMissil.setDrawable(new TextureRegionDrawable(getPowers("powerupRed_multibomb")));
            }
            if (storyPrefs.isPowerup_shield()) {
                iShield.setDrawable(new TextureRegionDrawable(getPowers("powerupRed_shield")));
            }
            if (storyPrefs.isPowerup_health()) {
                iHealth.setDrawable(new TextureRegionDrawable(getPowers("powerupRed_bolt")));
            }
            if (storyPrefs.isPowerup_help()) {
                iHelp.setDrawable(new TextureRegionDrawable(getPowers("powerupRed_help")));
            }
            if (storyPrefs.isPowerup_shooter()) {
                iBurst.setDrawable(new TextureRegionDrawable(getPowers("powerupRed_star")));
            }
            imgDefined = true;
        }
    }

    public void setBtns() {
        iMissil = new ImageGame(getUi("blank"));
        iSuperMissil = new ImageGame(getUi("blank"));
        iShield = new ImageGame(getUi("blank"));
        iHealth = new ImageGame(getUi("blank"));
        iHelp = new ImageGame(getUi("blank"));
        iBurst = new ImageGame(getUi("blank"));

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

    private void checkButtons() {
        if (storyPrefs.isPowerup_shield()) {
            if (timeShield == getTimeGame() - time1) {
                setBoolShield(true);
            }
        }
        if (storyPrefs.isPowerup_health()) {
            if (timeHealth == getTimeGame() - time2) {
                setBoolHealth(true);
            }
        }
        if (storyPrefs.isPowerup_missile()) {
            if (timeMisile == getTimeGame() - time2) {
                setBoolMisile(true);
            }

        }
        if (storyPrefs.isPowerup_help()) {
            if (timeHelp == getTimeGame() - time3) {
                setBoolHelp(true);
            }
        }
        if (storyPrefs.isPowerup_shooter()) {
            if (timeBurst == getTimeGame() - time4) {
                setBoolBurst(true);
            }
        }
        if (storyPrefs.isPowerup_supermissile()) {
            if (timeSuperMisile == getTimeGame() - time4) {
                setBoolSuperMisile(true);
            }
        }
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
                screen.getStage().addActor(new SuperBullet(screen, new Group[]{screen._groupEnemyBas, screen._groupEnemyAdv, screen._bossGroup, screen._megaBossGroup, screen._superBossGroup},
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
                screen.getStage().addActor(new MegaBullet(screen, new Group[]{screen._groupEnemyBas, screen._groupEnemyAdv, screen._bossGroup, screen._megaBossGroup, screen._superBossGroup},
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
                if (screen._groupShields.hasChildren()) {
                    if (Shield.getImpacts() < Shield.getMaxImpacts()) {
                        Shield.addImpacts(5);
                        setBoolShield(false);
                        timeShield = getTimeGame();
                    }
                } else {
                    switch (storyPrefs.getShip_shield()) {
                        case 1:
                            screen._groupShields.addActor(new ShieldBronze(screen));
                            break;
                        case 2:
                            screen._groupShields.addActor(new ShieldSilver(screen));
                            break;
                        case 3:
                            screen._groupShields.addActor(new ShieldGold(screen));
                            break;
                        default:
                            screen._groupShields.addActor(new ShieldBronze(screen));
                            break;
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
                        screen.getStage().addActor(new BulletOrange(screen,
                                        screen._groupAllied.getChildren().first().getX() - 2,
                                        screen._groupAllied.getChildren().first().getY() + 2,
                                        Bullet.BULLET_UP)
                        );
                        screen.getStage().addActor(new BulletOrange(screen,
                                        screen._groupAllied.getChildren().first().getX() + screen._groupAllied.getChildren().first().getWidth() - 10,
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
                if (screen._groupAllied.getChildren().first().getName().equals("Hurricane")) {
                    final Genesis g1 = new Genesis(screen);
                    final Genesis g2 = new Genesis(screen);
                    g1.setActive(false);
                    g2.setActive(false);
                    g1.enter(60, 60);
                    g2.enter(getScreenWidth() - 60 - g2.getWidth(), 60);
                    screen.getStage().addActor(g1);
                    screen.getStage().addActor(g2);
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
                hideWindowPause();
                showWindowGameOver();
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
        windowPause.setPosition(getScreenWidth() / 2 - windowPause.getWidth() / 2, getScreenHeight() * .75f - windowPause.getHeight() / 2);
        windowPause.setVisible(false);
        windowPause.setResizable(false);
        windowPause.add(tablePause);

        windowTarget = new WindowGame(screen, "");
        windowTarget.setBackground(new NinePatchDrawable(new NinePatch(getUi("blank"), 7, 7, 7, 7)));
        windowTarget.setWidth(getScreenWidth());
        windowTarget.setOrigin(windowPause.getWidth() / 2, windowPause.getHeight() / 2);
        windowTarget.setPosition(getScreenWidth() / 2 - windowPause.getWidth() / 2, windowPause.getY() - windowTarget.getHeight() - 80);
        windowTarget.setVisible(false);
        windowTarget.setResizable(false);
        windowTarget.align(Align.top);
        windowTarget.add(new LabelGame(getString("windowTitleTarget").toUpperCase(), .6f, ColorGame.ESMERALDA_LIGHT)).colspan(3);
        windowTarget.row();
    }

    private void setWindowGameOver() {
        btnReset = new ButtonGame(getString("GO_btTryAgain"), .5f);
        btnExitGame = new ButtonGame(getString("windowBtnExit"), .5f);
        btnReset.addListener(new Listener() {
            @Override
            public void action() {
                reset();
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
        tGameOver.add(btnReset).height(btnReset.getHeight() * btnReset.getScale()).width(btnReset.getWidth() * btnReset.getScale()).pad(15);
        tGameOver.add(btnExitGame).height(btnExitGame.getHeight() * btnExitGame.getScale()).width(btnExitGame.getWidth() * btnExitGame.getScale()).pad(15);
        wExit = new WindowGame(screen, getString("windowGOverTtl").toUpperCase());
        wExit.setBackground(getBotonMenu2Drawable());
        wExit.setWidth(getScreenWidth());
        wExit.setHeight(240);
        wExit.setOrigin(wExit.getWidth() / 2, wExit.getHeight() / 2);
        wExit.setPosition(getScreenWidth() / 2 - wExit.getWidth() / 2, getScreenHeight() / 2 - wExit.getHeight() / 2);
        wExit.setVisible(false);
        wExit.setResizable(false);
        wExit.row();
        wExit.add(tGameOver).padTop(-10);
        screen.getStage().addActor(wExit);
    }

    private void setWindowWinner() {
        btnNextLevel = new ButtonGame(getString("btnNextLevel"), .5f);
        btnNextLevel.addListener(new Listener() {
            @Override
            public void action() {
                exitGame();
            }
        });
        Table tGameOver = new Table();
        tGameOver.row().pad(10);
        tGameOver.add(btnNextLevel).height(btnNextLevel.getHeight() * btnNextLevel.getScale()).width(btnNextLevel.getWidth() * btnNextLevel.getScale()).pad(15);
        wWin = new WindowGame(screen, getString("windowWinnerTtl").toUpperCase());
        wWin.setBackground(getBotonMenu2Drawable());
        wWin.setWidth(getScreenWidth());
        wWin.setHeight(240);
        wWin.setOrigin(wWin.getWidth() / 2, wWin.getHeight() / 2);
        wWin.setPosition(getScreenWidth() / 2 - wWin.getWidth() / 2, getScreenHeight() / 2 - wWin.getHeight() / 2);
        wWin.setVisible(false);
        wWin.setResizable(false);
        wWin.row();
        wWin.add(tGameOver).padTop(-10);
        screen.getStage().addActor(wWin);
    }


    public void showWindowPause() {
        if (!wExit.isVisible() && !wWin.isVisible()) {
            ipauseBG.setVisible(true);
            ipauseBG.toFront();
            windowPause.setVisible(true);
            windowPause.toFront();
            windowTarget.setVisible(true);
            windowTarget.toFront();
            getTimer().stop();
            setPauseGame(true);
        }
    }

    public void hideWindowPause() {
        ipauseBG.setVisible(false);
        windowPause.setVisible(false);
        windowTarget.setVisible(false);
        getTimer().start();
        setPauseGame(false);
    }

    public void showWindowGameOver() {
        setPauseGame(true);
        getTimer().stop();
        ipauseBG.setVisible(true);
        ipauseBG.toFront();
        wExit.setVisible(true);
        wExit.toFront();
    }

    public void showWindowWinner() {
        setPauseGame(true);
        getTimer().stop();
        ipauseBG.setVisible(true);
        ipauseBG.toFront();
        wWin.setVisible(true);
        wWin.toFront();
    }

    public void actCounter() {
        lblCounter.setText(getNumberFormated(getCountEnemiesDispached()));
    }

    public final void exitGame() {
        scn.clearGroups();
        screen.getGame().setScreen(screen.getGame()._mapLevelScreen);
    }

    private void reset() {
        screen.getGame().setScreen(new LevelScreen(screen.getGame(), LevelScreen.level_selected, LevelScreen.world_selected));
    }
}
