package ru.sgpackage.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.sgpackage.base.Base2DScreen;

import static com.badlogic.gdx.Input.Keys.DOWN;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static com.badlogic.gdx.Input.Keys.UP;

public class MenuScreen extends Base2DScreen {
	SpriteBatch batch;
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
        batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		background = new Texture("starbg.jpg");
		pos = new Vector2(0, 0);
        destination = new Vector2(0,0);     //точка прибытия
        speed = new Vector2(0,0);           //скорость и направление

    }

    @Override
    public void render(float delta) {   //60 раз в сек
        super.render(delta);
        batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(img, pos.x, pos.y);
		batch.end();
            if (Gdx.graphics.getWidth() - 256 > pos.x && Gdx.graphics.getHeight() - 256 > pos.y && pos.x > 0 && pos.y > 0 && steps > 0) {
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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown " + screenX + " " + (Gdx.graphics.getHeight() - screenY));

        destination.set(screenX-128-pos.x, (Gdx.graphics.getHeight() - screenY-128-pos.y));         //выбор конечной точки
        speed.set(destination).nor().scl(2f);
        pos.add(speed);
        range = destination.len();
        step = speed.len();
        steps = range / step;

        return super.touchDown(screenX, screenY, pointer, button);
    }

//    DOWN; LEFT; RIGHT; UP;


    @Override
    public boolean keyDown(int keycode) {
        if (keycode == DOWN) {
            destination.set(0, -pos.y);
            speed.set(destination).nor().scl(2f);
            pos.add(speed);
            range = destination.len();
            step = speed.len();
            steps = range / step;
        } else if (keycode == LEFT) {
            destination.set(-pos.x, 0);
            speed.set(destination).nor().scl(2f);
            pos.add(speed);
            range = destination.len();
            step = speed.len();
            steps = range / step;
        } else if (keycode == RIGHT) {
            destination.set((Gdx.graphics.getWidth() - 256 - pos.x), 0);
            speed.set(destination).nor().scl(2f);
            pos.add(speed);
            range = destination.len();
            step = speed.len();
            steps = range / step;
        } else if (keycode == UP) {
            destination.set(0, Gdx.graphics.getHeight() - 256 - pos.y);
            speed.set(destination).nor().scl(2f);
            pos.add(speed);
            range = destination.len();
            step = speed.len();
            steps = range / step;
        }
        return super.keyDown(keycode);
    }
}
