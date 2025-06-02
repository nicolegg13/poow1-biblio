<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Home - Sistema Biblioteca</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
            color: #333;
        }
        h1 {
            color: #007bff;
        }
        p {
            margin-bottom: 20px;
        }
        nav ul {
            list-style-type: none;
            padding: 0;
        }
        nav li {
            margin-bottom: 10px;
        }
        nav a {
            text-decoration: none;
            color: #007bff;
            font-weight: bold;
            padding: 8px 12px;
            border: 1px solid #007bff;
            border-radius: 5px;
            display: inline-block;
            transition: background-color 0.3s, color 0.3s;
        }
        nav a:hover {
            background-color: #007bff;
            color: white;
        }
    </style>
</head>
<body>

<c:if test="${empty sessionScope.usuarioLogado}">
    <script>window.location='${pageContext.request.contextPath}/login';</script>
</c:if>

<h1>Bem-vindo, ${usuarioLogado.nome_us}!</h1>
<p>Você está logado como: ${usuarioLogado.tipo_us}</p>

<nav>
    <ul>
        <c:if test="${usuarioLogado.tipo_us eq 'ADMIN'}">
            <li><a href="${pageContext.request.contextPath}/autores">Gerenciar Autores</a></li>
            <li><a href="${pageContext.request.contextPath}/livros">Gerenciar Livros</a></li>
            <li><a href="${pageContext.request.contextPath}/emprestimos">Gerenciar Empréstimos</a></li>
        </c:if>
        <c:if test="${usuarioLogado.tipo_us eq 'USUARIO'}">
        <li><a href="${pageContext.request.contextPath}/meus-emprestimos">Meus Empréstimos</a></li>
        </c:if>
        <li><a href="${pageContext.request.contextPath}/logout">Sair</a></li>
    </ul>
</nav>
</body>
</html>