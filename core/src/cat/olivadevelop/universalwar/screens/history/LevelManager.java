package cat.olivadevelop.universalwar.screens.history;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Recuperamos los niveles de la base de datos y
 * los cargamos en un array de niveles
 * Created by Oliva on 06/02/2016.
 */
public class LevelManager {

    private ArrayList<Level> allLevels;
    private GeneralScreen screen;

    public LevelManager(GeneralScreen screen) {
        this.screen = screen;
        allLevels = new ArrayList<Level>();
        /*ConnectDB conn = new ConnectDB();
        ResultSet levels = conn.query("SELECT * FROM uw_level");
        try {
            if (levels.next()) {
                Blob blob = (Blob) levels.getBlob("object");
                Gdx.app.log("Leyendo DB", "Leyendo...");

                // Se reconstruye el objeto con un ObjectInputStream
                ObjectInputStream ois = new ObjectInputStream(blob.getBinaryStream());
                Level level = (Level) ois.readObject();
                level.setScreen(screen);
                level.setTitle(levels.getString("title"));

                Gdx.app.log("OBJ", "" + levels.getString("title"));

                // rellenamos el array de levels con el resultado de la bd
                allLevels.add(level);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public Level getCurrentLevel(int currentLevel) {
        currentLevel = (currentLevel > allLevels.size()) ? allLevels.size() : currentLevel;
        Gdx.app.log("Cargando nivel", "" + currentLevel);
        return allLevels.get(currentLevel - 1);
    }

    public void setScreen(GeneralScreen screen) {
        this.screen = screen;
    }
}
