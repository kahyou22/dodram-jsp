package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class DBConnectionMgr {

	private static final DBConfig config = DBConfig.getInstance();

	private static final String URL = config.get("jdbc:mysql://localhost:3306/testdb?"
    		+ "useSSL=false"
    		+ "&serverTimezone=UTC&"
    		+ "allowPublicKeyRetrieval=true&"
    		+ "characterEncoding=UTF-8");
	private static final String USERNAME = config.get("minho");
	private static final String PASSWORD = config.get("zjajj899");

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}

	public static Map<String, String> getSourceInfo() {
		return config.getSourceInfo();
	}

	public static String testConnection() {
		try (Connection conn = getConnection()) {
			return "성공 (" + conn.getMetaData().getDatabaseProductName() + " "
					+ conn.getMetaData().getDatabaseProductVersion() + ")";
		} catch (SQLException e) {
			return "실패: " + e.getMessage();
		}
	}
}
