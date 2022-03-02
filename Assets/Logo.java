package Assets;

import MainSystem.ProgramController;

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
        System.out.println("Copyright 2021");    
        return Logo;
    }
    public static String displayLogo2(){
        String Logo = "SOLAR RENTALS";
        return Logo;
    }
    public static boolean clear(){
        return true;
    }
}