package com.zh.base.action;

import java.util.List;
import java.util.Map;

import org.apache.avalon.framework.parameters.ParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zh.base.util.ConstantService;
import com.zh.core.base.action.BaseAction;
import com.zh.core.model.Pager;
import com.zh.web.model.ProductsModel;
import com.zh.web.model.bean.Products;
import com.zh.web.service.ProductsService;

public class InterfaceProductsAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private static Logger LOGGER = LoggerFactory.getLogger(InterfaceProductsAction.class); 

	public static final Integer PAGE_SIZE = 10;
	
	/**
	 * 产品接口
	 */
	@Autowired
	private ProductsService productsService;
	
	
	private ProductsModel productsModel = new ProductsModel();
	
	private String _ ;

	@Override
	public String execute() throws Exception {
		
		return "usersjson";

	}
	
	/**
	 * 获取用户接口,json返回格式
	 */
	public String queryProductsList() {
		LOGGER.debug("queryProductsList() ");
		//所有所有激活的用户
		/*Products products = new Products();
		List<Products> productsList = productsService.queryList(products);
		this.productsModel.setProductsList(productsList);*/
		
		Products products = this.productsModel.getProducts();
		String id = this.productsModel.getProductsID();
		if ( null !=id && !"".equals(id))
		products.setId(Long.valueOf(id));
		Pager page = productsModel.getPageInfo();

		Map<String, Object> jsonMap = this.productsModel.getDataMap();
		int size = productsService.count(products);
		page.setTotalRow(size);
		jsonMap.put("total", size);
		List<Products> table = productsService.queryList(products, page);
		
		jsonMap.put("rows", table);
		return "products";
	}
	
	public String queryProducts() throws ParameterException
	{
		LOGGER.debug("queryProducts() ");
		Long id = this.productsModel.getId();
		if ( null == id || "".equals(id))
		{
			throw new ParameterException("产品编号不允许为null");
		}
		Products products = new Products();
		products.setId(id);
		Products reult = productsService.query(products);
		Map<String, Object> dataMap = this.productsModel.getDataMap();
		if (null != reult)
		{
			dataMap.put(ConstantService.REULT_OBJECT, reult);
			dataMap.put(ConstantService.STATUS, "success");
		}else
		{
			dataMap.put(ConstantService.REULT_OBJECT, "");
			dataMap.put(ConstantService.STATUS, "error");
		}
		return "products";
	}

	@Override
	public String editor() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return productsModel;
	}

	public ProductsService getProductsService() {
		return productsService;
	}

	public void setProductsService(ProductsService productsService) {
		this.productsService = productsService;
	}

	public ProductsModel getProductsModel() {
		return productsModel;
	}

	public void setProductsModel(ProductsModel productsModel) {
		this.productsModel = productsModel;
	}

	public String get_() {
		return _;
	}

	public void set_(String _) {
		this._ = _;
	}

}
