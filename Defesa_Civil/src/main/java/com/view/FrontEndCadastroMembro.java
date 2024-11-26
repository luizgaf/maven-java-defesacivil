package com.view;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import javax.swing.text.MaskFormatter;
import java.io.*;
import com.controller.*;
import com.model.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FrontEndCadastroMembro extends JPanel {
    private BufferedImage image;
    private JPanel imagePanel;
    private JFrame frame;
    private JFormattedTextField telefoneField, telEmergenciaField, cpfField, cepField, birthDateField;
    private JTextField nameField, emailField, logradouroField, numeroField, cidadeField, complementoField;

    public FrontEndCadastroMembro(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Cadastro Membro", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(label, BorderLayout.NORTH);

        JPanel inputs = new JPanel();
        inputs.setLayout(new BoxLayout(inputs, BoxLayout.Y_AXIS));

        // Nome Completo
        JPanel nomePanel = new JPanel(new FlowLayout());
        JLabel name = new JLabel("Nome Completo:");
        nomePanel.add(name);
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(600, 30));
        nomePanel.add(nameField);

        // CPF e Data de Nascimento
        JPanel dataPanel = new JPanel(new FlowLayout());
        JLabel cpf = new JLabel("CPF:");
        dataPanel.add(cpf);
        cpfField = new JFormattedTextField();
        cpfField.setPreferredSize(new Dimension(200, 30));
        try {
            MaskFormatter cpfMask = new MaskFormatter("###.###.###-##");
            cpfMask.install(cpfField);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dataPanel.add(cpfField);

        // Telefone e Email
        JPanel dataPanel2 = new JPanel(new FlowLayout());
        JLabel telefone = new JLabel("Telefone:");
        dataPanel2.add(telefone);
        telefoneField = new JFormattedTextField();
        telefoneField.setPreferredSize(new Dimension(150, 30));
        try {
            MaskFormatter telefoneMask = new MaskFormatter("(##) #####-####");
            telefoneMask.install(telefoneField);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dataPanel2.add(telefoneField);

        JLabel email = new JLabel("Email:");
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 30));
        dataPanel2.add(email);
        dataPanel2.add(emailField);

        // Telefone Emergencial
        JPanel dataPanel3 = new JPanel(new FlowLayout());
        JLabel telEmergencia = new JLabel("Telefone Emergencia:");
        telEmergenciaField = new JFormattedTextField();
        telEmergenciaField.setPreferredSize(new Dimension(150, 30));
        try {
            MaskFormatter telEmergenciaMask = new MaskFormatter("(##) #####-####");
            telEmergenciaMask.install(telEmergenciaField);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dataPanel3.add(telEmergencia);
        dataPanel3.add(telEmergenciaField);

        // data nascimento
        JLabel birthDateLabel = new JLabel("Data de Nascimento:");
        birthDateField = new JFormattedTextField();
        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');
            dateMask.install(birthDateField);
            birthDateField.setPreferredSize(new Dimension(150, 30));
        } catch (Exception e) {
            e.printStackTrace();
        }
        dataPanel.add(birthDateLabel);
        dataPanel.add(birthDateField);


        // Upload de Foto
        JLabel uploadFoto = new JLabel("Upload Foto:");
        dataPanel3.add(uploadFoto);
        JButton uploaderFoto = new JButton("Selecionar Imagem");
        uploaderFoto.addActionListener(e -> selectImage());
        dataPanel3.add(uploaderFoto);

        JPanel dataPanel4 = new JPanel(new FlowLayout());
        imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image != null) {
                    g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
                }
            }
        };
        imagePanel.setPreferredSize(new Dimension(200, 200));
        dataPanel4.add(imagePanel);

        inputs.add(nomePanel);
        inputs.add(dataPanel);
        inputs.add(dataPanel2);
        inputs.add(dataPanel3);
        inputs.add(dataPanel4);

        // Endereço
        JPanel enderecoInput = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

// Outros campos de endereço
        logradouroField = new JTextField();
        numeroField = new JTextField();
        cidadeField = new JTextField();
        complementoField = new JTextField();

// Campo CEP com máscara
        cepField = new JFormattedTextField();
        try {
            MaskFormatter cepMask = new MaskFormatter("#####-###");
            cepMask.setPlaceholderCharacter('_'); // Opcional
            cepMask.install(cepField);
            cepField.setPreferredSize(new Dimension(100, 30));
        } catch (Exception e) {
            e.printStackTrace();
        }

        logradouroField.setPreferredSize(new Dimension(300, 30));
        numeroField.setPreferredSize(new Dimension(80, 30));
        cepField.setPreferredSize(new Dimension(120, 30));
        cidadeField.setPreferredSize(new Dimension(200, 30));
        complementoField.setPreferredSize(new Dimension(300, 30));


        enderecoInput.add(new JLabel("Logradouro:"));
        enderecoInput.add(logradouroField);
        enderecoInput.add(new JLabel("Complemento:"));
        enderecoInput.add(complementoField);
        enderecoInput.add(new JLabel("Número:"));
        enderecoInput.add(numeroField);
        enderecoInput.add(new JLabel("CEP:"));
        enderecoInput.add(cepField);
        enderecoInput.add(new JLabel("Cidade:"));
        enderecoInput.add(cidadeField);

        inputs.add(enderecoInput);
        add(inputs, BorderLayout.CENTER);

        // Botões
        JButton submitButton = new JButton("Cadastrar");
        JButton cancelButton = new JButton("Cancelar");
        addHoverEffect(submitButton);
        addHoverEffect(cancelButton);

        submitButton.addActionListener(e -> adcMembro());
        cancelButton.addActionListener(e -> voltar());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addHoverEffect(JButton button) {
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

    private void selectImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imagens", "jpg", "png", "jpeg"));
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                image = ImageIO.read(selectedFile);
                imagePanel.repaint();
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao carregar a imagem", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private byte[] convertImageToBytes(BufferedImage image) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "jpg", baos); // Você pode usar "png" ou outros formatos, se necessário
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao converter a imagem para bytes.", "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void adcMembro() {
        try {
            // Obter valores dos campos, removendo os caracteres das máscaras
            String nome = nameField.getText().trim();
            String cpf = cpfField.getText().replaceAll("\\D", "").trim(); // Remove tudo que não for número
            String telefone = telefoneField.getText().replaceAll("\\D", "").trim();
            String telEmergencia = telEmergenciaField.getText().replaceAll("\\D", "").trim();
            String email = emailField.getText().trim();

            String logradouro = logradouroField.getText().trim();
            String numero = numeroField.getText().trim();
            String cep = cepField.getText().replaceAll("\\D", "").trim(); // Remove tudo que não for número
            String cidade = cidadeField.getText().trim();
            String complemento = complementoField.getText().trim();

            String birthDate = birthDateField.getText().trim();


            // Converter para LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate birthDateParsed = LocalDate.parse(birthDate, formatter);


            // Validar campos obrigatórios
            StringBuilder erros = new StringBuilder();

            if (nome.isEmpty()) erros.append("O campo 'Nome Completo' é obrigatório.\n");
            if (cpf.length() != 11) erros.append("O campo 'CPF' deve conter exatamente 11 dígitos.\n");
            if (telefone.length() != 11) erros.append("O campo 'Telefone' deve conter exatamente 11 dígitos.\n");
            if (telEmergencia.length() != 11) erros.append("O campo 'Telefone Emergência' deve conter exatamente 11 dígitos.\n");
            if (email.isEmpty()) erros.append("O campo 'Email' é obrigatório.\n");
            if (logradouro.isEmpty()) erros.append("O campo 'Logradouro' é obrigatório.\n");
            if (numero.isEmpty() || !numero.matches("\\d+")) erros.append("O campo 'Número' deve conter apenas números.\n");
            if (cep.length() != 8) erros.append("O campo 'CEP' deve conter exatamente 8 dígitos.\n");
            if (cidade.isEmpty()) erros.append("O campo 'Cidade' é obrigatório.\n");
            if (image == null) erros.append("Você deve selecionar uma foto para o membro.\n");
            if (!birthDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
                erros.append("O campo 'Data de Nascimento' deve estar no formato dd/MM/yyyy.\n");
            }

            byte[] fotoBytes = convertImageToBytes(image);
            if (fotoBytes == null) {
                JOptionPane.showMessageDialog(this, "Erro ao processar a foto do membro.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Mostrar erros, se houver
            if (erros.length() > 0) {
                JOptionPane.showMessageDialog(this, erros.toString(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Criar instância do Endereco
            Endereco endereco = new Endereco();
            endereco.setLogradouro(logradouro);
            endereco.setNumero(Integer.parseInt(numero));
            endereco.setCEP(cep);
            endereco.setCidade(cidade);
            endereco.setComplemento(complemento);

            // Salvar endereço no banco
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            Endereco enderecoSalvo = enderecoDAO.Salvar(endereco);

            if (enderecoSalvo == null) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar o endereço no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Criar instância do Membro
            Membro membro = new Membro();
            membro.setNome(nome);
            membro.setCPF(cpf);
            membro.setNumTelefone(telefone);
            membro.setNumEmergencia(telEmergencia);
            membro.setEndereco(enderecoSalvo);
            membro.setEmail(email);
            membro.setDataNasc(birthDateParsed);
            membro.setFoto(fotoBytes);

            // Salvar membro no banco
            MembroDAO membroDAO = new MembroDAO();
            Membro membroSalvo = membroDAO.Salvar(membro);

            if (membroSalvo == null) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar o membro no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cadastro realizado com sucesso
            JOptionPane.showMessageDialog(this, "Membro cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            // Fechar a janela
            if (frame != null) {
                frame.dispose();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }


    private void voltar() {
        if (frame != null) {
            frame.dispose();
        }
    }
}
