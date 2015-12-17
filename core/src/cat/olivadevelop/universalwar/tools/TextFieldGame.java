package cat.olivadevelop.universalwar.tools;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import static cat.olivadevelop.universalwar.tools.GameLogic.getBotonMenuDrawable;

/**
 * Created by Oliva on 28/08/2015.
 */
public class TextFieldGame extends TextField {

    public TextFieldGame(String hint) {
        super("", GameLogic.getSkin_mini());
        this.setAlignment(Align.center);
        this.setMessageText(hint);
        this.setWidth(600);
        this.setHeight(80);
        this.setBounds(0, 0, this.getWidth(), this.getHeight());
        TextFieldStyle style = getStyle();
        style.background = getBotonMenuDrawable();
        this.setStyle(style);
    }

    @Override
    public void setStyle(TextFieldStyle style) {
        super.setStyle(style);
        textHeight = style.font.getCapHeight() - style.font.getDescent() * 2.5f;
    }
}
