package br.com.sergranbel.sistemacarregamentos.model;

public class Caminhao {

    private int id;
    private String placa;
    private String modelo;
    private boolean ativo;

    public Caminhao() {
    }

    public Caminhao(String placa, String modelo) {
        this.placa = placa;
        this.modelo = modelo;
        this.ativo = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    @Override
    public String toString() {
        return placa + " - " + modelo;
    }
}