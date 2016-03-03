package cat.olivadevelop.universalwar.screens.history;

import com.badlogic.gdx.utils.JsonValue;

import cat.olivadevelop.universalwar.tools.GameLogic;

import static cat.olivadevelop.universalwar.tools.GameLogic.getNumberFormated;
import static cat.olivadevelop.universalwar.tools.GameLogic.getString;

/**
 * edita y perfecciona la salida y entrada de los datos.
 * Created by Oliva on 14/02/2016.
 */
public class PreferenceStory {

    private static JsonValue world;
    public final String KILLBOSS = "kill_boss";
    public final String KILLMEGABOSS = "kill_megaboss";
    public final String KILLSUPERBOSS = "kill_superboss";
    public final String KILLENEMY = "kill_enemys";
    public final String SURVIVE = "survive";
    public final String XTREMSURVIVE = "xtrem_survive";
    private JsonValue data;
    private int id_level;

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

    private JsonValue targets;
    private int[] opt_target;

    public PreferenceStory(JsonValue data) {
        this.data = data;
        setTarget();
        setOpt_target();
        setBasic(data.getInt("basic_enemys"));
        setAdvanced(data.getInt("advan_enemys"));
        setBoss();
        setMegaboss();
        setSuperboss();
        this.id_level = data.getInt("id_level");
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

    public static JsonValue getWorld() {
        return world;
    }

    public static void setWorld(JsonValue world) {
        PreferenceStory.world = world;
    }

    public int getId_level() {
        return id_level;
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

    public JsonValue getTargets() {
        return targets;
    }

    public void setTarget() {
        // leemos los objetivos del nivel y implementamos un array a medida para
        // validar si el usuario ha ganado o no
        targets = this.data.get("main_target");
    }

    public String[] getStringTarget(String key, int value) {
        String target_name = "";
        String target_value = "";
        if (key.equals(KILLENEMY)) {
            target_name = getString("target_kill_enemys");
            target_value = getNumberFormated(value) + " " + getString("target_num");
        }
        if (key.equals(KILLBOSS)) {
            target_name = getString("target_kill_boss");
            target_value = getNumberFormated(value);
        }
        if (key.equals(KILLMEGABOSS)) {
            target_name = getString("target_kill_megaboss");
            target_value = getNumberFormated(value);
        }
        if (key.equals(KILLSUPERBOSS)) {
            target_name = getString("target_kill_superboss");
            target_value = getNumberFormated(value);
        }
        if (key.equals(SURVIVE)) {
            target_name = getString("target_survive");
            target_value = GameLogic.getFormatedTime(value) + " " + getString("target_mins");
        }
        if (key.equals(XTREMSURVIVE)) {
            target_name = getString("target_xtrem_survive");
            target_value = " " + getString("target_mins");
        }
        return new String[]{target_name, target_value};
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

    public JsonValue getData() {
        return data;
    }
}
