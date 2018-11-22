package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 9:01:53
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.StringChromosome.java

import java.io.PrintStream;

public class StringChromosome extends Chromosome
{

    public StringChromosome(int i)
    {
        length = i;
        bit = new int[length];
    }

    public void countFitness()
    {
        fitness = 0;
    }

    public void printSelf()
    {
        System.out.print("String: ");
        for(int i = 0; i < length; i++)
            System.out.print(bit[i]);

        System.out.println();
    }

    public Chromosome copy()
    {
        StringChromosome stringchromosome = new StringChromosome(length);
        for(int i = 0; i < length; i++)
            stringchromosome.bit[i] = bit[i];

        stringchromosome.fitness = fitness;
        return stringchromosome;
    }

    public Chromosome[] crossover(Chromosome chromosome, double d)
    {
        StringChromosome astringchromosome[] = new StringChromosome[2];
        if(d < Math.random() * 100D)
        {
            lastCrossoverPoint = -1;
            astringchromosome[0] = (StringChromosome)copy();
            astringchromosome[1] = (StringChromosome)((StringChromosome)chromosome).copy();
        } else
        {
            astringchromosome[0] = new StringChromosome(length);
            astringchromosome[1] = new StringChromosome(length);
            int i = (int)(Math.random() * (double)length);
            lastCrossoverPoint = i;
            int j;
            for(j = 0; j < i; j++)
            {
                astringchromosome[0].bit[j] = bit[j];
                astringchromosome[1].bit[j] = ((StringChromosome)chromosome).bit[j];
            }

            for(; j < length; j++)
            {
                astringchromosome[1].bit[j] = bit[j];
                astringchromosome[0].bit[j] = ((StringChromosome)chromosome).bit[j];
            }

        }
        return astringchromosome;
    }

    public void mute(double d)
    {
        for(int i = 0; i < length; i++)
            if(Math.random() * 100D < d)
                if(bit[i] == 1)
                    bit[i] = 0;
                else
                    bit[i] = 1;

    }

    public void randomize()
    {
        for(int i = 0; i < length; i++)
            if(Math.random() < 0.5D)
                bit[i] = 0;
            else
                bit[i] = 1;

    }

    public int bit[];
    public int length;
}
