package com.snakexenzia.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
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
    final int SPEEDUP_ID = 1;
    final int SLOWDOWN_ID = 2;
    final int CUTINHALF_ID = 3;
    final int BIGFOOD_ID = 4;
    final int BONUSPOINT_ID = 5;

    final Vector2 defaultPoint = new Vector2(-33,-33);
    SnakeGame game;

    Sound eatObj = Gdx.audio.newSound(Gdx.files.internal(".\\sound\\normal food eating.ogg"));
    Sound eatSpecialObj = Gdx.audio.newSound(Gdx.files.internal(".\\sound\\special food eating.ogg"));
    Sound eatCutInHalfObj = Gdx.audio.newSound(Gdx.files.internal(".\\sound\\cut in half.mp3"));
    //special food eating

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
    Texture blank;
    Timer timer;
    private float period = 1f;
    private float timeSeconds = 0f;
    double durationLeftPercentage = 0;
    double duration;
    SpriteBatch batch;
    public SnakeScreen(SnakeGame game, String pathmap){
        this.game = game;
        blank = new Texture("blank.png");
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

        batch = new SpriteBatch();
        List<Vector2> list = ReadFile.ReadMap(pathmap);

        for (Vector2 pos : list) {
            Wall newWall = new Wall();
            newWall.setPos(new Vector2(pos.x * GameObject.BlockSize, pos.y * GameObject.BlockSize));
            listWall.add(newWall);
        }
        loadObjects();
        normalFood.spawn(objects);
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

            bonusPoint.update(delta);
            speedUp.update(delta);
            slowDown.update(delta);
            cutInHalf.update(delta);
            bigFood.update(delta);

            snake.update(frameCount, objects);

            if(snake.isEat){
                snake.isEat = false;
                int nah = (int) (Math.random() * 10);
                switch (nah) {
                    case SPEEDUP_ID:
                        speedUp.spawn(objects);
                        break;
                    case SLOWDOWN_ID:
                        slowDown.spawn(objects);
                        break;
                    case CUTINHALF_ID:
                        cutInHalf.spawn(objects);
                        break;
                    case BIGFOOD_ID:
                        bigFood.spawn(objects);
                        break;
                    case BONUSPOINT_ID:
                        bonusPoint.spawn(objects);
                        break;
                    default:
                        break;
                }
                normalFood.spawn(objects);
            }

            if (snake.isPause) {
                dispose();
                game.setScreen(new GameoverScreen(game, score));
                return;
            }

            if(snake.startBuff){
                snake.startBuff = false;
                durationLeftPercentage = 1;
                duration = 5;
            }

            if(snake.isEatObj) {
                snake.isEatObj = false;
                eatObj.play();
            }

            if(snake.isEatSpecialObj) {
                snake.isEatSpecialObj = false;
                eatSpecialObj.play(0.3f);
            }

            if(snake.isCutHalf) {
                snake.isCutHalf = false;
                eatCutInHalfObj.play();
            }

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
        batch.draw(blank, 0, 0, (float) (Gdx.graphics.getWidth() * durationLeftPercentage), 5);
        batch.end();
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
        batch.dispose();
        for(int i = 0; i < objects.size(); i++) {
            objects.get(i).dispose();
        }

    }
}
