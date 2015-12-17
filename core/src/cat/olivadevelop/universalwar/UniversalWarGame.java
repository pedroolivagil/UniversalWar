package cat.olivadevelop.universalwar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import java.net.InetAddress;
import java.security.MessageDigest;

import cat.olivadevelop.universalwar.screens.GameArcadeScreen;
import cat.olivadevelop.universalwar.screens.GameHistoryScreen;
import cat.olivadevelop.universalwar.screens.GameOverScreen;
import cat.olivadevelop.universalwar.screens.MainMenuScreen;
import cat.olivadevelop.universalwar.screens.ScoreScreen;
import cat.olivadevelop.universalwar.screens.SettingsScreen;
import cat.olivadevelop.universalwar.screens.SignUpScreen;
import cat.olivadevelop.universalwar.screens.SplashScreen;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.Publicidad;

public class UniversalWarGame extends Game {
    public Screen _splashScreen;
    public Screen _mainMenuScreen;
    public Screen _gameArcadeScreen;
    public Screen _gameHistoryScreen;
    public Screen _highScoreScreen;
    public Screen _gameOverScreen;
    public Screen _settingsScreen;
    public Screen _signUpScreen;
    public Publicidad actionResolver = null;
    private String idDevice;

    public UniversalWarGame(Publicidad aResolver, String idDevice) {
        setIdDevice(idDevice);
        actionResolver = aResolver;
        GameLogic.setScreenWidth(720);
    }

    public String getIdDevice() {
        return idDevice;
    }

    private void setIdDevice(String idDevice) {
        if (idDevice.equals("PC_ID")) {
            try {
                String id = InetAddress.getLocalHost().getHostName() + "_" + InetAddress.getLocalHost().getCanonicalHostName();
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.reset();
                md.update(id.getBytes());
                StringBuilder sb = new StringBuilder();
                byte bytes[] = md.digest();
                for (byte aByte : bytes) {
                    String hex = Integer.toHexString(0xff & aByte);
                    if (hex.length() == 1)
                        sb.append('0');
                    sb.append(hex);
                }
                this.idDevice = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                this.idDevice = idDevice;
            }
        } else {
            this.idDevice = idDevice;
        }
    }

    @Override
    public void create() {
        GameLogic.load();

        /*boolean loadADS = true;
        ConnectDB conn = new ConnectDB();
        ResultSet userConfig = conn.query("SELECT registered FROM registros WHERE email LIKE '" + getPrefs().getString("user", "NULL") + "';");
        try {
            if (userConfig.next()) {
                loadADS = false;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Gdx.app.log("ADS", "" + loadADS);
        GameLogic.setShowADS(loadADS);*/

        _mainMenuScreen = new MainMenuScreen(this);
        _splashScreen = new SplashScreen(this);
        _gameArcadeScreen = new GameArcadeScreen(this);
        _highScoreScreen = new ScoreScreen(this);
        _gameOverScreen = new GameOverScreen(this);
        _settingsScreen = new SettingsScreen(this);
        _gameHistoryScreen = new GameHistoryScreen(this);
        _signUpScreen = new SignUpScreen(this);

        setScreen(_splashScreen);
    }

    @Override
    public void dispose() {
        if (GameLogic.isShowADS()) {
            actionResolver.showOrLoadInterstital();
        }
        super.dispose();
    }
/*
    @Override
    public void handleHttpResponse(final Net.HttpResponse httpResponse) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                Gdx.app.log("Conexion HTTP", "Conexion correcta");
            }
        });
    }

    @Override
    public void failed(Throwable t) {

    }

    @Override
    public void cancelled() {

    }*/
}
