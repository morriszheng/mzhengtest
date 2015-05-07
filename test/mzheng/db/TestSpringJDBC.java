package test.mzheng.db;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class TestSpringJDBC {

	@Test
	public void test() {
		MysqlDataSource ds = new MysqlDataSource();
		ds.setUrl("jdbc:mysql://192.168.1.100:3306/skila");
		ds.setUser("root");
		ds.setPassword("Password1");

		JdbcTemplate sql = new JdbcTemplate(ds);
		sql.queryForList("SELECT name FROM member limit 0, 10")
			.stream()
			.forEach(r ->
				System.out.println(r.get("name"))
			);
	}

}
