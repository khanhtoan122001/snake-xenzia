package com.snakexenzia.game.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;



public abstract class GameObject {
    protected static final int BlockSize = 16;

    protected Vector2 pos, dim, lastPos;
    protected float speed;
    protected int health;
    protected Rectangle screen;
    protected Rectangle bounds;
    protected float dx, dy;

    protected Pixmap pixmap;
    protected Texture tex;
    protected Sprite sprite;
    protected Color color;

    protected int width;
    protected int height;
    protected boolean isPause;

    protected GameObject() {
        pos = new Vector2();
        dim = new Vector2();
        speed = 0f;
        health = 0;
        isPause = false;
    }

    protected void createGraphics() {

        pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        pixmap.drawRectangle(0, 0, width, height);

        tex = new Texture(pixmap);

        sprite = new Sprite(tex);

    }

    public void update(int frameCount, List<GameObject> objects) {

    }

    public abstract void render(SpriteBatch sb);

    public void calcCollision(List<GameObject> objects, List<coEvent> coEvents){
        for (GameObject object:
             objects) {
            if(this.getBounds().overlaps(object.getBounds()) && !this.equals(object)){
                coEvent co = new coEvent();
                co.object = object;
                co.nx = object.getDim().x;
                co.ny = object.getDim().y;
                coEvents.add(co);
            }
        }
    }

    public void updateLastPos(){
        setLastPos(getPos());
    }

    // get


    public Vector2 getLastPos() {
        return lastPos;
    }

    public Vector2 getPos() {
        return pos;
    }

    public Vector2 getDim() {
        return dim;
    }

    public float getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Rectangle getScreen() {
        return screen;
    }

    public Rectangle getBounds() {
        bounds = new Rectangle(pos.x,pos.y, BlockSize, BlockSize);
        return bounds;
    }

    // set

    public void setLastPos(Vector2 lastPos) {
        this.lastPos = lastPos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public void setDim(Vector2 dim) {
        this.dim = dim;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setScreen(Rectangle screen) {
        this.screen = screen;
    }

}
