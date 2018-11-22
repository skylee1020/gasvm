package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 9:13:57
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.Expression.java


class FactorialExpressionNode
    implements ExpressionNode
{

    public FactorialExpressionNode(ExpressionNode expressionnode)
    {
        next = expressionnode;
    }

    public double value(double d, double d1)
    {
        double d2 = next.value(d, d1);
        int i = (int)d2;
        if((double)i != d2 || i < 0 || i > 1000)
            throw new ArithmeticException();
        if(i == 0)
            return 1.0D;
        double d3 = 1.0D;
        for(int j = 1; j <= i; j++)
            d3 *= j;

        return d3;
    }

    private ExpressionNode next;
}
