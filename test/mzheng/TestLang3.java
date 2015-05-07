package test.mzheng;

import java.io.File;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.SystemUtils;
import org.junit.Test;

public class TestLang3 {

	@Test
	public void test() {
		File home = SystemUtils.getJavaHome();
		System.out.println(home.getPath());

		int random = RandomUtils.nextInt(0, 100);
		System.out.println(random);

		String rstring = RandomStringUtils.random(10);
		System.out.println(rstring);
	}

}
