package com.snakexenzia.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snakexenzia.game.screens.MenuScreen;

public class Snake extends Game {
    public SpriteBatch spriteBatch;
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    public static float VOLUMN = 0.4f;
    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        this.setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }
}