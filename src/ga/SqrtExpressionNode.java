package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 9:11:48
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.Expression.java


class SqrtExpressionNode
    implements ExpressionNode
{

    public SqrtExpressionNode(ExpressionNode expressionnode)
    {
        next = expressionnode;
    }

    public double value(double d, double d1)
    {
        double d2 = next.value(d, d1);
        return Math.sqrt(d2);
    }

    private ExpressionNode next;
}
