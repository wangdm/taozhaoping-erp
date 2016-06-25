package com.zh.web.model;

import java.util.ArrayList;
import java.util.List;

import com.zh.core.base.model.BaseModel;
import com.zh.web.model.bean.ProcessingSingleDetail;
import com.zh.web.model.bean.ProcessingSinglePrimary;
import com.zh.web.model.bean.ProductionStorageDetail;
import com.zh.web.model.bean.SalesOrderPrimary;

public class ProcessingSingleModel extends BaseModel {

	private ProcessingSinglePrimary processingSinglePrimary = new ProcessingSinglePrimary();

	private List<ProcessingSinglePrimary> processingSinglePrimaryList = new ArrayList<ProcessingSinglePrimary>();

	private ProcessingSingleDetail processingSingleDetail = new ProcessingSingleDetail();

	private List<ProcessingSingleDetail> processingSingleDetailList = new ArrayList<ProcessingSingleDetail>();
	
	/**
	 * 销售订单集合
	 */
	private List<SalesOrderPrimary> salesOrderPrimaryList = new ArrayList<SalesOrderPrimary>();
	
	/**
	 *加工单入库_明细 
	 */
	private ProductionStorageDetail productionStorageDetail = new ProductionStorageDetail();
	
	/**
	 * 加工单入库_明细 集合
	 */
	private List<ProductionStorageDetail> productionStorageDetailList = new ArrayList<ProductionStorageDetail>();

	private String startDate;
	
	private String endDate;
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public ProcessingSinglePrimary getProcessingSinglePrimary() {
		return processingSinglePrimary;
	}

	public void setProcessingSinglePrimary(ProcessingSinglePrimary ProcessingSinglePrimary) {
		this.processingSinglePrimary = ProcessingSinglePrimary;
	}

	public List<ProcessingSinglePrimary> getProcessingSinglePrimaryList() {
		return processingSinglePrimaryList;
	}

	public void setProcessingSinglePrimaryList(List<ProcessingSinglePrimary> ProcessingSinglePrimaryList) {
		this.processingSinglePrimaryList = ProcessingSinglePrimaryList;
	}

	public ProcessingSingleDetail getProcessingSingleDetail() {
		return processingSingleDetail;
	}

	public void setProcessingSingleDetail(ProcessingSingleDetail ProcessingSingleDetail) {
		this.processingSingleDetail = ProcessingSingleDetail;
	}


	public List<ProcessingSingleDetail> getProcessingSingleDetailList() {
		return processingSingleDetailList;
	}

	public void setProcessingSingleDetailList(List<ProcessingSingleDetail> ProcessingSingleDetailList) {
		this.processingSingleDetailList = ProcessingSingleDetailList;
	}

	public List<SalesOrderPrimary> getSalesOrderPrimaryList() {
		return salesOrderPrimaryList;
	}

	public void setSalesOrderPrimaryList(
			List<SalesOrderPrimary> salesOrderPrimaryList) {
		this.salesOrderPrimaryList = salesOrderPrimaryList;
	}

	public ProductionStorageDetail getProductionStorageDetail() {
		return productionStorageDetail;
	}

	public void setProductionStorageDetail(
			ProductionStorageDetail productionStorageDetail) {
		this.productionStorageDetail = productionStorageDetail;
	}

	public List<ProductionStorageDetail> getProductionStorageDetailList() {
		return productionStorageDetailList;
	}

	public void setProductionStorageDetailList(
			List<ProductionStorageDetail> productionStorageDetailList) {
		this.productionStorageDetailList = productionStorageDetailList;
	}
	
}
