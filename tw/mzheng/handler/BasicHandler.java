package tw.mzheng.handler;

import java.util.ResourceBundle;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class BasicHandler<T> implements Handler<T> {
	
	// 初始化 DataSource
	private static final ResourceBundle config =
			ResourceBundle.getBundle("config.Database");
	
	// 使用MySQL官方提供的DataSource程式，裡面有更多的選項可調整 (例如: cache)
	static MysqlDataSource ds;
	static {
		ds = new MysqlDataSource();
		//ds.setUrl("jdbc:mysql://192.168.0.13:3306/dbname");
		//ds.setUser("user");
		//ds.setPassword("password");
		ds.setUrl(
				String.format("jdbc:mysql://%s:%s/%s",
				config.getString("DBServerIP"),	
				config.getString("DBServerPort"),
				config.getString("DBName")
			)
		);
		ds.setUser(config.getString("Account"));
		ds.setPassword(config.getString("Password"));
		
		// 參考自:
		// https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration
		
		// This sets the number of prepared statements that the MySQL driver
		// will cache per connection. The default is a conservative 25.
		// We recommend setting this to between 250-500.
		ds.setPreparedStatementCacheSize(250);
		
		// This is the maximum length of a prepared SQL statement that the
		// driver will cache. The MySQL default is 256. In our experience,
		// especially with ORM frameworks like Hibernate, this default is well
		// below the threshold of generated statement lengths. Our recommended
		// setting is 2048.
		ds.setPreparedStatementCacheSqlLimit(1024);
		
		// Neither of the above parameters have any effect if the cache is in
		// fact disabled, as it is by default. You must set this parameter to
		// true.
		ds.setCachePreparedStatements(true);
	}
	
	protected JdbcTemplate jdbc = new JdbcTemplate(ds);

	public T before(String... strings) {
		return null;
	}

	public T execute(String... strings) {
		return null;
	}

	public T after(String... strings) {
		return null;
	}

}
