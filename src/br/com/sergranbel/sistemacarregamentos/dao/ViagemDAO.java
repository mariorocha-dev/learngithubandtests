package br.com.sergranbel.sistemacarregamentos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import br.com.sergranbel.sistemacarregamentos.database.ConexaoBanco;
import br.com.sergranbel.sistemacarregamentos.model.*;

public class ViagemDAO {

    public int salvar(Connection conexao, Viagem viagem) throws Exception {

        String sql = """
                INSERT INTO viagens
                (nf, data, motorista_id, caminhao_id, carreta_id)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement stmt =
                     conexao.prepareStatement(
                             sql,
                             java.sql.Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, viagem.getNf());
            stmt.setString(2, viagem.getData().toString());
            stmt.setInt(3, viagem.getMotorista().getId());
            stmt.setInt(4, viagem.getCaminhao().getId());
            stmt.setInt(5, viagem.getCarreta().getId());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }

            throw new Exception(
                    "Não foi possível obter o ID da viagem."
            );
        }
    }
    public Viagem buscarPorId(
            Connection conexao,
            int viagemId) throws Exception {

        String sql = """
                SELECT id, nf, data,
                       motorista_id,
                       caminhao_id,
                       carreta_id
                FROM viagens
                WHERE id = ?
                """;

        try (PreparedStatement stmt =
                conexao.prepareStatement(sql)) {

            stmt.setInt(1, viagemId);

            try (ResultSet rs =
                    stmt.executeQuery()) {

                if (rs.next()) {

                    Viagem viagem =
                            new Viagem();

                    viagem.setId(
                            rs.getInt("id")
                    );

                    viagem.setNf(
                            rs.getInt("nf")
                    );

                    viagem.setData(
                            java.time.LocalDate.parse(
                                    rs.getString("data")
                            )
                    );


                    // ==============================
                    // MOTORISTA
                    // ==============================

                    MotoristaDAO motoristaDAO =
                            new MotoristaDAO();

                    Motorista motorista =
                            motoristaDAO.buscarPorId(
                                    conexao,
                                    rs.getInt("motorista_id")
                            );

                    viagem.setMotorista(motorista);


                    // ==============================
                    // CAMINHÃO
                    // ==============================

                    CaminhaoDAO caminhaoDAO =
                            new CaminhaoDAO();

                    Caminhao caminhao =
                            caminhaoDAO.buscarPorId(
                                    conexao,
                                    rs.getInt("caminhao_id")
                            );

                    viagem.setCaminhao(caminhao);


                    // ==============================
                    // CARRETA
                    // ==============================

                    CarretaDAO carretaDAO =
                            new CarretaDAO();

                    Carreta carreta =
                            carretaDAO.buscarPorId(
                                    conexao,
                                    rs.getInt("carreta_id")
                            );

                    viagem.setCarreta(carreta);


                    return viagem;
                }
            }
        }

        return null;
    }
    public Viagem buscarPorNf(int nf) throws Exception {

        String sql = """
                SELECT id, nf, data,
                       motorista_id,
                       caminhao_id,
                       carreta_id
                FROM viagens
                WHERE nf = ?
                """;

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, nf);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Viagem viagem = new Viagem();

                viagem.setId(rs.getInt("id"));
                viagem.setNf(rs.getInt("nf"));

                viagem.setData(
                        java.time.LocalDate.parse(
                                rs.getString("data")
                        )
                );

                return viagem;
            }
        }

        return null;
    }
    public boolean existePorNf(int nf) throws Exception {

        String sql = """
                SELECT COUNT(*)
                FROM viagens
                WHERE nf = ?
                """;

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            stmt.setInt(1, nf);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }

        return false;
    }
    public List<Viagem> buscarPorMotorista(
            Connection conexao,
            int motoristaId) throws Exception {

        String sql = """
                SELECT id, nf, data,
                       motorista_id,
                       caminhao_id,
                       carreta_id
                FROM viagens
                WHERE motorista_id = ?
                ORDER BY data DESC, id DESC
                """;

        List<Viagem> viagens = new ArrayList<>();

        try (PreparedStatement stmt =
                conexao.prepareStatement(sql)) {

            stmt.setInt(1, motoristaId);

            try (ResultSet rs =
                    stmt.executeQuery()) {

                while (rs.next()) {

                    Viagem viagem =
                            new Viagem();

                    viagem.setId(
                            rs.getInt("id")
                    );

                    viagem.setNf(
                            rs.getInt("nf")
                    );

                    viagem.setData(
                            java.time.LocalDate.parse(
                                    rs.getString("data")
                            )
                    );

                    // ==============================
                    // MOTORISTA
                    // ==============================

                    MotoristaDAO motoristaDAO =
                            new MotoristaDAO();

                    Motorista motorista =
                            motoristaDAO.buscarPorId(
                                    conexao,
                                    rs.getInt("motorista_id")
                            );

                    viagem.setMotorista(motorista);


                    // ==============================
                    // CAMINHÃO
                    // ==============================

                    CaminhaoDAO caminhaoDAO =
                            new CaminhaoDAO();

                    Caminhao caminhao =
                            caminhaoDAO.buscarPorId(
                                    conexao,
                                    rs.getInt("caminhao_id")
                            );

                    viagem.setCaminhao(caminhao);


                    // ==============================
                    // CARRETA
                    // ==============================

                    CarretaDAO carretaDAO =
                            new CarretaDAO();

                    Carreta carreta =
                            carretaDAO.buscarPorId(
                                    conexao,
                                    rs.getInt("carreta_id")
                            );

                    viagem.setCarreta(carreta);


                    // ==============================
                    // PRODUTOS
                    // ==============================

                    ItemViagemDAO itemViagemDAO =
                            new ItemViagemDAO();

                    List<ItemViagem> itens =
                            itemViagemDAO.buscarPorViagem(
                                    conexao,
                                    viagem.getId()
                            );

                    viagem.setItens(itens);


                    viagens.add(viagem);
                }
            }
        }

        return viagens;
    }
    public List<Viagem> buscarPorPeriodo(
            Connection conexao,
            LocalDate inicio,
            LocalDate fim) throws Exception {

        String sql = """
                SELECT id, nf, data,
                       motorista_id,
                       caminhao_id,
                       carreta_id
                FROM viagens
                WHERE data BETWEEN ? AND ?
                ORDER BY data DESC, id DESC
                """;

        List<Viagem> viagens = new ArrayList<>();

        try (PreparedStatement stmt =
                conexao.prepareStatement(sql)) {

            stmt.setString(1, inicio.toString());
            stmt.setString(2, fim.toString());

            try (ResultSet rs =
                    stmt.executeQuery()) {

                while (rs.next()) {

                    Viagem viagem =
                            new Viagem();

                    viagem.setId(
                            rs.getInt("id")
                    );

                    viagem.setNf(
                            rs.getInt("nf")
                    );

                    viagem.setData(
                            LocalDate.parse(
                                    rs.getString("data")
                            )
                    );

                    // ==============================
                    // MOTORISTA
                    // ==============================

                    MotoristaDAO motoristaDAO =
                            new MotoristaDAO();

                    Motorista motorista =
                            motoristaDAO.buscarPorId(
                                    conexao,
                                    rs.getInt("motorista_id")
                            );

                    viagem.setMotorista(motorista);


                    // ==============================
                    // CAMINHÃO
                    // ==============================

                    CaminhaoDAO caminhaoDAO =
                            new CaminhaoDAO();

                    Caminhao caminhao =
                            caminhaoDAO.buscarPorId(
                                    conexao,
                                    rs.getInt("caminhao_id")
                            );

                    viagem.setCaminhao(caminhao);


                    // ==============================
                    // CARRETA
                    // ==============================

                    CarretaDAO carretaDAO =
                            new CarretaDAO();

                    Carreta carreta =
                            carretaDAO.buscarPorId(
                                    conexao,
                                    rs.getInt("carreta_id")
                            );

                    viagem.setCarreta(carreta);


                    // ==============================
                    // ITENS
                    // ==============================

                    ItemViagemDAO itemViagemDAO =
                            new ItemViagemDAO();

                    List<ItemViagem> itens =
                            itemViagemDAO.buscarPorViagem(
                                    conexao,
                                    viagem.getId()
                            );

                    viagem.setItens(itens);


                    viagens.add(viagem);
                }
            }
        }

        return viagens;
    }
    public List<Viagem> buscarPorMotoristaEPeriodo(
            int motoristaId,
            java.time.LocalDate dataInicio,
            java.time.LocalDate dataFim)
            throws Exception {

        List<Viagem> viagens =
                new java.util.ArrayList<>();

        String sql = """
                SELECT id, nf, data,
                       motorista_id,
                       caminhao_id,
                       carreta_id
                FROM viagens
                WHERE motorista_id = ?
                AND date(data) BETWEEN date(?) AND date(?)
                ORDER BY date(data) DESC, id DESC
                """;

        try (Connection conexao =
                     ConexaoBanco.conectar();
             PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            stmt.setInt(1, motoristaId);

            stmt.setString(
                    2,
                    dataInicio.toString()
            );

            stmt.setString(
                    3,
                    dataFim.toString()
            );

            ResultSet rs =
                    stmt.executeQuery();

            while (rs.next()) {

                Viagem viagem =
                        new Viagem();

                viagem.setId(
                        rs.getInt("id")
                );

                viagem.setNf(
                        rs.getInt("nf")
                );

                viagem.setData(
                        java.time.LocalDate.parse(
                                rs.getString("data")
                        )
                );

                viagens.add(viagem);
            }
        }

        return viagens;
    }
    public List<Viagem> buscarPorPeriodo(
            java.time.LocalDate dataInicio,
            java.time.LocalDate dataFim)
            throws Exception {

        List<Viagem> viagens =
                new java.util.ArrayList<>();

        String sql = """
                SELECT id, nf, data,
                       motorista_id,
                       caminhao_id,
                       carreta_id
                FROM viagens
                WHERE date(data) BETWEEN date(?) AND date(?)
                ORDER BY date(data) DESC, id DESC
                """;

        try (Connection conexao =
                     ConexaoBanco.conectar();
             PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            stmt.setString(
                    1,
                    dataInicio.toString()
            );

            stmt.setString(
                    2,
                    dataFim.toString()
            );

            ResultSet rs =
                    stmt.executeQuery();

            while (rs.next()) {

                Viagem viagem =
                        new Viagem();

                viagem.setId(
                        rs.getInt("id")
                );

                viagem.setNf(
                        rs.getInt("nf")
                );

                viagem.setData(
                        java.time.LocalDate.parse(
                                rs.getString("data")
                        )
                );

                viagens.add(viagem);
            }
        }

        return viagens;
    }
}