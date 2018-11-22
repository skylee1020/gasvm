package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 8:49:23
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.Population.java

import java.awt.TextComponent;
import java.awt.TextField;
import java.io.PrintStream;

public class Population
{
    /**
     * Add sky1020 2005/11/16
     */
    private boolean elitism_first_mutate;
    private boolean elitism_second_mutate_cross;
    private boolean gasvm_cross_elitism;

    /**
     *
     * @param chromosome sample chromosome, used at init(). to be copied
     * @param population_size population size
     */
    public Population(Chromosome chromosome, int population_size)
    {
        this(chromosome, population_size, 0, 0);
    }

    public Population(Chromosome chromosome, int population_size, int _cross_overType , int _mutation_type ) {
        this(chromosome, population_size, _cross_overType, _mutation_type, true);
    }

    public Population(Chromosome chromosome, int population_size, int _cross_overType , int _mutation_type , boolean fitnessInitialized) {
        this(chromosome, population_size, _cross_overType, _mutation_type, fitnessInitialized, true, true, false, false, false);
    }

    /**
     * Add sky1020 2005/11/16
     * @param selectionInitRandom   true if it initializes chromosome when selection (of either feature or instance) is on
     *                              false otherwise
     */
    public Population(Chromosome chromosome, int population_size, int _cross_overType , int _mutation_type , boolean fitnessInitialized, boolean selectionInitRandom,
                      boolean useElitisim, boolean elitism_first_mutate, boolean elitism_second_mutate_cross , boolean gasvm_cross_elitism) {
        /**
         * Change sky1020 2005/11/16
         */
        elitism = useElitisim;
        parentIndex = new int[2];
        crossoverType = _cross_overType;
        mutationType = _mutation_type;
        size = population_size;
        pop = new Chromosome[size];
        sample = chromosome;
        this.elitism_first_mutate = elitism_first_mutate;
        this.elitism_second_mutate_cross = elitism_second_mutate_cross;
        this.gasvm_cross_elitism = gasvm_cross_elitism;
        init(fitnessInitialized, selectionInitRandom);
    }

    /**
     * Change sky1020 2005/11/16
     * Added selectionInitRandom parameter.
     * @param fitnessInitialized
     * @param selectionInitRandom
     */
    public void init(boolean fitnessInitialized, boolean selectionInitRandom)
    {
        population = -1;
        updatePopulation();
        for(int i = 0; i < size; i++) {
            pop[i] = sample.copy();
        }

        //System.out.println("[gasvm] Population.init randomize start");
        /**
         * Change sky1020 2005/11/16
         * randomize -> init_randomize
         * So that we can easily distinguish the randomization procedure
         */
        init_randomize(fitnessInitialized, selectionInitRandom);
        //System.out.println("[gasvm] Population.init randomize end");
        //System.out.println("[gasvm] Population.init countFitnesses start");
        //countFitnesses();
        //System.out.println("[gasvm] Population.init countFitnesses end");
        sort();
        newpop = new Chromosome[size + 2];
        oldchild = new Chromosome[2];
    }

    public void countFitnesses()
    {
        fitnessSum = 0;
        for(int i = 0; i < size; i++)
        {
            //System.out.println("[gasvm] Population.countFitness i:" + i + ", size:" + size);
            pop[i].countFitness();
            fitnessSum += pop[i].fitness;
        }

    }

    public void printSelf()
    {
        for(int i = 0; i < size; i++)
            pop[i].printSelf();

    }

    public void printValues()
    {
        System.out.println();
        for(int i = 0; i < size; i++)
            System.out.print(((NumberChromosome)pop[i]).value() + ",");

        System.out.println();
    }

    public void randomize()
    {
        randomize(true);
    }

    public void randomize(boolean fitnessInitialized) {
        population = -1;
        updatePopulation();
        for(int i = 0; i < size; i++) {
            pop[i].randomize();
        }

        if ( fitnessInitialized ) {
            countFitnesses();
            sort();
        }
    }

    public void init_randomize(boolean fitnessInitialized, boolean selectionInitRandom) {
        population = -1;
        updatePopulation();
        for(int i = 0; i < size; i++) {
            /**
             * Change sky1020 2005/11/16
             */
            pop[i].init_randomize(selectionInitRandom);
            //pop[i].randomize();
        }

        if ( fitnessInitialized ) {
            countFitnesses();
            sort();
        }
    }
    public void sort()
    {
        /**
         * todo sky1020 2004/10/19
         * had better do with 'Quick sort' :->
         */
        for(int i = size - 1; i > 0; i--)
        {
            for(int j = 0; j < i; j++)
                if(pop[j].getFitnessToSort() < pop[j + 1].getFitnessToSort())
                {
                    Chromosome chromosome = pop[j + 1];
                    pop[j + 1] = pop[j];
                    pop[j] = chromosome;
                }

        }

    }

    public void newGeneration()
    {
        newGeneration(80D, 5D);
    }

    public void slowNewGeneration()
    {
        slowNewGeneration(80D, 10D);
    }

    public Chromosome selectParent()
    {
        try
        {
            int i = (int)(Math.random() * (double)fitnessSum);
            int j = 0;
            int k = 0;
            do
            {
                j += pop[k].fitness;
                k++;
            } while(j < i);
            k--;
            parentIndex[parentIdx] = k;
            if(++parentIdx > 1)
                parentIdx = 0;
            return pop[k];
        }
        catch(ArrayIndexOutOfBoundsException _ex)
        {
            return pop[1];
        }
    }

    /**
     *
     * @param crossover_rate crossover rate, 0.0D ~ 100.D
     * @param mutation_rate mutation rate, 0.0D ~ 100.D
     */
    public void newGeneration(double crossover_rate, double mutation_rate)
    {
        int i = 0;
        if(elitism) {
            newpop[0] = pop[0];
            newpop[1] = pop[1];
            /**
             * change sky1020 2005/11/16
             */
            if ( elitism_first_mutate ) {
                newpop[0].mute(mutation_rate, mutationType);
            }
            if ( elitism_second_mutate_cross ) {
                Chromosome[] _newChroms = newpop[0].crossover(newpop[1], crossover_rate, crossoverType);
                _newChroms[1].mute(mutation_rate, mutationType);
                newpop[1] = _newChroms[1];
            }

            i = 2;
        }
        for(; i < size; i += 2)
        {
            parent1 = selectParent();
            /**
             * change sky1020 2005/11/16
             */            
            if ( gasvm_cross_elitism ) {
                parent2 = newpop[0];
            } else {
                parent2 = selectParent();
            }
            child = parent1.crossover(parent2, crossover_rate, crossoverType);
            child[0].mute(mutation_rate, mutationType);
            child[1].mute(mutation_rate, mutationType);
            newpop[i] = child[0];
            newpop[i + 1] = child[1];
        }

        for(int j = 0; j < size; j++)
            pop[j] = newpop[j];

        countFitnesses();
        sort();
        stepNumber = 0;
        updatePopulation();
    }

    public void setCrossoverCanvas(CrossoverCanvas crossovercanvas)
    {
        canvas = crossovercanvas;
    }

    public void makeStep()
    {
        switch(stepNumber)
        {
        default:
            break;

        case 0: // '\0'
            index = 0;
            subStepNumber = 0;
            if(elitism)
                stepNumber = 1;
            else
                stepNumber = 2;
            makeStep();
            return;

        case 1: // '\001'
            if(index > 1)
            {
                stepNumber = 2;
                makeStep();
                return;
            } else
            {
                newpop[index] = pop[index];
                canvas.makeElitism(++index);
                return;
            }

        case 2: // '\002'
            if(index >= size)
            {
                stepNumber = 3;
                makeStep();
                return;
            }
            switch(subStepNumber)
            {
            case 0: // '\0'
                parent1 = selectParent();
                parent2 = selectParent();
                subStepNumber = 1;
                child = parent1.crossover(parent2, 80D);
                oldchild[0] = child[0].copy();
                oldchild[1] = child[1].copy();
                canvas.showParents();
                return;

            case 1: // '\001'
                canvas.showChildren();
                subStepNumber = 2;
                return;

            case 2: // '\002'
                child[0].mute(4D);
                child[1].mute(4D);
                canvas.showMutated();
                subStepNumber = 3;
                return;

            case 3: // '\003'
                newpop[index] = child[0];
                newpop[index + 1] = child[1];
                index += 2;
                canvas.showNewChildren(index);
                subStepNumber = 0;
                return;
            }
            break;

        case 3: // '\003'
            for(int i = 0; i < size; i++)
                pop[i] = newpop[i];

            canvas.removeOldPopulation();
            stepNumber = 4;
            return;

        case 4: // '\004'
            countFitnesses();
            sort();
            canvas.showSort();
            updatePopulation();
            stepNumber = 0;
            return;
        }
    }

    public void slowNewGeneration(double d, double d1)
    {
        int i = 0;
        if(elitism)
        {
            newpop[0] = pop[0];
            newpop[1] = pop[1];
            i = 2;
        }
        for(; i < size; i += 2)
        {
            parent1 = selectParent();
            parent2 = selectParent();
            child = parent1.crossover(parent2, d);
            child[0].mute(d1);
            child[1].mute(d1);
            newpop[i] = child[0];
            newpop[i + 1] = child[1];
        }

        for(int j = 0; j < size; j++)
            pop[j] = newpop[j];

        countFitnesses();
        sort();
        updatePopulation();
    }

    public void updatePopulation()
    {
        population++;
        if(popNo == null)
        {
            return;
        } else
        {
            popNo.setText(String.valueOf(population));
            return;
        }
    }

    public int size;
    public Chromosome sample;
    public Chromosome pop[];
    public int fitnessSum;
    public boolean elitism;
    public int population;
    public TextField popNo;
    public Chromosome parent1;
    public Chromosome parent2;
    public Chromosome child[];
    public Chromosome oldchild[];
    public Chromosome newpop[];
    public int parentIndex[];
    protected int parentIdx;
    public int stepNumber;
    protected int subStepNumber;
    protected int index;
    protected CrossoverCanvas canvas;
    public int crossoverType;
    public int mutationType;
}
