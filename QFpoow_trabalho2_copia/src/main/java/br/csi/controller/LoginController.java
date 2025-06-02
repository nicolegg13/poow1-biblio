package br.csi.controller;
import br.csi.dao.UsuarioDAO;
import br.csi.model.Usuario;
import br.csi.service.LoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private static LoginService service = new LoginService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        Usuario usuario =  service.autenticar(email, senha);
        if ( usuario != null) {
            HttpSession session = req.getSession();
            session.setAttribute("usuarioLogado", usuario);
            // Increase session timeout to 60 minutes (3600 seconds)
            session.setMaxInactiveInterval(3600); // 60 minutes

            resp.sendRedirect(req.getContextPath() + "/home");

        } else {
            req.setAttribute("erro", "Email ou senha incorretos");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}