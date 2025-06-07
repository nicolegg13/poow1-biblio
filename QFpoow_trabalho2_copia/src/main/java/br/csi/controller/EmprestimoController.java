package br.csi.controller;
import br.csi.dao.EmprestimoDAO;
import br.csi.dao.LivroDAO;
import br.csi.dao.UsuarioDAO;
import br.csi.model.Emprestimo;
import br.csi.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.ArrayList;


@WebServlet("/emprestimos")
public class EmprestimoController extends HttpServlet {
    private final EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    private final LivroDAO livroDAO = new LivroDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuarioLogado") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

        String acao = req.getParameter("acao");
        if (acao != null) {
            if (acao.equals("devolver")) {
                int id = Integer.parseInt(req.getParameter("id"));
                String resultado = emprestimoDAO.devolver(id);
                req.getSession().setAttribute("mensagem", resultado);
            } else if (acao.equals("novo")) {
                req.setAttribute("livros", livroDAO.getLivrosDisponiveis());
                req.setAttribute("usuarios", usuarioDAO.getUsuariosAtivos());
                req.getRequestDispatcher("/WEB-INF/emprestimo/editar.jsp").forward(req, resp);
                return;
            }
        }
        ArrayList<Emprestimo> emprestimos = emprestimoDAO.getEmprestimos();
        req.setAttribute("emprestimos", emprestimos);
        req.getRequestDispatcher("/WEB-INF/emprestimo/listar.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuarioLogado") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

        String livroParam = req.getParameter("livro");
        String usuarioParam = req.getParameter("usuario");

        if (livroParam == null || usuarioParam == null || livroParam.isEmpty() || usuarioParam.isEmpty()) {
            req.getSession().setAttribute("mensagem", "Erro: Todos os campos são obrigatórios.");
            resp.sendRedirect(req.getContextPath() + "/emprestimos");
            return;
        }

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setId_livro_emp(Integer.parseInt(livroParam));
        emprestimo.setId_usuario_emp(Integer.parseInt(usuarioParam));
        emprestimo.setData_emprestimo_emp(new Date(System.currentTimeMillis()));

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 14);
        emprestimo.setData_devolucao_prevista_emp(new Date(cal.getTimeInMillis()));
        emprestimo.setStatus_emp("ATIVO");

        String resultado = emprestimoDAO.inserir(emprestimo);
        req.getSession().setAttribute("mensagem", resultado);
        resp.sendRedirect(req.getContextPath() + "/emprestimos");
    }
}