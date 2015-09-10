package tw.mzheng.json;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author Morris
 *
 */
public class JsonRawResultAccessor implements RawResultAccessor {
	
	private Gson gson;
	private Map<Object, Object> rawMap;
	private String rawData;
	
	public JsonRawResultAccessor(String rawData) {
		this.gson = new Gson();
		if (StringUtils.isNoneBlank(rawData)) {
			this.rawData = rawData;
			this.rawMap = this.gson.fromJson(this.rawData,
					TypeToken.get(LinkedHashMap.class).getType());
		}
	}
	
	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	@Override
	public <T> T get(String name, Class<T> type) {
		try {
			return type.cast(this.rawMap.get(name));
		} catch (Exception e) {
			return null;
		}
	}
}
