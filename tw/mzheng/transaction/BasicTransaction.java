package tw.mzheng.transaction;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Morris
 *
 */
public class BasicTransaction implements Transaction {
	
	private Map<Integer, Operation> Operations;
	private long id;
	
	public BasicTransaction() {}

	@Override
	public long getId() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return Long.parseLong(sdf.format(now));
	}

	@Override
	public boolean commit() {
		/*
		 * 需要依照index順序由小到大執行
		 * @see com.pokertown.gift.Transaction#commit()
		 */
		boolean allpast = true;
		Integer[] keys = Operations.keySet().toArray(
				new Integer[Operations.keySet().size()]);
		Arrays.sort(keys);
		for (Integer key : keys) {
			if (!Operations.get(key).go()) {
				allpast = false;
				break;
			}
		}
		return allpast;
	}

	@Override
	public boolean rollback() {
		/*
		 * 需要依據index順序由大到小回覆
		 * @see com.pokertown.gift.Transaction#rollback()
		 */
		boolean allpast = true;
		Integer[] keys = Operations.keySet().toArray(
				new Integer[Operations.keySet().size()]);
		Collections.reverse(Arrays.asList(keys));
		for (Integer key : keys) {
			if (!Operations.get(key).back()) {
				allpast = false;
				break;
			}
		}
		return allpast;
	}

	@Override
	public void addOperation(Operation o) {
		if (Operations.keySet().isEmpty()) {
			Operations.put(1, o);
		} else {
			Integer max = Collections.max(Operations.keySet());
			Operations.put(++max, o);
		}
		o.setId(id);
	}

	@Override
	public void begin() {
		Operations = new HashMap<>();
		id = getId();
	}

	@Override
	public void end() {
		Operations = null;
		id = -1;
	}

}
