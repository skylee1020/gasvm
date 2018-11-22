package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 9:02:01
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.GraphCanvas.java

import java.awt.*;

public class GraphCanvas extends Canvas
{

    public GraphCanvas()
    {
        this(690, 140);
    }

    public GraphCanvas(int i, int j)
    {
        mySize = new Dimension(i, j);
    }

    protected void drawGraph(Graphics g)
    {
        g.setColor(Color.black);
        g.drawRect(0, 0, mySize.width - 1, mySize.height - 1);
        for(int i = 0; i < mySize.height; i += 20)
            g.drawLine(0, i, mySize.width - 1, i);

        g.setColor(Color.blue);
        int j = 0;
        for(int l = 0; l < mySize.width - 1; l++)
        {
            int k = Function.value(l);
            if(l == 0)
            {
                j = k;
            } else
            {
                g.drawLine(l - 1, j, l, k);
                j = k;
            }
        }

    }

    protected void drawPopulation(Graphics g)
    {
        if(population == null)
            return;
        g.setColor(Color.green);
        for(int k = 1; k < population.size; k++)
        {
            int i = (int)((690L * ((NumberChromosome)population.pop[k]).value()) / 0x100000000L);
            g.drawLine(i, 1, i, mySize.width - 2);
        }

        g.setColor(Color.red);
        int j = (int)((690L * ((NumberChromosome)population.pop[0]).value()) / 0x100000000L);
        g.drawLine(j, 1, j, mySize.width - 2);
    }

    public void initPaint()
    {
        gr = getGraphics();
        //System.out.println("[sky1020] initPaint() gr:" + gr + ", isDisplay" + isDisplayable());

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
        drawPopulation(g);
    }

    public void setPopulation(Population population1)
    {
        population = population1;
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
    protected Population population;
    protected Graphics gr;
    protected Graphics offgr;
    protected Image offscreen;
}
