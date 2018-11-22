package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 9:01:05
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.CrossoverCanvas.java

import java.awt.*;

public class CrossoverCanvas extends Canvas
{

    public CrossoverCanvas()
    {
        this(690, 275);
    }

    public CrossoverCanvas(int i, int j)
    {
        animating = false;
        elitism = false;
        mySize = new Dimension(i, j);
    }

    public void animate(boolean flag)
    {
        animating = flag;
        paint();
    }

    protected void drawLabels(Graphics g)
    {
        g.drawString("Present population", 87, 245);
        g.drawString("New population", 510, 245);
        g.drawString("Parents", 262, 245);
        g.drawString("New", 340, 245);
        g.drawString("Offspring", 327, 258);
        g.drawString("Mutated", 397, 245);
        g.drawString("Offspring", 392, 258);
    }

    public void makeElitism(int i)
    {
        index = i;
        crossover = 0;
        elitism = true;
        paint();
    }

    public void showParents()
    {
        crossover = 1;
        elitism = false;
        paint();
        if(population.parent1.lastCrossoverPoint == -1)
        {
            gr.drawString("No crossover", 330, 130);
            gr.drawString("(childrens will be", 322, 150);
            gr.drawString("copies of parents)", 320, 170);
            return;
        } else
        {
            gr.drawString("Crossover point", 320, 9 + population.parent1.lastCrossoverPoint * 7);
            return;
        }
    }

    public void showChildren()
    {
        crossover = 2;
        paint();
    }

    public void showMutated()
    {
        crossover = 3;
        paint();
    }

    public void showNewChildren(int i)
    {
        crossover = 4;
        elitism = false;
        int j = (index - 1) * 10 + 472;
        for(int k = 405; k < j; k += 10)
        {
            paint();
            population.child[0].paint(gr, k, 5);
            population.child[1].paint(gr, k + 20, 5);
        }

        index = i;
        crossover = 0;
        paint();
    }

    public void showSort()
    {
        crossover = 0;
        paint();
        gr.drawString("has been sorted", 95, 258);
    }

    public void removeOldPopulation()
    {
        crossover = 0;
        elitism = false;
        for(int i = 472; i > 62; i -= 10)
        {
            offgr.setColor(getBackground());
            offgr.fillRect(0, 0, mySize.width, mySize.height);
            offgr.setColor(Color.black);
            for(int j = 0; j < population.size; j++)
                population.pop[j].paintEmpty(offgr, j * 10 + 62, 5);

            for(int k = 0; k < population.size; k++)
                population.newpop[k].paint(offgr, k * 10 + i, 5);

            offgr.setColor(Color.black);
            offgr.drawRect(0, 0, mySize.width - 1, mySize.height - 1);
            offgr.drawString("Removing old population and accepting the new one", 230, 258);
            gr.drawImage(offscreen, 0, 0, this);
        }

        index = 0;
        paint();
    }

    public void showPosition(Graphics g, int i, int j, Chromosome chromosome)
    {
        i += 3;
        g.setColor(Color.green);
        g.drawLine(i, 233, i, 265 + j);
        int k = (int)((690L * ((NumberChromosome)chromosome).value()) / 0x100000000L);
        g.drawLine(i, 265 + j, k, 265 + j);
        g.drawLine(k, 265 + j, k, 273);
        g.drawLine(k, 273, k - 2, 270);
        g.drawLine(k, 273, k + 2, 270);
        g.setColor(Color.black);
    }

    protected void drawPopulation(Graphics g)
    {
        if(population == null)
            return;
        for(int i = 0; i < population.size; i++)
            population.pop[i].paint(g, i * 10 + 62, 5);

        int j;
        for(j = 0; j < index; j++)
            population.newpop[j].paint(g, j * 10 + 472, 5);

        for(; j < population.size; j++)
            population.pop[j].paintEmpty(g, j * 10 + 472, 5);

        if(elitism)
        {
            g.drawString("Elitism", 320, 110);
            g.drawString("(the best chromosomes are just copied)", 240, 130);
            showPosition(g, (index - 1) * 10 + 472, 0, population.newpop[index - 1]);
        }
        switch(crossover)
        {
        case 4: // '\004'
            g.drawString("Accepting new offspring", 240, 140);
            return;

        case 3: // '\003'
            population.child[0].paint(g, 405, 5);
            population.child[1].paint(g, 425, 5);
            showPosition(g, 425, 0, population.child[1]);
            showPosition(g, 405, -3, population.child[0]);
            for(int l = 0; l < 2; l++)
            {
                for(int k = 0; k < 32; k++)
                    if(((NumberChromosome)population.child[l]).bit[k] != ((NumberChromosome)population.oldchild[l]).bit[k])
                        g.fillRect(l * 20 + 400, 7 + k * 7, 4, 4);

            }

            // fall through

        case 2: // '\002'
            population.oldchild[0].paint(g, 340, 5);
            population.oldchild[1].paint(g, 360, 5);
            if(crossover == 2)
            {
                showPosition(g, 340, 0, population.oldchild[0]);
                showPosition(g, 360, -3, population.oldchild[1]);
            }
            // fall through

        case 1: // '\001'
            population.parent1.paint(g, 270, 5);
            population.parent2.paint(g, 290, 5);
            if(crossover == 1)
            {
                showPosition(g, 270, 0, population.parent1);
                showPosition(g, 290, -3, population.parent2);
                int ai[] = population.parentIndex;
                g.fillRect(ai[0] * 10 + 63, 2, 7, 2);
                g.fillRect(ai[1] * 10 + 63, 2, 7, 2);
                g.fillRect(ai[0] * 10 + 63, 232, 7, 2);
                g.fillRect(ai[1] * 10 + 63, 232, 7, 2);
            }
            g.setColor(Color.green);
            g.fillRect(265, 5 + population.parent1.lastCrossoverPoint * 7, 173, 2);
            g.setColor(Color.black);
            return;

        case 0: // '\0'
        default:
            return;
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
        offgr.setColor(Color.black);
        paint(offgr);
        gr.drawImage(offscreen, 0, 0, this);
    }

    public void paint(Graphics g)
    {
        g.setColor(Color.black);
        g.drawRect(0, 0, mySize.width - 1, mySize.height - 1);
        if(!animating)
        {
            return;
        } else
        {
            drawLabels(g);
            drawPopulation(g);
            return;
        }
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
    protected boolean animating;
    protected boolean elitism;
    protected int index;
    protected int crossover;
}
