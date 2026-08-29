package br.com.sergranbel.sistemacarregamentos.view;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.sergranbel.sistemacarregamentos.dao.ProdutoDAO;
import br.com.sergranbel.sistemacarregamentos.model.Produto;

public class TelaAdicionarProduto extends JFrame {

    private static final long serialVersionUID = 1L;

    private JComboBox<Produto> comboProduto;
    private JTextField campoQuantidade;

    private ProdutoDAO produtoDAO;

    public TelaAdicionarProduto(JFrame pai) {

        setTitle("Adicionar Produto");
        setSize(500, 300);
        setLocationRelativeTo(pai);

        produtoDAO = new ProdutoDAO();

        JPanel principal =
                new JPanel(new GridLayout(4, 2, 10, 10));

        principal.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        // ==========================================
        // PRODUTO
        // ==========================================

        principal.add(
                new JLabel("Produto:")
        );

        comboProduto =
                new JComboBox<>();

        principal.add(comboProduto);


        // ==========================================
        // QUANTIDADE
        // ==========================================

        principal.add(
                new JLabel("Quantidade:")
        );

        campoQuantidade =
                new JTextField();

        principal.add(campoQuantidade);


        // ==========================================
        // HL
        // ==========================================

        principal.add(
                new JLabel("HL por pacote:")
        );

        JLabel labelHL =
                new JLabel("0,00");

        principal.add(labelHL);


        // ==========================================
        // BOTÕES
        // ==========================================

        JButton adicionar =
                new JButton("ADICIONAR");

        JButton cancelar =
                new JButton("CANCELAR");

        principal.add(adicionar);
        principal.add(cancelar);


        // ==========================================
        // CARREGAR PRODUTOS
        // ==========================================

        try {

            List<Produto> produtos =
                    produtoDAO.listarAtivos();

            for (Produto produto : produtos) {

                comboProduto.addItem(produto);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar produtos:\n"
                    + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }


        // ==========================================
        // ALTERAÇÃO DO PRODUTO
        // ==========================================

        comboProduto.addActionListener(e -> {

            Produto produto =
                    (Produto)
                    comboProduto.getSelectedItem();

            if (produto != null) {

                labelHL.setText(
                        String.valueOf(
                                produto.getHlPorPacote()
                        )
                );
            }
        });


        // ==========================================
        // ADICIONAR
        // ==========================================

        adicionar.addActionListener(e -> {

            try {

                Produto produto =
                        (Produto)
                        comboProduto.getSelectedItem();

                if (produto == null) {

                    throw new IllegalArgumentException(
                            "Selecione um produto."
                    );
                }

                int quantidade =
                        Integer.parseInt(
                                campoQuantidade
                                        .getText()
                                        .trim()
                        );

                if (quantidade <= 0) {

                    throw new IllegalArgumentException(
                            "A quantidade deve ser maior que zero."
                    );
                }

                double hl =
                        quantidade *
                        produto.getHlPorPacote();

                double hlRetornavel = 0;

                if (produto.isRetornavel()) {

                    hlRetornavel = hl;
                }

                JOptionPane.showMessageDialog(
                        this,
                        "Produto: "
                        + produto.getNome()
                        + "\nQuantidade: "
                        + quantidade
                        + "\nHL: "
                        + hl
                        + "\nHL Retornável: "
                        + hlRetornavel,
                        "Produto",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Digite uma quantidade válida.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );

            } catch (IllegalArgumentException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });


        // ==========================================
        // CANCELAR
        // ==========================================

        cancelar.addActionListener(
                e -> dispose()
        );


        setContentPane(principal);
        setVisible(true);
    }
}