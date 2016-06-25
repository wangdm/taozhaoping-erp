package com.zh.web.model;

import java.util.ArrayList;
import java.util.List;

import com.zh.base.model.bean.Dictionary;
import com.zh.core.base.model.BaseModel;
import com.zh.web.model.bean.BomDetail;
import com.zh.web.model.bean.BomPrimary;
import com.zh.web.model.bean.BomSub;
import com.zh.web.model.bean.ProcessBean;
import com.zh.web.model.bean.ProductProcess;
import com.zh.web.model.bean.Products;

public class ProductStructModel extends BaseModel {

	/**
	 * 结构主表
	 */
	private BomPrimary bomPrimary = new BomPrimary();
	
	/**
	 * 结构主表列表
	 */
	private List<BomPrimary> bomPrimaryList = new ArrayList<BomPrimary>();

	/**
	 * 结构明细
	 */
	private BomDetail bomDetail = new BomDetail();
	
	/**
	 * 替代料
	 */
	private BomSub bomSub = new BomSub();
	
	/**
	 * 结构明细列表
	 */
	private List<BomDetail> bomDetailList = new ArrayList<BomDetail>();
	
	/**
	 * 替代料列表
	 */
	private List<BomSub> bomSubList = new ArrayList<BomSub>();
	
	/**
	 * 工序配置列表
	 */
	private String  processListJson;
	
	private List<ProcessBean>  processList = new ArrayList<ProcessBean>();
	/**
	 * 产品工序
	 */
	private ProductProcess productProcess = new ProductProcess();
	
	/**
	 * 产品类型
	 */
	private List<Dictionary> productTypeList = new ArrayList<Dictionary>();
	
	/**
	 * //颜色
	 */
	private List<Dictionary> paintList = new ArrayList<Dictionary>();
	
	/**
	 * //产品来源
	 */
	private List<Dictionary> sourceTypeList = new ArrayList<Dictionary>();
	
	private List<ProductProcess> productProcessList = new ArrayList<ProductProcess>();
	
	/**
	 * 产品
	 */
	private Products products = new Products();

	public BomPrimary getBomPrimary() {
		return bomPrimary;
	}

	public void setBomPrimary(BomPrimary bomPrimary) {
		this.bomPrimary = bomPrimary;
	}

	public BomDetail getBomDetail() {
		return bomDetail;
	}

	public void setBomDetail(BomDetail bomDetail) {
		this.bomDetail = bomDetail;
	}

	public BomSub getBomSub() {
		return bomSub;
	}

	public void setBomSub(BomSub bomSub) {
		this.bomSub = bomSub;
	}

	public List<BomDetail> getBomDetailList() {
		return bomDetailList;
	}

	public void setBomDetailList(List<BomDetail> bomDetailList) {
		this.bomDetailList = bomDetailList;
	}

	public List<BomPrimary> getBomPrimaryList() {
		return bomPrimaryList;
	}

	public void setBomPrimaryList(List<BomPrimary> bomPrimaryList) {
		this.bomPrimaryList = bomPrimaryList;
	}

	public List<BomSub> getBomSubList() {
		return bomSubList;
	}

	public void setBomSubList(List<BomSub> bomSubList) {
		this.bomSubList = bomSubList;
	}

	public ProductProcess getProductProcess() {
		return productProcess;
	}

	public void setProductProcess(ProductProcess productProcess) {
		this.productProcess = productProcess;
	}

	public List<ProductProcess> getProductProcessList() {
		return productProcessList;
	}

	public void setProductProcessList(List<ProductProcess> productProcessList) {
		this.productProcessList = productProcessList;
	}

	public String getProcessListJson() {
		return processListJson;
	}

	public void setProcessListJson(String processListJson) {
		this.processListJson = processListJson;
	}

	public List<ProcessBean> getProcessList() {
		return processList;
	}

	public void setProcessList(List<ProcessBean> processList) {
		this.processList = processList;
	}

	public List<Dictionary> getProductTypeList() {
		return productTypeList;
	}

	public void setProductTypeList(List<Dictionary> productTypeList) {
		this.productTypeList = productTypeList;
	}

	public List<Dictionary> getPaintList() {
		return paintList;
	}

	public void setPaintList(List<Dictionary> paintList) {
		this.paintList = paintList;
	}

	public List<Dictionary> getSourceTypeList() {
		return sourceTypeList;
	}

	public void setSourceTypeList(List<Dictionary> sourceTypeList) {
		this.sourceTypeList = sourceTypeList;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

}
