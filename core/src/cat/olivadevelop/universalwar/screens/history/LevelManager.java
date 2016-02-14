package cat.olivadevelop.universalwar.screens.history;

import com.badlogic.gdx.utils.JsonValue;

import cat.olivadevelop.universalwar.tools.GameLogic;

/**
 * Recuperamos los niveles de la base de datos y
 * los cargamos en un array de niveles
 */
public class LevelManager {

    private JsonValue arr_levels;
    // ArrayList<JsonValue> arrayList;

    public LevelManager() {
        // El nivel de inicio de un jugador es 1. Por lo tanto buscaremos el nivel (level-1)
        arr_levels = GameLogic.getJsonLevels().get("levels");
    }

    public JsonValue getCurrentLevel(int currentLevel) {
        return this.arr_levels.get((currentLevel - 1));
    }
}
