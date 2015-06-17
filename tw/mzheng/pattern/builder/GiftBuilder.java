package tw.mzheng.pattern.builder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.apache.log4j.Logger;
import com.pokertown.api.ApiClient;
import com.pokertown.api.action.GetgetUser;
import com.pokertown.api.action.Payin;
import com.pokertown.api.action.Payout;
import com.pokertown.member.MemberUtils;

/**
 * Created by builder pattern with fluent interface.
 * @author Morris
 *
 */
public abstract class GiftBuilder {

	private static Logger log = Logger.getLogger(GiftBuilder.class);
	
	protected String from;
	protected String to;
	protected String point;
	protected String fee = "0";
	protected String memo = "";
	protected int type = -1;
	protected long transId = -1;
	
	public static String appId = "";
	public static String apiUrl = "";
	
	public String getFrom() {
		return from;
	}
	public String getTo() {
		return to;
	}
	public String getPoint() {
		return point;
	}
	public String getFee() {
		return fee;
	}
	public long getTransId() {
		return transId;
	}
	public int getType() {
		return type;
	}
	public String getMemo() {
		return memo;
	}

	private static final String TRANSACTION_ID_FORMAT = "yyyyMMddHHmmssSSS";

	public static long getId() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(TRANSACTION_ID_FORMAT);
		return Long.parseLong(sdf.format(now));
	}
	
	protected GiftBuilder() { }
	
	public abstract boolean give() throws Exception;
	public abstract boolean back() throws Exception;
	
	protected static String getgetUser(String accountId) throws Exception {
		GetUser getUser = null;
		try {
			GetUser =
				(GetUser) JsonClient.create("GetUser", appId, Url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		getUser.accountId = String.valueOf(accountId);
		String result = "";
		try {
			result = getUser.execute();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		log.debug(result);
		return (String) getUser.toMap(result).get(GetUser.Result.Name);
	}
	
	protected static Map payin(String accountTo, String account,
			String money) throws Exception {
        // hide
		return payin.toMap(result);
	}
	
	protected static Map payout(String accountTo, String account,
			String money) throws Exception {
		// hide
		return payout.toMap(result);
	}
	
	public abstract static class Builder { }
}
