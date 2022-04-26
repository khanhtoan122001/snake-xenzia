package com.snakexenzia.game.gameobjects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.snakexenzia.game.gameobjects.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Snake {

    Rectangle screen;

    SnakeHead head;

    List<SnakeBody> body;

    public Snake() {
        super();
        head = new SnakeHead();
        head.setPos(new Vector2(Gdx.graphics.getWidth() >> 1, Gdx.graphics.getHeight() >> 1));
        head.updateLastPos();
        head.setDim(new Vector2(0,0));
        body = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            if(i == 0){
                AddBody(head, Color.BLACK);
            }
            else {
                AddBody(body.get(body.size() - 1), Color.BROWN);
            }
        }
        screen = new Rectangle();
    }

    public void setScreen(Rectangle screen) {
        head.setScreen(screen);
    }

    public void AddBody(GameObject object, Color color) {
        SnakeBody newNode = new SnakeBody();

        Vector2 dim = object.getDim();
        Rectangle screen = object.getScreen();
        Vector2 pos = object.getPos();

        if(dim.x == 0 && dim.y == 0){
            newNode.setPos(new Vector2(pos.x + 16, pos.y));
        }
        else {
            newNode.setPos(new Vector2(pos.x + dim.x * 16, pos.y + dim.y * 16));
        }

        newNode.updateLastPos();
        newNode.setDim(dim);
        newNode.setScreen(screen);
        newNode.setColor(color);

        body.add(newNode);
    }

    public void update(int frameCount, List<GameObject> objects) {

        head.update(frameCount, objects);

        if(frameCount % 8 ==0){
            if(head.getDim().x != 0 || head.getDim().y != 0){
                body.get(0).updateLastPos();
                body.get(0).setPos(new Vector2(head.getLastPos()));

                for(int i = 1; i < body.size(); i++){
                    body.get(i).updateLastPos();
                    body.get(i).setPos(new Vector2(body.get(i - 1).getLastPos()));
                }
            }
        }

    }

    public List<GameObject> getObjects() {
        List<GameObject> res = new ArrayList<>();
        res.add(head);
        res.addAll(body);
        return res;
    }

    public void render(SpriteBatch sb) {
        head.render(sb);
        for(int i = 0; i < body.size(); i++){
            body.get(i).render(sb);
        }
    }
}
