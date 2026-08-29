package br.com.sergranbel.sistemacarregamentos.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.com.sergranbel.sistemacarregamentos.model.Carreta;
import br.com.sergranbel.sistemacarregamentos.service.CarretaService;

public class TelaCarretas extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField campoPlaca;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private CarretaService carretaService;

    public TelaCarretas(JFrame pai) {

        setTitle("Cadastro de Carretas");
        setSize(750, 550);
        setLocationRelativeTo(pai);

        carretaService = new CarretaService();

        JPanel p =
                new JPanel(new BorderLayout(10, 10));

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
                new JPanel(new GridLayout(1, 2, 10, 10));

        campos.setBorder(
                BorderFactory.createTitledBorder(
                        "Dados da carreta"
                )
        );

        campos.add(new JLabel("Placa:"));

        campoPlaca = new JTextField();

        campos.add(campoPlaca);

        p.add(
                campos,
                BorderLayout.NORTH
        );

        // ==========================================
        // TABELA
        // ==========================================

        String[] colunas = {
                "Placa",
                "Ativo"
        };

        modeloTabela =
                new DefaultTableModel(
                        new Object[0][2],
                        colunas
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        tabela =
                new JTable(modeloTabela);

        p.add(
                new JScrollPane(tabela),
                BorderLayout.CENTER
        );

        // ==========================================
        // BOTÕES
        // ==========================================

        JPanel botoes =
                new JPanel();

        JButton novo =
                new JButton("NOVO");

        JButton salvar =
                new JButton("SALVAR");

        JButton buscar =
                new JButton("BUSCAR");

        JButton alterar =
                new JButton("ALTERAR");

        JButton desativar =
                new JButton("DESATIVAR");

        JButton fechar =
                new JButton("FECHAR");

        botoes.add(novo);
        botoes.add(salvar);
        botoes.add(buscar);
        botoes.add(alterar);
        botoes.add(desativar);
        botoes.add(fechar);

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
                e -> cadastrarCarreta()
        );

        buscar.addActionListener(
                e -> buscarCarreta()
        );

        alterar.addActionListener(
                e -> alterarCarreta()
        );

        desativar.addActionListener(
                e -> desativarCarreta()
        );

        fechar.addActionListener(
                e -> dispose()
        );

        setVisible(true);
    }

    // ==========================================
    // CADASTRAR
    // ==========================================

    private void cadastrarCarreta() {

        try {

            Carreta carreta =
                    new Carreta();

            carreta.setPlaca(
                    campoPlaca.getText().trim()
            );

            carreta.setAtivo(true);

            carretaService.cadastrar(
                    carreta
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Carreta cadastrada com sucesso!"
            );

            adicionarNaTabela(
                    carreta
            );

            limparCampos();

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
                    "Erro ao cadastrar carreta:\n"
                            + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================================
    // BUSCAR
    // ==========================================

    private void buscarCarreta() {

        try {

            String placa =
                    campoPlaca.getText().trim();

            Carreta carreta =
                    carretaService.buscarPorPlaca(
                            placa
                    );

            preencherCampos(
                    carreta
            );

            limparTabela();

            adicionarNaTabela(
                    carreta
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
                    "Erro ao buscar carreta:\n"
                            + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================================
    // ALTERAR
    // ==========================================

    private void alterarCarreta() {

        try {

            String placa =
                    campoPlaca.getText().trim();

            Carreta carreta =
                    carretaService.buscarPorPlaca(
                            placa
                    );

            carretaService.alterar(
                    carreta
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Carreta alterada com sucesso!"
            );

            Carreta atualizada =
                    carretaService.buscarPorPlaca(
                            placa
                    );

            preencherCampos(
                    atualizada
            );

            limparTabela();

            adicionarNaTabela(
                    atualizada
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
                    "Erro ao alterar carreta:\n"
                            + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================================
    // DESATIVAR
    // ==========================================

    private void desativarCarreta() {

        try {

            String placa =
                    campoPlaca.getText().trim();

            int resposta =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Deseja realmente desativar "
                                    + "a carreta?",
                            "Confirmar desativação",
                            JOptionPane.YES_NO_OPTION
                    );

            if (resposta != JOptionPane.YES_OPTION) {

                return;
            }

            carretaService.desativar(
                    placa
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Carreta desativada com sucesso!"
            );

            Carreta carreta =
                    carretaService.buscarPorPlaca(
                            placa
                    );

            preencherCampos(
                    carreta
            );

            limparTabela();

            adicionarNaTabela(
                    carreta
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
                    "Erro ao desativar carreta:\n"
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
            Carreta carreta) {

        campoPlaca.setText(
                carreta.getPlaca()
        );
    }

    // ==========================================
    // ADICIONAR NA TABELA
    // ==========================================

    private void adicionarNaTabela(
            Carreta carreta) {

        modeloTabela.addRow(
                new Object[] {
                        carreta.getPlaca(),
                        carreta.isAtivo()
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

        campoPlaca.setText("");

        limparTabela();

        campoPlaca.requestFocus();
    }
}