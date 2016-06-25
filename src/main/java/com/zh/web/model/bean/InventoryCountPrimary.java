package com.zh.web.model.bean;

import com.zh.core.model.IDataObject;

/**
 * @Description: 库存变更头表
 * @Title: InventoryCountPrimary.java
 * @Package com.zh.web.model.bean
 * @date 2015年7月16日 下午8:06:05
 * @author taozhaoping 26078
 * @author mail taozhaoping@gmail.com
 * @version V1.0
 */
public class InventoryCountPrimary extends IDataObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6384038040240845486L;

	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 备注
	 */
	private String remarks;
	
	/**
	 * 仓库
	 */
	private Long warehouseID;
	
	/**
	 * 创建时间
	 */
	private String createDate;
	
	/**
	 * 盘点时间
	 */
	private String inventoryDate;
	
	/**
	 * 创建用户
	 */
	private Long userID;
	
	/**
	 * 是否审核
	 */
	private Integer status;

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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getWarehouseID() {
		return warehouseID;
	}

	public void setWarehouseID(Long warehouseID) {
		this.warehouseID = warehouseID;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getInventoryDate() {
		return inventoryDate;
	}

	public void setInventoryDate(String inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

}
