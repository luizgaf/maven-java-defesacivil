package com.controller;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.model.CadastroFamilia;
import com.model.Membro;
import util.JPAUtil;

import java.util.Collections;
import java.util.List;

public class MembroDAO {

    public Membro Salvar(Membro membro){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        try {
            transacao.begin();
            em.merge(membro);
            transacao.commit();
            return membro;
        }
        catch (Exception ex){
            if(transacao.isActive()){
                transacao.rollback();
            }
            System.err.println("Erro ao salvar membro da familia"+ ex.getMessage());
            return null;
        }finally {
            em.close();
        }
    }
    public Membro Atualizar (Membro membro){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        try {
            transacao.begin();
            em.merge(membro);
            transacao.commit();
            return membro;
        }
        catch (Exception ex){
            if(transacao.isActive()){
                transacao.rollback();
            }
            System.err.println("Erro ao atualizar membro da familia"+ ex.getMessage());
            return null;
        }finally {
            em.close();
        }
    }

    public Membro BuscarPorId (String cpf){
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.find(Membro.class, cpf);
        }
        catch (Exception ex){
            System.err.println("Erro ao buscar id do membro"+ ex.getMessage());
            return null;
        }
    }
    public void Deletar (String cpf){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        try {
            transacao.begin();
            Membro membro = em.find(Membro.class, cpf);
            if(membro != null){
                em.remove(membro);
                System.err.println("Exito ao deletar da familia");
            }
        }
        catch (Exception ex){
            if(transacao.isActive()){
                transacao.rollback();
            }
            System.err.println("Erro ao deletar da familia"+ ex.getMessage());

        }finally {
            em.close();
        }
    }
    public List<Membro> ListarMembros() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("FROM Membro", Membro.class).getResultList();
        } catch (Exception ex) {
            System.err.println("Erro ao listar todos os membros: " + ex.getMessage());
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }
    public List<Membro> BuscarPorCadastroFamilia(int idFamilia) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT m FROM Membro m WHERE m.cadastroFamilia.idFamilia = :idFamilia",
                    Membro.class
            ).setParameter("idFamilia", idFamilia).getResultList();
        } catch (Exception ex) {
            System.err.println("Erro ao buscar membros por cadastro de família: " + ex.getMessage());
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }
    public List<Membro> BuscarPorCep(String cidade) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT m FROM Membro m WHERE m.endereco.cidade = :cidade",
                    Membro.class
            ).setParameter("cidade", cidade).getResultList();
        } catch (Exception ex) {
            System.err.println("Erro ao buscar membros por cidade: " + ex.getMessage());
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }


}
