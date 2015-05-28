package tw.mzheng.db;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.bkk.utils.HibernateUtil;

/**
 * 
 * @author Morris
 *
 */
public class Entitys {

	private static SessionFactory factory = HibernateUtil.getSessionFactory();
	
	/**
	 * 
	 * @param t
	 */
	public static <T> void save(T t) {
		//SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		
		session.save(t);
		
		session.getTransaction().commit();
		session.close();
		//HibernateUtil.closeAllResources();
	}
	
	/**
	 * 取得table內的資料
	 * @param c Table對應的Bean class
	 * @return
	 */
	public static <T> List<T> getList(Class<?> c) {
		//SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();

		@SuppressWarnings("unchecked")
		List<T> result = session.createQuery("from " + c.getName()).list();

		session.getTransaction().commit();
		session.close();
		//HibernateUtil.closeAllResources();
		
		return result;
	}
	
	/**
	 * 以HQL取得table內的資料
	 * @param hql
	 * @param classes
	 * @return
	 */
	public static <T> List<T> get(String hql, List<Pair<String, String>> argus) {
		//SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();

		Query q = session.createQuery(hql);
		for (Pair<String, String> p : argus) {
			q.setParameter(p.getLeft(), p.getRight());
		}
		@SuppressWarnings("unchecked")
		List<T> result = q.list();

		session.getTransaction().commit();
		session.close();
		//HibernateUtil.closeAllResources();
		
		return result;
	}

	/**
	 * 
	 */
	public static void close() {
		HibernateUtil.closeAllResources();
	}
}
