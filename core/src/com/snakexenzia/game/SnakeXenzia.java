package com.snakexenzia.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.snakexenzia.game.gameobjects.GameObject;
import com.snakexenzia.game.gameobjects.food.NormalFood;
import com.snakexenzia.game.gameobjects.map.Background;
import com.snakexenzia.game.gameobjects.map.Wall;
import com.snakexenzia.game.gameobjects.player.Snake;
import com.snakexenzia.game.service.QuadTree;

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

        normalFood = new NormalFood();

        objects.addAll(snake.getObjects());
        objects.add(normalFood);
        normalFood.spawn(objects);
    }

    @Override
    public void render() {
        try {
            ScreenUtils.clear(0, 0, 0.2f, 1);

            frameCount++;
            camera.update();

            snake.update(frameCount, objects);

            if(snake.isEat){
                normalFood.spawn(objects);
                snake.isEat = false;
            }

            spriteBatch.begin();
            background.render(spriteBatch);
            snake.render(spriteBatch);
            normalFood.render(spriteBatch);
            wall.render(spriteBatch);
            spriteBatch.end();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        for(int i = 0; i < objects.size(); i++){
            objects.get(i).dispose();
        }
    }
}
