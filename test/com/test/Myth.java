package com.test;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.sylla.util.CryptUtil;

public class Myth {

	// private static final String DECRYPT_TEXT = "58226869";
	// private static final String ENCRYPT_TEXT =
	// "A4026BCF76877B3E323BFED253803419";
	private static final String DECRYPT_TEXT = "215000";

	private static final String ENCRYPT_TEXT = "AE2FE3DC01590DB1";

	private static final byte[] ENCRYPT_BYTES = hex2bytes(ENCRYPT_TEXT);

	private static final byte[] EMPTY_BYTES = {};

	/**
	 * convert hex string to byte array
	 * 
	 * @param s
	 * @return
	 */
	private static byte[] hex2bytes(String s) {
		if (s == null)
			return EMPTY_BYTES;
		String b;
		byte[] bs = new byte[s.length() / 2];
		for (int i = 0; i < bs.length; i++) {
			b = s.substring(i * 2, i * 2 + 2);
			bs[i] = (byte) Integer.valueOf(b, 16).intValue();
		}
		return bs;
	}

	/**
	 * convert byte array to hex string
	 * 
	 * @param bs
	 * @return
	 */
	private static String bytes2hex(byte[] bs) {
		if (bs == null)
			return "";
		StringBuffer sb = new StringBuffer();
		String s;
		for (int i = 0; i < bs.length; i++) {
			s = Integer.toHexString(bs[i]);
			if (s.length() > 6)
				s = s.substring(6);
			s = s.toUpperCase();
			if (s.length() < 2)
				sb.append('0');
			sb.append(s);
		}
		return sb.toString();
	}

	/**
	 * convert a long to byte array
	 * 
	 * @param l
	 * @return
	 */
	private static byte[] long2bytes(long l) {
		byte[] b = new byte[8];
		b[0] = (byte) (l >> 56);
		b[1] = (byte) (l >> 48);
		b[2] = (byte) (l >> 40);
		b[3] = (byte) (l >> 32);
		b[4] = (byte) (l >> 24);
		b[5] = (byte) (l >> 16);
		b[6] = (byte) (l >> 8);
		b[7] = (byte) (l >> 0);
		return b;
	}

	/**
	 * 421A045E02A31D431A2D34583D76C8E2 <==> 3205940000055880
	 * 0D1E26972F947F3EB8897A58E7AC9220630BAFD90570CD51 <==> ���ݾr���ó���޹�˾
	 * 12C7B1080DE5C691 <==> ̷��
	 * 5C7E013A74BEED9A9842B96DB11D1620E659BC26FCB29DAAA8F93C1ABE074168 <==>
	 * ������ƽ����ټ���8�ţ�1212�ң� 7A5559953F5E2542E980779A8B10F6F7 <==> 50 ��Ԫ
	 * F4CA463AA7D76572EAB498C628494E5E <==> 13092676002 AE2FE3DC01590DB1 <==>
	 * 215000 8C767C496A6B59F8FB0EBAE76246358615FF44E5BCC7D258 <==>
	 * ��֯����װ������Ʒ��
	 * CE1FEA245EF302EF398C8018476E8D1D0CB29257629449ED9282F04E67992324F14480877E258E0E
	 * <==> ���ݹ������������ƽ���־�¦�Ź����� FB50048E1F692BC34D11C3719F9AF749 <==>
	 * 37909 CE1FEA245EF302EF398C8018476E8D1D0CB29257629449ED407ED8EFFA9704A6 <==>
	 * ���ݹ������������ƽ���־� 2920ECE5A3AF67CC666DB3D888EC866B495C2CF74D9E5731
	 * <==> ���޹�˾(��Ȼ�˿ع�)
	 */
	static void test1() throws Exception {
		String s1 = "0D1E26972F947F3EB8897A58E7AC9220630BAFD90570CD51";
		String s2 = "���ݾr���ó���޹�˾";
		String s3 = "CBD5D6DDBEC1B2FDC9CCC3B3D3D0CFDEB9ABCBBE";
		String s4 = new String(s2.getBytes("UTF-8"));
		System.out.println("s1 = " + s1 + "[" + s1.length() + "]");
		System.out.println("s3 = " + s3 + "[" + s3.length() + "]");
		System.out.println("s4 = " + s4 + "[" + s4.length() + "]");
		System.out.println(URLEncoder.encode(s2, "UTF-8"));
		System.out.println(URLEncoder.encode(s4, "UTF-8"));
		// 000011010001111000100110
		// 110010111101010111010110
	}

	/**
	 * test hex2bytes & bytes2hex functions
	 * 
	 * @throws Exception
	 */
	static void test2() throws Exception {
		byte[] b = { -66, 88 };
		System.out.println(bytes2hex(b));
		String s = "BE58";
		b = hex2bytes(s);
		for (int i = 0; i < b.length; i++)
			System.out.print(b[i] + ", ");
		System.out.println();
	}

	static void test3() throws Exception {
		/**
		 * AE2FE3DC01590DB1 215000 E188DCA85647103F 215006
		 * EEE84D7F2C9B409C2E3C6102F038D0B2D29A76258BCD7B84 ��������ֵ�010170��
		 * EEE84D7F2C9B409CEE0BCBF8944F5CCC895EF5A1D33C6CD5 ��������ֵ�010056��
		 * 
		 * F2E8403D5DACC0BE 215127 0F1AD0B3D22A3C73 215159 A702C074895C696D δ��д
		 * DD0907163BEAB88E 215600 4D977A4F61F5A221 215633
		 */
		String s = "E188DCA85647103F";
		String key = "hilor";
		// System.out.println(toString(CryptUtil.AESEncrypt("215000", key)));
		// System.out.println(toString(CryptUtil.AESEncrypt("215006", key)));
		String[] ss = { "t", "215000", "215006", "��������ֵ�010170��",
				"��������ֵ�010056��" };
		for (int i = 0; i < ss.length; i++) {
			System.out.println(ss[i] + "==>"
					+ bytes2hex(CryptUtil.DESEncrypt(ss[i], key)));
			// System.out.println(ss[i] + "==>" +
			// CryptUtil.Base64Encode(CryptUtil.DESEncrypt(ss[i], key)));
			// System.out.println(ss[i] + "==>" + CryptUtil.MD5(ss[i]));
		}
	}

	static void test4() throws Exception {
		byte[] key = new byte[8];
		key = long2bytes(6666);
		System.out.println(bytes2hex(CryptUtil.DESEncrypt(DECRYPT_TEXT, key)));
		System.out.println(new String(CryptUtil.DESDecrypt(
				hex2bytes("69DFBA56E55492E57CFC7E3E5B81CA29"), key)));
		System.out.println(bytes2hex(CryptUtil.DESEncrypt(DECRYPT_TEXT, "hilor")));
		System.out.println(new String(CryptUtil.DESDecrypt(
				hex2bytes("4B8480FF5E3777B281ECF06918E2CD5F"), "hilor")));
	}

	/**
	 * try all keys to find key, too many keys, impossible
	 * 
	 * @throws Exception
	 */
	static void test5() throws Exception {
		byte[] key = new byte[8];
		for (long l = Long.MIN_VALUE; l <= Long.MAX_VALUE; l++) {
			key = long2bytes(l);
			if (ENCRYPT_TEXT
					.equals(bytes2hex(CryptUtil.DESEncrypt(DECRYPT_TEXT, key)))) {
				System.out.println(bytes2hex(key));
			}
		}
		System.out.println("DONE!!!");
	}

	/**
	 * just test how much time need to loop all long values
	 * 
	 * @throws Exception
	 */
	static void test6() throws Exception {
		Date b = new Date();
		for (long l = Long.MIN_VALUE; l < Long.MAX_VALUE; l++) {
			// if ((l & 0x0100000000000000L) == 0x0100000000000000L)
			// System.out.print(".");
		}
		Date e = new Date();
		long u = (e.getTime() - b.getTime()) / 1000;
		System.out.println("\nUsed " + u + " seconds!!!");
	}

	/**
	 * test AES 128, 192, 256 bits key encryption & decryption
	 * 
	 * @throws Exception
	 */
	static void test7() throws Exception {
		String key = "test";
		int[] bits = { CryptUtil.AES_128_KEYBITS, CryptUtil.AES_192_KEYBITS,
				CryptUtil.AES_256_KEYBITS };
		byte[] r;
		for (int i = 0; i < bits.length; i++) {
			r = CryptUtil.AESEncrypt(DECRYPT_TEXT, key, bits[i]);
			System.out.println(bytes2hex(r));
			System.out.println(new String(CryptUtil.AESDecrypt(r, key, bits[i])));
		}
		System.err.println(ENCRYPT_TEXT);
	}

	/**
	 * test java DES encryption & decryption with C#/C++, with key & vector, it's
	 * same
	 * 
	 * @throws Exception
	 */
	static void test8() throws Exception {
		String key = "ABCDEFGH";
		String vec = "ABCDEFGH";
		String d = "13988888888";
		String e = "BHFtXIFNMTKfE+QcKrxL9w==";
		byte[] r = CryptUtil.DESEncrypt(d, key, vec);
		System.out.println(d + "==>" + CryptUtil.Base64Encode(r));
		System.out.println(new String(CryptUtil.DESDecrypt(r, key, vec)));
		System.out
				.println(e
						+ "==>"
						+ new String(CryptUtil.DESDecrypt(CryptUtil.Base64Decode(e), key,
								vec)));
		d = "test";
		r = CryptUtil.SHA(d.getBytes());
		System.out.println(bytes2hex(r));
		r = CryptUtil.DESEncrypt(d, key, vec);
		System.out.println(d + "==>" + CryptUtil.Base64Encode(r));
	}

	/**
	 * guess the key
	 * 
	 * @throws Exception
	 */
	static void test9() throws Exception {
		String[] keys = { "218.4.189.213", "218.4.189", "madeinchina",
				"MadeinChina", "www.MadeinChina.cn", "www.madeinchina.cn",
				"MadeinChina.cn", "dingxing", "DingXing", "dingdian", "DingDian",
				"sz215000", "215000sz", "suzhoudx", "suzhoudd", "szdinxin",
				"0512-68636500", "68636500", "82788711", "19760308", "123456",
				"654321", "12345678", "87654321", "1111", "111111", "11111111",
				"666666", "66666666", "888888", "88888888", "999999", "99999999",
				"000000", "00000000", "abcdef", "ABCDEF", "abcdefgh", "ABCDEFGH",
				"abcdefg", "ABCDEFG", "!@#$%^", "!@#$%^&*", "^%$#@!", "*&^%$#@!",
				"szgswweb", "szgsSZGS", "szgsszgs", "szgswang", "sylla", "testtest",
				"test", "iloveyou", "password", "bigapple", "ILoveYou", "February",
				"february", "1qaz2wsx", "1qaz@WSX", "1qaz@wsx", "1qazxsw@", "1qazXSW@",
				"1qazxsw2", "!QAZ2wsx", "!QAZ@WSX", "!QAZ@wsx", "!qaz2wsx", "!qaz@WSX",
				"!qaz@wsx", "12qwaszx", "!@QWASZX", "12QWasZX", "!@qwASzx", "!@12QWqw",
				"!@QW12qw", "12qw!@QW", "12!@qwQW", "!@12qwas", "13qeadzc", "!#qeadzc",
				"!#%&1357", "1357!#%&", "1357qetu", "1357QETU", "!@#$1234", "1234qwer",
				"!@#$QWER", "1234QWER", "qazxcdew", "azxcdewq", "zxcdewqa", "xcdewqaz",
				"cdewqazx", "dewqazxc", "ewqazxcd", "wqazxcde", "windows", "microsoft",
				"asp..net", "desencrypt", "java.net", "goooogle", "zhaoweiwei",
				"zhweiwei", };
		byte[] r;
		String s;
		System.err.println(ENCRYPT_TEXT);
		for (int i = 0; i < keys.length; i++) {
			for (int j = 0; j < keys.length; j++) {
				r = CryptUtil.DESEncrypt(DECRYPT_TEXT, keys[i], keys[j]);
				s = bytes2hex(r);
				if (s.equals(ENCRYPT_TEXT))
					System.out.println(s + ": " + keys[i] + ", " + keys[j]);
				// System.out.println(new String(CryptUtil.DESDecrypt(r, keys[i],
				// vecs[j])));
			}
		}
		byte[][] bkeys = {
		// {0x63, 0x68, 0x65, 0x6E, 0x79, 0x75, 0x61, 0x6E},
				hex2bytes("6368656E7975616E"),
				{ -114, 16, 93, -100, 78, 4, -38, -124 },
				{ 56, 50, 55, 56, 56, 55, 49, 49 }, hex2bytes("FEDCBA9876543210"),
				{ -101, 103, -10, 8, 36, 99, -22, 36 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
				hex2bytes("0123456789ABCDEF"), hex2bytes("1234567890ABCDEF"),
				{ 100, 110, 120, -126, 100, 110, 120, -126 },
				{ 23, -22, -61, -91, -55, -16, -113, -58 }, };
		for (int i = 0; i < bkeys.length; i++) {
			for (int j = 0; j < bkeys.length; j++) {
				r = CryptUtil.DESEncrypt(DECRYPT_TEXT, bkeys[i], bkeys[j]);
				s = bytes2hex(r);
				if (s.equals(ENCRYPT_TEXT))
					System.out.println(s + ": " + bytes2hex(bkeys[i]) + ", "
							+ bytes2hex(bkeys[j]));
				// System.out.println(new String(CryptUtil.DESDecrypt(r, keys[i],
				// vecs[j])));
			}
		}
	}

	/**
	 * guess the key using birth date [19000101 - 20081231], not found
	 * 
	 * @throws Exception
	 */
	static void test10() throws Exception {
		byte[] r;
		String k;
		long l = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.set(1900, 0, 1, 0, 0, 0);
		c2.set(2008, 11, 31, 23, 59, 59);
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		while (c1.getTimeInMillis() < c2.getTimeInMillis()) {
			k = df.format(c1.getTime());
			r = CryptUtil.DESEncrypt(DECRYPT_TEXT, k, k);
			if (compare(ENCRYPT_BYTES, r) == 0) {
				System.err.println("\nFOUND: " + k);
			}
			// if (bytes2hex(r).startsWith("AE")) System.err.println(k);
			l++;
			if (l % 1000 == 0)
				System.out.print(".");
			c1.add(Calendar.DATE, 1);
		}
		System.out.println("\ntotal = " + l);
		System.err.println(bytes2hex(ENCRYPT_BYTES));
	}

	/**
	 * guess the key using all 8 digits, not found
	 * 
	 * @throws Exception
	 */
	static void test11() throws Exception {
		byte[] r;
		String k;
		long l = 0;
		for (int i = 0; i < 99999999; i++) {
			k = toString(i, 10, 8);
			r = CryptUtil.DESEncrypt(DECRYPT_TEXT, k, k);
			if (compare(ENCRYPT_BYTES, r) == 0) {
				System.err.println("\nFOUND: " + k);
			}
			l++;
			if (l % 100000 == 0)
				System.out.print(".");
		}
		System.out.println("\ntotal = " + l);
		System.err.println(bytes2hex(ENCRYPT_BYTES));
	}

	/**
	 * only the fore 8 bytes in the key and vec are effective
	 * 
	 * @throws Exception
	 */
	static void test12() throws Exception {
		String[] keys = { "19760308", "19760308shy", };
		byte[] r;
		String s;
		System.err.println(ENCRYPT_TEXT);
		for (int i = 0; i < keys.length; i++) {
			for (int j = 0; j < keys.length; j++) {
				r = CryptUtil.DESEncrypt(DECRYPT_TEXT, keys[i], keys[j]);
				s = bytes2hex(r);
				System.out.println(s + ": " + keys[i] + ", " + keys[j]);
			}
		}
	}

	/**
	 * key = 888888 or key = 999999 and same vector, got same encrypted codes key
	 * points: dks = bkey, aps = bvec, sk <> dks (888888's sk == 999999's sk)
	 * 
	 * @throws Exception
	 */
	static void test13() throws Exception {
		String[] keys = { "88888888", "99999999", };
		byte[] vec = new byte[8];
		new Random().nextBytes(vec);
		byte[] r;
		String s;
		System.err.println(ENCRYPT_TEXT);
		for (int i = 0; i < keys.length; i++) {
			r = CryptUtil.DESEncrypt(DECRYPT_TEXT, keys[i].getBytes(), vec);
			s = bytes2hex(r);
			System.out.println(s + ": " + keys[i] + ", " + bytes2hex(vec));
		}
	}

	// private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6',
	// '7', '8', '9'};
	private static final char[][] DIGITS_MAPS = {
			{ ')', '!', '@', '#', '$', '%', '^', '&', '*', '(', },
			{ 'P', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', },
			{ 'p', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', },
			{ ':', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', },
			{ ';', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', },
			{ '?', 'Z', 'X', 'C', 'V', 'B', 'N', 'M', '<', '>', },
			{ '/', 'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', }, };

	private static final String[] DIGITS_MAPSS = { ")!@#$%^&*(", "PQWERTYUIO",
			"pqwertyuio", ":ASDFGHJKL", ";asdfghjkl", "?ZXCVBNM<>", "/zxcvbnm,.", };

	private static String trans1(String n, char[] map) {
		StringBuffer sb = new StringBuffer();
		int c;
		int ii;
		for (int i = 0; i < n.length(); i++) {
			c = n.charAt(i);
			ii = c - 48;
			sb.append(map[ii]);
		}
		return sb.toString();
	}

	private static String trans2(String n, int off) {
		StringBuffer sb = new StringBuffer();
		char c;
		char cc;
		for (int i = 0; i < n.length(); i++) {
			c = n.charAt(i);
			cc = (char) (c + off);
			sb.append(cc);
		}
		return sb.toString();
	}

	/**
	 * guess the key using birth date [19000101 - 20081231] & offset transform,
	 * not found
	 * 
	 * @throws Exception
	 */
	static void test14() throws Exception {
		byte[] r;
		String k;
		long l = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		for (int i = -16; i <= 70; i++) {
			l = 0;
			c1.set(1900, 0, 1, 0, 0, 0);
			c2.set(2008, 11, 31, 23, 59, 59);
			while (c1.getTimeInMillis() < c2.getTimeInMillis()) {
				k = df.format(c1.getTime());
				k = trans2(k, i);
				r = CryptUtil.DESEncrypt(DECRYPT_TEXT, k, k);
				if (compare(ENCRYPT_BYTES, r) == 0) {
					System.err.println("\nFOUND: " + k);
				}
				l++;
				if (l % 1000 == 0)
					System.out.print(".");
				c1.add(Calendar.DATE, 1);
			}
			System.out.println("\n" + (char) (i + '0') + "... ...: " + l);
		}
		System.err.println(bytes2hex(ENCRYPT_BYTES));
	}

	/**
	 * guess the key using birth date [19000101 - 20081231] & map transform, not
	 * found
	 * 
	 * @throws Exception
	 */
	static void test15() throws Exception {
		byte[] r;
		String k;
		long l = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		for (int i = 0; i < DIGITS_MAPSS.length; i++) {
			l = 0;
			c1.set(1900, 0, 1, 0, 0, 0);
			c2.set(2008, 11, 31, 23, 59, 59);
			while (c1.getTimeInMillis() < c2.getTimeInMillis()) {
				k = df.format(c1.getTime());
				k = trans1(k, DIGITS_MAPSS[i].toCharArray());
				r = CryptUtil.DESEncrypt(DECRYPT_TEXT, k, k);
				if (compare(ENCRYPT_BYTES, r) == 0) {
					System.err.println("\nFOUND: " + k);
				}
				l++;
				if (l % 1000 == 0)
					System.out.print(".");
				c1.add(Calendar.DATE, 1);
			}
			System.out.println("\n" + DIGITS_MAPSS[i] + ": " + l);
		}
		System.err.println(bytes2hex(ENCRYPT_BYTES));
	}

	private final static char[] ALL_CHARS =
	// "
	// abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789~`!@#$%^&*()_+-={}[]:\";'<>?,./|\\'".toCharArray();
	"/3w-F.$TzN@j=}10Hn^Cyd'aAu+g;Rc{:v]WUG\"K9~ZQrIhSX mP#x6|Y_Dl7\\q?!eLMBb'fo)`Okts<J,8[>%E(&2i*p45V"
			.toCharArray();

	private final static int ALL_CHARS_LEN = ALL_CHARS.length;

	private static String random(char[] m, int n) {
		StringBuffer sb = new StringBuffer();
		int ii;
		Random r = new Random();
		for (int i = 0; i < n; i++) {
			ii = r.nextInt(ALL_CHARS_LEN);
			sb.append(m[ii]);
		}
		return sb.toString();
	}

	/**
	 * guess the key: random key, all characters a-z: 97 - 122 A-Z: 65 - 90
	 * 
	 * @throws Exception
	 */
	static void test16() throws Exception {
		byte[] r;
		String k;
		while (true) {
			k = random(ALL_CHARS, 8);
			r = CryptUtil.DESEncrypt(DECRYPT_TEXT, k, k);
			if (compare(ENCRYPT_BYTES, r) == 0) {
				System.err.println("\nFOUND: " + k);
				break;
			}
			if (compare(r, ENCRYPT_BYTES) == 0)
				System.out.println(k);
		}
		System.err.println(bytes2hex(ENCRYPT_BYTES));
	}

	/**
	 * guess the key using zhww + 4 digits, not found
	 * 
	 * @throws Exception
	 */
	static void test17() throws Exception {
		byte[] r;
		String k;
		long l = 0;
		for (int i = 0; i < 9999; i++) {
			k = "ZHWW" + toString(i, 10, 4);
			r = CryptUtil.DESEncrypt(DECRYPT_TEXT, k, k);
			if (compare(ENCRYPT_BYTES, r) == 0) {
				System.err.println("\nFOUND: " + k);
			}
			l++;
		}
		System.out.println("\ntotal = " + l);
		System.err.println(bytes2hex(ENCRYPT_BYTES));
	}

	private static String toString(int n, int radix, int digit) {
		StringBuffer sb = new StringBuffer();
		// ��ʽ�������
		int y = n;
		int s;
		while (y >= radix) {
			s = y / radix;
			y = y % radix;
			sb.insert(0, Character.toUpperCase(Character.forDigit(y, radix)));
			y = s;
		}
		sb.insert(0, Character.toUpperCase(Character.forDigit(y, radix)));
		while (sb.length() < digit) {
			sb.insert(0, '0');
		}
		return sb.toString();
	}

	private static int compare(byte[] b1, byte[] b2) {
		if (b1 == null && b2 == null)
			return 0;
		else if (b1 == null)
			return -1;
		else if (b2 == null)
			return 1;
		if (b1.length == 0 && b2.length == 0)
			return 0;
		else if (b1.length == 0)
			return -1;
		else if (b2.length == 0)
			return 1;
		int i = 0;
		while (i < b1.length && i < b2.length) {
			if (b1[i] > b2[i])
				return 1;
			else if (b1[i] < b2[i])
				return -1;
			i++;
		}
		if (b1.length < b2.length)
			return -1;
		else if (b1.length > b2.length)
			return 1;
		else
			return 0;
	}
}

/**
 * 218.4.189.213: 80 218.4.189.213: 110 218.4.189.213: 3128 218.4.189.213: 8080
 */
