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
        CharSequence str = "Use arrow keys to control\nthe snake";
        font.setColor(Color.BLACK);
        int startLine = game.WIDTH - (game.WIDTH - 20*2) + 20 + 60;
        font.draw(game.spriteBatch, str, startLine, game.HEIGHT - 20*2 - 60);

        //1st item
        Texture wall = new Texture("wall-block.png");
        game.spriteBatch.draw(wall, startLine, (game.HEIGHT - 20*2 - 60) - 32 - 60 , 32, 32);
        CharSequence wallDescription = "Hit this shit and you\ndie for real";
        font.setColor(Color.RED);
        font.draw(game.spriteBatch, wallDescription, startLine + 32 + 10, (game.HEIGHT - 20*2 - 60) - 60 );
        font.setColor(Color.BLACK);
        //2nd item
        Texture food = new Texture("bananas-icon.png");
        game.spriteBatch.draw(food, startLine, (game.HEIGHT - 20*2 - 60) - 32 - 60 - 60 , 32, 32);
        CharSequence foodDescription = "+1 snake length\n+1 point";
        font.setColor(Color.GOLDENROD);
        font.draw(game.spriteBatch, foodDescription, startLine + 32 + 10, (game.HEIGHT - 20*2 - 60) - 60 - 60 );
        game.spriteBatch.end();
        if (Gdx.input.justTouched()) {
            dispose();
            game.setScreen(game.menu);
        }
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
