package com.snakexenzia.game.gameobjects.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.snakexenzia.game.gameobjects.GameObject;
import com.snakexenzia.game.gameobjects.coEvent;

import java.util.List;

public class SnakeBody extends GameObject {

    public SnakeBody() {
        super();
        this.setColor(Color.BROWN);
        screen = new Rectangle();
        width = BlockSize;
        height = BlockSize;
        createGraphics();
    }

    public void dimUpdate(GameObject object){
        dim = object.getDim();
    }

    @Override
    public void update(List<GameObject> objects, List<coEvent> events) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sprite.setPosition(pos.x, pos.y);
        sprite.draw(sb);
    }
}
