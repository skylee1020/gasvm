package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 8:56:02
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.Chromosome.java

import java.awt.Graphics;
import java.io.PrintStream;

public abstract class Chromosome
    implements Cloneable
{

    public abstract void countFitness();

    public abstract Chromosome[] crossover(Chromosome chromosome, double d);

    public Chromosome[] crossover(Chromosome chromosome, double crossover_rate, int i)
    {
        if(i == 0)
            return crossover(chromosome, crossover_rate);
        else
            return null;
    }

    public abstract void mute(double d);

    public void mute(double d, int i)
    {
        if(i == 0)
            mute(d);
    }

    public abstract void randomize();

    /**
     * Add sky1020 2005/11/16
     * So that we can distinguish the randomization logic easily.
     * Up to this point, GASVMChromosome needs to override this logic.
     */
    public void init_randomize(boolean selectionInitRandom) {
        randomize();
    }

    public abstract Chromosome copy();

    public void paint(Graphics g, int i, int j)
    {
    }

    public void paintEmpty(Graphics g, int i, int j)
    {
    }

    public void printSelf()
    {
        System.out.println("Abstract chromosome");
    }

    public String getContent() {
        return "not yet implemented";
    }

    public Chromosome()
    {
    }

    public int lastCrossoverPoint;
    public int fitness;

    public double getFitnessToSort() {
        return fitness;
    }
    
    public static final int BASIC_CROSSOVER = 0;
    public static final int BASIC_MUTATION = 0;
}
