package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 8:57:15
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.TSPChromosome.java

import java.io.PrintStream;

public class TSPChromosome extends PermutationChromosome
{

    public TSPChromosome(int i, TSPInstance tspinstance)
    {
        super(i);
        tspi = tspinstance;
    }

    public void countFitness()
    {
        int i = 0;
        if(length > 0)
        {
            for(int j = 0; j < length - 1; j++)
                i += tspi.distance(perm[j], perm[j + 1]);

            i += tspi.distance(perm[length - 1], perm[0]);
        }
        fitness = 0x7fffffff - i;
    }

    public void printSelf()
    {
        System.out.print("TSP: ");
        for(int i = 0; i < length; i++)
            System.out.print(perm[i] + ",");

        System.out.println();
    }

    public Chromosome copy()
    {
        TSPChromosome tspchromosome = new TSPChromosome(length, tspi);
        for(int i = 0; i < length; i++)
            tspchromosome.perm[i] = perm[i];

        tspchromosome.fitness = fitness;
        tspchromosome.tspi = tspi;
        return tspchromosome;
    }

    public Chromosome[] crossover(Chromosome chromosome, double d)
    {
        TSPChromosome atspchromosome[] = new TSPChromosome[2];
        if(d < Math.random() * 100D)
        {
            lastCrossoverPoint = -1;
            atspchromosome[0] = (TSPChromosome)copy();
            atspchromosome[1] = (TSPChromosome)((TSPChromosome)chromosome).copy();
        } else
        {
            atspchromosome[0] = new TSPChromosome(length, tspi);
            atspchromosome[1] = new TSPChromosome(length, tspi);
            int i = (int)(Math.random() * (double)length);
            lastCrossoverPoint = i;
            boolean flag = false;
            boolean aflag[] = new boolean[length];
            boolean aflag1[] = new boolean[length];
            for(int j = 0; j < length; j++)
                aflag[j] = aflag1[j] = false;

            for(int k = 0; k < i; k++)
            {
                atspchromosome[0].perm[k] = perm[k];
                aflag[atspchromosome[0].perm[k]] = true;
                atspchromosome[1].perm[k] = ((TSPChromosome)chromosome).perm[k];
                aflag1[atspchromosome[1].perm[k]] = true;
            }

            int j1 = i;
            for(int l = 0; l < length; l++)
                if(!aflag[l])
                    atspchromosome[0].perm[j1++] = l;

            j1 = i;
            for(int i1 = 0; i1 < length; i1++)
                if(!aflag1[i1])
                    atspchromosome[1].perm[j1++] = i1;

        }
        return atspchromosome;
    }

    public Chromosome[] crossoverRest(Chromosome chromosome, double d)
    {
        TSPChromosome atspchromosome[] = new TSPChromosome[2];
        TSPChromosome tspchromosome = (TSPChromosome)chromosome;
        if(d < Math.random() * 100D)
        {
            lastCrossoverPoint = -1;
            atspchromosome[0] = (TSPChromosome)copy();
            atspchromosome[1] = (TSPChromosome)tspchromosome.copy();
        } else
        {
            atspchromosome[0] = new TSPChromosome(length, tspi);
            atspchromosome[1] = new TSPChromosome(length, tspi);
            int i = (int)(Math.random() * (double)length);
            int j = (int)(Math.random() * (double)length);
            if(i > j)
            {
                int k = i;
                i = j;
                j = k;
            }
            lastCrossoverPoint = i;
            boolean aflag[] = new boolean[length];
            for(int l = 0; l < length; l++)
                aflag[l] = false;

            for(int i1 = i; i1 < j; i1++)
            {
                atspchromosome[0].perm[i1] = perm[i1];
                aflag[perm[i1]] = true;
            }

            int j1 = -1;
            for(int k1 = 0; k1 < length; k1++)
                if(!aflag[tspchromosome.perm[k1]])
                {
                    aflag[tspchromosome.perm[k1]] = true;
                    for(j1++; j1 >= i && j1 < j; j1++);
                    atspchromosome[0].perm[j1] = tspchromosome.perm[k1];
                }

            for(int l1 = 0; l1 < length; l1++)
                aflag[l1] = false;

            for(int i2 = i; i2 < j; i2++)
            {
                atspchromosome[1].perm[i2] = tspchromosome.perm[i2];
                aflag[tspchromosome.perm[i2]] = true;
            }

            j1 = -1;
            for(int j2 = 0; j2 < length; j2++)
                if(!aflag[perm[j2]])
                {
                    aflag[perm[j2]] = true;
                    for(j1++; j1 >= i && j1 < j; j1++);
                    atspchromosome[1].perm[j1] = tspchromosome.perm[j2];
                }

        }
        return atspchromosome;
    }

    public Chromosome[] crossover(Chromosome chromosome, double d, int i)
    {
        if(i == 0)
            return crossover(chromosome, d);
        if(i == 1)
            return crossoverRest(chromosome, d);
        if(i == 2)
            return crossover(chromosome, -1D);
        else
            return null;
    }

    public void mute(double d, int i)
    {
        if(i == 0)
            mute(d);
        if(i == 1)
            muteImprovingRand(d);
        if(i == 2)
            muteImprovingCont(d);
        if(i == 3)
            muteImprovingRRand(d);
        if(i == 4)
            muteImprovingRCont(d);
        if(i == 5)
            return;
        else
            return;
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

    public void muteImprovingRRand(double d)
    {
        mute(d);
        muteImprovingRand(d);
    }

    protected void improve(int i, int j)
    {
        countFitness();
        int k = fitness;
        int l = perm[i];
        perm[i] = perm[j];
        perm[j] = l;
        countFitness();
        if(fitness < k)
        {
            int i1 = perm[i];
            perm[i] = perm[j];
            perm[j] = i1;
        }
    }

    public void muteImprovingRand(double d)
    {
        for(int i = 0; i < length * 2; i++)
        {
            int j = (int)(Math.random() * (double)length);
            int k = (int)(Math.random() * (double)length);
            improve(j, k);
        }

    }

    public void muteImprovingRCont(double d)
    {
        mute(d);
        muteImprovingCont(d);
    }

    public void muteImprovingCont(double d)
    {
        for(int i = 0; i < length; i++)
        {
            for(int j = 0; j < length; j++)
            {
                int k = i;
                int l = j;
                improve(k, l);
            }

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

    public TSPInstance tspi;
    public static final int REST_CROSSOVER = 1;
    public static final int NO_CROSSOVER = 2;
    public static final int RAND_IMPROVING_MUTATION = 1;
    public static final int CONT_IMPROVING_MUTATION = 2;
    public static final int RRAND_IMPROVING_MUTATION = 3;
    public static final int RCONT_IMPROVING_MUTATION = 4;
    public static final int NO_MUTATION = 5;
}
