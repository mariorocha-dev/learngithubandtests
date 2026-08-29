package br.com.sergranbel.sistemacarregamentos.service;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import br.com.sergranbel.sistemacarregamentos.dao.*;
import br.com.sergranbel.sistemacarregamentos.database.ConexaoBanco;
import br.com.sergranbel.sistemacarregamentos.model.*;

public class RelatorioService {

    private ItemViagemDAO itemViagemDAO;
    private ProdutoDAO produtoDAO;

    public RelatorioService() {

        this.itemViagemDAO =
                new ItemViagemDAO();

        this.produtoDAO =
                new ProdutoDAO();
    }


    public RelatorioProduto gerarRelatorioProduto(
            int produtoId,
            LocalDate dataInicio,
            LocalDate dataFim)
            throws Exception {


        // ==========================================
        // VALIDAR PRODUTO
        // ==========================================

        if (produtoId <= 0) {

            throw new IllegalArgumentException(
                    "O ID do produto deve ser maior que zero."
            );
        }


        // ==========================================
        // VALIDAR DATAS
        // ==========================================

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


        // ==========================================
        // BUSCAR PRODUTO
        // ==========================================

        Produto produto =
                produtoDAO.buscarPorId(produtoId);

        if (produto == null) {

            throw new IllegalArgumentException(
                    "Produto não encontrado."
            );
        }


        // ==========================================
        // BUSCAR MOVIMENTAÇÕES
        // ==========================================

        List<ItemViagem> movimentacoes;

        try (Connection conexao =
                ConexaoBanco.conectar()) {

            movimentacoes =
                    itemViagemDAO.buscarPorProdutoEPeriodo(
                            conexao,
                            produtoId,
                            dataInicio,
                            dataFim
                    );
        }


        // ==========================================
        // CRIAR RELATÓRIO
        // ==========================================

        RelatorioProduto relatorio =
                new RelatorioProduto();

        relatorio.setProduto(produto);

        relatorio.setDataInicio(
                dataInicio
        );

        relatorio.setDataFim(
                dataFim
        );

        relatorio.setMovimentacoes(
                movimentacoes
        );


        // ==========================================
        // CALCULAR TOTAIS
        // ==========================================

        int quantidadeTotal = 0;

        double hlTotal = 0;

        double hlRetornavelTotal = 0;


        for (ItemViagem item : movimentacoes) {

            quantidadeTotal +=
                    item.getQuantidade();

            hlTotal +=
                    item.getHl();

            hlRetornavelTotal +=
                    item.getHlRetornavel();
        }


        // ==========================================
        // SALVAR TOTAIS
        // ==========================================

        relatorio.setQuantidadeTotal(
                quantidadeTotal
        );

        relatorio.setHlTotal(
                hlTotal
        );

        relatorio.setHlRetornavelTotal(
                hlRetornavelTotal
        );


        return relatorio;
    }
    public RelatorioMotorista gerarRelatorioMotorista(
            int motoristaId,
            LocalDate dataInicio,
            LocalDate dataFim)
            throws Exception {


        // ==========================================
        // VALIDAR ID
        // ==========================================

        if (motoristaId <= 0) {

            throw new IllegalArgumentException(
                    "O ID do motorista deve ser maior que zero."
            );
        }


        // ==========================================
        // VALIDAR DATAS
        // ==========================================

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


        // ==========================================
        // BUSCAR MOTORISTA
        // ==========================================

        br.com.sergranbel.sistemacarregamentos.dao.MotoristaDAO motoristaDAO =
                new br.com.sergranbel.sistemacarregamentos.dao.MotoristaDAO();

        br.com.sergranbel.sistemacarregamentos.model.Motorista motorista =
                motoristaDAO.buscarPorId(motoristaId);

        if (motorista == null) {

            throw new IllegalArgumentException(
                    "Motorista não encontrado."
            );
        }


        // ==========================================
        // BUSCAR VIAGENS
        // ==========================================

        br.com.sergranbel.sistemacarregamentos.dao.ViagemDAO viagemDAO =
                new br.com.sergranbel.sistemacarregamentos.dao.ViagemDAO();

        List<Viagem> viagens =
                viagemDAO.buscarPorMotoristaEPeriodo(
                        motoristaId,
                        dataInicio,
                        dataFim
                );


        // ==========================================
        // COMPLETAR PRODUTOS DAS VIAGENS
        // ==========================================

        try (Connection conexao =
                     ConexaoBanco.conectar()) {

            for (Viagem viagem : viagens) {

                List<ItemViagem> itens =
                        itemViagemDAO.buscarPorViagem(
                                conexao,
                                viagem.getId()
                        );

                viagem.setItens(itens);
            }
        }


        // ==========================================
        // CRIAR RELATÓRIO
        // ==========================================

        RelatorioMotorista relatorio =
                new RelatorioMotorista();

        relatorio.setMotorista(motorista);

        relatorio.setDataInicio(dataInicio);

        relatorio.setDataFim(dataFim);

        relatorio.setViagens(viagens);


        // ==========================================
        // CALCULAR TOTAIS
        // ==========================================

        int quantidadeTotal = 0;

        double hlTotal = 0;

        double hlRetornavelTotal = 0;


        for (Viagem viagem : viagens) {

            for (ItemViagem item :
                    viagem.getItens()) {

                quantidadeTotal +=
                        item.getQuantidade();

                hlTotal +=
                        item.getHl();

                hlRetornavelTotal +=
                        item.getHlRetornavel();
            }
        }


        relatorio.setQuantidadeTotal(
                quantidadeTotal
        );

        relatorio.setHlTotal(
                hlTotal
        );

        relatorio.setHlRetornavelTotal(
                hlRetornavelTotal
        );


        return relatorio;
    }
    public RelatorioPeriodo gerarRelatorioPeriodo(
            LocalDate dataInicio,
            LocalDate dataFim)
            throws Exception {


        // ==========================================
        // VALIDAR DATAS
        // ==========================================

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


        // ==========================================
        // BUSCAR VIAGENS
        // ==========================================

        ViagemDAO viagemDAO =
                new ViagemDAO();

        List<Viagem> viagens =
                viagemDAO.buscarPorPeriodo(
                        dataInicio,
                        dataFim
                );


        // ==========================================
        // BUSCAR ITENS
        // ==========================================

        try (Connection conexao =
                     ConexaoBanco.conectar()) {

            for (Viagem viagem : viagens) {

                List<ItemViagem> itens =
                        itemViagemDAO.buscarPorViagem(
                                conexao,
                                viagem.getId()
                        );

                viagem.setItens(itens);
            }
        }


        // ==========================================
        // CRIAR RELATÓRIO
        // ==========================================

        RelatorioPeriodo relatorio =
                new RelatorioPeriodo();

        relatorio.setDataInicio(
                dataInicio
        );

        relatorio.setDataFim(
                dataFim
        );

        relatorio.setViagens(
                viagens
        );


        // ==========================================
        // CALCULAR TOTAIS
        // ==========================================

        int quantidadeTotal = 0;

        double hlTotal = 0;

        double hlRetornavelTotal = 0;


        for (Viagem viagem : viagens) {

            for (ItemViagem item :
                    viagem.getItens()) {

                quantidadeTotal +=
                        item.getQuantidade();

                hlTotal +=
                        item.getHl();

                hlRetornavelTotal +=
                        item.getHlRetornavel();
            }
        }


        // ==========================================
        // SALVAR TOTAIS
        // ==========================================

        relatorio.setQuantidadeTotal(
                quantidadeTotal
        );

        relatorio.setHlTotal(
                hlTotal
        );

        relatorio.setHlRetornavelTotal(
                hlRetornavelTotal
        );


        return relatorio;
    }
}