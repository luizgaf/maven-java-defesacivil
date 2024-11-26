package com.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.controller.*;
import com.model.*;

public class LoginScreen extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JFrame parentFrame;

    public LoginScreen(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Login", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Nome de usuário
        JLabel usernameLabel = new JLabel("Usuário:");
        usernameField = new JTextField(15);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(usernameLabel, gbc);
        gbc.gridx = 1;
        add(usernameField, gbc);

        // Senha
        JLabel passwordLabel = new JLabel("Senha:");
        passwordField = new JPasswordField(15);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Botão de login
        loginButton = new JButton("Entrar");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        // Ação do botão de login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });
    }

    private void realizarLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.loginUser(username, password);

            if (user != null) {
                // Login bem-sucedido: troca para a tela FrontEnd
                parentFrame.getContentPane().removeAll();
                FrontEnd frontEndPanel = new FrontEnd(parentFrame);
                parentFrame.add(frontEndPanel);

                parentFrame.setSize(800, 300);
                parentFrame.setLocationRelativeTo(null);
                parentFrame.setResizable(false);
                parentFrame.revalidate();
                parentFrame.repaint();
            } else {
                // Exibe mensagem de erro se as credenciais estiverem incorretas
                JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos!", "Erro de Login", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            // Exibe uma mensagem em caso de falha na conexão ou outros erros
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
