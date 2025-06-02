<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Acesso Negado</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            color: #333;
            text-align: center;
            padding-top: 50px;
        }
        .container {
            background-color: #fff;
            margin: 0 auto;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            width: 500px;
        }
        h1 {
            color: #dc3545; /* Bootstrap danger red */
        }
        p {
            font-size: 1.1em;
            margin-bottom: 20px;
        }
        a {
            color: #007bff;
            text-decoration: none;
            font-weight: bold;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Acesso Negado!</h1>
    <p>${mensagemAcessoNegado}</p>
    <p>Você não tem permissão para acessar esta página.</p>
    <p><a href="${pageContext.request.contextPath}/home">Voltar para Home</a></p>
</div>
</body>
</html>