package pcsd;

/**
 * This interface represents a Twitter-like who-follows-who service. At a high
 * level, the interface allows users to index the edges of a directed graph, and
 * to obtain vertices adjacent to any given vertex in the graph.
 * 
 * @author vmarcos, vdeurzen
 */
public interface GraphService {

	/**
	 * Loads the graph.
	 * 
	 * @param input
	 *            - the pathname to the file of key-value mappings for the
	 *            graph.
	 * @return see assignment description
	 */
	int bulkload(String filename);

	/**
	 * Obtains all values linked to the given key.
	 * 
	 * @param key
	 *            - the key being queried.
	 * @return see assignment description.
	 */
	Result getConnections(Integer key);
}
