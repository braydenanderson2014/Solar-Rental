package com.solarrental.assets;

import java.io.IOException;

import InstallManager.ProgramController;
import messageHandler.MessageProcessor;
public class Logo{
    private static final String SOLAR_RENTALS = "SOLAR RENTALS";

	public static String displayLogo(){
        try {
			ProgramController.clearScreen();
		} catch (IOException e) {
			MessageProcessor.processMessage(-2, e.toString(), true);
		} catch (InterruptedException e) {
			MessageProcessor.processMessage(-2, e.toString(), true);
		}
        System.out.println(" #####  ####### #          #    ######    ");
        System.out.println("#     # #     # #         # #   #     #   "); 
        System.out.println("#       #     # #        #   #  #     #   ");
        System.out.println(" #####  #     # #       #     # ######    "); 
        System.out.println("      # #     # #       ####### #   #     ");
        System.out.println("#     # #     # #       #     # #    #    ");
        System.out.println(" #####  ####### ####### #     # #     #   ");   
        System.out.println("==========================================");
        System.out.println("Copyright 2022");    
        return "SOLAR RENTALS";
    }

    public static String displayLogo2(){
        return SOLAR_RENTALS;
    }

    public static String displayLine(){
        System.out.println("==========================================");
        return "";
    }

}