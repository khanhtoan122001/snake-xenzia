package com.snakexenzia.game.gameobjects.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.snakexenzia.game.gameobjects.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    Rectangle screen;

    SnakeHead head;

    List<SnakeBody> body;

    public Snake() {
        super();
        head = new SnakeHead();
        body = new ArrayList<SnakeBody>();
        screen = new Rectangle();
    }

    public void setScreen(Rectangle screen) {
        head.setScreen(screen);
    }

    public void AddBody() {
        SnakeBody newNode = new SnakeBody();
        newNode.setSpeed(head.getSpeed());
        newNode.setDim(head.getDim());
        newNode.setScreen(head.getScreen());
    }

    public void update(float deltaTime, List<GameObject> objects) {
        head.update(deltaTime, objects);
        for(int i = 0; i < body.size(); i++){
            body.get(i).update(deltaTime, objects);
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
    }
}
