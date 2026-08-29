package br.com.sergranbel.sistemacarregamentos.model;
import java.time.LocalDate;

public class ItemViagem {
	
	private int nf;
	
	private LocalDate data;
	
    private int id;

    private Produto produto;

    private int quantidade;

    private double hl;

    private double hlRetornavel;

    public ItemViagem() {
    }

    public ItemViagem(Produto produto, int quantidade) {

        this.produto = produto;
        this.quantidade = quantidade;
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
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getHl() {
        return hl;
    }

    public void setHl(double hl) {
        this.hl = hl;
    }

    public double getHlRetornavel() {
        return hlRetornavel;
    }

    public void setHlRetornavel(double hlRetornavel) {
        this.hlRetornavel = hlRetornavel;
    }
}