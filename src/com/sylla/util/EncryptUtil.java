package com.sylla.util;

public class EncryptUtil {

	private static final String KEY = "zxcvbnm,./asdfghjkl;'qwertyuiop[]\\1234567890-=` ZXCVBNM<>?:LKJHGFDSAQWERTYUIOP{}|+_)(*&^%$#@!~";

	// private static final String KEY = "1234567890ABCDEF";

	/**
	 * decrypt to double
	 * 
	 * @param s
	 * @return
	 */
	public static double decryptToDouble(String s) {
		double d = 0;
		try {
			d = Double.parseDouble(decrypt(s));
		} catch (Exception e) {
		}
		return d;
	}

	/**
	 * decrypt to string
	 * 
	 * @param s
	 * @return
	 */
	public static String decrypt(String s) {
		String r = "";
		try {
			if (s == null || s.length() == 0)
				return r;
			if (s.length() % 2 == 1)
				s = s + "?";
			String s1 = "";
			int i = Math.round(s.length() / 2 - 1);
			int j = 0;
			while (j <= i) {
				char c = s.charAt(j * 2);
				int i1 = KEY.indexOf(s.charAt(j * 2 + 1));
				int j1 = c ^ i1;
				char c2 = (char) j1;
				s1 = s1 + c2;
				j++;
			}
			char c1 = 1;
			int k = s1.indexOf(c1);
			if (k >= 0) {
				r = s1.substring(0, k);
			} else {
				r = s1;
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return r;
	}

	/**
	 * encrypt
	 * 
	 * @param s
	 * @return
	 */
	public static String encrypt(String s) {
		String r = "";
		try {
			if (s == null || s.length() == 0)
				return r;
			String s1 = "";
			int j = 1;
			char c = 1;
			for (; s.length() < 8; s = s + c)
				;
			while (j <= s.length()) {
				char c1 = s.charAt(j - 1);
				int k;
				char c2;
				do {
					double d = Math.random();
					for (k = (int) Math.round(d * 100.0); k > 0
							&& ((k ^ c1) < 0 || (k ^ c1) > 90); k--)
						;
					c2 = (char) k;
				} while (k <= 35 || k >= 122 || c2 == '|' || c2 == '\'' || c2 == ','
						|| KEY.charAt(k ^ c1) == '|' || KEY.charAt(k ^ c1) == '\''
						|| KEY.charAt(k ^ c1) == ',');
				s1 = s1 + c2 + KEY.charAt(k ^ c1);
				j++;
			}
			r = s1;
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return r;
	}

}
