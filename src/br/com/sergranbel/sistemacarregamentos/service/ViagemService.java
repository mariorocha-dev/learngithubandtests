package br.com.sergranbel.sistemacarregamentos.service;


import java.sql.Connection;
import java.time.LocalDate;

import br.com.sergranbel.sistemacarregamentos.dao.*;
import br.com.sergranbel.sistemacarregamentos.model.*;
import br.com.sergranbel.sistemacarregamentos.database.ConexaoBanco;
import java.util.List;

public class ViagemService {

	private ViagemDAO viagemDAO;
	private ItemViagemDAO itemViagemDAO;
	private ProdutoDAO produtoDAO;

    public ViagemService() {

        this.viagemDAO = new ViagemDAO();
        this.itemViagemDAO = new ItemViagemDAO();
        this.produtoDAO = new ProdutoDAO();
        
    }
    
    public void cadastrar(Viagem viagem) throws Exception {
        
    	validarViagem(viagem);
        
        if (viagemDAO.existePorNf(viagem.getNf())) {
            throw new IllegalArgumentException( "Já existe uma viagem cadastrada com a NF " + viagem.getNf() + ".");
        }
        try (Connection conexao = ConexaoBanco.conectar()) {
        	
            conexao.setAutoCommit(false);
            
            try {
                int viagemId = viagemDAO.salvar(conexao, viagem);
                
                System.out.println("Viagem criada. ID: " + viagemId);
                
                for (ItemViagem item : viagem.getItens()) {
                	
                	System.out.println("Salvando produto: " + item.getProduto().getNome());
                	
                    calcularHL(item);
                    
                    itemViagemDAO.salvar(conexao,viagemId,item);
                }
                
                conexao.commit();
                
                System.out.println("Viagem salva com sucesso!");

            } catch (Exception e) {
                conexao.rollback();
                throw e;
            }
        }
    }
    public Viagem buscarPorId(int viagemId) throws Exception {

        if (viagemId <= 0) {
            throw new IllegalArgumentException(
                    "O ID da viagem deve ser maior que zero."
            );
        }

        try (Connection conexao = ConexaoBanco.conectar()) {

            Viagem viagem =
                    viagemDAO.buscarPorId(
                            conexao,
                            viagemId
                    );

            if (viagem == null) {

                throw new IllegalArgumentException(
                        "Viagem não encontrada. ID: " +
                        viagemId
                );
            }

            List<ItemViagem> itens =
                    itemViagemDAO.buscarPorViagem(
                            conexao,
                            viagemId
                    );

            viagem.setItens(itens);

            return viagem;
        }
    }

    private void validarViagem(Viagem viagem) {

        if (viagem == null) {
            throw new IllegalArgumentException(
                    "A viagem não pode ser nula."
            );
        }

        if (viagem.getNf() <= 0) {
            throw new IllegalArgumentException(
                    "A NF deve ser maior que zero."
            );
        }

        if (viagem.getData() == null) {
            throw new IllegalArgumentException(
                    "A data da viagem é obrigatória."
            );
        }

        if (viagem.getMotorista() == null) {
            throw new IllegalArgumentException(
                    "O motorista é obrigatório."
            );
        }
        if (!viagem.getMotorista().isAtivo()) {
            throw new IllegalArgumentException(
                    "O motorista está inativo."
            );
        }
        if (viagem.getCaminhao() == null) {
            throw new IllegalArgumentException(
                    "O caminhão é obrigatório."
            );
        }
        if (!viagem.getCaminhao().isAtivo()) {
            throw new IllegalArgumentException(
                    "O caminhão está inativo."
            );
        }
        if (viagem.getCarreta() == null) {
            throw new IllegalArgumentException(
                    "A carreta é obrigatória."
            );
        }
        if (!viagem.getCarreta().isAtivo()) {
            throw new IllegalArgumentException(
                    "A carreta está inativa."
            );
        }

        if (viagem.getItens() == null ||
            viagem.getItens().isEmpty()) {

            throw new IllegalArgumentException(
                    "A viagem precisa ter pelo menos um produto."
            );
        }

        for (ItemViagem item : viagem.getItens()) {

            if (item.getProduto() == null) {
                throw new IllegalArgumentException(
                        "Existe um item sem produto."
                );
            }
            Produto produtoBanco =
                    produtoDAO.buscarPorCodigo(
                            item.getProduto().getCodigo()
                    );

            if (produtoBanco == null) {

                throw new IllegalArgumentException(
                        "O produto " +
                        item.getProduto().getCodigo() +
                        " não existe no banco de dados."
                );
            }
            item.setProduto(produtoBanco);
            if (item.getProduto().getId() <= 0) {
                throw new IllegalArgumentException(
                        "O produto informado não possui um ID válido."
                );
            }
            if (!item.getProduto().isAtivo()) {
                throw new IllegalArgumentException(
                        "O produto " +
                        item.getProduto().getNome() +
                        " está inativo."
                );
            }

            if (item.getQuantidade() <= 0) {
                throw new IllegalArgumentException(
                        "A quantidade deve ser maior que zero."
                );
            }
        }
        for (int i = 0; i < viagem.getItens().size(); i++) {

            for (int j = i + 1; j < viagem.getItens().size(); j++) {

                ItemViagem item1 =
                        viagem.getItens().get(i);

                ItemViagem item2 =
                        viagem.getItens().get(j);

                if (item1.getProduto().getId()
                        == item2.getProduto().getId()) {

                    throw new IllegalArgumentException(
                            "O produto " +
                            item1.getProduto().getNome() +
                            " foi informado mais de uma vez na viagem."
                    );
                }
            }
        }
    }

    private void calcularHL(ItemViagem item) {

        double hlPorPacote =
                item.getProduto().getHlPorPacote();

        double hl =
                item.getQuantidade() * hlPorPacote;

        item.setHl(hl);

        if (item.getProduto().isRetornavel()) {

            item.setHlRetornavel(hl);

        } else {

            item.setHlRetornavel(0);
        }
    }
    public List<ItemViagem> buscarItens(int viagemId)
            throws Exception {

        try (Connection conexao =
                ConexaoBanco.conectar()) {

            return itemViagemDAO.buscarPorViagem(
                    conexao,
                    viagemId
            );
        }
    }
    public List<Viagem> buscarPorMotorista(
            int motoristaId) throws Exception {

        if (motoristaId <= 0) {

            throw new IllegalArgumentException(
                    "O ID do motorista deve ser maior que zero."
            );
        }

        try (Connection conexao =
                ConexaoBanco.conectar()) {

            return viagemDAO.buscarPorMotorista(
                    conexao,
                    motoristaId
            );
        }
    }
    public List<Viagem> buscarPorPeriodo(
            LocalDate inicio,
            LocalDate fim) throws Exception {

        if (inicio == null) {

            throw new IllegalArgumentException(
                    "A data inicial é obrigatória."
            );
        }

        if (fim == null) {

            throw new IllegalArgumentException(
                    "A data final é obrigatória."
            );
        }

        if (inicio.isAfter(fim)) {

            throw new IllegalArgumentException(
                    "A data inicial não pode ser maior que a data final."
            );
        }

        try (Connection conexao =
                ConexaoBanco.conectar()) {

            return viagemDAO.buscarPorPeriodo(
                    conexao,
                    inicio,
                    fim
            );
        }
    }
    public List<ItemViagem> buscarItensPorProduto(
            int produtoId) throws Exception {

        if (produtoId <= 0) {

            throw new IllegalArgumentException(
                    "O ID do produto deve ser maior que zero."
            );
        }

        try (Connection conexao =
                ConexaoBanco.conectar()) {

            return itemViagemDAO.buscarPorProduto(
                    conexao,
                    produtoId
            );
        }
    }
    public List<ItemViagem> buscarItensPorProdutoEPeriodo(
            int produtoId,
            LocalDate dataInicio,
            LocalDate dataFim)
            throws Exception {

        if (produtoId <= 0) {

            throw new IllegalArgumentException(
                    "O ID do produto deve ser maior que zero."
            );
        }

        if (dataInicio == null) {

            throw new IllegalArgumentException(
                    "A data inicial é obrigatória."
            );
        }

        if (dataFim == null) {

            throw new IllegalArgumentException(
                    "A data final é obrigatória."
            );
        }

        if (dataInicio.isAfter(dataFim)) {

            throw new IllegalArgumentException(
                    "A data inicial não pode ser posterior à data final."
            );
        }

        try (Connection conexao =
                ConexaoBanco.conectar()) {

            return itemViagemDAO.buscarPorProdutoEPeriodo(
                    conexao,
                    produtoId,
                    dataInicio,
                    dataFim
            );
        }
    }
}