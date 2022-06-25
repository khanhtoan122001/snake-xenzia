package com.snakexenzia.game.gameobjects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.snakexenzia.game.gameobjects.GameObject;
import com.snakexenzia.game.gameobjects.coEvent;
import com.snakexenzia.game.gameobjects.food.NormalFood;
import com.snakexenzia.game.gameobjects.items.BigFood;
import com.snakexenzia.game.gameobjects.items.BonusPoint;
import com.snakexenzia.game.gameobjects.items.CutInHalf;
import com.snakexenzia.game.gameobjects.items.SlowDown;
import com.snakexenzia.game.gameobjects.items.SpeedUp;
import com.snakexenzia.game.gameobjects.map.Wall;
import com.snakexenzia.game.screens.SnakeScreen;
import com.snakexenzia.game.service.QuadTree;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private static final Object TAG = "snake";

    Rectangle screen;

    public SnakeHead head;

    public List<SnakeBody> body;

    public boolean isPause = false;

    private int speed = 0;

    public boolean isEat = false;
    public boolean isEatObj = false;
    public boolean isEatSpecialObj = false;

    public boolean isCutHalf = false;

    public int score = 0;

    private int boost = 0;
    private float boostTime = 0;
    private float bonusTime = 0;

    public boolean startBuff = false;

    SnakeScreen gameScreen;
    public Snake(SnakeScreen gameScreen) {
        super();
        this.gameScreen = gameScreen;
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

        if(boost != 0){
            boostTime -= Gdx.graphics.getDeltaTime();
            if(boostTime <= 0)
                boost = 0;
        }

        if(bonusTime > 0){
            bonusTime -= Gdx.graphics.getDeltaTime();
            if(bonusTime <= 0)
                bonusTime = 0;
        }

        // update
        head.keysPressed();

        if(!isPause){
            if(frameCount % (GameObject.frameSpeed - (speed + boost)) == 0){
                List<coEvent> events = new ArrayList<>();

                head.update(objectList, events);

                if(head.getDim().x != 0 || head.getDim().y != 0){
                    body.get(0).updateLastPos();
                    body.get(0).setPos(new Vector2(head.getLastPos()));

                    for(int i = 1; i < body.size(); i++){
                        body.get(i).updateLastPos();
                        body.get(i).setPos(new Vector2(body.get(i - 1).getLastPos()));
                    }
                }

                head.calcCollision(objects, events);

                if (events.size() != 0) {
                    for (coEvent co : events) {
                        String coClassName = co.object.getClass().getName();

                        if (coClassName.equals(NormalFood.class.getName())) {
                            EatNormalFood(objects);
                            isEatObj = true;
                            break;
                        }
                        if(coClassName.equals(Wall.class.getName())){
                            Pause();
                            break;
                        }
                        if(coClassName.equals(SnakeBody.class.getName())){
                            Pause();
                            break;
                        }
                        if(coClassName.equals(SpeedUp.class.getName())){
                            boost = 2;
                            boostTime = 5;
                            startBuff = true;
                            co.object.hide();
                            isEatSpecialObj = true;
                            break;
                        }
                        if(coClassName.equals(SlowDown.class.getName())){
                            boost = -2;
                            boostTime = 5;
                            startBuff = true;
                            co.object.hide();
                            isEatSpecialObj = true;
                            break;
                        }
                        if(coClassName.equals(CutInHalf.class.getName())){
                            if(body.size() < 4) break;
                            CutHalfBody(objects);
                            co.object.hide();
                            break;
                        }
                        if(coClassName.equals(BigFood.class.getName())){
                            EatBigFood(objects);
                            co.object.hide();
                            isEatSpecialObj = true;
                            break;
                        }
                        if(coClassName.equals(BonusPoint.class.getName())){
                            bonusTime = 5;
                            startBuff = true;
                            co.object.hide();
                            isEatSpecialObj = true;
                            break;
                        }
                    }
                }
            }
        }
    }

    private void CutHalfBody(List<GameObject> objects) {
        int size = body.size();
        for (int i = 0; i < size / 2; i++) {
            SnakeBody body1 = body.get(body.size() - 1);
            objects.remove(body1);
            body.remove(body.size() - 1);
        }
        isCutHalf = true;
    }

    private void Pause() {
        List<GameObject> objects = getObjects();

        for (GameObject object : objects) {
            object.setPos(object.getLastPos());
        }

        isPause = true;
    }

    public void EatNormalFood(List<GameObject> objects){
        AddBody(body.get(body.size() - 1));
        objects.add(body.get(body.size() - 1));
        if(bonusTime > 0)
            score += 2;

        else
        score++;
        speed = score / 10;
        if(speed > 5)
            speed = 5;
        head.isAddBody = false;
        isEat = true;
    }

    public void EatBigFood(List<GameObject> objects){
        AddBody(body.get(body.size() - 1));
        objects.add(body.get(body.size() - 1));
        if(bonusTime > 0)
        score+=10;
        else
            score+=5;
        speed = score / 10;
        if(speed > 5)
            speed = 5;
        head.isAddBody = false;
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
