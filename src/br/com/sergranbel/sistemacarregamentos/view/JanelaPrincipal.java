package br.com.sergranbel.sistemacarregamentos.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;

public class JanelaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

	public JanelaPrincipal() {
        setTitle("Sistema de Carregamentos");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel principal = new JPanel(new BorderLayout(15, 15));
        principal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(principal);

        JLabel titulo = new JLabel("SISTEMA DE CARREGAMENTOS");
        titulo.setFont(titulo.getFont().deriveFont(24f));

        JLabel subtitulo = new JLabel("Controle de viagens e carregamentos");

        JPanel cabecalho = new JPanel(new BorderLayout());
        JPanel textos = new JPanel(new GridLayout(2, 1));
        textos.add(titulo);
        textos.add(subtitulo);
        cabecalho.add(textos, BorderLayout.WEST);

        JComboBox<String> periodo = new JComboBox<>(new String[] {
            "Agosto/2026", "Julho/2026", "Junho/2026"
        });
        JPanel periodoPanel = new JPanel();
        periodoPanel.add(new JLabel("Período:"));
        periodoPanel.add(periodo);
        cabecalho.add(periodoPanel, BorderLayout.EAST);

        principal.add(cabecalho, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(15, 15));

        JPanel indicadores = new JPanel(new GridLayout(1, 3, 15, 0));
        indicadores.add(indicador("VIAGENS", "0"));
        indicadores.add(indicador("HL TOTAL", "0,00"));
        indicadores.add(indicador("HL RETORNÁVEL", "0,00"));
        centro.add(indicadores, BorderLayout.NORTH);

        String[] colunas = {"NF", "Data", "Motorista", "Caminhão", "Carreta", "HL"};
        JTable tabela = new JTable(new Object[0][6], colunas);
        centro.add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        JButton novo = new JButton("NOVO CARREGAMENTO");
        JButton consultar = new JButton("CONSULTAR VIAGENS");
        JButton cadastros = new JButton("CADASTROS");

        novo.addActionListener(e -> new TelaNovoCarregamento(this));
        consultar.addActionListener(e -> new TelaConsultarViagens(this));
        cadastros.addActionListener(e -> new TelaCadastros(this));

        botoes.add(novo);
        botoes.add(consultar);
        botoes.add(cadastros);
        centro.add(botoes, BorderLayout.SOUTH);

        principal.add(centro, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel indicador(String titulo, String valor) {
        JPanel p = new JPanel(new GridLayout(2, 1));
        p.setBorder(BorderFactory.createEtchedBorder());
        JLabel t = new JLabel(titulo, SwingConstants.CENTER);
        JLabel v = new JLabel(valor, SwingConstants.CENTER);
        v.setFont(v.getFont().deriveFont(24f));
        p.add(t);
        p.add(v);
        return p;
    }
}
