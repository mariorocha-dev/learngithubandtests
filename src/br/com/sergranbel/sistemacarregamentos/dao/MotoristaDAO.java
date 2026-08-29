package br.com.sergranbel.sistemacarregamentos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.sergranbel.sistemacarregamentos.database.ConexaoBanco;
import br.com.sergranbel.sistemacarregamentos.model.Motorista;

public class MotoristaDAO {

    public void salvar(Motorista motorista) throws Exception {

        String sql = """
                INSERT INTO motoristas
                (cpf, nome, ativo)
                VALUES (?, ?, ?)
                """;

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, motorista.getCpf());
            stmt.setString(2, motorista.getNome());
            stmt.setBoolean(3, motorista.isAtivo());

            stmt.executeUpdate();
        }
    }

    public Motorista buscarPorCpf(String cpf) throws Exception {

        String sql = """
                SELECT id, cpf, nome, ativo
                FROM motoristas
                WHERE cpf = ?
                """;

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {

                Motorista motorista = new Motorista();

                motorista.setId(resultado.getInt("id"));
                motorista.setCpf(resultado.getString("cpf"));
                motorista.setNome(resultado.getString("nome"));
                motorista.setAtivo(resultado.getBoolean("ativo"));

                return motorista;
            }
        }

        return null;
    }
    public Motorista buscarPorId(
            Connection conexao,
            int id) throws Exception {

        String sql = """
                SELECT id, cpf, nome, ativo
                FROM motoristas
                WHERE id = ?
                """;

        try (PreparedStatement stmt =
                conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet resultado =
                    stmt.executeQuery()) {

                if (resultado.next()) {

                    Motorista motorista =
                            new Motorista();

                    motorista.setId(
                            resultado.getInt("id")
                    );

                    motorista.setCpf(
                            resultado.getString("cpf")
                    );

                    motorista.setNome(
                            resultado.getString("nome")
                    );

                    motorista.setAtivo(
                            resultado.getBoolean("ativo")
                    );

                    return motorista;
                }
            }
        }

        return null;
    }
    public Motorista buscarPorId(int id)
            throws Exception {

        String sql = """
                SELECT id, cpf, nome, ativo
                FROM motoristas
                WHERE id = ?
                """;

        try (Connection conexao =
                     ConexaoBanco.conectar();
             PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs =
                    stmt.executeQuery();

            if (rs.next()) {

                Motorista motorista =
                        new Motorista();

                motorista.setId(
                        rs.getInt("id")
                );

                motorista.setCpf(
                        rs.getString("cpf")
                );

                motorista.setNome(
                        rs.getString("nome")
                );

                motorista.setAtivo(
                        rs.getBoolean("ativo")
                );

                return motorista;
            }
        }

        return null;
    }
    public void desativar(int id) throws Exception {

        String sql = """
                UPDATE motoristas
                SET ativo = 0
                WHERE id = ?
                """;

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }
    public void alterar(Motorista motorista) throws Exception {

        String sql = """
                UPDATE motoristas
                SET cpf = ?,
                    nome = ?,
                    ativo = ?
                WHERE id = ?
                """;

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, motorista.getCpf());
            stmt.setString(2, motorista.getNome());
            stmt.setBoolean(3, motorista.isAtivo());
            stmt.setInt(4, motorista.getId());

            stmt.executeUpdate();
        }
    }
    public java.util.List<Motorista> listarAtivos()
            throws Exception {

        String sql = """
                SELECT id, cpf, nome, ativo
                FROM motoristas
                WHERE ativo = 1
                ORDER BY nome
                """;

        java.util.List<Motorista> motoristas =
                new java.util.ArrayList<>();

        try (Connection conexao =
                     ConexaoBanco.conectar();
             PreparedStatement stmt =
                     conexao.prepareStatement(sql);
             ResultSet resultado =
                     stmt.executeQuery()) {

            while (resultado.next()) {

                Motorista motorista =
                        new Motorista();

                motorista.setId(
                        resultado.getInt("id")
                );

                motorista.setCpf(
                        resultado.getString("cpf")
                );

                motorista.setNome(
                        resultado.getString("nome")
                );

                motorista.setAtivo(
                        resultado.getBoolean("ativo")
                );

                motoristas.add(motorista);
            }
        }

        return motoristas;
    }
}