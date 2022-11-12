package Assets;

import InstallManager.ProgramController;
public class Logo{
    public static String displayLogo(){
        ProgramController.clearScreen();
        String Logo = "SOLAR";
        System.out.println(" #####  ####### #          #    ######    ");
        System.out.println("#     # #     # #         # #   #     #   "); 
        System.out.println("#       #     # #        #   #  #     #   ");
        System.out.println(" #####  #     # #       #     # ######    "); 
        System.out.println("      # #     # #       ####### #   #     ");
        System.out.println("#     # #     # #       #     # #    #    ");
        System.out.println(" #####  ####### ####### #     # #     #   ");   
        System.out.println("==========================================");
        System.out.println("Copyright 2022");    
        return Logo;
    }

    public static String displayLogo2(){
        String Logo = "SOLAR RENTALS";
        return Logo;
    }

    public static String displayLine(){
        System.out.println("==========================================");
        return "";
    }

}