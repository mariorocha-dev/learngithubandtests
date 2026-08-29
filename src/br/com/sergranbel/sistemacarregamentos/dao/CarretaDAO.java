package br.com.sergranbel.sistemacarregamentos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.sergranbel.sistemacarregamentos.database.ConexaoBanco;
import br.com.sergranbel.sistemacarregamentos.model.Carreta;

public class CarretaDAO {

    public void salvar(Carreta carreta) throws Exception {

        String sql = """
                INSERT INTO carretas
                (placa, ativo)
                VALUES (?, ?)
                """;

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, carreta.getPlaca());
            stmt.setBoolean(2, carreta.isAtivo());

            stmt.executeUpdate();
        }
    }

    public Carreta buscarPorPlaca(String placa) throws Exception {

        String sql = """
                SELECT id, placa, ativo
                FROM carretas
                WHERE placa = ?
                """;

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, placa);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Carreta carreta = new Carreta();

                carreta.setId(rs.getInt("id"));
                carreta.setPlaca(rs.getString("placa"));
                carreta.setAtivo(rs.getBoolean("ativo"));

                return carreta;
            }
        }

        return null;
    }

    public void desativar(int id) throws Exception {

        String sql = """
                UPDATE carretas
                SET ativo = 0
                WHERE id = ?
                """;

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }
    public Carreta buscarPorId(
            Connection conexao,
            int id) throws Exception {

        String sql = """
                SELECT id, placa, ativo
                FROM carretas
                WHERE id = ?
                """;

        try (PreparedStatement stmt =
                conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs =
                    stmt.executeQuery()) {

                if (rs.next()) {

                    Carreta carreta =
                            new Carreta();

                    carreta.setId(
                            rs.getInt("id")
                    );

                    carreta.setPlaca(
                            rs.getString("placa")
                    );

                    carreta.setAtivo(
                            rs.getBoolean("ativo")
                    );

                    return carreta;
                }
            }
        }

        return null;
    }
    public void alterar(Carreta carreta) throws Exception {

        String sql = """
                UPDATE carretas
                SET placa = ?,
                    ativo = ?
                WHERE id = ?
                """;

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            stmt.setString(1, carreta.getPlaca());
            stmt.setBoolean(2, carreta.isAtivo());
            stmt.setInt(3, carreta.getId());

            stmt.executeUpdate();
        }
    }
    public Carreta buscarPorId(int id) throws Exception {

        String sql = """
                SELECT id, placa, ativo
                FROM carretas
                WHERE id = ?
                """;

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Carreta carreta = new Carreta();

                    carreta.setId(
                            rs.getInt("id")
                    );

                    carreta.setPlaca(
                            rs.getString("placa")
                    );

                    carreta.setAtivo(
                            rs.getBoolean("ativo")
                    );

                    return carreta;
                }
            }
        }

        return null;
    }
    public java.util.List<Carreta> listarAtivos()
            throws Exception {

        String sql = """
                SELECT id, placa, ativo
                FROM carretas
                WHERE ativo = 1
                ORDER BY placa
                """;

        java.util.List<Carreta> carretas =
                new java.util.ArrayList<>();

        try (Connection conexao =
                     ConexaoBanco.conectar();
             PreparedStatement stmt =
                     conexao.prepareStatement(sql);
             ResultSet resultado =
                     stmt.executeQuery()) {

            while (resultado.next()) {

                Carreta carreta =
                        new Carreta();

                carreta.setId(
                        resultado.getInt("id")
                );

                carreta.setPlaca(
                        resultado.getString("placa")
                );

                carreta.setAtivo(
                        resultado.getBoolean("ativo")
                );

                carretas.add(carreta);
            }
        }

        return carretas;
    }
}