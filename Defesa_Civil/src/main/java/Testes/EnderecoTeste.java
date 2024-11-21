package Testes;
import com.controller.*;
import com.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.List;


public class EnderecoTeste {
    public static void main(String[] args){
        Endereco endereco = new Endereco();
        EnderecoDAO enderecoDAO = new EnderecoDAO();

        Scanner input = new Scanner(System.in);


        System.out.println("Digite seu CEP");
        Endereco enderecoretornado = enderecoDAO.PreencherEnderecoPorCep(input.nextLine());

        enderecoretornado.setCEP(enderecoretornado.getCEP().replace("-", ""));
        System.out.println("ID: " + enderecoretornado.getIdEndereco() +
                ", CEP: " + enderecoretornado.getCEP() +
                ", Cidade: " + enderecoretornado.getCidade() +
                ", Logradouro: " + enderecoretornado.getLogradouro() +
                ", Numero: " + enderecoretornado.getNumero()+
                ", Complemento: " + enderecoretornado.getComplemento() );



        System.out.println("Digite o numero da rua");
        enderecoretornado.setNumero(input.nextInt());
        input.nextLine();

        System.out.println("Digite o complemento");
        enderecoretornado.setComplemento(input.nextLine());

        /*





        System.out.println("Digite o logradouro");
        endereco.setLogradouro(input.nextLine());

        System.out.println("Digite sua cidade");
        endereco.setCidade(input.nextLine());

*/

      /*  Endereco enderecoSalvo = enderecoDAO.Salvar(endereco);


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
                System.out.println("ID: " + enderecoretornado.getIdEndereco() +
                        ", CEP: " + enderecoretornado.getCEP() +
                        ", Cidade: " + enderecoretornado.getCidade() +
                        ", Logradouro: " + enderecoretornado.getLogradouro() +
                        ", Numero: " + enderecoretornado.getNumero()+
                        ", Complemento: " + enderecoretornado.getComplemento() );

            }
        } else {
            System.out.println("Nenhum endereço encontrado em ");
        }
        CadastroFamilia cadastroFamilia = new CadastroFamilia();
        CadastroFamiliaDAO cadastroFamiliaDAO = new CadastroFamiliaDAO();

        System.out.println("Digite o nome da familia");
        cadastroFamilia.setNomeFamilia(input.nextLine());

        TipoRiscoDAO tipoRisco = new TipoRiscoDAO();
        List<TipoRisco> riscos = tipoRisco.listarTodos();

        if (riscos != null && !riscos.isEmpty()) {
            System.out.println("Riscos encontrados ");
            for (TipoRisco risco : riscos) {
                System.out.println("ID: " + risco.getIdRisco() +
                        ", Categoria: " + risco.getCategoria()+"\n");

            }
        } else {
            System.out.println("Nenhum risco encontrado");
        }

        System.out.println("Digite o tipo de risco");
        cadastroFamilia.setTipoRisco(tipoRisco.buscarPorId(input.nextInt()));
        input.nextLine();

        TipoEmergenciaDAO tipoEmergencia = new TipoEmergenciaDAO();
        List<TipoEmergencia> emergencias = tipoEmergencia.listarTodos();
        if (emergencias != null && !emergencias.isEmpty()) {
            System.out.println("Emergencias encontrados ");
            for (TipoEmergencia emergencia : emergencias) {
                System.out.println("ID: " + emergencia.getIdEmergencia() +
                        ", Categoria: " + emergencia.getCategoria()+"\n");

            }
        } else {
            System.out.println("Nenhuma emergencia encontrado");
        }

        System.out.println("Digite o tipo de emergencia");
        cadastroFamilia.setTipoEmergencia(tipoEmergencia.buscarPorId(input.nextInt()));
        input.nextLine();

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



//        System.out.println("Digite a data de nascimento");
//        membro.setDataNasc(LocalDate.parse(input.nextLine()));
//        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("Endereço" + enderecoretornado.getCEP() + " , " + enderecoretornado.getLogradouro() + " , " + enderecoretornado.getNumero());
        membro.setEndereco(enderecoretornado);

        input.close();




        membro.setCadastroFamilia(cadastroFamilia);
        Membro membroSalva = membroDAO.Salvar(membro);




        if (cadastroFamilia != null) {
            System.out.println("Familia salva com sucesso!");
            System.out.println("Familia salva: " + cadastroFamilia.getNomeFamilia());
        } else {
            System.err.println("Erro ao salvar Familia");
        }

    }
}

