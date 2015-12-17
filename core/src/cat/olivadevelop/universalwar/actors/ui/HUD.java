package cat.olivadevelop.universalwar.actors.ui;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Timer;

import cat.olivadevelop.universalwar.actors.allied.Allied;
import cat.olivadevelop.universalwar.actors.bullets.Bullet;
import cat.olivadevelop.universalwar.actors.bullets.MegaBullet;
import cat.olivadevelop.universalwar.actors.bullets.SuperBullet;
import cat.olivadevelop.universalwar.actors.enemies.Enemy;
import cat.olivadevelop.universalwar.actors.shields.Shield;
import cat.olivadevelop.universalwar.actors.shields.ShieldBronze;
import cat.olivadevelop.universalwar.tools.ActorGame;
import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.GeneralScreen;
import cat.olivadevelop.universalwar.tools.ImageGame;
import cat.olivadevelop.universalwar.tools.Listener;

import static cat.olivadevelop.universalwar.tools.GameLogic.getCountEnemiesDispached;
import static cat.olivadevelop.universalwar.tools.GameLogic.getEnemy;
import static cat.olivadevelop.universalwar.tools.GameLogic.getNumberFormated;
import static cat.olivadevelop.universalwar.tools.GameLogic.getPowers;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScoreGame;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScreenHeight;
import static cat.olivadevelop.universalwar.tools.GameLogic.getScreenWidth;
import static cat.olivadevelop.universalwar.tools.GameLogic.getSkin;
import static cat.olivadevelop.universalwar.tools.GameLogic.getString;
import static cat.olivadevelop.universalwar.tools.GameLogic.getTimeGame;
import static cat.olivadevelop.universalwar.tools.GameLogic.getUi;
import static cat.olivadevelop.universalwar.tools.GameLogic.setTimeGame;

/**
 * Created by OlivaDevelop on 18/04/2015.
 */
public class HUD extends ActorGame {

    public static int tableBottomHeight = 60;
    private boolean powerShieldButtonActive;
    private boolean powerBombButtonActive;
    private boolean powerMultiBombButtonActive;
    private int temporal_score = 0;
    private int divisor = 10;
    private int healthMax = 400;
    private int shieldMax = 400;
    private int timeMin;
    private int timeSec;
    private float heightBarHealth = .9f;
    private float maxHealth = healthMax + (healthMax * (1 - heightBarHealth)) - 10;
    private float heightBarShield = .9f;
    private float maxShield = shieldMax + (shieldMax * (1 - heightBarShield)) - 10;
    private float textLabelSize = .5f;
    private double timeShieldButton;
    private double timeBombButton;
    private double timeMultiBombButton;
    private Group health;
    private Group shield;
    private Group buttonShield;
    private Group buttonBomb;
    private Group buttonMultiBomb;
    private ImageGame healthBar;
    private ImageGame healthBg;
    private ImageGame imageShield;
    private ImageGame imageBomb;
    private ImageGame imageMultiBomb;
    private ImageGame shieldBar;
    private ImageGame shieldBarBg;
    private Label labelInfoEnemiesDefeat;
    private Label labelInfoEnemies;
    private Label lShield;
    private Label lLives;
    private Label score;
    private Label time;
    private Table tableTop;
    private Table tableBottom;
    private Timer timer;

    public HUD(final GeneralScreen screen) {
        super(screen);
        timeMin = 0;
        timeSec = 0;
        timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                setTimeGame(getTimeGame() + 1);
                actTimeGame();
            }
        }, 0, 1);
        timer.start();
        // puntuacion
        score = new Label("", getSkin());
        score.setFontScale(textLabelSize * 1.50f);
        score.setColor(ColorGame.GREEN_POINTS);
        // tiempo
        time = new Label("", getSkin());
        time.setFontScale(textLabelSize - .2f);
        time.setAlignment(Align.bottom);
        time.setColor(ColorGame.BLUE_TIME);
        // barras de vida y escudo
        healthBg = new ImageGame(new NinePatch(getUi("bg_bar_blue"), 18, 18, 18, 18));
        healthBar = new ImageGame(new NinePatch(getUi("bar_blue"), 18, 18, 18, 18));
        shieldBarBg = new ImageGame(new NinePatch(getUi("bg_bar_red"), 18, 18, 18, 18));
        shieldBar = new ImageGame(new NinePatch(getUi("bar_red"), 18, 18, 18, 18));
        // botones centrales
        buttons();
        tableTop();
        tableBottom();
        infoEnemies();
        addListeners();
    }

    private void addListeners() {
        setPowerShieldButtonActive(true);
        setPowerBombButtonActive(true);

        buttonShield.addListener(new Listener() {
            @Override
            public void action() {
                if (isPowerShieldButtonActive()) {
                    if (screen._groupShields.hasChildren()) {
                        if (Shield.getImpacts() != Shield.getMaxImpacts()) {
                            setPowerShieldButtonActive(false);
                            timeShieldButton = getTimeGame();
                            Shield.reset();
                        }
                    } else {
                        screen._groupShields.addActor(new ShieldBronze(screen));
                        setPowerShieldButtonActive(false);
                        timeShieldButton = getTimeGame();
                    }
                }
                super.action();
            }
        });
        buttonBomb.addListener(new Listener() {
            @Override
            public void action() {
                if (isPowerBombButtonActive()) {
                    screen._stage.addActor(new SuperBullet(screen, screen._groupAllied.getChildren().first().getX() + (screen._groupAllied.getChildren().first().getWidth() / 2) + getWidth() / 2 - 5, screen._groupAllied.getChildren().first().getY() + 2, Bullet.BULLET_UP));
                    setPowerBombButtonActive(false);
                    timeBombButton = getTimeGame();
                }
                super.action();
            }
        });

        buttonMultiBomb.addListener(new Listener() {
            @Override
            public void action() {
                if (isPowerMultiBombButtonActive()) {
                    screen._stage.addActor(new MegaBullet(screen, screen._groupAllied.getChildren().first().getX() - 100, screen._groupAllied.getChildren().first().getY() + 2, Bullet.BULLET_UP));
                    screen._stage.addActor(new MegaBullet(screen, screen._groupAllied.getChildren().first().getX() + (screen._groupAllied.getChildren().first().getWidth() / 2) + getWidth() / 2 - 5, screen._groupAllied.getChildren().first().getY() + 2, Bullet.BULLET_UP));
                    screen._stage.addActor(new MegaBullet(screen, screen._groupAllied.getChildren().first().getX() + 140 + (screen._groupAllied.getChildren().first().getWidth() / 2) + getWidth() / 2 - 5, screen._groupAllied.getChildren().first().getY() + 2, Bullet.BULLET_UP));
                    setPowerMultiBombButtonActive(false);
                    timeMultiBombButton = getTimeGame();
                }
                super.action();
            }
        });
    }

    public void buttons() {
        buttonBomb = new Group();
        imageBomb = new ImageGame(getPowers("powerupRed_bomb"));
        imageBomb.setPosition(30, 15);
        imageBomb.setScale(1.5f);
        buttonBomb.setWidth(60);
        buttonBomb.setHeight(60);
        buttonBomb.addActor(imageBomb);

        buttonMultiBomb = new Group();
        imageMultiBomb = new ImageGame(getPowers("powerupRed_multibomb"));
        imageMultiBomb.setPosition(25, 15);
        imageMultiBomb.setScale(1.5f);
        buttonMultiBomb.setWidth(60);
        buttonMultiBomb.setHeight(60);
        buttonMultiBomb.addActor(imageMultiBomb);

        buttonShield = new Group();
        imageShield = new ImageGame(getPowers("powerupRed_shield"));
        imageShield.setPosition(20, 15);
        imageShield.setScale(1.5f);
        buttonShield.setWidth(60);
        buttonShield.setHeight(60);
        buttonShield.addActor(imageShield);
    }

    private void loadHealthBar() {
        lLives = new Label(getString("lLives").toUpperCase(), getSkin());
        lLives.setFontScale(.7f);
        lLives.setColor(ColorGame.TURQUESE);
        lLives.setAlignment(Align.center);
        health = new Group();
        healthBg.setWidth(healthMax);
        healthBg.setHeight(100);
        healthBar.setWidth(maxHealth);
        healthBar.setHeight(96);
        healthBar.setScale(heightBarHealth);

        healthBg.setPosition(0, 0);
        healthBar.setPosition(7, 7);

        health.addActor(healthBg);
        health.addActor(healthBar);
        health.addActor(lLives);

        health.setWidth(healthBg.getWidth());
        health.setHeight(healthBg.getHeight());
        health.setScale(.5f);
        health.setOrigin(health.getWidth() / 2, health.getHeight() / 2);
        lLives.setWidth(health.getWidth());
        lLives.setPosition(health.getX(), health.getY() - 11);

        healthBg.toFront();
        healthBar.toFront();
        lLives.toFront();
    }

    private void loadShieldBar() {
        lShield = new Label(getString("lShield").toUpperCase(), getSkin());
        lShield.setFontScale(.7f);
        lShield.setColor(ColorGame.DARK_RED);
        lShield.setAlignment(Align.center);
        shield = new Group();
        shieldBarBg.setWidth(healthMax);
        shieldBarBg.setHeight(100);
        shieldBar.setWidth(maxHealth);
        shieldBar.setHeight(96);
        shieldBar.setScale(heightBarHealth);

        shieldBarBg.setPosition(0, 0);
        shieldBar.setPosition(7, 7);

        shield.addActor(shieldBarBg);
        shield.addActor(shieldBar);
        shield.addActor(lShield);

        shield.setWidth(shieldBarBg.getWidth());
        shield.setHeight(shieldBar.getHeight());
        shield.setScale(.5f);
        shield.setOrigin(shield.getWidth() / 2, shield.getHeight() / 2);
        lShield.setWidth(shield.getWidth());
        lShield.setPosition(shield.getX(), shield.getY() - 11);

        shieldBarBg.toFront();
        shieldBar.toFront();
        lShield.toFront();
    }

    private void infoEnemies() {
        Group info = new Group();
        ImageGame imageInfoEnemies = new ImageGame(getEnemy(Enemy.BASIC[MathUtils.random(0, Enemy.BASIC.length - 1)]));
        labelInfoEnemies = new Label("", getSkin());
        ImageGame imageInfoEnemiesDefeat = new ImageGame(getEnemy(Enemy.ADVANCED[MathUtils.random(0, Enemy.ADVANCED.length - 1)]));
        ImageGame imageDelEnemiesDefeat = new ImageGame(getUi("close"));
        labelInfoEnemiesDefeat = new Label("", getSkin());

        info.setScale(.3f);
        imageDelEnemiesDefeat.setScale(.7f);

        info.setPosition(5, getScreenHeight() - 110);
        labelInfoEnemiesDefeat.setPosition(135, 20);
        imageInfoEnemies.setY(-135);
        labelInfoEnemies.setPosition(135, -115);
        imageDelEnemiesDefeat.setPosition(-17, -20);

        info.addActor(imageInfoEnemies);
        info.addActor(labelInfoEnemies);
        info.addActor(imageInfoEnemiesDefeat);
        info.addActor(labelInfoEnemiesDefeat);
        info.addActor(imageDelEnemiesDefeat);

        screen._stage.addActor(info);
    }

    public void actTimeGame() {
        if (getTimeGame() % 60 == 0) {
            setTimeMin(getTimeMin() + 1);
            setTimeSec(0);
        } else {
            setTimeSec(getTimeSec() + 1);
        }
    }

    public Label getTime() {
        return time;
    }

    public Timer getTimer() {
        return timer;
    }

    private void tableTop() {
        tableTop = new Table();
        tableTop.setBackground(new NinePatchDrawable(new NinePatch(getUi("botonMenu2"), 7, 7, 7, 7)));
        tableTop.bottom();
        tableTop.setY(getScreenHeight() - 77f);
        tableTop.setWidth(getScreenWidth());
        tableTop.setHeight(80f);
        tableTop.row().expandX();
        tableTop.add(score).center().padBottom(-50);
        tableTop.row().expandX();
        tableTop.add(getTime()).center().padBottom(-50);
        screen._stage.addActor(tableTop);
        getTime().setScale(.5f);
    }

    private void tableBottom() {
        loadHealthBar();
        loadShieldBar();
        tableBottom = new Table();
        tableBottom.setBackground(new NinePatchDrawable(new NinePatch(getUi("botonMenu2"), 7, 7, 7, 7)));
        tableBottom.bottom();
        tableBottom.setY(-20f);
        tableBottom.setWidth(getScreenWidth());
        tableBottom.setHeight(tableBottomHeight + 40);
        tableBottom.row();
        tableBottom.add(health).expandX();
        tableBottom.add(buttonBomb).width(80);
        tableBottom.add(buttonMultiBomb).width(80);
        tableBottom.add(buttonShield).width(80);
        tableBottom.add(shield).expandX();
        screen._stage.addActor(tableBottom);
        //tableBottom.debugAll();
    }

    public void actPoints() {
        temporal_score = temporal_score + (getScoreGame() - temporal_score) / divisor;
        if (((getScoreGame() - temporal_score) < divisor) && ((getScoreGame() - temporal_score) > 0)) {
            temporal_score++;
        }
        score.setText(getNumberFormated(temporal_score));
    }

    public void actShieldBar() {
        if (Shield.getImpacts() <= 0) {
            shieldBar.toBack();
        } else {
            shieldBar.toFront();
            lShield.toFront();
            shieldBar.setWidth((maxShield / Shield.getMaxImpacts()) * Shield.getImpacts());
        }
    }

    public void actHealthBar() {
        if (Allied.getHealth() <= 0) {
            healthBar.toBack();
        } else {
            healthBar.toFront();
            lLives.toFront();
            healthBar.setWidth((maxHealth / Allied.getMaxHealth()) * Allied.getHealth());
        }
    }

    public void actTime() {
        getTime().setText(((getTimeMin() < 10) ? "0" + getTimeMin() : "" + getTimeMin()) + ":" + ((getTimeSec() < 10) ? "0" + getTimeSec() : "" + getTimeSec()));
    }

    private void actGroupInfo() {
        labelInfoEnemies.setText(String.valueOf(getNumberFormated(screen._groupEnemy.getChildren().size)));
        labelInfoEnemiesDefeat.setText(String.valueOf(getNumberFormated(getCountEnemiesDispached())));
    }

    public int getTimeMin() {
        return timeMin;
    }

    public void setTimeMin(int timeMin) {
        this.timeMin = timeMin;
    }

    public int getTimeSec() {
        return timeSec;
    }

    public void setTimeSec(int timeSec) {
        this.timeSec = timeSec;
    }

    public boolean isPowerShieldButtonActive() {
        return powerShieldButtonActive;
    }

    public void setPowerShieldButtonActive(boolean b) {
        this.powerShieldButtonActive = b;
        buttonShield.clearActions();
        if (!b) {
            buttonShield.setColor(.2f, .2f, .2f, .5f);
        } else {
            buttonShield.setColor(1, 1, 1, 1);
        }
    }

    public boolean isPowerBombButtonActive() {
        return powerBombButtonActive;
    }

    public void setPowerBombButtonActive(boolean b) {
        this.powerBombButtonActive = b;
        buttonBomb.clearActions();
        if (!b) {
            buttonBomb.setColor(.2f, .2f, .2f, .5f);
        } else {
            buttonBomb.setColor(1, 1, 1, 1);
        }
    }

    public boolean isPowerMultiBombButtonActive() {
        return powerMultiBombButtonActive;
    }

    public void setPowerMultiBombButtonActive(boolean b) {
        this.powerMultiBombButtonActive = b;
        buttonMultiBomb.clearActions();
        if (!b) {
            buttonMultiBomb.setColor(.2f, .2f, .2f, .5f);
        } else {
            buttonMultiBomb.setColor(1, 1, 1, 1);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        actPoints();
        actTime();
        actGroupInfo();
        actHealthBar();
        actShieldBar();

        if (timeShieldButton == getTimeGame() - 30) {
            setPowerShieldButtonActive(true);
        }
        if (timeBombButton == getTimeGame() - 60) {
            setPowerBombButtonActive(true);
        }
        if (timeMultiBombButton == getTimeGame() - 10) {
            setPowerMultiBombButtonActive(true);
        }

        tableBottom.toFront();
        tableTop.toFront();
    }
}
