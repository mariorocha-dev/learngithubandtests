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

import br.com.sergranbel.sistemacarregamentos.model.Caminhao;
import br.com.sergranbel.sistemacarregamentos.service.CaminhaoService;

public class TelaCaminhoes extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField campoPlaca;
    private JTextField campoModelo;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private CaminhaoService caminhaoService;

    public TelaCaminhoes(JFrame pai) {

        setTitle("Cadastro de Caminhões");
        setSize(750, 550);
        setLocationRelativeTo(pai);

        caminhaoService = new CaminhaoService();

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
                new JPanel(new GridLayout(1, 4, 10, 10));

        campos.setBorder(
                BorderFactory.createTitledBorder(
                        "Dados do caminhão"
                )
        );

        campos.add(new JLabel("Placa:"));

        campoPlaca = new JTextField();
        campos.add(campoPlaca);

        campos.add(new JLabel("Modelo:"));

        campoModelo = new JTextField();
        campos.add(campoModelo);

        p.add(
                campos,
                BorderLayout.NORTH
        );

        // ==========================================
        // TABELA
        // ==========================================

        String[] colunas = {
                "Placa",
                "Modelo",
                "Ativo"
        };

        modeloTabela =
                new DefaultTableModel(
                        new Object[0][3],
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
                e -> cadastrarCaminhao()
        );

        buscar.addActionListener(
                e -> buscarCaminhao()
        );

        alterar.addActionListener(
                e -> alterarCaminhao()
        );

        desativar.addActionListener(
                e -> desativarCaminhao()
        );

        fechar.addActionListener(
                e -> dispose()
        );

        // ==========================================
        // MOSTRAR
        // ==========================================

        setVisible(true);
    }

    // ==========================================
    // CADASTRAR
    // ==========================================

    private void cadastrarCaminhao() {

        try {

            Caminhao caminhao =
                    new Caminhao();

            caminhao.setPlaca(
                    campoPlaca.getText().trim()
            );

            caminhao.setModelo(
                    campoModelo.getText().trim()
            );

            caminhao.setAtivo(true);

            caminhaoService.cadastrar(
                    caminhao
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Caminhão cadastrado com sucesso!"
            );

            adicionarNaTabela(
                    caminhao
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
                    "Erro ao cadastrar caminhão:\n"
                            + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================================
    // BUSCAR
    // ==========================================

    private void buscarCaminhao() {

        try {

            String placa =
                    campoPlaca.getText().trim();

            Caminhao caminhao =
                    caminhaoService.buscarPorPlaca(
                            placa
                    );

            preencherCampos(
                    caminhao
            );

            limparTabela();

            adicionarNaTabela(
                    caminhao
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
                    "Erro ao buscar caminhão:\n"
                            + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================================
    // ALTERAR
    // ==========================================

    private void alterarCaminhao() {

        try {

            String placa =
                    campoPlaca.getText().trim();

            Caminhao caminhao =
                    caminhaoService.buscarPorPlaca(
                            placa
                    );

            caminhao.setModelo(
                    campoModelo.getText().trim()
            );

            caminhaoService.alterar(
                    caminhao
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Caminhão alterado com sucesso!"
            );

            Caminhao atualizado =
                    caminhaoService.buscarPorPlaca(
                            placa
                    );

            preencherCampos(
                    atualizado
            );

            limparTabela();

            adicionarNaTabela(
                    atualizado
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
                    "Erro ao alterar caminhão:\n"
                            + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================================
    // DESATIVAR
    // ==========================================

    private void desativarCaminhao() {

        try {

            String placa =
                    campoPlaca.getText().trim();

            int resposta =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Deseja realmente desativar "
                                    + "o caminhão?",
                            "Confirmar desativação",
                            JOptionPane.YES_NO_OPTION
                    );

            if (resposta != JOptionPane.YES_OPTION) {

                return;
            }

            caminhaoService.desativar(
                    placa
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Caminhão desativado com sucesso!"
            );

            Caminhao caminhao =
                    caminhaoService.buscarPorPlaca(
                            placa
                    );

            preencherCampos(
                    caminhao
            );

            limparTabela();

            adicionarNaTabela(
                    caminhao
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
                    "Erro ao desativar caminhão:\n"
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
            Caminhao caminhao) {

        campoPlaca.setText(
                caminhao.getPlaca()
        );

        campoModelo.setText(
                caminhao.getModelo()
        );
    }

    // ==========================================
    // ADICIONAR NA TABELA
    // ==========================================

    private void adicionarNaTabela(
            Caminhao caminhao) {

        modeloTabela.addRow(
                new Object[] {
                        caminhao.getPlaca(),
                        caminhao.getModelo(),
                        caminhao.isAtivo()
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
        campoModelo.setText("");

        limparTabela();

        campoPlaca.requestFocus();
    }
}