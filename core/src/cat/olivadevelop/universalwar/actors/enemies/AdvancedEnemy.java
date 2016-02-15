package cat.olivadevelop.universalwar.actors.enemies;

import com.badlogic.gdx.math.MathUtils;

import cat.olivadevelop.universalwar.actors.bullets.Bullet;
import cat.olivadevelop.universalwar.actors.bullets.BulletBlue;
import cat.olivadevelop.universalwar.actors.drops.HeartDropBronze;
import cat.olivadevelop.universalwar.actors.drops.PointsUpGold;
import cat.olivadevelop.universalwar.actors.drops.PointsUpSilver;
import cat.olivadevelop.universalwar.actors.drops.ShieldBronzeDrop;
import cat.olivadevelop.universalwar.actors.drops.ShieldSilverDrop;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;


/**
 * Created by Oliva on 15/04/2015.
 */
public class AdvancedEnemy extends Enemy {

    public AdvancedEnemy(GeneralScreen screen, String ship) {
        super(screen, GameLogic.getEnemy(ship));
        score = 250;
        time = MathUtils.random(4, 5);
        setScale(1);
        setHealth(2);
        setName("advanced");
    }

    @Override
    public void shoot() {
        super.shoot();
        screen.getStage().addActor(new BulletBlue(screen, getX() + getWidth() / 2, getY() - 1, Bullet.BULLET_DOWN));
        //screen.getStage().addActor(new BulletBlue(screen, getX() + 5 + getWidth() / 2, getY() - 1, Bullet.BULLET_DOWN));
        if (GameLogic.isAudioOn()) {
            GameLogic.getSoundShootPlasma().play();
        }
    }

    @Override
    public void drop() {
        super.drop();
        if (calcDrop() <= 10) {
            screen.getStage().addActor(new HeartDropBronze(screen, this.getX() + calcPosition(), this.getY() + calcPosition()));
        }
        if (calcDrop() <= 10) {
            screen.getStage().addActor(new ShieldBronzeDrop(screen, this.getX() + calcPosition(), this.getY() + calcPosition()));
        }
        if (calcDrop() <= 8) {
            screen.getStage().addActor(new ShieldSilverDrop(screen, this.getX() + calcPosition(), this.getY() + calcPosition()));
        }
        if (calcDrop() <= 7) {
            screen.getStage().addActor(new PointsUpSilver(screen, this.getX() + calcPosition(), this.getY() + calcPosition()));
        }
        if (calcDrop() <= 2) {
            screen.getStage().addActor(new PointsUpGold(screen, this.getX() + calcPosition(), this.getY() + calcPosition()));
        }
    }
}
