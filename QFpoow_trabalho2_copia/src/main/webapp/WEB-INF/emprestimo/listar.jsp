<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Empréstimos</title>
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
        .atrasado {
            color: red;
            font-weight: bold;
        }
        a {
            text-decoration: none;
            background-color: #007bff;
            color: white;
            padding: 8px 12px;
            border-radius: 5px;
            display: inline-block;
            margin-bottom: 20px;
            transition: background-color 0.3s;
        }
        a:hover {
            background-color: #0056b3;
        }
        .devolver-btn {
            background-color: #28a745; /* Green for Devolver */
        }
        .devolver-btn:hover {
            background-color: #218838;
        }
        p[style="color: green;"] {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .back-link {
            margin-top: 20px;
            display: inline-block;
            color: #007bff;
            text-decoration: none;
            font-weight: bold;
            padding: 8px 12px;
            border: 1px solid #007bff;
            border-radius: 5px;
            transition: background-color 0.3s, color 0.3s;
        }
        .back-link:hover {
            background-color: #007bff;
            color: white;
        }
    </style>
</head>
<body>

<c:if test="${empty sessionScope.usuarioLogado}">
    <script>window.location='${pageContext.request.contextPath}/login';</script>
</c:if>

<h1>Empréstimos</h1>

<c:if test="${not empty mensagem}">
    <p style="color: green;">${mensagem}</p>
    <c:remove var="mensagem" scope="session"/>
</c:if>

<a href="${pageContext.request.contextPath}/emprestimos?acao=novo">Novo Empréstimo</a>

<table>
    <tr>
        <th>ID</th>
        <th>Livro</th>
        <th>Usuário</th>
        <th>Data Empréstimo</th>
        <th>Data Devolução Prevista</th>
        <th>Status</th>
        <th>Ações</th>
    </tr>
    <c:forEach items="${emprestimos}" var="emp">
        <tr class="${emp.status_emp eq 'ATRASADO' ? 'atrasado' : ''}">
            <td>${emp.id_emp}</td>
            <td>${emp.titulo_livro}</td>
            <td>${emp.nome_usuario}</td>
            <td>${emp.data_emprestimo_emp}</td>
            <td>${emp.data_devolucao_prevista_emp}</td>
            <td>${emp.status_emp}</td>
            <td>
                <c:if test="${emp.status_emp eq 'ATIVO'}">
                    <a href="${pageContext.request.contextPath}/emprestimos?acao=devolver&id=${emp.id_emp}" class="devolver-btn">Devolver</a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>

<a href="${pageContext.request.contextPath}/home" class="back-link">Voltar</a>
</body>
</html>