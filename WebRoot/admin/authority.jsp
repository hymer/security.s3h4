<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../taglib.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>后台管理</title>
		<script type="text/javascript" src="../scripts/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="../scripts/jquery-ui-1.8.20.custom.min.js"></script>
		<script type="text/javascript" src="../scripts/jquery.form.js"></script>
		<script type="text/javascript" src="../scripts/common-simpletable.js"></script>
		<script type="text/javascript" src="../scripts/common-tools.js"></script>
		<script type="text/javascript" src="../scripts/common-multiselector.js"></script>
		<link rel="stylesheet" type="text/css" href="../css/main.css" media="all" />
		<link rel="stylesheet" type="text/css" href="../css/jquery-ui-1.8.20.custom.css" media="all" />
		<link rel="stylesheet" type="text/css" href="../css/common-simpletable.css" media="all" />
	</head>
	<body>
		<div id="page">
			<%@ include file="top.jsp"%>
			<div id="body">
				<div id="left">
					<ul class="navigation">
						<li>
							<a href="user.jsp" >用户管理</a>
						</li>
						<li>
							<a href="role.jsp">角色管理</a>
						</li>
						<c:if test='${user.role.code eq "__SUPER_ROLE_FLAG__"}'>
							<li>
								<a href="authority.jsp">权限管理</a>
							</li>
							<li>
								<a href="resource.jsp">资源管理</a>
							</li>
						</c:if>
					</ul>
				</div>
				<!-- Main -->
				<div id="main">
					<script type="text/javascript" charset="utf-8">
						var t = null;
						$(function() {
							t = new SimpleTable("authorityQueryTable", {
								pageSize : 8,
								param : 'searchDiv',
								url : '<%=request.getContextPath()%>/admin/authority_query.do',
								sortables : ["name"],
								checkMode : 'single',
								pagingOptions : {
									first : true,
									end : true,
									go : true,
									firstHtml : '<a href="#">首页</a>',
									lastHtml : '<a href="#">上一页</a>',
									nextHtml : '<a href="#">下一页</a>',
									endHtml : '<a href="#">尾页</a>'
								},
								//checkMode : 'single',
								// scrollX : true,
								columns : {
									checkbox : {
										width : '4%'
									},
									name : {
										header : '权限名称',
										width : '25%'
									},
									description : {
										header : '权限描述',
										width : '40%'
									},
									disabled : {
										header : '是否禁用',
										width : '15%'
									},
									ops : {
										header : '操作',
										width : '12%'
									}
								},
								formatters : {
									disabled : function(v, obj) {
										if(v) {
											return '<font color="red">已禁用</font>';
										} else {
											return '<font color="green">未禁用</font>';
										}
									},
									ops : function(v, obj) {
										var html = '';
										html += "<a href='javascript:doEdit(" + obj.id + ");'>修改</a>";
										html += '&nbsp;&nbsp;<a href="javascript:doDisabled(' + obj.id + ',\'' + obj.name + '\');">禁用</a>';
										return html;
									}
								},
								info : {
									//pageSizeSelect:'', //不显示此控件
									pageSizeSelect : '显示{0}条记录',
									pagingInfo : '目前显示{0}-{1}条记录，共计：{2}'
								}
							});

							$('#authority_add_form_div').dialog({
								draggable : false,
								title : '添加/修改权限',
								width : 620,
								modal : true,
								// position:'center', 'left', 'right', 'top', 'bottom'.  or [350,100]
								resizable : false,
								draggable : true,
								autoOpen : false,
								// show:'slide',
								height : 180,
								buttons : {
									"OK" : function() {
										ajaxSubmitForm("authority_add_form", editCallback);
									},
									"Cancel" : function() {
										$(this).dialog("close");
									}
								}
							});
						});
						function editCallback() {
							t.doSearch();
						}

						function doEdit(id) {
							$.ajax({
								dataType : "json",
								type : "get",
								timeout : 30000,
								url : "<%=request.getContextPath()%>/admin/authority/" + id + ".do",
								data : '',
								success : function(data, textStatus, XMLHttpRequest) {
									fillForm("authority_add_form", data);
									$("#authority_add_form_div").dialog("open");
								}
							});
						}

						function doDisabled(id, name) {
							window.open("doDisabled：" + id + ".do");
						}
					</script>
					<div id="searchDiv" style="width: 90%;">
						<table width="100%">
							<tr>
								<td width="15%">权限名称：</td><td width="30%">
								<input name="name" />
								</td>
								<td></td>
								<td></td>
							</tr>
						</table>
					</div>
					<div align="right">
						<input type="button" id="searchButton" value="查询" />
						<script type='text/javascript'>
							$("#searchButton").click(function() {
								t.doSearch();
							});

						</script>
					</div>
					<div>
						<script type="text/javascript">
							function showAddForm() {
								document.forms['authority_add_form'].reset();
								var dialog = $('#authority_add_form_div').dialog("open");
							}

							function assignResources() {
								var ids = t.getSelected();
								if(ids.length == 0 || ids.length > 1) {
									showMsg("<font color='red'>请选择一条记录!</font>");
								} else {
									var selector = new MultiSelector("resource_multiselector", {
										url : "<%=request.getContextPath()%>/admin/authority/assign/" + ids[0] + ".do",
										allData : 'available',
										selectedData : 'selected',
										valueFiled : "id",
										displayField : "name",
										selectorHeight : '240px',
										autoLoad : true
									});
									$("#resource_multiselector").dialog({
										title : '为权限分配资源',
										modal : true,
										width : 390,
										height : 360,
										close : function() {
											$(this).empty();
										},
										buttons : {
											"确认" : function() {
												$(this).dialog("close");
												var resources = selector.val();
												confirmAssignResource(ids[0], resources);
											},
											"取消" : function() {
												$(this).dialog("close");
											}
										}
									});
								}
							}

							function confirmAssignResource(id, resources) {
								$.ajax({
									dataType : "json",
									type : "post",
									timeout : 30000,
									url : "<%=request.getContextPath()%>/admin/assignResource.do",
									data : 'id=' + id + "&resources=" + resources,
									success : function(data, textStatus, XMLHttpRequest) {
										showMsg(data);
									}
								});
							}
						</script>
						<a href="javascript:showAddForm();">添加新权限</a>
						&nbsp;&nbsp; <a href="javascript:assignResources();">分配资源</a>
						<br />
						<br/>
					</div>
					<div id="tableDiv" style="width: 100%;">
						<table id="authorityQueryTable"></table>
					</div>
				</div>
				<div id="authority_add_form_div" style="display: none;">
					<form id="authority_add_form" action="<%=request.getContextPath()%>/admin/authority_edit.do"  method="post">
						<input type="text" style="display: none;" name="id" />
						<table>
							<tr>
								<td>权限名称：</td><td>
								<input type="text" name="name" />
								</td>
								<td>是否禁用：</td><td>
								<input id="disabled_true" type="radio" name="disabled" value="true" />
								<label for="disabled_true">是</label>
								<input id="disabled_false" type="radio" name="disabled" checked="checked" value="false" />
								<label for="disabled_false">否</label></td>
							</tr>
							<tr>
								<td>权限描述：</td>
								<td colspan="3">
								<input type="text" size="54" name="description"/>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div id="resource_multiselector"></div>
			</div>
			<%@ include file="../foot.jsp"%>
		</div>
	</body>
</html>
