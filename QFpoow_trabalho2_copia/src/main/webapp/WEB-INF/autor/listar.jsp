<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Autores</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
            color: #333;
        }
        h1 {
            color: #007bff;
            margin-bottom: 20px;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
            background-color: #fff;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #e9ecef;
            color: #555;
            font-weight: bold;
        }
        tr:nth-child(even) {
            background-color: #f8f9fa;
        }
        tr:hover {
            background-color: #e2e6ea;
        }
        .actions {
            white-space: nowrap;
            width: 150px; /* Adjust as needed */
        }
        .actions a {
            text-decoration: none;
            padding: 5px 10px;
            border-radius: 4px;
            margin-right: 5px;
        }
        .actions a:hover {
            opacity: 0.8;
        }
        .actions a:first-child { /* Editar */
            background-color: #ffc107; /* Yellow */
            color: #333;
        }
        .actions a:last-child { /* Excluir */
            background-color: #dc3545; /* Red */
            color: white;
        }
        .btn-novo {
            background-color: #007bff;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            font-weight: bold;
            margin-bottom: 20px;
            display: inline-block;
            transition: background-color 0.3s;
        }
        .btn-novo:hover {
            background-color: #0056b3;
        }
        p[style="color: green;"] {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        div[style="margin-top: 20px;"] a {
            color: #007bff;
            text-decoration: none;
            font-weight: bold;
            padding: 8px 12px;
            border: 1px solid #007bff;
            border-radius: 5px;
            display: inline-block;
            transition: background-color 0.3s, color 0.3s;
        }
        div[style="margin-top: 20px;"] a:hover {
            background-color: #007bff;
            color: white;
        }
    </style>
</head>
<body>
<h1>Autores</h1>

<c:if test="${not empty mensagem}">
    <p style="color: green;">${mensagem}</p>
    <c:remove var="mensagem" scope="session"/>
</c:if>

<a href="${pageContext.request.contextPath}/autores?acao=novo" class="btn-novo">Novo Autor</a>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Nome</th>
        <th>Nacionalidade</th>
        <th>Ano de Nascimento</th>
        <th class="actions">Ações</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${autores}" var="autor">
        <tr>
            <td>${autor.id_aut}</td>
            <td>${autor.nome_aut}</td>
            <td>${autor.nacionalidade_aut}</td>
            <td>${autor.data_nascimento_aut}</td>
            <td class="actions">
                <a href="${pageContext.request.contextPath}/autores?acao=editar&id=${autor.id_aut}">Editar</a>
                <a href="${pageContext.request.contextPath}/autores?acao=excluir&id=${autor.id_aut}"
                   onclick="return confirm('Tem certeza que deseja excluir este autor?')">Excluir</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div style="margin-top: 20px;">
    <a href="${pageContext.request.contextPath}/home">Voltar para Home</a>
</div>
</body>
</html>