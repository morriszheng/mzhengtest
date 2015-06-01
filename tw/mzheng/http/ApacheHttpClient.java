package tw.mzheng.http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class ApacheHttpClient {

	public static String getHtml(String url) throws
			ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = client.execute(httpGet);
		// System.out.println(httpResponse.getStatusLine());

		HttpEntity entity = response.getEntity();

		// If the response does not enclose an entity, there is no need
		// to bother about connection release
		StringBuffer html = new StringBuffer();
		byte[] buffer = new byte[1024];
		if (entity != null) {
			InputStream inputStream = entity.getContent();
			try {
				int bytesRead = 0;
				BufferedInputStream bis = new BufferedInputStream(inputStream);
				while ((bytesRead = bis.read(buffer)) != -1) {
					String chunk = new String(buffer, 0, bytesRead);
					//System.out.println(chunk);
					html.append(chunk);
				}
			} catch (IOException ioException) {
				// In case of an IOException the connection will be released
				// back to the connection manager automatically
				ioException.printStackTrace();
			} catch (RuntimeException runtimeException) {
				// In case of an unexpected exception you may want to abort
				// the HTTP request in order to shut down the underlying
				// connection immediately.
				httpGet.abort();
				runtimeException.printStackTrace();
			} finally {
				// Closing the input stream will trigger connection release
				try {
					inputStream.close();
				} catch (Exception ignore) {
				}
			}
		}
		return html.toString();
	}
}
