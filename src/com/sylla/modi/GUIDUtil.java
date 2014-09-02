package com.sylla.modi;

import com.develop.jawin.GUID;

public class GUIDUtil {

	private GUIDUtil() {
	}

	public static GUID getGUID(String guid) {
		return new GUID(guid);
	}

	public static GUID getGUID(int d1, int d2, int d3, int[] d4) {
		byte[] b = new byte[d4.length];
		for (int i = 0; i < b.length; i++) b[i] = (byte) d4[i];
		return new GUID(d1, (short)d2, (short)d3, b);
	}
}
