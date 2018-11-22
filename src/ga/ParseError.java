package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 9:10:56
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.ParseError.java


public class ParseError extends Throwable
{

    ParseError(String s, int i)
    {
        string = s;
        position = i;
    }

    public String string;
    public int position;
}
