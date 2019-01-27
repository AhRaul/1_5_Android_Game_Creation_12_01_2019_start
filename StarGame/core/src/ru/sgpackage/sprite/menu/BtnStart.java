package ru.sgpackage.sprite.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.sgpackage.math.Rect;
import ru.sgpackage.screen.GameScreen;
import ru.sgpackage.screen.MenuScreen;

public class BtnStart extends ScaledTouchUpButton {

    private MenuScreen menuScreen;

    public BtnStart(TextureAtlas atlas, MenuScreen menuScreen) {
        super(atlas.findRegion("btPlay"));
        this.menuScreen = menuScreen;
        setHeightProportion(0.4f);
    }

    @Override
    public void resize(Rect worldBounds) {
        float posX = 0f;
        float posY = 0f;
        pos.set(posX, posY);
    }

    @Override
    public void action() {
        menuScreen.changeScreen();
    }
}
