package com.snakexenzia.game.gameobjects.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snakexenzia.game.gameobjects.GameObject;

public class Wall extends GameObject {

    public Wall(){
        createGraphics();
    }

    @Override
    protected void createGraphics() {
        tex = new Texture(Gdx.files.internal(".\\snake model\\wall.jpg"));
        sprite = new Sprite(tex);
    }

    @Override
    public void render(SpriteBatch sb) {
        sprite.setPosition(pos.x, pos.y);
        sprite.draw(sb);
    }
}
