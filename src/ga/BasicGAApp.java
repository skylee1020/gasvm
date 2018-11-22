package ga;

import java.awt.*;
import java.awt.event.*;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 2005. 1. 18.
 * Time: ¿ÀÈÄ 1:34:54
 * To change this template use Options | File Templates.
 */
public class BasicGAApp extends Frame  implements ActionListener, Runnable {
    private static final int BGCOLOR = Color.WHITE.getRGB();
    private static final long Generation_Pause = 200L;

    private Panel centerPanel;
    private Panel southPanel;
    private static final String EXIT = "Exit";
    private TextField popNo;
    private TextField bestChromosome;
    private TextField bestFit;
    private TextArea chromContents;

    /**
     * population setting
     */
    private int population_size = 16;
    private boolean elitism = true;
    private double cross_over_rate = 60D;
    private int cross_over_type = 0;
    private double mutation_rate = 5D;
    private int mutation_type = 0;



    public void initGA()
    {
        NumberChromosome numberchromosome = new NumberChromosome(32);
        numberchromosome.randomize();
        numberchromosome.countFitness();
        population = new Population(numberchromosome, population_size, cross_over_type, mutation_type);
        population.elitism = elitism;
        population.popNo = popNo;
        population.randomize();
    }

    protected void setBGColor()
    {
        try
        {
            Color color = new Color(BGCOLOR);
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

        //System.out.println("[sky1020] actionPerformed s:" + s);
        if(s.equals(Start.getLabel()))
            actionStart();
        if(s.equals(Stop.getLabel()))
            actionStop();
        if(s.equals(Reset.getLabel()))
            actionReset();
        if(s.equals(Step.getLabel()))
            actionStep();

        if(s.equals(EXIT)) {
            System.exit(0);
        }

    }

    protected void actionStart()
    {
        if(algThread != null)
        {
            return;
        } else
        {
            isStop = false;
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
            //algThread.stop();
            isStop = true;
            algThread = null;
        }
        Graph.paint();
    }

    protected void actionStep()
    {
        population.newGeneration(cross_over_rate, mutation_rate);
        Graph.paint();
        

        /**
         * write best
         */
        Chromosome[] pops = population.pop;
        if ( pops[0] != null ) {
            bestChromosome.setText( pops[0].getContent() );
            bestFit.setText( ""+ pops[0].fitness );
        }

        /**
         * write all population
         */
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < pops.length; i++) {
            Chromosome pop = pops[i];
            if ( pop != null) {
                sb.append("" + i + "th\t" + pop.getContent() + "\t" + pop.fitness + "\n" );
            }
        }
        chromContents.setText(sb.toString());
    }

    public void run()
    {
        //System.out.println("[sky1020] ga.BasicGA started");
        Graph.setPopulation(population);
        Graph.paint();
        try
        {
            Thread.sleep(Generation_Pause);
        }
        catch(InterruptedException _ex) { }
        do
        {
            if ( isStop ) {
                break;
            }

            actionStep();
            try
            {
                Thread.sleep(Generation_Pause);
            }
            catch(InterruptedException _ex) { }
        } while(true);
    }

    public void destroy()
    {
        if(algThread != null)
        {
            isStop = true;
            algThread = null;
        }
    }

    protected void finalize()
        throws Throwable
    {
        if(algThread != null)
        {
            isStop = true;
            algThread = null;
        }
        super.finalize();
    }

    public static void main(String args[]) {
        //BasicGAApp app =
                new BasicGAApp("Basic Genetic Algorithm", 700, 500);
    }

    public class myWindowListener implements WindowListener {
        public void windowOpened(WindowEvent e) {
        }

        public void windowClosing(WindowEvent e) {
            //System.out.println("windowClosing");
            System.exit(0);
        }

        public void windowClosed(WindowEvent e) {
        }

        public void windowIconified(WindowEvent e) {
        }

        public void windowDeiconified(WindowEvent e) {
        }

        public void windowActivated(WindowEvent e) {
        }

        public void windowDeactivated(WindowEvent e) {
        }

    }

    public BasicGAApp(String title, int width, int height) {
        super(title);
        this.addWindowListener( new myWindowListener());

        initUI();
        //System.out.println("[sky1020] centerPanel:" + centerPanel);
        initGA();

        setSize(width, height);
        show();

        setBGColor();
        Graph.initPaint();
    }

    private void initUI() {
        initUI_menu();

        initUI_center();

        initUI_south();
    }

    private void initUI_south() {

        GridBagLayout gridbaglayout = new GridBagLayout();
        GridBagConstraints gridbagconstraints = new GridBagConstraints();
        gridbagconstraints.insets = new Insets(2, 2, 2, 2);
        southPanel = new Panel(gridbaglayout);

        Label gen = new Label("Generation:");
        popNo = new TextField("0", 8);
        gridbagconstraints.gridx = 0;
        gridbagconstraints.gridy = 0;
        gridbagconstraints.anchor = GridBagConstraints.EAST;
        southPanel.add(gen, gridbagconstraints);

        gridbagconstraints.gridx = 1;
        gridbagconstraints.gridy = 0;
        gridbagconstraints.anchor = GridBagConstraints.WEST;
        southPanel.add(popNo, gridbagconstraints);

        Label bestlb = new Label("Best Chrom.:");
        bestChromosome = new TextField("0", 45);
        gridbagconstraints.gridx = 0;
        gridbagconstraints.gridy = 1;
        gridbagconstraints.anchor = GridBagConstraints.EAST;
        southPanel.add(bestlb, gridbagconstraints);

        gridbagconstraints.gridx = 1;
        gridbagconstraints.gridy = 1;
        gridbagconstraints.anchor = GridBagConstraints.WEST;
        southPanel.add(bestChromosome, gridbagconstraints);

        Label fitlb = new Label("Best Fitness:");
        bestFit = new TextField("0", 8);
        gridbagconstraints.gridx = 0;
        gridbagconstraints.gridy = 2;
        gridbagconstraints.anchor = GridBagConstraints.EAST;
        southPanel.add(fitlb, gridbagconstraints);

        gridbagconstraints.gridx = 1;
        gridbagconstraints.gridy = 2;
        gridbagconstraints.anchor = GridBagConstraints.WEST;
        southPanel.add(bestFit, gridbagconstraints);

        add("South", southPanel);
    }

    private void initUI_center() {
        /**
         * Center
         */
        //centerPanel = new Panel( new FlowLayout() );
        centerPanel = new Panel();
        Graph = new GraphCanvas();
        centerPanel.add(Graph, "North");

        chromContents = new TextArea();
        chromContents.setEditable(false);
        centerPanel.add(chromContents, "Center");

        Panel southPanel = new Panel(new FlowLayout());
        Start = new Button(" Start ");
        Step = new Button(" Step ");
        Stop = new Button(" Stop ");
        Reset = new Button(" Reset ");
        southPanel.add(Start);
        Start.addActionListener(this);
        southPanel.add(Step);
        Step.addActionListener(this);
        southPanel.add(Stop);
        Stop.addActionListener(this);
        southPanel.add(Reset);
        Reset.addActionListener(this);
        centerPanel.add(southPanel, "South");

        add("Center", centerPanel);
    }

    private void initUI_menu() {
        /**
         * menue
         */
        MenuBar menubar = new MenuBar();
        Menu menu = new Menu("Application", true);
        menubar.add(menu);
        menu.add(EXIT);
        menu.addActionListener(this);
        setMenuBar(menubar);
    }

    //private final int STEP_PAUSE = 200;

    Thread algThread;
    /**
     * Add sky1020 2005/01/18
     */
    boolean isStop;
    Population population;
    GraphCanvas Graph;
    Button Start;
    Button Step;
    Button Stop;
    Button Reset;
}
