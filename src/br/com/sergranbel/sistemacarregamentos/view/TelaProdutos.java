package br.com.sergranbel.sistemacarregamentos.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.com.sergranbel.sistemacarregamentos.model.Produto;
import br.com.sergranbel.sistemacarregamentos.service.ProdutoService;

public class TelaProdutos extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField campoCodigo;
    private JTextField campoNome;
    private JTextField campoMarca;
    private JTextField campoHl;

    private JComboBox<String> campoRetornavel;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private ProdutoService produtoService;

    public TelaProdutos(JFrame pai) {

        setTitle("Cadastro de Produtos");
        setSize(850, 600);
        setLocationRelativeTo(pai);

        produtoService = new ProdutoService();

        JPanel p = new JPanel(new BorderLayout(10, 10));

        p.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );

        setContentPane(p);

        // ==========================================
        // CAMPOS
        // ==========================================

        JPanel campos =
                new JPanel(new GridLayout(3, 4, 10, 10));

        campos.setBorder(
                BorderFactory.createTitledBorder(
                        "Dados do produto"
                )
        );

        campos.add(new JLabel("Código:"));

        campoCodigo = new JTextField();
        campos.add(campoCodigo);

        campos.add(new JLabel("Nome:"));

        campoNome = new JTextField();
        campos.add(campoNome);

        campos.add(new JLabel("Marca:"));

        campoMarca = new JTextField();
        campos.add(campoMarca);

        campos.add(new JLabel("HL por pacote:"));

        campoHl = new JTextField();
        campos.add(campoHl);

        campos.add(new JLabel("Retornável:"));

        campoRetornavel =
                new JComboBox<>(
                        new String[]{"Sim", "Não"}
                );

        campos.add(campoRetornavel);

        campos.add(new JLabel(""));
        campos.add(new JLabel(""));

        p.add(campos, BorderLayout.NORTH);

        // ==========================================
        // TABELA
        // ==========================================

        String[] colunas = {
                "Código",
                "Nome",
                "Marca",
                "HL/Pacote",
                "Retornável",
                "Ativo"
        };

        modeloTabela =
                new DefaultTableModel(
                        new Object[0][6],
                        colunas
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        tabela = new JTable(modeloTabela);

        p.add(
                new JScrollPane(tabela),
                BorderLayout.CENTER
        );

        // ==========================================
        // BOTÕES
        // ==========================================

        JPanel botoes = new JPanel();

        JButton novo =
                new JButton("NOVO");

        JButton salvar =
                new JButton("SALVAR");

        JButton buscar =
                new JButton("BUSCAR");

        JButton desativar =
                new JButton("DESATIVAR");

        JButton fechar =
                new JButton("FECHAR");
        
        JButton alterar =
                new JButton("ALTERAR");

        botoes.add(novo);
        botoes.add(salvar);
        botoes.add(buscar);
        botoes.add(desativar);
        botoes.add(fechar);
        botoes.add(alterar);

        p.add(
                botoes,
                BorderLayout.SOUTH
        );

        // ==========================================
        // EVENTOS
        // ==========================================

        novo.addActionListener(
                e -> limparCampos()
        );

        salvar.addActionListener(
                e -> cadastrarProduto()
        );

        buscar.addActionListener(
                e -> buscarProduto()
        );

        desativar.addActionListener(
                e -> desativarProduto()
        );

        fechar.addActionListener(
                e -> dispose()
        );
        alterar.addActionListener(
                e -> alterarProduto()
        );

        // ==========================================
        // MOSTRAR JANELA
        // ==========================================

        setVisible(true);
    }

    // ==========================================
    // CADASTRAR PRODUTO
    // ==========================================

    private void cadastrarProduto() {

        try {

            Produto produto =
                    new Produto();

            produto.setCodigo(
                    Integer.parseInt(
                            campoCodigo.getText().trim()
                    )
            );

            produto.setNome(
                    campoNome.getText().trim()
            );

            produto.setMarca(
                    campoMarca.getText().trim()
            );

            produto.setHlPorPacote(
                    Double.parseDouble(
                            campoHl.getText()
                                    .trim()
                                    .replace(",", ".")
                    )
            );

            produto.setRetornavel(
                    campoRetornavel
                            .getSelectedItem()
                            .equals("Sim")
            );

            produto.setAtivo(true);

            produtoService.cadastrar(produto);

            JOptionPane.showMessageDialog(
                    this,
                    "Produto cadastrado com sucesso!"
            );

            adicionarNaTabela(produto);

            limparCampos();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Código e HL devem ser números válidos.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao cadastrar produto:\n"
                            + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================================
    // BUSCAR PRODUTO
    // ==========================================

    private void buscarProduto() {

        try {

            int codigo =
                    Integer.parseInt(
                            campoCodigo.getText().trim()
                    );

            Produto produto =
                    produtoService.buscarPorCodigo(
                            codigo
                    );

            preencherCampos(produto);

            limparTabela();

            adicionarNaTabela(produto);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Informe um código válido.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao buscar produto:\n"
                            + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================================
    // DESATIVAR PRODUTO
    // ==========================================

    private void desativarProduto() {

        try {

            int codigo =
                    Integer.parseInt(
                            campoCodigo.getText().trim()
                    );

            int resposta =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Deseja realmente desativar "
                                    + "o produto " + codigo + "?",
                            "Confirmar desativação",
                            JOptionPane.YES_NO_OPTION
                    );

            if (resposta != JOptionPane.YES_OPTION) {
                return;
            }

            produtoService.desativar(codigo);

            JOptionPane.showMessageDialog(
                    this,
                    "Produto desativado com sucesso!"
            );

            Produto produto =
                    produtoService.buscarPorCodigo(
                            codigo
                    );

            preencherCampos(produto);

            limparTabela();

            adicionarNaTabela(produto);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Informe um código válido.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao desativar produto:\n"
                            + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================================
    // PREENCHER CAMPOS
    // ==========================================

    private void preencherCampos(
            Produto produto) {

        campoCodigo.setText(
                String.valueOf(
                        produto.getCodigo()
                )
        );

        campoNome.setText(
                produto.getNome()
        );

        campoMarca.setText(
                produto.getMarca()
        );

        campoHl.setText(
                String.valueOf(
                        produto.getHlPorPacote()
                )
        );

        campoRetornavel.setSelectedItem(
                produto.isRetornavel()
                        ? "Sim"
                        : "Não"
        );
    }

    // ==========================================
    // ADICIONAR NA TABELA
    // ==========================================

    private void adicionarNaTabela(
            Produto produto) {

        modeloTabela.addRow(
                new Object[] {
                        produto.getCodigo(),
                        produto.getNome(),
                        produto.getMarca(),
                        produto.getHlPorPacote(),
                        produto.isRetornavel()
                                ? "Sim"
                                : "Não",
                        produto.isAtivo()
                                ? "Sim"
                                : "Não"
                }
        );
    }

    // ==========================================
    // LIMPAR TABELA
    // ==========================================

    private void limparTabela() {

        modeloTabela.setRowCount(0);
    }

    // ==========================================
    // LIMPAR CAMPOS
    // ==========================================

    private void limparCampos() {

        campoCodigo.setText("");
        campoNome.setText("");
        campoMarca.setText("");
        campoHl.setText("");

        campoRetornavel.setSelectedIndex(0);

        limparTabela();

        campoCodigo.requestFocus();
    }
    private void alterarProduto() {

        try {

            int codigo =
                    Integer.parseInt(
                            campoCodigo.getText().trim()
                    );

            Produto produto =
                    produtoService.buscarPorCodigo(codigo);

            produto.setNome(
                    campoNome.getText().trim()
            );

            produto.setMarca(
                    campoMarca.getText().trim()
            );

            produto.setHlPorPacote(
                    Double.parseDouble(
                            campoHl.getText()
                                    .trim()
                                    .replace(",", ".")
                    )
            );

            produto.setRetornavel(
                    campoRetornavel
                            .getSelectedItem()
                            .equals("Sim")
            );

            produtoService.alterar(produto);

            JOptionPane.showMessageDialog(
                    this,
                    "Produto alterado com sucesso!"
            );

            Produto atualizado =
                    produtoService.buscarPorCodigo(
                            codigo
                    );

            preencherCampos(atualizado);

            limparTabela();

            adicionarNaTabela(atualizado);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Código e HL devem ser números válidos.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao alterar produto:\n"
                            + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}