package br.com.sergranbel.sistemacarregamentos.main;

import br.com.sergranbel.sistemacarregamento.testes.Teste;
import br.com.sergranbel.sistemacarregamentos.database.CriadorBanco;

public class Main {

    public static void main(String[] args) throws Exception {

        CriadorBanco.criarTabelas();

        //Teste.testeTerminal();
        //Teste.testeProdutoInativoNaViagem();
        //Teste.testeTodasEntidadesInativas();
     	Teste.testeView();
        //Teste.testeServicesCadastros();
        //Teste.testeAlterarProduto();
        //Teste.testeAlteracaoMotorista();
        
    }
}