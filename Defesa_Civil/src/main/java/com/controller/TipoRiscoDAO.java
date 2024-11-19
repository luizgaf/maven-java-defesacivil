package com.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.model.TipoRisco;
import util.JPAUtil;

public class TipoRiscoDAO {

    public TipoRisco Salvar(TipoRisco tipoRisco){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        try {
            transacao.begin();
            em.merge(tipoRisco);
            transacao.commit();
            return tipoRisco;
        }
        catch (Exception ex){
            if (transacao.isActive()){
                transacao.rollback();
            }
            System.err.println("Erro ao salvar tipo de risco:"+ ex.getMessage());
            return null;
        }finally {
            em.close();
        }

    }
    public TipoRisco BuscarPorId(int riscoId){
        EntityManager em = JPAUtil.getEntityManager();

        try{
            return em.find(TipoRisco.class, riscoId);
        }
        catch (Exception ex){
            System.err.println("Erro ao buscar tipo de risco por ID:"+ ex.getMessage());
            return null;
        }finally {
            em.close();
        }
    }
    public TipoRisco Atualizar(TipoRisco tipoRisco){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        try {
            transacao.begin();
            em.merge(tipoRisco);
            transacao.commit();
            return tipoRisco;
        }
        catch (Exception ex){
            System.err.println("Erro ao atualizar tipo de risco:"+ ex.getMessage());
            return null;
        }finally {
            em.close();
        }
    }
    public void Deletar(int riscoId){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        try {
            transacao.begin();
            TipoRisco tipoRisco = em.find(TipoRisco.class, riscoId);
            if(tipoRisco != null){
                em.remove(tipoRisco);
            }
            transacao.commit();
        }
        catch (Exception ex){
            if (transacao.isActive()){
                transacao.rollback();
            }
            System.err.println("Erro ao deletar tipo de risco:"+ ex.getMessage());
        }finally {
            em.close();
        }
    }
}

