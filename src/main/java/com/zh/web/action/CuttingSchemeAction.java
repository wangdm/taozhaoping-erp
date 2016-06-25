package com.zh.web.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zh.core.base.action.Action;
import com.zh.core.base.action.BaseAction;
import com.zh.core.model.Pager;
import com.zh.web.model.CuttingSchemeModel;
import com.zh.web.model.bean.CuttingScheme;
import com.zh.web.service.CuttingSchemeService;
import com.zh.web.service.ProductsService;

public class CuttingSchemeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 731437174900014739L;

	private static Logger LOGGER = LoggerFactory
			.getLogger(CuttingSchemeAction.class);

	@Autowired
	private CuttingSchemeService cuttingSchemeService;
	
	@Autowired
	private ProductsService productsService;
	
	private CuttingSchemeModel cuttingSchemeModel = new CuttingSchemeModel();

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return cuttingSchemeModel;
	}

	@Override
	public String execute() throws Exception {
		CuttingScheme cuttingScheme = this.cuttingSchemeModel
				.getCuttingScheme();
		Integer count = cuttingSchemeService.count(cuttingScheme);
		Pager page = this.cuttingSchemeModel.getPageInfo();
		page.setTotalRow(count);
		List<CuttingScheme> cuttingSchemeList = cuttingSchemeService
				.queryList(cuttingScheme, page);
		this.cuttingSchemeModel.setCuttingSchemeList(cuttingSchemeList);
		return Action.SUCCESS;
	}

	public String editor() throws Exception {
		LOGGER.debug("editor()");
		Long id = this.cuttingSchemeModel.getId();
		
		if (null != id) {
			// 查询信息
			LOGGER.debug("editor StoragePrimary id " + id);
			CuttingScheme cuttingScheme = this.cuttingSchemeModel
					.getCuttingScheme();
			cuttingScheme.setId(Long.valueOf(id));
			CuttingScheme reult = cuttingSchemeService.query(cuttingScheme);
			this.cuttingSchemeModel.setCuttingScheme(reult);

			// 判断是否已经入库，入库状态下，只进入查看页面
			String view = this.cuttingSchemeModel.getView();
			if ("view".equals(view)) {
				return Action.VIEW;
			}
		}
		return Action.EDITOR;
	}

	public String save() throws Exception {
		LOGGER.debug("save()");
		CuttingScheme cuttingScheme = this.cuttingSchemeModel
				.getCuttingScheme();
		Long id = this.cuttingSchemeModel.getId();
		if (null != id && !"".equals(id)) {
			String view = this.cuttingSchemeModel.getView();
			if (null != view && "enabled".equals(view)) {
				String enabled = this.cuttingSchemeModel.getEnabled();
				cuttingScheme = new CuttingScheme();
				if ("0".equals(enabled)) {
					cuttingScheme.setEnabled(1);
				} else {
					cuttingScheme.setEnabled(0);
				}
				LOGGER.debug("update warehouse enabled "
						+ cuttingScheme.getEnabled());
			}
			cuttingScheme.setId(id);
			cuttingSchemeService.update(cuttingScheme);
			LOGGER.debug("update storagePrimary id" + id);
		} else {
			// 新增
			cuttingSchemeService.insert(cuttingScheme);
			LOGGER.debug("add storagePrimary");
		}
		this.cuttingSchemeModel.setFormId(cuttingScheme.getId().toString());
		return Action.EDITOR_SAVE;
	}
}
