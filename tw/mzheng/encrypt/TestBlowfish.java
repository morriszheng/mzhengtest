package tw.mzheng.encrypt;

import static java.lang.System.out;

import org.junit.Test;

public class TestBlowfish {

	@Test
	public void testBlowfish() {
		String key = "HdlSD7f8hjk12";
		String data = "Hello World";
		out.format("data: %s, key: %s", data, key).println();
		try {
			String enc = Blowfish.encrypt(data, key);
			out.format("Encrypt: %s", enc).println();
			String dnc = Blowfish.decrypt(enc, key);
			out.format("Decrypt: %s", dnc).println();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
