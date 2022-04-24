package com.snakexenzia.game.gameobjects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.snakexenzia.game.gameobjects.GameObject;
import com.snakexenzia.game.gameobjects.coEvent;

import java.util.List;

public class SnakeHead extends GameObject {

    public SnakeHead() {
        super();
        this.setColor(Color.RED);
        this.setSpeed(5f);
        this.setDim(new Vector2(0, 1));
        screen = new Rectangle();
        width = 8;
        height = 8;
    }

    protected void toInScreen() {
        if(pos.x > screen.x + screen.width){
            pos = new Vector2(pos.x - screen.width, pos.y);
        }
        if(pos.x < screen.x){
            pos = new Vector2(pos.x + screen.width, pos.y);
        }
        if(pos.y > screen.y + screen.height){
            pos = new Vector2(pos.x, pos.y - screen.height);
        }
        if(pos.y < screen.y){
            pos = new Vector2(pos.x, pos.y + screen.height);
        }
    }

    @Override
    public void update(float deltaTime, List<GameObject> objects) {
        if (!isPause) {
            onKeysPressed();
            setPos(new Vector2(pos.x + deltaTime * speed * dim.x * width,
                    pos.y + deltaTime * speed * dim.y * height));
            toInScreen();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        createGraphics();
        sb.draw(tex, pos.x, pos.y);
    }

    @Override
    public void calcCollision(List<GameObject> objects, List<coEvent> coEvents) {

    }

    public void onKeysPressed() {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            if(this.getDim().x == 0){
                this.setDim(new Vector2(-1,0));
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            if(this.getDim().x == 0){
                this.setDim(new Vector2(1,0));
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            if(this.getDim().y == 0){
                this.setDim(new Vector2(0,1));
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            if(this.getDim().y == 0){
                this.setDim(new Vector2(0,-1));
            }
        }
    }
}
