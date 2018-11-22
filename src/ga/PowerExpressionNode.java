package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 9:18:40
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.Expression.java


class PowerExpressionNode
    implements ExpressionNode
{

    public PowerExpressionNode(ExpressionNode expressionnode, ExpressionNode expressionnode1)
    {
        left = expressionnode;
        right = expressionnode1;
    }

    public double value(double d, double d1)
    {
        return Math.pow(left.value(d, d1), right.value(d, d1));
    }

    private ExpressionNode left;
    private ExpressionNode right;
}
