package tw.mzheng.handler.impl;

import java.util.List;
import java.util.Map;

import com.aragorn.crm.v2.handler.BasicHandler;

public class LoginHandler extends BasicHandler<List<Map<String, Object>>> {

	@Override
	public List<Map<String, Object>> execute(String... strings) {
		super.execute(strings);
		
		// ------------------------------------------------------------------
		// legacy code
		// ------------------------------------------------------------------
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("    A.ID ");
		sql.append("  , B.ID AS SaleMember_ID");
		sql.append("  , D.Level						AS Level ");
		sql.append("  , A.NickName ");
		sql.append("  , A.Account ");
		sql.append("  , A.Cate_SaleKinds_ID ");
		sql.append("  , C.Chinese_Name				AS SalekindsName ");
		sql.append("  , A.Cate_Examine_ID           AS examineId ");
		sql.append("  , A.Cate_Status_ID            AS statusId ");
		sql.append("FROM ");
		sql.append("  MANAGEACCOUNT AS A ");
		sql.append("    LEFT JOIN Sale_MEMBER  AS B ");
		sql.append("    ON A.ID = B.MANAGEACCOUNT_ID ");
		sql.append("    LEFT JOIN Cate_SaleKinds  AS C ");
		sql.append("    ON A.Cate_SaleKinds_ID = C.ID ");
		sql.append("    LEFT JOIN Sale_HoldMember  AS D ");
		sql.append("    ON B.ID = D.Sale_Member_ID ");
		sql.append("WHERE ");
		sql.append("      A.ACCOUNT  = ? ");
		sql.append("  AND A.PASSWORD = ?");
		sql.append("  AND B.ID IS NOT NULL ");
		sql.append("  AND A.FLAG = 1 ");
		// ------------------------------------------------------------------
		
		return jdbc.queryForList(sql.toString(),
				new Object[] {
					strings[0],
					strings[1]
				}
		);
	}
}
