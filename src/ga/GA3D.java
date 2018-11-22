package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 8:35:10
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.GA3D.java

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class GA3D extends Applet
    implements ActionListener, KeyListener, ItemListener, Runnable
{

    public void init()
    {
        setBGColor();
        Number3DChromosome number3dchromosome = new Number3DChromosome(32, Graph.g3d);
        number3dchromosome.randomize();
        number3dchromosome.countFitness();
        population = new Population(number3dchromosome, 16);
        population.randomize();
        population.popNo = popNo;
        add(Graph);
        GridBagLayout gridbaglayout = new GridBagLayout();
        GridBagConstraints gridbagconstraints = new GridBagConstraints();
        gridbagconstraints.insets = new Insets(2, 2, 2, 2);
        Panel panel = new Panel(gridbaglayout);
        Panel panel1 = new Panel();
        panel1.add(expressionText);
        expressionText.addKeyListener(this);
        panel1.add(Change);
        Change.addActionListener(this);
        gridbagconstraints.fill = 10;
        gridbagconstraints.gridx = 0;
        gridbagconstraints.gridy = 2;
        gridbaglayout.setConstraints(panel1, gridbagconstraints);
        panel.add(panel1);
        Panel panel2 = new Panel(gridbaglayout);
        gridbagconstraints.fill = 10;
        gridbagconstraints.gridx = 0;
        gridbagconstraints.gridy = 0;
        Label label = new Label("X min");
        gridbaglayout.setConstraints(label, gridbagconstraints);
        panel2.add(label);
        label = new Label("X max");
        gridbagconstraints.gridx = 1;
        gridbaglayout.setConstraints(label, gridbagconstraints);
        panel2.add(label);
        label = new Label("Y min");
        gridbagconstraints.gridx = 2;
        gridbaglayout.setConstraints(label, gridbagconstraints);
        panel2.add(label);
        label = new Label("Y max");
        gridbagconstraints.gridx = 3;
        gridbaglayout.setConstraints(label, gridbagconstraints);
        panel2.add(label);
        gridbagconstraints.gridx = 0;
        gridbagconstraints.gridy = 1;
        gridbaglayout.setConstraints(xmin, gridbagconstraints);
        panel2.add(xmin);
        xmin.addKeyListener(this);
        gridbagconstraints.gridx = 1;
        gridbaglayout.setConstraints(xmax, gridbagconstraints);
        panel2.add(xmax);
        xmax.addKeyListener(this);
        gridbagconstraints.gridx = 2;
        gridbaglayout.setConstraints(ymin, gridbagconstraints);
        panel2.add(ymin);
        ymin.addKeyListener(this);
        gridbagconstraints.gridx = 3;
        gridbaglayout.setConstraints(ymax, gridbagconstraints);
        panel2.add(ymax);
        ymax.addKeyListener(this);
        gridbagconstraints.insets = new Insets(2, 35, 2, 2);
        gridbagconstraints.gridx = 4;
        gridbagconstraints.gridy = 0;
        label = new Label("Generation");
        gridbaglayout.setConstraints(label, gridbagconstraints);
        panel2.add(label);
        gridbagconstraints.gridy = 1;
        gridbaglayout.setConstraints(popNo, gridbagconstraints);
        panel2.add(popNo);
        gridbagconstraints.insets = new Insets(2, 2, 2, 2);
        gridbagconstraints.gridx = 0;
        gridbagconstraints.gridy = 3;
        gridbaglayout.setConstraints(panel2, gridbagconstraints);
        panel.add(panel2);
        Panel panel3 = new Panel();
        panel3.add(crossover);
        crossover.setValue(crossoverProb);
        panel3.add(mutation);
        mutation.setValue(mutationProb);
        gridbagconstraints.gridx = 0;
        gridbagconstraints.gridy = 4;
        gridbaglayout.setConstraints(panel3, gridbagconstraints);
        panel.add(panel3);
        panel3 = new Panel();
        panel3.add(Elitism);
        Elitism.addItemListener(this);
        Elitism.setState(true);
        panel3.add(Minimum);
        Minimum.addItemListener(this);
        Minimum.setState(true);
        gridbagconstraints.gridx = 0;
        gridbagconstraints.gridy = 5;
        gridbaglayout.setConstraints(panel3, gridbagconstraints);
        panel.add(panel3);
        Panel panel4 = new Panel();
        panel4.add(Start);
        Start.addActionListener(this);
        panel4.add(Step);
        Step.addActionListener(this);
        panel4.add(Stop);
        Stop.addActionListener(this);
        panel4.add(Continue);
        Continue.addActionListener(this);
        panel4.add(Reset);
        Reset.addActionListener(this);
        gridbagconstraints.gridx = 0;
        gridbagconstraints.gridy = 1;
        gridbaglayout.setConstraints(panel4, gridbagconstraints);
        panel.add(panel4);
        gridbagconstraints.gridx = 0;
        gridbagconstraints.gridy = 0;
        gridbaglayout.setConstraints(bestCoo, gridbagconstraints);
        panel.add(bestCoo);
        add(panel);
        Graph.initPaint();
        resetGraph();
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
        if(s.equals(Change.getLabel()))
            resetGraph();
        if(s.equals(Continue.getLabel()))
            actionContinue();
    }

    public void keyPressed(KeyEvent keyevent)
    {
        if(keyevent.getKeyCode() == 10)
            resetGraph();
    }

    public void keyReleased(KeyEvent keyevent)
    {
    }

    public void keyTyped(KeyEvent keyevent)
    {
    }

    public void itemStateChanged(ItemEvent itemevent)
    {
        population.elitism = Elitism.getState();
        Graph.g3d.lookingForMaximum = !Minimum.getState();
    }

    protected void resetGraph()
    {
        double d = -10D;
        double d1 = 10D;
        double d2 = -10D;
        double d3 = 10D;
        Function3D.parseFunction(expressionText.getText());
        try
        {
            d = (new Double(xmin.getText())).doubleValue();
            d1 = (new Double(xmax.getText())).doubleValue();
            d2 = (new Double(ymin.getText())).doubleValue();
            d3 = (new Double(ymax.getText())).doubleValue();
        }
        catch(NumberFormatException _ex)
        {
            d = -10D;
            d1 = 10D;
            d2 = -10D;
            d3 = 10D;
            Function3D.wasError = true;
        }
        Graph.g3d.setScale(d, d1, d2, d3);
        Graph.resetAll();
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

    protected void actionContinue()
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

    protected void actionStop()
    {
        if(algThread != null)
        {
            algThread.stop();
            algThread = null;
        }
        Graph.paint();
    }

    protected void changes()
    {
        if(crossover.changed())
            crossoverProb = crossover.getValue();
        if(mutation.changed())
            mutationProb = mutation.getValue();
    }

    protected void actionStep()
    {
        changes();
        population.newGeneration(crossoverProb, (double)mutationProb / 10D);
        Graph.paint();
        bestCoo.setText(Graph.theBest);
    }

    public void run()
    {
        Graph.setPopulation(population);
        Graph.paint();
        try
        {
            Thread.sleep(0L);
        }
        catch(InterruptedException _ex) { }
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

    public static void main(String args[])
    {
        GA3D ga3d = new GA3D();
        new GAApplication("Genetic Algorithm in 3D", ga3d, 390, 630);
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
        return "Title: Genetic algorithm in 3D function \nAuthor: Marek Obitko, obitko@email.cz \nApplet shows GA in 3D function and allows changes of the function.";
    }

    public GA3D()
    {
        Graph = new Graph3DCanvas(new Graph3D());
        Start = new Button(" Start ");
        Step = new Button(" Step ");
        Stop = new Button(" Stop ");
        Reset = new Button(" Reset ");
        Continue = new Button(" Continue ");
        Change = new Button("Change");
        Elitism = new Checkbox("Elitism");
        Minimum = new Checkbox("Minimum");
        xmin = new TextField("-10", 4);
        xmax = new TextField("10", 4);
        ymin = new TextField("-10", 4);
        ymax = new TextField("10", 4);
        popNo = new TextField("0", 8);
        bestCoo = new Label("Best [X,Y,Z]=[???????????; ???????????; ???????????]");
        crossover = new ShifterLabel("Crossover 1/100", 120, 10, 10, 100D);
        mutation = new ShifterLabel("Mutation 1/1000", 200, 10, 10, 200D);
        crossoverProb = 80;
        mutationProb = 5;
        expressionText = new TextField("abs(x*x+y*y)", 35);
    }

    private final int STEP_PAUSE = 0;
    private final int POP_SIZE = 16;
    Thread algThread;
    Population population;
    Graph3DCanvas Graph;
    Button Start;
    Button Step;
    Button Stop;
    Button Reset;
    Button Continue;
    Button Change;
    Checkbox Elitism;
    Checkbox Minimum;
    TextField xmin;
    TextField xmax;
    TextField ymin;
    TextField ymax;
    TextField popNo;
    Label bestCoo;
    ShifterLabel crossover;
    ShifterLabel mutation;
    protected int crossoverProb;
    protected int mutationProb;
    TextField expressionText;
}
