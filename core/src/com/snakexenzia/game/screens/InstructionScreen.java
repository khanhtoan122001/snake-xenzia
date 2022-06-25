package com.snakexenzia.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.snakexenzia.game.SnakeGame;
import org.w3c.dom.Text;

public class InstructionScreen implements Screen {
    SnakeGame game;
    Texture background;
    Texture paper;
    BitmapFont font;
    Texture arrow;
    int page = 1;
    public InstructionScreen(SnakeGame game){
        this.game = game;
        background = new Texture("background.jpg");
        paper = new Texture("paper.png");
        font = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.spriteBatch.begin();
        game.spriteBatch.draw(background, 0, 0, game.WIDTH, game.HEIGHT);
        game.spriteBatch.draw(paper, 20, 20, game.WIDTH - 20*2, game.HEIGHT - 20*2);
        font.getData().setScale(0.5f);
        //Page 1
        if (page == 1) {
            CharSequence str = "Use arrow keys to control\nthe snake";
            font.setColor(Color.BLACK);
            int startLine = game.WIDTH - (game.WIDTH - 20 * 2) + 20 + 60;
            font.draw(game.spriteBatch, str, startLine, game.HEIGHT - 20 * 2 - 60);

            //1st item
            Texture wall = new Texture("wall.jpg");
            game.spriteBatch.draw(wall, startLine, (game.HEIGHT - 20 * 2 - 60) - 32 - 60, 32, 32);
            CharSequence wallDescription = "It's gameover if you\nhit this";
            font.setColor(Color.RED);
            font.draw(game.spriteBatch, wallDescription, startLine + 32 + 10, (game.HEIGHT - 20 * 2 - 60) - 60);
            font.setColor(Color.BLACK);
            //2nd item
            Texture food = new Texture("snake model/item/strawberry-icon.png");
            game.spriteBatch.draw(food, startLine, (game.HEIGHT - 20 * 2 - 60) - 32 - 60 - 60, 32, 32);
            CharSequence foodDescription = "+1 snake length\n+1 point";
            font.setColor(Color.CORAL);
            font.draw(game.spriteBatch, foodDescription, startLine + 32 + 10, (game.HEIGHT - 20 * 2 - 60) - 60 - 60);

            //3rd item
            Texture star = new Texture("snake model/item/bigfood.png");
            game.spriteBatch.draw(star, startLine, (game.HEIGHT - 20 * 2 - 60) - 32 - 60 - 60 - 60 + 12, 32, 32);
            CharSequence starDes = "+5 point";
            font.setColor(Color.CORAL);
            font.draw(game.spriteBatch, starDes, startLine + 32 + 10, (game.HEIGHT - 20 * 2 - 60) - 60 - 60 - 60);
            //
            arrow = new Texture("right-arrow.png");
        }
        //Page 2
        else {
            CharSequence str = "There is 5% chance to \nspawn special items each\ntime you eat food";
            font.setColor(Color.BLACK);
            int startLine = game.WIDTH - (game.WIDTH - 20 * 2) + 20 + 60;
            font.draw(game.spriteBatch, str, startLine, game.HEIGHT - 20 * 2 - 60);

            //1st item
            Texture pill = new Texture("snake model/item/62990-pill-icon.png");
            game.spriteBatch.draw(pill, startLine, (game.HEIGHT - 20 * 2 - 60) - 32 - 60 - 10, 32, 32);
            CharSequence pillDes = "x0.5 snake length";
            font.setColor(Color.CORAL);
            font.draw(game.spriteBatch, pillDes, startLine + 32 + 10, (game.HEIGHT - 20 * 2 - 60) - 60 - 10 - 12);
            font.setColor(Color.BLACK);
            //2nd item
            Texture candy = new Texture("snake model/item/bonuspoint.png");
            game.spriteBatch.draw(candy, startLine, (game.HEIGHT - 20 * 2 - 60) - 32 - 60 - 60 - 10, 32, 32);
            CharSequence candyDes = "x2 point for 5s";
            font.setColor(Color.CORAL);
            font.draw(game.spriteBatch, candyDes, startLine + 32 + 10, (game.HEIGHT - 20 * 2 - 60) - 60 - 60 - 10 - 12);

            //3rd item
            Texture rocket = new Texture("snake model/item/speedup.png");
            game.spriteBatch.draw(rocket, startLine, (game.HEIGHT - 20 * 2 - 60) - 32 - 60 - 60 - 60 - 10, 32, 32);
            CharSequence rocketDes = "+2 or -2 Speed for 5s\n(Random)";
            font.setColor(Color.CORAL);
            font.draw(game.spriteBatch, rocketDes, startLine + 32 + 10, (game.HEIGHT - 20 * 2 - 60) - 60 - 60 - 60 - 10);
            //
            arrow = new Texture("right-arrow.png");
            //
            arrow = new Texture("left-arrow.png");
        }
        //Draw arrow
        game.spriteBatch.draw(arrow, game.WIDTH - 150, 90, 32, 32);
        if ((Gdx.input.getX() > game.WIDTH - 150 && Gdx.input.getX() < game.WIDTH - 150 + 32) && (game.HEIGHT - Gdx.input.getY() > 90 && game.HEIGHT - Gdx.input.getY() < 90 + 32)) {
            if (Gdx.input.justTouched()) {
                if (page == 1)
                    page = 2;
                else page = 1;
            }
        }
        else {
            if (Gdx.input.justTouched()) {
                dispose();
                game.setScreen(game.menu);
            }
        }
        game.spriteBatch.end();


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
        font.dispose();
        background.dispose();
        paper.dispose();
    }
}
