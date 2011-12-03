package pcsd.test;

import java.lang.reflect.Array;
import java.util.Random;

import pcsd.basic.BasicGraphService;
import pcsd.Result;

/**
 * @author You!
 */
public class SimpleBasicGraphServiceClient {

    private static int maxRandInt  = Integer.MAX_VALUE;
    private static int iterations  = 10000;
    private static boolean debug   = false;
    private static String filename = "";

    /**
     * Runs simple tests for a basic graph service.
     * 
     * @param args
     */
    public static void main(String[] args) {
      if (args.length < 1) {
            usage();
        }
        parseArgs(args);
        System.out.println("running on file: " + filename);
        BasicGraphService bgs = new BasicGraphService();
        int status = bgs.bulkload(filename);
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
			Random random = new Random();
	        for (int i = 0; i < iterations; i++) {
				int key = random.nextInt(maxRandInt);
				System.out.println("Key: " + key + "\t" +bgs.getConnections(key).toString());
			}
			break;
		}
        
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
