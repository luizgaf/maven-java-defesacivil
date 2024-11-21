package com.controller;

import com.model.TipoRisco;
import util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Collections;
import java.util.List;

public class TipoRiscoDAO {

    private boolean validarTipoRisco(TipoRisco tipoRisco) {
        if (tipoRisco == null) {
            System.err.println("O tipo de risco não pode ser nulo.");
            return false;
        }
        return true;
    }

    public TipoRisco salvar(TipoRisco tipoRisco) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        if (!validarTipoRisco(tipoRisco)) {
            System.err.println("Tipo de risco inválido. Operação cancelada.");
            return null;
        }
        try {
            transacao.begin();
            em.merge(tipoRisco);
            transacao.commit();
            return tipoRisco;
        } catch (Exception ex) {
            if (transacao.isActive()) {
                transacao.rollback();
            }
            System.err.println("Erro ao salvar tipo de risco: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public TipoRisco atualizar(TipoRisco tipoRisco) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        if (!validarTipoRisco(tipoRisco)) {
            System.err.println("Tipo de risco inválido. Operação cancelada.");
            return null;
        }
        try {
            transacao.begin();
            em.merge(tipoRisco);
            transacao.commit();
            return tipoRisco;
        } catch (Exception ex) {
            if (transacao.isActive()) {
                transacao.rollback();
            }
            System.err.println("Erro ao atualizar tipo de risco: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public TipoRisco buscarPorId(int riscoId) {
        EntityManager em = JPAUtil.getEntityManager();

        if (riscoId <= 0) {
            System.err.println("ID inválido. Deve ser maior que zero.");
            return null;
        }
        try {
            return em.find(TipoRisco.class, riscoId);
        } catch (Exception ex) {
            System.err.println("Erro ao buscar tipo de risco por ID: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public void deletar(int riscoId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        if (riscoId <= 0) {
            System.err.println("ID inválido. Deve ser maior que zero.");
            return;
        }
        try {
            transacao.begin();
            TipoRisco tipoRisco = em.find(TipoRisco.class, riscoId);
            if (tipoRisco != null) {
                em.remove(tipoRisco);
                transacao.commit();
                System.out.println("Tipo de risco deletado com sucesso.");
            } else {
                System.err.println("Tipo de risco não encontrado para exclusão.");
            }
        } catch (Exception ex) {
            if (transacao.isActive()) {
                transacao.rollback();
            }
            System.err.println("Erro ao deletar tipo de risco: " + ex.getMessage());
        } finally {
            em.close();
        }
    }

    public List<TipoRisco> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("FROM TipoRisco", TipoRisco.class).getResultList();
        } catch (Exception ex) {
            System.err.println("Erro ao listar tipos de risco: " + ex.getMessage());
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }
}
