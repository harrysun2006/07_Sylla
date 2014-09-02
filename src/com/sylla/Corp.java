package com.sylla;

public class Corp {

	public static final String CS_INITIAL = "I";

	// public static final String CS_RUNNING = "R";

	public static final String CS_DOWNLOADED = "D";

	// public static final String CS_VERIFIED = "V";

	// public static final String CS_COMPLETE = "C";

	public static final String CT_GSQY = PagePK.PT_GSQY;

	public static final String CT_GTQY = PagePK.PT_GTQY;

	private Long id;

	private String regNo;

	private String name1;

	private String name2;

	private String owner;

	private String address;

	private String capital;

	private String scope;

	private String telephone;

	private String postCode;

	private String industry;

	private String precinct;

	private String createDate1;

	private String createDate2;

	private String regOrgan;

	private String category;

	private Long page;

	private Integer pageIndex;

	private String status;

	private Long index;

	private String type;

	private Long maxPage;

	public Long getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(Long maxPage) {
		this.maxPage = maxPage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCreateDate1() {
		return createDate1;
	}

	public void setCreateDate1(String createDate1) {
		this.createDate1 = createDate1;
	}

	public String getCreateDate2() {
		return createDate2;
	}

	public void setCreateDate2(String createDate2) {
		this.createDate2 = createDate2;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPrecinct() {
		return precinct;
	}

	public void setPrecinct(String precinct) {
		this.precinct = precinct;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getRegOrgan() {
		return regOrgan;
	}

	public void setRegOrgan(String regOrgan) {
		this.regOrgan = regOrgan;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

}
