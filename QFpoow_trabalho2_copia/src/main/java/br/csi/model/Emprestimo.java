package br.csi.model;
import java.sql.Date;
public class Emprestimo {
    private int id_emp;
    private int id_livro_emp;
    private int id_usuario_emp;
    private Date data_emprestimo_emp;
    private Date data_devolucao_prevista_emp;
    private Date data_devolucao_efetiva_emp;
    private String status_emp;
    //extras - para exibir
    private String titulo_livro;
    private String nome_usuario;

    // Getters e Setters
    public int getId_emp() { return id_emp; }
    public void setId_emp(int id_emp) { this.id_emp = id_emp; }
    public int getId_livro_emp() { return id_livro_emp; }
    public void setId_livro_emp(int id_livro_emp) { this.id_livro_emp = id_livro_emp; }
    public int getId_usuario_emp() { return id_usuario_emp; }
    public void setId_usuario_emp(int id_usuario_emp) { this.id_usuario_emp = id_usuario_emp; }
    public Date getData_emprestimo_emp() { return data_emprestimo_emp; }
    public void setData_emprestimo_emp(Date data_emprestimo_emp) { this.data_emprestimo_emp = data_emprestimo_emp; }
    public Date getData_devolucao_prevista_emp() { return data_devolucao_prevista_emp; }
    public void setData_devolucao_prevista_emp(Date data_devolucao_prevista_emp) { this.data_devolucao_prevista_emp = data_devolucao_prevista_emp; }
    public Date getData_devolucao_efetiva_emp() { return data_devolucao_efetiva_emp; }
    public void setData_devolucao_efetiva_emp(Date data_devolucao_efetiva_emp) { this.data_devolucao_efetiva_emp = data_devolucao_efetiva_emp; }
    public String getStatus_emp() { return status_emp; }
    public void setStatus_emp(String status_emp) { this.status_emp = status_emp; }

    public String getTitulo_livro() { return titulo_livro; }
    public void setTitulo_livro(String titulo_livro) { this.titulo_livro = titulo_livro; }

    public String getNome_usuario() { return nome_usuario; }
    public void setNome_usuario(String nome_usuario) { this.nome_usuario = nome_usuario; }
}