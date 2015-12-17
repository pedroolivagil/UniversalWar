package cat.olivadevelop.universalwar.tools;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by OlivaDevelop on 26/05/2015.
 */
public class ImageGame extends Image {

    public ImageGame(NinePatch patch) {
        super(patch);
        centerOrigin();
    }

    public ImageGame(TextureRegion region) {
        super(region);
        setWidth(region.getRegionWidth());
        setHeight(region.getRegionHeight());
        centerOrigin();
    }

    private void centerOrigin() {
        setOrigin(getWidth() / 2, getHeight() / 2);
    }
}
