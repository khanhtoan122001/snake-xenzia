package com.snakexenzia.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.snakexenzia.game.gameobjects.GameObject;
import com.snakexenzia.game.gameobjects.food.NormalFood;
import com.snakexenzia.game.gameobjects.items.CutInHalf;
import com.snakexenzia.game.gameobjects.items.SpeedUp;
import com.snakexenzia.game.gameobjects.map.Background;
import com.snakexenzia.game.gameobjects.map.Wall;
import com.snakexenzia.game.gameobjects.player.Snake;

import java.util.ArrayList;
import java.util.List;

import service.ReadFile;

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
    List<Wall> listWall;
    SpeedUp speedUp;

    @Override
    public void create() {
        objects = new ArrayList<>();
        listWall = new ArrayList<>();
        speedUp = new SpeedUp();

        speedUp.setPos(new Vector2(64,64));


        background = new Background();

        snake = new Snake();

        screen = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        snake.setScreen(screen);

        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera();

        camera.setToOrtho(false, 640, 480);

        normalFood = new NormalFood();

        normalFood.spawn(objects);

        String pathname = ".\\maps\\data.txt";
        List<Vector2> list = ReadFile.ReadMap(pathname);
        for (Vector2 pos :
                list) {
            Wall newWall = new Wall();
            newWall.setPos(new Vector2(pos.x * GameObject.BlockSize, pos.y * GameObject.BlockSize));
            listWall.add(newWall);
        }
        loadObjects();
        //CreateFile.CreateInPath(pathname);
        //WriteToFile.WriteToPath(pathname);
    }

    private void loadObjects() {
        objects.clear();
        objects.add(speedUp);
        objects.addAll(snake.getObjects());
        objects.add(normalFood);
        objects.addAll(listWall);
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
    public void dispose() {
        spriteBatch.dispose();
        for(int i = 0; i < objects.size(); i++){
            objects.get(i).dispose();
        }
    }
}
