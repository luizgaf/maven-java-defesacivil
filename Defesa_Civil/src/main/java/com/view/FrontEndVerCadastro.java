package com.view;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.controller.*;
import com.model.*;

public class FrontEndVerCadastro extends JPanel {

    private JTable tabelaFamilias;
    private DefaultTableModel tableModel;
    private JButton editarButton, excluirButton, atualizarButton, voltarButton;
    private CadastroFamiliaDAO familiaDAO;
    private JFrame parentFrame;

    public FrontEndVerCadastro(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.familiaDAO = new CadastroFamiliaDAO(); // Inicializa o DAO

        setLayout(new BorderLayout());

        JLabel label = new JLabel("Lista de Famílias Cadastradas", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(label, BorderLayout.NORTH);

        // Configurar tabela
        tableModel = new DefaultTableModel(new String[]{"ID", "Nome", "Risco", "Emergência"}, 0);
        tabelaFamilias = new JTable(tableModel);
        carregarDadosTabela();

        JScrollPane scrollPane = new JScrollPane(tabelaFamilias);
        add(scrollPane, BorderLayout.CENTER);

        // Painel de Botões
        JPanel botoesPanel = new JPanel(new FlowLayout());

        editarButton = new JButton("Editar");
        editarButton.addActionListener(e -> editarFamilia());

        excluirButton = new JButton("Excluir");
        excluirButton.addActionListener(e -> excluirFamilia());

        atualizarButton = new JButton("Atualizar");
        atualizarButton.addActionListener(e -> atualizarTabela());

        voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(e -> voltar());

        addHoverEffect(editarButton);
        addHoverEffect(excluirButton);
        addHoverEffect(atualizarButton);
        addHoverEffect(voltarButton);

        botoesPanel.add(editarButton);
        botoesPanel.add(excluirButton);
        botoesPanel.add(atualizarButton);
        botoesPanel.add(voltarButton);

        add(botoesPanel, BorderLayout.SOUTH);
    }

    private void carregarDadosTabela() {
        List<CadastroFamilia> familias = familiaDAO.ListarCadastroFamilia();

        // Limpar tabela antes de carregar os dados
        tableModel.setRowCount(0);

        for (CadastroFamilia familia : familias) {
            tableModel.addRow(new Object[]{
                    familia.getIdFamilia(),
                    familia.getNomeFamilia(),
                    familia.getTipoRisco().getCategoria(),
                    familia.getTipoEmergencia().getCategoria()
            });
        }
    }

    private void atualizarTabela() {
        carregarDadosTabela();
        JOptionPane.showMessageDialog(this, "Dados atualizados com sucesso!", "Atualização", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editarFamilia() {
        int linhaSelecionada = tabelaFamilias.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma família para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idFamilia = (int) tabelaFamilias.getValueAt(linhaSelecionada, 0);
        CadastroFamilia familia = familiaDAO.BuscarPorId(idFamilia);

        if (familia == null) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados da família.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Criar a janela de edição
        JFrame frameEdicao = new JFrame("Editar Família");
        FrontEndCadastro painelEdicao = new FrontEndCadastro(frameEdicao); // Aqui está o "painelEdicao"
        painelEdicao.preencherCampos(familia); // Preenche os campos do formulário com os dados da família

        // Substituir o botão padrão de "Salvar" para que atualize a família
        painelEdicao.salvarButton.addActionListener(e -> {
            try {
                CadastroFamilia familiaEditada = painelEdicao.getFamiliaAtual();
                familiaEditada.setIdFamilia(familia.getIdFamilia()); // Garante que o ID não mude
                CadastroFamilia atualizado = familiaDAO.Atualizar(familiaEditada);

                if (atualizado != null) {
                    JOptionPane.showMessageDialog(frameEdicao, "Família atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    frameEdicao.dispose();
                    atualizarTabela(); // Atualiza a tabela principal
                } else {
                    JOptionPane.showMessageDialog(frameEdicao, "Erro ao atualizar a família.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frameEdicao, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        criarJanela("Editar Família", new Dimension(630, 500), painelEdicao); // Cria a janela com o painel de edição
    }

    private void excluirFamilia() {
        int linhaSelecionada = tabelaFamilias.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma família para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idFamilia = (int) tabelaFamilias.getValueAt(linhaSelecionada, 0);
        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir esta família?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            boolean sucesso = familiaDAO.Deletar(idFamilia);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Família excluída com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                atualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir a família.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void criarJanela(String titulo, Dimension dimensao, JPanel painel) {
        JFrame janela = new JFrame(titulo);
        janela.setSize(dimensao);
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        if (painel instanceof FrontEndCadastro) {
            ((FrontEndCadastro) painel).setFrame(janela);
        }

        janela.add(painel);
        janela.setResizable(false);
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }

    private void addHoverEffect(JButton button) {
        button.setBackground(new Color(50, 50, 50));
        button.setForeground(Color.white);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(150, 40));

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
}
