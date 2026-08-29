package br.com.sergranbel.sistemacarregamentos.service;

import br.com.sergranbel.sistemacarregamentos.dao.ProdutoDAO;
import br.com.sergranbel.sistemacarregamentos.model.Produto;


public class ProdutoService {
	// Instanciando objeto DAO
    private ProdutoDAO produtoDAO;
    
    // Constructor
    public ProdutoService() {
        this.produtoDAO = new ProdutoDAO();
    }

    // Função de cadastro de produto
    public void cadastrar(Produto produto) throws Exception {
    	// Chamo uma função de dentro da propria classe "Produto Service" para validade o produto
        validarProduto(produto);
        
        Produto existente = produtoDAO.buscarPorCodigo(produto.getCodigo());
        
        if (existente != null) {
        	throw new IllegalArgumentException("Já existe um produto cadastrado com o código " + produto.getCodigo() + ".");	
        }
        
        produtoDAO.salvar(produto);
        
    }

    // BUSCAR POR CÓDIGO

    public Produto buscarPorCodigo(int codigo) throws Exception {

        if (codigo <= 0) {
        	throw new IllegalArgumentException("O código do produto deve ser maior que zero.");
        }

        Produto produto = produtoDAO.buscarPorCodigo(codigo);

        if (produto == null) {
        	throw new IllegalArgumentException("Produto " + codigo + " não encontrado.");
        }

        return produto;
    }

    // DESATIVAR

    public void desativar(int codigo) throws Exception {

        Produto produto = buscarPorCodigo(codigo);

        if (!produto.isAtivo()) {
        	throw new IllegalArgumentException("O produto já está inativo.");
        }

        produtoDAO.desativar(produto.getId());
    }



    // VALIDAR

    private void validarProduto(Produto produto) {

        if (produto == null) {
            throw new IllegalArgumentException("O produto não pode ser nulo.");
        }

        if (produto.getCodigo() <= 0) {
        	throw new IllegalArgumentException("O código do produto deve ser maior que zero.");
        }

        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }

        if (produto.getMarca() == null || produto.getMarca().isBlank()) {
            throw new IllegalArgumentException("A marca do produto é obrigatória.");
        }
        
        if (produto.getHlPorPacote() <= 0) {
            throw new IllegalArgumentException("O HL por pacote deve ser maior que zero.");
        }
    }
    public void alterar(Produto produto)
            throws Exception {

        validarProduto(produto);

        if (produto.getId() <= 0) {

            throw new IllegalArgumentException(
                    "O produto deve possuir um ID válido."
            );
        }

        Produto atual =
                produtoDAO.buscarPorId(
                        produto.getId()
                );

        if (atual == null) {

            throw new IllegalArgumentException(
                    "Produto não encontrado."
            );
        }

        produtoDAO.alterar(produto);
    }
}