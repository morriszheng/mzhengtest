package tw.mzheng.http.client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class EasyJson {

	private static final Logger log = LoggerFactory.getLogger(EasyJson.class);

	/**
	 * 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static JsonObject get(String url) throws ClientProtocolException,
			IOException {
		return execute(new HttpGet(url));
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static JsonObject post(String url) throws ClientProtocolException,
			IOException {
		return execute(new HttpPost(url));
	}
	
	private static JsonObject execute(HttpUriRequest uri)
			throws ClientProtocolException, IOException {
		return HttpClients.createDefault().execute(
			uri,
			new ResponseHandler<JsonObject>() {
				@Override
				public JsonObject handleResponse(final HttpResponse response)
						throws IOException {
					StatusLine statusLine = response.getStatusLine();
					HttpEntity entity = response.getEntity();
					if (statusLine.getStatusCode() >= 300) {
						throw new HttpResponseException(
								statusLine.getStatusCode(),
								statusLine.getReasonPhrase());
					}
					if (entity == null) {
						throw new ClientProtocolException(
								"Response contains no content");
					}
					Gson gson = new GsonBuilder().create();
					ContentType contentType = ContentType.getOrDefault(entity);
					Charset charset = contentType.getCharset();
					Reader reader = new InputStreamReader(entity.getContent(),charset);
					return gson.fromJson(reader, JsonObject.class);
				}
			});
	}
}
