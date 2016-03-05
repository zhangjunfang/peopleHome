<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html lang="zh">
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
    <title>登录</title>
    <script
            type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/login.js"></script>
    <link
            href="${pageContext.request.contextPath}/static/style/login.css"
            rel="stylesheet"
            type="text/css">
</head>
<body>
<h2>登录</h2>
<h3 class="red">${message}</h3>
<form action="${pageContext.request.contextPath}/login" method="post">
    姓名：<input type="text" name="username"/><br/>
    密码：<input type="password" name="password"/><br/>
    <input type="submit" value="登录"/>
</form>
</body>
</html>
