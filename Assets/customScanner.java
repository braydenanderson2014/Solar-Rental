package Assets;

import java.util.Scanner;

import messageHandler.messageHandler;
/**
 * Write a description of class customScanner here.
 *
 * @author (Brayden Anderson)
 * @version (Base Version: Beta 1.0.1, Snapshot 3xWav-7)
 */
public class customScanner
{
    public static Scanner scan;
   
    public customScanner(){
         scan = new Scanner(System.in);
         messageHandler.HandleMessage(1, "Created Scanner Object for user input", false);
    }
    public static Scanner getScanner(){
        return scan;
    }
    public static double nextDouble(){
        double d = scan.nextDouble();
        return d;
    }
    
    public static String nextLine(){
        String s = scan.nextLine();
        return s;
    }
    
    public static int nextInt(){
        int i = scan.nextInt();
        return i;
    }
    
    public int close(int b){
        scan.close();
        try{
            b = System.in.available();
        }catch(Exception e){
            messageHandler.HandleMessage(-2, e.toString(), true);
            e.printStackTrace();
            b = 0;
        }
        return b;
    }
}
