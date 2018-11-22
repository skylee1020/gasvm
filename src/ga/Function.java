package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 9:19:08
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.Function.java


public class Function
{

    public static int value(long l)
    {
        int i = (int)((110D - Math.abs(((double)l - 280D) / 5D)) + Math.sin((double)l / 2D) * 10D + Math.cos((double)l / 5D) * 5D);
        return i;
    }

    public Function()
    {
    }
}
