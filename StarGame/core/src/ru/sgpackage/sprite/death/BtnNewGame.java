package ru.sgpackage.sprite.death;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.sgpackage.math.Rect;
import ru.sgpackage.sprite.game.MainShip;
import ru.sgpackage.sprite.menu.ScaledTouchUpButton;

public class BtnNewGame extends ScaledTouchUpButton {


    private MainShip mainShip;
    private Rect worldBounds;

    public BtnNewGame(TextureAtlas atlas, MainShip mainShip, Rect worldBounds) {
        super(atlas.findRegion("button_new_game"));
        this.mainShip = mainShip;
        this.worldBounds = worldBounds;
        setHeightProportion(0.05f);
    }

    @Override
    public void resize(Rect worldBounds) {
        float posX = 0f;
        float posY = worldBounds.getBottom()+0.25f;
        pos.set(posX, posY);
    }

    @Override
    public void action() {
        mainShip.flushDestroy();
        mainShip.restartGame();
    }
}
