package com.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import com.controller.*;
import com.model.*;


public class FrontEndCadastro extends JPanel {

    private JButton adicionarButton, adcMembro, voltarButton;
    private JTextField logradouroField, numeroField, cepField, cidadeField, complementoField, nomeFamiliaField;
    private JComboBox<String> risco;
    private JFrame parentFrame;

    public FrontEndCadastro(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        setLayout(new BorderLayout());

        JLabel label = new JLabel("Cadastro de Família", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(label, BorderLayout.NORTH);

        JPanel inputs = new JPanel(new GridLayout(0, 1));

        JPanel nomeFamiliaInput = new JPanel(new FlowLayout());

        nomeFamiliaField = new JTextField();
        JLabel nomeFamiliaLabel = new JLabel("Nome da Família:");

        nomeFamiliaField.setPreferredSize(new Dimension(300, 30));

        nomeFamiliaInput.add(nomeFamiliaLabel);
        nomeFamiliaInput.add(nomeFamiliaField);
        inputs.add(nomeFamiliaInput);

        JPanel riscoImput = new JPanel(new FlowLayout(FlowLayout.CENTER));
        String[] riscoStrings = {"em risco", "risco alto", "risco extremo"};
        risco = new JComboBox<>(riscoStrings);
        risco.setPreferredSize(new Dimension(150, 30));
        riscoImput.add(new JLabel("Nível de Risco:"));
        riscoImput.add(risco);
        inputs.add(riscoImput);

        add(inputs, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        add(buttonPanel, BorderLayout.SOUTH);

        adicionarButton = new JButton("Concluido");
        adcMembro = new JButton("Adicionar Membro");
        voltarButton = new JButton("Voltar");

        addHoverEffect(adicionarButton);
        addHoverEffect(adcMembro);
        addHoverEffect(voltarButton);

        adicionarButton.addActionListener(e -> validarCampos());
        adcMembro.addActionListener(e -> adicionarMembro());
        voltarButton.addActionListener(e -> voltar());

        buttonPanel.add(adicionarButton);
        buttonPanel.add(adcMembro);
        buttonPanel.add(voltarButton);
    }

    private void validarCampos() {
        StringBuilder erros = new StringBuilder();

        if (nomeFamiliaField.getText().trim().isEmpty()) {
            erros.append("O campo 'Nome da Família' é obrigatório.\n");
        }
        if (logradouroField.getText().trim().isEmpty()) {
            erros.append("O campo 'Logradouro' é obrigatório.\n");
        }
        if (numeroField.getText().trim().isEmpty()) {
            erros.append("O campo 'Número' é obrigatório.\n");
        }
        if (cepField.getText().trim().isEmpty()) {
            erros.append("O campo 'CEP' é obrigatório.\n");
        }
        if (cidadeField.getText().trim().isEmpty()) {
            erros.append("O campo 'Cidade' é obrigatório.\n");
        }

        if (erros.length() > 0) {
            JOptionPane.showMessageDialog(this, erros.toString(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        } else {
            adicionarFamilia();
        }
    }

    private void adicionarFamilia() {
        System.out.println("Família adicionada com sucesso!");
        System.out.println(", Risco: " + risco.getSelectedItem());
    }

    private void adicionarMembro() {
        JFrame adcMembro = new JFrame("Adição de Membro");
        adcMembro.setSize(800, 1000);
        adcMembro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel cadastroPanel = new FrontEndCadastroMembro(adcMembro);
        adcMembro.add(cadastroPanel);
        adcMembro.setResizable(false);

        adcMembro.setLocationRelativeTo(null);
        adcMembro.setVisible(true);
    }

    private void voltar() {
        if (parentFrame != null) {
            parentFrame.dispose();
        }
    }

    public void addHoverEffect(JButton button) {
        button.setBackground(new Color(50, 50, 50));
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
