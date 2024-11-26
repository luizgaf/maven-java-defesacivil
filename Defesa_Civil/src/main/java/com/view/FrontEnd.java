package com.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.controller.*;
import com.model.*;


public class FrontEnd extends JPanel{

    private JButton verCadastro, adcCadastro, btnSair, adcMembroFamilia;


    public FrontEnd(JFrame frame){
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Defesa Civil | Gerenciamento de Famílias", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(label, BorderLayout.NORTH);

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 30));

        verCadastro= new JButton("Ver cadastros");
        adcCadastro = new JButton("Adicionar Família");
        adcMembroFamilia = new JButton("Adicionar Membro");
        btnSair = new JButton("Sair");

        addHoverEffect(verCadastro);
        addHoverEffect(adcCadastro);
        addHoverEffect(btnSair);
        addHoverEffect(adcMembroFamilia);

        botoesPanel.add(verCadastro);
        botoesPanel.add(adcCadastro);
        botoesPanel.add(adcMembroFamilia);
        botoesPanel.add(btnSair);

        btnSair.addActionListener(e -> btnSair(frame));

        add(botoesPanel, BorderLayout.CENTER);

        verCadastro.addActionListener(e -> verCadastros());
        adcCadastro.addActionListener(e -> janelaCadastro());
        adcMembroFamilia.addActionListener(e -> adicionarMembro());
    }

    private void criarJanela(String titulo, Dimension dimensao, JPanel painel) {
        JFrame janela = new JFrame(titulo);
        janela.setSize(dimensao);
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Configura o frame no painel, se aplicável
        if (painel instanceof FrontEndCadastro) {
            ((FrontEndCadastro) painel).setFrame(janela);
        } else if (painel instanceof FrontEndCadastroMembro) {
            ((FrontEndCadastroMembro) painel).setFrame(janela);
        } else if (painel instanceof FrontEndVerCadastro) {
            ((FrontEndVerCadastro) painel).setFrame(janela);
        }

        janela.add(painel);
        janela.setResizable(false);
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }

    // Método para abrir a janela de Cadastro de Família
    private void janelaCadastro() {
        criarJanela("Cadastro de Família", new Dimension(630, 500), new FrontEndCadastro(null));
    }

    // Método para abrir a janela de Adição de Membro
    private void adicionarMembro() {
        criarJanela("Adição de Membro", new Dimension(800, 1000), new FrontEndCadastroMembro(null));
    }

    // Método para abrir a janela de Visualização de Cadastros
    private void verCadastros() {
        criarJanela("Ver Cadastros", new Dimension(800, 1000), new FrontEndVerCadastro(null));
    }



    public void btnSair(JFrame frame){
        frame.dispose();
    }

    public void addHoverEffect(JButton button) {
        button.setBackground(new Color(50,50,50));
        button.setForeground(Color.white);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(200, 50));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.BLACK); 
                button.setForeground(Color.WHITE); 
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(50, 50, 50)); 
                button.setForeground(Color.WHITE); 
            }
        });
    }
}