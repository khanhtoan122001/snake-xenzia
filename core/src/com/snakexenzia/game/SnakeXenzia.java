package com.snakexenzia.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.snakexenzia.game.gameobjects.GameObject;
import com.snakexenzia.game.gameobjects.food.NormalFood;
import com.snakexenzia.game.gameobjects.player.Snake;

import java.util.ArrayList;
import java.util.List;

public class SnakeXenzia extends ApplicationAdapter {
    Sound sound;
    Music music;
    OrthographicCamera camera;
    Snake snake;
    SpriteBatch spriteBatch;
    Rectangle screen;
    NormalFood normalFood;
    List<GameObject> objects;

    @Override
    public void create() {
        objects = new ArrayList<GameObject>();

        snake = new Snake();

        screen = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        snake.setScreen(screen);

        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        spawnNormalFood();

        objects.addAll(snake.getObjects());
    }

    private void spawnNormalFood() {
        normalFood = new NormalFood();
        int x = MathUtils.random(0, Gdx.graphics.getWidth() - 8);
        int y = MathUtils.random(0, Gdx.graphics.getHeight() - 8);
        normalFood.setPos(new Vector2(x, y));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();

        snake.update(Gdx.graphics.getDeltaTime(), objects);

        spriteBatch.begin();
        snake.render(spriteBatch);
        normalFood.render(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}
