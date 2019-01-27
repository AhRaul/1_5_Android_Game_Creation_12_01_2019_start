package ru.sgpackage;

import com.badlogic.gdx.Game;

import ru.sgpackage.screen.GameScreen;
import ru.sgpackage.screen.MenuScreen;

public class StarGame extends Game {

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

}
