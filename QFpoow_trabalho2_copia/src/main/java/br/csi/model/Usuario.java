package br.csi.model;

public class Usuario {
    private int id_us;
    private String email_us;
    private String senha_us;
    private String nome_us;
    private boolean ativo_us;
    private String tipo_us;
    // Getters e Setters
    public int getId_us() { return id_us; }
    public void setId_us(int id_us) { this.id_us = id_us; }
    public String getEmail_us() { return email_us; }
    public void setEmail_us(String email_us) { this.email_us = email_us; }
    public String getSenha_us() { return senha_us; }
    public void setSenha_us(String senha_us) { this.senha_us = senha_us; }
    public String getNome_us() { return nome_us; }
    public void setNome_us(String nome_us) { this.nome_us = nome_us; }
    public boolean isAtivo_us() { return ativo_us; }
    public void setAtivo_us(boolean ativo_us) { this.ativo_us = ativo_us; }
    public String getTipo_us() { return tipo_us; }
    public void setTipo_us(String tipo_us) { this.tipo_us = tipo_us; }
}