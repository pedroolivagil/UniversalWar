package cat.olivadevelop.universalwar.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import static cat.olivadevelop.universalwar.tools.GameLogic.getUi;

/**
 * Created by OlivaDevelop on 26/05/2015.
 */
public class ButtonGame extends Group {
    private Label label;
    private Image image;

    public ButtonGame(String text) {
        create(text);
        setScale(.7f);
    }

    public ButtonGame(String text, float scale) {
        create(text);
        setScale(scale);
    }

    private void create(String text) {
        label = new Label(text.toUpperCase(), GameLogic.getSkin());
        image = new Image(getUi("botonMenu"));

        image.setWidth(label.getWidth() + (label.getWidth() * .1f));
        image.setHeight(label.getHeight());
        setWidth(image.getWidth());
        setHeight(image.getHeight());

        addActors(new Actor[]{label, image});
    }

    public void addActors(Actor[] actor) {
        if (hasChildren()) {
            clearChildren();
        }
        for (Actor anActor : actor) {
            addActor(anActor);
        }
    }

    public float getScale() {
        return super.getScaleX();
    }

    @Override
    public void setColor(Color color) {
        label.setColor(color);
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width * getScale());
        image.setWidth(width + (width * .1f));
        label.setX(image.getWidth() / 2 - label.getWidth() / 2);
    }
}
