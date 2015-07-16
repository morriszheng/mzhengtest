package tw.mzheng.script.comic;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class DmzjCrawler {

	//private static final String Volumn = "001";
	private static final String Volumn = "%E7%AC%AC02%E8%AF%9D";
	private static final String FirstPage = "001";
	private static final String LastPage = "080";
	private static final String Pattern =
			"http://images.dmzj.com/o/overload/%s/overload02_%s.jpg";
	
	/* 每隔 5 秒抓取一次 */
	private static final long SleepTime = 5000;
	
	public static void main(String[] args) {
		Integer start = Integer.parseInt(FirstPage);
		Integer last = Integer.parseInt(LastPage);
		for (int i = start; i <= last; i++) {
			String targetLink = String.format(
				Pattern,
				Volumn,
				getPageIndex(i)
			);
			System.out.println(targetLink);
			try {
				String fileName = targetLink.split("/")[6];
				saveToFile(targetLink, fileName);
				Thread.sleep(SleepTime);
			} catch (Exception e) {
				System.err.println(targetLink+" not found.");
			}
		}
	}

	private static String getPageIndex(Integer i) {
		if (i >= 1 && i < 10)
			return "00" + i;
		else if (i >= 10 && i < 100)
			return "0" + i;
		else
			return ""+i;
	}
	
	private static void saveToFile(String url, String fileName)
			throws Exception {
		URL u = new URL(url);
		InputStream is = u.openStream();
		OutputStream os = new FileOutputStream(fileName);
		
		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}
}
