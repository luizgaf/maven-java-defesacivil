package com.view;

import com.controller.UserDAO;
import com.model.User;
import util.CriptografiaUtil;

import javax.swing.*;

public class LoginView {

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();

        // Opção de Login ou Cadastro
        String[] options = {"Login", "Registrar"};
        int choice = JOptionPane.showOptionDialog(null,
                "Escolha uma opção",
                "Sistema de Login",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        if (choice == 0) {
            // Login
            String username = JOptionPane.showInputDialog("Digite seu usuário:");
            String password = JOptionPane.showInputDialog("Digite sua senha:");

            User user = userDAO.loginUser(username, CriptografiaUtil.criptografarSenha(password));
            if (user != null && CriptografiaUtil.verificarSenha(password, user.getPassword())) {
                JOptionPane.showMessageDialog(null, "Bem-vindo, " + user.getUsername() + "!");
            } else {
                JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos!");
            }
        } else if (choice == 1) {

            String username = JOptionPane.showInputDialog("Digite seu usuário:");
            String password = JOptionPane.showInputDialog("Digite sua senha:");
            String role = JOptionPane.showInputDialog("Digite o papel do usuário (ex: ADMIN, USER):");

            User user = new User();
            user.setUsername(username);
            password = CriptografiaUtil.criptografarSenha(password);
            user.setPassword(password);
            user.setRole(role);

            if (userDAO.registerUser(user)) {
                JOptionPane.showMessageDialog(null, "Usuário registrado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao registrar o usuário!");
            }
        }
    }
}

