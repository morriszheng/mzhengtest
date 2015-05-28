package test.mzheng.guava;

import java.util.Collection;

import org.junit.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class TestMultimap {

	@Test
	public void test() {
		Multimap<String, String> mmap = ArrayListMultimap.create();

		mmap.put("Employee", "David");
		mmap.put("Employee", "Tom");
		mmap.put("Employee", "Morris");
		mmap.put("City", "Taichung");
		mmap.put("City", "Taipei");

		int size = mmap.size();
		Collection<String> empolyee = mmap.get("Employee");
		Collection<String> city = mmap.get("City");
		mmap.remove("Fruits", "Pear");
		mmap.removeAll("Fruits");
		
		for (String value : mmap.values()) {
			System.out.println(value);
		}
	}

}
