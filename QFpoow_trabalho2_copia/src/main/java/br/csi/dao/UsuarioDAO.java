package br.csi.dao;
import br.csi.model.Usuario;
import java.sql.*;
import java.util.ArrayList;

public class UsuarioDAO {
    public Usuario autenticar(String email, String senha) {
        try (Connection conn = ConectarBancoDados.conectarBancoPostgres()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM usuario WHERE email_us = ? AND senha_us = ? AND ativo_us = true"
            );
            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId_us(rs.getInt("id_us"));
                u.setEmail_us(rs.getString("email_us"));
                u.setSenha_us(rs.getString("senha_us"));
                u.setNome_us(rs.getString("nome_us"));
                u.setTipo_us(rs.getString("tipo_us"));
                return u;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Usuario> getUsuariosAtivos() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = ConectarBancoDados.conectarBancoPostgres();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT * FROM usuario WHERE ativo_us = true AND tipo_us = 'USUARIO'"
             )) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId_us(rs.getInt("id_us"));
                u.setEmail_us(rs.getString("email_us"));
                u.setNome_us(rs.getString("nome_us"));
                usuarios.add(u);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}