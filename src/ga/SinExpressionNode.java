package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 9:13:41
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.Expression.java


class SinExpressionNode
    implements ExpressionNode
{

    public SinExpressionNode(ExpressionNode expressionnode)
    {
        next = expressionnode;
    }

    public double value(double d, double d1)
    {
        return Math.sin(next.value(d, d1));
    }

    private ExpressionNode next;
}
