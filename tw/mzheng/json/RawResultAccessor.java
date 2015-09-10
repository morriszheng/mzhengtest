package tw.mzheng.json;

public interface RawResultAccessor {

	/**
	 * 存取 JSON 內部的値
	 * @param name JSON 名稱
	 * @param type 取出後的格式
	 * @return
	 */
	public <T> T get(String name, Class<T> type);
}
