package com.snakexenzia.game.gameobjects.food;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snakexenzia.game.gameobjects.GameObject;

import java.util.List;

public class NormalFood extends GameObject {

    public NormalFood() {
        super();
        this.setColor(Color.YELLOW);
        width = BlockSize;
        height = BlockSize;
    }

    @Override
    public void update(int frameCount, List<GameObject> objects) {

    }

    @Override
    public void render(SpriteBatch sb) {
        createGraphics();
        sprite.setPosition(pos.x, pos.y);
        sprite.draw(sb);
    }

}
