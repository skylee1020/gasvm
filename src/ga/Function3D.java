package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 8:54:28
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.Function3D.java


public class Function3D
{

    public static synchronized double value(double d, double d1)
    {
        switch(returnType)
        {
        case 1: // '\001'
            try
            {
                return expr.value(d, d1);
            }
            catch(ArithmeticException _ex)
            {
                wasError = true;
            }
            returnType = 0;
            return value(d, d1);

        case 2: // '\002'
            return Math.sin(d / 20D) * 20D + Math.cos(d1 / 40D) * 40D;
        }
        return 0.0D;
    }

    public static synchronized void parseFunction(String s)
    {
        exprStr = s;
        returnType = 1;
        wasError = false;
        try
        {
            expr = new Expression(exprStr);
            return;
        }
        catch(ParseError _ex)
        {
            returnType = 0;
            wasError = true;
            return;
        }
        catch(ArithmeticException _ex)
        {
            returnType = 0;
        }
        wasError = true;
    }

    public Function3D()
    {
    }

    protected static String exprStr;
    protected static Expression expr;
    public static int returnType;
    public static final int returnBasic = 0;
    public static final int returnParsed = 1;
    public static final int returnStatic1 = 2;
    public static boolean wasError;
}
