package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 9:01:43
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.Graph3D.java


public class Graph3D
{

    public Graph3D()
    {
        this(80);
    }

    public Graph3D(int i)
    {
        theta = 0.69813170079773179D;
        phi = 1.48352986419518D;
        cSize = 300;
        xMin = -100D;
        yMin = -100D;
        xMax = 100D;
        yMax = 100D;
        graphTop = 10D;
        graphBottom = -10D;
        bigStep = 1;
        lines = 10;
        lookingForMaximum = false;
        lines = i / 4;
        resample(i);
        setScale(-100D, 100D, -100D, 100D);
        setSize(300);
    }

    public synchronized void setScale(double d, double d1, double d2, double d3)
    {
        xMin = d;
        xMax = d1;
        yMin = d2;
        yMax = d3;
        xScale = (xMax - xMin) / (double)steps;
        yScale = (yMax - yMin) / (double)steps;
    }

    public synchronized void setLines(int i)
    {
        lines = i;
        bigStep = steps / lines;
    }

    public synchronized void setSize(int i)
    {
        cSize = i;
        sh = cSize / 50;
        xs = 1.0D;
        ys = 1.0D;
    }

    public synchronized void resample(int i)
    {
        valid = false;
        steps = i;
        x = new int[steps][steps];
        y = new int[steps][steps];
        dx = new double[steps][steps];
        dy = new double[steps][steps];
        bigStep = steps / lines;
    }

    public synchronized void recalculate()
    {
        valid = false;
        xmin = 1.7976931348623157E+308D;
        xmax = Double.MIN_VALUE;
        ymin = 1.7976931348623157E+308D;
        ymax = Double.MIN_VALUE;
        graphBottom = 1.7976931348623157E+308D;
        graphTop = Double.MIN_VALUE;
        for(int i = 0; i < steps; i++)
        {
            for(int k = 0; k < steps; k++)
            {
                double d2 = (double)i * xScale + xMin;
                double d3 = (double)k * yScale + yMin;
                double d4 = Function3D.value(d2, d3);
                if(d4 < graphBottom)
                    graphBottom = d4;
                if(d4 > graphTop)
                    graphTop = d4;
                double d = d2 * Math.sin(theta) + d3 * Math.cos(theta);
                double d1 = (d2 * Math.cos(theta) - d3 * Math.sin(theta)) * Math.sin(phi) + d4 * Math.cos(phi);
                double d5 = dx[i][k] = d;
                double d6 = dy[i][k] = d1;
                if(d5 < xmin)
                    xmin = d5;
                if(d6 < ymin)
                    ymin = d6;
                if(d5 > xmax)
                    xmax = d5;
                if(d6 > ymax)
                    ymax = d6;
            }

        }

        xs = ((double)cSize - sh * 2D) / (xmax - xmin);
        ys = ((double)cSize - sh * 2D) / (ymax - ymin);
        for(int j = 0; j < steps; j++)
        {
            for(int l = 0; l < steps; l++)
            {
                x[j][l] = (int)((dx[j][l] - xmin) * xs + sh);
                y[j][l] = cSize - (int)((dy[j][l] - ymin) * ys + sh);
            }

        }

        valid = true;
    }

    public synchronized void xyzCoords(double ad[], int ai[])
    {
        double d = ad[0];
        double d1 = ad[1];
        double d2 = Function3D.value(d, d1);
        double d3 = d * Math.sin(theta) + d1 * Math.cos(theta);
        double d4 = (d * Math.cos(theta) - d1 * Math.sin(theta)) * Math.sin(phi) + d2 * Math.cos(phi);
        ai[0] = (int)((d3 - xmin) * xs + sh);
        ai[1] = cSize - (int)((d4 - ymin) * ys + sh);
    }

    public synchronized void xyCoords(double ad[], int ai[])
    {
        double d = ad[0];
        double d1 = ad[1];
        double d2 = ad[2];
        double d3 = d * Math.sin(theta) + d1 * Math.cos(theta);
        double d4 = (d * Math.cos(theta) - d1 * Math.sin(theta)) * Math.sin(phi) + d2 * Math.cos(phi);
        ai[0] = (int)((d3 - xmin) * xs + sh);
        ai[1] = cSize - (int)((d4 - ymin) * ys + sh);
    }

    public synchronized void xyBottomCoords(long al[], int ai[])
    {
        double ad[] = new double[3];
        ad[0] = ((xMax - xMin) * (double)al[0]) / 4294967296D + xMin;
        ad[1] = ((yMax - yMin) * (double)al[1]) / 4294967296D + yMin;
        ad[2] = graphBottom;
        xyCoords(ad, ai);
    }

    public synchronized void xyTopCoords(long al[], int ai[])
    {
        double ad[] = new double[3];
        ad[0] = ((xMax - xMin) * (double)al[0]) / 4294967296D + xMin;
        ad[1] = ((yMax - yMin) * (double)al[1]) / 4294967296D + yMin;
        ad[2] = graphTop;
        xyCoords(ad, ai);
    }

    public synchronized void xyZCoords(long al[], int ai[])
    {
        double ad[] = new double[3];
        ad[0] = ((xMax - xMin) * (double)al[0]) / 4294967296D + xMin;
        ad[1] = ((yMax - yMin) * (double)al[1]) / 4294967296D + yMin;
        ad[2] = Function3D.value(ad[0], ad[1]);
        xyCoords(ad, ai);
    }

    public synchronized int countFitness(long l, long l1)
    {
        double d = ((xMax - xMin) * (double)l) / 4294967296D + xMin;
        double d1 = ((yMax - yMin) * (double)l1) / 4294967296D + yMin;
        double d2 = Function3D.value(d, d1);
        int i = (int)((10000D * (d2 - graphBottom)) / (graphTop - graphBottom));
        if(lookingForMaximum)
            return i;
        else
            return 10010 - i;
    }

    public double xValue(long l)
    {
        return ((xMax - xMin) * (double)l) / 4294967296D + xMin;
    }

    public double yValue(long l)
    {
        return ((yMax - yMin) * (double)l) / 4294967296D + yMin;
    }

    public double zValue(long l, long l1)
    {
        double d = ((xMax - xMin) * (double)l) / 4294967296D + xMin;
        double d1 = ((yMax - yMin) * (double)l1) / 4294967296D + yMin;
        double d2 = Function3D.value(d, d1);
        return d2;
    }

    protected final int SPACE_AROUND = 50;
    public int x[][];
    public int y[][];
    public double dx[][];
    public double dy[][];
    public int steps;
    public boolean valid;
    public double theta;
    public double phi;
    protected int cSize;
    protected double xMin;
    protected double yMin;
    protected double xMax;
    protected double yMax;
    protected double xScale;
    protected double yScale;
    protected double sh;
    protected double xs;
    protected double ys;
    protected double xmin;
    protected double ymin;
    protected double xmax;
    protected double ymax;
    protected double graphTop;
    protected double graphBottom;
    public int bigStep;
    public int lines;
    public boolean lookingForMaximum;
}
