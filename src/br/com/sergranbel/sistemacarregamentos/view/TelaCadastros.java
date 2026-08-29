package br.com.sergranbel.sistemacarregamentos.view;

import java.awt.GridLayout;
import javax.swing.*;

public class TelaCadastros extends JFrame {

    private static final long serialVersionUID = 1L;

	public TelaCadastros(JFrame pai) {
        setTitle("Cadastros");
        setSize(450, 400);
        setLocationRelativeTo(pai);

        JPanel p = new JPanel(new GridLayout(5, 1, 10, 10));
        p.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton produtos = new JButton("PRODUTOS");
        JButton motoristas = new JButton("MOTORISTAS");
        JButton caminhoes = new JButton("CAMINHÕES");
        JButton carretas = new JButton("CARRETAS");
        JButton fechar = new JButton("FECHAR");

        produtos.addActionListener(e -> new TelaProdutos(this));
        motoristas.addActionListener(e -> new TelaMotoristas(this));
        caminhoes.addActionListener(e -> new TelaCaminhoes(this));
        carretas.addActionListener(e -> new TelaCarretas(this));
        fechar.addActionListener(e -> dispose());

        p.add(produtos);
        p.add(motoristas);
        p.add(caminhoes);
        p.add(carretas);
        p.add(fechar);

        setContentPane(p);
        setVisible(true);
    }
}
