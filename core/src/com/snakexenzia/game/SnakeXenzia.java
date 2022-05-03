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
import com.snakexenzia.game.gameobjects.map.Background;
import com.snakexenzia.game.gameobjects.map.Wall;
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
    Background background;
    int frameCount = 0;
    Wall wall;

    @Override
    public void create() {
        objects = new ArrayList<>();

        wall = new Wall();

        background = new Background();

        snake = new Snake();

        screen = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        snake.setScreen(screen);

        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera();

        camera.setToOrtho(false, 640, 480);
        spawnNormalFood();

        objects.addAll(snake.getObjects());
        objects.add(normalFood);
    }

    private void spawnNormalFood() {
        if(normalFood == null)
            normalFood = new NormalFood();
        int x = (int)(MathUtils.random(0, Gdx.graphics.getWidth() - 16)) / 16;
        int y = (int)(MathUtils.random(0, Gdx.graphics.getHeight() - 16)) / 16;
        normalFood.setPos(new Vector2(x * 16, y * 16));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        frameCount++;
        camera.update();

        snake.update(frameCount, objects);

        if(snake.isEat){
            spawnNormalFood();
            snake.isEat = false;
        }

        spriteBatch.begin();
        background.render(spriteBatch);
        snake.render(spriteBatch);
        normalFood.render(spriteBatch);
        wall.render(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}
