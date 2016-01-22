package cat.olivadevelop.universalwar.actors.enemies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import cat.olivadevelop.universalwar.actors.bullets.Bullet;
import cat.olivadevelop.universalwar.actors.bullets.BulletYellow;
import cat.olivadevelop.universalwar.actors.drops.HeartDropBronze;
import cat.olivadevelop.universalwar.actors.drops.ShieldGoldDrop;
import cat.olivadevelop.universalwar.actors.drops.ShieldSilverDrop;
import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

/**
 * Created by OlivaDevelop on 03/08/2015.
 */
public class Boss extends Enemy {

    public Boss(GeneralScreen screen) {
        super(screen, GameLogic.getEnemy(Enemy.BASIC[MathUtils.random(0, Enemy.BASIC.length - 1)]));
        score = 5000;
        time = MathUtils.random(7, 8);
        setScale(1.2f);
        setHealth(15);
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
            shape.rect(polygon.getX() - (getWidth() * .15f), polygon.getY() - (getHeight() * .5f), 110, 10);
            shape.setColor(ColorGame.DARK_BLUE);
            shape.rect(polygon.getX() - (getWidth() * .15f) + 1, polygon.getY() - (getHeight() * .5f) + 1, (110 / maxHealth) * getHealth(), 8);
            shape.end();
            batch.begin();
        }
    }

    @Override
    public void shoot() {
        super.shoot();
        screen.getStage().addActor(new BulletYellow(screen, getX() - 5 + getWidth() / 2, getY() - 1, Bullet.BULLET_DOWN));
        screen.getStage().addActor(new BulletYellow(screen, getX() + 5 + getWidth() / 2, getY() - 1, Bullet.BULLET_DOWN));
        if (GameLogic.isAudioOn()) {
            GameLogic.getSoundShootLaser().play();
        }
    }

    @Override
    public void drop() {
        super.drop();
        for (int z = 0; z < 5; z++) {
            screen.getStage().addActor(new HeartDropBronze(screen, this.getX() + calcPosition(), this.getY() + calcPosition()));
        }
        if (calcDrop() <= 3) {
            screen.getStage().addActor(new ShieldGoldDrop(screen, this.getX() + calcPosition(), this.getY() + calcPosition()));
        }
        screen.getStage().addActor(new ShieldSilverDrop(screen, this.getX() + calcPosition(), this.getY() + calcPosition()));
    }
}
