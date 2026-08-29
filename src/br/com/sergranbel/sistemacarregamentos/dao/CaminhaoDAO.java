package br.com.sergranbel.sistemacarregamentos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.sergranbel.sistemacarregamentos.database.ConexaoBanco;
import br.com.sergranbel.sistemacarregamentos.model.Caminhao;

public class CaminhaoDAO {

    public void salvar(Caminhao caminhao) throws Exception {

        String sql = """
                INSERT INTO caminhoes
                (placa, modelo, ativo)
                VALUES (?, ?, ?)
                """;

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, caminhao.getPlaca());
            stmt.setString(2, caminhao.getModelo());
            stmt.setBoolean(3, caminhao.isAtivo());

            stmt.executeUpdate();
        }
    }

    public Caminhao buscarPorPlaca(String placa) throws Exception {

        String sql = """
                SELECT *
                FROM caminhoes
                WHERE placa = ?
                """;

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, placa);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Caminhao caminhao = new Caminhao();

                caminhao.setId(rs.getInt("id"));
                caminhao.setPlaca(rs.getString("placa"));
                caminhao.setModelo(rs.getString("modelo"));
                caminhao.setAtivo(rs.getBoolean("ativo"));

                return caminhao;
            }
        }

        return null;
    }
    public void desativar(int id) throws Exception {

        String sql = """
                UPDATE caminhoes
                SET ativo = 0
                WHERE id = ?
                """;

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }
    public Caminhao buscarPorId(
            Connection conexao,
            int id) throws Exception {

        String sql = """
                SELECT id, placa, modelo, ativo
                FROM caminhoes
                WHERE id = ?
                """;

        try (PreparedStatement stmt =
                conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs =
                    stmt.executeQuery()) {

                if (rs.next()) {

                    Caminhao caminhao =
                            new Caminhao();

                    caminhao.setId(
                            rs.getInt("id")
                    );

                    caminhao.setPlaca(
                            rs.getString("placa")
                    );

                    caminhao.setModelo(
                            rs.getString("modelo")
                    );

                    caminhao.setAtivo(
                            rs.getBoolean("ativo")
                    );

                    return caminhao;
                }
            }
        }

        return null;
    }
    public void alterar(Caminhao caminhao) throws Exception {

        String sql = """
                UPDATE caminhoes
                SET placa = ?,
                    modelo = ?,
                    ativo = ?
                WHERE id = ?
                """;

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            stmt.setString(1, caminhao.getPlaca());
            stmt.setString(2, caminhao.getModelo());
            stmt.setBoolean(3, caminhao.isAtivo());
            stmt.setInt(4, caminhao.getId());

            stmt.executeUpdate();
        }
    }
    public java.util.List<Caminhao> listarAtivos()
            throws Exception {

        String sql = """
                SELECT id, placa, modelo, ativo
                FROM caminhoes
                WHERE ativo = 1
                ORDER BY placa
                """;

        java.util.List<Caminhao> caminhoes =
                new java.util.ArrayList<>();

        try (Connection conexao =
                     ConexaoBanco.conectar();
             PreparedStatement stmt =
                     conexao.prepareStatement(sql);
             ResultSet resultado =
                     stmt.executeQuery()) {

            while (resultado.next()) {

                Caminhao caminhao =
                        new Caminhao();

                caminhao.setId(
                        resultado.getInt("id")
                );

                caminhao.setPlaca(
                        resultado.getString("placa")
                );

                caminhao.setModelo(
                        resultado.getString("modelo")
                );

                caminhao.setAtivo(
                        resultado.getBoolean("ativo")
                );

                caminhoes.add(caminhao);
            }
        }

        return caminhoes;
    }
}