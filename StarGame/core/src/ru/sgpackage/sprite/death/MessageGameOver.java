package ru.sgpackage.sprite.death;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.sgpackage.base.Sprite;
import ru.sgpackage.math.Rect;

public class MessageGameOver extends Sprite {

    public MessageGameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
        setHeightProportion(0.06f);
    }

    @Override
    public void resize(Rect worldBounds) {
        float posX = 0f;
        float posY = 0f;
        pos.set(posX, posY);
    }
}
