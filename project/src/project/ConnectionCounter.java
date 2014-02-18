package project;
/**
 * A counter to count the number of current connections and all the connections,
 * i.e. connecting clients and having connected clients.
 * 
 * @author <a href="mailto:Y.Li38@student.liverpool.ac.uk">Li Yiming</a>
 * @version 2.0
 * 
 */
public class ConnectionCounter {
	
	/* ------------------ Fields ------------------------ */
	
	/**
	 * To initialise, it should be 0.
	 */
	private int connections = 0;

	/**
	 * To initialise, it should be 0.
	 */
	private int currentConnections = 0;

	/* ------------------ Methods ------------------------ */
	
	/**
	 * Get all the connections of clients.
	 * 
	 * @return the integer for all the connections of clients
	 */
	public int getConnections() {
		return connections;
	}

	/**
	 * Get the current connections of clients.
	 * 
	 * @return the integer for current connections of clients
	 */
	public int getCurrentConnections() {
		return currentConnections;
	}

	/**
	 * Both connections increase by 1.
	 */
	public void addConnection() {
		connections++;
		currentConnections++;
	}

	/**
	 * Current connection decrease by 1.
	 */
	public void endConnection() {
		currentConnections--;
	}
}
