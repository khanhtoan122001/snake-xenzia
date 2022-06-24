package com.snakexenzia.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snakexenzia.game.screens.GameoverScreen;
import com.snakexenzia.game.screens.MenuScreen;

import java.awt.*;

public class SnakeGame extends Game {
    public SpriteBatch spriteBatch;
    public static MenuScreen menu;
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    public static float VOLUMN = 0.5f;
    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        menu = new MenuScreen(this);
        this.setScreen(menu);
    }

    @Override
    public void render() {
        super.render();
    }
}
