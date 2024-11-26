package com.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import com.controller.*;
import com.model.*;
import java.util.List;



public class FrontEndCadastro extends JPanel {

    private JButton adicionarButton, voltarButton, associarMembroButton;
    private JTextField nomeFamiliaField;
    private JComboBox<String> risco, tipoEmergencia;
    private JFrame parentFrame;

    public FrontEndCadastro(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        setLayout(new BorderLayout());

        JLabel label = new JLabel("Cadastro de Família", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(label, BorderLayout.NORTH);

        JPanel inputs = new JPanel(new GridLayout(0, 1));

        // Campo Nome da Família
        JPanel nomeFamiliaInput = new JPanel(new FlowLayout());
        nomeFamiliaField = new JTextField();
        JLabel nomeFamiliaLabel = new JLabel("Nome da Família:");
        nomeFamiliaField.setPreferredSize(new Dimension(300, 30));
        nomeFamiliaInput.add(nomeFamiliaLabel);
        nomeFamiliaInput.add(nomeFamiliaField);
        inputs.add(nomeFamiliaInput);

        // Campo Nível de Risco
        JPanel riscoImput = new JPanel(new FlowLayout(FlowLayout.CENTER));
        String[] riscoStrings = {"Em risco", "Alto Risco", "Extremo Risco"};
        risco = new JComboBox<>(riscoStrings);
        risco.setPreferredSize(new Dimension(150, 30));
        riscoImput.add(new JLabel("Nível de Risco:"));
        riscoImput.add(risco);
        inputs.add(riscoImput);

        // Campo Tipo de Emergência
        JPanel tipoEmergenciaInput = new JPanel(new FlowLayout(FlowLayout.CENTER));
        String[] emergenciaStrings = {"Inundação", "Vendaval", "Desmoronamentos", "Incêndio", "Tsuname", "Tempestade"};
        tipoEmergencia = new JComboBox<>(emergenciaStrings);
        tipoEmergencia.setPreferredSize(new Dimension(200, 30));
        tipoEmergenciaInput.add(new JLabel("Tipo de Emergência:"));
        tipoEmergenciaInput.add(tipoEmergencia);
        inputs.add(tipoEmergenciaInput);

        // Botão para Associar Membros
        JPanel associarMembroPanel = new JPanel(new FlowLayout());
        associarMembroButton = new JButton("Associar Membros");
        associarMembroButton.addActionListener(e -> abrirDialogoAssociarMembros());
        associarMembroPanel.add(associarMembroButton);
        inputs.add(associarMembroPanel);

        add(inputs, BorderLayout.CENTER);

        // Painel de Botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        adicionarButton = new JButton("Concluído");
        voltarButton = new JButton("Voltar");

        addHoverEffect(adicionarButton);
        addHoverEffect(voltarButton);

        adicionarButton.addActionListener(e -> adicionarFamilia());
        voltarButton.addActionListener(e -> voltar());

        buttonPanel.add(adicionarButton);
        buttonPanel.add(voltarButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void adicionarFamilia() {
        try {
            // Obter valores dos campos
            String nomeFamilia = nomeFamiliaField.getText().trim();
            String nivelRisco = (String) risco.getSelectedItem(); // Ex.: "Em risco", "Alto Risco", "Extremo Risco"
            String tipoEmergenciaSelecionado = (String) tipoEmergencia.getSelectedItem(); // Ex.: "Inundação", "Vendaval"

            // Validar campos obrigatórios
            StringBuilder erros = new StringBuilder();
            if (nomeFamilia.isEmpty()) {
                erros.append("O campo 'Nome da Família' é obrigatório.\n");
            }
            if (nivelRisco == null || nivelRisco.isEmpty()) {
                erros.append("O campo 'Nível de Risco' é obrigatório.\n");
            }
            if (tipoEmergenciaSelecionado == null || tipoEmergenciaSelecionado.isEmpty()) {
                erros.append("O campo 'Tipo de Emergência' é obrigatório.\n");
            }

            // Mostrar erros, se houver
            if (erros.length() > 0) {
                JOptionPane.showMessageDialog(this, erros.toString(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Mapear IDs para TipoRisco e TipoEmergencia
            int idRisco = switch (nivelRisco) {
                case "Em risco" -> 1;
                case "Alto Risco" -> 2;
                case "Extremo Risco" -> 3;
                default -> throw new IllegalArgumentException("Nível de risco inválido.");
            };

            int idEmergencia = switch (tipoEmergenciaSelecionado) {
                case "Inundação" -> 1;
                case "Vendaval" -> 2;
                case "Desmoronamentos" -> 3;
                case "Incêndio" -> 4;
                case "Tsuname" -> 5;
                case "Tempestade" -> 6;
                default -> throw new IllegalArgumentException("Tipo de emergência inválido.");
            };

            // Criar instâncias de TipoRisco e TipoEmergencia
            TipoRisco tipoRisco = new TipoRisco(idRisco, nivelRisco);
            TipoEmergencia tipoEmergencia = new TipoEmergencia(idEmergencia, tipoEmergenciaSelecionado);

            // Criar instância de CadastroFamilia
            CadastroFamilia cadastroFamilia = new CadastroFamilia();
            cadastroFamilia.setNomeFamilia(nomeFamilia);
            cadastroFamilia.setTipoRisco(tipoRisco);
            cadastroFamilia.setTipoEmergencia(tipoEmergencia);

            // Salvar no banco de dados usando o DAO
            CadastroFamiliaDAO dao = new CadastroFamiliaDAO();
            CadastroFamilia familiaSalva = dao.Salvar(cadastroFamilia);

            // Verificar se foi salvo com sucesso
            if (familiaSalva == null || familiaSalva.getIdFamilia() == 0) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar a família no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Família cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                if (parentFrame != null) {
                    parentFrame.dispose();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }


    private void abrirDialogoAssociarMembros() {
        try {
            JDialog dialog = new JDialog((Frame) null, "Associar Membros", true);
            dialog.setSize(400, 300);
            dialog.setLayout(new BorderLayout());

            // Obter membros cadastrados do DAO
            MembroDAO membroDAO = new MembroDAO();
            List<Membro> membros = membroDAO.ListarMembros();

            if (membros == null) {
                JOptionPane.showMessageDialog(this, "Nenhum membro cadastrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Criar lista de seleção de membros
            DefaultListModel<Membro> listModel = new DefaultListModel<>();
            for (Membro membro : membros) {
                listModel.addElement(membro);
            }

            JList<Membro> membroList = new JList<>(listModel);
            membroList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            JScrollPane scrollPane = new JScrollPane(membroList);

            dialog.add(scrollPane, BorderLayout.CENTER);

            // Botão de confirmação
            JButton confirmarButton = new JButton("Confirmar");
            confirmarButton.addActionListener(e -> {
                List<Membro> membrosSelecionados = membroList.getSelectedValuesList();
                if (membrosSelecionados != null ) {
                    System.out.println("Membros associados: " + membrosSelecionados);
                }
                dialog.dispose();
            });

            dialog.add(confirmarButton, BorderLayout.SOUTH);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar os membros: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    private void voltar() {
        if (parentFrame != null) {
            int confirm = JOptionPane.showConfirmDialog(
                    parentFrame,
                    "Tem certeza que deseja voltar?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                parentFrame.dispose();
            }
        }
    }

    public void setFrame(JFrame frame) {
        this.parentFrame = frame;
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

