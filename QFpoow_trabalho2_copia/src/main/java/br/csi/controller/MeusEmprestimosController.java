package br.csi.controller;
import br.csi.dao.EmprestimoDAO;
import br.csi.dao.LivroDAO; // Adicionado para buscar livros disponíveis
import br.csi.dao.UsuarioDAO; // Adicionado para buscar usuários ativos
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
import java.util.ArrayList;
import java.util.Calendar;

@WebServlet("/meus-emprestimos")
public class MeusEmprestimosController extends HttpServlet {
    private final EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    private final LivroDAO livroDAO = new LivroDAO(); // Instanciar LivroDAO
    private final UsuarioDAO usuarioDAO = new UsuarioDAO(); // Instanciar UsuarioDAO

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuarioLogado") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        ArrayList<Emprestimo> emprestimos = emprestimoDAO.getEmprestimosPorUsuario(usuario.getId_us());

        req.setAttribute("emprestimos", emprestimos);
        req.getRequestDispatcher("/WEB-INF/emprestimo/meus-emprestimos.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuarioLogado") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        String acao = req.getParameter("acao");
        if ("novo".equals(acao)) {
            // Logic to create a new loan (similar to EmprestimoController's doPost for new loan)
            String livroParam = req.getParameter("livro");
            // The user making the request is the current logged in user
            String usuarioParam = String.valueOf(usuarioLogado.getId_us());

            if (livroParam == null || livroParam.isEmpty()) {
                req.getSession().setAttribute("mensagem", "Erro: Selecione um livro para empréstimo.");
                resp.sendRedirect(req.getContextPath() + "/meus-emprestimos");
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
            resp.sendRedirect(req.getContextPath() + "/meus-emprestimos");
            return;
        } else if ("devolver".equals(acao)) {
            int id = Integer.parseInt(req.getParameter("id"));
            String resultado = emprestimoDAO.devolver(id);
            req.getSession().setAttribute("mensagem", resultado);
            resp.sendRedirect(req.getContextPath() + "/meus-emprestimos");
            return;
        }

        // If no specific action, just redirect to list
        resp.sendRedirect(req.getContextPath() + "/meus-emprestimos");
    }
}