<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<title>登录页面</title>
</head>
<body>
	<form action="/weixinauth/callback" method="post">
		<input type="text" name="account"/><br>
		<input type="password" name="password" /><br>
		<input type="hidden" name="openid" value="${openid }" /><br>
		<input type="submit" value="登录并绑定" />
	</form>
</body>
</html>