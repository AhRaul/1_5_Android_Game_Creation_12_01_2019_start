package ru.sgpackage.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.sgpackage.math.Rect;
import ru.sgpackage.utils.Regions;

//отсюда наследуются только классы, отвечающие только за графические обьекты 1_5_4_ 0:51:00
public class Sprite extends Rect {

    protected float angle;      //угол поворота графического обьекта
    protected float scale = 1f;      //,скалировение размера обьекта
    protected TextureRegion[] regions;  //массив текстур из атласа ?
    protected int frame;            //номер текущего кадра
    private boolean isDestroyed;    //активность обьекта

    public Sprite() {
    }

    //конструктор, работающий с одной простой текстурой
    public Sprite(TextureRegion region) {
        if(region == null) {
            throw new NullPointerException("Create Sprite with null region");
        }
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    //конструктор для разбиения сплошной текстуры на регионы
    public Sprite(TextureRegion region, int rows, int cols, int frames) {
        if (region == null) {
            throw new NullPointerException("Create Sprite with null region");
        }
        this.regions = Regions.split(region, rows, cols, frames);
    }

    //метод задает ширину и высоту
    public void setHeightProportion(float height) {
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();  //??
        setWidth(height * aspect);
    }

    //установка границ экрана
    public void resize(Rect worldBounds) {

    }

    public void update(float delta) {

    }

    public boolean touchDown(Vector2 touch, int pointer) {
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],             //текущий регион
                getLeft(), getBottom(),     // точка отрисовки, центровка обьекта
                halfWidth, halfHeight,      // точка вращения
                getWidth(), getHeight(),    //ширина, высота
                scale, scale,               //масштаб по осям x и y
                angle                       //угол вращения
        );
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void destroy() {         //пометка обьекта на удаление при попадании за экран
        this.isDestroyed = true;
    }

    public void flushDestroy() {    //сброс метки удаления для повторного использования обьекта
        this.isDestroyed = false;
    }

    public boolean isDestroyed() {  //гетер, уничтожен ли объект
        return isDestroyed;
    }

    public void disposeShipSounds() {

    }


}
