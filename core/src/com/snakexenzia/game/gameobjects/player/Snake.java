package com.snakexenzia.game.gameobjects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.snakexenzia.game.gameobjects.GameObject;
import com.snakexenzia.game.service.QuadTree;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Snake {

    Rectangle screen;

    public SnakeHead head;

    public List<SnakeBody> body;

    private int speed = 0;

    public boolean isEat = false;

    public Snake() {
        super();
        head = new SnakeHead();
        head.setPos(getPoint(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        head.updateLastPos();
        head.setDim(new Vector2(0,0));
        body = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            if(i == 0){
                AddBody(head);
            }
            else {
                AddBody(body.get(body.size() - 1));
            }
        }
        screen = new Rectangle();
    }

    private Vector2 getPoint(int width, int height) {
        Vector2 res = new Vector2();
        int sx = width / GameObject.BlockSize;
        int sy = height / GameObject.BlockSize;
        res.x = (sx >> 1) * GameObject.BlockSize;
        res.y = (sy >> 1) * GameObject.BlockSize;
        return res;
    }

    public void setScreen(Rectangle screen) {
        head.setScreen(screen);
    }

    public void AddBody(GameObject object) {
        SnakeBody newNode = new SnakeBody();

        Vector2 dim = object.getDim();
        Rectangle screen = object.getScreen();
        Vector2 pos = object.getPos();

        if(dim.x == 0 && dim.y == 0){
            newNode.setPos(new Vector2(pos.x + GameObject.BlockSize, pos.y));
        }
        else {
            newNode.setPos(new Vector2(pos.x + dim.x * GameObject.BlockSize, pos.y + dim.y * GameObject.BlockSize));
        }

        newNode.updateLastPos();
        newNode.setDim(dim);
        newNode.setScreen(screen);

        body.add(newNode);
    }

    public void insertQuardTree(List<GameObject> objs, QuadTree quadTree) {
        for (GameObject obj:
                objs) {
            quadTree.insert(obj);
        }
    }

    public void update(int frameCount, List<GameObject> objects) {
        // setup quadtree
        List<GameObject> objectList = new ArrayList<>();
        QuadTree quadTree = new QuadTree(0, screen);
        quadTree.clear();
        insertQuardTree(objects, quadTree);
        quadTree.retrieve(objectList, this.head);

        // update
        head.keysPressed();

        if(head.isAddBody){
            AddBody(body.get(body.size() - 1));
            objects.add(body.get(body.size() - 1));
            speed = body.size() / 10;
            if(speed > 5)
                speed = 5;
            head.isAddBody = false;
            isEat = true;
        }

        if(frameCount % (GameObject.frameSpeed - speed) == 0){

            head.update(objectList);

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
