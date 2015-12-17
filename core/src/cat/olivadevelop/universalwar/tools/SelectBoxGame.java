package cat.olivadevelop.universalwar.tools;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

/**
 * Created by Oliva on 12/12/2015.
 */
public class SelectBoxGame extends SelectBox<String> {
    public SelectBoxGame(Skin skin) {
        super(skin);
        setBG();
    }

    private void setBG() {
        SelectBoxStyle style = getStyle();
        style.background = new NinePatchDrawable(new NinePatch(GameLogic.getUi("botonMenuSelect")));
        style.scrollStyle.background = new NinePatchDrawable(new NinePatch(GameLogic.getUi("botonMenu")));
        style.listStyle.selection = new NinePatchDrawable(new NinePatch(GameLogic.getUi("botonMenu")));
        setStyle(style);
    }
}
