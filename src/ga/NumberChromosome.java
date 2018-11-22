package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 8:49:40
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.NumberChromosome.java

import java.awt.Color;
import java.awt.Graphics;
import java.io.PrintStream;

public class NumberChromosome extends StringChromosome
{

    public NumberChromosome(int i)
    {
        super(i);
    }

    public Chromosome copy()
    {
        NumberChromosome numberchromosome = new NumberChromosome(length);
        for(int i = 0; i < length; i++)
            numberchromosome.bit[i] = bit[i];

        numberchromosome.fitness = fitness;
        return numberchromosome;
    }

    public Chromosome[] crossover(Chromosome chromosome, double d)
    {
        NumberChromosome anumberchromosome[] = new NumberChromosome[2];
        if(d < Math.random() * 100D)
        {
            lastCrossoverPoint = -1;
            anumberchromosome[0] = (NumberChromosome)copy();
            anumberchromosome[1] = (NumberChromosome)((NumberChromosome)chromosome).copy();
        } else
        {
            anumberchromosome[0] = new NumberChromosome(length);
            anumberchromosome[1] = new NumberChromosome(length);
            int i = (int)(Math.random() * (double)length);
            lastCrossoverPoint = i;
            int j;
            for(j = 0; j < i; j++)
            {
                anumberchromosome[0].bit[j] = bit[j];
                anumberchromosome[1].bit[j] = ((NumberChromosome)chromosome).bit[j];
            }

            for(; j < length; j++)
            {
                anumberchromosome[1].bit[j] = bit[j];
                anumberchromosome[0].bit[j] = ((NumberChromosome)chromosome).bit[j];
            }

        }
        return anumberchromosome;
    }

    public long value()
    {
        long l = 0L;
        long l1 = 1L;
        for(int i = 0; i < length; i++)
        {
            l += l1 * (long)bit[i];
            l1 *= 2L;
        }

        return l;
    }

    /**
     * Add sky1020 2005/01/18
     * @return
     */
    public String getContent() {
        StringBuffer sb = new StringBuffer();
        //sb.append("chrom:");
        for (int i = bit.length; i > 0 ; i--) {
            int val = bit[i-1];
            sb.append(val);
        }
        //sb.append(",val:" + value());
        return sb.toString();
    }

    public void paintEmpty(Graphics g, int i, int j)
    {
        g.setColor(Color.black);
        g.drawRect(i, j, 8, 7 * length + 1);
    }

    public void paint(Graphics g, int i, int j)
    {
        g.setColor(Color.black);
        g.drawRect(i, j, 8, 7 * length + 1);
        for(int k = 0; k < length; k++)
        {
            if(bit[k] == 1)
                g.setColor(Color.red);
            else
                g.setColor(Color.blue);
            g.fillRect(i + 2, j + k * 7 + 2, 5, 5);
        }

        g.setColor(Color.black);
    }

    public void printSelf()
    {
        System.out.print("Number: ");
        for(int i = 0; i < length; i++)
            System.out.print(bit[i]);

        System.out.print(" = " + value());
        System.out.println();
    }

    public void countFitness()
    {
        fitness = Function.value((690L * value()) / 0x100000000L);
    }
}
