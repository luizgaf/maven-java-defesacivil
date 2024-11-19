package com.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.model.TipoEmergencia;
import util.JPAUtil;

public class TipoEmergenciaDAO {
    public TipoEmergencia Salvar(TipoEmergencia tipomergencia){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        try {
            transacao.begin();
            em.merge(tipomergencia);
            transacao.commit();
            return tipomergencia;
        }
        catch (Exception ex){
            if (transacao.isActive()){
                transacao.rollback();
            }
            System.err.println("Erro ao salvar tipo de emergencia:"+ ex.getMessage());
            return null;
        }finally {
            em.close();
        }

    }
    public TipoEmergencia BuscarPorId(int idEmergencia){
        EntityManager em = JPAUtil.getEntityManager();

        try{
            return em.find(TipoEmergencia.class, idEmergencia);
        }
        catch (Exception ex){
            System.err.println("Erro ao buscar tipo de emergencia por ID:"+ ex.getMessage());
            return null;
        }finally {
            em.close();
        }
    }
    public TipoEmergencia Atualizar(TipoEmergencia tipomergencia){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        try {
            transacao.begin();
            em.merge(tipomergencia);
            transacao.commit();
            return tipomergencia;
        }
        catch (Exception ex){
            System.err.println("Erro ao atualizar tipo de emergencia:"+ ex.getMessage());
            return null;
        }finally {
            em.close();
        }
    }
    public void Deletar(int idEmergencia){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        try {
            transacao.begin();
            TipoEmergencia tipomergencia = em.find(TipoEmergencia.class, idEmergencia);
            if(tipomergencia != null){
                em.remove(tipomergencia);
            }
            transacao.commit();
        }
        catch (Exception ex){
            if (transacao.isActive()){
                transacao.rollback();
            }
            System.err.println("Erro ao deletar tipo de emergencia:"+ ex.getMessage());
        }finally {
            em.close();
        }
    }
}
