package ru.sgpackage.sprite.menu;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.sgpackage.base.Sprite;

public abstract class ScaledTouchUpButton extends Sprite {

    private static final float PRESS_SCALE = 0.9f;  //изменение размера кнопки до 90 % от исходного при нажатии

    private int pointer;                            //хранение номера нажимающего пальца
    private boolean isPressed;                      //состояние нажатости кнопки

    public ScaledTouchUpButton(TextureRegion region) {
        super(region);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if(isPressed || !isMe(touch)) {  //если кнопка уже нажата || если не попали по кнопке
            return false;
        }
        this.pointer = pointer;         //при правильном нажатии сохранение номера пальца
        this.scale = PRESS_SCALE;       //задается размер кнопки при нажатии
        this.isPressed = true;          //сохранение состояния нажаточни кнопки
        return super.touchDown(touch, pointer);

    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if(this.pointer != pointer || !isPressed) {
            return false;
        }
        if(isMe(touch)) {
            //TODO действие
            action();
        }
        this.isPressed = false;
        this.scale = 1f;
        return super.touchUp(touch, pointer);
    }



    public abstract void action();
}
