package ru.sgpackage.sprite.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.sgpackage.math.Rect;

public class BtnClose extends ScaledTouchUpButton {

    public BtnClose(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
        setHeightProportion(0.1f);
    }

    @Override
    public void resize(Rect worldBounds) {
        float posX = worldBounds.getRight()*0.8f;
        float posY = 0.4f;
        pos.set(posX, posY);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
