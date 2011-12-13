package pcsd.modular;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.spi.SyncResolver;

import pcsd.ModularGraphService;
import pcsd.Result;

/**
 * @author You!
 */
public class BasicModularGraphService extends java.rmi.server.UnicastRemoteObject implements ModularGraphService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String host = "localhost";
	private static String name = "modularService";	
	private static int port = 1099;
	private static int port2 = 1000;
	private static Object lock = new Object();

	private Map<Integer, List<Integer>> graphMap = null;
	protected BasicModularGraphService() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}



	public static void main(String[] args) {
		System.out.println("Starting server");
		parseArgs(args);
		try {
			BasicModularGraphService bmgs = new BasicModularGraphService();
			Naming.rebind(name, bmgs);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
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
		synchronized (lock) {
			if(graphMap != null) {
				return -45;
			}
			graphMap = new HashMap<Integer, List<Integer>>();
		}
		try {
			BufferedReader in
			= new BufferedReader(new FileReader(filename));
			String strline;
			while ((strline = in.readLine()) != null) {
				// separate key value
				String delims = "[\t ]+";
				String[] tokens = strline.split(delims);
				int k = Integer.parseInt(tokens[0]);
				int v = Integer.parseInt(tokens[1]);
				if(graphMap.containsKey(k)) {
					graphMap.get(k).add(v);
				} else {
					List<Integer> l = new ArrayList<Integer>();
					l.add(v);
					graphMap.put(k, l);  
				}
			}
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			return -42;
		} catch (IOException e) {
			//e.printStackTrace();
			return -43;
		} catch (OutOfMemoryError e) {
			//e.printStackTrace();
			return -44;
		} catch (NumberFormatException e) {
			//e.printStackTrace();
			return -43;
		}
		return 0; 

	}


	/**
	 * Add comment.
	 */
	@Override
	public Result getConnections(Integer key) throws RemoteException {
		Result r = new Result();
		r.values = new ArrayList<Integer>();
		if(graphMap != null) {
			if(graphMap.containsKey(key)) {
				r.status = 0;
				r.values = graphMap.get(key);
				return r;
			} else{
				// no key
				r.status = -46;
				return r;
			}
		} else {
			// data not loaded
			r.status = -45;
			return r;  
		}
	}
}

