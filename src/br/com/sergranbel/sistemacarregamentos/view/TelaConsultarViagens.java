package br.com.sergranbel.sistemacarregamentos.view;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;

public class TelaConsultarViagens extends JFrame {

    private static final long serialVersionUID = 1L;

	public TelaConsultarViagens(JFrame pai) {
        setTitle("Consultar Viagens");
        setSize(1100, 650);
        setLocationRelativeTo(pai);

        JPanel principal = new JPanel(new BorderLayout(10, 10));
        principal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(principal);

        JPanel filtros = new JPanel(new GridLayout(3, 4, 10, 10));
        filtros.setBorder(BorderFactory.createTitledBorder("Filtros"));

        filtros.add(new JLabel("Data inicial:"));
        filtros.add(new JTextField());
        filtros.add(new JLabel("Data final:"));
        filtros.add(new JTextField());

        filtros.add(new JLabel("Motorista:"));
        filtros.add(new JComboBox<String>());
        filtros.add(new JLabel("Caminhão:"));
        filtros.add(new JComboBox<String>());

        filtros.add(new JLabel("Carreta:"));
        filtros.add(new JComboBox<String>());
        filtros.add(new JLabel("NF:"));
        filtros.add(new JTextField());

        principal.add(filtros, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(10, 10));

        String[] colunas = {"NF", "Data", "Motorista", "Caminhão", "Carreta", "HL Total", "HL Retornável"};
        JTable tabela = new JTable(new Object[0][7], colunas);
        centro.add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel resumo = new JPanel();
        resumo.add(new JLabel("Viagens: 0"));
        resumo.add(new JLabel("HL Total: 0,00"));
        resumo.add(new JLabel("HL Retornável: 0,00"));
        centro.add(resumo, BorderLayout.SOUTH);

        principal.add(centro, BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        JButton pesquisar = new JButton("PESQUISAR");
        JButton detalhes = new JButton("VER DETALHES");
        JButton fechar = new JButton("FECHAR");

        pesquisar.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "A pesquisa será ligada ao banco depois.")
        );
        detalhes.addActionListener(e -> new TelaDetalhesViagem(this));
        fechar.addActionListener(e -> dispose());

        botoes.add(pesquisar);
        botoes.add(detalhes);
        botoes.add(fechar);
        principal.add(botoes, BorderLayout.SOUTH);

        setVisible(true);
    }
}
