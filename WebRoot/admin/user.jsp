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
		<script type="text/javascript" src="../scripts/common-ajaxselector.js"></script>
		<script type="text/javascript" src="../scripts/common-tools.js"></script>
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
							<a href="#" >用户管理</a>
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
							t = new SimpleTable("userQueryTable", {
								pageSize : 8,
								param : 'searchDiv',
								url : '<%=request.getContextPath()%>/admin/user_query.do',
								sortables : ["name"],
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
									userName : {
										header : '用户名称',
										width : '19%'
									},
									roleName : {
										header : '用户角色',
										width : '28%'
									},
									createTime : {
										header : '创建时间',
										width : '20%'
									},
									disabled : {
										header : '是否禁用',
										width : '10%'
									},
									ops : {
										header : '操作',
										width : '18%'
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
										html += '&nbsp;&nbsp;<a href="javascript:changePassword(' + obj.id + ');">密码重置</a>';
										return html;
									}
								},
								info : {
									//pageSizeSelect:'', //不显示此控件
									pageSizeSelect : '显示{0}条记录',
									pagingInfo : '目前显示{0}-{1}条记录，共计：{2}'
								}
							});

							$('#user_add_form_div').dialog({
								draggable : false,
								title : '添加用户',
								width : 620,
								modal : true,
								// position:'center', 'left', 'right', 'top', 'bottom'.  or [350,100]
								resizable : false,
								draggable : true,
								autoOpen : false,
								// show:'slide',
								height : 220,
								buttons : {
									"OK" : function() {
										ajaxSubmitForm("user_add_form", editCallback);
									},
									"Cancel" : function() {
										$(this).dialog("close");
									}
								}
							});
							$('#user_edit_form_div').dialog({
								draggable : false,
								title : '修改用户',
								width : 620,
								modal : true,
								resizable : false,
								draggable : true,
								autoOpen : false,
								height : 180,
								buttons : {
									"OK" : function() {
										ajaxSubmitForm("user_edit_form", editCallback);
									},
									"Cancel" : function() {
										$(this).dialog("close");
									}
								}
							});
							$('#change_password_form_div').dialog({
								draggable : false,
								title : '重置密码',
								width : 320,
								modal : true,
								resizable : false,
								draggable : true,
								autoOpen : false,
								height : 180,
								buttons : {
									"OK" : function() {
										ajaxSubmitForm("change_password_form");
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
								url : "<%=request.getContextPath()%>/admin/user/" + id + ".do",
								data : '',
								success : function(data, textStatus, XMLHttpRequest) {
									document.forms['user_edit_form'].reset();
									fillForm("user_edit_form", data);
									$("#user_edit_form_div").dialog("open");
								}
							});
						}

						function changePassword(id) {
							document.forms['change_password_form'].reset();
							fillForm("change_password_form", {
								id : id
							});
							$("#change_password_form_div").dialog("open");
						}
					</script>
					<div id="searchDiv" style="width: 90%;">
						<table width="100%">
							<tr>
								<td width="20%">用户名称：</td><td width="30%">
								<input name="userName" />
								</td>
								<td width="20%">用户角色：</td><td width="30%">
								<select name="roleId" class="ajaxSelector" autoLoad="false"
								url="<%=request.getContextPath()%>/admin/role/list.do" dataRoot="roles"
								valueField="id" displayField="name">
									<option value="" selected="selected">所有</option>
								</select></td>
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
								document.forms['user_add_form'].reset();
								$('#user_add_form_div').dialog("open");
							}
						</script>
						<a href="javascript:showAddForm();">添加新用户</a>
						<br />
						<br/>
					</div>
					<div id="tableDiv" style="width: 100%;">
						<table id="userQueryTable"></table>
					</div>
				</div>
				<div id="user_add_form_div" style="display: none;">
					<form id="user_add_form" action="<%=request.getContextPath()%>/admin/user_edit.do"  method="post">
						<input type="text" style="display: none;" name="id" />
						<table>
							<tr>
								<td>用户名称：</td><td>
								<input type="text" name="userName" />
								</td>
								<td>角色：</td><td><select name="roleId" class="ajaxSelector" autoLoad="true"
								url="<%=request.getContextPath()%>/admin/role/list.do" dataRoot="roles"
								valueField="id" displayField="name"></select></td>
							</tr>
							<tr>
								<td>密码：</td><td>
								<input type="password" name="password1" />
								</td>
								<td>密码确认：</td><td>
								<input type="password" name="password2" />
								</td>
							</tr>
							<tr>
								<td>是否禁用：</td><td colspan="3">
								<input id="disabled_true" type="radio" name="disabled" value="true" />
								<label for="disabled_true">禁用</label>
								<input id="disabled_false" type="radio" name="disabled" checked="checked" value="false" />
								<label for="disabled_false">不禁用</label></td>
							</tr>
						</table>
					</form>
				</div>
				<div id="user_edit_form_div" style="display: none;">
					<form id="user_edit_form" action="<%=request.getContextPath()%>/admin/user_edit.do"  method="post">
						<input type="text" style="display: none;" name="id" />
						<table>
							<tr>
								<td>用户名称：</td><td>
								<input type="text" name="userName" readonly="readonly" />
								</td>
								<td>角色：</td><td><select name="roleId" class="ajaxSelector" autoLoad="true"
								url="<%=request.getContextPath()%>/admin/role/list.do" dataRoot="roles"
								valueField="id" displayField="name"></select></td>
							</tr>
							<tr>
								<td>是否禁用：</td><td colspan="3">
								<input id="disabled_true_edit" type="radio" name="disabled" value="true" />
								<label for="disabled_true_edit">禁用</label>
								<input id="disabled_false_edit" type="radio" name="disabled" checked="checked" value="false" />
								<label for="disabled_false_edit">不禁用</label></td>
							</tr>
						</table>
					</form>
				</div>
				<div id="change_password_form_div" style="display: none;">
					<form id="change_password_form" action="<%=request.getContextPath()%>/admin/password/reset.do"  method="post">
						<input type="text" style="display: none;" name="id" />
						<table>
							<tr>
								<td>新密码：</td><td>
								<input type="password" name="password1"  />
								</td>
							</tr>
							<tr>
								<td>密码确认：</td><td>
								<input type="password" name="password2"  />
								</td>
							</tr>
						</table>
				</div>
			</div>
			<%@ include file="../foot.jsp"%>
		</div>
	</body>
</html>
