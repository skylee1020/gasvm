package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 8:57:06
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.TSPInstance.java


public class TSPInstance
{

    public TSPInstance(int i)
    {
        max = i;
        size = 0;
        x = new int[i];
        y = new int[i];
        dist = new int[i][i];
        dmComputed = false;
    }

    public void resize(int i)
    {
        int ai[] = x;
        int ai1[] = y;
        x = new int[i];
        y = new int[i];
        dist = new int[i][i];
        dmComputed = false;
        int j = max;
        if(i < j)
            j = i;
        max = i;
        for(int k = 0; k < j; k++)
        {
            x[k] = ai[k];
            y[k] = ai1[k];
        }

        size = j;
    }

    public void clearCities()
    {
        size = 0;
        dmComputed = false;
    }

    public void addCity(int i, int j)
    {
        if(size == max)
        {
            return;
        } else
        {
            x[size] = i;
            y[size] = j;
            size++;
            dmComputed = false;
            return;
        }
    }

    public int nearCity(int i, int j)
    {
        for(int k = 0; k < size; k++)
            if(Math.sqrt((x[k] - i) * (x[k] - i) + (y[k] - j) * (y[k] - j)) < 7D)
                return k;

        return -1;
    }

    public void deleteCity(int i)
    {
        size--;
        for(int j = i; j < size; j++)
        {
            x[j] = x[j + 1];
            y[j] = y[j + 1];
        }

        dmComputed = false;
    }

    public void checkCity(int i, int j)
    {
        int k = nearCity(i, j);
        if(k == -1)
        {
            addCity(i, j);
            return;
        } else
        {
            deleteCity(k);
            return;
        }
    }

    public void computeDistanceMatrix()
    {
        for(int i = 0; i < size; i++)
            dist[i][i] = 0;

        for(int j = 0; j < size; j++)
        {
            for(int k = 0; k < j; k++)
                dist[j][k] = dist[k][j] = (int)Math.round(Math.sqrt((x[j] - x[k]) * (x[j] - x[k]) + (y[j] - y[k]) * (y[j] - y[k])));

        }

        dmComputed = true;
    }

    public int count()
    {
        return size;
    }

    public int distance(int i, int j)
    {
        if(!dmComputed)
            return 0x7fffffff;
        else
            return dist[i][j];
    }

    public void changeSize(int i, int j)
    {
        double d = (double)j / (double)i;
        for(int k = 0; k < size; k++)
        {
            x[k] = (int)(d * (double)x[k]);
            y[k] = (int)(d * (double)y[k]);
        }

    }

    public int max;
    public int size;
    public int x[];
    public int y[];
    int dist[][];
    public boolean dmComputed;
    private static final int NEAR_SENSITIVITY = 7;
}
