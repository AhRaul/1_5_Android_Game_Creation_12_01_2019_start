package ru.sgpackage.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.sgpackage.base.Sprite;
import ru.sgpackage.math.Rect;
import ru.sgpackage.pool.BulletPool;
import ru.sgpackage.pool.ExplosionPool;

public class Ship extends Sprite {

    protected Rect worldBounds;

    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected Vector2 v = new Vector2();

    protected TextureRegion bulletRegion;

    protected float reloadInterval;
    protected float reloadTimer;

    private float damageInterval = 0.1f;
    private float damageTimer = damageInterval;

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

    @Override
    public void update(float delta) {
        super.update(delta);
        damageTimer += delta;
        if (damageTimer >= damageInterval) {
            frame = 0;
        }
    }

    public void resize(Rect worldBounds)  {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    public void shoot() {
        Bullet bullet = bulletPool.obtain("bullet");
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
        shootSound.play(0.1f);
    }

    //взрыв корабля
    public void boom() {
        Explosion explosion = explosionPool.obtain("Ship boom");
        explosion.set(getHeight(), pos);
    }

    public void damage(int damage) {
        frame = 1;
        damageTimer = 0f;
        hp -= damage;
        if(hp <= 0) {
            destroy();
            boom();
        }
    }

    @Override
    public void dispose() {
        shootSound.dispose();
    }

    public int getDamage() {
        return damage;
    }
}
