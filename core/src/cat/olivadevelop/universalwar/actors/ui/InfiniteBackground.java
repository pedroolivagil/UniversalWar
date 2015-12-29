package cat.olivadevelop.universalwar.actors.ui;

import com.badlogic.gdx.graphics.g2d.Batch;

import cat.olivadevelop.universalwar.tools.ActorGame;
import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.GeneralScreen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by OlivaDevelop on 17/04/2015.
 */
public class InfiniteBackground extends ActorGame {

    public InfiniteBackground(GeneralScreen screen) {
        super(screen);
        texture = GameLogic.getUi("background");
        setWidth(width);
        setHeight(height);
        setPosition(0, height);
        addAction(forever(sequence(moveTo(0, 0, 25), moveTo(0, height))));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(ColorGame.WHITE);
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        batch.draw(texture, getX(), getY() - getHeight() - 4, getWidth(), getHeight());
    }
}
