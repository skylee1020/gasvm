package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 9:01:34
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.Shifter.java

import java.awt.*;
import java.awt.event.*;

public class Shifter extends Canvas
    implements MouseMotionListener, MouseListener
{

    public Shifter(int i, int j)
    {
        this(i, j, 10, 100D);
    }

    public Shifter(int i, int j, int k)
    {
        this(i, j, k, 100D);
    }

    public Shifter(int i, int j, int k, double d)
    {
        boxWidth = 10;
        mRange = 100D;
        change = true;
        mouseDown = false;
        mySize = new Dimension(i, j);
        setSize(i, j);
        boxWidth = k;
        mRange = d;
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    public int getValue()
    {
        return (int)((mRange * (double)value) / (double)(mySize.width - boxWidth - 1));
    }

    public void setValue(int i)
    {
        value = (int)((double)(i * (mySize.width - boxWidth - 1)) / mRange);
        repaint();
    }

    public void setValueLabel(Label label1)
    {
        label = label1;
    }

    public void paint(Graphics g)
    {
        getSize();
        g.setColor(Color.black);
        g.drawRect(0, 0, mySize.width - 1, mySize.height - 1);
        g.fillRect(value, 0, boxWidth, mySize.height - 1);
        if(label != null)
            label.setText(String.valueOf(getValue()));
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

    public boolean changed()
    {
        if(change)
        {
            change = false;
            return true;
        } else
        {
            return false;
        }
    }

    public void mouseDragged(MouseEvent mouseevent)
    {
        if(!mouseDown)
            return;
        int i = mouseevent.getY();
        if(i < 0)
            return;
        if(i > mySize.height)
            return;
        int j = mouseevent.getX();
        if(j < 0)
            return;
        if(j > mySize.width)
            return;
        int k = j - boxWidth / 2;
        if(k < 0)
            k = 0;
        if(k > mySize.width - boxWidth - 1)
            k = mySize.width - boxWidth - 1;
        if(value != k)
        {
            value = k;
            change = true;
            repaint();
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
        int i = mouseevent.getY();
        if(i < 0)
            return;
        if(i > mySize.height)
            return;
        int j = mouseevent.getX();
        if(j < 0)
            return;
        if(j > mySize.width)
        {
            return;
        } else
        {
            mouseDown = true;
            mouseDragged(mouseevent);
            return;
        }
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
        mouseDown = false;
    }

    protected int value;
    protected int boxWidth;
    protected double mRange;
    private Label label;
    protected Dimension mySize;
    protected boolean change;
    protected boolean mouseDown;
}
