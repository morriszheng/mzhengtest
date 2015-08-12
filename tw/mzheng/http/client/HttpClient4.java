package tw.mzheng.http.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Morris
 *
 */
public class HttpClient4 {

	private static final Logger log =
			LoggerFactory.getLogger(HttpClient4.class);
	
	public static String SCHEME = "http";
	public static String HOST = "";
	public static Integer PORT = 80;
	
	/**
	 * Execute Http Post
	 * @param path
	 * @param argus
	 * @return
	 */
	public static String POST(final String path,
			final List<NameValuePair> argus) {
		try {
			URI uri = getUri(path, argus);
			log.debug("[{}] {}", path, uri.toString());
			return HttpClients.createDefault().execute(
				new HttpPost(uri),
				new BasicResponseHandler()
			);
		} catch (Exception e) {
			log.error("No response from {}", e.getMessage());
			return StringUtils.EMPTY;
		}
	}
	
	/**
	 * Execute Http Get
	 * @param path
	 * @param argus
	 * @return
	 */
	public static String GET(final String path,
			final List<NameValuePair> argus) {
		try {
			URI uri = getUri(path, argus);
			log.debug("[{}] {}", path, uri.toString());
			return HttpClients.createDefault().execute(
				new HttpGet(uri),
				new BasicResponseHandler()
			);
		} catch (Exception e) {
			log.error("No response from {}", e.getMessage());
			return StringUtils.EMPTY;
		}
	}
	
	/**
	 * Execute Http Put
	 * @param path
	 * @param argus
	 * @return
	 */
	public static String PUT(final String path,
			final List<NameValuePair> argus) {
		try {
			URI uri = getUri(path, argus);
			log.debug("[{}] {}", path, uri.toString());
			return HttpClients.createDefault().execute(
				new HttpPut(uri),
				new BasicResponseHandler()
			);
		} catch (Exception e) {
			log.error("No response from {}", e.getMessage());
			return StringUtils.EMPTY;
		}
	}
	
	public static URI getUri(String path, List<NameValuePair> nvps)
			throws URISyntaxException {
		return new URIBuilder()
				.setScheme(SCHEME)
				.setHost(HOST)
				.setPort(PORT)
				.setPath("/" + path)
				.setParameters(nvps)
				.build();
	}
}