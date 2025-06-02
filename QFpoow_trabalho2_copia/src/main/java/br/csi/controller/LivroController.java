package br.csi.controller;

import br.csi.dao.AutorDAO;
import br.csi.dao.LivroDAO;
import br.csi.model.Livro;
import br.csi.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet("/livros")
public class LivroController extends HttpServlet {
    private final LivroDAO livroDAO = new LivroDAO();
    private final AutorDAO autorDAO = new AutorDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuarioLogado") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario.getTipo_us().equals("USUARIO")) {
            req.setAttribute("mensagemAcessoNegado", "Você não tem acesso a essa página.");
            req.getRequestDispatcher("/WEB-INF/acessoNegado.jsp").forward(req, resp);
            return;
        }

        String acao = req.getParameter("acao");
        if (acao != null) {
            if (acao.equals("excluir")) {
                int id = Integer.parseInt(req.getParameter("id"));
                String resultado = livroDAO.excluir(id);
                req.getSession().setAttribute("mensagem", resultado);
            } else if (acao.equals("editar")) {
                int id = Integer.parseInt(req.getParameter("id"));
                Livro livro = livroDAO.getLivroById(id);
                req.setAttribute("livro", livro);
                req.setAttribute("autores", autorDAO.getAutores());
                req.getRequestDispatcher("/WEB-INF/livro/editar.jsp").forward(req, resp);
                return;
            } else if (acao.equals("novo")) {
                req.setAttribute("autores", autorDAO.getAutores());
                req.getRequestDispatcher("/WEB-INF/livro/editar.jsp").forward(req, resp);
                return;
            }
        }
        ArrayList<Livro> livros = livroDAO.getLivros();
        req.setAttribute("livros", livros);
        req.getRequestDispatcher("/WEB-INF/livro/listar.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuarioLogado") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario.getTipo_us().equals("USUARIO")) {
            req.setAttribute("mensagemAcessoNegado", "Você não tem acesso a essa página.");
            req.getRequestDispatcher("/WEB-INF/acessoNegado.jsp").forward(req, resp);
            return;
        }

        String titulo = req.getParameter("titulo");
        String isbn = req.getParameter("isbn");
        String anoParam = req.getParameter("ano");
        String autorParam = req.getParameter("autor");
        if (titulo == null || isbn == null || anoParam == null || autorParam == null ||
                titulo.isEmpty() || isbn.isEmpty() || anoParam.isEmpty() || autorParam.isEmpty()) {
            req.getSession().setAttribute("mensagem", "Erro: Todos os campos são obrigatórios.");
            resp.sendRedirect(req.getContextPath() + "/livros");
            return;
        }

        Livro livro = new Livro();
        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            livro.setId_liv(Integer.parseInt(idParam));
        }
        livro.setTitulo_liv(titulo);
        livro.setIsbn_liv(isbn);
        livro.setAno_publicacao_liv(Integer.parseInt(anoParam));
        livro.setId_autor_liv(Integer.parseInt(autorParam));
        livro.setDisponivel_liv(true);

        String resultado;
        if (idParam != null && !idParam.isEmpty()) {
            resultado = livroDAO.alterar(livro);
        } else {
            resultado = livroDAO.inserir(livro);
        }
        req.getSession().setAttribute("mensagem", resultado);
        resp.sendRedirect(req.getContextPath() + "/livros");
    }
}