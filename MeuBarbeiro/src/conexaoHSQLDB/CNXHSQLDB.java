package conexaoHSQLDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CNXHSQLDB {

	private static String usuario = "SA";
	private static String senha = "";
	private static String PathBase = "C:\\Users\\gusta\\eclipse-workspace\\MeuBarbeiro\\src\\base\\hsqldb";
	private static String URL = "jdbc:hsqldb:file:" + PathBase + ";";

	public static Connection conectar() {
		try {
			return DriverManager.getConnection(URL, usuario, senha);
		} catch (SQLException e) {
//			throw new RuntimeException();
			e.printStackTrace();
		}
		return null;
	}

}