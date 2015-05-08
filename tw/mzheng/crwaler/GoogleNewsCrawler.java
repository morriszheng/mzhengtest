package tw.mzheng.crwaler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class GoogleNewsCrawler implements Runnable {

	private static MysqlDataSource ds = new MysqlDataSource();
	static {
		ds.setUrl("jdbc:MySQL://localhost:3306/test");
		ds.setUser("root");
		ds.setPassword("123qwe");
	}
	private static JdbcTemplate sql = new JdbcTemplate();
	static {
		sql.setDataSource(ds);
	}
	
	public static void main(String[] args) {
		while (true) {
			new Thread(new GoogleNewsCrawler()).start();
			try {
				Thread.sleep(100000);	// 100s
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		Document doc = null;
		try {
			doc = Jsoup.connect("https://news.google.com.tw/").get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String now = format.format(new Date());
		
		List<Pair<String, String>> titles = new ArrayList<Pair<String, String>>();
		for (String title : doc.select(".titletext").text().split(" ")) {
			titles.add(ImmutablePair.of(title, now));
		}
		
		/*
		titles.stream().forEach(t ->
			System.out.println(
				String.format(
						"[%s] %s",
						t.getRight(),
						t.getLeft()
					)
				)
			);
		*/
		
		titles.stream().forEach(t ->
			sql.update(
				"insert into test.news (title, `when`) values (?, ?)",
				t.getLeft(),
				t.getRight()
			)
		);
	}
}
