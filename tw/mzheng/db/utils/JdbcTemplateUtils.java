package tw.mzheng.db.utils;

import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * 
 * @author morriszheng
 */
public class JdbcTemplateUtils {

	public static boolean LAZY_INITIAL = true;
	
	private static MysqlDataSource ds;
	static {
		ds = new MysqlDataSource();
		ds.setUser("user");
		ds.setPassword("password1");
		ds.setUrl("jdbc:mysql://localhost:3306/dbname");
		try {
			
			// Refer to:
			// https://goo.gl/zb9bRU
			
			// prepStmtCacheSize
			// This sets the number of prepared statements that the MySQL driver
			// will cache per connection. The default is a conservative 25.
			// We recommend setting this to between 250-500.
			ds.setPrepStmtCacheSize(250);
			
			// prepStmtCacheSqlLimit
			// This is the maximum length of a prepared SQL statement that the
			// driver will cache. The MySQL default is 256. In our experience,
			// especially with ORM frameworks like Hibernate, this default is
			// well below the threshold of generated statement lengths.
			// Our recommended setting is 2048.
			ds.setPrepStmtCacheSqlLimit(2048);
			
			// cachePrepStmts
			// Neither of the above parameters have any effect if the cache
			// is in fact disabled, as it is by default. You must set this
			// parameter to true.
			ds.setCachePrepStmts(true);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static JdbcTemplate getInstance() {
		return new JdbcTemplate(ds, LAZY_INITIAL);
	}
}
