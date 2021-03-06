package com.snakexenzia.game.service;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.snakexenzia.game.gameobjects.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * PR QuadTree implementation for LibGDX
 *
 * @author Nahum Rosillo
 */

public class QuadTree
{
    //  --- Configuration
    private static final int MAX_OBJECTS_BY_NODE = 20;
    private static final int MAX_LEVEL = 4;
    //  ---

    private final int level;
    private final List<GameObject> objects;
    private final Rectangle bounds;
    private final QuadTree[] nodes;

    public QuadTree(int level, Rectangle bounds)
    {
        this.level = level;
        this.bounds = bounds;
        objects = new ArrayList<>();
        nodes = new QuadTree[4];
    }

    public void getZones(Array<Rectangle> allZones)
    {
        allZones.add(bounds);
        if (nodes[0] != null)
        {
            nodes[0].getZones(allZones);
            nodes[1].getZones(allZones);
            nodes[2].getZones(allZones);
            nodes[3].getZones(allZones);
        }
    }

    public void clear()
    {
        objects.clear();
        for (int i = 0; i < nodes.length; i++)
        {
            if (nodes[i] != null)
            {
                nodes[i].clear();
                nodes[i] = null;
            }
        }
    }

    public void insert(GameObject rect)
    {
        if (nodes[0] != null)
        {
            int index = getIndex(rect);
            if (index != -1)
            {
                nodes[index].insert(rect);
                return;
            }
        }

        objects.add(rect);

        if (objects.size() > MAX_OBJECTS_BY_NODE && level < MAX_LEVEL)
        {
            if (nodes[0] == null)
                split();

            int i = 0;
            while(i < objects.size())
            {
                int index = getIndex(objects.get(i));

                if (index != -1)
                    nodes[index].insert(objects.remove(i));
                else
                    i++;
            }
        }
    }

    public List<GameObject> retrieve(List<GameObject> list, GameObject area)
    {
        int index = getIndex(area);

        if (index != -1 & nodes[0] != null)
            nodes[index].retrieve(list, area);

        list.addAll(this.objects);

        return list;
    }

    public List<GameObject> retrieveFast(List<GameObject> list, GameObject area)
    {
        int index = getIndex(area);

        if (index != -1 & nodes[0] != null)
            nodes[index].retrieveFast(list, area);

        //  This if(..) is configurable: only process elements in MAX_LEVEL and MAX_LEVEL-1
        if (level == MAX_LEVEL || level == MAX_LEVEL-1)
            list.addAll(objects);

        return list;
    }

    private void split()
    {
        float subWidth =  (bounds.getWidth() * 0.5f);
        float subHeight = (bounds.getHeight() * 0.5f);
        float x = bounds.getX();
        float y = bounds.getY();

        nodes[0] = new QuadTree(level+1, new Rectangle(x + subWidth, y, subWidth, subHeight));
        nodes[1] = new QuadTree(level+1, new Rectangle(x, y, subWidth, subHeight));
        nodes[2] = new QuadTree(level+1, new Rectangle(x, y + subHeight, subWidth, subHeight));
        nodes[3] = new QuadTree(level+1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight));

    }

    private int getIndex(GameObject object)
    {
        int index = -1;
        Rectangle bound = object.getBounds();
        Rectangle pRect = new Rectangle(bound.x - 32, bound.y - 32, bound.width + 64, bound.height + 64);
        float verticalMidpoint = bounds.getX() + (bounds.getWidth() * 0.5f);
        float horizontalMidpoint = bounds.getY() + (bounds.getHeight() * 0.5f);

        boolean topQuadrant = (pRect.getY() < horizontalMidpoint && pRect.getY() + pRect.getHeight() < horizontalMidpoint);
        boolean bottomQuadrant = (pRect.getY() > horizontalMidpoint);

        if (pRect.getX() < verticalMidpoint && pRect.getX() + pRect.getWidth() < verticalMidpoint)
        {
            if (topQuadrant)
                index = 1;
            else if (bottomQuadrant)
                index = 2;
        }
        else if (pRect.getX() > verticalMidpoint)
        {
            if (topQuadrant)
                index = 0;
            else if (bottomQuadrant)
                index = 3;
        }

        return index;
    }
}