package cat.olivadevelop.universalwar.screens.history;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

import cat.olivadevelop.universalwar.screens.history.levels.Level1;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Recuperamos los niveles de la base de datos y
 * los cargamos en un array de niveles
 */
public class LevelManager {

    private ArrayList<Level> allLevels;

    public LevelManager(GeneralScreen screen) {
        allLevels = new ArrayList<Level>();
        allLevels.add(new Level1(screen));
    }

    public Level getCurrentLevel(int currentLevel) {
        currentLevel = (currentLevel > allLevels.size()) ? allLevels.size() : currentLevel;
        Gdx.app.log("Cargando nivel", "" + currentLevel);
        return allLevels.get(currentLevel - 1);
    }
}
