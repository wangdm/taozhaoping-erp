package com.zh.base.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zh.base.model.WarehouseModel;
import com.zh.base.model.bean.Enterprise;
import com.zh.base.model.bean.Warehouse;
import com.zh.base.service.WarehouseService;
import com.zh.core.base.action.Action;
import com.zh.core.base.action.BaseAction;
import com.zh.core.model.Pager;
import com.zh.web.util.UtilService;

public class WarehouseAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8144200795355470737L;

	private static Logger LOGGER = LoggerFactory
			.getLogger(WarehouseAction.class);

	private WarehouseModel warehouseModel = new WarehouseModel();

	private WarehouseService warehouseService;

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return warehouseModel;
	}

	@Override
	public String execute() throws Exception {
		Warehouse warehouse = this.warehouseModel.getWarehouse();
		warehouse.setType(UtilService.WAREHOUSE_TYPE_ZERO);
		Integer count = warehouseService.count(warehouse);
		Pager page = this.warehouseModel.getPageInfo();
		page.setTotalRow(count);
		List<Warehouse> list = warehouseService.queryList(warehouse, page);
		this.warehouseModel.setWarehouseList(list);
		return Action.SUCCESS;

	}

	public WarehouseModel getWarehouseModel() {
		return warehouseModel;
	}

	public void setWarehouseModel(WarehouseModel warehouseModel) {
		this.warehouseModel = warehouseModel;
	}

	public WarehouseService getWarehouseService() {
		return warehouseService;
	}

	public void setWarehouseService(WarehouseService warehouseService) {
		this.warehouseService = warehouseService;
	}

	@Override
	public String editor() throws Exception {
		LOGGER.debug("editor()");
		Long id = this.warehouseModel.getId();

		// 获取企业列表
		List<Enterprise> enterpriseList = this.queryEnterpriseList();
		this.warehouseModel.setEnterpriseList(enterpriseList);

		if (null != id) {
			// 查询信息
			LOGGER.debug("editor Warehouse id " + id);
			Warehouse warehouse = this.warehouseModel.getWarehouse();
			warehouse.setId(Long.valueOf(id));
			Warehouse reult = warehouseService.query(warehouse);
			this.warehouseModel.setWarehouse(reult);
		}

		return Action.EDITOR;
	}

	@Override
	public String save() throws Exception {
		LOGGER.debug("save()");
		Warehouse warehouse = this.warehouseModel.getWarehouse();
		Long id = this.warehouseModel.getId();
		if (null != id && !"".equals(id)) {
			String view = this.warehouseModel.getView();
			if (null != view && "enabled".equals(view)) {
				String enabled = this.warehouseModel.getEnabled();
				warehouse = new Warehouse();
				if ("0".equals(enabled)) {
					warehouse.setEnabled(1);
				} else {
					warehouse.setEnabled(0);
				}
				LOGGER.debug("update warehouse enabled "
						+ warehouse.getEnabled());
			}
			warehouse.setId(id);
			warehouseService.update(warehouse);
			LOGGER.debug("update warehouse id" + id);
		} else {
			// 新增
			warehouseService.insert(warehouse);
			LOGGER.debug("add warehouse");
		}
		return Action.EDITOR_SUCCESS;
	}

}
