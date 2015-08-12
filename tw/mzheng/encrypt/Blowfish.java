package tw.mzheng.encrypt;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Morris
 * Key size of Blowfish must be 32 - 448 bit. So it is necessary to make a byte
 * array according to the bit number (4 byte for 32 bit) and vice-verse.
 * 
 * The unlimited strength file must install first.
 * JDK6: http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html
 * JDK7: http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html
 * JDK8: http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html
 * 
 * Install the file in ${java.home}/jre/lib/security/.
 */
public class Blowfish {

	// Block cipher: Blowfish can operate in "ECB," "OFB," "CBC," and "CFB"
	// Blowfish/CFB/NoPadding
	// Blowfish/OFB/NoPadding
	// Blowfish/CFB/PKCS5Padding
	// Blowfish/ECB/PKCS5Padding (No IvParameters, and it's insecure)
	// Blowfish/CBC/PKCS5Padding
	// @Morris
	private final static String ALGORITM_TYPE = "Blowfish";
	private final static String ALGORITM = "Blowfish/CFB/PKCS5Padding";
	private static final String LANGUAGE = "UTF-8";
	private static final String RANDOM_TYPE = "SHA1PRNG";
	
	public static String encrypt(final String data, final String key)
			throws Exception {
		//return execute(data, key, Cipher.ENCRYPT_MODE, ALGORITM_TYPE);
		
		Cipher cipher = Cipher.getInstance(ALGORITM);
		SecretKey sk = new SecretKeySpec(key.getBytes(), ALGORITM_TYPE);
		
		byte[] iv = new byte[cipher.getBlockSize()];
		new SecureRandom().nextBytes(iv);
		IvParameterSpec IvParameters = new IvParameterSpec(iv);

		cipher.init(Cipher.ENCRYPT_MODE, sk, IvParameters);
		
		byte[] data64 = cipher.doFinal(data.getBytes(LANGUAGE));
		return Base64.encodeBase64String(data64);
	}
	
	public static String decrypt(final String data, final String key)
			throws Exception {
		//return execute(data, key, Cipher.DECRYPT_MODE, ALGORITM_TYPE);
		
		Cipher cipher = Cipher.getInstance(ALGORITM);
		SecretKey sk = new SecretKeySpec(key.getBytes(), ALGORITM_TYPE);
		
		byte[] iv = new byte[cipher.getBlockSize()];
		new SecureRandom().nextBytes(iv);
		IvParameterSpec IvParameters = new IvParameterSpec(iv);

		cipher.init(Cipher.DECRYPT_MODE, sk, IvParameters);
		
		byte[] data64 = Base64.decodeBase64(data);
		return new String(cipher.doFinal(data64), LANGUAGE);
	}
	
	private static String execute(final String data, final String key,
			final int mode, final String type) throws Exception {
		Cipher cipher = Cipher.getInstance(ALGORITM);
		SecretKey sk = new SecretKeySpec(key.getBytes(), type);
		
		SecureRandom rnd = new SecureRandom();
		byte[] iv = new byte[cipher.getBlockSize()];
	    rnd.nextBytes(iv);
		IvParameterSpec IvParameters = new IvParameterSpec(iv);

		cipher.init(mode, sk, IvParameters);
		return Base64.encodeBase64String(
				cipher.doFinal(data.getBytes())
		);
	}
}
