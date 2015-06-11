package tw.mzheng.event;

import com.aragorn.crm.v2.handler.BasicHandler;

public class BasicEvent<V> implements Event<BasicHandler<V>, V> {

	@Override
	public V run(BasicHandler<V> handler, String... strings) {
		handler.before(strings);
		V result = handler.execute(strings);
		handler.after(strings);
		return result;
	}

}
