package br.csi.model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Autor {
    private int id_aut;
    private String nome_aut;
    private String nacionalidade_aut;
    private String data_nascimento_aut;

    // Getters e Setters
    public int getId_aut() { return id_aut; }
    public void setId_aut(int id_aut) { this.id_aut = id_aut; }

    public String getNome_aut() { return nome_aut; }
    public void setNome_aut(String nome_aut) {
        if (nome_aut == null || nome_aut.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do autor é obrigatório");
        }
        this.nome_aut = nome_aut;
    }

    public String getNacionalidade_aut() { return nacionalidade_aut; }
    public void setNacionalidade_aut(String nacionalidade_aut) {
        this.nacionalidade_aut = nacionalidade_aut;
    }

    public String getData_nascimento_aut() { return data_nascimento_aut; }
    public void setData_nascimento_aut(String data_nascimento_aut) {
        if (data_nascimento_aut == null || data_nascimento_aut.trim().isEmpty()) {
            throw new IllegalArgumentException("Data de nascimento é obrigatória");
        }

        // Converte para o formato AAAA-MM-dd para o input type="date"
        try {
            LocalDate date = LocalDate.parse(data_nascimento_aut);
            this.data_nascimento_aut = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (Exception e) {
            throw new IllegalArgumentException("Formato de data inválido. Use o formato AAAA-MM-DD");
        }
    }
}