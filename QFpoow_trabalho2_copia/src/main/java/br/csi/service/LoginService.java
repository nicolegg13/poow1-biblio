package br.csi.service;
import br.csi.dao.UsuarioDAO;
import br.csi.model.Usuario;

public class LoginService {

    private static UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario autenticar(String email, String senha) {

        Usuario usuario = usuarioDAO.autenticar(email, senha);
        if (usuario == null) {
            return null;
        } else {
            return usuario;
        }

    }
}