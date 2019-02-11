package ru.sgpackage.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.sgpackage.StarGame;
import ru.sgpackage.base.Base2DScreen;
import ru.sgpackage.math.Rect;
import ru.sgpackage.pool.BulletPool;
import ru.sgpackage.pool.EnemyPool;
import ru.sgpackage.pool.ExplosionPool;
import ru.sgpackage.sprite.Background;
import ru.sgpackage.sprite.Star;
import ru.sgpackage.sprite.death.BtnNewGame;
import ru.sgpackage.sprite.death.MessageGameOver;
import ru.sgpackage.sprite.game.Bullet;
import ru.sgpackage.sprite.game.Enemy;
import ru.sgpackage.sprite.game.HPMainShipScale;
import ru.sgpackage.sprite.game.MainShip;
import ru.sgpackage.sprite.menu.BtnClose;
import ru.sgpackage.utils.EnemyEmitter;
import ru.sgpackage.utils.Font;

//Экран активной игры
public class GameScreen extends Base2DScreen {

    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private TextureAtlas atlas;
    private Texture bg;
    private TextureAtlas hpAtlas;
    private Background background;
    private Star star[];      //множество звезд

    private StarGame starGame;

    private MainShip mainShip;

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;

    private Music music;

    private EnemyEmitter enemyEmitter;          //задать параметры вражеских кораблей

    private EnemyPool enemyPool;    //инициализация пула вражеских генерируемых кораблей

    private BtnNewGame btnNewGame;
    private MessageGameOver messageGameOver;
    private BtnClose btnClose;
    private TextureAtlas atlasClose;

    private HPMainShipScale hpMainShipScale;

    private Font font;                          //переменная для добавления шрифта
    private StringBuilder sbFrags = new StringBuilder();
    private StringBuilder sbHP = new StringBuilder();
    private StringBuilder sbLevel = new StringBuilder();

    int frags = 0;                              //подсчет количества убитых противников

    public GameScreen(StarGame starGame) {
        this.starGame = starGame;
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/MusicFonGame.mp3"));
        music.setLooping(true);
        music.play();
    }

    @Override
    public void show() {
        super.show();

        bg = new Texture("starbg.jpg");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("mainAtlas.tpack");   //добавление трека
        atlasClose = new TextureAtlas("menuAtlas.tpack");
        hpAtlas = new TextureAtlas("textures/hp10Var.pack");
        star = new Star[64];                                         //количество звёзд
        for(int i = 0; i < star.length; i++) {
            star[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        enemyPool = new EnemyPool(bulletPool, explosionPool, mainShip);

        enemyEmitter = new EnemyEmitter(atlas, enemyPool, worldBounds);

        btnNewGame = new BtnNewGame(atlas, mainShip, worldBounds);
        messageGameOver = new MessageGameOver(atlas);
        btnClose = new BtnClose(atlasClose);
        this.font = new Font("font/font.fnt", "font/font.png");
        this.font.setSize(0.02f);                                      //размер шрифта
        hpMainShipScale = new HPMainShipScale(hpAtlas, mainShip);

    }

    @Override
    public void render(float delta) {   //60 раз в сек
        super.render(delta);
        update (delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();

    }

    private void update (float delta) {
        for (Star aStar : star) {
            aStar.update(delta);
        }
        if(!mainShip.isDestroyed()) {
            mainShip.update(delta);
            enemyEmitter.generate(delta, frags);               //генератор вражеских кораблей по времени
            enemyPool.updateActiveSprites(delta);       //движение вражеских кораблей за счет передачи времени
        }
        bulletPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
    }

    public void checkCollisions() {
        //проверка столкновения кораблей
        List<Enemy> enemyList = enemyPool.getActiveObjects();   //получить список активных вражеских кораблей для проверки пересечения
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth(); //расчет минимальной дальности для взрыва при ударе кораблей
            if (enemy.pos.dst2(mainShip.pos) < minDist * minDist) {     //оптимальный аналог операции enemy.pos.dst(mainShip.pos) < minDist
                enemy.destroy();
                enemy.boom();
                mainShip.damage(enemy.getDamage());
                return;
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();    //список активных пуль

        //проверка прикосновения пули противника и корабля игрока
        for (Bullet bullet : bulletList) {
            if(bullet.getOwner() == mainShip || bullet.isDestroyed()) {
                continue;
            }
            if(mainShip.isBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
            }
        }

        //проверка прикосновения пули игрока и корабля противника
        for (Enemy enemy: enemyList){   //проверка пуль целей
            if(enemy.isDestroyed()) {
                continue;
            }
            for (Bullet bullet : bulletList) {
                if(bullet.getOwner() != mainShip || bullet.isDestroyed()) { //если пуля не принадлежит кораблю игрока, или уже уничтожена
                    continue;
                }
                if(enemy.isBulletCollision(bullet)) {      //если пуля за границей экрана
                    enemy.damage(mainShip.getDamage());
                    if(enemy.isDestroyed()) {               //если враг уничтожен пулей, +1 к счетчику убийств
                        frags++;
                    }
                    bullet.destroy();
                }
            }
        }

        //проверка закончилась ли игра, для уничтожения оставшихся целей
        if(mainShip.isDestroyed()) {
            for (Enemy enemy : enemyList) {
                enemy.destroy();
                enemy.boom();                   //бум, для красоты
            }
        }
    }

    private void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites("bullet");
        explosionPool.freeAllDestroyedActiveSprites("explosion");
        enemyPool.freeAllDestroyedActiveSprites("enemy");          //стирание неиспользованных пулов
    }

    private void draw() {
        Gdx.gl.glClearColor(0.5f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);     //0:41:42 бакгроунд сам знает как себя нарисовать
        for (Star aStar : star) {
            aStar.draw(batch);
        }
        if(!mainShip.isDestroyed()) {
            mainShip.draw(batch);
            enemyPool.drawActiveSprites(batch);         //отображение активных кораблей
        }
        bulletPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        if(mainShip.isDestroyed()) {                    //если корабль уничтожен, вывести кнопку перезапуска
            messageGameOver.draw(batch);
            btnNewGame.draw(batch);
            btnClose.draw(batch);
        }
        printInfo();
        hpMainShipScale.draw(batch);
        batch.end();
    }

    public void printInfo() {
        sbFrags.setLength(0);
        sbHP.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft(), worldBounds.getTop());
        font.draw(batch, sbHP.append(HP).append(mainShip.getHP()), worldBounds.pos.x, worldBounds.getTop(), Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight(), worldBounds.getTop(), Align.right);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        btnNewGame.resize(worldBounds);
        messageGameOver.resize(worldBounds);
        btnClose.resize(worldBounds);
        hpMainShipScale.resize(worldBounds);
    }

    //очистка памяти при завершении игры
    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        music.dispose();
        mainShip.dispose();
        font.dispose();             //не забыть очистить от надписей память
        messageGameOver.dispose();
        hpMainShipScale.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(!mainShip.isDestroyed()) {
            mainShip.keyDown(keycode);
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if(!mainShip.isDestroyed()) {
            mainShip.keyUp(keycode);
        }
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if(!mainShip.isDestroyed()) {
            mainShip.touchDown(touch, pointer);
        }
        if(mainShip.isDestroyed()) {
            btnNewGame.touchDown(touch, pointer);
            btnClose.touchDown(touch, pointer);
        }

        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if(!mainShip.isDestroyed()) {
            mainShip.touchUp(pointer);
        }
        if(mainShip.isDestroyed()) {
            btnNewGame.touchUp(touch, pointer);
            btnClose.touchUp(touch, pointer);
            frags = 0;
            enemyEmitter.setLevel(1);
        }
        return super.touchUp(touch, pointer);
    }

    //метод возврата в окно меню (пока не используется)
    public void changeScreen() {
        starGame.setMenuScreen();
    }
}
