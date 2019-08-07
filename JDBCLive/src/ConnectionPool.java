import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
	
	private static List<Connection>freeConnections = new ArrayList<Connection>();  //can use stack to reduce connection
	private static List<Connection>usedConnections = new ArrayList<Connection>();
	
	private final int MAX_CONNECTIONS = 5;
	
	private final static String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
	private final static String user = "postgres";
	private final static String password = "admin";
	
	public ConnectionPool() throws SQLException {

		for (int count = 0; count <MAX_CONNECTIONS; count++) {
			freeConnections.add(DriverManager.getConnection(url, user, password));
		}
	}
	
	public boolean returnConnection(Connection conn) {
		if (conn != null) {
			usedConnections.remove(conn);
			freeConnections.add(conn);
			return true;
		}
		return false;
	}
	
	public static Connection getConnection() {
		if (freeConnections.size() == 0) {
			System.out.println("All connections are Used !!");
			return null;
		} else {
			Connection conn = 
			freeConnections.remove(
				freeConnections.size() - 1);
			usedConnections.add(conn);
			return conn;
		}
	}
	
	public int getFreeConnectionCount() {
		return freeConnections.size();
	}

	
	public static void main(String[] args) throws SQLException {
		
		ConnectionPool pool = new ConnectionPool();
		Connection conn1 = ConnectionPool.getConnection();
		Connection conn2 = ConnectionPool.getConnection();
		System.out.println(pool.getFreeConnectionCount());
		Connection conn3 = ConnectionPool.getConnection();
		Connection conn4 = ConnectionPool.getConnection();
		Connection conn5 = ConnectionPool.getConnection();
		Connection conn6 = ConnectionPool.getConnection();
		System.out.println(pool.getFreeConnectionCount());
		pool.returnConnection(conn1);
		pool.returnConnection(conn2);
		pool.returnConnection(conn4);
		System.out.println(pool.getFreeConnectionCount());
		}
}