package com.snakexenzia.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.snakexenzia.game.SnakeGame;


public class GameoverScreen implements Screen {
    SnakeGame game;

    int score, highscore;

    private static final int GAMEOVER_WIDTH = 350;
    private static final int GAMEOVER_HEIGHT = 100;

    Texture gameoverBanner;
    BitmapFont scoreFont;

    public GameoverScreen(SnakeGame game, int score) {
        this.game = game;
        this.score = score;
        gameoverBanner = new Texture("game_over.png");
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        game.spriteBatch.begin();
        //Draw Gameover
        game.spriteBatch.draw(gameoverBanner, game.WIDTH / 2 - GAMEOVER_WIDTH / 2, game.HEIGHT - GAMEOVER_HEIGHT - 10, GAMEOVER_WIDTH, GAMEOVER_HEIGHT);
        //Draw Score
        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "Score: \n" + score, Color.WHITE, 0, Align.left, false);
        scoreFont.draw(game.spriteBatch, scoreLayout, game.WIDTH / 2 - scoreLayout.width / 2, game.HEIGHT - GAMEOVER_HEIGHT - 10 * 2);
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

    }
}
