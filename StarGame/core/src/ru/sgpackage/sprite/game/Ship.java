package ru.sgpackage.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.sgpackage.base.Sprite;
import ru.sgpackage.math.Rect;
import ru.sgpackage.pool.BulletPool;

public class Ship extends Sprite {

    protected Rect worldBounds;

    protected BulletPool bulletPool;

    protected Vector2 v = new Vector2();

    protected TextureRegion bulletRegion;

    protected float reloadInterval;
    protected float reloadTimer;

    protected Sound shootSound;

    protected Vector2 bulletV;
    protected float bulletHeight;
    protected int damage;
    protected int hp;

    public Ship() {
        super();
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    public void resize(Rect worldBounds)  {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    public void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
        shootSound.play(0.1f);
    }

    @Override
    public void dispose() {
        shootSound.dispose();
    }
}
