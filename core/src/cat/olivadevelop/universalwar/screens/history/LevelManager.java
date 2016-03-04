package cat.olivadevelop.universalwar.screens.history;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonValue;

import cat.olivadevelop.universalwar.tools.GameLogic;

import static cat.olivadevelop.universalwar.screens.history.PreferenceStory.getWorld;
import static cat.olivadevelop.universalwar.screens.history.PreferenceStory.setWorld;

/**
 * Recuperamos los niveles de la base de datos y
 * los cargamos en un array de niveles
 */
public class LevelManager {

    public static int WORLD;
    private static JsonValue arr_levels;
    private static JsonValue world_sel;

    public LevelManager() {
        // El nivel de inicio de un jugador es 1.
        // Por lo tanto buscaremos el nivel (level-1)
        setWorld(GameLogic.getJsonLevels().get("world"));
        world_sel = getWorld().get(WORLD);
        arr_levels = world_sel.get("level");
    }

    public static void setArrayLevel() {
        world_sel = getWorld().get(WORLD - 1);
        arr_levels = world_sel.get("level");
    }

    public static JsonValue getArr_levels() {
        setArrayLevel();
        return arr_levels;
    }

    public JsonValue getCurrentLevel(int currentLevel) {
        Gdx.app.log("Current Level", "" + (currentLevel) % 6);
        return arr_levels.get((currentLevel - 1) % 6);
    }
}
