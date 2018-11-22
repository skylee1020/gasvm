package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 8:54:20
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.Number3DChromosome.java

import java.io.PrintStream;

public class Number3DChromosome extends Chromosome
{

    public Number3DChromosome(int i, Graph3D graph3d)
    {
        length = i;
        xbit = new int[length];
        ybit = new int[length];
        g3d = graph3d;
    }

    public Chromosome copy()
    {
        Number3DChromosome number3dchromosome = new Number3DChromosome(length, g3d);
        for(int i = 0; i < length; i++)
        {
            number3dchromosome.xbit[i] = xbit[i];
            number3dchromosome.ybit[i] = ybit[i];
        }

        number3dchromosome.fitness = fitness;
        return number3dchromosome;
    }

    public Chromosome[] crossover(Chromosome chromosome, double d)
    {
        Number3DChromosome anumber3dchromosome[] = new Number3DChromosome[2];
        if(d < Math.random() * 100D)
        {
            lastCrossoverPoint = -1;
            anumber3dchromosome[0] = (Number3DChromosome)copy();
            anumber3dchromosome[1] = (Number3DChromosome)((Number3DChromosome)chromosome).copy();
        } else
        {
            anumber3dchromosome[0] = new Number3DChromosome(length, g3d);
            anumber3dchromosome[1] = new Number3DChromosome(length, g3d);
            int i = (int)(Math.random() * (double)length);
            lastCrossoverPoint = i;
            int j;
            for(j = 0; j < i; j++)
            {
                anumber3dchromosome[0].xbit[j] = xbit[j];
                anumber3dchromosome[1].xbit[j] = ((Number3DChromosome)chromosome).xbit[j];
            }

            for(; j < length; j++)
            {
                anumber3dchromosome[1].xbit[j] = xbit[j];
                anumber3dchromosome[0].xbit[j] = ((Number3DChromosome)chromosome).xbit[j];
            }

            for(j = 0; j < i; j++)
            {
                anumber3dchromosome[0].ybit[j] = ybit[j];
                anumber3dchromosome[1].ybit[j] = ((Number3DChromosome)chromosome).ybit[j];
            }

            for(; j < length; j++)
            {
                anumber3dchromosome[1].ybit[j] = ybit[j];
                anumber3dchromosome[0].ybit[j] = ((Number3DChromosome)chromosome).ybit[j];
            }

        }
        return anumber3dchromosome;
    }

    public long xvalue()
    {
        long l = 0L;
        long l1 = 1L;
        for(int i = 0; i < length; i++)
        {
            l += l1 * (long)xbit[i];
            l1 *= 2L;
        }

        return l;
    }

    public long yvalue()
    {
        long l = 0L;
        long l1 = 1L;
        for(int i = 0; i < length; i++)
        {
            l += l1 * (long)ybit[i];
            l1 *= 2L;
        }

        return l;
    }

    public void printSelf()
    {
        System.out.print("Number(3D): ");
        for(int i = 0; i < length; i++)
            System.out.print(xbit[i]);

        System.out.print(", ");
        for(int j = 0; j < length; j++)
            System.out.print(ybit[j]);

        System.out.print(" = [" + xvalue() + "," + yvalue() + "]");
        System.out.println();
    }

    public void countFitness()
    {
        fitness = g3d.countFitness(xvalue(), yvalue());
    }

    public void mute(double d)
    {
        for(int i = 0; i < length; i++)
        {
            if(Math.random() * 100D < d)
                if(xbit[i] == 1)
                    xbit[i] = 0;
                else
                    xbit[i] = 1;
            if(Math.random() * 100D < d)
                if(ybit[i] == 1)
                    ybit[i] = 0;
                else
                    ybit[i] = 1;
        }

    }

    public void randomize()
    {
        for(int i = 0; i < length; i++)
        {
            if(Math.random() < 0.5D)
                xbit[i] = 0;
            else
                xbit[i] = 1;
            if(Math.random() < 0.5D)
                ybit[i] = 0;
            else
                ybit[i] = 1;
        }

    }

    public int xbit[];
    public int ybit[];
    public int length;
    public Graph3D g3d;
}
