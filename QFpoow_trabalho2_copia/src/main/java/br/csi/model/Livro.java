package br.csi.model;

public class Livro {
    private int id_liv;
    private String titulo_liv;
    private String isbn_liv;
    private int ano_publicacao_liv;
    private int id_autor_liv;
    private boolean disponivel_liv;
    //extras - para exibir
    private String nome_autor;
    // Getters e Setters
    public int getId_liv() { return id_liv; }
    public void setId_liv(int id_liv) { this.id_liv = id_liv; }
    public String getTitulo_liv() { return titulo_liv; }
    public void setTitulo_liv(String titulo_liv) { this.titulo_liv = titulo_liv; }
    public String getIsbn_liv() { return isbn_liv; }
    public void setIsbn_liv(String isbn_liv) { this.isbn_liv = isbn_liv; }
    public int getAno_publicacao_liv() { return ano_publicacao_liv; }
    public void setAno_publicacao_liv(int ano_publicacao_liv) { this.ano_publicacao_liv = ano_publicacao_liv; }
    public int getId_autor_liv() { return id_autor_liv; }
    public void setId_autor_liv(int id_autor_liv) { this.id_autor_liv = id_autor_liv; }
    public boolean isDisponivel_liv() { return disponivel_liv; }
    public void setDisponivel_liv(boolean disponivel_liv) { this.disponivel_liv = disponivel_liv; }

    public String getNome_autor() { return nome_autor; }
    public void setNome_autor(String nome_autor) { this.nome_autor = nome_autor; }
}