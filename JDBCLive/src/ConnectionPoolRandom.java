import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPoolRandom {
	
	private static Stack<Connection> freeConnections = new Stack<Connection>();  //can use stack to reduce connection
	private static Stack<Connection> usedConnections = new Stack<Connection>();
	
	private final int MAX_CONNECTIONS = 5;
	
	private final static String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
	private final static String user = "postgres";
	private final static String password = "admin";
	
	public ConnectionPoolRandom() throws SQLException {

		for (int count = 0; count <MAX_CONNECTIONS; count++) {
			freeConnections.push(DriverManager.getConnection(url, user, password));
		}
	}
	
	public boolean returnConnection(Connection conn) {
		if (conn != null) {
			usedConnections.pop();
			freeConnections.push(conn);
			return true;
		}
		return false;
	}
	
	public static Connection getConnection() {
		if (freeConnections.size() == 0) {
			System.out.println("No free connections available!");
			return null;
		} else {
			Connection conn = freeConnections.pop();
			usedConnections.push(conn);
			return conn;
		}
	}
	
	public int getFreeConnectionCount() {
		return freeConnections.size();
	}

	
	public static void main(String[] args) throws SQLException {
		
		ConnectionPoolRandom pool = new ConnectionPoolRandom();
		Connection conn1 = ConnectionPoolRandom.getConnection();
		Connection conn2 = ConnectionPoolRandom.getConnection();
		System.out.println(pool.getFreeConnectionCount());
		Connection conn3 = ConnectionPoolRandom.getConnection();
		Connection conn4 = ConnectionPoolRandom.getConnection();
		Connection conn5 = ConnectionPoolRandom.getConnection();
		System.out.println(pool.getFreeConnectionCount());
		Connection conn6 = ConnectionPoolRandom.getConnection();
		System.out.println(pool.getFreeConnectionCount());
		pool.returnConnection(conn1);
		pool.returnConnection(conn2);
		pool.returnConnection(conn4);
		System.out.println(pool.getFreeConnectionCount());
		}
}