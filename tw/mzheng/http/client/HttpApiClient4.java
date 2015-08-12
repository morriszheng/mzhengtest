package tw.mzheng.http.client;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Morris
 *
 */
public class HttpApiClient4 {

	private static final Logger log = LoggerFactory
			.getLogger(HttpApiClient4.class);

	/**
	 * 
	 * @param resturl
	 * @return
	 */
	public static String GET(String resturl) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String responseBody = StringUtils.EMPTY;
		try {
			HttpGet httpget = new HttpGet(resturl);
			log.debug("GET {}", resturl);
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity,
								"UTF-8") : null;
					} else {
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}
			};
			try {
				responseBody = httpclient.execute(httpget, responseHandler);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseBody;
	}

	/**
	 * 
	 * @param resturl
	 * @return
	 */
	public static String POST(String resturl) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String responseBody = StringUtils.EMPTY;
		try {
			HttpPost httpget = new HttpPost(resturl);
			log.debug("POST {}", resturl);
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity,
								"UTF-8") : null;
					} else {
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}
			};
			try {
				responseBody = httpclient.execute(httpget, responseHandler);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseBody;
	}
	
	/**
	 * 
	 * @param resturl
	 * @return
	 */
	public static String PUT(String resturl) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String responseBody = StringUtils.EMPTY;
		try {
			HttpPut httpget = new HttpPut(resturl);
			log.debug("POST {}", resturl);
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity,
								"UTF-8") : null;
					} else {
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}
			};
			try {
				responseBody = httpclient.execute(httpget, responseHandler);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseBody;
	}
}
