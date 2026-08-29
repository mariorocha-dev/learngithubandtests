package br.com.sergranbel.sistemacarregamentos.model;

public class Carreta {

    private int id;
    private String placa;
    private boolean ativo;

    public Carreta() {
    }

    public Carreta(String placa) {
        this.placa = placa;
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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    @Override
    public String toString() {
        return placa;
    }
}