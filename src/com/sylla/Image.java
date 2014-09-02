package com.sylla;



public class Image {

	public static final String IS_INITIAL = "I";

	// public static final String IS_RUNNING = "R";

	public static final String IS_DOWNLOADED = "D";

	public static final String IS_STORED = "S";

	public static final String IS_PACKED = "P";

	public static final String IS_COMPLETE = "C";

	public static final String IS_COMPLETE2 = "N";

	private String code;

	private String text;

	private Double accurate;

	private String status;

	private Long index;

	private String type;

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public Double getAccurate() {
		return accurate;
	}

	public void setAccurate(Double accurate) {
		this.accurate = accurate;
	}

	/**
	public Blob getBody() {
		return body;
	}

	public void setBody(Blob body) {
		this.body = body;
	}

	public byte[] getImage() throws SQLException {
		return this.body.getBytes(1L, (int) this.body.length());
	}

	public void setImage(byte[] b) {
		this.body = Hibernate.createBlob(b);
	}
	**/

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
