package com.snakexenzia.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.snakexenzia.game.SnakeGame;

import java.awt.*;


public class GameoverScreen implements Screen {
    SnakeGame game;

    int score, highscore;

    private static final int GAMEOVER_WIDTH = 350;
    private static final int GAMEOVER_HEIGHT = 100;

    Texture gameoverBanner;
    BitmapFont scoreFont;
    boolean gotNewHighscore = false;
    public GameoverScreen(SnakeGame game, int score) {
        Preferences prefs = Gdx.app.getPreferences("snakexenzia");
        this.highscore = prefs.getInteger("highscore", 0);

        this.game = game;
        this.score = score;
        gameoverBanner = new Texture("game_over.png");
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));

        if (score > highscore) {
            prefs.putInteger("highscore", score);
            prefs.flush();
            gotNewHighscore = true;
            highscore = score;
        }
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
        GlyphLayout highscoreLayout = new GlyphLayout(scoreFont, "Highscore: \n" + highscore, Color.WHITE, 0, Align.left, false);
        scoreFont.draw(game.spriteBatch, highscoreLayout, game.WIDTH / 2 - scoreLayout.width / 2, game.HEIGHT - GAMEOVER_HEIGHT - 60 * 2);
        GlyphLayout newHighscoreLayout = new GlyphLayout(scoreFont, "New highscore!\n", Color.YELLOW, 0, Align.left, false);
        if (gotNewHighscore)
            scoreFont.draw(game.spriteBatch, newHighscoreLayout, game.WIDTH / 2 - newHighscoreLayout.width / 2, 50);
        game.spriteBatch.end();
        if (Gdx.input.justTouched())
            game.setScreen(game.menu);
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
        scoreFont.dispose();
    }
}
