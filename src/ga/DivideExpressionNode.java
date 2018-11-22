package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 9:11:14
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.Expression.java


class DivideExpressionNode
    implements ExpressionNode
{

    public DivideExpressionNode(ExpressionNode expressionnode, ExpressionNode expressionnode1)
    {
        left = expressionnode;
        right = expressionnode1;
    }

    public double value(double d, double d1)
    {
        return left.value(d, d1) / right.value(d, d1);
    }

    private ExpressionNode left;
    private ExpressionNode right;
}
