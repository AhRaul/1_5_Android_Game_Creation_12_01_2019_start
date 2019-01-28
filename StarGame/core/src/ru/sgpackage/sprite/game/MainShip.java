package ru.sgpackage.sprite.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.sgpackage.base.Sprite;
import ru.sgpackage.math.Rect;
import ru.sgpackage.pool.BulletPool;

public class MainShip extends Sprite {

    private Rect worldBounds;

    private final Vector2 v0 = new Vector2(0.5f, 0f);

    private Vector2 v = new Vector2();

    private boolean isPressedLeft;      //хранение состояния нажатости кнопки
    private boolean isPressedRight;

    private BulletPool bulletPool;

    private TextureRegion bulletRegion;

    private int pointer;                            //хранение номера последнего активного пальца

    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletPool = bulletPool;
        setHeightProportion(0.15f);
    }

    public void resize(Rect worldBounds)  {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.05f);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
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
            case Input.Keys.UP: //стрельба при нажатии стрелки вверх
                shoot();
                break;
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
            this.isPressedLeft = true;          //сохранение состояния нажаточни кнопки
            this.isPressedRight = false;          //сохранение состояния нажаточни кнопки
            moveLeft();
            return super.touchDown(touch, pointer);						//??не понимаю такой ретурн, здесь размещается boolean??
        } else if(!isPressedRight && !leftPlace(touch)) {	//если правая кнопка не нажата || если не попали по левой области
            this.pointer = pointer;         	//при правильном нажатии сохранение номера пальца
            this.isPressedLeft = false;
            this.isPressedRight = true;          //сохранение состояния нажаточни кнопки
            moveRight();
            return super.touchDown(touch, pointer);						//??не понимаю такой ретурн, здесь размещается boolean??
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
        v.set(v0);
    }

    //движение корабля влево
    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    //остановка корабля
    private void stop() {
        v.setZero();            //что эффективнее: setZero() или set(0,0)? Зачем нужен отдельный целый метод?
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.setDamage(this, bulletRegion, pos, new Vector2(0, 0.5f), 0.01f, worldBounds, 1);
    }

    //возвращает true, при попадании вектора в левую область экрана
    private boolean leftPlace (Vector2 vector) {
        if(worldBounds.getLeft() <= vector.x && 0.0f >= vector.x) {
            return true;
        } else {
            return false;
        }
    }

}
