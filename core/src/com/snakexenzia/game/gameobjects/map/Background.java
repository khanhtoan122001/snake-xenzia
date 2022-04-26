package com.snakexenzia.game.gameobjects.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.Image;

public class Background {
    protected Pixmap pixmap;
    protected Texture tex;
    protected Sprite sprite;
    protected Color color;

    protected void createGraphics() {

        pixmap = new Pixmap(640, 480, Pixmap.Format.RGBA8888);

        tex = new Texture(Gdx.files.internal("map.png"));

        sprite = new Sprite(tex);

    }

    public void render(SpriteBatch sb){
        createGraphics();
        sprite.setPosition(0,0);
        sprite.draw(sb);
    }
}
