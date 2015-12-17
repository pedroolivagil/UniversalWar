package cat.olivadevelop.universalwar.actors.bullets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import cat.olivadevelop.universalwar.actors.enemies.Enemy;
import cat.olivadevelop.universalwar.tools.ActorGame;
import cat.olivadevelop.universalwar.tools.GeneralScreen;
import cat.olivadevelop.universalwar.tools.IntersectorGame;

import static cat.olivadevelop.universalwar.tools.GameLogic.getUi;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.color;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.removeActor;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by OlivaDevelop on 25/08/2015.
 */
public class Shockwave extends ActorGame {

    Circle circle;

    public Shockwave(GeneralScreen screen, float x, float y) {
        super(screen);
        texture = getUi("shockwave");
        setPosition(x, y);
        setWidth(2);
        setHeight(2);
        setOrigin(getWidth() / 2, getHeight() / 2);
        circle = new Circle(x, y, 2);
        addAction(sequence(delay(.8f), color(new Color(0, 0, 0, 0), .5f), removeActor()));
    }

    public void expand(float num) {
        circle.radius += (num + num) / 2;
        setScale(getScaleX() + 1 + (num + num) / 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color);
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        expand(220 * delta);
        Enemy enemy;
        for (Actor a : screen._groupEnemy.getChildren()) {
            enemy = (Enemy) a;
            if (enemy.alive && IntersectorGame.overlaps(enemy.polygon, circle)) {
                enemy.kicked();
            }
        }
    }
}
