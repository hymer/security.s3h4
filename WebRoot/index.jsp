<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="taglib.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>深圳市南山区科技企业孵化器</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/main.css" media="all" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common-simpletable.css" media="all" />
	</head>
	<body>
		<c:if test="${sessionScope.user eq null}">
			<script type="text/javascript">
				window.location = "login.html";
			</script>
		</c:if>
		<div id="page">
			<%@ include file="top.jsp"%>
			<div id="body">
				<!-- Main -->
				<div id="main">
					<center style="font-size:15pt;">
						<c:if test='${user.role.code eq "__SUPER_ROLE_FLAG__"}'>
							<a href="./admin/admin.jsp">后台管理</a>
							<br />
							<br />
						</c:if>
					</center>
				</div>
			</div>
			<%@ include file="foot.jsp"%>
		</div>
	</body>
</html>
