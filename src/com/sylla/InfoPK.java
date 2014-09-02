package com.sylla;

import java.io.Serializable;

public class InfoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String IT_REGNO = "RN";

	public static final String IT_NAME2 = "N2";

	public static final String IT_OWNER = "O";

	public static final String IT_ADDR = "A";

	public static final String IT_CAP = "CP";

	// public static final String IT_SCOPE = "S";

	public static final String IT_TEL = "T";

	public static final String IT_POSTCODE = "PC";

	public static final String IT_INDUSTRY = "I";

	public static final String IT_PRECINCT = "P";

	public static final String IT_CREATEDATE2 = "CD2";

	public static final String IT_REGORGAN = "RO";

	public static final String IT_CATEGORY = "C";

	private Long corpId;

	private String type;

	public InfoPK() {
		
	}

	public InfoPK(Long corpId, String type) {
		setCorpId(corpId);
		setType(type);
	}

	public Long getCorpId() {
		return corpId;
	}

	public void setCorpId(Long corpId) {
		this.corpId = corpId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean equals(Object other) {
		if(!(other instanceof InfoPK)) return false;
		InfoPK o = (InfoPK)other;
		return (corpId != null 
			&& type != null 
			&& corpId.equals(o.getCorpId()) 
			&& type.equalsIgnoreCase(o.getType()));
	}

	public int hashCode() {
		return corpId.hashCode() + type.hashCode();
	}
}
