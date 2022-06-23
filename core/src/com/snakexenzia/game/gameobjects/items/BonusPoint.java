package com.snakexenzia.game.gameobjects.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snakexenzia.game.gameobjects.GameObject;

public class BonusPoint extends GameObject {
    public BonusPoint(){
        super();
        this.setColor(Color.BLACK);
        width = BlockSize;
        height = BlockSize;
        createGraphics();
    }

    @Override
    public void render(SpriteBatch sb) {
        sprite.setPosition(pos.x, pos.y);
        sprite.draw(sb);
    }
}
