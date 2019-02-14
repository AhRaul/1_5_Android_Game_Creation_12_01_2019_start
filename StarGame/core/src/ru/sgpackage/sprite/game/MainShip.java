package ru.sgpackage.sprite.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.sgpackage.math.Rect;
import ru.sgpackage.pool.BulletPool;
import ru.sgpackage.pool.ExplosionPool;

public class MainShip extends Ship {

    private static final int MAX_HP_FOR_MAIN = 100;

    private final Vector2 v0 = new Vector2(0.5f, 0f);

    private boolean isPressedLeft;      //хранение состояния нажатости кнопки
    private boolean isPressedRight;

    private int pointer;                            //хранение номера последнего активного пальца


    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.reloadInterval = 0.2f;
        setHeightProportion(0.15f);
        this.shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/LaserShoot7.wav"));
        this.bulletV = new Vector2(0, 0.5f);
        this.bulletHeight = 0.01f;
        this.damage = 1;
        this.hp = MAX_HP_FOR_MAIN;
    }

    public void resize(Rect worldBounds)  {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        reloadTimer += delta;       //таймер, связанный с приходящей времени delta;
        if(reloadTimer >= reloadInterval) {     //при совпадении значения таймера стрельбы и заданного интервала стрельбы
            reloadTimer = 0f;
            shoot();
        }
        if (getLeft() < worldBounds.getLeft()) { //условие для остановки движения корабля при приближении к границе экрана
            setLeft(worldBounds.getLeft());     //v.1.5.оптимизировано для сведения к 0 шанса выхода за контролируемую область экрана, и застревании корабля.
            stop();
        }
        if (getRight() > worldBounds.getRight()) { //условие для остановки движения корабля при приближении к границе экрана
            setRight(worldBounds.getRight());
            stop();
        }
    }

    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                isPressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                isPressedRight = true;
                moveRight();
                break;
//            case Input.Keys.UP: //стрельба при нажатии стрелки вверх
//                shoot();
//                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                isPressedLeft = false;
                if(isPressedRight) {    //можно доработать метод с учетом непрерывного нажатия одной из кнопок.
                    moveRight();
                } else {
                    stop();
                }
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                isPressedRight = false;
                if(isPressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
        return false;
    }

    //Дома проверить (корабль должен двигаться в направлении последней нажатой стороны)
    public boolean touchDown(Vector2 touch, int pointer) {
        //вызов тач даун корабля
        if(!isPressedLeft && leftPlace(touch)) { //если левая кнопка не нажата || если попали по левой области
            this.pointer = pointer;         	//при правильном нажатии сохранение номера пальца
            this.isPressedLeft = true;          //сохранение состояния нажатости кнопки
            this.isPressedRight = false;          //сохранение состояния нажатостии кнопки
            moveLeft();
            return super.touchDown(touch, pointer);
        } else if(!isPressedRight && !leftPlace(touch)) {	//если правая кнопка не нажата || если не попали по левой области
            this.pointer = pointer;         	//при правильном нажатии сохранение номера пальца
            this.isPressedLeft = false;
            this.isPressedRight = true;          //сохранение состояния нажатости кнопки
            moveRight();
            return super.touchDown(touch, pointer);
        } else {
            return false;
        }
    }

    //проверить дома (последний нажимавший палец, отпуская экран, все останавливает)
    public boolean touchUp(int pointer) {
        //вызов тач ап корабля
        if(this.pointer == pointer) {
            this.isPressedLeft = false;
            this.isPressedRight = false;
            stop();
        }
        return false;
    }

    //движение корабля вправо
    private void moveRight() {
        if(pos.x > worldBounds.getRight()-0.065f) {      //условие блокировки движения при нажатии, если корабль уже прижат к границе
            return;
        }
        v.set(v0);
    }

    //движение корабля влево
    private void moveLeft() {
        if(pos.x < worldBounds.getLeft()+0.065f) {       //условие блокировки тача
            return;
        }
        v.set(v0).rotate(180);
    }

    //остановка корабля
    private void stop() {
        v.setZero();            //что эффективнее: setZero() или set(0,0)? Зачем нужен отдельный целый метод?
    }



    //возвращает true, при попадании пальца в левую область экрана
    private boolean leftPlace (Vector2 vector) {
        if(0.0f >= vector.x) {
            return true;
        } else {
            return false;
        }
    }

    //метод, позволяет узнать, долетела ли пуля противника до нижней половины корабля игрока
    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > pos.y
                || bullet.getTop() < getBottom()
        );
    }

    public void restartGame() {     //обновление корабля при перезапуске игры
        stop();
        pos.x = worldBounds.pos.x;
        this.hp = MAX_HP_FOR_MAIN;
        flushDestroy();
    }

    public int getHP() {
        return this.hp;
    }

    public int getHPPercent() {
        return (int)(((double)this.hp/(double)MAX_HP_FOR_MAIN)*100);
    }
}
