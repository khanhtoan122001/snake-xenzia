package com.snakexenzia.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.snakexenzia.game.SnakeGame;
import com.snakexenzia.game.gameobjects.GameObject;
import com.snakexenzia.game.gameobjects.food.NormalFood;
import com.snakexenzia.game.gameobjects.items.BigFood;
import com.snakexenzia.game.gameobjects.items.BonusPoint;
import com.snakexenzia.game.gameobjects.items.CutInHalf;
import com.snakexenzia.game.gameobjects.items.SlowDown;
import com.snakexenzia.game.gameobjects.items.SpeedUp;
import com.snakexenzia.game.gameobjects.map.Background;
import com.snakexenzia.game.gameobjects.map.Wall;
import com.snakexenzia.game.gameobjects.player.Snake;

import java.util.ArrayList;
import java.util.List;

import service.ReadFile;

public class SnakeScreen implements Screen {
    final Vector2 defaultPoint = new Vector2(-33,-33);
    SnakeGame game;

    OrthographicCamera camera;
    Snake snake;
    Rectangle rectangle;
    NormalFood normalFood;
    List<GameObject> objects;
    Background background;
    int frameCount = 0;
    List<Wall> listWall;
    SpeedUp speedUp;
    BitmapFont scoreFont;
    SlowDown slowDown;
    CutInHalf cutInHalf;
    BigFood bigFood;
    BonusPoint bonusPoint;
    int score;

    public SnakeScreen(SnakeGame game, String pathmap){
        this.game = game;

        scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));

        objects = new ArrayList<>();
        listWall = new ArrayList<>();
        speedUp = new SpeedUp();
        slowDown = new SlowDown();
        cutInHalf = new CutInHalf();
        bigFood = new BigFood();
        bonusPoint = new BonusPoint();

        speedUp.setPos(defaultPoint);
        slowDown.setPos(defaultPoint);
        cutInHalf.setPos(defaultPoint);
        bigFood.setPos(defaultPoint);
        bonusPoint.setPos(defaultPoint);


        background = new Background();

        snake = new Snake(this);
        score = 0;

        rectangle = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        snake.setScreen(rectangle);

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
        objects.add(slowDown);
        objects.add(bigFood);
        objects.add(cutInHalf);
        objects.add(bonusPoint);
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
            score = snake.score;
            frameCount++;
            camera.update();

            snake.update(frameCount, objects);
            if(snake.isEat){
                normalFood.spawn(objects);
                snake.isEat = false;
            }

            if (snake.isPause) {
                dispose();
                game.setScreen(new GameoverScreen(game, score));
                return;
            }
            //if(snake.isCutHalf) {
            //loadObjects();
            //snake.isEat = false;
            //}
            game.spriteBatch.begin();

            background.render(game.spriteBatch);
            snake.render(game.spriteBatch);
            RenderMap();
            GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "" + score);
            scoreFont.draw(game.spriteBatch, scoreLayout, game.WIDTH / 2 - scoreLayout.width / 2, game.HEIGHT - scoreLayout.height - 10);
            game.spriteBatch.end();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void RenderMap() {
        normalFood.render(game.spriteBatch);
        for (Wall wall : listWall) {
            wall.render(game.spriteBatch);
        }
        speedUp.render(game.spriteBatch);
        slowDown.render(game.spriteBatch);
        cutInHalf.render(game.spriteBatch);
        bigFood.render(game.spriteBatch);
        bonusPoint.render(game.spriteBatch);
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
        background.dispose();
        scoreFont.dispose();
        for(int i = 0; i < objects.size(); i++) {
            objects.get(i).dispose();
        }

    }
}
