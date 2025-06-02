<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${empty livro ? 'Novo Livro' : 'Editar Livro'}</title>
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
        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            width: 500px;
            margin-top: 20px;
        }
        div {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"],
        input[type="number"],
        select {
            width: calc(100% - 20px);
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        button[type="submit"] {
            background-color: #28a745; /* Green for Save */
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            margin-right: 10px;
        }
        button[type="submit"]:hover {
            background-color: #218838;
        }
        a {
            text-decoration: none;
            color: #dc3545; /* Red for Cancel */
            padding: 10px 15px;
            border: 1px solid #dc3545;
            border-radius: 4px;
            font-size: 16px;
        }
        a:hover {
            background-color: #dc3545;
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
    </c:when>
    <c:otherwise>
        <h1>${empty livro ? 'Novo Livro' : 'Editar Livro'}</h1>

        <form action="${pageContext.request.contextPath}/livros" method="post">
            <c:if test="${not empty livro}">
                <input type="hidden" name="id" value="${livro.id_liv}">
            </c:if>

            <div>
                <label>Título:</label>
                <input type="text" name="titulo" value="${livro.titulo_liv}" required>
            </div>

            <div>
                <label>ISBN:</label>
                <input type="text" name="isbn" value="${livro.isbn_liv}" required>
            </div>

            <div>
                <label>Ano Publicação:</label>
                <input type="number" name="ano" value="${livro.ano_publicacao_liv}" required>
            </div>

            <div>
                <label>Autor:</label>
                <select name="autor" required>
                    <option value="">Selecione um autor</option>
                    <c:forEach items="${autores}" var="autor">
                        <option value="${autor.id_aut}"
                            ${livro.id_autor_liv eq autor.id_aut ? 'selected' : ''}>
                                ${autor.nome_aut}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <button type="submit">Salvar</button>
            <a href="${pageContext.request.contextPath}/livros">Cancelar</a>
        </form>
    </c:otherwise>
</c:choose>

</body>
</html>