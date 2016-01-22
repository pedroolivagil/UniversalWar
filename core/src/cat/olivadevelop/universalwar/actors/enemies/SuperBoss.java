package cat.olivadevelop.universalwar.actors.enemies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;

import cat.olivadevelop.universalwar.actors.bullets.Bullet;
import cat.olivadevelop.universalwar.actors.bullets.BulletRed;
import cat.olivadevelop.universalwar.actors.drops.HeartDropBronze;
import cat.olivadevelop.universalwar.actors.drops.ShieldGoldDrop;
import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by OlivaDevelop on 03/08/2015.
 */
public class SuperBoss extends Enemy {

    private Timer t;

    public SuperBoss(GeneralScreen screen) {
        super(screen, GameLogic.getEnemy(Enemy.BOSS[MathUtils.random(0, Enemy.BOSS.length - 1)]));
        score = 50000;
        time = MathUtils.random(12, 13);
        setHealth(30);
        setScale(.8f);
        setShowLive(true);
        setBoss(true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (isShowLive()) {
            batch.end();
            shape.setProjectionMatrix(batch.getProjectionMatrix());
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(ColorGame.BLUE_CYAN);
            shape.rect(polygon.getX() + 50, polygon.getY() - 5, 180, 10);
            shape.setColor(ColorGame.DARK_BLUE);
            shape.rect(polygon.getX() + 51, polygon.getY() - 4, (180 / maxHealth) * getHealth(), 8);
            shape.end();
            batch.begin();
        }
    }

    @Override
    public void shoot() {
        super.shoot();
        t = new Timer();
        t.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                screen.getStage().addActor(new BulletRed(screen, getX() - 20 + getWidth() / 2, getY() + 5, Bullet.BULLET_DOWN));
                screen.getStage().addActor(new BulletRed(screen, getX() - 5 + getWidth() / 2, getY() - 1, Bullet.BULLET_DOWN));
                if (GameLogic.isAudioOn()) {
                    GameLogic.getSoundShootLaser().play();
                }
            }
        }, 0, .3f, 2);
        t.start();
    }

    @Override
    public void drop() {
        super.drop();
        for (int z = 0; z < 10; z++) {
            screen.getStage().addActor(new HeartDropBronze(screen, this.getX() + calcPosition(), this.getY() + calcPosition()));
        }
        if (calcDrop() <= 3) {
            screen.getStage().addActor(new ShieldGoldDrop(screen, this.getX() + calcPosition(), this.getY() + calcPosition()));
        }
        screen.getStage().addActor(new ShieldGoldDrop(screen, this.getX() + calcPosition(), this.getY() + calcPosition()));
    }
}
