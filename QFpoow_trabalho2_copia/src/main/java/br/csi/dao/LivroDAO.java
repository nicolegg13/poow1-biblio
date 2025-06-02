package br.csi.dao;
import br.csi.model.Livro;
import java.sql.*;
import java.util.ArrayList;

public class LivroDAO {
    public String inserir(Livro livro) {
        try (Connection conn = ConectarBancoDados.conectarBancoPostgres()) {
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO livro (titulo_liv, isbn_liv, ano_publicacao_liv, id_autor_liv, disponivel_liv) VALUES (?, ?, ?, ?, ?)"
            );
            stmt.setString(1, livro.getTitulo_liv());
            stmt.setString(2, livro.getIsbn_liv());
            stmt.setInt(3, livro.getAno_publicacao_liv());
            stmt.setInt(4, livro.getId_autor_liv());
            stmt.setBoolean(5, livro.isDisponivel_liv());
            stmt.executeUpdate();
            conn.commit();
            return "Livro inserido com sucesso";
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return "Erro ao inserir livro";
        }
    }

    public ArrayList<Livro> getLivros() {
        ArrayList<Livro> livros = new ArrayList<>();
        try (Connection conn = ConectarBancoDados.conectarBancoPostgres()) {
            String sql = "SELECT l.*, a.nome_aut as nome_autor " +
                    "FROM livro l JOIN autor a ON l.id_autor_liv = a.id_aut";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Livro l = new Livro();
                l.setId_liv(rs.getInt("id_liv"));
                l.setTitulo_liv(rs.getString("titulo_liv"));
                l.setIsbn_liv(rs.getString("isbn_liv"));
                l.setAno_publicacao_liv(rs.getInt("ano_publicacao_liv"));
                l.setId_autor_liv(rs.getInt("id_autor_liv"));
                l.setDisponivel_liv(rs.getBoolean("disponivel_liv"));

                // Adiciona o nome do autor
                l.setNome_autor(rs.getString("nome_autor"));
                livros.add(l);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return livros;
    }

    public ArrayList<Livro> getLivrosDisponiveis() {
        ArrayList<Livro> livros = new ArrayList<>();
        try (Connection conn = ConectarBancoDados.conectarBancoPostgres();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT l.*, a.nome_aut FROM livro l JOIN autor a ON l.id_autor_liv = a.id_aut WHERE l.disponivel_liv = true"
             )) {
            while (rs.next()) {
                Livro l = new Livro();
                l.setId_liv(rs.getInt("id_liv"));
                l.setTitulo_liv(rs.getString("titulo_liv"));
                l.setIsbn_liv(rs.getString("isbn_liv"));
                l.setAno_publicacao_liv(rs.getInt("ano_publicacao_liv"));
                l.setId_autor_liv(rs.getInt("id_autor_liv"));
                l.setDisponivel_liv(rs.getBoolean("disponivel_liv"));
                l.setNome_autor(rs.getString("nome_aut")); // Ensure author name is also set for available books
                livros.add(l);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return livros;
    }

    public Livro getLivroById(int id) {
        try (Connection conn = ConectarBancoDados.conectarBancoPostgres()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT l.*, a.nome_aut FROM livro l JOIN autor a ON l.id_autor_liv = a.id_aut WHERE l.id_liv = ?"
            );
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Livro l = new Livro();
                l.setId_liv(rs.getInt("id_liv"));
                l.setTitulo_liv(rs.getString("titulo_liv"));
                l.setIsbn_liv(rs.getString("isbn_liv"));
                l.setAno_publicacao_liv(rs.getInt("ano_publicacao_liv"));
                l.setId_autor_liv(rs.getInt("id_autor_liv"));
                l.setDisponivel_liv(rs.getBoolean("disponivel_liv"));
                l.setNome_autor(rs.getString("nome_aut")); // Ensure author name is also set
                return l;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String alterar(Livro livro) {
        try (Connection conn = ConectarBancoDados.conectarBancoPostgres()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE livro SET titulo_liv = ?, isbn_liv = ?, ano_publicacao_liv = ?, id_autor_liv = ?, disponivel_liv = ? WHERE id_liv = ?"
            );
            stmt.setString(1, livro.getTitulo_liv());
            stmt.setString(2, livro.getIsbn_liv());
            stmt.setInt(3, livro.getAno_publicacao_liv());
            stmt.setInt(4, livro.getId_autor_liv());
            stmt.setBoolean(5, livro.isDisponivel_liv());
            stmt.setInt(6, livro.getId_liv());
            int updateCount = stmt.executeUpdate();
            if (updateCount <= 0) {
                return "Nenhum livro atualizado";
            }
            return "Livro atualizado com sucesso";
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return "Erro ao atualizar livro";
        }
    }

    public String excluir(int id) {
        try (Connection conn = ConectarBancoDados.conectarBancoPostgres()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM livro WHERE id_liv = ?");
            stmt.setInt(1, id);
            int updateCount = stmt.executeUpdate();
            if (updateCount <= 0) {
                return "Nenhum livro excluído";
            }
            return "Livro excluído com sucesso";
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return "Erro ao excluir livro";
        }
    }
}