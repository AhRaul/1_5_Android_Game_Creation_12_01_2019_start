package ru.sgpackage.sprite.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.sgpackage.math.Rect;

public class BtnStart extends ScaledTouchUpButton {

    private Rect worldBounds;

    public BtnStart(TextureAtlas atlas) {
        super(atlas.findRegion("btPlay"));
        setHeightProportion(0.4f);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float posX = 0f;
        float posY = 0f;
        pos.set(posX, posY);
    }

    @Override
    public void action() {

    }
}
