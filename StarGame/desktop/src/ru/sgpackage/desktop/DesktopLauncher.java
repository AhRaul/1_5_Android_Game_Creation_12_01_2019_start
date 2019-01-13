package ru.sgpackage.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.sgpackage.StarGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("user.name", "Public");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 1000;
		config.width = 500;
		new LwjglApplication(new StarGame(), config);
	}
}
