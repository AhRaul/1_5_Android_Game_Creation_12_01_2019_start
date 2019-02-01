package ru.sgpackage.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.sgpackage.math.Rect;
import ru.sgpackage.pool.BulletPool;

public class Enemy extends Ship {

    private Vector2 v0 = new Vector2();

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

        if(isOutside(worldBounds)) {
            destroy();
        }
        if(this.pos.y < worldBounds.getTop()) {        //условие, запрещающее сттрельбу до тех пор, пока центр корабля не появится на экране
            reloadTimer += delta;
            if (reloadTimer >= reloadInterval) {     //при совпадении значения таймера стрельбы и заданного интервала стрельбы
                reloadTimer = 0f;                    //обнуляется таймер
                shoot();                             //происходит стрельба
            }
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
        reloadTimer = reloadInterval;                   //выстрелы при появлении корабля
        v.set(v0);
        this.worldBounds = worldBounds;
    }
}
