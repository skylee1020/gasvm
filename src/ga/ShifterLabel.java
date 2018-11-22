package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 8:54:45
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.ShifterLabel.java

import java.awt.*;

class ShifterLabel extends Panel
{

    public ShifterLabel(String s)
    {
        this(s, 100);
    }

    public ShifterLabel(String s, int i)
    {
        this(s, i, 10, 10, 100D);
    }

    public ShifterLabel(String s, int i, int j, int k, double d)
    {
        shifter = new Shifter(i, j, k, d);
        setLayout(new BorderLayout());
        label = new Label(s);
        number = new Label(String.valueOf((int)d - 1));
        add("West", label);
        add("East", number);
        add("South", shifter);
        shifter.setValueLabel(number);
    }

    public int value()
    {
        return shifter.getValue();
    }

    public int getValue()
    {
        return shifter.getValue();
    }

    public void setValue(int i)
    {
        shifter.setValue(i);
    }

    public boolean changed()
    {
        return shifter.changed();
    }

    Shifter shifter;
    Label label;
    Label number;
}
