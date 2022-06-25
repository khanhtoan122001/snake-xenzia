package com.snakexenzia.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
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
    Rectangle rectangle;
    NormalFood normalFood;
    List<GameObject> objects;
    Background background;
    int frameCount = 0;
    List<Wall> listWall;
    SpeedUp speedUp;
    BitmapFont scoreFont;
    int score;
    Texture blank;
    Timer timer;
    private float period = 1f;
    private float timeSeconds = 0f;
    double durationLeftPercentage = 0;
    double duration;
    public SnakeScreen(SnakeGame game){
        this.game = game;
        blank = new Texture("blank.png");
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));


        objects = new ArrayList<>();
        listWall = new ArrayList<>();
        speedUp = new SpeedUp();

        speedUp.setPos(new Vector2(64,64));


        background = new Background();

        snake = new Snake(this);
        score = 0;

        rectangle = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        snake.setScreen(rectangle);

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
//           ScreenUtils.clear(0, 0, 0.2f, 1);
            score = snake.score;
            frameCount++;
            camera.update();

            snake.update(frameCount, objects);
            if(snake.isEat){
                normalFood.spawn(objects);
                snake.isEat = false;
                durationLeftPercentage = 1;
                duration = 5;
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
            if (duration > 0)
                DrawBar(duration);
            game.spriteBatch.end();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void DrawBar(double duration) {
        SpriteBatch batch = new SpriteBatch();
        timeSeconds += Gdx.graphics.getDeltaTime();
        if (timeSeconds > period){
            timeSeconds -= period;
            if (durationLeftPercentage > 0) {
                durationLeftPercentage = durationLeftPercentage - 1 / duration;
                if (durationLeftPercentage < 0)
                    durationLeftPercentage = 0;
            }
            durationLeftPercentage = Math.round(durationLeftPercentage * 100.0) / 100.0;
        }
        batch.begin();
        if (durationLeftPercentage >= 0.8)
            batch.setColor(Color.BLUE);
        else if (durationLeftPercentage >= 0.6)
            batch.setColor(Color.YELLOW);
        else if (durationLeftPercentage >= 0.4)
            batch.setColor(Color.ORANGE);
        else
            batch.setColor(Color.RED);
        batch.draw(blank, snake.head.getPos().x - snake.head.getWidth() / 2, snake.head.getPos().y + snake.head.getHeight() + 10, (float) (60 * durationLeftPercentage), 10);
        batch.end();
    }

    private void RenderMap() {
        normalFood.render(game.spriteBatch);
        for (Wall wall : listWall) {
            wall.render(game.spriteBatch);
        }
        speedUp.render(game.spriteBatch);
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
