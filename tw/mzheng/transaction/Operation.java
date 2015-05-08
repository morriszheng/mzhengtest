package tw.mzheng.transaction;

/**
 * Operation for Transaction
 * 
 * @author Morris
 *
 */
public interface Operation {
	
	public void setId(long id);
	public long getId();
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean go();
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean back();
}
