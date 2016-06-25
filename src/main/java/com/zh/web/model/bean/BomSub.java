package com.zh.web.model.bean;

import com.zh.core.model.IDataObject;

/**
 * @Title: ProductStructure.java
 * @Package com.zh.web.model.bean
 * @Description: 产品结构替代料
 * @date 2015年4月1日 下午2:32:40
 * @author taozhaoping 26078
 * @author mail taozhaoping@gmail.com
 * @version V1.0
 */
public class BomSub extends IDataObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2984289025067968893L;

	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 产品结构头表主建
	 */
	private Long primaryId;
	
	/**
	 * 产品id
	 */
	private Long productsId;
	
	/**
	 * 主料编号
	 */
	private Long mainProductsId;

	/**
	 * 替代料编号
	 */
	private Long subProductsId;

	/**
	 * 替代料名称
	 */
	private String subProductsName;
	
	/**
	 * 库位号
	 */
	private Integer position;
	
	/**
	 * 数量
	 */
	private Float qty;
	
	/**
	 * 备注
	 */
	private String remarks;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPrimaryId() {
		return primaryId;
	}

	public void setPrimaryId(Long primaryId) {
		this.primaryId = primaryId;
	}

	public Long getProductsId() {
		return productsId;
	}

	public void setProductsId(Long productsId) {
		this.productsId = productsId;
	}

	public Long getMainProductsId() {
		return mainProductsId;
	}

	public void setMainProductsId(Long mainProductsId) {
		this.mainProductsId = mainProductsId;
	}

	public Long getSubProductsId() {
		return subProductsId;
	}

	public void setSubProductsId(Long subProductsId) {
		this.subProductsId = subProductsId;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Float getQty() {
		return qty;
	}

	public void setQty(Float qty) {
		this.qty = qty;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getSubProductsName() {
		return subProductsName;
	}

	public void setSubProductsName(String subProductsName) {
		this.subProductsName = subProductsName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id:").append(id)
		.append(" primaryId:").append(primaryId)
		.append(" mainProductsId:").append(mainProductsId)
		.append(" position:").append(position)
		.append(" subProductsId:").append(subProductsId)
		.append(" subProductsName:").append(subProductsName)
		.append(" qty:").append(qty)
		.append(" remarks:").append(remarks);
		return sb.toString();
	}

}
