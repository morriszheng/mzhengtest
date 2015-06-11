package tw.mzheng.event;

import com.aragorn.crm.v2.handler.Handler;

public interface Event<T extends Handler<V>, V> {
	public V run(T handler, String... strings);
}
