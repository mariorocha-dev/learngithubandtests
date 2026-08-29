package br.com.sergranbel.sistemacarregamentos.model;

public class Motorista {

    private int id;
    private String cpf;
    private String nome;
    private boolean ativo;

    public Motorista() {
    }

    public Motorista(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
        this.ativo = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    @Override
    public String toString() {
        return nome;
    }
}
