<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
		
		<shiro:hasPermission name="role:view">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="row-fluid">
					<div class="btn-toolbar">
						<shiro:hasPermission name="role:add">
						<a class="btn btn-primary" href="role!editor.jspa?menuId=${menuId}&menu2Id=${menu2Id}&spaceId=${spaceId}">
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
									<th>名称</th>
									<th>描述</th>
									<th>创建时间</th>
									<th>修改时间</th>
									<th style="width: 26px;"></th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="roleList" var="role" status="index">
									<tr>
										<td><s:property value="#index.index + 1" /></td>
										<td><s:property value="#role.name" /></td>
										<td><s:property value="#role.describe" /></td>
										<td><s:property value="#role.createtime" /></td>
										<td><s:property value="#role.updatetime" /></td>
										<td>
										<shiro:hasPermission name="role:edit">
											<a href="role!editor.jspa?id=<s:property value='#role.id'/>&menuId=${menuId}&menu2Id=${menu2Id}&spaceId=${spaceId}"><i
												class="icon-pencil"></i></a> <!-- 
											<a href="#myModal" role="button" data-toggle="modal"><i class="icon-remove"></i></a>
											 -->
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
		<shiro:lacksPermission name="role:view">
		<%@ include file="/pages/common/unauthorized.jsp"%>
		</shiro:lacksPermission>
	</div>
	<form action="${menu2Id}.jspa?menuId=${menuId}&menu2Id=${menu2Id}"
		id="queryForm" method="post">
		<input id="curPage" name="pageInfo.curPage"	value="${pageInfo.curPage}" type="hidden"/>
		<input type="hidden" name="spaceId" value="${spaceId}">
	</form>
	<%@ include file="/pages/common/footer.jsp"%>
	<script src="<%=path%>/js/bootstrap.js"></script>
	<script src="<%=path%>/js/collapsePulg.js"></script>
	<script type="text/javascript">
		$("[rel=tooltip]").tooltip();
		var menuId = '${menuId}';
		var menu2Id = '${menu2Id}';
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

			$.jqPaginator('#pagination', {
				//设置分页的总页数
				totalPages : totalPage,
				//设置分页的总条目数
				totalCounts : totalRow,
				pageSize : pageSize,
				//最多显示的页码
				visiblePages : 10,
				currentPage : curPage,
				onPageChange : function(num, type) {
					if ("init" == type) {
						return false;
					}
					$('#curPage').val(num);
					$('#queryForm').submit();
					//document.getElementsByName("operateForm")[0].submit(); 
				}
			});
		});
	</script>
</body>
</html>