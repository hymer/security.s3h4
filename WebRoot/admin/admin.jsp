<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../taglib.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>后台管理</title>
		<link rel="stylesheet" type="text/css" href="../css/main.css" media="all" />
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
					欢迎来到管理员界面！请点击左边栏菜单项进入相应功能页面。
				</div>
			</div>
			<%@ include file="../foot.jsp"%>
		</div>
	</body>
</html>
