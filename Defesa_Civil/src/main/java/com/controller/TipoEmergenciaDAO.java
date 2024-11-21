package com.controller;

import com.model.TipoEmergencia;
import com.model.TipoRisco;
import util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Collections;
import java.util.List;

public class TipoEmergenciaDAO {

    private boolean validarTipoEmergencia(TipoEmergencia tipoEmergencia) {
        if (tipoEmergencia == null) {
            System.err.println("O tipo de emergência não pode ser nulo.");
            return false;
        }

        return true;
    }

    public TipoEmergencia salvar(TipoEmergencia tipoEmergencia) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        if (!validarTipoEmergencia(tipoEmergencia)) {
            System.err.println("Tipo de emergência inválido. Operação cancelada.");
            return null;
        }
        try {
            transacao.begin();
            em.merge(tipoEmergencia);
            transacao.commit();
            return tipoEmergencia;
        } catch (Exception ex) {
            if (transacao.isActive()) {
                transacao.rollback();
            }
            System.err.println("Erro ao salvar tipo de emergência: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public TipoEmergencia atualizar(TipoEmergencia tipoEmergencia) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        if (!validarTipoEmergencia(tipoEmergencia)) {
            System.err.println("Tipo de emergência inválido. Operação cancelada.");
            return null;
        }
        try {
            transacao.begin();
            em.merge(tipoEmergencia);
            transacao.commit();
            return tipoEmergencia;
        } catch (Exception ex) {
            if (transacao.isActive()) {
                transacao.rollback();
            }
            System.err.println("Erro ao atualizar tipo de emergência: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public TipoEmergencia buscarPorId(int idEmergencia) {
        EntityManager em = JPAUtil.getEntityManager();

        if (idEmergencia <= 0) {
            System.err.println("ID inválido. Deve ser maior que zero.");
            return null;
        }
        try {
            return em.find(TipoEmergencia.class, idEmergencia);
        } catch (Exception ex) {
            System.err.println("Erro ao buscar tipo de emergência por ID: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }


    public void deletar(int idEmergencia) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        if (idEmergencia <= 0) {
            System.err.println("ID inválido. Deve ser maior que zero.");
            return;
        }
        try {
            transacao.begin();
            TipoEmergencia tipoEmergencia = em.find(TipoEmergencia.class, idEmergencia);
            if (tipoEmergencia != null) {
                em.remove(tipoEmergencia);
                transacao.commit();
                System.out.println("Tipo de emergência deletado com sucesso.");
            } else {
                System.err.println("Tipo de emergência não encontrado para exclusão.");
            }
        } catch (Exception ex) {
            if (transacao.isActive()) {
                transacao.rollback();
            }
            System.err.println("Erro ao deletar tipo de emergência: " + ex.getMessage());
        } finally {
            em.close();
        }
    }

    public List<TipoEmergencia> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("FROM TipoEmergencia", TipoEmergencia.class).getResultList();
        } catch (Exception ex) {
            System.err.println("Erro ao listar tipos de emergência: " + ex.getMessage());
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }
}
