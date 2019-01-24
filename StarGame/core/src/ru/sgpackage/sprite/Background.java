package ru.sgpackage.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.sgpackage.base.Sprite;
import ru.sgpackage.math.Rect;

public class Background extends Sprite {

    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        pos.set(worldBounds.pos);
        setHeightProportion(worldBounds.getHeight());
    }
}
