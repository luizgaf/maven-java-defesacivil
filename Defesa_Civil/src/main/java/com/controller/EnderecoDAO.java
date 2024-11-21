package com.controller;

import com.model.Endereco;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class EnderecoDAO {

    private boolean validarEndereco(Endereco endereco) {
        if (endereco.getCEP() == null || !endereco.getCEP().matches("\\d{8}")) {
            System.err.println("CEP inválido. Deve conter exatamente 8 números.");
            return false;
        }
        if (endereco.getNumero() <= 0) {
            System.err.println("Número do endereço deve ser maior que zero.");
            return false;
        }

        return true;
    }

    public Endereco Salvar(Endereco endereco) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        if (!validarEndereco(endereco)) {
            return null;
        }

        try {
            transacao.begin();
            em.merge(endereco);
            transacao.commit();
            return endereco;
        } catch (Exception ex) {
            if (transacao.isActive()) {
                transacao.rollback();
            }
            System.err.println("Erro ao salvar endereço: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public Endereco Atualizar(Endereco endereco) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        if (!validarEndereco(endereco)) {
            return null;
        }

        try {
            transacao.begin();
            em.merge(endereco);
            transacao.commit();
            return endereco;
        } catch (Exception ex) {
            if (transacao.isActive()) {
                transacao.rollback();
            }
            System.err.println("Erro ao atualizar endereço: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public Endereco BuscarPorID(int idEndereco) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.find(Endereco.class, idEndereco);
        } catch (Exception ex) {
            System.err.println("Erro ao buscar endereço por ID: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public void Deletar(int idEndereco) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        try {
            if (idEndereco <= 0) {
                System.err.println("ID inválido para deleção.");
                return;
            }

            transacao.begin();
            Endereco endereco = em.find(Endereco.class, idEndereco);
            if (endereco != null) {
                em.remove(endereco);
                transacao.commit();
            } else {
                System.err.println("Endereço não encontrado para o ID: " + idEndereco);
            }
        } catch (Exception ex) {
            if (transacao.isActive()) {
                transacao.rollback();
            }
            System.err.println("Erro ao deletar endereço: " + ex.getMessage());
        } finally {
            em.close();
        }
    }

    public List<Endereco> ListarEndereco() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            String jpql = "SELECT e FROM Endereco e";
            TypedQuery<Endereco> query = em.createQuery(jpql, Endereco.class);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao listar endereços: " + e.getMessage());
            return List.of(); // Retorna lista vazia
        } finally {
            em.close();
        }
    }

    public Endereco PreencherEnderecoPorCep(String cep) {
        String apiUrl = "https://viacep.com.br/ws/" + cep + "/json/";

        if (cep == null || !cep.matches("\\d{8}")) {
            System.err.println("CEP inválido. Deve conter exatamente 8 números.");
            return null;
        }

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setRequestProperty("Accept", "application/json");

            if (conexao.getResponseCode() == 200) {
                InputStreamReader leitor = new InputStreamReader(conexao.getInputStream());
                JsonObject json = JsonParser.parseReader(leitor).getAsJsonObject();

                if (!json.has("logradouro") || !json.has("localidade")) {
                    System.err.println("Resposta da API incompleta ou inválida.");
                    return null;
                }

                Endereco endereco = new Endereco();
                endereco.setLogradouro(json.get("logradouro").getAsString());
                endereco.setCidade(json.get("localidade").getAsString());
                endereco.setCEP(json.get("cep").getAsString());
                endereco.setComplemento(json.has("complemento") ? json.get("complemento").getAsString() : null);

                return endereco;
            } else {
                System.err.println("Erro ao consultar o CEP: Código " + conexao.getResponseCode());
                return null;
            }
        } catch (Exception e) {
            System.err.println("Erro ao consultar o CEP: " + e.getMessage());
            return null;
        }
    }

    public Endereco SalvarEnderecoComCep(String cep, Endereco enderecoParcial) {
        Endereco enderecoPreenchido = PreencherEnderecoPorCep(cep);

        if (enderecoPreenchido != null) {
            if (!cep.equals(enderecoPreenchido.getCEP())) {
                System.err.println("O CEP retornado pela API não corresponde ao informado.");
                return null;
            }

            enderecoPreenchido.setNumero(enderecoParcial.getNumero());
            enderecoPreenchido.setComplemento(enderecoParcial.getComplemento());

            return Salvar(enderecoPreenchido);
        } else {
            System.err.println("Não foi possível preencher o endereço automaticamente.");
            return null;
        }
    }
}
