package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 9:11:04
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.ProgressGraph.java

import java.awt.*;

public class ProgressGraph extends Canvas
{

    public ProgressGraph()
    {
        this(690, 140, 1);
    }

    public ProgressGraph(int i, int j, int k)
    {
        scale = 10D;
        mySize = new Dimension(i, j);
        colors = k;
        val = new int[2 * i][colors];
        end = new int[colors];
    }

    public void deleteAll()
    {
        for(int i = 0; i < colors; i++)
            end[i] = 0;

    }

    public void addValue(int i, int j)
    {
        if(++end[j] > 2 * mySize.width - 1)
            end[j] = 2 * mySize.width - 1;
        val[end[j]][j] = i;
    }

    protected void drawGraph(Graphics g)
    {
        g.setColor(Color.black);
        g.drawRect(0, 0, mySize.width - 1, mySize.height - 1);
        if(end[0] == 0)
            scale = 10D;
        else
            scale = (double)mySize.width / (double)end[0];
        for(int i = 0; i < colors; i++)
        {
            switch(i)
            {
            case 0: // '\0'
                g.setColor(Color.red);
                break;

            case 1: // '\001'
                g.setColor(Color.blue);
                break;

            case 2: // '\002'
                g.setColor(Color.green);
                break;
            }
            int j = val[0][i];
            g.drawLine(1, j, 1, j);
            for(int k = 1; k < end[i]; k++)
            {
                g.drawLine((int)((double)k * scale), j, (int)((double)(k + 1) * scale), val[k][i]);
                j = val[k][i];
            }

        }

    }

    public void initPaint()
    {
        gr = getGraphics();
        offscreen = createImage(mySize.width, mySize.height);
        offgr = offscreen.getGraphics();
    }

    public void paint()
    {
        if(offgr == null)
            initPaint();
        offgr.setColor(getBackground());
        offgr.fillRect(0, 0, mySize.width, mySize.height);
        offgr.setColor(getForeground());
        paint(offgr);
        gr.drawImage(offscreen, 0, 0, this);
    }

    public void paint(Graphics g)
    {
        drawGraph(g);
    }

    public Dimension getPreferredSize()
    {
        return mySize;
    }

    public Dimension getMaximumSize()
    {
        return mySize;
    }

    public Dimension getMinimumSize()
    {
        return mySize;
    }

    protected Dimension mySize;
    protected Graphics gr;
    protected Graphics offgr;
    protected Image offscreen;
    protected int colors;
    protected int val[][];
    protected int end[];
    protected double scale;
}
