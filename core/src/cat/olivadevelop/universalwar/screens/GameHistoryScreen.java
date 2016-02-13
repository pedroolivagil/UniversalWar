package cat.olivadevelop.universalwar.screens;

import com.badlogic.gdx.scenes.scene2d.Group;

import cat.olivadevelop.universalwar.UniversalWarGame;
import cat.olivadevelop.universalwar.actors.ui.HUDHistory;
import cat.olivadevelop.universalwar.screens.history.LevelManager;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by Oliva on 12/12/2015.
 */
public class GameHistoryScreen extends GeneralScreen {

    private LevelManager levelManager;
    private Group groupLevel;

    public GameHistoryScreen(UniversalWarGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        levelManager = new LevelManager(this);
        groupLevel = levelManager.getCurrentLevel(1);
        _hudHistory = new HUDHistory(this);
        getStage().addActor(groupLevel);
        getStage().addActor(_hudHistory);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        _hudHistory.toFront();
    }
}
