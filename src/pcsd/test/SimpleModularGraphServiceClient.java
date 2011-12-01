package pcsd.test;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import pcsd.modular.BasicModularGraphService;
import pcsd.ModularGraphService;
import pcsd.Result;

/**
 * @author You!
 */
public class SimpleModularGraphServiceClient {

    private static int maxRandInt  = Integer.MAX_VALUE;
    private static int iterations  = 10000;
    private static boolean debug   = false;
    private static String filename = "";
    private static String name = "modularService";
    private static String host = "localhost";
    private static int port = 1099;

    /**
     * Runs simple tests for a modular graph service.
     * 
     * @param args
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            usage();
        }

        parseArgs(args);


        /*
         * Here you should test your ModularGraphService implementation.
         */
    }


    /**
     * Print a simple usage message.
     */
    public static void usage() {
        System.out.println("Usage: java <java-class> [-d] [-m <value>] FILENAME");
        System.out.println("\t -d \t\t Enable debug mode.");
        System.out.println("\t -m <value>\t Set the maximum random value " + 
                           "to a specific integer value.");
        System.out.println("\t -n <value>\t Set the number of iterations " + 
                           "for the test.");
        System.out.println("\t --host=<url> \t\t The RMI registry host.");
        System.out.println("\t --port=<value>\t The RMI registry port.");
        System.out.println("\t --name <value>\t The name of the server.");
        System.out.println("\n");
        System.exit(1);
    }


    /**
     * Parse commandline arguments.
     *
     * Instead of mixing this code into the code in main, we use this function
     * to allow easier extension of the number of arguments.
     */
    public static void parseArgs(String[] args) {
        int argc = args.length,
            i    = 0;

        for (; i < argc; i++) {
            if (args[i].equals("-d")) {
                debug = true;
            } 
            else if(args[i].equals("-m")) {
                try {
                    maxRandInt = Integer.parseInt(args[++i]);
                } catch(NumberFormatException e) {
                    System.out.println(
                            "Maximum random value must be a valid integer!"
                            );
                    usage();
                }
            } else if(args[i].equals("-n")) {
                try {
                    iterations = Integer.parseInt(args[++i]);
                } catch(NumberFormatException e) {
                    System.out.println(
                            "Iteration value must be a valid integer!"
                            );
                    usage();
                }
            } else if(args[i].equals("--host")) {
                host = args[++i];
            } else if(args[i].equals("--port")) {
                try {
                    port = Integer.parseInt(args[++i]);
                } catch(NumberFormatException e) {
                    System.out.println(
                            "Port must be a valid integer!"
                            );
                    usage();
                }
            } else if(args[i].equals("--name")) {
                name = args[++i];
            } else {
                filename = args[i];
            }
        }

        if (filename.equals("")) {
            System.out.println("Must supply a filename!\n");
            usage();
        }
    }

}
