<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@  page import="com.zh.base.util.JspUtil" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
					<span class="number"><s:property value="#session.taskSize"/></span>tasks
				</p>
				<p class="stat">
					<span class="number">15</span>waiting
				</p>
			</div>

			<h1 class="page-title" id="menu2Name">&nbsp;</h1>
		</div>

		<ul class="breadcrumb">
			<li><a href="<%=path%>/home/main.jspa">主页</a> <span class="divider">/</span></li>
			<li class="active" id="navigation"></li>
		</ul>

		<shiro:hasPermission name="salesOrder:view">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="row-fluid">
					<div>
						<form action="${menu2Id}.jspa?menuId=${menuId}&menu2Id=${menu2Id}" id="queryForm" method="post">
							<input id="curPage" name="pageInfo.curPage" value="${pageInfo.curPage}" type="hidden"/>
							<input type="hidden" name="spaceId" value="${spaceId}">
							<dir class="row">
								<div class="span5">
									<label class="control-label">销售订单：
										<input type="text" maxlength="25" id="inputOdrerId" name="salesOrderPrimary.orderID"
											value="${salesOrderPrimary.orderID}" class="input-large">
										</label>
								</div>
								<div class="span4">
									<label class="control-label">合同单号：
										<input type="text" maxlength="25" id="inputContractNumber" name="salesOrderPrimary.contractNumber"
											value="${salesOrderPrimary.contractNumber}" class="input-large">
									</label>
								</div>
							</dir>
							
							<dir class="row">
								<div class="span5">
									<label class="control-label">订单状态：
										<select id="inputStatus"  name="storagePrimary.status" class="input-medium" placeholder="状态" >
											<option value=""></option>
											<option value="0">发起</option>
											<option value="1">采购</option>
											<option value="2">生产</option>
											<option value="3">交付</option>
											<option value="4">未结账</option>
											<option value="5">完成</option>
										</select>
									</label>		
								</div>
								<div class="span4">
									
								</div>
								
								<div class="span3">
									<button class="btn" type="button" id="btnSubmit">
										<i class=" icon-search"></i> 搜索
									</button>
			
									<button class="btn" type="button" id="btnClear">
										<i class="icon-remove"></i> 清除
									</button>
								</div>
							</dir>
						</form>
					</div>
					
					<div class="btn-toolbar">
						<shiro:hasPermission name="salesOrder:add">
						<a class="btn btn-primary" href="${menu2Id}!editor.jspa?menuId=${menuId}&menu2Id=${menu2Id}&spaceId=${spaceId}">
							<i class="icon-plus"></i> 新增
						</a>
						</shiro:hasPermission>
						<div class="btn-group"></div>
					</div>
					<div class="well">
						<table class="table">
							<thead>
								<tr>
									<th>序号</th>
									<th>销售订单</th>
									<th>合同单号</th>
									<th>客户</th>
									<th>检查日期</th>
									<th>装货港口</th>
									<th>订单状态</th>
									<th style="width: 40px;">操作</th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="salesOrderPrimaryList" var="tp" status="index">
									<tr>
									<td><s:property value="#index.index + 1"/></td>
										<td><s:property value="#tp.orderID"/></td>
										<td><s:property value="#tp.contractNumber"/></td>
										<td>
											<s:set id="customerID" value="#tp.customerID"></s:set>
											<%=userName.queryCustomer(String.valueOf(request.getAttribute("customerID"))) %>
										</td>
										<td><s:property value="#tp.inspection"/></td>
										<td><s:property value="#tp.loadingPort"/></td>
										<td>
											<s:if test="#tp.status == 0">发起</s:if>
											<s:elseif test="#tp.status == 1">采购</s:elseif>
											<s:elseif test="#tp.status == 2">生产</s:elseif>
											<s:elseif test="#tp.status == 3">交付</s:elseif>
											<s:elseif test="#tp.status == 4">未结账</s:elseif>
											<s:elseif test="#tp.status == 5">完成</s:elseif>
										</td>
										
										<td>
											<shiro:hasPermission name="salesOrder:edit">
											<a title="修改" style="margin: 0px 3px;" href="${menu2Id}!editor.jspa?id=<s:property value='#tp.id'/>&menuId=${menuId}&menu2Id=${menu2Id}&spaceId=${spaceId}"><i class="icon-pencil"></i></a>
											</shiro:hasPermission> 
											<shiro:hasPermission name="salesOrder:view">
											<a title="查看" style="margin: 0px 3px;" href="${menu2Id}!editor.jspa?id=<s:property value='#tp.id'/>&view=view&menuId=${menuId}&menu2Id=${menu2Id}&spaceId=${spaceId}"><i class="icon-search"></i></a>
											</shiro:hasPermission> 
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</div>
					<div class="pagination">
						<ul id="pagination">
						</ul>					
					</div>
				</div>
			</div>
		</div>
		</shiro:hasPermission>
		<shiro:lacksPermission name="salesOrder:view">
		<%@ include file="/pages/common/unauthorized.jsp"%>
		</shiro:lacksPermission>
	</div>
	<!-- 
	<form action="${menu2Id}.jspa?menuId=${menuId}&menu2Id=${menu2Id}" id="queryForm" method="post">
		<input id="curPage" name="pageInfo.curPage" value="${pageInfo.curPage}" type="hidden"/>
		<input type="hidden" name="spaceId" value="${spaceId}">
	</form>
	 -->
	<%@ include file="/pages/common/footer.jsp"%>
	<script src="<%=path%>/js/bootstrap.js"></script>
	<script src="<%=path %>/js/collapsePulg.js"></script>
	<script type="text/javascript">
		$("[rel=tooltip]").tooltip();
		var menuId='${menuId}';
		var menu2Id='${menu2Id}';
		var totalPage = ${pageInfo.totalPage};
		var totalRow = ${pageInfo.totalRow};
		var pageSize = ${pageInfo.pageSize};
		var curPage = ${pageInfo.curPage};
		$(function() {
			$('.demo-cancel-click').click(function() {
				return false;
			});
			var headText = $("#" + menu2Id).text();
			$("#menu2Name").text(headText);
			$("#navigation").text(headText);
			//展开一级菜单
			collapseMenu(menuId);
			
			//提交按钮
			$("#btnSubmit").click(function(){
				$('#curPage').val("1");
				$('#queryForm').submit();
			});
			
			//清空按钮
			$("#btnClear").click(function(){
				$("#inputOdrerId").val("");
				$("#inputContractNumber").val("");
				$("#inputStatus").val("");
				
				$('#curPage').val("");
			});
			
			$.jqPaginator('#pagination', {
				//设置分页的总页数
		        totalPages: totalPage,
		        //设置分页的总条目数
		        totalCounts:totalRow,
		        pageSize:pageSize,
		        //最多显示的页码
		        visiblePages: 10,
		        currentPage: curPage,
		        onPageChange: function (num, type) {
		           if("init"==type)
		        	{
		        	 	return false;  
		        	}
		           $('#curPage').val(num);
		        	$('#queryForm').submit();
		        	//document.getElementsByName("operateForm")[0].submit(); 
		        }
		    });
			
		});
		
		function addObject(name)
		{
			var url=url + "?menuId="+menuId+"&menu2Id="+menu2Id;
			
		}
		
	</script>
</body>
</html>