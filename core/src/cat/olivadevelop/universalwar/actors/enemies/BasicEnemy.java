package cat.olivadevelop.universalwar.actors.enemies;

import cat.olivadevelop.universalwar.actors.bullets.Bullet;
import cat.olivadevelop.universalwar.actors.bullets.BulletGreen;
import cat.olivadevelop.universalwar.actors.drops.PointsUpBronze;
import cat.olivadevelop.universalwar.actors.drops.PointsUpSilver;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by Oliva on 15/04/2015.
 */
public class BasicEnemy extends Enemy {

    public BasicEnemy(GeneralScreen screen, String ship) {
        super(screen, GameLogic.getEnemy(ship));
        setScale(.7f);
        score = 100;
        setHealth(1);
        setName("basic");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void shoot() {
        super.shoot();
        screen.getStage().addActor(new BulletGreen(screen, getX() + getWidth() / 2, getY() - 1, Bullet.BULLET_DOWN));
        if (GameLogic.isAudioOn()) {
            GameLogic.getSoundShootLaser().play();
        }
    }

    @Override
    public void drop() {
        super.drop();
        if (calcDrop() <= 5) {
            screen.getStage().addActor(new PointsUpBronze(screen, this.getX() + calcPosition(), this.getY() + calcPosition()));
        }
        if (calcDrop() <= 2) {
            screen.getStage().addActor(new PointsUpSilver(screen, this.getX() + calcPosition(), this.getY() + calcPosition()));
        }
    }
}
