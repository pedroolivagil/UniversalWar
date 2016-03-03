package cat.olivadevelop.universalwar.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class ColorGame extends Color {
    public static Color ESMERALDA_LIGHT;
    public static Color ESMERALDA;
    public static Color RED;
    public static Color ORANGE;
    public static Color GREEN;
    public static Color GREEN_SAD;
    public static Color GREEN_POINTS;
    public static Color BLUE_CYAN;
    public static Color BLUE_TIME;
    public static Color SKYBLUE;
    public static Color TURQUESE;
    public static Color PURLPE_NORMAL;
    public static Color SALMON;
    public static Color DARK_RED;
    public static Color DARK_YELLOW;
    public static Color DARK_BLUE;
    public static Color DARK_BLUE_2;
    public static Color DARK_PINK;
    public static Color DARK_GREEN;
    public static Color PURPLE_BULLET;
    public static Color STAR_YELLOW;
    public static Color TURQUESE_MENU;
    public static Color BLUE_HEALTH_BAR;

    public static void initColorGame() {
        Gdx.app.log("Color LOAD", "True");
        String theme = GameLogic.getPrefs().getString("theme", GameLogic.COLOR_BASIC);
        if (theme.equals(GameLogic.COLOR_BASIC)) {
            RED = valueOf("FF4D4D");
            ORANGE = valueOf("ff8a00");
            GREEN = valueOf("adb55f");
            GREEN_POINTS = valueOf("B4F156");
            BLUE_CYAN = valueOf("78b7cc");
            BLUE_TIME = valueOf("63A4D3");
            SKYBLUE = valueOf("32abea");
            TURQUESE = valueOf("3893c2");
            PURLPE_NORMAL = valueOf("A64DFF");
            SALMON = valueOf("FFCFBF");
            DARK_RED = valueOf("660000");
            DARK_YELLOW = valueOf("D59C00");
            DARK_BLUE = valueOf("0f5480");
            DARK_BLUE_2 = valueOf("4DA6FF");
            DARK_PINK = valueOf("ea7dac");
            DARK_GREEN = valueOf("254203");
            PURPLE_BULLET = Color.PURPLE;
            STAR_YELLOW = valueOf("fffdde");
            BLUE_HEALTH_BAR = valueOf("3b96d8");
            ESMERALDA = valueOf("2d997a");
            ESMERALDA_LIGHT = valueOf("00c78d");
        } else if (theme.equals(GameLogic.COLOR_BWHITE)) {
            RED = valueOf("FFFFFF");
            ORANGE = valueOf("FFFFFF");
            GREEN = valueOf("FFFFFF");
            GREEN_SAD = valueOf("FFFFFF");
            GREEN_POINTS = valueOf("FFFFFF");
            BLUE_CYAN = valueOf("FFFFFF");
            BLUE_TIME = valueOf("FFFFFF");
            SKYBLUE = valueOf("FFFFFF");
            TURQUESE = valueOf("FFFFFF");
            PURLPE_NORMAL = valueOf("FFFFFF");
            SALMON = valueOf("FFFFFF");
            DARK_RED = valueOf("FFFFFF");
            DARK_YELLOW = valueOf("FFFFFF");
            DARK_BLUE = valueOf("FFFFFF");
            DARK_BLUE_2 = valueOf("FFFFFF");
            DARK_PINK = valueOf("FFFFFF");
            DARK_GREEN = valueOf("FFFFFF");
            PURPLE_BULLET = valueOf("FFFFFF");
            STAR_YELLOW = valueOf("FFFFFF");
            TURQUESE_MENU = valueOf("FFFFFF");
        }
    }
}
