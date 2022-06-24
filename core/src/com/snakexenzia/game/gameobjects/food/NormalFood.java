package com.snakexenzia.game.gameobjects.food;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.snakexenzia.game.gameobjects.GameObject;
import com.snakexenzia.game.gameobjects.coEvent;

import java.util.ArrayList;
import java.util.List;

public class NormalFood extends GameObject {

    public NormalFood() {
        super();
        tex = new Texture(".\\snake model\\item\\nfood.png");
//        this.setColor(Color.YELLOW);
        this.sprite = new Sprite(tex);
        width = BlockSize;
        height = BlockSize;
//        createGraphics();
    }

    public void spawn(List<GameObject> list) {
        int x = 0, y = 0;
        while(true){
            x = (MathUtils.random(0, Gdx.graphics.getWidth() - BlockSize)) / BlockSize;
            y = (MathUtils.random(0, Gdx.graphics.getHeight() - BlockSize)) / BlockSize;
            Vector2 pos = new Vector2(x * BlockSize,y * BlockSize);
            boolean isOk = true;
            for (GameObject obj :
                    list) {
                if (obj.getClass() != this.getClass()){
                    if(obj.getPos().equals(pos)){
                        isOk = false;
                        break;
                    }
                }
            }
            if(isOk){
                this.setPos(pos);
                return;
            }
        }
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
