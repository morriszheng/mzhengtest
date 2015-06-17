package tw.mzheng.file;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.common.collect.TreeTraverser;
import com.google.common.io.Files;
import com.myapp.av.entity.Item;
import com.myapp.av.utils.HibernateUtil;

public class FolderNameParser {

	//private static final List<String> items = new ArrayList<>();
	private static final Set<String> items = new HashSet<String>();
	
	public static void main(String[] args) {
		traverser(new File("H://"));
		traverser(new File("I://"));
		writeToDb(items);
	}
	
	private static void traverser(File f) {
		TreeTraverser<File> traverser = Files.fileTreeTraverser();
		traverser.children(f).forEach(new Consumer<File>() {
			@Override
			public void accept(File t) {
				String name = t.getName();
				if (!name.startsWith("$"))
					items.add(name);
			}
		});
	}

	private static void writeToDb(Set<String> i) {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		for (String item : i) {
			Item it = new Item();
			it.setName(item);
			try {
				//Entitys.save(actor);
				session.save(it);
				session.flush();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
        session.clear();
		session.getTransaction().commit();
		session.close();
	}
}
