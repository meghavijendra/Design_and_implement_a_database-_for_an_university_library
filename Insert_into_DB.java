package Soccer;

import java.io.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHandler {

	public static void main(String[] args) {
		String path = "C://Users/arpit/OneDrive/Desktop/Proj2_phase2/new_tables/ISSUE_NOTICE.csv";
		String table = "ISSUE_NOTICE";
		String dbname = "LIBRARY";

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			insertIntoDB(path, table, dbname);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	private static void insertIntoDB(String path, String table, String dbname)
			throws SQLException, FileNotFoundException, IOException {
		Connection conn;
		conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbname, "root", "root");
		System.out.println("connected");
		Statement query = (Statement) conn.createStatement();
		FileReader file = new FileReader(path);
		BufferedReader buff = new BufferedReader(file);
		String line = new String();

		while ((line = buff.readLine()) != null) {
			if (line.equals("NEW TABLE")) {
				continue;
			} else {
				System.out.println("inside");
				query.execute("Insert into " + table + " values(" + line + ")");

			}
		}
		conn.close();
		buff.close();
		file.close();
		System.out.println("Inserted into database");
	}

}
