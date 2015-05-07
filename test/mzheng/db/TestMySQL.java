package test.mzheng.db;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.junit.Test;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class TestMySQL {

	@Test
	public void test() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection conn = null;
		try {
			conn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://192.168.1.100:3306/skila", "root", "Password1");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String sql = "SELECT * FROM member limit 0, 10";
		List<String> names = new ArrayList<>();
		try {
			Statement sm = (Statement) conn.createStatement();
			ResultSet rs = sm.executeQuery(sql);
			while (rs.next()) {
				names.add(rs.getString("name"));
			}
			rs.close();
			sm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		names.stream().forEach(System.out::println);
	}

	/*
	 * 測試 commons-dbutils
	 * http://commons.apache.org/proper/commons-dbutils/examples.html
	 */
	@Test
	public void test_DbUtils() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection conn = null;
		try {
			conn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://192.168.1.100:3306/skila", "root", "Password1");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		QueryRunner run = new QueryRunner();
		try {
			run.query(conn, "SELECT * FROM member limit 0, 10",
				new ResultSetHandler<List<String>>() {
					@Override
					public List<String> handle(ResultSet rs)
							throws SQLException {
						List<String> names = new ArrayList<>();
						while (rs.next()) {
							names.add(rs.getString("name"));
						}
						return names;
					}
				}
			).forEach(System.out::println);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
