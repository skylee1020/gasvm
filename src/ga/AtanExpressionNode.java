package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 9:12:59
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.Expression.java


class AtanExpressionNode
    implements ExpressionNode
{

    public AtanExpressionNode(ExpressionNode expressionnode)
    {
        next = expressionnode;
    }

    public double value(double d, double d1)
    {
        return Math.atan(next.value(d, d1));
    }

    private ExpressionNode next;
}
