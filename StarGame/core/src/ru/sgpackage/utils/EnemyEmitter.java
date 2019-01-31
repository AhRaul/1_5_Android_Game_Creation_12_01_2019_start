package ru.sgpackage.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.sgpackage.math.Rect;
import ru.sgpackage.math.Rnd;
import ru.sgpackage.pool.EnemyPool;
import ru.sgpackage.sprite.game.Enemy;

public class EnemyEmitter {

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;    //размер маленького корабля
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;    //размер пули маленького корабля
    private static final float ENEMY_SMALL_BULLET_VY = -0.3f;    //направление маленькой пули
    private static final int ENEMY_SMALL_DAMAGE = 1;    //урон маленькой пули
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 3f;    //перезарядка малой пули
    private static final int ENEMY_SMALL_HP = 1;    //перезарядка малой пули

    private Vector2 enemySmallV = new Vector2(0, -0.2f);        //вектор скорости движения малого корабля
    private TextureRegion[] enemySmallRegion;

    private TextureRegion bulletRegion;

    private float generateInterval = 4f;    //раз в 4 сек будет генерироваться корабль
    private float generateTimer;

    //настройка корабля
    private EnemyPool enemyPool;

    private Rect worldBounds;

    public EnemyEmitter(TextureAtlas atlas, EnemyPool enemyPool, Rect worldBounds) {
        this.enemyPool = enemyPool;
        TextureRegion textureRegion = atlas.findRegion("enemy0");
        this.enemySmallRegion = Regions.split(textureRegion, 1, 2, 2);
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.worldBounds = worldBounds;
    }

    //метод генерирования кораблей
    public void generate(float delta) {
        generateTimer += delta;
        if(generateTimer >= generateInterval) {
            generateTimer = 0f;
            Enemy enemy = enemyPool.obtain();        //достаем из пула обьектов новый корабль
            enemy.set(
                    enemySmallRegion,
                    enemySmallV,
                    bulletRegion,
                    ENEMY_SMALL_BULLET_HEIGHT,
                    ENEMY_SMALL_BULLET_VY,
                    ENEMY_SMALL_DAMAGE,
                    ENEMY_SMALL_RELOAD_INTERVAL,
                    ENEMY_SMALL_HEIGHT,
                    ENEMY_SMALL_HP
            );
            //интервал появления корабля по осям x и y
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
            System.out.println("СОЗДАН КОРАБЛЬ МАЛЫЙ ВРАЖЕСКИЙ");
        }
    }
}
