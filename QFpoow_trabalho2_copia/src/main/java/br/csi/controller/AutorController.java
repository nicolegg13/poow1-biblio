package br.csi.controller;
import br.csi.dao.AutorDAO;
import br.csi.model.Autor;
import br.csi.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/autores")
public class AutorController extends HttpServlet {
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
        if (acao != null && acao.equals("novo")) {
            req.getRequestDispatcher("/WEB-INF/autor/editar.jsp").forward(req, resp);
            return;
        }

        if (acao != null && acao.equals("editar")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Autor autor = autorDAO.getAutorById(id);
            req.setAttribute("autorEdicao", autor);
            req.getRequestDispatcher("/WEB-INF/autor/editar.jsp").forward(req, resp);
            return;
        }

        if (acao != null && acao.equals("excluir")) {
            int id = Integer.parseInt(req.getParameter("id"));
            String resultado = autorDAO.excluir(id);
            req.getSession().setAttribute("mensagem", resultado);
            resp.sendRedirect(req.getContextPath() + "/autores");
            return;
        }

        ArrayList<Autor> autores = autorDAO.getAutores();
        req.setAttribute("autores", autores);
        req.getRequestDispatcher("/WEB-INF/autor/listar.jsp").forward(req, resp);
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

        Autor autor = new Autor();
        try {
            autor.setNome_aut(req.getParameter("nome"));
            autor.setNacionalidade_aut(req.getParameter("nacionalidade"));
            autor.setData_nascimento_aut(req.getParameter("data_nascimento"));

            String idParam = req.getParameter("id");
            String resultado;

            if (idParam != null && !idParam.isEmpty()) {
                autor.setId_aut(Integer.parseInt(idParam));
                resultado = autorDAO.alterar(autor);
            } else {
                resultado = autorDAO.inserir(autor);
            }

            req.getSession().setAttribute("mensagem", resultado);
        } catch (Exception e) {
            req.getSession().setAttribute("mensagem", "Erro: " + e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/autores");
    }
}