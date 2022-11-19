package assets;

import java.util.InputMismatchException;
import java.util.Scanner;

import messageHandler.MessageProcessor;
/**
 * Write a description of class customScanner here.
 *
 * @author (Brayden Anderson)
 * @version (Base Version: Beta 1.0.1, Snapshot 3xWav-7)
 */
public class CustomScanner
{
    public static Scanner scan;

    public CustomScanner(){
        scan = new Scanner(System.in);
        MessageProcessor.processMessage(1, "Created Scanner Object for user input", false);
    }

    public static Scanner getScanner(){
        return scan;
    }

    public static double nextDouble() throws InputMismatchException{
        return scan.nextDouble();
    }

    public static String nextLine(){
        return scan.nextLine();
    }

    public static int nextInt() throws InputMismatchException{
        return scan.nextInt();
    }

    public static byte nextByte() throws InputMismatchException{
        return scan.nextByte();
    }

    public static short nextShort() throws InputMismatchException{
        return scan.nextShort();
    }

    public static Boolean nextBoolean() throws InputMismatchException{
        return scan.nextBoolean();
    }

    public int close(int b){
        scan.close();
        try{
            b = System.in.available();
        }catch(Exception e){
            MessageProcessor.processMessage(-2, e.toString(), true);
            e.printStackTrace();
            b = 0;
        }
        return b;
    }
}
