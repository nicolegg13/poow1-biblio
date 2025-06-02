<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login - Sistema Biblioteca</title>
    <style>
        .login-container { width: 300px; margin: 100px auto; }
        .error { color: red; }
    </style>
</head>
<body>
<div class="login-container">
    <h2>Login</h2>
    <c:if test="${not empty erro}">
        <p class="error">${erro}</p>
    </c:if>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <div>
            <label>Email:</label>
            <input type="email" name="email" required>
        </div>
        <div>
            <label>Senha:</label>
            <input type="password" name="senha" required>
        </div>
        <button type="submit">Entrar</button>
    </form>
</div>
</body>
</html>
