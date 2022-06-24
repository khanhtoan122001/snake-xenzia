package com.snakexenzia.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.snakexenzia.game.SnakeGame;
import com.snakexenzia.game.gameobjects.GameObject;
import com.snakexenzia.game.gameobjects.food.NormalFood;
import com.snakexenzia.game.gameobjects.items.SpeedUp;
import com.snakexenzia.game.gameobjects.map.Background;
import com.snakexenzia.game.gameobjects.map.Wall;
import com.snakexenzia.game.gameobjects.player.Snake;

import java.util.ArrayList;
import java.util.List;

import service.ReadFile;

public class SnakeScreen implements Screen {
    SnakeGame game;

    OrthographicCamera camera;
    Snake snake;
    public SpriteBatch spriteBatch;
    Rectangle rectangle;
    NormalFood normalFood;
    List<GameObject> objects;
    Background background;
    int frameCount = 0;
    List<Wall> listWall;
    SpeedUp speedUp;

    public SnakeScreen(SnakeGame game, String pathmap){
        this.game = game;
        objects = new ArrayList<>();
        listWall = new ArrayList<>();
        speedUp = new SpeedUp();

        speedUp.setPos(new Vector2(64,64));


        background = new Background();

        snake = new Snake();

        rectangle = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        snake.setScreen(rectangle);

        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera();

        camera.setToOrtho(false, 640, 480);

        normalFood = new NormalFood();

        normalFood.spawn(objects);

        List<Vector2> list = ReadFile.ReadMap(pathmap);

        for (Vector2 pos : list) {
            Wall newWall = new Wall();
            newWall.setPos(new Vector2(pos.x * GameObject.BlockSize, pos.y * GameObject.BlockSize));
            listWall.add(newWall);
        }
        loadObjects();
    }

    private void loadObjects() {
        objects.clear();
        objects.add(speedUp);
        objects.addAll(snake.getObjects());
        objects.add(normalFood);
        objects.addAll(listWall);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        try {
            ScreenUtils.clear(0, 0, 0.2f, 1);

            frameCount++;
            camera.update();

            snake.update(frameCount, objects);
            if(snake.isEat){
                normalFood.spawn(objects);
                snake.isEat = false;
            }
            //if(snake.isCutHalf) {
            //loadObjects();
            //snake.isEat = false;
            //}
            spriteBatch.begin();
            background.render(spriteBatch);
            snake.render(spriteBatch);
            RenderMap();
            spriteBatch.end();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void RenderMap() {
        normalFood.render(spriteBatch);
        for (Wall wall : listWall) {
            wall.render(spriteBatch);
        }
        speedUp.render(spriteBatch);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        for(int i = 0; i < objects.size(); i++){
            objects.get(i).dispose();
        }
    }
}
