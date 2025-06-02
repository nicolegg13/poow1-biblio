<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Livros</title>
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
        h3 {
            color: #dc3545; /* Red for access denied */
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
        .action-links {
            white-space: nowrap;
            width: 150px; /* Adjust as needed */
        }
        .action-links a {
            text-decoration: none;
            padding: 5px 10px;
            border-radius: 4px;
            margin-right: 5px;
        }
        .action-links a:hover {
            opacity: 0.8;
        }
        .action-links a:first-child { /* Editar */
            background-color: #ffc107; /* Yellow */
            color: #333;
        }
        .action-links a:last-child { /* Excluir */
            background-color: #dc3545; /* Red */
            color: white;
        }
        .new-livro-btn {
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
        .new-livro-btn:hover {
            background-color: #0056b3;
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

<c:choose>
    <c:when test="${usuarioLogado.tipo_us eq 'USUARIO'}">
        <h3>Você não tem acesso a essa página.</h3>
        <p><a href="${pageContext.request.contextPath}/home" class="back-link">Voltar para Home</a></p>
    </c:when>
    <c:otherwise>
        <h1>Livros</h1>

        <a href="${pageContext.request.contextPath}/livros?acao=novo" class="new-livro-btn">Novo Livro</a>

        <table>
            <tr>
                <th>ID</th>
                <th>Título</th>
                <th>ISBN</th>
                <th>Ano Publicação</th>
                <th>Autor</th>
                <th>Disponível</th>
                <th>Ações</th>
            </tr>
            <c:forEach items="${livros}" var="livro">
                <tr>
                    <td>${livro.id_liv}</td>
                    <td>${livro.titulo_liv}</td>
                    <td>${livro.isbn_liv}</td>
                    <td>${livro.ano_publicacao_liv}</td>
                    <td>${livro.nome_autor}</td>
                    <td>${livro.disponivel_liv ? "Sim" : "Não"}</td>
                    <td class="action-links">
                        <a href="${pageContext.request.contextPath}/livros?acao=editar&id=${livro.id_liv}">Editar</a>
                        <a href="${pageContext.request.contextPath}/livros?acao=excluir&id=${livro.id_liv}"
                           onclick="return confirm('Tem certeza que deseja excluir?')">Excluir</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <a href="${pageContext.request.contextPath}/home" class="back-link">Voltar</a>
    </c:otherwise>
</c:choose>

</body>
</html>