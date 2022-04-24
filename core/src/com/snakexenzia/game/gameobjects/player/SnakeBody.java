package com.snakexenzia.game.gameobjects.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.snakexenzia.game.gameobjects.GameObject;
import com.snakexenzia.game.gameobjects.coEvent;

import java.util.List;

public class SnakeBody extends GameObject {

    public SnakeBody() {
        super();
        this.setColor(Color.BLUE);
        screen = new Rectangle();
        width = 8;
        height = 8;
    }

    @Override
    public void update(float deltaTime, List<GameObject> objects) {
        if(!isPause){

        }
    }

    @Override
    public void render(SpriteBatch sb) {

    }

    @Override
    public void calcCollision(List<GameObject> objects, List<coEvent> coEvents) {

    }
}
