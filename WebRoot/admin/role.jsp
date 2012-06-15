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
							<a href="#">角色管理</a>
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
							t = new SimpleTable("roleQueryTable", {
								pageSize : 8,
								param : 'searchDiv',
								url : '<%=request.getContextPath()%>/admin/role_query.do',
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
										width : '5%'
									},
									name : {
										header : '角色名称',
										width : '15%'
									},
									code : {
										header : '角色代码',
										width : '28%'
									},
									description : {
										header : '角色描述',
										width : '30%'
									},
									disabled : {
										header : '是否禁用',
										width : '10%'
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
										return html;
									}
								},
								info : {
									//pageSizeSelect:'', //不显示此控件
									pageSizeSelect : '显示{0}条记录',
									pagingInfo : '目前显示{0}-{1}条记录，共计：{2}'
								}
							});

							$('#role_add_form_div').dialog({
								draggable : false,
								title : '添加/修改角色',
								width : 620,
								modal : true,
								// position:'center', 'left', 'right', 'top', 'bottom'.  or [350,100]
								resizable : false,
								draggable : true,
								autoOpen : false,
								// show:'slide',
								height : 210,
								buttons : {
									"OK" : function() {
										ajaxSubmitForm("role_add_form", editCallback);
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
								url : "<%=request.getContextPath()%>/admin/role/" + id + ".do",
								data : '',
								success : function(data, textStatus, XMLHttpRequest) {
									fillForm("role_add_form", data);
									$("#role_add_form_div").dialog("open");
								}
							});
						}
					</script>
					<div id="searchDiv" style="width: 90%;">
						<table width="100%">
							<tr>
								<td width="20%">角色名称：</td><td width="30%">
								<input name="name" />
								</td>
								<td width="20%">角色代码：</td><td width="30%">
								<input name="code" />
								</td>
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
								document.forms['role_add_form'].reset();
								var dialog = $('#role_add_form_div').dialog("open");
							}

							function assignAuthorities() {
								var ids = t.getSelected();
								if(ids.length == 0 || ids.length > 1) {
									showMsg("<font colo='red'>请选择一条记录!</font>");
								} else {
									var selector = new MultiSelector("authority_multiselector", {
										url : "<%=request.getContextPath()%>/admin/role/assign/" + ids[0] + ".do",
										allData : 'available',
										selectedData : 'selected',
										valueFiled : "id",
										displayField : "name",
										selectorHeight : '240px',
										autoLoad : true
									});
									$("#authority_multiselector").dialog({
										title : '为角色分配权限',
										modal : true,
										width : 390,
										height : 360,
										close : function() {
											$(this).empty();
										},
										buttons : {
											"确认" : function() {
												$(this).dialog("close");
												var authorities = selector.val();
												confirmAssignAuthority(ids[0], authorities);
											},
											"取消" : function() {
												$(this).dialog("close");
											}
										}
									});
								}
							}

							function confirmAssignAuthority(id, authorities) {
								$.ajax({
									dataType : "json",
									type : "post",
									timeout : 30000,
									url : "<%=request.getContextPath()%>/admin/assignAuthority.do",
									data : 'id=' + id + "&authorities=" + authorities,
									success : function(data, textStatus, XMLHttpRequest) {
										showMsg(data);
									}
								});
							}
						</script>
						</script> <a href="javascript:showAddForm();">添加新角色</a>
						&nbsp;&nbsp; <a href="javascript:assignAuthorities();">分配权限</a>
						<br />
						<br/>
					</div>
					<div id="tableDiv" style="width: 100%;">
						<table id="roleQueryTable"></table>
					</div>
				</div>
				<div id="role_add_form_div" style="display: none;">
					<form id="role_add_form" action="<%=request.getContextPath()%>/admin/role_edit.do"  method="post">
						<input type="text" style="display: none;" name="id" />
						<table>
							<tr>
								<td>角色名称：</td><td>
								<input type="text" name="name" />
								</td>
								<td>角色代码：</td>
								<td>
								<input type="text" name="code" />
								</td>
							</tr>
							<tr>
								<td>是否禁用：</td><td colspan="3">
								<input id="disabled_true" type="radio" name="disabled" value="true" />
								<label for="disabled_true">禁用</label>
								<input id="disabled_false" type="radio" name="disabled" checked="checked" value="false" />
								<label for="disabled_false">不禁用</label></td>
							</tr>
							<tr>
								<td>角色描述：</td>
								<td colspan="3">
								<input type="text" size="54" name="description"/>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div id="authority_multiselector"></div>
			</div>
			<%@ include file="../foot.jsp"%>
		</div>
	</body>
</html>
