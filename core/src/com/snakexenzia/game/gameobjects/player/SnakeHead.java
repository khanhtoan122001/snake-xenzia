package com.snakexenzia.game.gameobjects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.snakexenzia.game.gameobjects.GameObject;
import com.snakexenzia.game.gameobjects.coEvent;

import java.util.ArrayList;
import java.util.List;

public class SnakeHead extends GameObject {
    public boolean isAddBody = false;
    private boolean isCanChange = true;

    public SnakeHead() {
        super();
        this.setColor(Color.RED);
        this.setSpeed(5f);
        this.setDim(new Vector2(0, 1));
        screen = new Rectangle();
        width = BlockSize;
        height = BlockSize;
        createGraphics();
    }

    protected void toInScreen() {
        if (pos.x == screen.width) {
            pos = new Vector2(0, pos.y);
            return;
        }
        if (pos.x == -BlockSize) {
            pos = new Vector2(screen.width, pos.y);
            return;
        }
        if (pos.y == screen.height) {
            pos = new Vector2(pos.x, 0);
            return;
        }
        if (pos.y == -BlockSize) {
            pos = new Vector2(pos.x, screen.height);
        }
    }

    @Override
    public void update(List<GameObject> objects, List<coEvent> events) {
        isCanChange = true;
        dx += dim.x * width;
        dy += dim.y * height;

        updateLastPos();

        setPos(new Vector2(pos.x + dx, pos.y + dy));

        toInScreen();

        dx = dy = 0;

    }

    public void keysPressed(){
        if(isCanChange)
            onKeysPressed();
    }

    @Override
    public void render(SpriteBatch sb) {
        sprite.setPosition(pos.x, pos.y);
        sprite.draw(sb);
    }

    public void onKeysPressed() {
        lastPos = dim;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (lastPos.x == 0) {
                this.setDim(new Vector2(-1, 0));
                isCanChange = false;
                return;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (lastPos.x == 0) {
                this.setDim(new Vector2(1, 0));
                isCanChange = false;
                return;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (lastPos.y == 0) {
                this.setDim(new Vector2(0, 1));
                isCanChange = false;
                return;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if (lastPos.y == 0) {
                this.setDim(new Vector2(0, -1));
                isCanChange = false;
            }
        }
    }
}
