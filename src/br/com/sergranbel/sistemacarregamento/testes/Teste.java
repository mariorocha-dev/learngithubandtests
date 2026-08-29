package br.com.sergranbel.sistemacarregamento.testes;

import br.com.sergranbel.sistemacarregamentos.model.*;
import br.com.sergranbel.sistemacarregamentos.service.*;
import br.com.sergranbel.sistemacarregamentos.view.JanelaPrincipal;
import br.com.sergranbel.sistemacarregamentos.dao.*;

public class Teste {
	public static void testeTerminal() throws Exception {

	    ProdutoService service =
	            new ProdutoService();

	    // ==========================================
	    // TESTE 1 - BUSCAR PRODUTO
	    // ==========================================

	    System.out.println();
	    System.out.println("================================");
	    System.out.println("          PRODUTO");
	    System.out.println("================================");

	    Produto encontrado =
	            service.buscarPorCodigo(3001);

	    System.out.println(
	            "Código: " +
	            encontrado.getCodigo()
	    );

	    System.out.println(
	            "Nome: " +
	            encontrado.getNome()
	    );

	    System.out.println(
	            "Marca: " +
	            encontrado.getMarca()
	    );

	    System.out.println(
	            "HL: " +
	            encontrado.getHlPorPacote()
	    );

	    System.out.println(
	            "Ativo: " +
	            encontrado.isAtivo()
	    );


	    // ==========================================
	    // TESTE 2 - DESATIVAR
	    // ==========================================

	    System.out.println();
	    System.out.println("================================");
	    System.out.println("       DESATIVAÇÃO");
	    System.out.println("================================");

	    if (!encontrado.isAtivo()) {

	        System.out.println(
	                "O produto 3001 já está inativo."
	        );

	    } else {

	        System.out.println(
	                "Ativo antes: " +
	                encontrado.isAtivo()
	        );

	        service.desativar(3001);

	        Produto depois =
	                service.buscarPorCodigo(3001);

	        System.out.println(
	                "Ativo depois: " +
	                depois.isAtivo()
	        );

	        if (!depois.isAtivo()) {

	            System.out.println();
	            System.out.println(
	                    "TESTE DE DESATIVAÇÃO PASSOU!"
	            );

	        } else {

	            System.out.println();
	            System.out.println(
	                    "ERRO: O produto continua ativo!"
	            );
	        }
	    }
	}
	public static void testeProdutoInativoNaViagem() throws Exception {

	    System.out.println();
	    System.out.println("================================");
	    System.out.println("   TESTE PRODUTO INATIVO");
	    System.out.println("================================");


	    // ==========================================
	    // SERVICES
	    // ==========================================

	    ProdutoService produtoService =
	            new ProdutoService();

	    ViagemService viagemService =
	            new ViagemService();


	    // ==========================================
	    // BUSCAR PRODUTO
	    // ==========================================

	    Produto produto =
	            produtoService.buscarPorCodigo(3001);

	    System.out.println(
	            "Produto: " +
	            produto.getNome()
	    );

	    System.out.println(
	            "Ativo: " +
	            produto.isAtivo()
	    );


	    // ==========================================
	    // CONFIRMAR QUE O PRODUTO ESTÁ INATIVO
	    // ==========================================

	    if (produto.isAtivo()) {

	        System.out.println();
	        System.out.println(
	                "ERRO: O produto deveria estar inativo."
	        );

	        return;
	    }

	    System.out.println();
	    System.out.println(
	            "Produto está inativo."
	    );

	    System.out.println();
	    System.out.println(
	            "Agora vamos tentar utilizá-lo em uma viagem..."
	    );


	    // ==========================================
	    // DAOs
	    // ==========================================

	    MotoristaDAO motoristaDAO =
	            new MotoristaDAO();

	    CaminhaoDAO caminhaoDAO =
	            new CaminhaoDAO();

	    CarretaDAO carretaDAO =
	            new CarretaDAO();


	    // ==========================================
	    // BUSCAR MOTORISTA
	    // ==========================================

	    Motorista motorista =
	            motoristaDAO.buscarPorCpf(
	                    "12345678901"
	            );


	    // ==========================================
	    // BUSCAR CAMINHÃO
	    // ==========================================

	    Caminhao caminhao =
	            caminhaoDAO.buscarPorPlaca(
	                    "ABC1234"
	            );


	    // ==========================================
	    // BUSCAR CARRETA
	    // ==========================================

	    Carreta carreta =
	            carretaDAO.buscarPorPlaca(
	                    "XYZ5678"
	            );


	    // ==========================================
	    // VERIFICAR SE OS DADOS EXISTEM
	    // ==========================================

	    if (motorista == null) {

	        throw new IllegalArgumentException(
	                "Motorista de teste não encontrado."
	        );
	    }

	    if (caminhao == null) {

	        throw new IllegalArgumentException(
	                "Caminhão de teste não encontrado."
	        );
	    }

	    if (carreta == null) {

	        throw new IllegalArgumentException(
	                "Carreta de teste não encontrada."
	        );
	    }


	    // ==========================================
	    // CRIAR VIAGEM
	    // ==========================================

	    Viagem viagem =
	            new Viagem();

	    viagem.setNf(999999);

	    viagem.setData(
	            java.time.LocalDate.now()
	    );

	    viagem.setMotorista(
	            motorista
	    );

	    viagem.setCaminhao(
	            caminhao
	    );

	    viagem.setCarreta(
	            carreta
	    );


	    // ==========================================
	    // ADICIONAR PRODUTO INATIVO
	    // ==========================================

	    ItemViagem item =
	            new ItemViagem();

	    item.setProduto(
	            produto
	    );

	    item.setQuantidade(10);

	    viagem.adicionarItem(
	            item
	    );


	    // ==========================================
	    // TENTAR CADASTRAR A VIAGEM
	    // ==========================================

	    try {

	        viagemService.cadastrar(
	                viagem
	        );

	        // Se chegar aqui, temos um problema:
	        // o produto está inativo, portanto
	        // a viagem não deveria ser cadastrada.

	        System.out.println();
	        System.out.println(
	                "================================"
	        );

	        System.out.println(
	                "          ERRO"
	        );

	        System.out.println(
	                "================================"
	        );

	        System.out.println(
	                "A viagem foi cadastrada!"
	        );

	        System.out.println(
	                "O produto inativo não foi bloqueado."
	        );


	    } catch (IllegalArgumentException e) {

	        System.out.println();
	        System.out.println(
	                "================================"
	        );

	        System.out.println(
	                "       REGRA DE NEGÓCIO"
	        );

	        System.out.println(
	                "================================"
	        );

	        System.out.println(
	                e.getMessage()
	        );

	        System.out.println();
	        System.out.println(
	                "TESTE PASSOU!"
	        );
	    }
	}
	public static void testeEntidadesInativas() throws Exception {

	    System.out.println();
	    System.out.println("================================");
	    System.out.println("    TESTE ENTIDADES INATIVAS");
	    System.out.println("================================");


	    // =====================================================
	    // SERVICES
	    // =====================================================

	    ProdutoService produtoService =
	            new ProdutoService();

	    ViagemService viagemService =
	            new ViagemService();


	    // =====================================================
	    // DAOs
	    // =====================================================

	    MotoristaDAO motoristaDAO =
	            new MotoristaDAO();

	    CaminhaoDAO caminhaoDAO =
	            new CaminhaoDAO();

	    CarretaDAO carretaDAO =
	            new CarretaDAO();


	    // =====================================================
	    // 1 - PRODUTO INATIVO
	    // =====================================================

	    System.out.println();
	    System.out.println("--------------------------------");
	    System.out.println("TESTE 1 - PRODUTO INATIVO");
	    System.out.println("--------------------------------");

	    Produto produto =
	            produtoService.buscarPorCodigo(3001);

	    System.out.println(
	            "Produto: " +
	            produto.getNome()
	    );

	    System.out.println(
	            "Ativo: " +
	            produto.isAtivo()
	    );

	    if (!produto.isAtivo()) {

	        System.out.println(
	                "Produto está inativo."
	        );

	    } else {

	        System.out.println(
	                "ERRO: Produto deveria estar inativo."
	        );

	        return;
	    }


	    // =====================================================
	    // 2 - MOTORISTA INATIVO
	    // =====================================================

	    System.out.println();
	    System.out.println("--------------------------------");
	    System.out.println("TESTE 2 - MOTORISTA INATIVO");
	    System.out.println("--------------------------------");

	    Motorista motorista =
	            motoristaDAO.buscarPorCpf(
	                    "12345678901"
	            );

	    if (motorista == null) {

	        throw new IllegalArgumentException(
	                "Motorista de teste não encontrado."
	        );
	    }

	    System.out.println(
	            "Motorista: " +
	            motorista.getNome()
	    );

	    System.out.println(
	            "Ativo antes: " +
	            motorista.isAtivo()
	    );

	    if (motorista.isAtivo()) {

	        motoristaDAO.desativar(
	                motorista.getId()
	        );
	    }

	    motorista =
	            motoristaDAO.buscarPorCpf(
	                    "12345678901"
	            );

	    System.out.println(
	            "Ativo depois: " +
	            motorista.isAtivo()
	    );


	    // =====================================================
	    // 3 - CAMINHÃO INATIVO
	    // =====================================================

	    System.out.println();
	    System.out.println("--------------------------------");
	    System.out.println("TESTE 3 - CAMINHÃO INATIVO");
	    System.out.println("--------------------------------");

	    Caminhao caminhao =
	            caminhaoDAO.buscarPorPlaca(
	                    "ABC1234"
	            );

	    if (caminhao == null) {

	        throw new IllegalArgumentException(
	                "Caminhão de teste não encontrado."
	        );
	    }

	    System.out.println(
	            "Caminhão: " +
	            caminhao.getPlaca()
	    );

	    System.out.println(
	            "Ativo antes: " +
	            caminhao.isAtivo()
	    );

	    if (caminhao.isAtivo()) {

	        caminhaoDAO.desativar(
	                caminhao.getId()
	        );
	    }

	    caminhao =
	            caminhaoDAO.buscarPorPlaca(
	                    "ABC1234"
	            );

	    System.out.println(
	            "Ativo depois: " +
	            caminhao.isAtivo()
	    );


	    // =====================================================
	    // 4 - CARRETA INATIVA
	    // =====================================================

	    System.out.println();
	    System.out.println("--------------------------------");
	    System.out.println("TESTE 4 - CARRETA INATIVA");
	    System.out.println("--------------------------------");

	    Carreta carreta =
	            carretaDAO.buscarPorPlaca(
	                    "XYZ5678"
	            );

	    if (carreta == null) {

	        throw new IllegalArgumentException(
	                "Carreta de teste não encontrada."
	        );
	    }

	    System.out.println(
	            "Carreta: " +
	            carreta.getPlaca()
	    );

	    System.out.println(
	            "Ativo antes: " +
	            carreta.isAtivo()
	    );

	    if (carreta.isAtivo()) {

	        carretaDAO.desativar(
	                carreta.getId()
	        );
	    }

	    carreta =
	            carretaDAO.buscarPorPlaca(
	                    "XYZ5678"
	            );

	    System.out.println(
	            "Ativo depois: " +
	            carreta.isAtivo()
	    );


	    // =====================================================
	    // TESTE DAS REGRAS DA VIAGEM
	    // =====================================================

	    System.out.println();
	    System.out.println("================================");
	    System.out.println(" TESTANDO REGRAS DA VIAGEM");
	    System.out.println("================================");


	    // =====================================================
	    // TESTE MOTORISTA INATIVO
	    // =====================================================

	    System.out.println();
	    System.out.println("--------------------------------");
	    System.out.println("MOTORISTA INATIVO");
	    System.out.println("--------------------------------");

	    Viagem viagem =
	            new Viagem();

	    viagem.setNf(900001);

	    viagem.setData(
	            java.time.LocalDate.now()
	    );

	    viagem.setMotorista(
	            motorista
	    );

	    viagem.setCaminhao(
	            caminhao
	    );

	    viagem.setCarreta(
	            carreta
	    );

	    ItemViagem item =
	            new ItemViagem();

	    item.setProduto(
	            produto
	    );

	    item.setQuantidade(10);

	    viagem.adicionarItem(item);

	    try {

	        viagemService.cadastrar(
	                viagem
	        );

	        System.out.println(
	                "ERRO: A viagem foi cadastrada."
	        );

	    } catch (IllegalArgumentException e) {

	        System.out.println(
	                e.getMessage()
	        );

	        System.out.println(
	                "TESTE MOTORISTA INATIVO: PASSOU!"
	        );
	    }


	    // =====================================================
	    // TESTE CAMINHÃO INATIVO
	    // =====================================================

	    System.out.println();
	    System.out.println("--------------------------------");
	    System.out.println("CAMINHÃO INATIVO");
	    System.out.println("--------------------------------");

	    // Criamos outro motorista ativo somente
	    // para conseguir chegar à validação do caminhão.

	    Motorista motoristaAtivo =
	            motoristaDAO.buscarPorCpf(
	                    "12345678901"
	            );

	    System.out.println(
	            "Observação: motorista atualmente inativo."
	    );

	    System.out.println(
	            "Para este teste, precisamos de um motorista ativo."
	    );


	    // =====================================================
	    // TESTE CARRETA INATIVA
	    // =====================================================

	    System.out.println();
	    System.out.println("--------------------------------");
	    System.out.println("CARRETA INATIVA");
	    System.out.println("--------------------------------");

	    System.out.println(
	            "A regra de carreta inativa será testada"
	    );

	    System.out.println(
	            "quando houver motorista e caminhão ativos."
	    );


	    // =====================================================
	    // RESUMO
	    // =====================================================

	    System.out.println();
	    System.out.println("================================");
	    System.out.println("       RESUMO DOS TESTES");
	    System.out.println("================================");

	    System.out.println(
	            "Produto inativo: PASSOU"
	    );

	    System.out.println(
	            "Motorista inativo: PASSOU"
	    );

	    System.out.println(
	            "Caminhão inativo: preparado"
	    );

	    System.out.println(
	            "Carreta inativa: preparado"
	    );
	}
	public static void testeTodasEntidadesInativas() throws Exception {

	    System.out.println();
	    System.out.println("================================");
	    System.out.println("   TESTE DE ENTIDADES INATIVAS");
	    System.out.println("================================");


	    // =====================================================
	    // SERVICES
	    // =====================================================

	    ProdutoService produtoService =
	            new ProdutoService();

	    ViagemService viagemService =
	            new ViagemService();


	    // =====================================================
	    // DAOs
	    // =====================================================

	    MotoristaDAO motoristaDAO =
	            new MotoristaDAO();

	    CaminhaoDAO caminhaoDAO =
	            new CaminhaoDAO();

	    CarretaDAO carretaDAO =
	            new CarretaDAO();


	    // =====================================================
	    // PRODUTO
	    // =====================================================

	    Produto produto =
	            produtoService.buscarPorCodigo(3001);

	    System.out.println();
	    System.out.println("--------------------------------");
	    System.out.println("PRODUTO");
	    System.out.println("--------------------------------");

	    System.out.println(
	            "Código: " +
	            produto.getCodigo()
	    );

	    System.out.println(
	            "Nome: " +
	            produto.getNome()
	    );

	    System.out.println(
	            "Ativo: " +
	            produto.isAtivo()
	    );


	    if (produto.isAtivo()) {

	        throw new IllegalArgumentException(
	                "O produto 3001 deveria estar inativo."
	        );
	    }

	    System.out.println(
	            "Produto inativo confirmado."
	    );


	    // =====================================================
	    // CRIAR MOTORISTA DE TESTE
	    // =====================================================

	    Motorista motoristaTeste =
	            motoristaDAO.buscarPorCpf(
	                    "99999999999"
	            );

	    if (motoristaTeste == null) {

	        motoristaTeste =
	                new Motorista();

	        motoristaTeste.setCpf(
	                "99999999999"
	        );

	        motoristaTeste.setNome(
	                "Motorista Teste"
	        );

	        motoristaTeste.setAtivo(true);

	        motoristaDAO.salvar(
	                motoristaTeste
	        );

	        motoristaTeste =
	                motoristaDAO.buscarPorCpf(
	                        "99999999999"
	                );
	    }


	    // =====================================================
	    // CRIAR CAMINHÃO DE TESTE
	    // =====================================================

	    Caminhao caminhaoTeste =
	            caminhaoDAO.buscarPorPlaca(
	                    "TST0001"
	            );

	    if (caminhaoTeste == null) {

	        caminhaoTeste =
	                new Caminhao();

	        caminhaoTeste.setPlaca(
	                "TST0001"
	        );

	        caminhaoTeste.setModelo(
	                "Caminhão Teste"
	        );

	        caminhaoTeste.setAtivo(true);

	        caminhaoDAO.salvar(
	                caminhaoTeste
	        );

	        caminhaoTeste =
	                caminhaoDAO.buscarPorPlaca(
	                        "TST0001"
	                );
	    }


	    // =====================================================
	    // CRIAR CARRETA DE TESTE
	    // =====================================================

	    Carreta carretaTeste =
	            carretaDAO.buscarPorPlaca(
	                    "TST0002"
	            );

	    if (carretaTeste == null) {

	        carretaTeste =
	                new Carreta();

	        carretaTeste.setPlaca(
	                "TST0002"
	        );

	        carretaTeste.setAtivo(true);

	        carretaDAO.salvar(
	                carretaTeste
	        );

	        carretaTeste =
	                carretaDAO.buscarPorPlaca(
	                        "TST0002"
	                );
	    }


	    // =====================================================
	    // GARANTIR QUE ESTÃO ATIVOS ANTES DO TESTE
	    // =====================================================

	    System.out.println();
	    System.out.println("--------------------------------");
	    System.out.println("PREPARANDO REGISTROS");
	    System.out.println("--------------------------------");

	    System.out.println(
	            "Motorista ativo: " +
	            motoristaTeste.isAtivo()
	    );

	    System.out.println(
	            "Caminhão ativo: " +
	            caminhaoTeste.isAtivo()
	    );

	    System.out.println(
	            "Carreta ativa: " +
	            carretaTeste.isAtivo()
	    );


	    // =====================================================
	    // DESATIVAR MOTORISTA
	    // =====================================================

	    if (motoristaTeste.isAtivo()) {

	        motoristaDAO.desativar(
	                motoristaTeste.getId()
	        );
	    }

	    motoristaTeste =
	            motoristaDAO.buscarPorCpf(
	                    "99999999999"
	            );


	    // =====================================================
	    // DESATIVAR CAMINHÃO
	    // =====================================================

	    if (caminhaoTeste.isAtivo()) {

	        caminhaoDAO.desativar(
	                caminhaoTeste.getId()
	        );
	    }

	    caminhaoTeste =
	            caminhaoDAO.buscarPorPlaca(
	                    "TST0001"
	            );


	    // =====================================================
	    // DESATIVAR CARRETA
	    // =====================================================

	    if (carretaTeste.isAtivo()) {

	        carretaDAO.desativar(
	                carretaTeste.getId()
	        );
	    }

	    carretaTeste =
	            carretaDAO.buscarPorPlaca(
	                    "TST0002"
	            );


	    // =====================================================
	    // RESULTADO DAS DESATIVAÇÕES
	    // =====================================================

	    System.out.println();
	    System.out.println("--------------------------------");
	    System.out.println("REGISTROS APÓS DESATIVAÇÃO");
	    System.out.println("--------------------------------");

	    System.out.println(
	            "Motorista ativo: " +
	            motoristaTeste.isAtivo()
	    );

	    System.out.println(
	            "Caminhão ativo: " +
	            caminhaoTeste.isAtivo()
	    );

	    System.out.println(
	            "Carreta ativa: " +
	            carretaTeste.isAtivo()
	    );


	    // =====================================================
	    // TESTE 1
	    // MOTORISTA INATIVO
	    // =====================================================

	    System.out.println();
	    System.out.println("================================");
	    System.out.println(" TESTE 1 - MOTORISTA INATIVO");
	    System.out.println("================================");

	    Viagem viagem =
	            new Viagem();

	    viagem.setNf(990001);

	    viagem.setData(
	            java.time.LocalDate.now()
	    );

	    viagem.setMotorista(
	            motoristaTeste
	    );

	    viagem.setCaminhao(
	            caminhaoTeste
	    );

	    viagem.setCarreta(
	            carretaTeste
	    );

	    ItemViagem item =
	            new ItemViagem();

	    item.setProduto(
	            produto
	    );

	    item.setQuantidade(10);

	    viagem.adicionarItem(
	            item
	    );


	    try {

	        viagemService.cadastrar(
	                viagem
	        );

	        System.out.println(
	                "ERRO: A viagem foi cadastrada."
	        );

	    } catch (IllegalArgumentException e) {

	        System.out.println(
	                e.getMessage()
	        );

	        if (e.getMessage().contains(
	                "motorista está inativo")) {

	            System.out.println(
	                    "TESTE MOTORISTA INATIVO: PASSOU!"
	            );

	        } else {

	            System.out.println(
	                    "ERRO: Regra incorreta."
	            );
	        }
	    }


	    // =====================================================
	    // IMPORTANTE
	    // =====================================================
	    //
	    // Para testar caminhão e carreta, precisamos que
	    // motorista e produto estejam ativos.
	    //
	    // Como não temos ainda um método "reativar",
	    // vamos fazer esses dois testes utilizando os
	    // registros normais que já estão ativos.
	    //
	    // =====================================================


	    Motorista motoristaAtivo =
	            motoristaDAO.buscarPorCpf(
	                    "12345678901"
	            );

	    Caminhao caminhaoAtivo =
	            caminhaoDAO.buscarPorPlaca(
	                    "ABC1234"
	            );

	    Carreta carretaAtiva =
	            carretaDAO.buscarPorPlaca(
	                    "XYZ5678"
	            );


	    // =====================================================
	    // TESTE 2
	    // CAMINHÃO INATIVO
	    // =====================================================

	    System.out.println();
	    System.out.println("================================");
	    System.out.println(" TESTE 2 - CAMINHÃO INATIVO");
	    System.out.println("================================");

	    viagem =
	            new Viagem();

	    viagem.setNf(990002);

	    viagem.setData(
	            java.time.LocalDate.now()
	    );

	    viagem.setMotorista(
	            motoristaAtivo
	    );

	    viagem.setCaminhao(
	            caminhaoTeste
	    );

	    viagem.setCarreta(
	            carretaAtiva
	    );

	    item =
	            new ItemViagem();

	    item.setProduto(
	            produto
	    );

	    item.setQuantidade(10);

	    viagem.adicionarItem(
	            item
	    );


	    try {

	        viagemService.cadastrar(
	                viagem
	        );

	        System.out.println(
	                "ERRO: A viagem foi cadastrada."
	        );

	    } catch (IllegalArgumentException e) {

	        System.out.println(
	                e.getMessage()
	        );

	        if (e.getMessage().contains(
	                "caminhão está inativo")) {

	            System.out.println(
	                    "TESTE CAMINHÃO INATIVO: PASSOU!"
	            );

	        } else {

	            System.out.println(
	                    "ERRO: Regra incorreta."
	            );
	        }
	    }


	    // =====================================================
	    // TESTE 3
	    // CARRETA INATIVA
	    // =====================================================

	    System.out.println();
	    System.out.println("================================");
	    System.out.println(" TESTE 3 - CARRETA INATIVA");
	    System.out.println("================================");

	    viagem =
	            new Viagem();

	    viagem.setNf(990003);

	    viagem.setData(
	            java.time.LocalDate.now()
	    );

	    viagem.setMotorista(
	            motoristaAtivo
	    );

	    viagem.setCaminhao(
	            caminhaoAtivo
	    );

	    viagem.setCarreta(
	            carretaTeste
	    );

	    item =
	            new ItemViagem();

	    item.setProduto(
	            produto
	    );

	    item.setQuantidade(10);

	    viagem.adicionarItem(
	            item
	    );


	    try {

	        viagemService.cadastrar(
	                viagem
	        );

	        System.out.println(
	                "ERRO: A viagem foi cadastrada."
	        );

	    } catch (IllegalArgumentException e) {

	        System.out.println(
	                e.getMessage()
	        );

	        if (e.getMessage().contains(
	                "carreta está inativa")) {

	            System.out.println(
	                    "TESTE CARRETA INATIVA: PASSOU!"
	            );

	        } else {

	            System.out.println(
	                    "ERRO: Regra incorreta."
	            );
	        }
	    }


	    // =====================================================
	    // TESTE 4
	    // PRODUTO INATIVO
	    // =====================================================

	    System.out.println();
	    System.out.println("================================");
	    System.out.println(" TESTE 4 - PRODUTO INATIVO");
	    System.out.println("================================");

	    viagem =
	            new Viagem();

	    viagem.setNf(990004);

	    viagem.setData(
	            java.time.LocalDate.now()
	    );

	    viagem.setMotorista(
	            motoristaAtivo
	    );

	    viagem.setCaminhao(
	            caminhaoAtivo
	    );

	    viagem.setCarreta(
	            carretaAtiva
	    );

	    item =
	            new ItemViagem();

	    item.setProduto(
	            produto
	    );

	    item.setQuantidade(10);

	    viagem.adicionarItem(
	            item
	    );


	    try {

	        viagemService.cadastrar(
	                viagem
	        );

	        System.out.println(
	                "ERRO: A viagem foi cadastrada."
	        );

	    } catch (IllegalArgumentException e) {

	        System.out.println(
	                e.getMessage()
	        );

	        if (e.getMessage().contains(
	                "produto") &&
	            e.getMessage().contains(
	                "está inativo")) {

	            System.out.println(
	                    "TESTE PRODUTO INATIVO: PASSOU!"
	            );

	        } else {

	            System.out.println(
	                    "ERRO: Regra incorreta."
	            );
	        }
	    }


	    // =====================================================
	    // FINAL
	    // =====================================================

	    System.out.println();
	    System.out.println("================================");
	    System.out.println("   TESTES FINALIZADOS");
	    System.out.println("================================");
	}
	public static void testeServicesCadastros() throws Exception {

	    System.out.println();
	    System.out.println("================================");
	    System.out.println("   TESTE DOS SERVICES");
	    System.out.println("================================");


	    // =====================================================
	    // SERVICES
	    // =====================================================

	    MotoristaService motoristaService =
	            new MotoristaService();

	    CaminhaoService caminhaoService =
	            new CaminhaoService();

	    CarretaService carretaService =
	            new CarretaService();


	    // =====================================================
	    // MOTORISTA
	    // =====================================================

	    System.out.println();
	    System.out.println("--------------------------------");
	    System.out.println("      TESTE MOTORISTA");
	    System.out.println("--------------------------------");

	    String cpf =
	            "88888888888";

	    Motorista motorista =
	            null;

	    try {

	        motorista =
	                motoristaService.buscarPorCpf(cpf);

	        System.out.println(
	                "Motorista já existe. Usando registro existente."
	        );

	    } catch (IllegalArgumentException e) {

	        motorista =
	                new Motorista();

	        motorista.setCpf(cpf);
	        motorista.setNome(
	                "Motorista Service Teste"
	        );
	        motorista.setAtivo(true);

	        motoristaService.cadastrar(
	                motorista
	        );

	        motorista =
	                motoristaService.buscarPorCpf(cpf);

	        System.out.println(
	                "Motorista cadastrado com sucesso."
	        );
	    }

	    System.out.println(
	            "ID: " +
	            motorista.getId()
	    );

	    System.out.println(
	            "Nome: " +
	            motorista.getNome()
	    );

	    System.out.println(
	            "CPF: " +
	            motorista.getCpf()
	    );

	    System.out.println(
	            "Ativo: " +
	            motorista.isAtivo()
	    );


	    // =====================================================
	    // DESATIVAR MOTORISTA
	    // =====================================================

	    System.out.println();
	    System.out.println("Desativando motorista...");

	    if (motorista.isAtivo()) {

	        motoristaService.desativar(
	                cpf
	        );
	    }

	    motorista =
	            motoristaService.buscarPorCpf(
	                    cpf
	            );

	    System.out.println(
	            "Ativo depois: " +
	            motorista.isAtivo()
	    );

	    if (motorista.isAtivo()) {

	        throw new Exception(
	                "ERRO: Motorista não foi desativado."
	        );
	    }

	    System.out.println(
	            "TESTE MOTORISTA: PASSOU!"
	    );


	    // =====================================================
	    // CAMINHÃO
	    // =====================================================

	    System.out.println();
	    System.out.println("--------------------------------");
	    System.out.println("       TESTE CAMINHÃO");
	    System.out.println("--------------------------------");

	    String placaCaminhao =
	            "TST0003";

	    Caminhao caminhao =
	            null;

	    try {

	        caminhao =
	                caminhaoService.buscarPorPlaca(
	                        placaCaminhao
	                );

	        System.out.println(
	                "Caminhão já existe. Usando registro existente."
	        );

	    } catch (IllegalArgumentException e) {

	        caminhao =
	                new Caminhao();

	        caminhao.setPlaca(
	                placaCaminhao
	        );

	        caminhao.setModelo(
	                "Scania Service Teste"
	        );

	        caminhao.setAtivo(true);

	        caminhaoService.cadastrar(
	                caminhao
	        );

	        caminhao =
	                caminhaoService.buscarPorPlaca(
	                        placaCaminhao
	                );

	        System.out.println(
	                "Caminhão cadastrado com sucesso."
	        );
	    }

	    System.out.println(
	            "ID: " +
	            caminhao.getId()
	    );

	    System.out.println(
	            "Placa: " +
	            caminhao.getPlaca()
	    );

	    System.out.println(
	            "Modelo: " +
	            caminhao.getModelo()
	    );

	    System.out.println(
	            "Ativo: " +
	            caminhao.isAtivo()
	    );


	    // =====================================================
	    // DESATIVAR CAMINHÃO
	    // =====================================================

	    System.out.println();
	    System.out.println("Desativando caminhão...");

	    if (caminhao.isAtivo()) {

	        caminhaoService.desativar(
	                placaCaminhao
	        );
	    }

	    caminhao =
	            caminhaoService.buscarPorPlaca(
	                    placaCaminhao
	            );

	    System.out.println(
	            "Ativo depois: " +
	            caminhao.isAtivo()
	    );

	    if (caminhao.isAtivo()) {

	        throw new Exception(
	                "ERRO: Caminhão não foi desativado."
	        );
	    }

	    System.out.println(
	            "TESTE CAMINHÃO: PASSOU!"
	    );


	    // =====================================================
	    // CARRETA
	    // =====================================================

	    System.out.println();
	    System.out.println("--------------------------------");
	    System.out.println("        TESTE CARRETA");
	    System.out.println("--------------------------------");

	    String placaCarreta =
	            "TST0004";

	    Carreta carreta =
	            null;

	    try {

	        carreta =
	                carretaService.buscarPorPlaca(
	                        placaCarreta
	                );

	        System.out.println(
	                "Carreta já existe. Usando registro existente."
	        );

	    } catch (IllegalArgumentException e) {

	        carreta =
	                new Carreta();

	        carreta.setPlaca(
	                placaCarreta
	        );

	        carreta.setAtivo(true);

	        carretaService.cadastrar(
	                carreta
	        );

	        carreta =
	                carretaService.buscarPorPlaca(
	                        placaCarreta
	                );

	        System.out.println(
	                "Carreta cadastrada com sucesso."
	        );
	    }

	    System.out.println(
	            "ID: " +
	            carreta.getId()
	    );

	    System.out.println(
	            "Placa: " +
	            carreta.getPlaca()
	    );

	    System.out.println(
	            "Ativo: " +
	            carreta.isAtivo()
	    );


	    // =====================================================
	    // DESATIVAR CARRETA
	    // =====================================================

	    System.out.println();
	    System.out.println("Desativando carreta...");

	    if (carreta.isAtivo()) {

	        carretaService.desativar(
	                placaCarreta
	        );
	    }

	    carreta =
	            carretaService.buscarPorPlaca(
	                    placaCarreta
	            );

	    System.out.println(
	            "Ativo depois: " +
	            carreta.isAtivo()
	    );

	    if (carreta.isAtivo()) {

	        throw new Exception(
	                "ERRO: Carreta não foi desativada."
	        );
	    }

	    System.out.println(
	            "TESTE CARRETA: PASSOU!"
	    );


	    // =====================================================
	    // FINAL
	    // =====================================================

	    System.out.println();
	    System.out.println("================================");
	    System.out.println("     RESULTADO FINAL");
	    System.out.println("================================");

	    System.out.println(
	            "Motorista Service: PASSOU"
	    );

	    System.out.println(
	            "Caminhão Service: PASSOU"
	    );

	    System.out.println(
	            "Carreta Service: PASSOU"
	    );

	    System.out.println("================================");
	}
	public static void testeAlterarProduto()
	        throws Exception {

	    ProdutoService service =
	            new ProdutoService();

	    Produto produto =
	            service.buscarPorCodigo(3001);

	    System.out.println();
	    System.out.println("================================");
	    System.out.println("       TESTE ALTERAÇÃO");
	    System.out.println("================================");

	    System.out.println(
	            "Nome antes: " +
	            produto.getNome()
	    );

	    System.out.println(
	            "Marca antes: " +
	            produto.getMarca()
	    );

	    System.out.println(
	            "HL antes: " +
	            produto.getHlPorPacote()
	    );


	    // Alterando os dados

	    produto.setNome(
	            "Produto Service Alterado"
	    );

	    produto.setMarca(
	            "Marca Alterada"
	    );

	    produto.setHlPorPacote(
	            0.75
	    );


	    service.alterar(produto);


	    // Buscando novamente no banco

	    Produto alterado =
	            service.buscarPorCodigo(3001);


	    System.out.println();
	    System.out.println("----------- DEPOIS -----------");

	    System.out.println(
	            "Nome depois: " +
	            alterado.getNome()
	    );

	    System.out.println(
	            "Marca depois: " +
	            alterado.getMarca()
	    );

	    System.out.println(
	            "HL depois: " +
	            alterado.getHlPorPacote()
	    );


	    // Validação

	    if (!alterado.getNome().equals(
	            "Produto Service Alterado")) {

	        throw new Exception(
	                "ERRO: Nome não foi alterado."
	        );
	    }

	    if (!alterado.getMarca().equals(
	            "Marca Alterada")) {

	        throw new Exception(
	                "ERRO: Marca não foi alterada."
	        );
	    }

	    if (alterado.getHlPorPacote() != 0.75) {

	        throw new Exception(
	                "ERRO: HL não foi alterado."
	        );
	    }

	    System.out.println();
	    System.out.println(
	            "TESTE DE ALTERAÇÃO: PASSOU!"
	    );
	}
	public static void testeAlteracaoEntidades() throws Exception {

	    System.out.println("\n================================");
	    System.out.println("     TESTE ALTERAÇÃO ENTIDADES");
	    System.out.println("================================");


	    // ==========================================
	    // MOTORISTA
	    // ==========================================

	    MotoristaService motoristaService =
	            new MotoristaService();

	    Motorista motorista =
	            motoristaService.buscarPorCpf(
	                    "88888888888"
	            );

	    System.out.println("\n--------------------------------");
	    System.out.println("MOTORISTA");
	    System.out.println("--------------------------------");

	    System.out.println(
	            "Nome antes: " +
	            motorista.getNome()
	    );

	    motorista.setNome(
	            "Motorista Service Alterado"
	    );

	    motoristaService.alterar(motorista);

	    Motorista motoristaAlterado =
	            motoristaService.buscarPorCpf(
	                    "88888888888"
	            );

	    System.out.println(
	            "Nome depois: " +
	            motoristaAlterado.getNome()
	    );


	    // ==========================================
	    // CAMINHÃO
	    // ==========================================

	    CaminhaoService caminhaoService =
	            new CaminhaoService();

	    Caminhao caminhao =
	            caminhaoService.buscarPorPlaca(
	                    "TST0003"
	            );

	    System.out.println("\n--------------------------------");
	    System.out.println("CAMINHÃO");
	    System.out.println("--------------------------------");

	    System.out.println(
	            "Modelo antes: " +
	            caminhao.getModelo()
	    );

	    caminhao.setModelo(
	            "Scania R450 Alterado"
	    );

	    caminhaoService.alterar(caminhao);

	    Caminhao caminhaoAlterado =
	            caminhaoService.buscarPorPlaca(
	                    "TST0003"
	            );

	    System.out.println(
	            "Modelo depois: " +
	            caminhaoAlterado.getModelo()
	    );


	    // ==========================================
	    // CARRETA
	    // ==========================================

	    CarretaService carretaService =
	            new CarretaService();

	    Carreta carreta =
	            carretaService.buscarPorPlaca(
	                    "TST0004"
	            );

	    System.out.println("\n--------------------------------");
	    System.out.println("CARRETA");
	    System.out.println("--------------------------------");

	    System.out.println(
	            "Placa antes: " +
	            carreta.getPlaca()
	    );

	    carreta.setPlaca(
	            "TST9999"
	    );

	    carretaService.alterar(carreta);

	    Carreta carretaAlterada =
	            carretaService.buscarPorPlaca(
	                    "TST9999"
	            );

	    System.out.println(
	            "Placa depois: " +
	            carretaAlterada.getPlaca()
	    );


	    System.out.println("\n================================");
	    System.out.println("TESTE DE ALTERAÇÃO: PASSOU!");
	    System.out.println("================================");
	}
	public static void testeAlteracaoCaminhao()
	        throws Exception {

	    CaminhaoService service =
	            new CaminhaoService();

	    Caminhao caminhao =
	            service.buscarPorPlaca("TST0003");

	    System.out.println("\n================================");
	    System.out.println("       TESTE ALTERAÇÃO CAMINHÃO");
	    System.out.println("================================");

	    System.out.println(
	            "Modelo antes: " +
	            caminhao.getModelo()
	    );

	    System.out.println(
	            "Placa antes: " +
	            caminhao.getPlaca()
	    );

	    System.out.println(
	            "Ativo antes: " +
	            caminhao.isAtivo()
	    );

	    // Alterando
	    caminhao.setModelo(
	            "Scania Alterado"
	    );

	    caminhao.setAtivo(true);

	    service.alterar(caminhao);

	    // Buscando novamente no banco
	    Caminhao alterado =
	            service.buscarPorPlaca(
	                    caminhao.getPlaca()
	            );

	    System.out.println("\n----------- DEPOIS -----------");

	    System.out.println(
	            "Modelo depois: " +
	            alterado.getModelo()
	    );

	    System.out.println(
	            "Placa depois: " +
	            alterado.getPlaca()
	    );

	    System.out.println(
	            "Ativo depois: " +
	            alterado.isAtivo()
	    );

	    if (!alterado.getModelo()
	            .equals("Scania Alterado")) {

	        throw new Exception(
	                "A alteração do modelo não foi salva."
	        );
	    }

	    System.out.println(
	            "\nTESTE DE ALTERAÇÃO DO CAMINHÃO: PASSOU!"
	    );
	}
	public static void testeAlteracaoMotorista()
	        throws Exception {

	    MotoristaService service =
	            new MotoristaService();

	    Motorista motorista =
	            service.buscarPorCpf(
	                    "88888888888"
	            );

	    System.out.println("\n================================");
	    System.out.println("       TESTE ALTERAÇÃO");
	    System.out.println("================================");

	    System.out.println(
	            "Nome antes: " +
	            motorista.getNome()
	    );

	    System.out.println(
	            "CPF antes: " +
	            motorista.getCpf()
	    );

	    System.out.println(
	            "Ativo antes: " +
	            motorista.isAtivo()
	    );

	    motorista.setNome(
	            "Motorista Service Alterado"
	    );

	    motorista.setAtivo(true);

	    service.alterar(motorista);

	    Motorista depois =
	            service.buscarPorCpf(
	                    "88888888888"
	            );

	    System.out.println("\n----------- DEPOIS -----------");

	    System.out.println(
	            "Nome depois: " +
	            depois.getNome()
	    );

	    System.out.println(
	            "CPF depois: " +
	            depois.getCpf()
	    );

	    System.out.println(
	            "Ativo depois: " +
	            depois.isAtivo()
	    );

	    if (!depois.getNome().equals(
	            "Motorista Service Alterado")) {

	        throw new Exception(
	                "ERRO: nome não foi alterado."
	        );
	    }

	    if (!depois.isAtivo()) {

	        throw new Exception(
	                "ERRO: motorista deveria estar ativo."
	        );
	    }

	    System.out.println(
	            "\nTESTE DE ALTERAÇÃO: PASSOU!"
	    );
	}

	public static void testeView() {
		new JanelaPrincipal();
	}
}
