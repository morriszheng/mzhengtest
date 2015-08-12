package com.aragorn.thirdparty.blueisland.sport.client;

import gnu.crypto.cipher.Blowfish;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GNUBlueIslandEncryptor {

	private static final Logger log =
			LoggerFactory.getLogger(GNUBlueIslandEncryptor.class);
	
	private static final String PUBLIC_KEY = "SCKEYhjshgsdgy37GHhsd567";
	private static final String PRIVATE_KEY = "0099HJSmsd4hgJHSYGhfbhBDJDFBJD8890";
	private static final String PASS_ACCESS_KEY = "DSetsvdgfh";

	private static String encryptByGNU(String data, String key)
			throws InvalidKeyException, UnsupportedEncodingException {
		byte[] plainText;
		byte[] encryptedText;
		Blowfish blowfish = new Blowfish();
		// create a key
		byte[] keyBytes = key.getBytes();
		Object keyObject = blowfish.makeKey(keyBytes, 8);
		// make the length of the text a multiple of the block size
		if ((data.length() % 8) != 0) {
			while ((data.length() % 8) != 0) {
				data += " ";
			}
		}
		// initialize byte arrays for plain/encrypted text
		plainText = data.getBytes();
		encryptedText = new byte[data.length()];
		// encrypt text in 8-byte chunks
		for (int i = 0; i < plainText.length; i += 8) {
			blowfish.encrypt(plainText, i, encryptedText, i, keyObject, 8);
		}
		String encryptedString = Base64.encodeBase64String(encryptedText);
		return encryptedString;
	}

	public static class WithPassAccessKey {

	}

	public static class WithAccountCode {

		 public static String encrypt(final String accountCode, final String
		 data) throws Exception {
			 
			 log.debug("OR: {}", data);
			 String level1 =  encryptByGNU(data, PRIVATE_KEY)+"^"+accountCode;
			 log.debug("1st level data: {}", level1);
			 String level2 =  encryptByGNU(level1, PUBLIC_KEY);
			 log.debug("2nd level data: {}", level2);
			 
			 return level2;
		 }
	}
}
