package com.sylla.util;

import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.sql.Blob;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class CryptUtil {

	public static final int AES_128_KEYBITS = 128;

	public static final int AES_192_KEYBITS = 192;

	public static final int AES_256_KEYBITS = 256;

	public static final int AES_DEF_KEYBITS = AES_128_KEYBITS;

	public static final int DES_56_KEYBITS = 56;

	public static final int DES_64_KEYBITS = 64;

	public static final int DES_112_KEYBITS = 112;

	public static final int DES_168_KEYBITS = 168;

	public static final int DES_DEF_KEYBITS = DES_64_KEYBITS;

	/**
	 * get bits-length AES key
	 * 
	 * @param key
	 * @param bits
	 * @return
	 * @throws Exception
	 */
	private static Key getAESKey(String key, int bits) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(bits, new SecureRandom(key.getBytes()));
		return kgen.generateKey();
	}

	/**
	 * AES Decryption
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] AESDecrypt(byte[] data, String key) throws Exception {
		return AESDecrypt(data, key, AES_DEF_KEYBITS);
	}

	public static byte[] AESDecrypt(byte[] data, String key, int bits)
			throws Exception {
		return AESDecrypt(data, getAESKey(key, bits));
	}

	public static String AESDecrypt(Blob data, String key, String charSet)
			throws Exception {
		return AESDecrypt(data, key, AES_DEF_KEYBITS, charSet);
	}

	public static String AESDecrypt(Blob data, String key, int bits,
			String charSet) throws Exception {
		return AESDecrypt(data.getBytes((long) 1, (int) data.length()), key, bits,
				charSet);
	}

	public static String AESDecrypt(byte[] data, String key, String charSet)
			throws Exception {
		return AESDecrypt(data, key, AES_DEF_KEYBITS, charSet);
	}

	public static String AESDecrypt(byte[] data, String key, int bits,
			String charSet) throws Exception {
		/**
		 * String r = null; if(data != null) { byte[] b = AESDecrypt(data, key,
		 * bits); if(b != null) r = new String(b, charSet); } return r;
		 */
		byte[] r = AESDecrypt(data, key, bits);
		if (charSet != null && charSet.trim().length() > 0) {
			return new String(r, charSet);
		} else {
			return new String(r);
		}
	}

	private static byte[] AESDecrypt(byte[] data, Key key) throws Exception {
		if (data == null)
			return null;
		/**
		 * byte[] raw = key.getBytes("ASCII"); byte[] rawKey = new byte[bits];
		 * for(int i = 0; i < raw.length && i < rawKey.length; i++) rawKey[i] =
		 * raw[i]; SecretKeySpec skeySpec = new SecretKeySpec(rawKey, 0, bits,
		 * "AES");
		 */
		byte[] raw = key.getEncoded();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		return cipher.doFinal(data);
	}

	/**
	 * AES Encryption
	 * 
	 * @param text
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] AESEncrypt(String text, String key) throws Exception {
		return AESEncrypt(text, key, AES_DEF_KEYBITS);
	}

	public static byte[] AESEncrypt(String text, String key, int bits)
			throws Exception {
		return AESEncrypt(text, key, "", bits);
	}

	public static byte[] AESEncrypt(String text, String key, String charSet)
			throws Exception {
		return AESEncrypt(text, key, charSet, AES_DEF_KEYBITS);
	}

	public static byte[] AESEncrypt(String text, String key, String charSet,
			int bits) throws Exception {
		byte[] data;
		if (charSet != null && charSet.trim().length() > 0) {
			data = text.getBytes(charSet);
		} else {
			data = text.getBytes();
		}
		return AESEncrypt(data, getAESKey(key, bits));
	}

	private static byte[] AESEncrypt(byte[] data, Key key) throws Exception {
		if (data == null)
			return null;
		byte[] raw = key.getEncoded();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		return cipher.doFinal(data);
	}

	/**
	 * get bits-length DES key
	 * 
	 * @param key
	 * @param bits
	 * @return
	 * @throws Exception
	 */
	private static Key getDESKey(byte[] key, int bits) throws Exception {
		return getDESKey1(key, bits);
	}

	private static Key getDESKey1(byte[] key, int bits) throws Exception {
		byte[] k = new byte[8];
		for (int i = 0; i < k.length && i < key.length; i++)
			k[i] = key[i];
		DESKeySpec dks = new DESKeySpec(k);
		SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");
		return kf.generateSecret(dks);
	}

	private static Key getDESKey2(byte[] key, int bits) throws Exception {
		byte[] k = new byte[8];
		for (int i = 0; i < k.length && i < key.length; i++)
			k[i] = key[i];
		return new SecretKeySpec(k, "DES");
	}

	private static Key getDESKey(String key, int bits) throws Exception {
		return getDESKey3(key, bits);
	}

	private static Key getDESKey1(String key, int bits) throws Exception {
		// Security.insertProviderAt(new com.sun.crypto.provider.SunJCE(), 1);
		KeyGenerator kg = KeyGenerator.getInstance("DES");
		kg.init(new SecureRandom());
		return kg.generateKey();
	}

	private static Key getDESKey2(String key, int bits) throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance("DES");
		kg.init(new SecureRandom(key.getBytes()));
		return kg.generateKey();
	}

	private static Key getDESKey3(String key, int bits) throws Exception {
		byte[] b = new byte[8];
		byte[] bk = key.getBytes();
		for (int i = 0; i < b.length && i < bk.length; i++)
			b[i] = bk[i];
		return new SecretKeySpec(b, "DES");
		// return new SecretKeySpec(key.getBytes(), "DES");
	}

	/**
	 * DES Decryption
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] DESDecrypt(byte[] data, byte[] key) throws Exception {
		return DESDecrypt(data, key, DES_DEF_KEYBITS);
	}

	public static byte[] DESDecrypt(byte[] data, byte[] key, int bits)
			throws Exception {
		return DESDecrypt(data, getDESKey(key, bits));
	}

	public static byte[] DESDecrypt(byte[] data, String key) throws Exception {
		return DESDecrypt(data, key, DES_DEF_KEYBITS);
	}

	public static byte[] DESDecrypt(byte[] data, String key, int bits)
			throws Exception {
		return DESDecrypt(data, getDESKey(key, bits));
	}

	private static byte[] DESDecrypt(byte[] data, Key key) throws Exception {
		if (data == null)
			return null;
		byte[] raw = key.getEncoded();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "DES");
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		// Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		return cipher.doFinal(data);
	}

	/**
	 * DES Encryption
	 * 
	 * @param text
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] DESEncrypt(String text, byte[] key) throws Exception {
		return DESEncrypt(text, key, DES_DEF_KEYBITS);
	}

	public static byte[] DESEncrypt(String text, byte[] key, int bits)
			throws Exception {
		return DESEncrypt(text, getDESKey(key, bits));
	}

	public static byte[] DESEncrypt(String text, String key) throws Exception {
		return DESEncrypt(text, key, DES_DEF_KEYBITS);
	}

	public static byte[] DESEncrypt(String text, String key, int bits)
			throws Exception {
		return DESEncrypt(text, getDESKey(key, bits));
	}

	private static byte[] DESEncrypt(String text, Key key) throws Exception {
		if (text == null)
			return null;
		byte[] raw = key.getEncoded();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "DES");
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		// Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		return cipher.doFinal(text.getBytes());
	}

	/**
	 * DES decryption with vector
	 * 
	 * @param data
	 * @param key
	 * @param vec
	 * @return
	 * @throws Exception
	 */
	public static byte[] DESDecrypt(byte[] data, byte[] key, byte[] vec)
			throws Exception {
		Cipher cipher = getCipher(key, vec, Cipher.DECRYPT_MODE);
		return cipher.doFinal(data);
	}

	public static byte[] DESDecrypt(byte[] data, String key, String vec)
			throws Exception {
		Cipher cipher = getCipher(key, vec, Cipher.DECRYPT_MODE);
		return cipher.doFinal(data);
	}

	/**
	 * DES Encryption with vector
	 * 
	 * @param text
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] DESEncrypt(byte[] data, byte[] key, byte[] vec)
			throws Exception {
		Cipher cipher = getCipher(key, vec, Cipher.ENCRYPT_MODE);
		return cipher.doFinal(data);
	}

	public static byte[] DESEncrypt(byte[] data, String key, String vec)
			throws Exception {
		Cipher cipher = getCipher(key, vec, Cipher.ENCRYPT_MODE);
		return cipher.doFinal(data);
	}

	public static byte[] DESEncrypt(String text, byte[] key, byte[] vec)
			throws Exception {
		return DESEncrypt(text.getBytes(), key, vec);
	}

	public static byte[] DESEncrypt(String text, String key, String vec)
			throws Exception {
		return DESEncrypt(text.getBytes(), key, vec);
	}

	private static Cipher getCipher(String key, String vec, int mode)
			throws Exception {
		return getCipher(key.getBytes(), vec.getBytes(), mode);
	}

	private static Cipher getCipher(byte[] key, byte[] vec, int mode)
			throws Exception {
		/*
		 * MessageDigest md = MessageDigest.getInstance("MD5"); md.update(key);
		 * DESKeySpec dks = new DESKeySpec(md.digest());
		 */
		byte[] bkey = new byte[8];
		for (int i = 0; i < bkey.length && i < key.length; i++)
			bkey[i] = key[i];
		byte[] bvec = new byte[8];
		for (int i = 0; i < bvec.length && i < vec.length; i++)
			bvec[i] = vec[i];
		DESKeySpec dks = new DESKeySpec(bkey);
		SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");
		SecretKey sk = kf.generateSecret(dks);
		IvParameterSpec ivps = new IvParameterSpec(bvec);
		AlgorithmParameterSpec aps = ivps;
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		// Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(mode, sk, aps);
		return cipher;
	}

	public static final String Base64Encode(byte[] b) {
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(b);
	}

	public static final byte[] Base64Decode(String s) {
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] b = null;
		try {
			b = decoder.decodeBuffer(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public static final String encode(byte[] data) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
				'b', 'c', 'd', 'e', 'f' };
		try {
			int j = data.length;
			char str[] = new char[j * 2];
			int k = 0;
			byte b;
			for (int i = 0; i < j; i++) {
				b = data[i];
				str[k++] = hexDigits[b >>> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static byte[] MD5(byte[] data) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(data);
		return md.digest();
	}

	public static byte[] SHA(byte[] data) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA");
		md.update(data);
		return md.digest();
	}
}
