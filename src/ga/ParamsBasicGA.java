package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 8:35:21
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.ParamsBasicGA.java

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class ParamsBasicGA extends Applet
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
        add(Graph);
        add(Progress);
        Panel panel = new Panel();
        panel.setLayout(new BorderLayout());
        Panel panel1 = new Panel();
        panel1.setLayout(new FlowLayout());
        Panel panel2 = new Panel();
        panel2.setLayout(new FlowLayout());
        panel1.add(Start);
        Start.addActionListener(this);
        panel1.add(Step);
        Step.addActionListener(this);
        panel1.add(Stop);
        Stop.addActionListener(this);
        panel1.add(Continue);
        Continue.addActionListener(this);
        panel1.add(Reset);
        Reset.addActionListener(this);
        panel2.add(Speed);
        panel2.add(crossover);
        crossover.setValue(crossoverProb);
        panel2.add(mutation);
        mutation.setValue(mutationProb);
        panel2.add(Elitism);
        Elitism.addItemListener(this);
        panel.add("North", panel1);
        panel.add("South", panel2);
        add(panel);
        Progress.deleteAll();
        Graph.initPaint();
        population.elitism = Elitism.getState();
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
        if(s.equals(Continue.getLabel()))
            actionContinue();
    }

    public void itemStateChanged(ItemEvent itemevent)
    {
        population.elitism = Elitism.getState();
    }

    protected void actionStart()
    {
        initialized = false;
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

    protected void actionContinue()
    {
        initialized = true;
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
        Graph.setPopulation(population);
        population.randomize();
        Progress.deleteAll();
        Progress.paint();
        Graph.paint();
        changes();
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
        changes();
        population.newGeneration(crossoverProb, mutationProb);
        Progress.addValue(population.fitnessSum / population.size, 1);
        Progress.addValue(population.pop[0].fitness, 0);
        Graph.paint();
        Progress.paint();
    }

    protected void changes()
    {
        if(Speed.changed())
            STEP_PAUSE = Speed.getValue();
        if(crossover.changed())
            crossoverProb = crossover.getValue();
        if(mutation.changed())
            mutationProb = mutation.getValue();
    }

    public void run()
    {
        if(!initialized)
        {
            Graph.setPopulation(population);
            Graph.paint();
            Progress.deleteAll();
            Progress.paint();
            changes();
            try
            {
                Thread.sleep(STEP_PAUSE);
            }
            catch(InterruptedException _ex) { }
        }
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
        return "Title: Genetic algorithm with parameters \nAuthor: Marek Obitko, obitko@email.cz \nApplet shows GA and allows to change parameters of GA. Also shows progress graph.";
    }

    public static void main(String args[])
    {
        ParamsBasicGA paramsbasicga = new ParamsBasicGA();
        new GAApplication("Basic genetic algorithm with possibility of parameters change", paramsbasicga, 720, 430);
    }

    public ParamsBasicGA()
    {
        Graph = new GraphCanvas();
        Start = new Button(" Start ");
        Step = new Button(" Step ");
        Stop = new Button(" Stop ");
        Continue = new Button(" Continue ");
        Reset = new Button(" Reset ");
        Elitism = new Checkbox("Elitism");
        Speed = new ShifterLabel("Speed", 100, 10, 10, 1000D);
        crossover = new ShifterLabel("Crossover");
        mutation = new ShifterLabel("Mutation");
        Progress = new ProgressGraph(690, 140, 2);
        initialized = false;
        crossoverProb = 30;
        mutationProb = 4;
    }

    protected int STEP_PAUSE;
    private final int POP_SIZE = 16;
    Thread algThread;
    Population population;
    GraphCanvas Graph;
    Button Start;
    Button Step;
    Button Stop;
    Button Continue;
    Button Reset;
    Checkbox Elitism;
    ShifterLabel Speed;
    ShifterLabel crossover;
    ShifterLabel mutation;
    ProgressGraph Progress;
    protected boolean initialized;
    protected int crossoverProb;
    protected int mutationProb;
}
