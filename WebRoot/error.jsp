<%@page contentType="text/html; charset=UTF-8" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>错误页面</title>
		<link rel="stylesheet" type="text/css" href="css/main.css" media="all" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common-simpletable.css" media="all" />
	</head>
	<body>
		<div id="page">
			<div id="head">
				<br />
				<h1> 错误信息</h1>
			</div>
			<div id="body">
				<hr style="clear: both;" />
				<!-- Main -->
				<div id="main">
					<center>
						<font color="red"><%=request.getAttribute("error_msg") != null ? request.getAttribute("error_msg")  : "系统错误，如需帮助，请联系管理员。"%></font>
						<br/>
						<div style="margin:50px 200px;">
							<a href="#"
							style="color: green; text-decoration: underline; font-size: 14px;"
							onclick="top.location='index.jsp';">返回首页</a>
						</div>
					</center>
				</div>
			</div>
		</div>
	</body>
</html>
