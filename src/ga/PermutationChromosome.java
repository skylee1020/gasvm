package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 9:02:09
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.PermutationChromosome.java

import java.io.PrintStream;

public class PermutationChromosome extends Chromosome
{

    public PermutationChromosome(int i)
    {
        length = i;
        perm = new int[length];
    }

    public void countFitness()
    {
        fitness = 0;
    }

    public void printSelf()
    {
        System.out.print("Permutation: ");
        for(int i = 0; i < length; i++)
            System.out.print(perm[i] + ",");

        System.out.println();
    }

    public Chromosome copy()
    {
        PermutationChromosome permutationchromosome = new PermutationChromosome(length);
        for(int i = 0; i < length; i++)
            permutationchromosome.perm[i] = perm[i];

        permutationchromosome.fitness = fitness;
        return permutationchromosome;
    }

    public Chromosome[] crossover(Chromosome chromosome, double d)
    {
        return null;
    }

    public void mute(double d)
    {
        for(int i = 0; i < length; i++)
            if(Math.random() * 100D < d)
            {
                int j = (int)(Math.random() * (double)length);
                int k = (int)(Math.random() * (double)length);
                int l = perm[j];
                perm[j] = perm[k];
                perm[k] = l;
            }

    }

    public void randomize()
    {
        for(int i = 0; i < length; i++)
            perm[i] = i;

        for(int j = 0; j < length / 2; j++)
        {
            int k = (int)(Math.random() * (double)length);
            int l = (int)(Math.random() * (double)length);
            int i1 = perm[k];
            perm[k] = perm[l];
            perm[l] = i1;
        }

    }

    public int perm[];
    public int length;
}
