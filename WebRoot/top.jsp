<%@ page language="java" pageEncoding="utf-8"%>
<div id="head">
	<br />
	<h1> 科技企业孵化器 （南山区）</h1>
	<div>
		<span><a href="<%=request.getContextPath()%>/index.jsp"><font color="green">回到首页</font></a></span>
		<span style="float: right;"> 欢迎您:<font color="green">${sessionScope.user.userName}</font>, <a href="<%=request.getContextPath()%>/j_security_logout.do">注销</a> </span>
		<hr style="clear: both;" />
	</div>
</div>
