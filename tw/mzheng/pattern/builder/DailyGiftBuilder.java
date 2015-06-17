package tw.mzheng.pattern.builder;

import java.util.Map;
import org.apache.log4j.Logger;
import com.pokertown.member.MemberUtils;

public class DailyGiftBuilder extends GiftBuilder {
	
	private static Logger log = Logger.getLogger(DailyGiftBuilder.class);

	@Override
	public boolean give() throws Exception {
		boolean success = true;
		String toId = "";
		try {
			toId = MemberUtils.getInstance().findAccountId(to);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		double dpoint = 0;
		try {
			dpoint = Double.parseDouble(point);
		} catch (Exception e) {
			throw new Exception();
		}
		
		double toMoney = Double.parseDouble(getUserMoney(toId));
		
		String mToId =  MemberUtils.getInstance().findMemberId(to);
		GiftLog.write(transId, Gift.From.System+"", mToId, point,
			Gift.Type.DailyGift, memo,
			"0", toMoney+"", "0", "0",
			Gift.Status.Fail+""
		);
		Map fromSys = payin(toId, to, point);
		if (!Boolean.parseBoolean((String) fromSys.get("result"))) {
			throw new Exception((String) fromSys.get("message"));
		}
		GiftLog.update(transId, Gift.From.System+"", mToId, point,
			Gift.Type.DailyGift, memo,
			"0", toMoney+"", "0", getUserMoney(toId),
			Gift.Status.Success+"",
			GiftLog.getId(transId, Gift.Type.DailyGift)
		);
		
		return success;
	}

	@Override
	public boolean back() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public static Builder builder() {
		return new DailyGiftBuilder.Builder();
	}

	public static class Builder {
		private GiftBuilder instance = new DailyGiftBuilder();
		
		public Builder() {	}
		
		public Builder setId(long transId) {
			instance.transId = transId;
			return this;
		}
		
		public Builder from(String account) {
			instance.from = account;
			return this;
		}
		
		public Builder to(String account) {
			instance.to = account;
			return this;
		}
		
		public Builder point(String point) {
			instance.point = point;
			return this;
		}
		
		public Builder fee(String point) {
			instance.fee = point;
			return this;
		}
		
		public Builder type(int type) {
			instance.type = type;
			return this;
		}
		
		public Builder memo(String memo) {
			instance.memo = memo;
			return this;
		}
		
		public GiftBuilder build() {
			return instance;
		}
	}
}
