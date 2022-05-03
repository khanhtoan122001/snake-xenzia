package com.snakexenzia.game.gameobjects.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snakexenzia.game.gameobjects.GameObject;

public class Wall extends GameObject {

    @Override
    protected void createGraphics() {
        tex = new Texture(Gdx.files.internal("wall-block.png"));
        sprite = new Sprite(tex);
    }

    @Override
    public void render(SpriteBatch sb) {
        createGraphics();
        sprite.setPosition(64,64);
        sprite.draw(sb);
    }
}
