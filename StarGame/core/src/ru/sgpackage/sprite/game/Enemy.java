package ru.sgpackage.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.sgpackage.math.Rect;
import ru.sgpackage.pool.BulletPool;

public class Enemy extends Ship {

    private Vector2 v0 = new Vector2();
    private Rect worldBounds;

    public Enemy(Sound shootSound, BulletPool bulletPool) {
        super();
        this.shootSound = shootSound;
        this.bulletPool = bulletPool;
        this.v.set(v0);
        this.bulletV = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        this.pos.mulAdd(v, delta);
        //нужен запас высоты для новых обьектов, иначе они вылетают из пула не успев в него попасть, т.к. вражеские корабли появляются за видимой областью экрана
        if(isOutside(worldBounds)) {
            destroy();
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int bulletDamage,
            float reloadInterval,
            float height,
            int hp,
            Rect worldBounds
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = bulletDamage;
        this.reloadInterval = reloadInterval;
        setHeightProportion(height);
        this.hp = hp;
        reloadTimer = reloadInterval;       //выстрелы при появлении корабля
        v.set(v0);
        this.worldBounds = worldBounds;
    }
}
