package com.snakexenzia.game.gameobjects.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snakexenzia.game.gameobjects.GameObject;

public class SlowDown extends GameObject {
    public int time = 5;
    public SlowDown(){
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
