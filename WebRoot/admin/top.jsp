<%@ page language="java" pageEncoding="utf-8"%>
<div id="head">
	<br />
	<h1> 后台管理 </h1>
	<div>
		<span></span>
		<span style="float: right;"> 欢迎您:<font color="green">${sessionScope.user.userName}</font>, <a href="<%=request.getContextPath()%>/j_security_logout.do">注销</a> </span>
		<br />
		<hr style="clear: both;" />
	</div>
</div>
