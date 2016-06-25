<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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
			<li><a href="" id="navigation"></a> <span class="divider">/</span></li>
			<li class="active">编辑</li>
		</ul>
		
		<div class="container-fluid">
		<form id="editForm" class="form-horizontal" action="${menu2Id}!save.jspa" method="post">
			<div class="row-fluid">
				<div class="row-fluid">
				
					<div class="btn-toolbar">
						<button class="btn btn-primary" type="submit">
							<i class="icon-save"></i> 保存
						</button>
						<a class="btn" id="backList" href="">
							返回</a>
						<div class="btn-group"></div>
					</div>
					<div class="well">
						<div id="myTabContent" class="tab-content">
							<div class="tab-pane active" id="home">
									<input type="hidden" name="id" value="${warehouse.id}">
									<input type="hidden" name="menuId" value="${menuId}">
									<input type="hidden" name="menu2Id" value="${menu2Id}">
									<input type="hidden" name="spaceId" value="${spaceId}">
									<div class="control-group" id="name_div">
										<label class="control-label" for="name_input">名称：</label>
										<div class="controls">
											<input type="text" data-required="true" maxlength="30"  id="name_input" name="warehouse.name" value="${warehouse.name}" class="input-xlarge">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="inputaddress">地址：</label>
										<div class="controls">
											<input type="text" id="inputaddress" maxlength="100"  name="warehouse.address" value="${warehouse.address}" class="input-xlarge">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="inputenterpriseID">企业：</label>
										<div class="controls">
											<s:select id="inputenterpriseID"  list="enterpriseList" listKey="id" listValue="name"
													name="warehouse.enterpriseID" cssClass="input-xlarge">
													
												</s:select>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="inputenabled">状态:</label>
											<div class="controls">
												<select id="inputenabled" class="input-xlarge"
													name="warehouse.enabled">
													<option value="0" selected="selected">激活</option>
													<option value="1">未激活</option>
												</select>
											</div>
									</div>
									
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
		</div>
		</div>
		<%@ include file="/pages/common/footer.jsp"%>
		<script src="<%=path%>/js/bootstrap.js"></script>
		<script src="<%=path %>/js/collapsePulg.js"></script>
		<script src="<%=path %>/js/common.js"></script>
		<script src="<%=path %>/js/jquery-validate.js"></script>
			<script src="<%=path%>/js/datetimepicker/bootstrap-datetimepicker.js"></script>
		<script src="<%=path%>/js/select2/select2.js"></script>
	<script src="<%=path%>/js/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript">
			$("[rel=tooltip]").tooltip();
			var menuId='${menuId}';
			var menu2Id='${menu2Id}';
			var spaceId = '${spaceId}';
			var url=$("#"+menu2Id).attr('url');
			
			$("select").select2();
			var enabled ='${warehouse.enabled}';
			if ( enabled != null)
			{
				$("#inputenabled").val(enabled).trigger("change");
			}
		</script>
</body>
</html>