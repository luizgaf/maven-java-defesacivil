package Testes;
import com.controller.EnderecoDAO;
import com.controller.MembroDAO;
import com.model.Endereco;
import com.model.Membro;

import java.util.Scanner;
import java.util.List;


public class EnderecoTeste {
    public static void main(String[] args){
        Endereco endereco = new Endereco();
        EnderecoDAO enderecoDAO = new EnderecoDAO();

        Scanner input = new Scanner(System.in);

        System.out.println("Digite seu CEP");
        endereco.setCEP(input.nextLine());


        System.out.println("Digite o complemento");
        endereco.setComplemento(input.nextLine());


        System.out.println("Digite o numero da rua");
        endereco.setNumero(input.nextInt());
        input.nextLine();

        System.out.println("Digite o logradouro");
        endereco.setLogradouro(input.nextLine());

        System.out.println("Digite sua cidade");
        endereco.setCidade(input.nextLine());


        /*Endereco enderecoSalvo = enderecoDAO.Salvar(endereco);

        if (enderecoSalvo != null) {
            System.out.println("Endereço salvo com sucesso!");
            System.out.println("Endereço salvo: " + enderecoSalvo.getLogradouro());
        } else {
            System.err.println("Erro ao salvar Endereço");
        }
        */


        List<Endereco> enderecos = enderecoDAO.ListarEndereco();

        if (enderecos != null && !enderecos.isEmpty()) {
            System.out.println("Endereços encontrados ");
            for (Endereco enderecoParaListagem : enderecos) {
                System.out.println("ID: " + enderecoParaListagem.getIdEndereco() +
                        ", Logradouro: " + enderecoParaListagem.getLogradouro() +
                        ", Cidade: " + enderecoParaListagem.getCidade() +
                        ", Complemento: " + enderecoParaListagem.getComplemento() +
                        ", CEP: " + enderecoParaListagem.getCEP() +
                        ", Numero: " + enderecoParaListagem.getNumero());
            }
        } else {
            System.out.println("Nenhum endereço encontrado em ");
        }

        Membro membro = new Membro();
        MembroDAO membroDAO = new MembroDAO();


        System.out.println("Digite seu CPF");
        membro.setCPF(input.nextLine());


        System.out.println("Digite o nome");
        membro.setNome(input.nextLine());


        System.out.println("Digite o numero de telefone");
        membro.setNumTelefone(input.nextLine());


        System.out.println("Digite o Email");
        membro.setEmail(input.nextLine());

        System.out.println("Digite um contato de emergencia");
        membro.setNumEmergencia(input.nextLine());

        /*
        System.out.println("Digite a data de nascimento");
        membro.setDataNasc(input.nextLine());
        */
        System.out.println("Endereço" + endereco.getCEP() + " , " + endereco.getLogradouro() + " , " + endereco.getNumero());
        membro.setEndereco(endereco);

        input.close();
        Membro membroSalvo = membroDAO.Salvar(membro);

        if (membroSalvo != null) {
            System.out.println("membro salvo com sucesso!");
            System.out.println("Membro salvo: " + membroSalvo.getNome());
        } else {
            System.err.println("Erro ao salvar membro");
        }

    }
}

