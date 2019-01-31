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

    private static final float ENEMY_MIDDLE_HEIGHT = 0.15f;
    private static final float ENEMY_MIDDLE_BULLET_HEIGHT = 0.03f;
    private static final float ENEMY_MIDDLE_BULLET_VY = -0.25f;
    private static final int ENEMY_MIDDLE_DAMAGE = 3;
    private static final float ENEMY_MIDDLE_RELOAD_INTERVAL = 4f;
    private static final int ENEMY_MIDDLE_HP = 2;

    private static final float ENEMY_BIG_HEIGHT = 0.2f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.05f;
    private static final float ENEMY_BIG_BULLET_VY = -0.2f;
    private static final int ENEMY_BIG_DAMAGE = 5;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 5f;
    private static final int ENEMY_BIG_HP = 3;

    private Vector2 enemySmallV = new Vector2(0, -0.2f);        //вектор скорости движения малого корабля
    private Vector2 enemyMiddleV = new Vector2(0, -0.175f);        //вектор скорости движения среднего корабля
    private Vector2 enemyBigV = new Vector2(0, -0.15f);        //вектор скорости движения большого корабля
    private TextureRegion[] enemySmallRegion;
    private TextureRegion[] enemyMiddleRegion;
    private TextureRegion[] enemyBigRegion;

    private TextureRegion bulletRegion;

    private float generateInterval = 3f;    //раз в 3 сек будет генерироваться корабль
    private float generateTimer;

    private int randomEnemy;                //хранит случайное число для поеределения цели

    //настройка корабля
    private EnemyPool enemyPool;

    private Rect worldBounds;

    public EnemyEmitter(TextureAtlas atlas, EnemyPool enemyPool, Rect worldBounds) {
        this.enemyPool = enemyPool;
        TextureRegion textureSmallRegion = atlas.findRegion("enemy0");
        this.enemySmallRegion = Regions.split(textureSmallRegion, 1, 2, 2);
        TextureRegion textureMiddleRegion = atlas.findRegion("enemy1");
        this.enemyMiddleRegion = Regions.split(textureMiddleRegion, 1, 2, 2);
        TextureRegion textureBigRegion = atlas.findRegion("enemy2");
        this.enemyBigRegion = Regions.split(textureBigRegion, 1, 2, 2);
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.worldBounds = worldBounds;
    }

    //метод генерирования кораблей
    public void generate(float delta) {
        generateTimer += delta;
        if(generateTimer >= generateInterval) {
            generateTimer = 0f;
            Enemy enemy = enemyPool.obtain("Enemy");        //достаем из пула обьектов новый корабль
            randomEnemy = (int)(Math.random()*100);                 //случайное число интервал
            if(randomEnemy<40) {                                    //вероятность появления малого корабля 40%
                enemy.set(
                        enemySmallRegion,
                        enemySmallV,
                        bulletRegion,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        ENEMY_SMALL_BULLET_VY,
                        ENEMY_SMALL_DAMAGE,
                        ENEMY_SMALL_RELOAD_INTERVAL,
                        ENEMY_SMALL_HEIGHT,
                        ENEMY_SMALL_HP,
                        worldBounds
                );
            } else if (randomEnemy>=40 && randomEnemy < 80) {     //вероятность появления среднего корабля 30%
                enemy.set(
                        enemyMiddleRegion,
                        enemyMiddleV,
                        bulletRegion,
                        ENEMY_MIDDLE_BULLET_HEIGHT,
                        ENEMY_MIDDLE_BULLET_VY,
                        ENEMY_MIDDLE_DAMAGE,
                        ENEMY_MIDDLE_RELOAD_INTERVAL,
                        ENEMY_MIDDLE_HEIGHT,
                        ENEMY_MIDDLE_HP,
                        worldBounds
                );
            } else {                                            //вероятность появления большого корабля 20%
                enemy.set(
                        enemyBigRegion,
                        enemyBigV,
                        bulletRegion,
                        ENEMY_BIG_BULLET_HEIGHT,
                        ENEMY_BIG_BULLET_VY,
                        ENEMY_BIG_DAMAGE,
                        ENEMY_BIG_RELOAD_INTERVAL,
                        ENEMY_BIG_HEIGHT,
                        ENEMY_BIG_HP,
                        worldBounds
                );
            }
            //интервал появления корабля по осям x и y
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }
}
