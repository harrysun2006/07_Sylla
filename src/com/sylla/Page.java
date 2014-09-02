package com.sylla;

public class Page {

	public static final String PS_INITIAL = "I";

	// public static final String PS_REMOVED = "R";

	public static final String PS_DOWNLOADED = "D";

	public static final String PS_VERIFIED = "V";

	// public static final String PS_COMPLETE = "C";

	private PagePK id;

	private String status;

	private Long index;

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public Page() {
		
	}

	public Page(Long pageId, String type, String status) {
		setId(new PagePK(pageId, type));
		setStatus(status);
	}

	public Long getPageId() {
		return (id == null) ? null : id.getPageId();
	}

	public void setPageId(Long pageId) {
		if (id == null) id = new PagePK();
		id.setPageId(pageId);
	}

	public String getType() {
		return (id == null) ? null : id.getType();
	}

	public void setType(String type) {
		if (id == null) id = new PagePK();
		id.setType(type);
	}

	public PagePK getId() {
		return id;
	}

	public void setId(PagePK id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
