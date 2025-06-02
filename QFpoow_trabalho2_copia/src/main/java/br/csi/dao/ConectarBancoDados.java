package br.csi.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectarBancoDados {
    public static Connection conectarBancoPostgres() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/poow_trabalho";
        String user = "postgres";
        String senha = "1234";
        return DriverManager.getConnection(url, user, senha);
    }
}