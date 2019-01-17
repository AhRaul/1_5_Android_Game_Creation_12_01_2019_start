package ru.sgpackage.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.sgpackage.base.Base2DScreen;

public class MenuScreen extends Base2DScreen {
	SpriteBatch batch;
	Texture img;
	Texture background;

	Vector2 pos;
	Vector2 speed;

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		background = new Texture("starbg.jpg");
		pos = new Vector2(0, 0);
		speed = new Vector2(2,2);   //speed and direction

    }

    @Override
    public void render(float delta) {   //60 раз в сек
        super.render(delta);
        batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(img, pos.x, pos.y);
		batch.end();
		if(Gdx.graphics.getWidth() - 256 > pos.x && Gdx.graphics.getHeight() - 256 > pos.y && pos.x >= 0 && pos.y >= 0) {
            pos.add(speed);
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
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
