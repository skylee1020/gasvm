/*
#Copyright (c) 2018 <Kyoung-jae Kim, Kichun Lee, and Hyunchul Ahn>
#
#All rights reserved under BSD License.
#
#Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
#
#
# - Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
#
# - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
#
# - Neither the name of the Samsung Electronics Co., Ltd nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
#
#
#THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package gasvm;

import ga.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.StringTokenizer;


public class LearningApp extends Frame  implements ActionListener, Runnable {
    private static final int BGCOLOR = Color.WHITE.getRGB();
    private static final long Generation_Pause = 200L;

    private Panel centerPanel;
    private Panel southPanel;
    private static final String EXIT = "Exit";
    private TextField popNo;
    private TextField bestChromosome;
    private TextField bestFit;
    private TextField bestVal;
    private TextField generateGene;
    private TextField filePrefix;
    private TextArea chromContents;

    /**
     * population setting
     */
    //private int population_size = 16;
    private int population_size = LearningProperties.population_size;
    private boolean elitism = true;
    private double cross_over_rate = LearningProperties.crossover_percent;
    private int cross_over_type = 0;
    private double mutation_rate = LearningProperties.mutataion_percent;;
    private int mutation_type = 0;

    private int seperator = 0;
    private int s_code=0;
    private double best_valid =0;

    //private FileOutputStream logfile_os;
    private PrintWriter logfile_pw;
    private PrintWriter logfile_pw_svm_specific;
    private int unitGeneration = LearningProperties.generation;


    public void initGA()
    {
        //NumberChromosome numberchromosome = new NumberChromosome(32);
        GASVMChromosome gasvmChromosome = null;
        try {
            gasvmChromosome = new GASVMChromosome(LearningProperties.train_file, LearningProperties.test_file , LearningProperties.validation_file,
                            LearningProperties.svm_selection, LearningProperties.feature_selection, LearningProperties.instance_selection ,
                    LearningProperties.svm_kernel_type,
                    LearningProperties.c_min, LearningProperties.c_max, LearningProperties.c_gene_length,
                    LearningProperties.d_min, LearningProperties.d_max, LearningProperties.d_gene_length,
                    LearningProperties.g_min, LearningProperties.g_max, LearningProperties.g_gene_length,
                    LearningProperties.c_default, LearningProperties.d_default, LearningProperties.g_default);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            System.exit(1);
        }
        //gasvmChromosome.randomize();
        //gasvmChromosome.countFitness();
        /**
         * Population gets the property value of random_initialize_selection
         */
        population = new Population(gasvmChromosome, population_size, cross_over_type, mutation_type, false, LearningProperties.random_initialize_selection,
               LearningProperties.gasvm_elitism, LearningProperties.elitism_first_mutate, LearningProperties.elitism_second_mutate_cross , LearningProperties.gasvm_cross_elitism);
        population.elitism = elitism;
        population.popNo = popNo;
        //System.out.println("[gasvm] Learning.initGA population.randomize starts");
        //population.randomize();
        //System.out.println("[gasvm] Learning.initGA population.randomize ends");
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

        //System.out.println("[] actionPerformed s:" + s);
        if(s.equals(Start.getLabel()))
            actionStart();
        if(s.equals(Stop.getLabel()))
            actionStop();
        //if(s.equals(Reset.getLabel()))
        //    actionReset();
        if(s.equals(Step.getLabel()))
            actionStep();
        if (s.equals(Generate.getLabel())) {
            actionGenerate();
        }

        if(s.equals(EXIT)) {
            System.exit(0);
        }
    }

    private void actionGenerate() {
        String geneToGenerate = this.generateGene.getText();
        String filePrefix = this.filePrefix.getText();
        //System.out.println("geneToGenerate:" + geneToGenerate);
        //System.out.println("filePrefix:" + filePrefix);
        //geneToGenerate = "c:83.478983,d:1.904762,g:189.301075;No feature_selection;No instance_selection"
        //geneToGenerate = "c:83.478983,d:1.904762,g:189.301075;0,1,2;2,23,49,120"
        //geneToGenerate = "No instance selection;0,1,2;2,23,49,120"
        double _c_param, _g_param;
        int _d_param;
        int[] _exclude_features, _exclude_instances;
        if ( geneToGenerate.indexOf("No svm_selection") >= 0 ) {
            //off
            _c_param = LearningProperties.c_default;
            _d_param = LearningProperties.d_default;
            _g_param = LearningProperties.g_default;
        } else {
            //if svm selection turns on
            //System.out.println("geneToGenerate:" + geneToGenerate);
            //System.out.println("geneToGenerate.indexOf(\";\"):" + geneToGenerate.indexOf(";"));
            String _svm_paramPart = geneToGenerate.substring(0,geneToGenerate.indexOf(";") );
            String _cPart = _svm_paramPart.substring(2, _svm_paramPart.indexOf(",",2));
            String _dPart = _svm_paramPart.substring(_svm_paramPart.indexOf("d:") + 2, _svm_paramPart.indexOf(",",2 + _svm_paramPart.indexOf("d:")));
            String _gPart = _svm_paramPart.substring(_svm_paramPart.indexOf("g:") + 2);

            if ( LearningProperties.debug_GenerateGene ) {
                System.out.println("_svm_paramPart:" + _svm_paramPart);
                System.out.println("_cPart:" + _cPart);
                System.out.println("_pPart:" + _dPart);
                System.out.println("_gPart:" + _gPart);
            }

            _c_param = Double.valueOf(_cPart).doubleValue();
            _d_param = Integer.valueOf(_dPart).intValue();
            _g_param = Double.valueOf(_gPart).doubleValue();

            if ( LearningProperties.debug_GenerateGene ) {
                System.out.println("_c_param:" + _c_param);
                System.out.println("_d_param:" + _d_param);
                System.out.println("_g_param:" + _g_param);
            }
        }

        if ( geneToGenerate.indexOf("No feature_selection") >= 0) {
            _exclude_features = new int[0];
        } else {
            //c:133.861193,d:1.301587,g:106.238514;0,3;No instance_selection
            int firstMark = geneToGenerate.indexOf(";");
            int secondMark = geneToGenerate.indexOf(";",firstMark + 1);
            if ( LearningProperties.debug_GenerateGene ) {
                System.out.println("firstMark:" + firstMark);
                System.out.println("secondMark:" + secondMark);
            }

            String _feature_excludePart = geneToGenerate.substring(firstMark + 1, secondMark);
            StringTokenizer st = new StringTokenizer(_feature_excludePart,",");
            //System.out.println("token count:" + st.countTokens());
            _exclude_features = new int[st.countTokens()];
            int pos = 0;
            while (st.hasMoreTokens()) {
                //System.out.println(st.nextToken());
                //System.out.println("exclude feature index:" + st.nextToken());
                _exclude_features[pos] = Integer.parseInt(st.nextToken());
                //System.out.println("exclude feature index:" + _exclude_features[pos]);
                pos++;
            }
            //if feature selection turns on
        }

        if ( geneToGenerate.indexOf("No instance_selection") >= 0) {
            _exclude_instances = new int[0];
        } else {
            //if instance selection turns on
            int lastMark = geneToGenerate.lastIndexOf(";");
            String _instance_excludePart = geneToGenerate.substring(lastMark +1);
            if ( LearningProperties.debug_GenerateGene ) {
                System.out.println("_instance_excludePart:" + _instance_excludePart);
            }
            StringTokenizer st = new StringTokenizer(_instance_excludePart,",");
            if ( LearningProperties.debug_GenerateGene ) {
                System.out.println("token count:" + st.countTokens());
            }
            _exclude_instances = new int[st.countTokens()];
            int pos = 0;
            while (st.hasMoreTokens()) {
                //System.out.println(st.nextToken());
                //System.out.println("exclude feature index:" + st.nextToken());
                _exclude_instances[pos] = Integer.parseInt(st.nextToken());
                if ( LearningProperties.debug_GenerateGene ) {
                    System.out.println("exclude instance index:" + _exclude_instances[pos]);
                }
                pos++;
            }
        }
        LearningUnit unit = null;
        try {
            unit = new LearningUnit(LearningProperties.train_file, LearningProperties.test_file, LearningProperties.validation_file);
            unit.getModelPrediction(LearningProperties.svm_kernel_type, _d_param , _g_param, _c_param, true, filePrefix, _exclude_features, _exclude_instances);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
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

    //protected void actionReset()
    //{
    //    population.randomize();
        //Graph.paint();
    //}

    protected void actionStop()
    {
        if(algThread != null)
        {
            //algThread.stop();
            isStop = true;
            algThread = null;
        }
        //Graph.paint();
    }

    protected void actionStep()
    {
        population.newGeneration(cross_over_rate, mutation_rate);
        //Graph.paint();
        

        /**
         * write best
         */
        Chromosome[] pops = population.pop;
        if ( pops[0] != null ) {
            bestChromosome.setText( pops[0].getContent() );
            bestFit.setText( ""+ ((GASVMChromosome)pops[0]).fitness_l );
        }

        /**
         * write all population
         */
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < pops.length; i++) {
            Chromosome pop = pops[i];
            GASVMChromosome gasvmPop = (GASVMChromosome)pop;

            if (best_valid<gasvmPop.fitnessValidation_l) {
                best_valid=gasvmPop.fitnessValidation_l;
            }

            if ( pop != null) {
                //sb.append("" + i + "th\t" + pop.getContent() + "\t" + gasvmPop.fitness_l + "\t" + gasvmPop.fitnessTrain_l + "\t" + gasvmPop.fitnessValidation_l +"\n" );
                //sb.append("" + population.population + "-" + "" + i + "th\t"  + gasvmPop.fitnessTrain_l +"\t" + gasvmPop.fitness_l + "\t" + gasvmPop.fitnessValidation_l  + "\t" + pop.getContent() +"\n");
                sb.append(getGeneInfo(i, gasvmPop, pop) +"\n");
                //java.lang.Math.round(value * 1000000) / 1000000d
            }
        }
        bestVal.setText(""+best_valid);

        /**
         * write information of population to logging files
         */
        LoggingGene(pops);

        chromContents.setText(sb.toString());
    }

    private void LoggingGene(Chromosome[] pops) {
        if ( this.logfile_pw != null ) {
            //logfile_pw.println("--generation " + population.population + "--");
            for (int i = 0; i < pops.length; i++) {
                Chromosome pop = pops[i];
                GASVMChromosome gasvmPop = (GASVMChromosome)pop;

                ++seperator;
                if (seperator>60000) {
                    seperator=0;
                    ++s_code;
                    initGASVMParam();
                }

                if ( pop != null) {
                    //logfile_pw.println("" + i + "th\t" + pop.getContent() + "\t" + gasvmPop.fitness_l + "\t" + gasvmPop.fitnessTrain_l + "\t" + gasvmPop.fitnessValidation_l +"\n" );
                    //logfile_pw.println("" + population.population + "-" + "" + i + "th\t"  + gasvmPop.fitnessTrain_l +"\t" + gasvmPop.fitness_l + "\t" + gasvmPop.fitnessValidation_l  + "\t" + pop.getContent() );
                    logfile_pw.println(getGeneInfo(i, gasvmPop, pop) );
                    //sb.append("" + population.population + "-" + "" + i + "th\t"  + java.lang.Math.round(gasvmPop.fitnessTrain_l * 1000000) / 1000000d +"\t" + java.lang.Math.round(gasvmPop.fitness_l * 1000000) / 1000000d  + "\t" +  java.lang.Math.round(gasvmPop.fitnessValidation_l * 1000000) / 1000000d   + "\t" + pop.getContent() +"\n");
                    if ( logfile_pw_svm_specific != null ) {
                        logfile_pw_svm_specific.println(getGeneInfoForSvm(i, gasvmPop, pop) );
                    }
                }
            }
            logfile_pw.flush();
            if ( logfile_pw_svm_specific != null ) {
                logfile_pw_svm_specific.flush();
            }
        }
    }

    private String getGeneInfo(int i, GASVMChromosome gasvmPop, Chromosome pop) {
        return "" + population.population + "-" + "" + i + "th\t"  + round(gasvmPop.fitnessTrain_l, LearningProperties.roundDigitCount ) +"\t" + round(gasvmPop.fitness_l, LearningProperties.roundDigitCount ) + "\t" +  round(gasvmPop.fitnessValidation_l, LearningProperties.roundDigitCount ) + "\t" + pop.getContent();
    }

    private String getGeneInfoForSvm(int i, GASVMChromosome gasvmPop, Chromosome pop) {
        StringBuffer sb = new StringBuffer(512);
        sb.append("" + population.population + "-" + "" + i + "th\t"  + round(gasvmPop.fitnessTrain_l, LearningProperties.roundDigitCount ) +"\t" + round(gasvmPop.fitness_l, LearningProperties.roundDigitCount ) + "\t" +  round(gasvmPop.fitnessValidation_l, LearningProperties.roundDigitCount ) + "\t");
        sb.append("t:" + LearningProperties.svm_kernel_type + ",");
        /**
         * error in logging  @_@ when svm_selection is off 
         */
        if ( LearningProperties.svm_selection ) {
            switch(LearningProperties.svm_kernel_type) {
                case 0 :
                    sb.append("c:" + gasvmPop.get_C());
                    gasvmPop.getContentLastPart(sb);
                    break;
                case 1 :
                    sb.append("c:" + gasvmPop.get_C() + ",d:" + gasvmPop.get_D());
                    gasvmPop.getContentLastPart(sb);
                    break;
                case 2 :
                case 3 :
                    sb.append("c:" + gasvmPop.get_C() + ",g:" + gasvmPop.get_G());
                    gasvmPop.getContentLastPart(sb);
                    break;
                case 4 :
                default :
                    sb.append( pop.getContent());
                    break;
            }
        } else {
            sb.append( pop.getContent());
        }

        return sb.toString();
    }

    public static String round(double v, int length) {
        if ( length < 1 ) {
            return new Double(java.lang.Math.round(v)).toString();
        }
        int factorValue = 1;
        for ( int i = 0 ; i < length ; i++ ) {
            factorValue = factorValue * 10;
        }
        double newValue = java.lang.Math.round(v * factorValue ) / (double) factorValue ;

        String _val = Double.toString(newValue);
        int pointIndex = -1;
        if ( ( pointIndex = _val.indexOf(".") ) > 0 ) {
            String remainingStr = _val.substring(pointIndex + 1 );
            int diffCount = (length - remainingStr.length());
            for ( int i = 0 ; i < diffCount ; i++ ) {
                _val = _val + "0";
            }

            //return Double.valueOf(_val).doubleValue();
            return _val;
        } else {
            return _val;
        }
    }

    public void run()
    {
        //System.out.println("[] ga.BasicGA started");
        //Graph.setPopulation(population);
        //Graph.paint();
        int generationCount = 0;
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
            if ( generationCount >= this.unitGeneration) {
                if ( LearningProperties.debug) {
                    System.out.println("[gasvm] LearningApp unit generation(" + unitGeneration + ") ends. So stop this thread");
                }
                break;
            }

            actionStep();
            generationCount++;
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
                new LearningApp("GASVM application", 700, 550);
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

    public LearningApp(String title, int width, int height) {
        super(title);
        this.addWindowListener( new myWindowListener());

        initUI();
        //System.out.println("[] centerPanel:" + centerPanel);
        initGASVMParam();
        initGA();

        setSize(width, height);
        show();

        setBGColor();
        //Graph.initPaint();
    }

    private void initGASVMParam() {
        try {
            FileOutputStream logfile_os = new FileOutputStream(LearningProperties.generation_logfileDir + LearningProperties.generation_logfile + s_code + ".txt", true);
            logfile_pw = new PrintWriter(logfile_os, true);

            FileOutputStream logfile_os_svm_specific = new FileOutputStream(LearningProperties.generation_logfileDir + LearningProperties.generation_logfile_svm_specific + s_code + ".txt", true);
            logfile_pw_svm_specific = new PrintWriter(logfile_os_svm_specific, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
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

        Label fitlb = new Label("Best Test Result:");
        bestFit = new TextField("0", 8);
        gridbagconstraints.gridx = 0;
        gridbagconstraints.gridy = 2;
        gridbagconstraints.anchor = GridBagConstraints.EAST;
        southPanel.add(fitlb, gridbagconstraints);

        gridbagconstraints.gridx = 1;
        gridbagconstraints.gridy = 2;
        gridbagconstraints.anchor = GridBagConstraints.WEST;
        southPanel.add(bestFit, gridbagconstraints);

        Label fitvlb = new Label("Best Valid. Result:");
        bestVal = new TextField("0", 8);
        gridbagconstraints.gridx = 0;
        gridbagconstraints.gridy = 3;
        gridbagconstraints.anchor = GridBagConstraints.EAST;
        southPanel.add(fitvlb, gridbagconstraints);

        gridbagconstraints.gridx = 1;
        gridbagconstraints.gridy = 3;
        gridbagconstraints.anchor = GridBagConstraints.WEST;
        southPanel.add(bestVal, gridbagconstraints);


//        Label helpSeparator = new Label("-----------------------------------------------------------------------------------------------");
//        gridbagconstraints.gridx = 0;
//        gridbagconstraints.gridy = 3;
//        gridbagconstraints.gridwidth = 2;
//        //gridbagconstraints.anchor = GridBagConstraints.EAST;
//        southPanel.add(helpSeparator, gridbagconstraints);

        Label lbGenerateGene = new Label("Generate Gene:");
        gridbagconstraints.gridx = 0;
        gridbagconstraints.gridy = 5;
        gridbagconstraints.anchor = GridBagConstraints.EAST;
        southPanel.add(lbGenerateGene, gridbagconstraints);
        generateGene = new TextField("0", 45);
        gridbagconstraints.gridx = 1;
        gridbagconstraints.gridy = 5;
        gridbagconstraints.anchor = GridBagConstraints.WEST;
        southPanel.add(generateGene, gridbagconstraints);

        Label lbFilePrefix = new Label("File Prefix:");
        gridbagconstraints.gridx = 0;
        gridbagconstraints.gridy = 6;
        gridbagconstraints.anchor = GridBagConstraints.EAST;
        southPanel.add(lbFilePrefix, gridbagconstraints);
        filePrefix = new TextField("0",14);
        gridbagconstraints.gridx = 1;
        gridbagconstraints.gridy = 6;
        gridbagconstraints.anchor = GridBagConstraints.WEST;
        southPanel.add(filePrefix, gridbagconstraints);

        add("South", southPanel);
    }

    private void initUI_center() {
        /**
         * Center
         */
        //centerPanel = new Panel( new FlowLayout() );
        centerPanel = new Panel(new BorderLayout());
        //Graph = new GraphCanvas();
        //centerPanel.add(Graph, "North");

        chromContents = new TextArea();

        chromContents.setEditable(false);
        //chromContents.setSize( (int) (this.getSize().width ), (int) (this.getSize().height * 0.6f));
        centerPanel.add(chromContents, "Center");

        Panel southPanel = new Panel(new FlowLayout());
        Start = new Button(" Start ");
        Step = new Button(" Step ");
        Stop = new Button(" Stop ");
        //Reset = new Button(" Reset ");
        Generate = new Button("Generate");

        southPanel.add(Start);
        Start.addActionListener(this);

        southPanel.add(Step);
        Step.addActionListener(this);

        southPanel.add(Stop);
        Stop.addActionListener(this);

        //southPanel.add(Reset);
        //Reset.addActionListener(this);

        southPanel.add(Generate);
        Generate.addActionListener(this);

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

    boolean isStop;
    Population population;
    //GraphCanvas Graph;
    Button Start;
    Button Step;
    Button Stop;
    //Button Reset;
    Button Generate;
}
