package br.com.sergranbel.sistemacarregamentos.model;

import java.time.LocalDate;
import java.util.List;

public class RelatorioProduto {

    private Produto produto;

    private LocalDate dataInicio;
    private LocalDate dataFim;

    private List<ItemViagem> movimentacoes;

    private int quantidadeTotal;

    private double hlTotal;

    private double hlRetornavelTotal;


    public RelatorioProduto() {
    }


    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }


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


    public List<ItemViagem> getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(
            List<ItemViagem> movimentacoes) {

        this.movimentacoes = movimentacoes;
    }


    public int getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(
            int quantidadeTotal) {

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