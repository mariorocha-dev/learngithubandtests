package br.com.sergranbel.sistemacarregamentos.view;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;

public class TelaDetalhesViagem extends JFrame {

    private static final long serialVersionUID = 1L;

	public TelaDetalhesViagem(JFrame pai) {
        setTitle("Detalhes da Viagem");
        setSize(950, 650);
        setLocationRelativeTo(pai);

        JPanel principal = new JPanel(new BorderLayout(10, 10));
        principal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(principal);

        JPanel dados = new JPanel(new GridLayout(4, 4, 10, 10));
        dados.setBorder(BorderFactory.createTitledBorder("Dados da viagem"));

        dados.add(new JLabel("NF:"));
        dados.add(new JLabel("-"));
        dados.add(new JLabel("Data:"));
        dados.add(new JLabel("-"));

        dados.add(new JLabel("Motorista:"));
        dados.add(new JLabel("-"));
        dados.add(new JLabel("CPF:"));
        dados.add(new JLabel("-"));

        dados.add(new JLabel("Caminhão:"));
        dados.add(new JLabel("-"));
        dados.add(new JLabel("Modelo:"));
        dados.add(new JLabel("-"));

        dados.add(new JLabel("Carreta:"));
        dados.add(new JLabel("-"));
        dados.add(new JLabel(""));
        dados.add(new JLabel(""));

        principal.add(dados, BorderLayout.NORTH);

        String[] colunas = {"Código", "Produto", "Quantidade", "HL", "HL Retornável"};
        JTable tabela = new JTable(new Object[0][5], colunas);
        principal.add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel rodape = new JPanel();
        rodape.add(new JLabel("HL Total: 0,00"));
        rodape.add(new JLabel("HL Retornável: 0,00"));

        JButton fechar = new JButton("FECHAR");
        fechar.addActionListener(e -> dispose());
        rodape.add(fechar);

        principal.add(rodape, BorderLayout.SOUTH);

        setVisible(true);
    }
}
