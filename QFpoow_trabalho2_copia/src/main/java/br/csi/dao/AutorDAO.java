package br.csi.dao;
import br.csi.model.Autor;
import java.sql.*;
import java.util.ArrayList;

public class AutorDAO {

    public String inserir(Autor autor) {
        String sql = "INSERT INTO autor (nome_aut, nacionalidade_aut, data_nascimento_aut) VALUES (?, ?, ?)";
        try (Connection conn = ConectarBancoDados.conectarBancoPostgres();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, autor.getNome_aut());
            stmt.setString(2, autor.getNacionalidade_aut());
            stmt.setDate(3, Date.valueOf(autor.getData_nascimento_aut()));

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                return "Falha ao criar autor. Nenhuma linha afetada.";
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    autor.setId_aut(generatedKeys.getInt(1));
                }
            }

            return "Autor criado com sucesso!";
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return "Erro ao criar autor: " + e.getMessage();
        }
    }

    public String alterar(Autor autor) {
        try (Connection conn = ConectarBancoDados.conectarBancoPostgres()) {
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE autor SET nome_aut = ?, nacionalidade_aut = ?, data_nascimento_aut = ? WHERE id_aut = ?"
            );
            stmt.setString(1, autor.getNome_aut());
            stmt.setString(2, autor.getNacionalidade_aut());
            stmt.setDate(3, Date.valueOf(autor.getData_nascimento_aut()));
            stmt.setInt(4, autor.getId_aut());
            int updateCount = stmt.executeUpdate();
            conn.commit();
            if (updateCount <= 0) {
                return "Nenhum autor alterado";
            }
            return "Autor alterado com sucesso";
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return "Erro ao alterar autor: " + e.getMessage();
        }
    }

    public static String excluir(int id) {
        try (Connection conn = ConectarBancoDados.conectarBancoPostgres()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM autor WHERE id_aut = ?");
            stmt.setInt(1, id);
            int updateCount = stmt.executeUpdate();
            if (updateCount <= 0) {
                return "Nenhum autor excluído";
            }
            return "Autor excluído com sucesso";
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return "Erro ao excluir autor: " + e.getMessage();
        }
    }

    public static Autor getAutorById(int id) {
        try (Connection conn = ConectarBancoDados.conectarBancoPostgres()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM autor WHERE id_aut = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Autor a = new Autor();
                a.setId_aut(rs.getInt("id_aut"));
                a.setNome_aut(rs.getString("nome_aut"));
                a.setNacionalidade_aut(rs.getString("nacionalidade_aut"));
                a.setData_nascimento_aut(rs.getDate("data_nascimento_aut").toString());
                return a;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Autor> getAutores() {
        ArrayList<Autor> autores = new ArrayList<>();
        try (Connection conn = ConectarBancoDados.conectarBancoPostgres();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM autor")) {
            while (rs.next()) {
                Autor a = new Autor();
                a.setId_aut(rs.getInt("id_aut"));
                a.setNome_aut(rs.getString("nome_aut"));
                a.setNacionalidade_aut(rs.getString("nacionalidade_aut"));
                a.setData_nascimento_aut(rs.getDate("data_nascimento_aut").toString());
                autores.add(a);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return autores;
    }
}