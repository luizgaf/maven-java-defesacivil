package com.controller;

import com.model.CadastroFamilia;
import util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Collections;
import java.util.List;

public class CadastroFamiliaDAO {

    public CadastroFamilia Salvar(CadastroFamilia cf){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        try {
            transacao.begin();
            em.merge(cf);
            transacao.commit();
            return cf;
        }
        catch (Exception ex){
            if(transacao.isActive()){
                transacao.rollback();
            }
            System.err.println("Erro ao salvar Cadastro da familia"+ ex.getMessage());
            return cf;
        }finally {
            em.close();
        }
    }
    public CadastroFamilia Atualizar(CadastroFamilia cf){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        try {
            transacao.begin();
            em.merge(cf);
            transacao.commit();
            return cf;
        }
        catch (Exception ex){
            if(transacao.isActive()){
                transacao.rollback();
            }
            System.err.println("Erro ao atualizar cadastro da familia"+ ex.getMessage());
            return null;
        }finally {
            em.close();
        }
    }
    public CadastroFamilia BuscarPorId(int idFamilia) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.find(CadastroFamilia.class, idFamilia);
        } catch (Exception ex) {
            System.err.println("Erro ao buscar o cadastro da família por ID: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }


    public boolean Deletar(int idFamilia) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        try {
            transacao.begin();
            CadastroFamilia cf = em.find(CadastroFamilia.class, idFamilia);
            if (cf != null) {
                em.remove(cf);
                transacao.commit();
                return true;
            } else {
                System.err.println("Cadastro da família não encontrado.");
                return false;
            }
        } catch (Exception ex) {
            if (transacao.isActive()) {
                transacao.rollback();
            }
            System.err.println("Erro ao deletar cadastro da família: " + ex.getMessage());
            return false;
        } finally {
            em.close();
        }
    }


    public List<CadastroFamilia> ListarCadastroFamilia() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("FROM CadastroFamilia", CadastroFamilia.class).getResultList();
        } catch (Exception ex) {
            System.err.println("Erro ao listar todos os cadastros de família: " + ex.getMessage());
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }


    public List<CadastroFamilia> BuscarPorTipoRisco(int idRisco) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT cf FROM CadastroFamilia cf WHERE cf.tipoRisco.idRisco = :idRisco",
                    CadastroFamilia.class
            ).setParameter("idRisco", idRisco).getResultList();
        } catch (Exception ex) {
            System.err.println("Erro ao buscar cadastros por tipo de risco: " + ex.getMessage());
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }


    public List<CadastroFamilia> BuscarPorTipoEmergencia(int idEmergencia) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT cf FROM CadastroFamilia cf WHERE cf.tipoEmergencia.idEmergencia = :idEmergencia",
                    CadastroFamilia.class
            ).setParameter("idEmergencia", idEmergencia).getResultList();
        } catch (Exception ex) {
            System.err.println("Erro ao buscar cadastros por tipo de emergência: " + ex.getMessage());
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }


    public List<CadastroFamilia> ListarComPaginacao(int offset, int limite) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("FROM CadastroFamilia", CadastroFamilia.class)
                    .setFirstResult(offset) // Início
                    .setMaxResults(limite) // Quantidade
                    .getResultList();
        } catch (Exception ex) {
            System.err.println("Erro ao listar cadastros com paginação: " + ex.getMessage());
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }


    public boolean ExistePorId(int idFamilia) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            CadastroFamilia cf = em.find(CadastroFamilia.class, idFamilia);
            return cf != null;
        } finally {
            em.close();
        }
    }


    public List<CadastroFamilia> BuscarPorDescricao(String descricao) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT cf FROM CadastroFamilia cf WHERE cf.descricao LIKE :descricao",
                    CadastroFamilia.class
            ).setParameter("descricao", "%" + descricao + "%").getResultList();
        } catch (Exception ex) {
            System.err.println("Erro ao buscar cadastros por descrição: " + ex.getMessage());
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }
}
