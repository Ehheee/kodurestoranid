package thething.kodurestoranid.dataobjects;

import java.util.Date;

public class ThingNode {
	/*
	 * 
	 *  create table objects (
	 id SERIAL,
	 name VARCHAR(60),
	 type VARCHAR(10),
	 text1 VARCHAR(2000),
	 text2 VARCHAR(2000),
	 date1 DATE,
	 date2 DATE,
	 author BIGINT,
	 active BOOLEAN,
	 disabled BOOLEAN,
	 requiresPermissions BOOLEAN,
	 parentId BIGINT,
	 sort INT,
	 location VARCHAR(20),
	 custom01 VARCHAR(1000)
	 );
	 */
	
	
	private String id;
	private String name;
	private String type;
	private String text1;
	private String text2;
	private Date date1;
	private Date date2;
	private Long author;
	private Boolean active;
	private Boolean disabled;
	private Boolean requiresPermission;
	private String parentId;
	private Integer sort;
	private String location;
	private String custom01;


	public ThingNode() {
	}
	
	
	
	
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
	public String getText1() {
		return text1;
	}
	public void setText1(String text1) {
		this.text1 = text1;
	}

	
	public String getText2() {
		return text2;
	}
	public void setText2(String text2) {
		this.text2 = text2;
	}

	
	public Date getDate1() {
		return date1;
	}
	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	
	public Date getDate2() {
		return date2;
	}
	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	
	public Long getAuthor() {
		return author;
	}
	public void setAuthor(Long author) {
		this.author = author;
	}

	
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}

	
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	
	public Boolean getRequiresPermission() {
		return requiresPermission;
	}
	public void setRequiresPermission(Boolean requiresPermission) {
		this.requiresPermission = requiresPermission;
	}
	

	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	
	public String getCustom01() {
		return custom01;
	}
	public void setCustom01(String custom01) {
		this.custom01 = custom01;
	}

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
}
