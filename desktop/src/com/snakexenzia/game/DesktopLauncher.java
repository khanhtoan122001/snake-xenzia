package com.snakexenzia.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowSizeLimits(SnakeGame.WIDTH, SnakeGame.HEIGHT, SnakeGame.WIDTH * 2, SnakeGame.HEIGHT * 2);
		config.setForegroundFPS(60);
		config.useVsync(true);
		config.setTitle("SnakeGame Xenzia");
		new Lwjgl3Application(new SnakeGame(), config);

	}
}
