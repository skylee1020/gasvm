package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 9:01:15
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.Graph3DCanvas.java

import java.awt.*;
import java.awt.event.*;

public class Graph3DCanvas extends Canvas
    implements MouseMotionListener, MouseListener
{

    public Graph3DCanvas(Graph3D graph3d)
    {
        this(350, 350, graph3d);
    }

    public Graph3DCanvas(int i, int j, Graph3D graph3d)
    {
        mouseDown = false;
        mySize = new Dimension(i, j);
        g3d = graph3d;
        g3d.cSize = i;
        g3d.recalculate();
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    protected void drawGraph(Graphics g)
    {
        if(!g3d.valid)
            return;
        drawBox(g);
        int i = 0;
        int j = 0;
        for(int k2 = 0; k2 < g3d.steps; k2 += g3d.bigStep)
        {
            for(int k3 = 0; k3 < g3d.steps; k3++)
            {
                int k = g3d.x[k2][k3];
                int k1 = g3d.y[k2][k3];
                if(k3 != 0)
                    g.drawLine(i, j, k, k1);
                i = k;
                j = k1;
            }

        }

        int l2 = g3d.steps - 1;
        for(int l3 = 0; l3 < g3d.steps; l3++)
        {
            int l = g3d.x[l2][l3];
            int l1 = g3d.y[l2][l3];
            if(l3 != 0)
                g.drawLine(i, j, l, l1);
            i = l;
            j = l1;
        }

        for(int i4 = 0; i4 < g3d.steps; i4 += g3d.bigStep)
        {
            for(int i3 = 0; i3 < g3d.steps; i3++)
            {
                int i1 = g3d.x[i3][i4];
                int i2 = g3d.y[i3][i4];
                if(i3 != 0)
                    g.drawLine(i, j, i1, i2);
                i = i1;
                j = i2;
            }

        }

        int j4 = g3d.steps - 1;
        for(int j3 = 0; j3 < g3d.steps; j3++)
        {
            int j1 = g3d.x[j3][j4];
            int j2 = g3d.y[j3][j4];
            if(j3 != 0)
                g.drawLine(i, j, j1, j2);
            i = j1;
            j = j2;
        }

    }

    protected void drawBox(Graphics g)
    {
        long al[] = new long[2];
        int ai[] = new int[2];
        int ai1[] = new int[2];
        int ai2[] = new int[2];
        int ai3[] = new int[2];
        int ai4[] = new int[2];
        int ai5[] = new int[2];
        int ai6[] = new int[2];
        int ai7[] = new int[2];
        g.setColor(Color.cyan);
        al[0] = 0L;
        al[1] = 0L;
        g3d.xyBottomCoords(al, ai);
        g3d.xyTopCoords(al, ai3);
        al[1] = 0x100000000L;
        g3d.xyBottomCoords(al, ai4);
        g3d.xyTopCoords(al, ai7);
        al[0] = 0x100000000L;
        g3d.xyBottomCoords(al, ai5);
        g3d.xyTopCoords(al, ai6);
        al[1] = 0L;
        g3d.xyBottomCoords(al, ai1);
        g3d.xyTopCoords(al, ai2);
        g.drawLine(ai[0], ai[1], ai3[0], ai3[1]);
        g.drawLine(ai[0], ai[1], ai4[0], ai4[1]);
        g.drawLine(ai[0], ai[1], ai1[0], ai1[1]);
        g.drawLine(ai6[0], ai6[1], ai5[0], ai5[1]);
        g.drawLine(ai6[0], ai6[1], ai7[0], ai7[1]);
        g.drawLine(ai6[0], ai6[1], ai2[0], ai2[1]);
        g.drawLine(ai3[0], ai3[1], ai2[0], ai2[1]);
        g.drawLine(ai3[0], ai3[1], ai7[0], ai7[1]);
        g.drawLine(ai1[0], ai1[1], ai2[0], ai2[1]);
        g.drawLine(ai1[0], ai1[1], ai5[0], ai5[1]);
        g.drawLine(ai4[0], ai4[1], ai5[0], ai5[1]);
        g.drawLine(ai4[0], ai4[1], ai7[0], ai7[1]);
        g.setColor(Color.black);
    }

    protected void drawPopulation(Graphics g)
    {
        if(population == null)
            return;
        long al[] = new long[2];
        int ai[] = new int[2];
        int ai1[] = new int[2];
        int ai2[] = new int[2];
        g.setColor(Color.blue);
        for(int i = 1; i < population.size; i++)
        {
            al[0] = ((Number3DChromosome)population.pop[i]).xvalue();
            al[1] = ((Number3DChromosome)population.pop[i]).yvalue();
            g3d.xyBottomCoords(al, ai);
            g3d.xyTopCoords(al, ai1);
            g3d.xyZCoords(al, ai2);
            g.drawLine(ai[0], ai[1], ai1[0], ai1[1]);
            g.fillRect(ai2[0] - 2, ai2[1] - 2, 5, 5);
        }

        g.setColor(Color.red);
        al[0] = ((Number3DChromosome)population.pop[0]).xvalue();
        al[1] = ((Number3DChromosome)population.pop[0]).yvalue();
        StringBuffer stringbuffer = new StringBuffer(String.valueOf(g3d.xValue(al[0])));
        stringbuffer.setLength(11);
        String s = "Best [X,Y,Z]=[" + stringbuffer + "; ";
        stringbuffer = new StringBuffer(String.valueOf(g3d.yValue(al[1])));
        stringbuffer.setLength(11);
        s = s + stringbuffer + "; ";
        stringbuffer = new StringBuffer(String.valueOf(g3d.zValue(al[0], al[1])));
        stringbuffer.setLength(11);
        s = s + stringbuffer + "]";
        g3d.xyBottomCoords(al, ai);
        g3d.xyTopCoords(al, ai1);
        g3d.xyZCoords(al, ai2);
        theBest = s;
        g.drawLine(ai[0], ai[1], ai1[0], ai1[1]);
        g.fillRect(ai2[0] - 2, ai2[1] - 2, 5, 5);
        g.setColor(Color.black);
    }

    public void initPaint()
    {
        gr = getGraphics();
        offscreen = createImage(mySize.width, mySize.height);
        offgr = offscreen.getGraphics();
    }

    public synchronized void paint()
    {
        if(offgr == null)
            initPaint();
        offgr.setColor(getBackground());
        offgr.fillRect(0, 0, mySize.width, mySize.height);
        offgr.setColor(getForeground());
        paint(offgr);
        gr.drawImage(offscreen, 0, 0, this);
    }

    public synchronized void paint(Graphics g)
    {
        g.setColor(Color.black);
        drawGraph(g);
        drawPopulation(g);
        if(Function3D.wasError)
        {
            g.setColor(Color.red);
            g.drawRect(0, 0, mySize.width - 1, mySize.height - 1);
            g.drawRect(1, 1, mySize.width - 3, mySize.height - 3);
            g.drawString("Please check inputs.", 10, 20);
            g.setColor(Color.black);
            return;
        } else
        {
            g.setColor(Color.black);
            g.drawRect(0, 0, mySize.width - 1, mySize.height - 1);
            return;
        }
    }

    public synchronized void resetAll()
    {
        g3d.recalculate();
        paint();
    }

    public synchronized void mouseDragged(MouseEvent mouseevent)
    {
        if(!mouseDown)
        {
            return;
        } else
        {
            double d = (double)(mouseevent.getX() - mouseX) * 0.0087266462599716477D;
            double d1 = (double)(mouseevent.getY() - mouseY) * 0.0087266462599716477D;
            g3d.theta -= d;
            g3d.phi += d1;
            mouseX = mouseevent.getX();
            mouseY = mouseevent.getY();
            g3d.recalculate();
            paint();
            return;
        }
    }

    public void mouseMoved(MouseEvent mouseevent)
    {
    }

    public void mouseClicked(MouseEvent mouseevent)
    {
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
    }

    public void mouseExited(MouseEvent mouseevent)
    {
    }

    public void mousePressed(MouseEvent mouseevent)
    {
        mouseX = mouseevent.getX();
        mouseY = mouseevent.getY();
        mouseDown = true;
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
        mouseDown = false;
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
    Graph3D g3d;
    String theBest;
    int mouseX;
    int mouseY;
    protected static final double SHIFT_FACTOR = 0.0087266462599716477D;
    protected boolean mouseDown;
}
