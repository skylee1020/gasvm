package ga;

// Decompiled by DJ v2.3.3.38 Copyright 2000 Atanas Neshkov  Date: 2004-10-04 ¿ÀÈÄ 8:54:37
// Home Page : http//members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ga.GAApplication.java

import java.applet.Applet;
import java.awt.*;

public class GAApplication extends Frame
{

    public GAApplication(String s, Applet applet, int i, int j)
    {
        super(s);
        MenuBar menubar = new MenuBar();
        Menu menu = new Menu("Application", true);
        menubar.add(menu);
        menu.add("Exit");
        setMenuBar(menubar);

        add("Center", applet);

        setSize(i, j);
        show();
        System.out.println("[sky1020] applet:" + applet);
        applet.init();
        applet.start();

    }

    public boolean action(Event event, Object obj)
    {
        if(event.target instanceof MenuItem)
        {
            String s = (String)obj;
            if(s.equals("Exit"))
                System.exit(0);
        }
        return false;
    }
}
