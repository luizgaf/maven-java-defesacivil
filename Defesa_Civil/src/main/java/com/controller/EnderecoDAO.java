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

    public Endereco Salvar (Endereco endereco){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        try {
            transacao.begin();
            em.merge(endereco);
            transacao.commit();
            return endereco;
        }
        catch (Exception ex){
            if (transacao.isActive()){
                transacao.rollback();
            }
            System.err.println("Erro ao salvar endereço do membro da familia"+ ex.getMessage());
            return null;
        }finally {
            em.close();
        }
    }
    public Endereco Atualiazar (Endereco endereco){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        try {
            transacao.begin();
            em.merge(endereco);
            transacao.commit();
            return endereco;
        }
        catch (Exception ex){
            if (transacao.isActive()){
                transacao.rollback();
            }
            System.err.println("Erro ao buscar id do enderço"+ ex.getMessage());
            return null;
        }finally {
            em.close();
        }
    }

    public Endereco BuscarPorID (int idEndereco){
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.find(Endereco.class, idEndereco);
        }
        catch (Exception ex){
            System.err.println("Erro ao buscar id do enderço"+ ex.getMessage());
            return null;
        }finally {
            em.close();
        }
    }



    public void Deletar(int idEndereco){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        try {
            transacao.begin();
            Endereco endereco = em.find(Endereco.class, idEndereco);
            if (endereco != null){
                em.remove(endereco);
            }
        }
        catch (Exception ex){
            if(transacao.isActive()){
                transacao.rollback();
            }
            System.err.println("Erro ao Deletar id do enderço"+ ex.getMessage());
        }finally {
            em.close();
        }
    }
    public List<Endereco> ListarEndereco(){
        EntityManager em = JPAUtil.getEntityManager();

        try {
            String jpql = "SELECT e FROM Endereco e";
            TypedQuery<Endereco> query = em.createQuery(jpql, Endereco.class);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao listar Endereços: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }
    public Endereco PreencherEnderecoPorCep(String cep) {
        String apiUrl = "https://viacep.com.br/ws/" + cep + "/json/";

        try {

            URL url = new URL(apiUrl);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setRequestProperty("Accept", "application/json");

            if (conexao.getResponseCode() == 200) { // Sucesso

                InputStreamReader leitor = new InputStreamReader(conexao.getInputStream());
                JsonObject json = JsonParser.parseReader(leitor).getAsJsonObject();


                Endereco endereco = new Endereco();
                endereco.setLogradouro(json.get("logradouro").getAsString());
                endereco.setCidade(json.get("localidade").getAsString());
                endereco.setCEP(json.get("cep").getAsString());
                endereco.setComplemento(json.has("complemento") ? json.get("complemento").getAsString() : null);

                return endereco; // Retorna o endereço preenchido
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

            enderecoPreenchido.setNumero(enderecoParcial.getNumero());
            enderecoPreenchido.setComplemento(enderecoParcial.getComplemento());


            return Salvar(enderecoPreenchido);
        } else {
            System.err.println("Não foi possível preencher o endereço automaticamente.");
            return null;
        }
    }
}
