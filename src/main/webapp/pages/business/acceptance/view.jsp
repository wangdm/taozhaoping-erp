<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@  page import="com.zh.base.util.JspUtil" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="getText('COM.OSFI.WINDOW.TITLE')" /></title>
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/theme.css">
<link rel="stylesheet" href="<%=path%>/css/font-awesome.css">
<link rel="stylesheet" href="<%=path%>/js/select2/select2.css">
<script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/js/jqPaginator.min.js"></script>
<!-- Demo page code -->
<style type="text/css">
#line-chart {
	height: 300px;
	width: 800px;
	margin: 0px auto;
	margin-top: 1em;
}

.brand {
	font-family: georgia, serif;
}

.brand .first {
	color: #ccc;
	font-style: italic;
}

.brand .second {
	color: #fff;
	font-weight: bold;
}
</style>
<link href="<%=path%>/img/favicon_32.ico" rel="bookmark"
	type="image/x-icon" />
<link href="<%=path%>/img/favicon_32.ico" rel="icon" type="image/x-icon" />
<link href="<%=path%>/img/favicon_32.ico" rel="shortcut icon"
	type="image/x-icon" />
</head>
<!--[if lt IE 7 ]> <body class="ie ie6"> <![endif]-->
<!--[if IE 7 ]> <body class="ie ie7 "> <![endif]-->
<!--[if IE 8 ]> <body class="ie ie8 "> <![endif]-->
<!--[if IE 9 ]> <body class="ie ie9 "> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<body class="">
<jsp:useBean id="userName" class="com.zh.base.util.JspUtil" scope="session"></jsp:useBean>
	<!--<![endif]-->
	<%@ include file="/pages/common/titleWithNav.jsp"%>
	<%@ include file="/pages/common/sidebarWithNav.jsp"%>

	<div class="content">
		<div class="header">
			<div class="stats">
				<p class="stat">
					<span class="number">53</span>tickets
				</p>
				<p class="stat">
					<span class="number"><s:property value="#session.taskSize" /></span>tasks
				</p>
				<p class="stat">
					<span class="number">15</span>waiting
				</p>
			</div>

			<h1 class="page-title" id="menu2Name">&nbsp;</h1>
		</div>

		<ul class="breadcrumb">
			<li><a href="<%=path%>/home/main.jspa">主页</a> <span
				class="divider">/</span></li>
			<li><a href="" id="navigation"></a> <span class="divider">/</span></li>
			<li class="active">查看</li>
		</ul>
		<s:set name="ProcessId"
					value="productionTask.id!=null&&productionTask.id!=''" />
		<div class="container-fluid">
				<input type="hidden" id="formChanged" name="formChanged" value="0" />
				<div class="row-fluid">

					<div class="btn-toolbar">
						
						<a class="btn" id="backList" href=""> 返回</a>
						<div class="btn-group"></div>
						<div class="pull-right">
							<shiro:hasPermission name="acceptance:approve">
							<s:if test="1 == productionTask.status">
								<button class="btn btn-danger" type="button" id="approveBtn"
								data-toggle="modal" data-target="#forMchangefirm">
									<i class="icon-ok"></i> 审核
								</button>
							</s:if>
							</shiro:hasPermission>
						</div>
						
					</div>
					<div class="well">
						<ul class="nav nav-tabs">
							<li><a id="homeButt" href="#home" data-toggle="tab">基本信息</a></li>
							<li><a id="storagedetailButt" href="#storagedetail" data-toggle="tab">验收清单</a></li>
						</ul>
						<div id="myTabContent" class="tab-content">
							<dl class="tab-pane fade dl-horizontal" id="home">
								<dir class="row">
									<div class="span4">
										<div class="control-group">
											<dt>生产任务单：</dt>
											<dd>${productionTask.productionOrder}</dd>
										</div>
									</div>
									
									<div class="span4">
										<div class="control-group">
											<dt>加工单号：</dt>
											<dd>
												<dd>${productionTask.processingsingleID}</dd>
											</dd>
										</div>
									</div>
								</dir>
								
								<dir class="row">
									<div class="span4">
										<div class="control-group">
											<dt>加工日期：</dt>
											<dd>
												<s:date name="productionTask.startDate" format="yyyy-MM-dd" />
											</dd>
										</div>
									</div>
									<div class="span4">
										<div class="control-group">
											<dt>完成日期：</dt>
											<dd>
												<s:date name="productionTask.endDate" format="yyyy-MM-dd" />
											</dd>
										</div>
									</div>
								</dir>
								
								<dir class="row">
									<div class="span4">
										<div class="control-group">
											<dt>状态：</dt>
											<dd>
												<s:if test="0 == productionTask.status">领料中</s:if>
												<s:elseif test="1 == productionTask.status">加工中</s:elseif>
												<s:elseif test="2 == productionTask.status">验收中</s:elseif>
												<s:elseif test="3 == productionTask.status">完成</s:elseif>
											</dd>
										</div>
									</div>
								</dir>
							</dl>
							
							<div class="tab-pane fade" id="storagedetail">
								<table class="table ">
									<thead>
										<tr>
										<th>序号</th>
										<th>产品编号</th>
										<th>工序名称</th>
										<th>验收时间</th>
										<th>操作</th>
										</tr>
									</thead>
									
									<tbody id="maillistSearch">
										<tr>
											<!-- 产品列表-->
											<s:iterator value="acceptanceList" var="tp" status="index">
											<tr>
												<td><s:property value="#index.index +1" /></td>
												<td><s:property value="#tp.productsID" /></td>
												<td><s:property value="#tp.processName" /></td>
												<td><s:property value="#tp.acceptanceDate" /></td>
												<td>
													<s:if test="1 == productionTask.status and 0 == #tp.isAcceptance">
													<a title="验收" style="margin: 0px 3px;" href="${menu2Id}!updateAcceptance.jspa?productionTask.id=${productionTask.id}&acceptance.id=<s:property value='#tp.id'/>&menuId=${menuId}&menu2Id=${menu2Id}&spaceId=${spaceId}&tabID=storagedetailButt"><i class="icon-ok"></i></a>
													</s:if>
												</td>
											</tr>
											</s:iterator>
										</tr>
									</tbody>
								</table>
								<div class="pagination">
									<ul id="pagination">
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
		</div>
	</div>
	
	<div class="modal small hide fade" id="forMchangefirm" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3 id="myModalLabel">提示</h3>
		</div>
		<div class="modal-body">
			<p class="error-text">
				<i class="icon-warning-sign modal-icon "></i>当前单据审核后将不可更改.继续请按."审核" 否则请按 "取消"
			</p>
		</div>
		<div class="modal-footer">
			<button class="btn btn-danger" data-dismiss="modal"
				id="formChangefirmBtn">审核</button>
			<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
		</div>
	</div>

	<form action="${menu2Id}!editor.jspa?menuId=${menuId}&menu2Id=${menu2Id}" id="queryForm" method="post">
		<input id="curPage" name="pageInfo.curPage" value="${pageInfo.curPage}" type="hidden"/>
		<input type="hidden" name="spaceId" value="${spaceId}">
		<input type="hidden" name="view" value="view">
		<input type="hidden" name="id" value="${productionTask.id}">
		<input type="hidden" name="tabID" value="storagedetailButt">
	</form>
	
	<!-- 审核生效 -->
	<form action="${menu2Id}!auditStatus.jspa" id="auditForm" method="post">
		<input type="hidden" name="productionTask.id" value="${productionTask.id}">
		<input type="hidden" name="menuId" value="${menuId}">
		<input type="hidden" name="menu2Id" value="${menu2Id}">
		<input type="hidden" name="spaceId" value="${spaceId}">
	</form>
	<%@ include file="/pages/common/footer.jsp"%>
	<script src="<%=path%>/js/bootstrap.js"></script>
	<script src="<%=path%>/js/collapsePulg.js"></script>
	<script src="<%=path%>/js/querycommon.js"></script>
	<script type="text/javascript">
		$("[rel=tooltip]").tooltip();
		var menuId = '${menuId}';
		var menu2Id = '${menu2Id}';
		var spaceId = '${spaceId}';
		var url = $("#" + menu2Id).attr('url');
		var totalPage = ${pageInfo.totalPage};
		var totalRow = ${pageInfo.totalRow};
		var pageSize = ${pageInfo.pageSize};
		var curPage = ${pageInfo.curPage};
		var tabID = "${tabID}";
		
		//审核生效状态
		$("#formChangefirmBtn").click(function(){
			//提交表单
			$("#auditForm").submit();
		});
		
	</script>
</body>
</html>