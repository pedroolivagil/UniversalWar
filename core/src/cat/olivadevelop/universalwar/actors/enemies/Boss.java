package cat.olivadevelop.universalwar.actors.enemies;

import com.badlogic.gdx.math.MathUtils;

import cat.olivadevelop.universalwar.actors.bullets.Bullet;
import cat.olivadevelop.universalwar.actors.bullets.BulletRed;
import cat.olivadevelop.universalwar.actors.drops.HeartDropBronze;
import cat.olivadevelop.universalwar.actors.drops.ShieldGoldDrop;
import cat.olivadevelop.universalwar.actors.drops.ShieldSilverDrop;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by OlivaDevelop on 03/08/2015.
 */
public class Boss extends Enemy {

    public Boss(GeneralScreen screen, String ship) {
        super(screen, GameLogic.getEnemy(ship));
        score = 5000;
        time = MathUtils.random(7, 8);
        setScale(1.5f);
        setHealth(14);
        setShowLive(true);
    }

    @Override
    public void shoot() {
        super.shoot();
        screen._stage.addActor(new BulletRed(screen, getX() - 5 + getWidth() / 2, getY() - 1, Bullet.BULLET_DOWN));
        screen._stage.addActor(new BulletRed(screen, getX() + 5 + getWidth() / 2, getY() - 1, Bullet.BULLET_DOWN));
        if (GameLogic.isAudioOn()) {
            GameLogic.getSoundShootLaser().play();
        }
    }

    @Override
    public void drop() {
        super.drop();
        for (int z = 0; z < 5; z++) {
            screen._stage.addActor(new HeartDropBronze(screen, this.getX() + calcPosition(), this.getY() + calcPosition()));
        }
        if (calcDrop() <= 3) {
            screen._stage.addActor(new ShieldGoldDrop(screen, this.getX() + calcPosition(), this.getY() + calcPosition()));
        }
        screen._stage.addActor(new ShieldSilverDrop(screen, this.getX() + calcPosition(), this.getY() + calcPosition()));
    }
}
