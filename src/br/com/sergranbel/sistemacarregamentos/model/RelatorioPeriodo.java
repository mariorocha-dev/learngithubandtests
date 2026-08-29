package br.com.sergranbel.sistemacarregamentos.model;

import java.time.LocalDate;
import java.util.List;

public class RelatorioPeriodo {

    private LocalDate dataInicio;
    private LocalDate dataFim;

    private List<Viagem> viagens;

    private int quantidadeTotal;

    private double hlTotal;

    private double hlRetornavelTotal;


    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }


    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }


    public List<Viagem> getViagens() {
        return viagens;
    }

    public void setViagens(List<Viagem> viagens) {
        this.viagens = viagens;
    }


    public int getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(int quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }


    public double getHlTotal() {
        return hlTotal;
    }

    public void setHlTotal(double hlTotal) {
        this.hlTotal = hlTotal;
    }


    public double getHlRetornavelTotal() {
        return hlRetornavelTotal;
    }

    public void setHlRetornavelTotal(
            double hlRetornavelTotal) {

        this.hlRetornavelTotal =
                hlRetornavelTotal;
    }
}
