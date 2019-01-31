package ru.sgpackage.sprite.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.sgpackage.base.Sprite;

public class Explosion extends Sprite {

    //таймер
    private float animateInterval = 0.017f;
    private float animateTimer;
    private Sound explosionSound;

    public Explosion(TextureRegion region, int rows, int cols, int frames, Sound explosionSound) {
        super(region, rows, cols, frames);
        this.explosionSound = explosionSound;
    }

    //set для получения взрыва из пола обьектов
    public void set(float height, Vector2 pos) {
        this.pos.set(pos);
        setHeightProportion(height);        //размер взрыва с размер корабля
        explosionSound.play();
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if(animateTimer >= animateInterval) {
            animateTimer = 0f;
            if(++frame == regions.length) {
                destroy();
            }
        }
     }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }

}
