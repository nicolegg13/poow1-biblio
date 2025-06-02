package br.csi.dao;
import br.csi.model.Emprestimo;
import java.sql.*;
import java.util.ArrayList;

public class EmprestimoDAO {
    public String inserir(Emprestimo emprestimo) {
        try (Connection conn = ConectarBancoDados.conectarBancoPostgres()) {
            conn.setAutoCommit(false);
            PreparedStatement stmtEmp = conn.prepareStatement(
                    "INSERT INTO emprestimo (id_livro_emp, id_usuario_emp, data_emprestimo_emp, data_devolucao_prevista_emp, status_emp) VALUES (?, ?, ?, ?, ?)"
            );
            stmtEmp.setInt(1, emprestimo.getId_livro_emp());
            stmtEmp.setInt(2, emprestimo.getId_usuario_emp());
            stmtEmp.setDate(3, emprestimo.getData_emprestimo_emp());
            stmtEmp.setDate(4, emprestimo.getData_devolucao_prevista_emp());
            stmtEmp.setString(5, emprestimo.getStatus_emp());
            stmtEmp.executeUpdate();
            PreparedStatement stmtLivro = conn.prepareStatement("UPDATE livro SET disponivel_liv = false WHERE id_liv = ?");
            stmtLivro.setInt(1, emprestimo.getId_livro_emp());
            stmtLivro.executeUpdate();

            conn.commit();
            return "Empréstimo registrado com sucesso";
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return "Erro ao registrar empréstimo";
        }
    }

    public ArrayList<Emprestimo> getEmprestimos() {
        ArrayList<Emprestimo> emprestimos = new ArrayList<>();
        try (Connection conn = ConectarBancoDados.conectarBancoPostgres()) {
            String sql = "SELECT e.*, l.titulo_liv as titulo_livro, u.nome_us as nome_usuario " +
                    "FROM emprestimo e " +
                    "JOIN livro l ON e.id_livro_emp = l.id_liv " +
                    "JOIN usuario u ON e.id_usuario_emp = u.id_us";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Emprestimo e = new Emprestimo();
                e.setId_emp(rs.getInt("id_emp"));
                e.setId_livro_emp(rs.getInt("id_livro_emp"));
                e.setId_usuario_emp(rs.getInt("id_usuario_emp"));
                e.setData_emprestimo_emp(rs.getDate("data_emprestimo_emp"));
                e.setData_devolucao_prevista_emp(rs.getDate("data_devolucao_prevista_emp"));
                e.setData_devolucao_efetiva_emp(rs.getDate("data_devolucao_efetiva_emp"));
                e.setStatus_emp(rs.getString("status_emp"));

                // Adiciona os dados relacionados
                e.setTitulo_livro(rs.getString("titulo_livro"));
                e.setNome_usuario(rs.getString("nome_usuario"));

                emprestimos.add(e);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return emprestimos;
    }

    public ArrayList<Emprestimo> getEmprestimosPorUsuario(int idUsuario) {
        ArrayList<Emprestimo> emprestimos = new ArrayList<>();
        try (Connection conn = ConectarBancoDados.conectarBancoPostgres()) {
            String sql = "SELECT e.*, l.titulo_liv as titulo_livro, u.nome_us as nome_usuario " +
                    "FROM emprestimo e " +
                    "JOIN livro l ON e.id_livro_emp = l.id_liv " +
                    "JOIN usuario u ON e.id_usuario_emp = u.id_us " +
                    "WHERE e.id_usuario_emp = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Emprestimo e = new Emprestimo();
                e.setId_emp(rs.getInt("id_emp"));
                e.setId_livro_emp(rs.getInt("id_livro_emp"));
                e.setId_usuario_emp(rs.getInt("id_usuario_emp"));
                e.setData_emprestimo_emp(rs.getDate("data_emprestimo_emp"));
                e.setData_devolucao_prevista_emp(rs.getDate("data_devolucao_prevista_emp"));
                e.setData_devolucao_efetiva_emp(rs.getDate("data_devolucao_efetiva_emp"));
                e.setStatus_emp(rs.getString("status_emp"));
                e.setTitulo_livro(rs.getString("titulo_livro"));
                e.setNome_usuario(rs.getString("nome_usuario"));
                emprestimos.add(e);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return emprestimos;
    }

    public String devolver(int idEmprestimo) {
        try (Connection conn = ConectarBancoDados.conectarBancoPostgres()) {
            conn.setAutoCommit(false);
            PreparedStatement stmtEmp = conn.prepareStatement(
                    "UPDATE emprestimo SET status_emp = 'CONCLUIDO', data_devolucao_efetiva_emp = CURRENT_DATE WHERE id_emp = ?"
            );
            stmtEmp.setInt(1, idEmprestimo);
            stmtEmp.executeUpdate();

            PreparedStatement stmtGetLivro = conn.prepareStatement("SELECT id_livro_emp FROM emprestimo WHERE id_emp = ?");
            stmtGetLivro.setInt(1, idEmprestimo);
            ResultSet rs = stmtGetLivro.executeQuery();
            if (rs.next()) {
                int idLivro = rs.getInt("id_livro_emp");
                PreparedStatement stmtLivro = conn.prepareStatement("UPDATE livro SET disponivel_liv = true WHERE id_liv = ?");
                stmtLivro.setInt(1, idLivro);
                stmtLivro.executeUpdate();
            }
            conn.commit();
            return "Devolução registrada com sucesso";
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return "Erro ao registrar devolução";
        }
    }
}