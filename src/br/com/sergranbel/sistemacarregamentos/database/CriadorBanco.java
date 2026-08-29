package br.com.sergranbel.sistemacarregamentos.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CriadorBanco {

    public static void criarTabelas() {

        try (Connection conexao = ConexaoBanco.conectar();
             Statement stmt = conexao.createStatement()) {

            // MOTORISTAS
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS motoristas (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    cpf TEXT NOT NULL UNIQUE,
                    nome TEXT NOT NULL,
                    ativo INTEGER NOT NULL DEFAULT 1
                )
            """);

            // CAMINHÕES
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS caminhoes (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    placa TEXT NOT NULL UNIQUE,
                    modelo TEXT NOT NULL,
                    ativo INTEGER NOT NULL DEFAULT 1
                )
            """);

            // CARRETAS
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS carretas (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    placa TEXT NOT NULL UNIQUE,
                    ativo INTEGER NOT NULL DEFAULT 1
                )
            """);

            // PRODUTOS
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS produtos (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    codigo INTEGER NOT NULL UNIQUE,
                    nome TEXT NOT NULL,
                    marca TEXT NOT NULL,
                    hl_por_pacote REAL NOT NULL,
                    retornavel INTEGER NOT NULL,
                    ativo INTEGER NOT NULL DEFAULT 1
                )
            """);

            // VIAGENS
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS viagens (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nf INTEGER NOT NULL UNIQUE,
                    data TEXT NOT NULL,
                    motorista_id INTEGER NOT NULL,
                    caminhao_id INTEGER NOT NULL,
                    carreta_id INTEGER NOT NULL,

                    FOREIGN KEY (motorista_id)
                        REFERENCES motoristas(id),

                    FOREIGN KEY (caminhao_id)
                        REFERENCES caminhoes(id),

                    FOREIGN KEY (carreta_id)
                        REFERENCES carretas(id)
                )
            """);

            // ITENS DA VIAGEM
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS itens_viagem (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    viagem_id INTEGER NOT NULL,
                    produto_id INTEGER NOT NULL,
                    quantidade INTEGER NOT NULL,
                    hl REAL NOT NULL,
                    hl_retornavel REAL NOT NULL,

                    FOREIGN KEY (viagem_id)
                        REFERENCES viagens(id),

                    FOREIGN KEY (produto_id)
                        REFERENCES produtos(id),

                    UNIQUE (viagem_id, produto_id)
                )
            """);

            System.out.println("TABELAS CRIADAS COM SUCESSO!");

        } catch (SQLException e) {

            System.out.println("ERRO AO CRIAR AS TABELAS.");

            e.printStackTrace();
        }
    }
}