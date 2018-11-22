package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 8:54:53
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.TSPPathCanvas.java

import java.awt.*;
import java.awt.event.*;

public class TSPPathCanvas extends Component
    implements MouseListener
{

    public TSPPathCanvas()
    {
        this(260, 270, null);
    }

    public TSPPathCanvas(int i, int j)
    {
        this(i, j, null);
    }

    public TSPPathCanvas(int i, int j, TSPInstance tspinstance)
    {
        DRAW_NUMBERS = true;
        eventChanged = false;
        BORDER = 1;
        tspi = tspinstance;
        mySize = new Dimension(i, j);
        makeTrivialPath();
        canAdd = true;
        addMouseListener(this);
        drawPath = false;
    }

    public void enableMouseEvents()
    {
        enableEvents(16L);
    }

    private void makeTrivialPath()
    {
        path = new int[tspi.max];
        for(int i = 0; i < tspi.max; i++)
            path[i] = i;

    }

    public void changeSize(int i)
    {
        tspi.resize(i);
        makeTrivialPath();
    }

    public void clearCities()
    {
        tspi.clearCities();
        makeTrivialPath();
        canAdd = true;
        drawPath = false;
    }

    public void randomize()
    {
        randomize(tspi.max);
    }

    public void randomize(int i)
    {
        tspi.clearCities();
        for(int j = 0; j < i; j++)
            tspi.addCity((int)(Math.random() * (double)(mySize.width - 28)) + 10, (int)(Math.random() * (double)(mySize.height - 20)) + 10);

        drawPath = false;
    }

    public TSPInstance getInstance()
    {
        return tspi;
    }

    public void settspi(TSPInstance tspinstance)
    {
        tspi = tspinstance;
        paint();
    }

    public synchronized void setPath(int ai[])
    {
        boolean flag = true;
        try
        {
            for(int i = 0; i < tspi.size; i++)
            {
                if(ai[i] == path[i])
                    continue;
                flag = false;
                break;
            }

            if(flag)
                return;
            for(int j = 0; j < tspi.size; j++)
                path[j] = ai[j];

        }
        catch(ArrayIndexOutOfBoundsException _ex) { }
        paint();
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

    public synchronized void paint(Graphics g)
    {
        g.setColor(getBackground());
        g.fillRect(0, 0, 400, 400);
        g.setColor(Color.black);
        g.drawRect(1, 1, mySize.width - 5, mySize.height - 5);
        g.setColor(Color.blue);
        for(int i = 0; i < tspi.size; i++)
            g.drawOval(tspi.x[i] - 2, tspi.y[i] - 2, 4, 4);

        if(DRAW_NUMBERS)
        {
            g.setColor(Color.blue);
            for(int j = 0; j < tspi.size; j++)
                g.drawString(String.valueOf(j + 1), tspi.x[j] + 4, tspi.y[j] + 1);

        }
        if(drawPath)
        {
            g.setColor(Color.red);
            int k = tspi.x[path[0]];
            int l = tspi.y[path[0]];
            for(int i1 = 1; i1 < tspi.size; i1++)
            {
                int j1 = tspi.x[path[i1]];
                int k1 = tspi.y[path[i1]];
                g.drawLine(k, l, j1, k1);
                k = j1;
                l = k1;
            }

            g.drawLine(k, l, tspi.x[path[0]], tspi.y[path[0]]);
        }
    }

    public Dimension getPreferredSize()
    {
        return mySize;
    }

    public Dimension getMinimumSize()
    {
        return mySize;
    }

    public Dimension getMaximumSize()
    {
        return mySize;
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
        if(canAdd && tspi.size != tspi.max && mouseevent.getX() < mySize.width - 20 * BORDER && mouseevent.getX() > 10 * BORDER && mouseevent.getY() < mySize.height - 10 * BORDER && mouseevent.getY() > 10 * BORDER)
        {
            drawPath = false;
            eventChanged = true;
            tspi.checkCity(mouseevent.getX(), mouseevent.getY());
            paint();
            if(parentApplet != null)
                parentApplet.actionPerformed(new ActionEvent(this, 1002, "path"));
        }
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
    }

    public synchronized boolean changed()
    {
        if(eventChanged)
        {
            eventChanged = false;
            return true;
        } else
        {
            return false;
        }
    }

    public boolean notEnough()
    {
        return tspi.size < 3;
    }

    public boolean DRAW_NUMBERS;
    protected TSPInstance tspi;
    protected int path[];
    public boolean canAdd;
    public boolean drawPath;
    protected boolean eventChanged;
    protected Dimension mySize;
    protected Graphics gr;
    protected Graphics offgr;
    protected Image offscreen;
    public ActionListener parentApplet;
    public int BORDER;
}
