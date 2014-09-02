package com.sylla;

import java.io.Serializable;

public class PagePK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String PT_GSQY = "S";

	public static final String PT_GTQY = "T";

	private Long pageId;

	private String type;

	public PagePK() {
		
	}

	public PagePK(Long pageId, String type) {
		setPageId(pageId);
		setType(type);
	}

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean equals(Object other) {
		if(!(other instanceof PagePK)) return false;
		PagePK o = (PagePK)other;
		return (pageId != null 
			&& type != null 
			&& pageId.equals(o.getPageId()) 
			&& type.equalsIgnoreCase(o.getType()));
	}

	public int hashCode() {
		return pageId.hashCode() + type.hashCode();
	}

}
