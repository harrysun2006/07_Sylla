package com.sylla;


public class Info {

	public static final String IS_INITIAL = "I";

	// public static final String IS_RUNNING = "R";

	public static final String IS_DOWNLOADED = "D";

	// public static final String IS_COMPLETE = "C";

	private InfoPK id;

	private String code;

	private String status;

	private Long index;

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public Info() {
	}

	public Info(Long corpId, String type, String code, String status) {
		setId(new InfoPK(corpId, type));
		setCode(code);
		setStatus(status);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getCorpId() {
		return (id == null) ? null : id.getCorpId();
	}

	public void setCorpId(Long corpId) {
		if(id == null) id = new InfoPK();
		id.setCorpId(corpId);
	}

	public String getType() {
		return (id == null) ? null : id.getType();
	}

	public void setType(String type) {
		if(id == null) id = new InfoPK();
		id.setType(type);
	}

	public InfoPK getId() {
		return id;
	}

	public void setId(InfoPK id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
