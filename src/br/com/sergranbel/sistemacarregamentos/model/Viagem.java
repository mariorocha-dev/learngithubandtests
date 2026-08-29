package br.com.sergranbel.sistemacarregamentos.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Viagem {

    private int id;
    private int nf;
    private LocalDate data;
    private Motorista motorista;
    private Caminhao caminhao;
    private Carreta carreta;

    private List<ItemViagem> itens;

    public Viagem() {
        itens = new ArrayList<>();
    }

    public Viagem(int nf,
                  LocalDate data,
                  Motorista motorista,
                  Caminhao caminhao,
                  Carreta carreta) {

        this.nf = nf;
        this.data = data;
        this.motorista = motorista;
        this.caminhao = caminhao;
        this.carreta = carreta;

        this.itens = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNf() {
        return nf;
    }

    public void setNf(int nf) {
        this.nf = nf;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public Caminhao getCaminhao() {
        return caminhao;
    }

    public void setCaminhao(Caminhao caminhao) {
        this.caminhao = caminhao;
    }

    public Carreta getCarreta() {
        return carreta;
    }

    public void setCarreta(Carreta carreta) {
        this.carreta = carreta;
    }

    public List<ItemViagem> getItens() {
        return itens;
    }

    public void setItens(List<ItemViagem> itens) {
        this.itens = itens;
    }

    public void adicionarItem(ItemViagem item) {
        itens.add(item);
    }
    public double getHlTotal() {

        double total = 0;

        for (ItemViagem item : itens) {
            total += item.getHl();
        }

        return total;
    }
    public double getHlRetornavelTotal() {

        double total = 0;

        for (ItemViagem item : itens) {
            total += item.getHlRetornavel();
        }

        return total;
    }
}
