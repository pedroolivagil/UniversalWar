package cat.olivadevelop.universalwar.screens.history;

import com.badlogic.gdx.scenes.scene2d.Group;

import java.io.Serializable;

import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by Oliva on 06/02/2016.
 */
public class Level extends Group implements Serializable {
    public String reward_medall = null;
    public int reward_points = 0;
    public String title = null;
    public String descript = null;
    public GeneralScreen screen;

    public Level(GeneralScreen screen) {
        this.screen = screen;
    }

    // Getters y setters
    public GeneralScreen getScreen() {
        return screen;
    }

    public String getReward_medall() {
        return reward_medall;
    }

    public void setReward_medall(String reward_medall) {
        this.reward_medall = reward_medall;
    }

    public int getReward_points() {
        return reward_points;
    }

    public void setReward_points(int reward_points) {
        this.reward_points = reward_points;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }
}