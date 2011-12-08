package pcsd.test;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

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
        // do rimming here
        ModularGraphService mgs = null;
        Remote remoteStub;
        
        System.setSecurityManager(new RMISecurityManager());
        
        try {
			String url = "rmi://" + host + port +"/"+name;
			remoteStub = Naming.lookup(url);
			mgs = (ModularGraphService)remoteStub;
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        debug(debug, "Initializing file: " + filename);
        int status = 0;
		try {
			status = mgs.bulkload(filename);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        debug(debug, "Loading done on file: " + filename);
        switch (status) {
		case -42:
			System.out.println("File not found on server");
			break;
		case -43:
			System.out.println("Error while parsing file");
			break;
		case -44:
			System.out.println("Not enough memory to build mapping");
			break;
		case -45:
			System.out.println("Function called more than once");
			break;
		default:
			debug(debug, "Loading succesful");
			Random random = new Random();
			long before = System.currentTimeMillis();
	        for (int i = 0; i < iterations; i++) {
				int key = random.nextInt(maxRandInt);
				try {
					debug(debug,"Key: " + key + "\t" +mgs.getConnections(key).toString());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	        long after = System.currentTimeMillis();
	        debug(debug, "time: " + (after - before) + " milliseconds");
	        
	        // throughput test, run 10 test per 1 second 
			for (int i = 0; i < 10; i++) {
				throughput(mgs);
			}
			break;
		}


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
    /**
     * Runs as many operations as possible on bmgs for 1000 milliseconds
     * @param mgs BasicModularGraphService
     */
    public static void throughput(ModularGraphService mgs) {
    	Random random = new Random();
    	long timer = System.currentTimeMillis();
		long dif = 0l;
		int ops = 0;
		while(dif < 1000) {
			int key = random.nextInt(maxRandInt);
			try {
				mgs.getConnections(key);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dif = System.currentTimeMillis() - timer;
			ops++;
		}
		debug(debug, "operations in 1 second: " + ops);
    }
    /**
     * Prints message, message, if d is true.
     * 
     * @param d boolean
     * @param message String
     */
    public static void debug(boolean d, String message) {
    	if(d) {
    		System.out.println(message);
    	}
    }

}
