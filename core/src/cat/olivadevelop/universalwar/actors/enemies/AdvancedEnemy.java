package cat.olivadevelop.universalwar.actors.enemies;

import com.badlogic.gdx.math.MathUtils;

import cat.olivadevelop.universalwar.actors.bullets.Bullet;
import cat.olivadevelop.universalwar.actors.bullets.BulletBlue;
import cat.olivadevelop.universalwar.actors.drops.HeartDropBronze;
import cat.olivadevelop.universalwar.actors.drops.PointsUpGold;
import cat.olivadevelop.universalwar.actors.drops.PointsUpSilver;
import cat.olivadevelop.universalwar.actors.drops.ShieldBronzeDrop;
import cat.olivadevelop.universalwar.actors.drops.ShieldGoldDrop;
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
    }

    @Override
    public void shoot() {
        super.shoot();
        screen._stage.addActor(new BulletBlue(screen, getX() + getWidth() / 2, getY() - 1, Bullet.BULLET_DOWN));
        //screen._stage.addActor(new BulletBlue(screen, getX() + 5 + getWidth() / 2, getY() - 1, Bullet.BULLET_DOWN));
        if (GameLogic.isAudioOn()) {
            GameLogic.getSoundShootPlasma().play();
        }
    }

    @Override
    public void drop() {
        super.drop();
        if (calcDrop() <= 10) {
            screen._stage.addActor(new HeartDropBronze(screen, this.getX(), this.getY()));
        }
        if (calcDrop() <= 10) {
            screen._stage.addActor(new ShieldBronzeDrop(screen, this.getX(), this.getY()));
        }
        if (calcDrop() <= 8) {
            screen._stage.addActor(new ShieldSilverDrop(screen, this.getX(), this.getY()));
        }
        if (calcDrop() <= 3) {
            screen._stage.addActor(new ShieldGoldDrop(screen, this.getX(), this.getY()));
        }
        if (calcDrop() <= 7) {
            screen._stage.addActor(new PointsUpSilver(screen, this.getX(), this.getY()));
        }
        if (calcDrop() <= 2) {
            screen._stage.addActor(new PointsUpGold(screen, this.getX(), this.getY()));
        }
    }
}
