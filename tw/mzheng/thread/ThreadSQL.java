package tw.mzheng.thread;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import com.aragorn.database.LightSQLBean;

public class ThreadSQL {
	
	private static final Logger log = Logger.getLogger(LightSQLBean.class);
	
	private static ResourceBundle resource = 
			ResourceBundle.getBundle("DatabaseConfig");
	
	private static PoolProperties p = new PoolProperties();
	static {
		p.setUrl(String.format(
        		"jdbc:mysql://%s:%s/%s",
        		resource.getString("DBServerIP"),
        		resource.getString("DBServerPort"),
        		resource.getString("DBName")
        	)
        );
        p.setDriverClassName("com.mysql.jdbc.Driver");
        p.setUsername(resource.getString("Account"));
        p.setPassword(resource.getString("Password"));
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(500);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(50);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors(
          "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
          "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
	}
	
	private static DataSource ds;
	
	private Map<Long, List<String>> parametersCache;

	public ThreadSQL() {
		if (ds == null) {
			ds = new DataSource();
			ds.setPoolProperties(p);
		}
		
		//parameters = Collections.synchronizedList(new ArrayList<String>());
		//parameters = new ArrayList<>();
		
		parametersCache = new HashMap<>();
	}

	/**
	 * 
	 * @param parameter
	 */
	public void addParameter(String parameter) {
		
		long threadId = Thread.currentThread().getId();
		
		List<String> parameters = null;
		if (!parametersCache.containsKey(threadId)) {
			parameters = Collections.synchronizedList(new ArrayList<String>());
		} else {
			parameters = parametersCache.get(threadId);
		}
		parameters.add(parameter);
		parametersCache.put(threadId, parameters);
	}

	public static List<HashMap<String, String>> executeQuery(final String sql) {
		Thread currentThread = Thread.currentThread();
		
		// Thread-1, To get connection.
		class GetConnectionThread extends Thread {
			private Connection conn;
			public GetConnectionThread(Connection conn) {
				this.conn = conn;
			}
			@Override
			public void run() {
				try {
					Future<Connection> f = ds.getConnectionAsync();
					this.join(100);		// 讓給其他Thread進行
					if (f.isDone()) {
						conn = f.get();
						System.out.println(Thread.currentThread().getName() 
								+ " get connection.");
					}
					log.debug(this.getName() + " get connection.");
				} catch (SQLException 
						| InterruptedException
						| ExecutionException e) {
					e.printStackTrace();
				}
			}
			public Connection getConnection() {
				return this.conn;
			}
		}
		Connection conn = null;
		GetConnectionThread t1 = new GetConnectionThread(conn);
		t1.start();
		
		try {
			Thread.currentThread().join();	// 停止Main Thread，讓給其他Thread進行
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (!t1.isAlive()) {
			conn = t1.getConnection();
			System.out.println("Connection is close? " + conn.isClosed());
		}
		
		// Thread-2, To get cache data.
		
		List<String> parameters = null;
		final long threadId = Thread.currentThread().getId();
		
		class GetParametersFromCacheThread extends Thread {
			private List<String> parameters;
			@Override
			public void run() {
				// 應該取main thread id的參數，不是目前執行的參數
				//long threadId = Thread.currentThread().getId();
				synchronized (parametersCache) {
					if (parametersCache.containsKey(threadId)) {
						System.out.println("Parameters found in thread-"+threadId);
						parameters = parametersCache.get(threadId);
						//System.out.println(parameters.toString());
					} else {
						System.out.println("No parameters in the cache.");
					}
				}
			}
			public List<String> getParameters() {
				return this.parameters;
			}
		}
		GetParametersFromCacheThread t2 = new GetParametersFromCacheThread();
		t2.start();
		try {
			Thread.currentThread().join(100);	// 停止Main Thread，讓給其他Thread進行
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (!t2.isAlive()) {
			parameters = t2.getParameters();
			System.out.println("parameters: " + parameters.toString());
		}
		
		if (!currentThread.isAlive()) {
			System.out.println(currentThread.getName() 
					+ " is not alive, wake it up.");
			currentThread.notify();
		}
		
		// return value
		LinkedList<HashMap<String, String>> result = new LinkedList<>();
		
		if (!t1.isAlive() & !t2.isAlive()) {
			
			// 開始執行SQL
			PreparedStatement ps = conn.prepareStatement(sql);
			log.debug(ps.toString());
			
			// 參數
			int index = 1;
			for (String parameter : parameters) {
				ps.setString(index, parameter);
				index++;
			}
			
			ResultSet rs = ps.executeQuery();
			
			// 取得欄位
			ResultSetMetaData md = rs.getMetaData();
			List<String> columnnames = new ArrayList<>();
			for (int i = 1; i <= md.getColumnCount(); i++) {
				columnnames.add(md.getColumnName(i));
			}
			
			// return value
			while (rs.next()) {
				//synchronized (result) {
					HashMap<String, String> row = new HashMap<String, String>();
					for (String column : columnnames) {
						row.put(column, rs.getString(column));
					}
					result.add(row);
				//}
			}
			
			// close all resources
			rs.close();
			ps.close();
			conn.close();
			
			// clear argument
			parameters.clear();
			parametersCache.remove(threadId);
			System.out.println("thread-"+threadId+" parameters remove.");
		}
		
		return result;
	}
}
