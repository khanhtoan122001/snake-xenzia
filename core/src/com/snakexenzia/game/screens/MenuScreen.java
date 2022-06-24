package com.snakexenzia.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.snakexenzia.game.Snake;
import com.snakexenzia.game.SnakeXenzia;

public class MenuScreen implements Screen {
    Snake game;

    private static final int EXIT_BUTTON_WIDTH = 200;
    private static final int EXIT_BUTTON_HEIGHT = 100;
    private static final int EXIT_BUTTON_Y = 100;
    private static final int PLAY_BUTTON_WIDTH = 220;
    private static final int PLAY_BUTTON_HEIGHT = 100;
    private static final int PLAY_BUTTON_Y = 200;

    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture playButtonActive;
    Texture playButtonInactive;
    Texture background;
    Sound clickSound = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));
    Music backGroundMusic = Gdx.audio.newMusic(Gdx.files.internal("TemShop.mp3"));

    public MenuScreen (Snake game) {
        this.game = game;
        playButtonActive = new Texture("play_button_active.png");
        playButtonInactive = new Texture("play_button_inactive.png");
        exitButtonActive = new Texture("exit_button_active.png");
        exitButtonInactive = new Texture("exit_button_inactive.png");
        background = new Texture("background.jpg");
    }

    @Override
    public void show() {
        backGroundMusic.setVolume(game.VOLUMN);
        backGroundMusic.setLooping(true);
        backGroundMusic.play();
    }
    @Override
    public void render(float delta) {
        game.spriteBatch.begin();
        game.spriteBatch.draw(background, 0, 0, game.WIDTH, game.HEIGHT);
        int exitX = game.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2;
        int playX = game.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2;
        //Exit Button
        if ((Gdx.input.getX() > exitX && Gdx.input.getX() < exitX + EXIT_BUTTON_WIDTH) && (game.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y && game.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT)) {
            game.spriteBatch.draw(exitButtonActive, game.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            if (Gdx.input.justTouched()) {
                clickSound.play(game.VOLUMN);
                Gdx.app.exit();
            }
        }
        else {
            game.spriteBatch.draw(exitButtonInactive, game.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }
        //Play Button
        if ((Gdx.input.getX() > playX && Gdx.input.getX() < playX + PLAY_BUTTON_WIDTH) && (game.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y && game.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT)) {
            game.spriteBatch.draw(playButtonActive, game.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
            if (Gdx.input.justTouched()) {
                //Open Snake Screen
                clickSound.play(game.VOLUMN);
            }
        } else {
            game.spriteBatch.draw(playButtonInactive, game.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
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

    }
}