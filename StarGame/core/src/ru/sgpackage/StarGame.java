package ru.sgpackage;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.sgpackage.base.Base2DScreen;
import ru.sgpackage.screen.GameScreen;
import ru.sgpackage.screen.MenuScreen;

public class StarGame extends Game /*extends ApplicationAdapter*/ {

	private GameScreen gameScreen;
	private MenuScreen menuScreen;

	@Override
	public void create() {
setScreen(new MenuScreen(this));
	}

	public void setGameScreen()
	{
		gameScreen=new GameScreen(this);
		setScreen(gameScreen);
	}

	public void setMenuScreen()
	{
		menuScreen=new MenuScreen(this);
		setScreen(menuScreen);
	}

//	SpriteBatch batch;
//	Texture img;
//	Texture background;
//
//	@Override
//	public void create () {
//		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");
//		background = new Texture("starbg.jpg");
//	}
//
//	@Override
//	public void render () {
//		//Gdx.gl.glClearColor(1, 0, 0, 1);
//		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//		batch.draw(background, 0, 0);
//		batch.draw(img, 0, 0);
//		batch.end();
//	}
//
//	@Override
//	public void dispose () {
//		batch.dispose();
//		background.dispose();
//		img.dispose();
//	}

}
