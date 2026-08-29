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

import br.com.sergranbel.sistemacarregamentos.model.Motorista;
import br.com.sergranbel.sistemacarregamentos.service.MotoristaService;

public class TelaMotoristas extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField campoCpf;
    private JTextField campoNome;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private MotoristaService motoristaService;

    public TelaMotoristas(JFrame pai) {

        setTitle("Cadastro de Motoristas");
        setSize(750, 550);
        setLocationRelativeTo(pai);

        motoristaService = new MotoristaService();

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
                        "Dados do motorista"
                )
        );

        campos.add(new JLabel("CPF:"));

        campoCpf = new JTextField();
        campos.add(campoCpf);

        campos.add(new JLabel("Nome:"));

        campoNome = new JTextField();
        campos.add(campoNome);

        p.add(
                campos,
                BorderLayout.NORTH
        );

        // ==========================================
        // TABELA
        // ==========================================

        String[] colunas = {
                "CPF",
                "Nome",
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
                e -> cadastrarMotorista()
        );

        buscar.addActionListener(
                e -> buscarMotorista()
        );

        alterar.addActionListener(
                e -> alterarMotorista()
        );

        desativar.addActionListener(
                e -> desativarMotorista()
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

    private void cadastrarMotorista() {

        try {

            Motorista motorista =
                    new Motorista();

            motorista.setCpf(
                    campoCpf.getText().trim()
            );

            motorista.setNome(
                    campoNome.getText().trim()
            );

            motorista.setAtivo(true);

            motoristaService.cadastrar(
                    motorista
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Motorista cadastrado com sucesso!"
            );

            adicionarNaTabela(
                    motorista
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
                    "Erro ao cadastrar motorista:\n"
                            + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================================
    // BUSCAR
    // ==========================================

    private void buscarMotorista() {

        try {

            String cpf =
                    campoCpf.getText().trim();

            Motorista motorista =
                    motoristaService.buscarPorCpf(
                            cpf
                    );

            preencherCampos(
                    motorista
            );

            limparTabela();

            adicionarNaTabela(
                    motorista
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
                    "Erro ao buscar motorista:\n"
                            + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================================
    // ALTERAR
    // ==========================================

    private void alterarMotorista() {

        try {

            String cpf =
                    campoCpf.getText().trim();

            Motorista motorista =
                    motoristaService.buscarPorCpf(
                            cpf
                    );

            motorista.setNome(
                    campoNome.getText().trim()
            );

            motoristaService.alterar(
                    motorista
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Motorista alterado com sucesso!"
            );

            Motorista atualizado =
                    motoristaService.buscarPorCpf(
                            cpf
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
                    "Erro ao alterar motorista:\n"
                            + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================================
    // DESATIVAR
    // ==========================================

    private void desativarMotorista() {

        try {

            String cpf =
                    campoCpf.getText().trim();

            int resposta =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Deseja realmente desativar "
                                    + "o motorista?",
                            "Confirmar desativação",
                            JOptionPane.YES_NO_OPTION
                    );

            if (resposta != JOptionPane.YES_OPTION) {

                return;
            }

            motoristaService.desativar(
                    cpf
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Motorista desativado com sucesso!"
            );

            Motorista motorista =
                    motoristaService.buscarPorCpf(
                            cpf
                    );

            preencherCampos(
                    motorista
            );

            limparTabela();

            adicionarNaTabela(
                    motorista
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
                    "Erro ao desativar motorista:\n"
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
            Motorista motorista) {

        campoCpf.setText(
                motorista.getCpf()
        );

        campoNome.setText(
                motorista.getNome()
        );
    }

    // ==========================================
    // ADICIONAR NA TABELA
    // ==========================================

    private void adicionarNaTabela(
            Motorista motorista) {

        modeloTabela.addRow(
                new Object[] {
                        motorista.getCpf(),
                        motorista.getNome(),
                        motorista.isAtivo()
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

        campoCpf.setText("");
        campoNome.setText("");

        limparTabela();

        campoCpf.requestFocus();
    }
}