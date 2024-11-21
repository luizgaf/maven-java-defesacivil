package com.view;

import com.controller.UserDAO;
import com.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserView extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnCadastrar;
    private JButton btnAutenticar;
    private JLabel lblStatus;
    private UserDAO userDAO;

    public UserView() {
        userDAO = new UserDAO();

        // Configurações da janela
        setTitle("Cadastro e Autenticação de Usuário");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout da interface
        setLayout(new GridLayout(4, 2, 10, 10));

        // Componentes
        JLabel lblUsername = new JLabel("Nome de Usuário:");
        txtUsername = new JTextField();

        JLabel lblPassword = new JLabel("Senha:");
        txtPassword = new JPasswordField();

        btnCadastrar = new JButton("Cadastrar");
        btnAutenticar = new JButton("Autenticar");
        lblStatus = new JLabel("");

        // Adiciona os componentes na janela
        add(lblUsername);
        add(txtUsername);
        add(lblPassword);
        add(txtPassword);
        add(btnCadastrar);
        add(btnAutenticar);
        add(lblStatus);

        // Ações dos botões
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarUsuario();
            }
        });

        btnAutenticar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticarUsuario();
            }
        });
    }

    private void cadastrarUsuario() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        User usuario = new User();
        usuario.setUsername(username);
        usuario.setPassword(password);

        boolean sucesso = userDAO.cadastrarUsuario(usuario);
        if (sucesso) {
            lblStatus.setText("Usuário cadastrado com sucesso.");
            lblStatus.setForeground(Color.GREEN);
        } else {
            lblStatus.setText("Erro ao cadastrar o usuário.");
            lblStatus.setForeground(Color.RED);
        }
    }

    private void autenticarUsuario() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        User usuario = userDAO.autenticarUsuario(username, password);

        if (usuario != null) {
            lblStatus.setText("Usuário autenticado com sucesso.");
            lblStatus.setForeground(Color.GREEN);
        } else {
            lblStatus.setText("Usuário ou senha inválidos.");
            lblStatus.setForeground(Color.RED);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UserView view = new UserView();
                view.setVisible(true);
            }
        });
    }
}
