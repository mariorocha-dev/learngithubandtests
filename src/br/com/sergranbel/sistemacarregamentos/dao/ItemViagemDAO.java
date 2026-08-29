package br.com.sergranbel.sistemacarregamentos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.sergranbel.sistemacarregamentos.database.ConexaoBanco;
import br.com.sergranbel.sistemacarregamentos.model.ItemViagem;
import br.com.sergranbel.sistemacarregamentos.model.Produto;

public class ItemViagemDAO {

    public void salvar(
            Connection conexao,
            int viagemId,
            ItemViagem item) throws Exception {

        String sql = """
                INSERT INTO itens_viagem
                (viagem_id, produto_id, quantidade, hl, hl_retornavel)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            stmt.setInt(1, viagemId);
            stmt.setInt(2, item.getProduto().getId());
            stmt.setInt(3, item.getQuantidade());
            stmt.setDouble(4, item.getHl());
            stmt.setDouble(5, item.getHlRetornavel());

            stmt.executeUpdate();
        }
    }
    public boolean produtoFoiUtilizado(int produtoId)
            throws Exception {

        String sql = """
                SELECT COUNT(*)
                FROM itens_viagem
                WHERE produto_id = ?
                """;

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            stmt.setInt(1, produtoId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }

        return false;
    }

    public List<ItemViagem> buscarPorViagem(
            Connection conexao,
            int viagemId) throws Exception {

        String sql = """
                SELECT
                    iv.id,
                    iv.quantidade,
                    iv.hl,
                    iv.hl_retornavel,
                    p.id AS produto_id,
                    p.codigo,
                    p.nome,
                    p.marca,
                    p.hl_por_pacote,
                    p.retornavel,
                    p.ativo
                FROM itens_viagem iv
                INNER JOIN produtos p
                    ON p.id = iv.produto_id
                WHERE iv.viagem_id = ?
                """;

        List<ItemViagem> itens = new ArrayList<>();

        try (PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            stmt.setInt(1, viagemId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Produto produto = new Produto();

                produto.setId(
                        rs.getInt("produto_id")
                );

                produto.setCodigo(
                        rs.getInt("codigo")
                );

                produto.setNome(
                        rs.getString("nome")
                );

                produto.setMarca(
                        rs.getString("marca")
                );

                produto.setHlPorPacote(
                        rs.getDouble("hl_por_pacote")
                );

                produto.setRetornavel(
                        rs.getBoolean("retornavel")
                );

                produto.setAtivo(
                        rs.getBoolean("ativo")
                );

                ItemViagem item = new ItemViagem();

                item.setId(
                        rs.getInt("id")
                );

                item.setProduto(produto);

                item.setQuantidade(
                        rs.getInt("quantidade")
                );

                item.setHl(
                        rs.getDouble("hl")
                );

                item.setHlRetornavel(
                        rs.getDouble("hl_retornavel")
                );

                itens.add(item);
            }
        }

        return itens;
    }
    public List<ItemViagem> buscarPorProduto(
            Connection conexao,
            int produtoId) throws Exception {

        String sql = """
                SELECT iv.id,
                       iv.viagem_id,
                       iv.produto_id,
                       iv.quantidade,
                       iv.hl,
                       iv.hl_retornavel,
                       v.nf,
                       v.data
                FROM itens_viagem iv
                INNER JOIN viagens v
                    ON v.id = iv.viagem_id
                WHERE iv.produto_id = ?
                ORDER BY v.data DESC, v.id DESC
                """;

        List<ItemViagem> itens = new ArrayList<>();

        try (PreparedStatement stmt =
                conexao.prepareStatement(sql)) {

            stmt.setInt(1, produtoId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    ItemViagem item = new ItemViagem();

                    item.setId(
                            rs.getInt("id")
                    );

                    item.setQuantidade(
                            rs.getInt("quantidade")
                    );

                    item.setHl(
                            rs.getDouble("hl")
                    );

                    item.setHlRetornavel(
                            rs.getDouble("hl_retornavel")
                    );

                    // NOVO
                    item.setNf(
                            rs.getInt("nf")
                    );

                    // NOVO
                    String data =
                            rs.getString("data");

                    if (data != null) {

                        item.setData(
                                LocalDate.parse(data)
                        );
                    }

                    itens.add(item);
                }
            }
        }

        return itens;
    }
    public List<ItemViagem> buscarPorProdutoEPeriodo(
            Connection conexao,
            int produtoId,
            LocalDate dataInicio,
            LocalDate dataFim) throws Exception {

        String sql = """
                SELECT iv.id,
                       iv.viagem_id,
                       iv.produto_id,
                       iv.quantidade,
                       iv.hl,
                       iv.hl_retornavel,
                       v.nf,
                       v.data
                FROM itens_viagem iv
                INNER JOIN viagens v
                    ON v.id = iv.viagem_id
                WHERE iv.produto_id = ?
                  AND v.data BETWEEN ? AND ?
                ORDER BY v.data DESC, v.id DESC
                """;

        List<ItemViagem> itens = new ArrayList<>();

        try (PreparedStatement stmt =
                conexao.prepareStatement(sql)) {

            stmt.setInt(1, produtoId);
            stmt.setString(2, dataInicio.toString());
            stmt.setString(3, dataFim.toString());

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    ItemViagem item =
                            new ItemViagem();

                    item.setId(
                            rs.getInt("id")
                    );

                    item.setQuantidade(
                            rs.getInt("quantidade")
                    );

                    item.setHl(
                            rs.getDouble("hl")
                    );

                    item.setHlRetornavel(
                            rs.getDouble("hl_retornavel")
                    );

                    item.setNf(
                            rs.getInt("nf")
                    );

                    item.setData(
                            LocalDate.parse(
                                    rs.getString("data")
                            )
                    );

                    itens.add(item);
                }
            }
        }

        return itens;
    }
}