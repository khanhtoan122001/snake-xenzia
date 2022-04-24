package com.snakexenzia.game.gameobjects.food;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snakexenzia.game.gameobjects.GameObject;
import com.snakexenzia.game.gameobjects.coEvent;

import java.util.List;

public class NormalFood extends GameObject {

    public NormalFood() {
        super();
        this.setColor(Color.YELLOW);
        width = 8;
        height = 8;
    }

    @Override
    public void update(float deltaTime, List<GameObject> objects) {

    }

    @Override
    public void render(SpriteBatch sb) {
        createGraphics();
        sb.draw(tex, pos.x, pos.y);
    }

    @Override
    public void calcCollision(List<GameObject> objects, List<coEvent> coEvents) {

    }

}
