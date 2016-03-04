package cat.olivadevelop.universalwar.actors.bullets;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import cat.olivadevelop.universalwar.actors.allied.Allied;
import cat.olivadevelop.universalwar.actors.enemies.Enemy;
import cat.olivadevelop.universalwar.actors.explosions.ExplosionMini;
import cat.olivadevelop.universalwar.actors.shields.Shield;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;
import cat.olivadevelop.universalwar.tools.IntersectorGame;

/**
 * Created by Oliva on 15/04/2015.
 */
public class Bullet extends Image {

    public static final float BULLET_UP = 0f;               // rotacion de direccion
    public static final float BULLET_DOWN = 180f;           // rotacion de direccion
    public Circle circle;
    GeneralScreen screen;
    private float vel;
    private float posBulletFinal;
    private int damage;

    public Bullet(GeneralScreen screen, float x, float y, float vel) {
        super(GameLogic.getUi("bullet"));
        this.screen = screen;
        setOrigin(getWidth() / 2, getHeight() / 2);
        circle = new Circle(getX() + (getWidth() / 2), getY(), getWidth() / 2);
        this.vel = vel;
        setPosition(x, y);
        setScale(.7f);
        setDamage(1);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public void setRotation(float degrees) {
        super.setRotation(degrees);
        this.vel = (degrees < 180) ? this.vel : -this.vel;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (vel < 0) {
            circle.setPosition(getX() + (getWidth() / 2), getY() + 10);
        } else {
            circle.setPosition(getX() + (getWidth() / 2), getY() + getHeight() + 10);
        }
        moveBy(0, vel * delta);
        if (getY() < -getHeight() || getY() > GameLogic.getScreenHeight()) {
            remove();
        }
        if (vel > 0) {
            if (screen._groupEnemy.hasChildren()) {
                Enemy enemy;
                for (Actor a : screen._groupEnemy.getChildren()) {
                    enemy = (Enemy) a;
                    if (enemy.alive && IntersectorGame.overlaps(enemy.polygon, circle)) {//Bala inpacta en enemic
                        enemy.kicked(getDamage());
                        removeBullet();
                    }
                }
            }
            if (screen._groupEnemyBas.hasChildren()) {
                Enemy enemy;
                for (Actor a : screen._groupEnemyBas.getChildren()) {
                    enemy = (Enemy) a;
                    if (enemy.alive && IntersectorGame.overlaps(enemy.polygon, circle)) {//Bala inpacta en enemic
                        enemy.kicked(getDamage());
                        removeBullet();
                    }
                }
            }
            if (screen._groupEnemyAdv.hasChildren()) {
                Enemy enemy;
                for (Actor a : screen._groupEnemyAdv.getChildren()) {
                    enemy = (Enemy) a;
                    if (enemy.alive && IntersectorGame.overlaps(enemy.polygon, circle)) {//Bala inpacta en enemic
                        enemy.kicked(getDamage());
                        removeBullet();
                    }
                }
            }
            if (screen._bossGroup.hasChildren()) {
                Enemy enemy;
                for (Actor a : screen._bossGroup.getChildren()) {
                    enemy = (Enemy) a;
                    if (enemy.alive && IntersectorGame.overlaps(enemy.polygon, circle)) {//Bala inpacta en enemic
                        enemy.kicked(getDamage());
                        removeBullet();
                    }
                }
            }
            if (screen._megaBossGroup.hasChildren()) {
                Enemy enemy;
                for (Actor a : screen._megaBossGroup.getChildren()) {
                    enemy = (Enemy) a;
                    if (enemy.alive && IntersectorGame.overlaps(enemy.polygon, circle)) {//Bala inpacta en enemic
                        enemy.kicked(getDamage());
                        removeBullet();
                    }
                }
            }
            if (screen._superBossGroup.hasChildren()) {
                Enemy enemy;
                for (Actor a : screen._superBossGroup.getChildren()) {
                    enemy = (Enemy) a;
                    if (enemy.alive && IntersectorGame.overlaps(enemy.polygon, circle)) {//Bala inpacta en enemic
                        enemy.kicked(getDamage());
                        removeBullet();
                    }
                }
            }
        } else {
            Shield shield;
            Allied allied;
            if (screen._groupShields.hasChildren()) {
                for (Actor a : screen._groupShields.getChildren()) {
                    shield = (Shield) a;
                    if (IntersectorGame.overlaps(shield.circle, circle)) {
                        for (int z = 0; z < getDamage(); z++) {
                            shield.hitShield();
                        }
                        removeBullet();
                    }
                }
            } else {
                for (Actor a : screen._groupAllied.getChildren()) {
                    allied = (Allied) a;
                    if (allied.alive && IntersectorGame.overlaps(allied.polygon, circle)) {
                        if (Enemy.isCanShoot()) {
                            //allied.kicked(getDamage());
                        }
                        removeBullet();
                    }
                }
            }
        }
    }

    public void removeBullet() {
        posBulletFinal = (getRotation() == 0) ? getY() + getHeight() : getY();
        screen.getStage().addActor(new ExplosionMini(getX(), posBulletFinal, MathUtils.random(0, 3) * 90));
        remove();
    }
}
