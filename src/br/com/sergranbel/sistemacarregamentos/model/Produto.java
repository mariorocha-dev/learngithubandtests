package br.com.sergranbel.sistemacarregamentos.model;

public class Produto {

    private int id;
    private int codigo;
    private String nome;
    private String marca;
    private double hlPorPacote;
    private boolean retornavel;
    private boolean ativo;

    public Produto() {
    }

    public Produto(int codigo, String nome, String marca,
                   double hlPorPacote, boolean retornavel) {

        this.codigo = codigo;
        this.nome = nome;
        this.marca = marca;
        this.hlPorPacote = hlPorPacote;
        this.retornavel = retornavel;
        this.ativo = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getHlPorPacote() {
        return hlPorPacote;
    }

    public void setHlPorPacote(double hlPorPacote) {
        this.hlPorPacote = hlPorPacote;
    }

    public boolean isRetornavel() {
        return retornavel;
    }

    public void setRetornavel(boolean retornavel) {
        this.retornavel = retornavel;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    @Override
    public String toString() {
        return codigo + " - " + nome;
    }
}