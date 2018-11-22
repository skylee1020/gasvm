package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 9:01:25
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.Expression.java


public class Expression
{

    public Expression(String s)
        throws ParseError
    {
        LexicalAnalyzer lexicalanalyzer = new LexicalAnalyzer(s);
        node = analyzeExpression(lexicalanalyzer);
    }

    public double value(double d, double d1)
    {
        return node.value(d, d1);
    }

    private static ExpressionNode analyzeLiteral(LexicalAnalyzer lexicalanalyzer)
        throws ParseError
    {
        switch(lexicalanalyzer.type)
        {
        case 1: // '\001'
            double d = lexicalanalyzer.value;
            lexicalanalyzer.next();
            return new NumberExpressionNode(d);

        case 2: // '\002'
            lexicalanalyzer.next();
            return new XExpressionNode();

        case 31: // '\037'
            lexicalanalyzer.next();
            return new YExpressionNode();

        case 3: // '\003'
            lexicalanalyzer.next();
            return new NumberExpressionNode(2.7182818284590451D);

        case 4: // '\004'
            lexicalanalyzer.next();
            return new NumberExpressionNode(3.1415926535897931D);

        case 5: // '\005'
            lexicalanalyzer.next();
            ExpressionNode expressionnode = analyzeSum(lexicalanalyzer);
            if(lexicalanalyzer.type != 6)
                lexicalanalyzer.throwError();
            lexicalanalyzer.next();
            return expressionnode;
        }
        lexicalanalyzer.throwError();
        return null;
    }

    private static ExpressionNode analyzePower(LexicalAnalyzer lexicalanalyzer)
        throws ParseError
    {
        Object obj = analyzeLiteral(lexicalanalyzer);
        do
            switch(lexicalanalyzer.type)
            {
            case 7: // '\007'
                lexicalanalyzer.next();
                obj = new PowerExpressionNode(((ExpressionNode) (obj)), analyzeLiteral(lexicalanalyzer));
                break;

            case 12: // '\f'
                lexicalanalyzer.next();
                obj = new FactorialExpressionNode(((ExpressionNode) (obj)));
                break;

            default:
                return ((ExpressionNode) (obj));
            }
        while(true);
    }

    private static ExpressionNode analyzeFunction(LexicalAnalyzer lexicalanalyzer)
        throws ParseError
    {
        switch(lexicalanalyzer.type)
        {
        case 9: // '\t'
            lexicalanalyzer.next();
            return new UnaryMinusExpressionNode(analyzeFunction(lexicalanalyzer));

        case 8: // '\b'
            lexicalanalyzer.next();
            return analyzeFunction(lexicalanalyzer);

        case 13: // '\r'
            lexicalanalyzer.next();
            return new SinExpressionNode(analyzeFunction(lexicalanalyzer));

        case 14: // '\016'
            lexicalanalyzer.next();
            return new AsinExpressionNode(analyzeFunction(lexicalanalyzer));

        case 15: // '\017'
            lexicalanalyzer.next();
            return new CosExpressionNode(analyzeFunction(lexicalanalyzer));

        case 16: // '\020'
            lexicalanalyzer.next();
            return new AcosExpressionNode(analyzeFunction(lexicalanalyzer));

        case 17: // '\021'
            lexicalanalyzer.next();
            return new TanExpressionNode(analyzeFunction(lexicalanalyzer));

        case 18: // '\022'
            lexicalanalyzer.next();
            return new AtanExpressionNode(analyzeFunction(lexicalanalyzer));

        case 19: // '\023'
            lexicalanalyzer.next();
            return new SinhExpressionNode(analyzeFunction(lexicalanalyzer));

        case 20: // '\024'
            lexicalanalyzer.next();
            return new AsinhExpressionNode(analyzeFunction(lexicalanalyzer));

        case 21: // '\025'
            lexicalanalyzer.next();
            return new CoshExpressionNode(analyzeFunction(lexicalanalyzer));

        case 22: // '\026'
            lexicalanalyzer.next();
            return new AcoshExpressionNode(analyzeFunction(lexicalanalyzer));

        case 23: // '\027'
            lexicalanalyzer.next();
            return new TanhExpressionNode(analyzeFunction(lexicalanalyzer));

        case 24: // '\030'
            lexicalanalyzer.next();
            return new AtanhExpressionNode(analyzeFunction(lexicalanalyzer));

        case 25: // '\031'
            lexicalanalyzer.next();
            return new LogExpressionNode(analyzeFunction(lexicalanalyzer));

        case 26: // '\032'
            lexicalanalyzer.next();
            return new LnExpressionNode(analyzeFunction(lexicalanalyzer));

        case 27: // '\033'
            lexicalanalyzer.next();
            return new SqrExpressionNode(analyzeFunction(lexicalanalyzer));

        case 28: // '\034'
            lexicalanalyzer.next();
            return new SqrtExpressionNode(analyzeFunction(lexicalanalyzer));

        case 29: // '\035'
            lexicalanalyzer.next();
            return new AbsExpressionNode(analyzeFunction(lexicalanalyzer));

        case 30: // '\036'
            lexicalanalyzer.next();
            return new IntExpressionNode(analyzeFunction(lexicalanalyzer));

        case 10: // '\n'
        case 11: // '\013'
        case 12: // '\f'
        default:
            return analyzePower(lexicalanalyzer);
        }
    }

    private static ExpressionNode analyzeProduct(LexicalAnalyzer lexicalanalyzer)
        throws ParseError
    {
        Object obj = analyzeFunction(lexicalanalyzer);
        do
            switch(lexicalanalyzer.type)
            {
            case 10: // '\n'
                lexicalanalyzer.next();
                obj = new MultiplyExpressionNode(((ExpressionNode) (obj)), analyzeFunction(lexicalanalyzer));
                break;

            case 11: // '\013'
                lexicalanalyzer.next();
                obj = new DivideExpressionNode(((ExpressionNode) (obj)), analyzeFunction(lexicalanalyzer));
                break;

            default:
                return ((ExpressionNode) (obj));
            }
        while(true);
    }

    private static ExpressionNode analyzeSum(LexicalAnalyzer lexicalanalyzer)
        throws ParseError
    {
        Object obj = analyzeProduct(lexicalanalyzer);
        do
            switch(lexicalanalyzer.type)
            {
            case 8: // '\b'
                lexicalanalyzer.next();
                obj = new PlusExpressionNode(((ExpressionNode) (obj)), analyzeProduct(lexicalanalyzer));
                break;

            case 9: // '\t'
                lexicalanalyzer.next();
                obj = new MinusExpressionNode(((ExpressionNode) (obj)), analyzeProduct(lexicalanalyzer));
                break;

            default:
                return ((ExpressionNode) (obj));
            }
        while(true);
    }

    private static ExpressionNode analyzeExpression(LexicalAnalyzer lexicalanalyzer)
        throws ParseError
    {
        ExpressionNode expressionnode = analyzeSum(lexicalanalyzer);
        if(lexicalanalyzer.type != 0)
            lexicalanalyzer.throwError();
        return expressionnode;
    }

    private ExpressionNode node;
}
