package tw.mzheng.transaction;

/**
 * 
 * @author Morris
 *
 */
public interface Transaction {
	
	/**
	 * 交易啟動
	 */
	public void begin();
	
	/**
	 * 交易結束
	 */
	public void end();

	/**
	 * 交易序號 Transaction Unique Key
	 * @return long Key
	 */
	long getId();
	
	/**
	 * 加入操作
	 * @param operation
	 */
	public void addOperation(Operation o);
	
	/**
	 * 實作
	 * @return <strong>true</strong>交易寫入成功 | <strong>false</strong>交易寫入失敗
	 */
	public boolean commit();
	
	/**
	 * 返回
	 * @return <strong>true</strong>交易回寫成功 | <strong>false</strong>交易回寫失敗
	 */
	public boolean rollback();
}
