package pcsd.modular;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import pcsd.ModularGraphService;
import pcsd.Result;

/**
 * @author You!
 */
public class BasicModularGraphService implements ModularGraphService {

  private static String host = "localhost";
  private static String name = "modularService";
  private static int port = 1099;
  private static int port2 = 1000;

  private Map<Integer, List<Integer>> graphMap = null;
  
  public static void main(String[] args) {
    parseArgs(args);
  }

  /**
   * Parse commandline arguments.
   * 
   * Instead of mixing this code into the code in main, we use this function
   * to allow easier extension of the number of arguments.
   */
  public static void parseArgs(String[] args) {
    int argc = args.length, i = 0;

    for (; i < argc; i++) {
      if (args[i].equals("--host")) {
        host = args[++i];
      } else if (args[i].equals("--port")) {
        try {
          port = Integer.parseInt(args[++i]);
        } catch (NumberFormatException e) {
          System.out.println("Port must be a valid integer!");
          usage();
        }
      } else if (args[i].equals("--name")) {
        name = args[++i];
      } else if (args[i].equals("-h")) {
        usage();
      }
    }
  }

  /**
   * Print a simple usage message.
   */
  public static void usage() {
    System.out.println("Usage: java <java-class> [options]");
    System.out.println("\t Options:");
    System.out.println("\t --host <url> \t\t The RMI registry host.");
    System.out.println("\t --port <value>\t The RMI registry port.");
    System.out.println("\t --name <value>\t The name of the server.");
    System.out.println("\n");
    System.exit(1);
  }

  /**
   * Add comment.
   */
  @Override
  public int bulkload(String filename) throws RemoteException {
    throw new UnsupportedOperationException();
  }

  /**
   * Add comment.
   */
  @Override
  public Result getConnections(Integer key) throws RemoteException {
    throw new UnsupportedOperationException();
  }
}
