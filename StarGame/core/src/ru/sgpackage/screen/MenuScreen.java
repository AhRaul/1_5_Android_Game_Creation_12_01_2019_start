package ru.sgpackage.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.sgpackage.base.Base2DScreen;

import static com.badlogic.gdx.Input.Keys.DOWN;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static com.badlogic.gdx.Input.Keys.UP;

public class MenuScreen extends Base2DScreen {

    public static float SPEED = 0.002f;
	// SpriteBatch batch; //перенесена для расчета матрицы в Base2DScreen
	Texture img;
	Texture background;

	Vector2 pos;

    Vector2 destination;
	Vector2 speed;

	float range;
	float step;
	float steps;

    @Override
    public void show() {
        super.show();
//        batch = new SpriteBatch();                        //  ДЗ3 перенос инициализации в Base2DScreen
        //batch.getProjectionMatrix().idt();                //ДЗ3 Единичное преобразование матрицы, (после переноса не нужно)
                                                            //прямая отрисовка в OpenGL без пиксельного преобразования
		img = new Texture("badlogic.jpg");
		background = new Texture("starbg.jpg");
		pos = new Vector2(-0.5f, -0.5f);
        speed = new Vector2(0f, 0f);                  //скорость и направление

        destination = new Vector2(-0.5f,-0.5f);       //точка прибытия

    }

    @Override
    public void render(float delta) {   //60 раз в сек
        super.render(delta);
        Gdx.gl.glClearColor(0.5f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
		batch.draw(background, -0.5f, -0.5f, 1f, 1f);
		batch.draw(img, pos.x, pos.y, 0.5f, 0.5f);
		batch.end();

            if (0f > pos.x && 0f > pos.y && pos.x > -0.5f && pos.y > -0.5f && steps > 0) {
                pos.add(speed);
                steps--;
            }
    }

    @Override
    public void dispose() {
        batch.dispose();
		img.dispose();
        super.dispose();
    }


//    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        System.out.println("touchDown " + screenX + " " + (Gdx.graphics.getHeight() - screenY));
//
//        /* ДЗ2
//        destination.set(screenX-128-pos.x, (Gdx.graphics.getHeight() - screenY-128-pos.y));         //выбор конечной точки
//        speed.set(destination).nor().scl(2f);
//        pos.add(speed);
//        range = destination.len();
//        step = speed.len();
//        steps = range / step;
//        */
//
//        return super.touchDown(screenX, screenY, pointer, button);
//    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {

        moving(touch.x-0.25f-pos.x,touch.y-0.25f-pos.y);
//        destination.set(touch.x-pos.x, touch.y-pos.y);         //выбор конечной точки
//        speed.set(destination).nor().scl(SPEED);
//        pos.add(speed);
//        range = destination.len();
//        step = speed.len();
//        steps = range / step;

        return super.touchDown(touch, pointer);
    }



//    DOWN; LEFT; RIGHT; UP;


    @Override
    public boolean keyDown(int keycode) {
        if (keycode == DOWN) {
            moving(0f,-1f-pos.y);
        } else if (keycode == LEFT) {
            moving(-1f-pos.x, 0);
        } else if (keycode == RIGHT) {
            moving(0f-pos.x, 0);
        } else if (keycode == UP) {
            moving(0, 0f-pos.y);
//            destination.set(0, Gdx.graphics.getHeight() - 256 - pos.y);
//            speed.set(destination).nor().scl(2f);
//            pos.add(speed);
//            range = destination.len();
//            step = speed.len();
//            steps = range / step;
        }
        return super.keyDown(keycode);
    }

    /**
     * метод расчета расстояния, дальности, координат, количества шагов к новой точке
     * (можно сжедать один шаг "pos.add(speed)" не смотря на запрет в render, если вдруг обьект окажется вне разрешенной для движения зоны)
     * @param newPosX   - координата Х выбранной точки будущего расположения
     * @param newPosY   - координата Y выбранной точки будущего расположения
     */
    private void moving(float newPosX, float newPosY) {
        destination.set(newPosX, newPosY);         //выбор конечной точки
        speed.set(destination).nor().scl(SPEED);
        pos.add(speed);                            //один шаг, для обхода запретов границ
        range = destination.len();
        step = speed.len();
        steps = range / step;
    }

}
