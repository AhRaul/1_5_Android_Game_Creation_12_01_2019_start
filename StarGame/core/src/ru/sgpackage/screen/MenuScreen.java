package ru.sgpackage.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.sgpackage.StarGame;
import ru.sgpackage.base.Base2DScreen;
import ru.sgpackage.math.Rect;
import ru.sgpackage.sprite.Background;
import ru.sgpackage.sprite.Star;
import ru.sgpackage.sprite.menu.BtnClose;
import ru.sgpackage.sprite.menu.BtnStart;

//Класс окошка меню игры
public class MenuScreen extends Base2DScreen {

    private TextureAtlas atlas;
	private Texture bg;
	private Background background;
	private Star star[];      //множество звезд
    private BtnClose btnClose;
    private BtnStart btnStart;

    private StarGame starGame;

    public MenuScreen(StarGame starGame) {
        this.starGame = starGame;
    }

    @Override
    public void show() {
        super.show();

		bg = new Texture("starbg.jpg");
		background = new Background(new TextureRegion(bg));
		atlas = new TextureAtlas("menuAtlas.tpack");   //добавление трека
        star = new Star[256];
        for(int i = 0; i < star.length; i++) {
            star[i] = new Star(atlas);
        }
        btnClose = new BtnClose(atlas);
        btnStart = new BtnStart(atlas, this);

    }

    @Override
    public void render(float delta) {   //60 раз в сек
        super.render(delta);
        update (delta);
        draw();

    }

    public void update (float delta) {
        for (int i = 0; i < star.length; i++) {
            star[i].update(delta);
        }
    }

    public void draw () {
        Gdx.gl.glClearColor(0.5f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);     //0:41:42 бакгроунд сам знает как себя нарисовать
        for (int i = 0; i < star.length; i++) {
            star[i].draw(batch);
        }
        btnClose.draw(batch);
        btnStart.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
        btnClose.resize(worldBounds);
        btnStart.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        btnClose.touchDown(touch, pointer);                 //при нажатии проверка действия кнопки меню
        btnStart.touchDown(touch, pointer);                 //при нажатии проверка действия кнопки меню
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        btnClose.touchUp(touch, pointer);                   //при отпускании проверка действия кнопки меню
        btnStart.touchUp(touch, pointer);                   //при отпускании проверка действия кнопки меню
        return super.touchUp(touch, pointer);
    }

    public void changeScreen() {
        //this.dispose();       //?? нужно ли очистить память от старого окна? (вопрос на лекцию)
                                //исключено, т.к. вызывает ошибку:
                                //Exception in thread "LWJGL Application" java.lang.IllegalArgumentException:
                                //buffer not allocated with newUnsafeByteBuffer or already disposed
        starGame.setGameScreen();
    }
}
