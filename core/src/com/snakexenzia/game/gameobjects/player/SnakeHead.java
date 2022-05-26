package com.snakexenzia.game.gameobjects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.snakexenzia.game.gameobjects.GameObject;
import com.snakexenzia.game.gameobjects.coEvent;
import com.snakexenzia.game.gameobjects.food.NormalFood;

import java.util.ArrayList;
import java.util.List;

public class SnakeHead extends GameObject {
    final float timePause = 0.3f;
    float timeWait = 0;
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
        if (pos.x >= screen.x + screen.width) {
            //updateLastPos();
            pos = new Vector2(pos.x - screen.width, pos.y);
            return;
        }
        if (pos.x <= screen.x - BlockSize) {
            //updateLastPos();
            pos = new Vector2(pos.x + screen.width, pos.y);
            return;
        }
        if (pos.y >= screen.y + screen.height) {
            //updateLastPos();
            pos = new Vector2(pos.x, pos.y - screen.height);
            return;
        }
        if (pos.y <= screen.y - BlockSize) {
            //updateLastPos();
            pos = new Vector2(pos.x, pos.y + screen.height);
        }
    }

    @Override
    public void update(List<GameObject> objects) {

        List<coEvent> events = new ArrayList<>();

        if (!isPause) {

            isCanChange = true;
            dx += dim.x * width;
            dy += dim.y * height;

            updateLastPos();

            setPos(new Vector2(pos.x + dx, pos.y + dy));

            dx = dy = 0;

            toInScreen();

            calcCollision(objects, events);

            if (events.size() != 0) {
                for (coEvent co :
                        events) {
                    if (co.object.getClass().getName().equals(NormalFood.class.getName())) {
                        isAddBody = true;
                        break;
                    }
                }
            }
        }
    }

    public void keysPressed(){
        if(isCanChange)
            onKeysPressed();
    }

    @Override
    public void render(SpriteBatch sb) {
        sprite.setPosition(pos.x, pos.y);
        sprite.draw(sb);
        //sb.draw(tex, pos.x, pos.y);
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
