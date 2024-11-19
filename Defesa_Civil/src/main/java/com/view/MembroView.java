package com.view;

import com.controller.MembroDAO;
import com.model.Membro;
import com.model.Endereco;
import com.model.CadastroFamilia;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MembroView {

    private static final Scanner scanner = new Scanner(System.in);
    private static final MembroDAO membroDAO = new MembroDAO();

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n=== Gerenciamento de Membros ===");
            System.out.println("1. Salvar Membro");
            System.out.println("2. Atualizar Membro");
            System.out.println("3. Buscar Membro por CPF");
            System.out.println("4. Deletar Membro");
            System.out.println("5. Listar Todos os Membros");
            System.out.println("6. Buscar Membros por Família");
            System.out.println("7. Buscar Membros por CEP");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> salvarMembro();
                case 2 -> atualizarMembro();
                case 3 -> buscarPorIdcpf();
                case 4 -> deletarcpf();
                case 5 -> listarMembros();
                case 6 -> BuscarPorCadastroFamilia();
                case 7 -> BuscarPorCep();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void salvarMembro() {
        Membro membro = new Membro();
        System.out.print("CPF: ");
        membro.setCPF(scanner.nextLine());
        System.out.print("Nome: ");
        membro.setNome(scanner.nextLine());
        System.out.print("Número de Telefone: ");
        membro.setNumTelefone(scanner.nextLine());
        System.out.print("Data de Nascimento (AAAA-MM-DD): ");
        String dataNascStr = scanner.nextLine();
        membro.setDataNasc(LocalDate.parse(dataNascStr));
        System.out.print("Email: ");
        membro.setEmail(scanner.nextLine());
        System.out.print("Telefone de Emergência: ");
        membro.setNumEmergencia(scanner.nextLine());


        Endereco endereco = new Endereco();
        membro.setEndereco(endereco);

        CadastroFamilia familia = new CadastroFamilia();
        membro.setCadastroFamilia(familia);


        byte[] foto = new byte[0];
        membro.setFoto(foto);

        if (membroDAO.Salvar(membro) != null) {
            System.out.println("Membro salvo com sucesso!");
        } else {
            System.out.println("Erro ao salvar o membro.");
        }
    }

    private static void atualizarMembro() {
        System.out.print("CPF do Membro a ser atualizado: ");
        String cpf = scanner.nextLine();
        Membro membro = membroDAO.BuscarPorId(cpf);
        if (membro == null) {
            System.out.println("Membro não encontrado.");
            return;
        }

        System.out.print("Novo Nome: ");
        membro.setNome(scanner.nextLine());
        System.out.print("Novo Número de Telefone: ");
        membro.setNumTelefone(scanner.nextLine());
        System.out.print("Novo Email: ");
        membro.setEmail(scanner.nextLine());
        System.out.print("Novo Telefone de Emergência: ");
        membro.setNumEmergencia(scanner.nextLine());

        if (membroDAO.Atualizar(membro) != null) {
            System.out.println("Membro atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar o membro.");
        }
    }

    private static void buscarPorIdcpf() {
        System.out.print("Informe o CPF do Membro: ");
        String cpf = scanner.nextLine();
        Membro membro = membroDAO.BuscarPorId(cpf);
        if (membro == null) {
            System.out.println("Membro não encontrado.");
        } else {
            System.out.println("Membro encontrado: " + membro.getNome());
        }
    }

    private static void deletarcpf() {
        System.out.print("Informe o CPF do Membro para deletar: ");
        String cpf = scanner.nextLine();
        membroDAO.Deletar(cpf);
    }

    private static void listarMembros() {
        List<Membro> membros = membroDAO.ListarMembros();
        if (membros.isEmpty()) {
            System.out.println("Nenhum membro encontrado.");
        } else {
            membros.forEach(membro -> System.out.println(membro.getNome() + " - CPF: " + membro.getCPF()));
        }
    }

    private static void BuscarPorCadastroFamilia() {
        System.out.print("Informe o ID da Família: ");
        int familiaId = scanner.nextInt();
        scanner.nextLine();  // Consumir quebra de linha
        List<Membro> membros = membroDAO.BuscarPorCadastroFamilia(familiaId);
        if (membros.isEmpty()) {
            System.out.println("Nenhum membro encontrado para esta família.");
        } else {
            membros.forEach(membro -> System.out.println(membro.getNome() + " - CPF: " + membro.getCPF()));
        }
    }

    private static void BuscarPorCep() {
        System.out.print("Informe a cidade: ");
        String CEP = scanner.nextLine();
        List<Membro> membros = membroDAO.BuscarPorCep(CEP);
        if (membros.isEmpty()) {
            System.out.println("Nenhum membro encontrado para esta cidade.");
        } else {
            membros.forEach(membro -> System.out.println(membro.getNome() + " - CPF: " + membro.getCPF()));
        }
    }
}
