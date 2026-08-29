package br.com.sergranbel.sistemacarregamentos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.sergranbel.sistemacarregamentos.database.ConexaoBanco;
import br.com.sergranbel.sistemacarregamentos.model.Produto;

public class ProdutoDAO {

	public void salvar(Produto produto) throws Exception {

	    String sql = """
	            INSERT INTO produtos
	            (codigo, nome, marca, hl_por_pacote, retornavel, ativo)
	            VALUES (?, ?, ?, ?, ?, ?)
	            """;

	    try (Connection conexao = ConexaoBanco.conectar();
	         PreparedStatement stmt = conexao.prepareStatement(sql)) {

	        stmt.setInt(1, produto.getCodigo());
	        stmt.setString(2, produto.getNome());
	        stmt.setString(3, produto.getMarca());
	        stmt.setDouble(4, produto.getHlPorPacote());
	        stmt.setBoolean(5, produto.isRetornavel());
	        stmt.setBoolean(6, produto.isAtivo());

	        stmt.executeUpdate();
	    }
	}
    public Produto buscarPorCodigo(int codigo) {

        String sql = """
                SELECT id, codigo, nome, marca,
                       hl_por_pacote, retornavel, ativo
                FROM produtos
                WHERE codigo = ?
                """;

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            var resultado = stmt.executeQuery();

            if (resultado.next()) {

                Produto produto = new Produto();

                produto.setId(resultado.getInt("id"));
                produto.setCodigo(resultado.getInt("codigo"));
                produto.setNome(resultado.getString("nome"));
                produto.setMarca(resultado.getString("marca"));
                produto.setHlPorPacote(resultado.getDouble("hl_por_pacote"));
                produto.setRetornavel(resultado.getBoolean("retornavel"));
                produto.setAtivo(resultado.getBoolean("ativo"));

                return produto;
            }

        } catch (Exception e) {

            System.out.println("Erro ao buscar produto.");
            e.printStackTrace();
        }

        return null;
    }
    public void alterar(Produto produto) throws Exception {

        String sql = """
                UPDATE produtos
                SET nome = ?,
                    marca = ?,
                    hl_por_pacote = ?,
                    retornavel = ?,
                    ativo = ?
                WHERE id = ?
                """;

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getMarca());
            stmt.setDouble(3, produto.getHlPorPacote());
            stmt.setBoolean(4, produto.isRetornavel());
            stmt.setBoolean(5, produto.isAtivo());
            stmt.setInt(6, produto.getId());

            stmt.executeUpdate();
        }
    }
    public Produto buscarPorId(int id) throws Exception {

        String sql = """
                SELECT id,
                       codigo,
                       nome,
                       marca,
                       hl_por_pacote,
                       retornavel,
                       ativo
                FROM produtos
                WHERE id = ?
                """;

        try (Connection conexao =
                ConexaoBanco.conectar();
             PreparedStatement stmt =
                conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs =
                    stmt.executeQuery()) {

                if (rs.next()) {

                    Produto produto =
                            new Produto();

                    produto.setId(
                            rs.getInt("id")
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

                    return produto;
                }
            }
        }

        return null;
    }
    public void desativar(int id) throws Exception {

        String sql = """
                UPDATE produtos
                SET ativo = 0
                WHERE id = ?
                """;

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }
    public java.util.List<Produto> listarAtivos()
            throws Exception {

        String sql = """
                SELECT id,
                       codigo,
                       nome,
                       marca,
                       hl_por_pacote,
                       retornavel,
                       ativo
                FROM produtos
                WHERE ativo = 1
                ORDER BY nome
                """;

        java.util.List<Produto> produtos =
                new java.util.ArrayList<>();

        try (Connection conexao =
                     ConexaoBanco.conectar();
             PreparedStatement stmt =
                     conexao.prepareStatement(sql);
             ResultSet resultado =
                     stmt.executeQuery()) {

            while (resultado.next()) {

                Produto produto =
                        new Produto();

                produto.setId(
                        resultado.getInt("id")
                );

                produto.setCodigo(
                        resultado.getInt("codigo")
                );

                produto.setNome(
                        resultado.getString("nome")
                );

                produto.setMarca(
                        resultado.getString("marca")
                );

                produto.setHlPorPacote(
                        resultado.getDouble("hl_por_pacote")
                );

                produto.setRetornavel(
                        resultado.getBoolean("retornavel")
                );

                produto.setAtivo(
                        resultado.getBoolean("ativo")
                );

                produtos.add(produto);
            }
        }

        return produtos;
    }
    
}