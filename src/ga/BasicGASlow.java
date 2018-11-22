package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 8:33:41
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.BasicGASlow.java

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class BasicGASlow extends Applet
    implements ActionListener, ItemListener, Runnable
{

    public void init()
    {
        setBGColor();
        NumberChromosome numberchromosome = new NumberChromosome(32);
        numberchromosome.randomize();
        numberchromosome.countFitness();
        population = new Population(numberchromosome, 16);
        population.randomize();
        add(PopGraph);
        add(Graph);
        add(Start);
        Start.addActionListener(this);
        add(Step);
        Step.addActionListener(this);
        add(Stop);
        Stop.addActionListener(this);
        add(Reset);
        Reset.addActionListener(this);
        add(Fast);
        Fast.addItemListener(this);
        Graph.initPaint();
        PopGraph.setPopulation(population);
        PopGraph.animate(!fastRun);
        population.setCrossoverCanvas(PopGraph);
        Graph.setPopulation(population);
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

    public void itemStateChanged(ItemEvent itemevent)
    {
        fastRun = Fast.getState();
        if(fastRun)
            STEP_PAUSE = 0;
        else
            STEP_PAUSE = 1000;
        PopGraph.animate(!fastRun);
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
        population.stepNumber = 0;
        population.randomize();
        Graph.paint();
        PopGraph.paint();
    }

    protected void actionStop()
    {
        if(algThread != null)
        {
            algThread.stop();
            algThread = null;
        }
        Graph.paint();
        PopGraph.paint();
    }

    protected void actionStep()
    {
        if(fastRun)
        {
            population.newGeneration(80D, 4D);
            Graph.paint();
            PopGraph.paint();
            return;
        } else
        {
            population.makeStep();
            Graph.paint();
            return;
        }
    }

    public void run()
    {
        Graph.setPopulation(population);
        Graph.paint();
        actionStep();
        try
        {
            Thread.sleep(STEP_PAUSE);
        }
        catch(InterruptedException _ex) { }
        do
        {
            actionStep();
            try
            {
                Thread.sleep(STEP_PAUSE);
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
        return "Title: Genetic algorithm in 2D function with visualization of population\nAuthor: Marek Obitko, obitko@email.cz \nApplet shows GA in 2D function. Visualises population forming.";
    }

    public static void main(String args[])
    {
        BasicGASlow basicgaslow = new BasicGASlow();
        new GAApplication("Genetic Algorithm", basicgaslow, 730, 550);
    }

    public BasicGASlow()
    {
        STEP_PAUSE = 1000;
        Graph = new GraphCanvas();
        PopGraph = new CrossoverCanvas();
        Start = new Button(" Start ");
        Step = new Button(" Step ");
        Stop = new Button(" Stop ");
        Reset = new Button(" Reset ");
        Fast = new Checkbox("Fast");
        fastRun = false;
    }

    private final int SLOW_STEP_PAUSE = 1000;
    private int STEP_PAUSE;
    private final int POP_SIZE = 16;
    Thread algThread;
    Population population;
    GraphCanvas Graph;
    CrossoverCanvas PopGraph;
    Button Start;
    Button Step;
    Button Stop;
    Button Reset;
    Checkbox Fast;
    boolean fastRun;
}
