package com.zh.web.dao;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.zh.core.base.dao.BaseDao;
import com.zh.web.model.bean.ProductionStorageDetail;

@Component("productionStorageDetailDao")
public class ProductionStorageDetailDao extends BaseDao<ProductionStorageDetail> {

	@Override
	@PostConstruct
	public void init() {
		this.setNamespace("M_ProductionStorageDetail");
	}
	
}
