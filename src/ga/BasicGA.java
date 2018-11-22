package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 8:33:21
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.BasicGA.java

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BasicGA extends Applet
    implements ActionListener, Runnable
{

    public void init()
    {
        setBGColor();
        NumberChromosome numberchromosome = new NumberChromosome(32);
        numberchromosome.randomize();
        numberchromosome.countFitness();
        population = new Population(numberchromosome, 16);
        population.randomize();
        add(Graph);
        add(Start);
        Start.addActionListener(this);
        add(Step);
        Step.addActionListener(this);
        add(Stop);
        Stop.addActionListener(this);
        add(Reset);
        Reset.addActionListener(this);
        Graph.initPaint();
    }

    protected void setBGColor()
    {
        try
        {
            Color color = new Color(Integer.parseInt(getParameter("BGCOLOR"), 16));
            setBackground(color);
            return;
        }
        catch(NumberFormatException _ex)
        {
            return;
        }
        catch(NullPointerException _ex)
        {
            return;
        }
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        String s = actionevent.getActionCommand();
        if(s.equals(Start.getLabel()))
            actionStart();
        if(s.equals(Stop.getLabel()))
            actionStop();
        if(s.equals(Reset.getLabel()))
            actionReset();
        if(s.equals(Step.getLabel()))
            actionStep();
    }

    protected void actionStart()
    {
        if(algThread != null)
        {
            return;
        } else
        {
            algThread = new Thread(this);
            algThread.start();
            return;
        }
    }

    protected void actionReset()
    {
        population.randomize();
        Graph.paint();
    }

    protected void actionStop()
    {
        if(algThread != null)
        {
            algThread.stop();
            algThread = null;
        }
        Graph.paint();
    }

    protected void actionStep()
    {
        population.newGeneration(60D, 5D);
        Graph.paint();
    }

    public void run()
    {
        //System.out.println("[sky1020] ga.BasicGA started");
        Graph.setPopulation(population);
        Graph.paint();
        try
        {
            Thread.sleep(200L);
        }
        catch(InterruptedException _ex) { }
        do
        {
            actionStep();
            try
            {
                Thread.sleep(200L);
            }
            catch(InterruptedException _ex) { }
        } while(true);
    }

    public void destroy()
    {
        if(algThread != null)
        {
            algThread.stop();
            algThread = null;
        }
        super.destroy();
    }

    protected void finalize()
        throws Throwable
    {
        if(algThread != null)
        {
            algThread.stop();
            algThread = null;
        }
        super.finalize();
    }

    public String[][] getParameterInfo()
    {
        String as[][] = {
            {
                "BGCOLOR", "color (6 hexa digits)", "Background color, for example \"00ff40\"."
            }
        };
        return as;
    }

    public String getAppletInfo()
    {
        return "Title: Genetic algorithm in 2D function \nAuthor: Marek Obitko, obitko@email.cz \nApplet shows GA in 2D function.";
    }

    public static void main(String args[])
    {
        BasicGA basicga = new BasicGA();
        new GAApplication("Basic Genetic Algorithm", basicga, 700, 500);
    }

    public BasicGA()
    {
        Graph = new GraphCanvas();
        Start = new Button(" Start ");
        Step = new Button(" Step ");
        Stop = new Button(" Stop ");
        Reset = new Button(" Reset ");
    }

    private final int STEP_PAUSE = 200;
    private final int POP_SIZE = 16;
    Thread algThread;
    Population population;
    GraphCanvas Graph;
    Button Start;
    Button Step;
    Button Stop;
    Button Reset;
}
