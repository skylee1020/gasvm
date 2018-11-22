package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 8:35:31
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.TSPGAAll.java

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class TSPGAAll extends Applet
    implements ActionListener, ItemListener, Runnable
{

    public void init()
    {
        setBGColor();
        gaInitialized = false;
        population = null;
        Path = new TSPPathCanvas[16];
        GridBagLayout gridbaglayout = new GridBagLayout();
        GridBagConstraints gridbagconstraints = new GridBagConstraints();
        gridbagconstraints.insets = new Insets(0, 0, 0, 0);
        Panel panel = new Panel(gridbaglayout);
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                Path[i * 4 + j] = new TSPPathCanvas(120, 120, points);
                gridbagconstraints.fill = 10;
                gridbagconstraints.gridx = j;
                gridbagconstraints.gridy = i;
                gridbaglayout.setConstraints(Path[i * 4 + j], gridbagconstraints);
                panel.add(Path[i * 4 + j]);
                Path[i * 4 + j].drawPath = true;
                Path[i * 4 + j].parentApplet = this;
            }

        }

        Path[0].randomize(5);
        Path[0].drawPath = true;
        pv.add("one", panel);
        BestPath = new TSPPathCanvas(480, 480, points);
        Panel panel1 = new Panel(new BorderLayout());
        panel1.add("Center", BestPath);
        pv.add("two", panel1);
        BestPath.parentApplet = this;
        BestPath.canAdd = true;
        BestPath.drawPath = true;
        BestPath.BORDER = 4;
        add(pv);
        Panel panel2 = new Panel();
        panel2.add(Start);
        Start.addActionListener(this);
        panel2.add(Step);
        Step.addActionListener(this);
        panel2.add(Stop);
        Stop.addActionListener(this);
        panel2.add(Reset);
        Reset.addActionListener(this);
        panel2.add(new Label(" "));
        panel2.add(View);
        View.addActionListener(this);
        panel2.add(new Label("  Generation", 2));
        panel2.add(popNo);
        add(panel2);
        Panel panel3 = new Panel();
        panel3.add(showNumbers);
        showNumbers.setState(true);
        showNumbers.addItemListener(this);
        crossoverC.add("One point crossover");
        crossoverC.add("Two point crossover");
        crossoverC.add("No crossover");
        panel3.add(crossoverC);
        crossoverC.addItemListener(this);
        mutationC.add("Normal random mutation");
        mutationC.add("Random only improving mutation");
        mutationC.add("Systematic only improving mutation");
        mutationC.add("Random improving mutation");
        mutationC.add("Systematic improving mutation");
        mutationC.add("No mutation");
        panel3.add(mutationC);
        mutationC.addItemListener(this);
        add(panel3);
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

    public void itemStateChanged(ItemEvent itemevent)
    {
        java.awt.ItemSelectable itemselectable = itemevent.getItemSelectable();
        if(itemselectable == showNumbers)
        {
            for(int i = 0; i < 16; i++)
            {
                Path[i].DRAW_NUMBERS = showNumbers.getState();
                Path[i].paint();
                BestPath.DRAW_NUMBERS = showNumbers.getState();
                BestPath.paint();
            }

        }
        if(itemselectable == mutationC)
        {
            mutationType = mutationC.getSelectedIndex();
            if(population != null)
                population.mutationType = mutationType;
        }
        if(itemselectable == crossoverC)
        {
            crossoverType = crossoverC.getSelectedIndex();
            if(population != null)
                population.crossoverType = crossoverType;
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
        if(s.equals(View.getLabel()))
            actionChangeView();
        if(s.equals("path"))
            actionInit();
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

    protected synchronized void actionChangeView()
    {
        if(panel16)
        {
            viewLayout.last(pv);
            points.changeSize(120, 480);
        } else
        {
            viewLayout.first(pv);
            points.changeSize(480, 120);
        }
        panel16 = !panel16;
    }

    protected synchronized void actionReset()
    {
        if(!gaInitialized)
            actionInit();
        population.randomize();
        for(int i = 0; i < 16; i++)
            Path[i].setPath(((TSPChromosome)population.pop[i]).perm);

        BestPath.setPath(((TSPChromosome)population.pop[0]).perm);
    }

    protected void actionStop()
    {
        if(algThread != null)
        {
            algThread.stop();
            algThread = null;
        }
        for(int i = 0; i < 16; i++)
        {
            Path[i].paint();
            Path[i].canAdd = true;
        }

        BestPath.paint();
        BestPath.canAdd = true;
    }

    protected synchronized void actionStep()
    {
        if(!gaInitialized)
            actionInit();
        population.newGeneration();
        for(int i = 0; i < 16; i++)
            Path[i].setPath(((TSPChromosome)population.pop[i]).perm);

        BestPath.setPath(((TSPChromosome)population.pop[0]).perm);
    }

    protected void actionInit()
    {
        for(int i = 0; i < 16; i++)
            Path[i].changed();

        BestPath.changed();
        points.computeDistanceMatrix();
        TSPChromosome tspchromosome = new TSPChromosome(points.size, points);
        tspchromosome.randomize();
        tspchromosome.countFitness();
        population = new Population(tspchromosome, 16);
        population.crossoverType = crossoverType;
        population.mutationType = mutationType;
        population.popNo = popNo;
        population.randomize();
        for(int j = 0; j < 16; j++)
        {
            Path[j].setPath(((TSPChromosome)population.pop[j]).perm);
            Path[j].drawPath = true;
            Path[j].paint();
        }

        BestPath.setPath(((TSPChromosome)population.pop[0]).perm);
        BestPath.drawPath = true;
        BestPath.paint();
        gaInitialized = true;
    }

    public void run()
    {
        do
        {
            actionStep();
            try
            {
                Thread.sleep(0L);
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
        return "Title: Genetic algorithm for TSP \nAuthor: Marek Obitko, obitko@email.cz \nApplet shows GA solving travelling salesman problem. It allows to choose operators andenables to show whole population.";
    }

    public static void main(String args[])
    {
        BasicGA basicga = new BasicGA();
        new GAApplication("Genetic Algorithm for TSP", basicga, 720, 250);
    }

    public TSPGAAll()
    {
        points = new TSPInstance(100);
        Start = new Button(" Start ");
        Step = new Button(" Step ");
        Stop = new Button(" Stop ");
        Reset = new Button(" Reset ");
        View = new Button("Change View");
        popNo = new TextField("0", 5);
        showNumbers = new Checkbox("Numbers");
        mutationC = new Choice();
        crossoverC = new Choice();
        crossoverType = 0;
        mutationType = 0;
        viewLayout = new CardLayout();
        pv = new Panel(viewLayout);
        panel16 = true;
    }

    private final int STEP_PAUSE = 0;
    private final int POP_SIZE = 16;
    Thread algThread;
    Population population;
    TSPInstance points;
    protected boolean gaInitialized;
    TSPPathCanvas Path[];
    TSPPathCanvas BestPath;
    Button Start;
    Button Step;
    Button Stop;
    Button Reset;
    Button View;
    TextField popNo;
    Checkbox showNumbers;
    Choice mutationC;
    Choice crossoverC;
    public int crossoverType;
    public int mutationType;
    CardLayout viewLayout;
    Panel pv;
    protected boolean panel16;
}
