package com.zh.web.service.impl;

import java.util.List;

import org.apache.avalon.framework.parameters.ParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zh.core.exception.ProjectException;
import com.zh.core.model.Pager;
import com.zh.web.concurrent.ExpandProductionTask;
import com.zh.web.dao.ProcessingSingleDetailDao;
import com.zh.web.dao.ProcessingSinglePrimaryDao;
import com.zh.web.model.bean.ProcessingSingleDetail;
import com.zh.web.model.bean.ProcessingSinglePrimary;
import com.zh.web.model.bean.ProductionTask;
import com.zh.web.model.bean.SalesOrderBom;
import com.zh.web.service.ProcessingSinglePrimaryService;
import com.zh.web.service.ProductionTaskService;
import com.zh.web.service.SalesOrderBomService;
import com.zh.web.util.ExecutorServiceHandler;
import com.zh.web.util.StockUtil;
import com.zh.web.util.UtilService;

@Component("processingSinglePrimaryService")
public class ProcessingSinglePrimaryServiceImpl implements ProcessingSinglePrimaryService {

	@Autowired
	private ProcessingSinglePrimaryDao processingSinglePrimaryDao;

	@Autowired
	private ProcessingSingleDetailDao processingSingleDetailDao;
	
	@Autowired
	private SalesOrderBomService salesOrderBomService;
	
	@Autowired
	private ProductionTaskService productionTaskService;
	
	@Autowired
	private ExpandProductionTask command;

	@Override
	public ProcessingSinglePrimary query(ProcessingSinglePrimary processingSinglePrimary) {
		return processingSinglePrimaryDao.query(processingSinglePrimary);
	}

	@Override
	public void update(ProcessingSinglePrimary processingSinglePrimary) {
		processingSinglePrimaryDao.update(processingSinglePrimary);
	}

	@Override
	public List<ProcessingSinglePrimary> queryList(ProcessingSinglePrimary processingSinglePrimary) {
		return processingSinglePrimaryDao.queryList(processingSinglePrimary);
	}

	@Override
	public List<ProcessingSinglePrimary> queryList(ProcessingSinglePrimary processingSinglePrimary,
			Pager page) {
		return processingSinglePrimaryDao.queryPageList(processingSinglePrimary, page);
	}

	@Override
	public Integer count(ProcessingSinglePrimary processingSinglePrimary) {
		return processingSinglePrimaryDao.count(processingSinglePrimary);
	}

	@Override
	public void delete(ProcessingSinglePrimary processingSinglePrimary) {
		processingSinglePrimaryDao.delete(processingSinglePrimary);
	}

	@Override
	public Long insert(ProcessingSinglePrimary processingSinglePrimary) {
		processingSinglePrimaryDao.insert(processingSinglePrimary);
		Long processingSingleId = processingSinglePrimary.getId();
		//销售订单id
		Long salesOrdePrimaryId = processingSinglePrimary.getPurchaseOrderId();
		SalesOrderBom data = new SalesOrderBom();
		//设置自生产类型
		data.setSourceType(UtilService.PRODUCTION_SOURCE_TYPE_SELF_PROCESSING);
		data.setOrderID(salesOrdePrimaryId);
		//销售中的产品
		List<SalesOrderBom> salesOrderBomList = salesOrderBomService.queryList(data);
		for(SalesOrderBom sod : salesOrderBomList){
			Float sotrageNumber = sod.getQty();
			ProcessingSingleDetail psd = new ProcessingSingleDetail();
			psd.setProcessingSingleId(processingSingleId);
			psd.setSalesOrderBomID(sod.getId());
			psd.setProductsId(sod.getProductsID());
			psd.setProcessingNumber(sotrageNumber);
			psd.setTier(sod.getTier());
			if("N".equals(sod.getMainSub()))
			{
				psd.setProductionMark(UtilService.PROCESSING_SINGLE_DETAIL_NO_PRODUCTION);
			}else
			{
				psd.setProductionMark(UtilService.PROCESSING_SINGLE_DETAIL_PRODUCTION);
			}
			processingSingleDetailDao.insert(psd);
		}
		return processingSingleId;
	}
	
	/**
	 * 审核加工单
	 * @throws ParameterException 
	 */
	public void increase(Long processingSingleId) throws ParameterException
	{
		//判断加工时间是否填写
		ProcessingSingleDetail data = new ProcessingSingleDetail();
		data.setProcessingSingleId(processingSingleId);
		List<ProcessingSingleDetail> list = processingSingleDetailDao.queryList(data);
		for (ProcessingSingleDetail processingSingleDetail : list) {
			if("N".equals(processingSingleDetail.getMainsub()))
			{
				continue;
			}
			if(processingSingleDetail.getStartDate() == null || processingSingleDetail.getEndDate() == null)
			{
				throw new ParameterException("加工单明细中产品加工时间未填写，请先填写完成后，在审核");
			}
		}
		
		//审核状态修改
		ProcessingSinglePrimary processingSinglePrimary = new ProcessingSinglePrimary();
		processingSinglePrimary.setId(processingSingleId);
		processingSinglePrimary
				.setStatus(UtilService.PROCESSING_SINGLE_STATUS_EXAMINE);
		processingSinglePrimaryDao.update(processingSinglePrimary);
		
		command.setProcessingSingleId(processingSingleId);
		ExecutorServiceHandler.getInstance().execute(command);
	}

	@Override
	public void increaseStock(Long id) {
		// TODO Auto-generated method stub
		ProcessingSinglePrimary processingSinglePrimary = new ProcessingSinglePrimary();
		processingSinglePrimary.setId(id);
		ProcessingSinglePrimary reult = this.query(processingSinglePrimary);
		if (null == reult)
		{
			throw new RuntimeException("数据库不存在该单据");
		}
		
		if (1 == reult.getStatus())
		{
			//生产任务单是否完成
			ProductionTask productionTask = new ProductionTask();
			productionTask.setInventoryCountID(id);
			List<ProductionTask> taskReultList = productionTaskService.queryList(productionTask);
			for (ProductionTask taskInfo : taskReultList) {
				if(taskInfo.getStatus() != UtilService.TASK_STATUS_COMPLETE)
				{
					throw new RuntimeException("生产订单没有完成，请先核对订单生产状态");
				}
			}
			
			//设置入库状态
			processingSinglePrimary.setStatus(2);
			this.update(processingSinglePrimary);
			
			//单据入库
			StockUtil stockUtil = StockUtil.getInstance();
			stockUtil.operationStock(reult.getId(),0L,StockUtil.TASK_INCREASE);
		}else
		{
			throw new RuntimeException("生产单据号：" + reult.getId() + "，已经入库!不允许重复入库");
		}
	}


}
