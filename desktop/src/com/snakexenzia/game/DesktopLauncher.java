package com.snakexenzia.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.snakexenzia.game.SnakeXenzia;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowSizeLimits(Snake.WIDTH, Snake.HEIGHT,Snake.WIDTH * 2,Snake.HEIGHT * 2);
		config.setForegroundFPS(60);
		config.useVsync(true);
		config.setTitle("Snake Xenzia");
		new Lwjgl3Application(new Snake(), config);
	}
}
