package ru.sgpackage.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.sgpackage.math.MatrixUtils;
import ru.sgpackage.math.Rect;

public class Base2DScreen implements Screen, InputProcessor {

    protected SpriteBatch batch;

    private Rect screenBounds;      //границы области рисования в пикселях
    protected Rect worldBounds;       //границы проекции координат игрового мира
    private Rect glBounds;          //Квадрат на который все проецируется, границы OpenGL

    private Matrix4 worldToGL;      //матрица 4 на 4 для перевода из мировой системы координат в OpenGL
    private Matrix3 screenToWorlds;  //матрица перевода щелчков

    private Vector2 touch;           //вспомогательный вектор для пересчета щелчков

    @Override
    public void show() {
        System.out.println("show");
        Gdx.input.setInputProcessor(this);

        batch = new SpriteBatch();              //перенос инициализации из MenuScreen
        this.screenBounds = new Rect();         //инициализация
        this.worldBounds = new Rect();
        this.glBounds = new Rect(0,0,1f, 1f);   //инициализация пространства OpenGL
        this.worldToGL = new Matrix4();
        this.screenToWorlds = new Matrix3();
        touch = new Vector2();
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize w = " + width + " h = " + height);
        screenBounds.setSize(width, height);    //после получения текцщих границ игрового мира, расчет aspect
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);      //?? whata...??  почему влияет на положение Y при клике?

        float aspect = width / (float) height;
        worldBounds.setHeight(1f);              //заданы границы игрового мира
        worldBounds.setWidth(1f * aspect);

        MatrixUtils.calcTransitionMatrix(worldToGL, worldBounds, glBounds);     //расчет матрицы
        batch.setProjectionMatrix(worldToGL);

        MatrixUtils.calcTransitionMatrix(screenToWorlds, screenBounds, worldBounds);    //расчет матрицы для кликов
        resize(worldBounds);
    }

    //аргумент - границы игрового мира  (для переопределения в MenuScreen)
    public void resize(Rect worldBounds) {

    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void hide() {
        System.out.println("hide");
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown keycode = " + keycode);     //добавить управление клавиатурой
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {                       //отжатие кнопки
        System.out.println("keyUp keycode = " + keycode);     //добавить управление клавиатурой
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped character = " + character);     //символ
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown screenX = " + screenX + " screenY = " + screenY);
        //при прикосновении расчет
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorlds);     //?? не понимаю как работает, надо сидеть, копаться, разбираться, эксперементировать
        touchDown(touch, pointer);
        return false;
    }

    //перегрузка lection3 1:51:00
    public boolean touchDown(Vector2 touch, int pointer) {
        System.out.println("touchDown touch.x = " + touch.x + " touch.y = " + touch.y);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchUp screenX = " + screenX + " screenY = " + screenY);
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorlds);     //?? не понимаю как работает, надо сидеть, копаться, разбираться, эксперементировать
        touchUp(touch, pointer);
        return false;
    }

    //1:52:20 1_5_3
    public boolean touchUp(Vector2 touch, int pointer) {
        System.out.println("touchUp touch.x = " + touch.x + " touch.y = " + touch.y);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchDragged screenX = " + screenX + " screenY = " + screenY);
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorlds);     //?? не понимаю как работает, надо сидеть, копаться, разбираться, эксперементировать
        touchDragged(touch, pointer);
        return false;
    }

    //1:52:35 1_5_3
    public boolean touchDragged(Vector2 touch, int pointer) {
        System.out.println("touchDragged touch.x = " + touch.x + " touch.y = " + touch.y);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
