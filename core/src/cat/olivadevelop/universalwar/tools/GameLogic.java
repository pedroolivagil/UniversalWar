package cat.olivadevelop.universalwar.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Timer;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.Locale;

/**
 * Created by OlivaDevelop on 26/05/2015.
 */
public abstract class GameLogic implements Disposable {

    public final static String prefsName = "localPreferences";
    public final static String COOKIE_KEY = "5d77314492c1e10daa3a2366ca1b7103578d856c3a5c89e30b879ad4";
    // Textures
    public static String COLOR_BASIC = "basic";
    public static String COLOR_BWHITE = "blackandwhite";
    private static Preferences prefs;
    // Bundle
    private static I18NBundle bundle;
    // Variables
    private static String[] serverData = new String[]{"hl317.dinaserver.com", "ps_cc26d4a67a8ef5c2", "ps-cc26d4a67a8ef", "vNI3ihYN"};
    private static Timer timer;
    private static boolean pauseGame;
    private static boolean showADS;
    private static double timeGame;
    private static int screenWidth;
    private static int screenHeight;
    private static int scoreGame;
    private static int livesGame;
    private static int timeMin;
    private static int timeSec;
    private static int countEnemiesDispached;
    private static int maxEnemiesIntoGroup;
    // Skin
    private static Skin skin;
    private static Skin skin_mini;
    private static TextureAtlas ui;
    private static TextureAtlas enemy;
    private static TextureAtlas boss;
    private static TextureAtlas player;
    private static TextureAtlas powers;
    private static TextureAtlas planets;
    private static TextureAtlas meteors;
    private static TextureAtlas fires;
    private static Texture medExpTex;
    private static Texture buttons;
    private static Texture animHealth;
    // Sounds
    private static Sound SOUND_SHOOT_NORMAL;
    private static Sound SOUND_SHOOT_LASER;
    private static Sound SOUND_SHOOT_PLASMA;
    private static Sound SOUND_EXPLODE;
    //private static Sound SOUND_AMBIENT;

    // User Vars
    private static ResultSet userQuery;
    private static int id_obj_health = 20;

    public static void loadUI() {
        // Textures
        /*
         * Guardar el String del tema para cada usuario en las preferencia del juego
         *  - Basico (basic);
         *  - Blanco y Negro (blacandwhite);
         */
        ui = new TextureAtlas("textures/" + getPrefs().getString("theme", COLOR_BASIC) + "/ui.atlas");
        enemy = new TextureAtlas("textures/" + getPrefs().getString("theme", COLOR_BASIC) + "/enemy_ship.atlas");
        boss = new TextureAtlas("textures/" + getPrefs().getString("theme", COLOR_BASIC) + "/boss.atlas");
        player = new TextureAtlas("textures/" + getPrefs().getString("theme", COLOR_BASIC) + "/player_ship.atlas");
        powers = new TextureAtlas("textures/" + getPrefs().getString("theme", COLOR_BASIC) + "/powers.atlas");
        planets = new TextureAtlas("textures/" + getPrefs().getString("theme", COLOR_BASIC) + "/planets.atlas");
        fires = new TextureAtlas("textures/" + getPrefs().getString("theme", COLOR_BASIC) + "/fires.atlas");
        meteors = new TextureAtlas("textures/" + getPrefs().getString("theme", COLOR_BASIC) + "/meteors.atlas");
        medExpTex = new Texture("textures/" + getPrefs().getString("theme", COLOR_BASIC) + "/other/exp_medium.png");
        buttons = new Texture("textures/" + getPrefs().getString("theme", COLOR_BASIC) + "/other/buttons.png");
        animHealth = new Texture("textures/" + getPrefs().getString("theme", COLOR_BASIC) + "/other/healthUp.png");
        // Sounds
        SOUND_SHOOT_NORMAL = Gdx.audio.newSound(Gdx.files.internal(("sounds/shoot_normal.mp3")));
        SOUND_SHOOT_LASER = Gdx.audio.newSound(Gdx.files.internal(("sounds/shoot_laser.mp3")));
        SOUND_SHOOT_PLASMA = Gdx.audio.newSound(Gdx.files.internal(("sounds/shoot_plasma.mp3")));
        SOUND_EXPLODE = Gdx.audio.newSound(Gdx.files.internal(("sounds/explosion_spaceship.mp3")));
        //SOUND_AMBIENT = Gdx.audio.newSound(Gdx.files.internal(("sounds/ambient.mp3")));
        // Preferencias
        if (getPrefs().getBoolean("localReg", true)) {
            getPrefs().putBoolean("localReg", true);
        }
        if (getPrefs().getString("user", "").equals("")) {
            getPrefs().putString("user", "");
        }
        getPrefs().flush();
    }

    public static void load() {
        // Preferencias
        prefs = Gdx.app.getPreferences(GameLogic.prefsName);
        // Bundle
        bundle = I18NBundle.createBundle(Gdx.files.internal("i18n/MyBundle"), Locale.getDefault());
        // Skin
        skin = new Skin(Gdx.files.internal("skin/basic/uiskin.json"));
        skin_mini = new Skin(Gdx.files.internal("skin/mini/uiskin.json"));
        ColorGame.initColorGame();
    }

    public static boolean isShowADS() {
        return showADS;
    }

    public static void setShowADS(boolean showADS) {
        GameLogic.showADS = showADS;
        if (showADS) {
            GameLogic.setScreenHeight(1180);
        } else {
            GameLogic.setScreenHeight(1280);
        }
    }

    public static String getNumberFormated(double num) {
        String n = String.valueOf(num);
        StringBuffer sb = new StringBuffer();
        for (int z = n.length(); z > 0; z--) {
            if (z % 3 == 0 && z != n.length()) {
                sb.append(".");
            }
            sb.append(n.charAt(n.length() - z));
        }
        return sb.toString();
    }

    public static String getNumberFormated(float num) {
        String n = String.valueOf(num);
        StringBuffer sb = new StringBuffer();
        for (int z = n.length(); z > 0; z--) {
            if (z % 3 == 0 && z != n.length()) {
                sb.append(".");
            }
            sb.append(n.charAt(n.length() - z));
        }
        return sb.toString();
    }

    public static String getNumberFormated(int num) {
        String n = String.valueOf(num);
        StringBuffer sb = new StringBuffer();
        for (int z = n.length(); z > 0; z--) {
            if (z % 3 == 0 && z != n.length()) {
                sb.append(".");
            }
            sb.append(n.charAt(n.length() - z));
        }
        return sb.toString();
    }

    public static float resizeImg(float anchoOriginal, float altoOriginal, float anchoDeseado) {
        return (anchoDeseado * altoOriginal) / anchoOriginal;
    }

    public static boolean isAudioOn() {
        return getPrefs().getBoolean("audio", true);
    }

    public static void setAudioOn(boolean b) {
        getPrefs().putBoolean("audio", b);
        getPrefs().flush();
    }

    public static void setTheme(String theme) {
        getPrefs().putString("theme", theme);
        getPrefs().flush();
    }

    // Bundle
    public static String getString(String key) {
        return bundle.format(key);
    }

    // Textures
    public static Skin getSkin() {
        return skin;
    }

    public static Skin getSkin_mini() {
        return skin_mini;
    }

    public static TextureRegion getUi(String region) {
        return ui.findRegion(region);
    }

    public static TextureRegion getEnemy(String region) {
        return enemy.findRegion(region);
    }
    public static TextureRegion getBoss(String region) {
        return boss.findRegion(region);
    }

    public static TextureRegion getPlayer(String region) {
        return player.findRegion(region);
    }

    public static TextureRegion getPowers(String region) {
        return powers.findRegion(region);
    }

    public static TextureRegion getPlanets(String region) {
        return planets.findRegion(region);
    }

    public static TextureRegion getMeteors(String region) {
        return meteors.findRegion(region);
    }

    public static TextureRegion getFires(String region) {
        return fires.findRegion(region);
    }

    public static Texture getExplosionMedium() {
        return medExpTex;
    }

    public static Texture getButtons() {
        return buttons;
    }

    public static Texture getAnimHealth() {
        return animHealth;
    }

    public static NinePatchDrawable getBotonMenuDrawable() {
        return new NinePatchDrawable(new NinePatch(getUi("botonMenu"), 7, 7, 7, 7));
    }

    public static NinePatchDrawable getBotonMenu2Drawable() {
        return new NinePatchDrawable(new NinePatch(getUi("botonMenu2"), 7, 7, 7, 7));
    }

    public static TextureRegion[] getSprites(int cols, int rows, Texture t) {
        TextureRegion[][] tmp = TextureRegion.split(t, t.getWidth() / cols, t.getHeight() / rows);
        TextureRegion[] Frames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Frames[index++] = tmp[i][j];
            }
        }
        return Frames;
    }

    // Sounds
    public static Sound getSoundShootNormal() {
        return SOUND_SHOOT_NORMAL;
    }

    public static Sound getSoundShootLaser() {
        return SOUND_SHOOT_LASER;
    }

    public static Sound getSoundShootPlasma() {
        return SOUND_SHOOT_PLASMA;
    }

    public static Sound getSoundExplode() {
        return SOUND_EXPLODE;
    }

    /*public static Sound getSoundAmbient() {
        return SOUND_AMBIENT;
    }*/
    public static int getUserID() {
        return getPrefs().getInteger("userID", 0);
    }

    // Variables
    public static void setUserID(int userID) {
        getPrefs().putInteger("userID", userID);
        getPrefs().flush();
    }

    public static String getUserName() {
        return getPrefs().getString("userName", "");
    }

    // Variables
    public static void setUserName(String userName) {
        getPrefs().putString("userName", userName);
        getPrefs().flush();
    }

    public static String getUserLast() {
        return getPrefs().getString("userLast", "");
    }

    // Variables
    public static void setUserLast(String userLast) {
        getPrefs().putString("userLast", userLast);
        getPrefs().flush();
    }

    public static String getUserMail() {
        return getPrefs().getString("userMail", "");
    }

    // Variables
    public static void setUserMail(String userMail) {
        getPrefs().putString("userMail", userMail);
        getPrefs().flush();
    }


    public static int getId_obj_health() {
        return id_obj_health;
    }

    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encrypt(String passwd) {
        String pass = COOKIE_KEY + "" + passwd;
        return getMD5(pass);
    }

    public static String getServerData(int pos) {
        return serverData[pos];
    }

    public static int getScoreGame() {
        return scoreGame;
    }

    public static void setScoreGame(int scoreGame) {
        GameLogic.scoreGame = scoreGame;
    }

    public static int getLivesGame() {
        return livesGame;
    }

    public static void setLivesGame(int livesGame) {
        GameLogic.livesGame = livesGame;
    }

    /* Vars Time*/
    public static void setTimeDefault() {
        GameLogic.timeMin = 0;
        GameLogic.timeSec = 0;
        timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                setTimeGame(getTimeGame() + 1);
                actTimeGame();
            }
        }, 0, 1);
        timer.start();
    }

    public static Timer getTimer() {
        return timer;
    }

    public static double getTimeGame() {
        return timeGame;
    }

    public static void setTimeGame(double timeGame) {
        GameLogic.timeGame = timeGame;
    }

    public static void actTimeGame() {
        if (getTimeGame() % 60 == 0) {
            setTimeMin(getTimeMin() + 1);
            setTimeSec(0);
        } else {
            setTimeSec(getTimeSec() + 1);
        }
    }

    public static int getTimeMin() {
        return GameLogic.timeMin;
    }

    public static void setTimeMin(int timeMin) {
        GameLogic.timeMin = timeMin;
    }

    public static int getTimeSec() {
        return GameLogic.timeSec;
    }

    public static void setTimeSec(int timeSec) {
        GameLogic.timeSec = timeSec;
    }

    /* Vars */
    public static void addToScore(int points) {
        GameLogic.scoreGame += points;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static void setScreenWidth(int screenWidth) {
        GameLogic.screenWidth = screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static void setScreenHeight(int screenHeight) {
        GameLogic.screenHeight = screenHeight;
    }

    public static int getCountEnemiesDispached() {
        return countEnemiesDispached;
    }

    public static void setCountEnemiesDispached(int countEnemiesDispached) {
        GameLogic.countEnemiesDispached = countEnemiesDispached;
    }

    public static void addCountEnemiesDispached() {
        GameLogic.countEnemiesDispached++;
    }

    public static void setPauseGame(boolean pauseGame) {
        GameLogic.pauseGame = pauseGame;
    }

    public static boolean isPause() {
        return pauseGame;
    }

    public static int getMaxEnemiesIntoGroup() {
        return maxEnemiesIntoGroup;
    }

    public static void setMaxEnemiesIntoGroup(int maxEnemiesIntoGroup) {
        GameLogic.maxEnemiesIntoGroup = maxEnemiesIntoGroup;
    }

    public static void addMaxEnemiesIntoGroup() {
        setMaxEnemiesIntoGroup(getMaxEnemiesIntoGroup() + 3);
    }

    public static Preferences getPrefs() {
        return prefs;
    }

    @Override
    public void dispose() {
        skin.dispose();
        skin_mini.dispose();

        ui.dispose();
        player.dispose();
        enemy.dispose();
        powers.dispose();
        planets.dispose();
        fires.dispose();
        meteors.dispose();

        medExpTex.dispose();

        SOUND_SHOOT_NORMAL.dispose();
        SOUND_SHOOT_LASER.dispose();
        SOUND_SHOOT_PLASMA.dispose();
        SOUND_EXPLODE.dispose();
        //SOUND_AMBIENT.dispose();
        getTimer().clear();
    }
}
