package cat.olivadevelop.universalwar.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import cat.olivadevelop.universalwar.UniversalWarGame;
import cat.olivadevelop.universalwar.tools.GameLogic;
public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = (int) GameLogic.resizeImg(720, 1280, 550);
        config.width = 550;
        config.y = 10;
        config.addIcon("textures/logo.png", Files.FileType.Internal);
        config.title = "OlivaDevelop Games - Universal War";
        config.useGL30 = false;
        GameLogic.setScreenHeight(1280);
        new LwjglApplication(new UniversalWarGame(new PublicidadDesktop(), "PC_ID"), config);
    }
}
