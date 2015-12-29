package cat.olivadevelop.universalwar.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import static cat.olivadevelop.universalwar.tools.GameLogic.getSkin;

/**
 * Created by Oliva on 30/08/2015.
 */
public class LabelGame extends Label {

    public LabelGame(CharSequence text) {
        super(text, getSkin());
        setFontScale(1f);
    }

    public LabelGame(CharSequence text, float scale) {
        super(text, getSkin());
        setFontScale(scale);
    }

    public LabelGame(CharSequence text, float scale, Color color) {
        super(text, getSkin());
        setFontScale(scale);
        setColor(color);
    }

    public LabelGame center() {
        setAlignment(Align.center);
        return this;
    }

    public LabelGame setPositionXY(float x, float y) {
        setPosition(x, y);
        return this;
    }
}
