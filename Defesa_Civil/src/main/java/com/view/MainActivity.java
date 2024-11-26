package com.view;

import javax.swing.*;

public class MainActivity {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Defesa Civil");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Adiciona a tela de login
        LoginScreen loginPanel = new LoginScreen(frame);
        frame.add(loginPanel);

        // Exibindo a janela
        frame.setVisible(true);
    }
}
