package com.bpk.app.emrapp;

import javax.swing.plaf.metal.*;
import javax.swing.plaf.*;
import java.awt.*;

public class Theme extends MetalTheme
{
    // Blue
    /*
    private final ColorUIResource primary1 = new ColorUIResource( 66, 125, 181);
    private final ColorUIResource primary2 = new ColorUIResource(173, 199, 222);
    private final ColorUIResource primary3 = new ColorUIResource(189, 211, 231);
    private final ColorUIResource secondary1 = new ColorUIResource( 90, 113, 173);
    private final ColorUIResource secondary2 = new ColorUIResource(132, 150, 198);
    private final ColorUIResource secondary3 = new ColorUIResource(214, 227, 239);
    */

    // Sandstone
    /*
    private final ColorUIResource primary1 = new ColorUIResource( 87,  87,  47);
    private final ColorUIResource primary2 = new ColorUIResource(159, 151, 111);
    private final ColorUIResource primary3 = new ColorUIResource(199, 183, 143);
    private final ColorUIResource secondary1 = new ColorUIResource( 111,  111,  111);
    private final ColorUIResource secondary2 = new ColorUIResource(159, 159, 159);
    private final ColorUIResource secondary3 = new ColorUIResource(231, 215, 183);
    */

    // Default
    /**/
    private final ColorUIResource  primary1 = new ColorUIResource(102, 102, 153);
    private final ColorUIResource  primary2 = new ColorUIResource(153, 153, 204);
    private final ColorUIResource  primary3 = new ColorUIResource(204, 204, 255);
    private final ColorUIResource  secondary1 = new ColorUIResource(102, 102, 102);
    private final ColorUIResource  secondary2 = new ColorUIResource(153, 153, 153);
    private final ColorUIResource  secondary3 = new ColorUIResource(204, 204, 204);
    /**/

    private FontUIResource controlFont;
    private FontUIResource systemFont;
    private FontUIResource userFont;
    private FontUIResource smallFont;

    public Theme()
    {
      controlFont = new FontUIResource( Font.getFont( "swing.plaf.metal.controlFont", new Font("Tahoma", Font.PLAIN, 15) ) );
      systemFont  = new FontUIResource( Font.getFont( "swing.plaf.metal.systemFont" , new Font("Tahoma", Font.PLAIN, 15) ) );
      userFont    = new FontUIResource( Font.getFont( "swing.plaf.metal.userFont"   , new Font("Tahoma", Font.PLAIN, 15) ) );
      smallFont   = new FontUIResource( Font.getFont( "swing.plaf.metal.smallFont"  , new Font("Tahoma", Font.PLAIN, 14) ) );
    }

    // these are blue in Metal Default Theme
    protected ColorUIResource  getPrimary1() { return primary1; }
    protected ColorUIResource  getPrimary2() { return primary2; }
    protected ColorUIResource  getPrimary3() { return primary3; }

    // these are gray in Metal Default Theme
    protected ColorUIResource  getSecondary1()   { return secondary1; }
    protected ColorUIResource  getSecondary2()   { return secondary2; }
    protected ColorUIResource  getSecondary3()   { return secondary3; }

    public FontUIResource  getControlTextFont()  { return controlFont; }
    public FontUIResource  getSystemTextFont()   { return systemFont;  }
    public FontUIResource  getUserTextFont()     { return userFont;    }

    public FontUIResource  getMenuTextFont()     { return controlFont; }
    public FontUIResource  getSubTextFont()      { return smallFont;   }
    public FontUIResource  getWindowTitleFont()  { return controlFont; }

    public String getName()
    {
      return "ThaiSteel";
    }
}
