package ru.sgpackage.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.sgpackage.base.Sprite;
import ru.sgpackage.math.Rect;
import ru.sgpackage.pool.BulletPool;
import ru.sgpackage.pool.ExplosionPool;

public class Enemy extends Ship {

    private enum State {DESCENT, FIGHT}

    private Vector2 v0 = new Vector2();
    private State state;                    //задать начальную скорость до появления, и после
    private Vector2 descentV = new Vector2(0, -0.5f);      //скорость выхода на экран
    private MainShip mainShip;

    public Enemy(Sound shootSound, BulletPool bulletPool, ExplosionPool explosionPool, MainShip mainShip) {
        super();
        this.shootSound = shootSound;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.mainShip = mainShip;
        this.v.set(v0);
        this.bulletV = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        this.pos.mulAdd(v, delta);

        //задание скорости для различных состояний противника (выход на экран/бой)
        switch (state) {
            case DESCENT:       //выход на экран
                if (getTop() <= worldBounds.getTop()){
                    v.set(v0);
                    state = State.FIGHT;
                }
                break;
            case FIGHT:         //режим бой
                if(isOutside(worldBounds)) {
                    mainShip.damage(this.damage);
                    destroy();
                    //boom();     //!!этот метод временный, убрать его, заменив на метод нанесения урона игроку при прохождении врагами нижней части экрана
                }
                if(this.pos.y < worldBounds.getTop()) {        //условие, запрещающее сттрельбу до тех пор, пока центр корабля не появится на экране
                    reloadTimer += delta;
                    if (reloadTimer >= reloadInterval) {     //при совпадении значения таймера стрельбы и заданного интервала стрельбы
                        reloadTimer = 0f;                    //обнуляется таймер
                        shoot();                             //происходит стрельба
                    }
                }
                break;
        }

        //старая запись боя, без предварительного вывода на экран, пригодится
        /*
        if(isOutside(worldBounds)) {
            destroy();
            boom();     //!!этот метод временный, убрать его, заменив на метод нанесения урона игроку при прохождении врагами нижней части экрана
        }
        if(this.pos.y < worldBounds.getTop()) {        //условие, запрещающее сттрельбу до тех пор, пока центр корабля не появится на экране
            reloadTimer += delta;
            if (reloadTimer >= reloadInterval) {     //при совпадении значения таймера стрельбы и заданного интервала стрельбы
                reloadTimer = 0f;                    //обнуляется таймер
                shoot();                             //происходит стрельба
            }
        }
        */
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
        //v.set(v0);        //старая форма для однорежимного состояния боя (пригодится)
        v.set(descentV);            //для многорежимного геймплея
        state = State.DESCENT;      //для многорежимного геймплея
        this.worldBounds = worldBounds;
    }

    //метод, позволяет узнать, долетела ли пуля игрока до верхней половины вражеского корабля
    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
        || bullet.getLeft() > getRight()
        || bullet.getBottom() > getTop()
        || bullet.getTop() < pos.y
        );
    }
}
