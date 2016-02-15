package cat.olivadevelop.universalwar.screens.history;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonValue;

/**
 * edita y perfecciona la salida y entrada de los datos.
 * Created by Oliva on 14/02/2016.
 */
public class PreferenceStory {

    private final String KILLBOSS = "kill_boss";
    private final String KILLMEGABOSS = "kill_megaboss";
    private final String KILLSUPERBOSS = "kill_superboss";
    private final String KILLENEMY = "kill_enemys";
    private final String SURVIVE = "survive";
    private final String XTREMSURVIVE = "xtrem_survive";

    private JsonValue data;

    private boolean new_story;

    private int basic;
    private int advanced;
    private int boss;
    private int megaboss;
    private int superboss;

    private int timerate_basic;
    private int timerate_advanced;
    private int timerate_boss;
    private int timerate_megaboss;
    private int timerate_superboss;
    private int max_bas_into_group;
    private int max_adv_into_group;

    private String medal;
    private int reward;

    private int ship_health;
    private boolean ship_can_shoot;
    private int ship_shield;

    private boolean powerup_health;
    private boolean powerup_shield;
    private boolean powerup_help;
    private boolean powerup_missile;
    private boolean powerup_supermissile;
    private boolean powerup_shooter;

    private int target;
    private int[] opt_target;

    public PreferenceStory(JsonValue data) {
        this.data = data;
        setTarget();
        setOpt_target();
        setNew_story(data.getBoolean("new_story"));
        setBasic(data.getInt("basic_enemys"));
        setAdvanced(data.getInt("advan_enemys"));
        setBoss();
        setMegaboss();
        setSuperboss();
        this.timerate_basic = data.getInt("timerate_ba_enemy");
        this.timerate_advanced = data.getInt("timerate_av_enemy");
        this.timerate_boss = data.getInt("timerate_boss");
        this.timerate_megaboss = data.getInt("timerate_megaboss");
        this.timerate_superboss = data.getInt("timerate_superboss");
        this.reward = data.getInt("reward_points");
        this.medal = data.getString("reward_medal");
        this.ship_can_shoot = data.getBoolean("ship_shoot");
        this.ship_health = data.getInt("ship_health");
        this.ship_shield = data.getInt("ship_shield");
        this.powerup_health = data.getBoolean("powerup_health");
        this.powerup_help = data.getBoolean("powerup_help");
        this.powerup_missile = data.getBoolean("powerup_missile");
        this.powerup_shield = data.getBoolean("powerup_shield");
        this.powerup_shooter = data.getBoolean("powerup_shooter");
        this.powerup_supermissile = data.getBoolean("powerup_supermissile");
        this.max_bas_into_group = data.getInt("max_basic_into_group");
        this.max_adv_into_group = data.getInt("max_advan_into_group");
    }

    public int getTimerate_basic() {
        return timerate_basic;
    }

    public int getTimerate_advanced() {
        return timerate_advanced;
    }

    public int getTimerate_boss() {
        return timerate_boss;
    }

    public int getTimerate_megaboss() {
        return timerate_megaboss;
    }

    public int getTimerate_superboss() {
        return timerate_superboss;
    }

    public int getMax_bas_into_group() {
        return max_bas_into_group;
    }

    public int getMax_adv_into_group() {
        return max_adv_into_group;
    }

    public String getMedal() {
        return medal;
    }

    public int getReward() {
        return reward;
    }

    public boolean isPowerup_shooter() {
        return powerup_shooter;
    }

    public boolean isPowerup_supermissile() {
        return powerup_supermissile;
    }

    public boolean isPowerup_missile() {
        return powerup_missile;
    }

    public boolean isPowerup_help() {
        return powerup_help;
    }

    public boolean isPowerup_shield() {
        return powerup_shield;
    }

    public boolean isPowerup_health() {
        return powerup_health;
    }

    public int getShip_shield() {
        return ship_shield;
    }

    public boolean isShip_can_shoot() {
        return ship_can_shoot;
    }

    public int getShip_health() {
        return ship_health;
    }

    public boolean isNew_story() {
        return new_story;
    }

    public void setNew_story(boolean new_story) {
        this.new_story = new_story;
    }

    public int getBasic() {
        return basic;
    }

    public void setBasic(int basic) {
        this.basic = basic;
    }

    public int getAdvanced() {
        return advanced;
    }

    public void setAdvanced(int advanced) {
        this.advanced = advanced;
    }

    public int getBoss() {
        return boss;
    }

    public void setBoss() {
    }

    public int getMegaboss() {
        return megaboss;
    }

    public void setMegaboss() {
    }

    public int getSuperboss() {
        return superboss;
    }

    public void setSuperboss() {
    }

    public int getTarget() {
        return target;
    }

    public void setTarget() {
        // leemos los objetivos del nivel y implementamos un array a medida para
        // validar si el usuario ha ganado o no

        JsonValue t = this.data.get("main_target");
        for (int x = 0; x < t.size; x++) {
            JsonValue tmp = t.get(x);
            String key = tmp.name();
            int value = this.data.get("main_target").getInt(key);

            if (key.equals(KILLENEMY)) {
                Gdx.app.log("Target", "" + key + " -> " + value);
            }
            if (key.equals(KILLBOSS)) {
                Gdx.app.log("Target", "" + key + " -> " + value);
            }
            if (key.equals(KILLMEGABOSS)) {
                Gdx.app.log("Target", "" + key + " -> " + value);
            }
            if (key.equals(KILLSUPERBOSS)) {
                Gdx.app.log("Target", "" + key + " -> " + value);
            }
            if (key.equals(SURVIVE)) {
                Gdx.app.log("Target", "" + key + " -> " + value);
            }
            if (key.equals(XTREMSURVIVE)) {
                Gdx.app.log("Target", "" + key + " -> " + value);
            }
        }
    }

    public int[] getOpt_target() {
        return opt_target;
    }

    public void setOpt_target() {
        /*JsonValue t = this.data.get("optl_target").get(0);
        if (t.toString().equals("kill_enemys")) {
            Gdx.app.log("Opt Target", "" + t.toString());
        }*/
    }
}
