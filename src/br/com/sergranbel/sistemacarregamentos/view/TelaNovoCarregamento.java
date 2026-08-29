package br.com.sergranbel.sistemacarregamentos.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

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
import javax.swing.SwingConstants;

import br.com.sergranbel.sistemacarregamentos.dao.*;
import br.com.sergranbel.sistemacarregamentos.model.Caminhao;
import br.com.sergranbel.sistemacarregamentos.model.Carreta;
import br.com.sergranbel.sistemacarregamentos.model.Motorista;

public class TelaNovoCarregamento extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField campoNF;
    private JTextField campoData;

    private JComboBox<Motorista> comboMotorista;
    private JComboBox<Caminhao> comboCaminhao;
    private JComboBox<Carreta> comboCarreta;
    
    private MotoristaDAO motoristaDAO;
    private CaminhaoDAO caminhaoDAO;
    private CarretaDAO carretaDAO;

    private JTable tabelaProdutos;

    public TelaNovoCarregamento(JFrame pai) {

        setTitle("Novo Carregamento");
        setSize(1000, 650);
        setLocationRelativeTo(pai);

        JPanel principal =
                new JPanel(new BorderLayout(10, 10));

        principal.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );

        setContentPane(principal);

        
        motoristaDAO = new MotoristaDAO();
        caminhaoDAO = new CaminhaoDAO();
        carretaDAO = new CarretaDAO();

        

        // ==========================================
        // CABEÇALHO
        // ==========================================

        JPanel cabecalho =
                new JPanel(new GridLayout(2, 4, 10, 10));

        cabecalho.setBorder(
                BorderFactory.createTitledBorder(
                        "Dados do carregamento"
                )
        );

        cabecalho.add(new JLabel("NF:"));

        campoNF =
                new JTextField();

        cabecalho.add(campoNF);

        cabecalho.add(new JLabel("Data:"));

        campoData =
                new JTextField();

        campoData.setText(
                java.time.LocalDate.now().toString()
        );

        cabecalho.add(campoData);

        cabecalho.add(
                new JLabel("Motorista:")
        );

        comboMotorista =
                new JComboBox<>();

        cabecalho.add(comboMotorista);

        cabecalho.add(
                new JLabel("Caminhão:")
        );

        comboCaminhao =
                new JComboBox<>();

        cabecalho.add(comboCaminhao);

        principal.add(
                cabecalho,
                BorderLayout.NORTH
        );

        // ==========================================
        // CARRETA
        // ==========================================

        JPanel painelCarreta =
                new JPanel(
                        new GridLayout(1, 2, 10, 10)
                );

        painelCarreta.setBorder(
                BorderFactory.createTitledBorder(
                        "Carreta"
                )
        );

        painelCarreta.add(
                new JLabel("Carreta:")
        );

        comboCarreta =
                new JComboBox<>();

        painelCarreta.add(
                comboCarreta
        );
        carregarDados();
        // ==========================================
        // PRODUTOS
        // ==========================================

        JPanel centro =
                new JPanel(
                        new BorderLayout(10, 10)
                );

        centro.add(
                painelCarreta,
                BorderLayout.NORTH
        );

        String[] colunas = {
                "Produto",
                "Código",
                "Quantidade",
                "HL",
                "HL Retornável"
        };

        tabelaProdutos =
                new JTable(
                        new Object[0][5],
                        colunas
                );

        centro.add(
                new JScrollPane(
                        tabelaProdutos
                ),
                BorderLayout.CENTER
        );

        principal.add(
                centro,
                BorderLayout.CENTER
        );

        // ==========================================
        // RODAPÉ
        // ==========================================

        JPanel rodape =
                new JPanel(
                        new BorderLayout(10, 10)
                );

        JPanel resumo =
                new JPanel(
                        new GridLayout(1, 3, 10, 10)
                );

        resumo.add(
                indicador(
                        "QUANTIDADE TOTAL",
                        "0"
                )
        );

        resumo.add(
                indicador(
                        "HL TOTAL",
                        "0,00"
                )
        );

        resumo.add(
                indicador(
                        "HL RETORNÁVEL",
                        "0,00"
                )
        );

        rodape.add(
                resumo,
                BorderLayout.CENTER
        );

        JPanel botoes =
                new JPanel();

        JButton adicionar =
                new JButton(
                        "ADICIONAR PRODUTO"
                );

        JButton remover =
                new JButton(
                        "REMOVER PRODUTO"
                );

        JButton salvar =
                new JButton(
                        "SALVAR CARREGAMENTO"
                );

        JButton cancelar =
                new JButton(
                        "CANCELAR"
                );

        botoes.add(adicionar);
        botoes.add(remover);
        botoes.add(salvar);
        botoes.add(cancelar);

        rodape.add(
                botoes,
                BorderLayout.SOUTH
        );

        principal.add(
                rodape,
                BorderLayout.SOUTH
        );

        // ==========================================
        // EVENTOS
        // ==========================================

        adicionar.addActionListener(
                e -> adicionarProduto()
        );

        remover.addActionListener(
                e -> removerProduto()
        );

        salvar.addActionListener(
                e -> salvarCarregamento()
        );

        cancelar.addActionListener(
                e -> dispose()
        );

        setVisible(true);
    }

    // ==========================================
    // INDICADOR
    // ==========================================

    private JPanel indicador(
            String titulo,
            String valor) {

        JPanel painel =
                new JPanel(
                        new GridLayout(2, 1)
                );

        painel.setBorder(
                BorderFactory.createEtchedBorder()
        );

        JLabel labelTitulo =
                new JLabel(
                        titulo,
                        SwingConstants.CENTER
                );

        JLabel labelValor =
                new JLabel(
                        valor,
                        SwingConstants.CENTER
                );

        labelValor.setFont(
                labelValor.getFont()
                        .deriveFont(20f)
        );

        painel.add(labelTitulo);
        painel.add(labelValor);

        return painel;
    }

    // ==========================================
    // ADICIONAR PRODUTO
    // ==========================================

    private void adicionarProduto() {

        new TelaAdicionarProduto(this);
    }

    // ==========================================
    // REMOVER PRODUTO
    // ==========================================

    private void removerProduto() {

        System.out.println(
                "Remover produto..."
        );
    }

    // ==========================================
    // SALVAR
    // ==========================================

    private void salvarCarregamento() {

        System.out.println(
                "Salvar carregamento..."
        );
    }
    private void carregarDados() {

        try {

            List<Motorista> motoristas =
                    motoristaDAO.listarAtivos();

            for (Motorista motorista : motoristas) {

            	comboMotorista.addItem(motorista);
            }


            List<Caminhao> caminhoes =
                    caminhaoDAO.listarAtivos();

            for (Caminhao caminhao : caminhoes) {

            	comboCaminhao.addItem(caminhao);
            }


            List<Carreta> carretas =
                    carretaDAO.listarAtivos();

            for (Carreta carreta : carretas) {

            	comboCarreta.addItem(carreta);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar os dados:\n"
                    + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }
}