package com.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.controller.*;
import com.model.*;

public class FrontEndCadastro extends JPanel {

    public JButton salvarButton, voltarButton, associarMembroButton;
    private JTextField nomeFamiliaField;
    private JComboBox<String> risco, tipoEmergencia;
    private JFrame parentFrame;
    private CadastroFamilia familiaAtual; // Família que será editada ou criada

    public FrontEndCadastro(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.familiaAtual = null; // Novo cadastro por padrão

        setLayout(new BorderLayout());

        // Título
        JLabel label = new JLabel("Cadastro de Família", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(label, BorderLayout.NORTH);

        // Painel de entrada de dados
        JPanel inputs = new JPanel();
        inputs.setLayout(new BoxLayout(inputs, BoxLayout.Y_AXIS));

        // Campo Nome da Família
        JPanel nomeFamiliaInput = new JPanel(new FlowLayout());
        nomeFamiliaField = new JTextField();
        JLabel nomeFamiliaLabel = new JLabel("Nome da Família:");
        nomeFamiliaField.setPreferredSize(new Dimension(300, 30));
        nomeFamiliaInput.add(nomeFamiliaLabel);
        nomeFamiliaInput.add(nomeFamiliaField);
        inputs.add(nomeFamiliaInput);

        // Campo Nível de Risco
        JPanel riscoInput = new JPanel(new FlowLayout(FlowLayout.CENTER));
        String[] riscoStrings = {"Em risco", "Alto Risco", "Extremo Risco"};
        risco = new JComboBox<>(riscoStrings);
        risco.setPreferredSize(new Dimension(150, 30));
        riscoInput.add(new JLabel("Nível de Risco:"));
        riscoInput.add(risco);
        inputs.add(riscoInput);

        // Campo Tipo de Emergência
        JPanel tipoEmergenciaInput = new JPanel(new FlowLayout(FlowLayout.CENTER));
        String[] emergenciaStrings = {"Inundação", "Vendaval", "Desmoronamentos", "Incêndio", "Tsuname", "Tempestade"};
        tipoEmergencia = new JComboBox<>(emergenciaStrings);
        tipoEmergencia.setPreferredSize(new Dimension(200, 30));
        tipoEmergenciaInput.add(new JLabel("Tipo de Emergência:"));
        tipoEmergenciaInput.add(tipoEmergencia);
        inputs.add(tipoEmergenciaInput);

        add(inputs, BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        salvarButton = new JButton("Salvar");
        voltarButton = new JButton("Voltar");

        addHoverEffect(salvarButton);
        addHoverEffect(voltarButton);

        salvarButton.addActionListener(e -> salvarFamilia());
        voltarButton.addActionListener(e -> voltar());

        buttonPanel.add(salvarButton);
        buttonPanel.add(voltarButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void salvarFamilia() {
        try {
            // Obter valores dos campos
            String nomeFamilia = nomeFamiliaField.getText().trim();
            String nivelRisco = (String) risco.getSelectedItem();
            String tipoEmergenciaSelecionado = (String) tipoEmergencia.getSelectedItem();

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

            // Criar ou Atualizar instância de CadastroFamilia
            if (familiaAtual == null) {
                familiaAtual = new CadastroFamilia();
            }

            familiaAtual.setNomeFamilia(nomeFamilia);
            familiaAtual.setTipoRisco(tipoRisco);
            familiaAtual.setTipoEmergencia(tipoEmergencia);

            // Salvar no banco de dados usando o DAO
            CadastroFamiliaDAO dao = new CadastroFamiliaDAO();
            CadastroFamilia familiaSalva = dao.Salvar(familiaAtual);

            // Verificar se foi salvo com sucesso
            if (familiaSalva == null || familiaSalva.getIdFamilia() == 0) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar a família no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Família salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                if (parentFrame != null) {
                    parentFrame.dispose();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
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

    public CadastroFamilia getFamiliaAtual() {
        return familiaAtual;
    }

    public void preencherCampos(CadastroFamilia familia) {
        this.familiaAtual = familia;
        if (familia == null) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar os dados da família.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        nomeFamiliaField.setText(familia.getNomeFamilia());
        if (familia.getTipoRisco() != null) {
            risco.setSelectedItem(familia.getTipoRisco().getCategoria());
        }
        if (familia.getTipoEmergencia() != null) {
            tipoEmergencia.setSelectedItem(familia.getTipoEmergencia().getCategoria());
        }
    }

    public void addHoverEffect(JButton button) {
        button.setBackground(new Color(50, 50, 50));
        button.setForeground(Color.white);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(150, 50));

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

    public void setFrame(JFrame frame) {
        this.parentFrame = frame;
    }
}
