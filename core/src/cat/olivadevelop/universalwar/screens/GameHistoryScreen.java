package cat.olivadevelop.universalwar.screens;

import cat.olivadevelop.universalwar.UniversalWarGame;
import cat.olivadevelop.universalwar.actors.ui.HUDHistory;
import cat.olivadevelop.universalwar.screens.history.LevelManager;
import cat.olivadevelop.universalwar.screens.history.PreferenceStory;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by Oliva on 12/12/2015.
 */
public class GameHistoryScreen extends GeneralScreen {

    private LevelManager levelManager;
    private PreferenceStory storyPrefs;

    public GameHistoryScreen(UniversalWarGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        // instanciamos el generador de niveles, segun el nivel del usuario
        levelManager = new LevelManager();
        // data_level contiene todos los datos del nivel
        storyPrefs = new PreferenceStory(levelManager.getCurrentLevel(1));

        _hudHistory = new HUDHistory(this);
        getStage().addActor(_hudHistory);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        _hudHistory.toFront();
    }
}
