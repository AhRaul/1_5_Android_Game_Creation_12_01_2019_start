package ru.sgpackage.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.sgpackage.base.Sprite;
import ru.sgpackage.math.Rect;

public class HPOneScale extends Sprite {                                //ячейка здоровья

    private int state;          //состояние ячейки: 0-полная, ... 9-пустая, 10 - должна исчезнуть.
    private int frames;

    public HPOneScale(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);          //получить часть текстуры
        this.frames = frames;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
    }


    public void setState(int state) {
        this.state = state;
        if (this.state <= frames && this.state >=0) {
            this.frame = state;                     //9 state =  9 фрейм, 0 state = 0 фрейм
        }
    }

    public int getState() {
        return this.state;
    }
}
