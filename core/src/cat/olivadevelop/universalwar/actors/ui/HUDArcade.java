package cat.olivadevelop.universalwar.actors.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;

import cat.olivadevelop.universalwar.actors.allied.Allied;
import cat.olivadevelop.universalwar.actors.allied.Genesis;
import cat.olivadevelop.universalwar.actors.bullets.Bullet;
import cat.olivadevelop.universalwar.actors.bullets.BulletOrange;
import cat.olivadevelop.universalwar.actors.bullets.MegaBullet;
import cat.olivadevelop.universalwar.actors.bullets.SuperBullet;
import cat.olivadevelop.universalwar.actors.shields.Shield;
import cat.olivadevelop.universalwar.actors.shields.ShieldBronze;
import cat.olivadevelop.universalwar.tools.ActorGame;
import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.GeneralScreen;
import cat.olivadevelop.universalwar.tools.ImageGame;
import cat.olivadevelop.universalwar.tools.LabelGame;
import cat.olivadevelop.universalwar.tools.Listener;

import static cat.olivadevelop.universalwar.tools.GameLogic.getBotonMenu2Drawable;
import static cat.olivadevelop.universalwar.tools.GameLogic.getButtons;
import static cat.olivadevelop.universalwar.tools.GameLogic.getNumberFormated;
import static cat.olivadevelop.universalwar.tools.GameLogic.getPowers;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScoreGame;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScreenHeight;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScreenWidth;
import static cat.olivadevelop.universalwar.tools.GameLogic.getSkin;
import static cat.olivadevelop.universalwar.tools.GameLogic.getString;
import static cat.olivadevelop.universalwar.tools.GameLogic.getTimeGame;
import static cat.olivadevelop.universalwar.tools.GameLogic.getTimeMin;
import static cat.olivadevelop.universalwar.tools.GameLogic.getTimeSec;
import static cat.olivadevelop.universalwar.tools.GameLogic.getUi;
import static cat.olivadevelop.universalwar.tools.GameLogic.setTimeDefault;

/**
 * Created by OlivaDevelop on 18/04/2015.
 */
public class HUDArcade extends ActorGame {

    private int time = 1;
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

    public HUDArcade(final GeneralScreen screen) {
        super(screen);
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
        i.setWidth(getScreenWidth());
        i.setHeight(getScreenHeight() / 2 - 50);
        i.addListener(new Listener() {
            @Override
            public void action() {
                Allied a = (Allied) screen._groupAllied.getChildren().first();
                a.shoot();
            }
        });

        tShoot = new Table(getSkin());
        tShoot.setWidth(getScreenWidth());
        tShoot.setHeight(getScreenHeight() / 2 - 50);
        tShoot.setY(tBottom.getX() + tBottom.getHeight() - 10);
        tShoot.add(i).width(getScreenWidth()).height(getScreenHeight() / 2 - 50);
        screen._stage.addActor(tShoot);
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

        sBarBg = new ImageGame(new NinePatch(getUi("bg_bar_red"), 18, 18, 18, 18));
        sBar = new ImageGame(new NinePatch(getUi("bar_red"), 18, 18, 18, 18));
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
        iHelp = new ImageGame(getPowers("powerupRed_star"));
        iBurst = new ImageGame(getPowers("powerupRed_star"));

        int pad = 10;
        tbBtns = new Table(getSkin());
        tbBtns.setBackground(new TextureRegionDrawable(new TextureRegion(getButtons())));
        //tbBtns.setWidth(getScreenWidth());
        tbBtns.row().height(barsHeight);
        tbBtns.add(iHelp).width(50).height(50).padLeft(pad).padRight(pad);
        tbBtns.add(iHealth).width(50).height(50).padLeft(pad).padRight(pad);
        tbBtns.add(iMissil).width(50).height(50).padLeft(pad).padRight(pad);
        tbBtns.add(iSuperMissil).width(50).height(50).padLeft(pad).padRight(pad);
        tbBtns.add(iShield).width(50).height(50).padLeft(pad).padRight(pad);
        tbBtns.add(iBurst).width(50).height(50).padLeft(pad).padRight(pad);

        anim = new Animation(1 / 13f, getSprites(1, 8));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        elapsedTime += Gdx.graphics.getDeltaTime();
        tbBtns.setBackground(new TextureRegionDrawable(anim.getKeyFrame(elapsedTime, true)));
    }

    private TextureRegion[] getSprites(int cols, int rows) {
        TextureRegion[][] tmp = TextureRegion.split(getButtons(), getButtons().getWidth() / cols, getButtons().getHeight() / rows);
        TextureRegion[] Frames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Frames[index++] = tmp[i][j];
            }
        }
        return Frames;
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

    public boolean isBoolShield() {
        return boolShield;
    }

    public void setBoolShield(boolean boolShield) {
        this.boolShield = boolShield;
        if (!boolShield) {
            iShield.setColor(.5f, .5f, .5f, .5f);
        } else {
            iShield.setColor(1, 1, 1, 2);
        }
    }

    public boolean isBoolHealth() {
        return boolHealth;
    }

    public void setBoolHealth(boolean boolHealth) {
        this.boolHealth = boolHealth;
        if (!boolHealth) {
            iHealth.setColor(.5f, .5f, .5f, .5f);
        } else {
            iHealth.setColor(1, 1, 1, 2);
        }
    }

    public boolean isBoolMisile() {
        return boolMisile;
    }

    public void setBoolMisile(boolean boolMisile) {
        this.boolMisile = boolMisile;
        if (!boolMisile) {
            iMissil.setColor(.5f, .5f, .5f, .5f);
        } else {
            iMissil.setColor(1, 1, 1, 2);
        }
    }

    public boolean isBoolSuperMisile() {
        return boolSuperMisile;
    }

    public void setBoolSuperMisile(boolean boolSuperMisile) {
        this.boolSuperMisile = boolSuperMisile;
        if (!boolSuperMisile) {
            iSuperMissil.setColor(.5f, .5f, .5f, .5f);
        } else {
            iSuperMissil.setColor(1, 1, 1, 2);
        }
    }

    public boolean isBoolHelp() {
        return boolHelp;
    }

    public void setBoolHelp(boolean boolHelp) {
        this.boolHelp = boolHelp;
        if (!boolHelp) {
            iHelp.setColor(.5f, .5f, .5f, .5f);
        } else {
            iHelp.setColor(1, 1, 1, 2);
        }
    }

    public boolean isBoolBurst() {
        return boolBurst;
    }

    public void setBoolBurst(boolean boolBurst) {
        this.boolBurst = boolBurst;
        if (!boolBurst) {
            iBurst.setColor(.5f, .5f, .5f, .5f);
        } else {
            iBurst.setColor(1, 1, 1, 2);
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
                        setBoolShield(false);
                        timeShield = getTimeGame();
                    } else {
                        screen._groupShields.addActor(new ShieldBronze(screen));
                    }
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
        if (timeMisile == getTimeGame() - time) {
            setBoolMisile(true);
        }
        if (timeBurst == getTimeGame() - time) {
            setBoolBurst(true);
        }
        if (timeHealth == getTimeGame() - time) {
            setBoolHealth(true);
        }
        if (timeHelp == getTimeGame() - time) {
            setBoolHelp(true);
        }
        if (timeSuperMisile == getTimeGame() - time) {
            setBoolSuperMisile(true);
        }
        if (timeShield == getTimeGame() - time) {
            setBoolShield(true);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        actLabelTime();
        actLabelScore();
        toFront();
        tTop.toFront();
        tBottom.toFront();
        actHealthBar();
        actShieldBar();
        checkButtons();
        tShoot.toFront();
        tbBtns.toFront();
    }
}
