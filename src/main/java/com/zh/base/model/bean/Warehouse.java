package com.zh.base.model.bean;

import com.zh.core.model.IDataObject;

/**
 * 
* @Title: Warehouse.java 
* @Package com.zh.base.model.bean 
* @Description: 仓库信息
* @date 2015年3月25日 下午7:32:36 
* @author taozhaoping 26078
* @author mail taozhaoping@gmail.com
* @version V1.0
 */
public class Warehouse extends IDataObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -916242789369646195L;

	private Long id;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 仓库类型，0：用户创建,1:系统仓库(默认0用户仓库)
	 */
	private Integer type;
	
	/**
	 * 地址
	 */
	private String address;
	
	/**
	 * 企业ID
	 */
	private Integer enterpriseID;
	
	/**
	 * 状态
	 */
	private Integer enabled;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Integer getEnterpriseID() {
		return enterpriseID;
	}

	public void setEnterpriseID(Integer enterpriseID) {
		this.enterpriseID = enterpriseID;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
