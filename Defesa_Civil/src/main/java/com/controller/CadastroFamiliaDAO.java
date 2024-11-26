package com.controller;

import com.model.CadastroFamilia;
import com.model.TipoEmergencia;
import com.model.TipoRisco;
import com.model.Endereco;
import util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Collections;
import java.util.List;

public class CadastroFamiliaDAO {


    private boolean validarCadastroFamilia(CadastroFamilia cf) {
        if (cf == null) {
            System.err.println("Cadastro de família não pode ser nulo.");
            return false;
        }
        if (cf.getNomeFamilia() == null || cf.getNomeFamilia().trim().isEmpty()) {
            System.err.println("O nome da família é obrigatório.");
            return false;
        }
        if (!validarTipoRisco(cf.getTipoRisco().getIdRisco())) {
            System.err.println("Tipo de risco inválido ou inexistente.");
            return false;
        }
        if (!validarTipoEmergencia(cf.getTipoEmergencia().getIdEmergencia())) {
            System.err.println("Tipo de emergência inválido ou inexistente.");
            return false;
        }
        return true;
    }

    private boolean validarTipoRisco(int idRisco) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(TipoRisco.class, idRisco) != null;
        } finally {
            em.close();
        }
    }

    private boolean validarTipoEmergencia(int idEmergencia) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(TipoEmergencia.class, idEmergencia) != null;
        } finally {
            em.close();
        }
    }


    public CadastroFamilia Salvar(CadastroFamilia cf) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        if (!validarCadastroFamilia(cf)) {
            return null;
        }
        try {
            transacao.begin();
            CadastroFamilia fam = em.merge(cf);
            transacao.commit();
            return fam;
        } catch (Exception ex) {
            if (transacao.isActive()) {
                transacao.rollback();
            }
            System.err.println("Erro ao salvar Cadastro da família: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public CadastroFamilia Atualizar(CadastroFamilia cf) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        if (!validarCadastroFamilia(cf)) {
            return null;
        }
        try {
            transacao.begin();
            em.merge(cf);
            transacao.commit();
            return cf;
        } catch (Exception ex) {
            if (transacao.isActive()) {
                transacao.rollback();
            }
            System.err.println("Erro ao atualizar cadastro da família: " + ex.getMessage());
            return null;
        } finally {
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

    public CadastroFamilia BuscarPorNome(String nomeFamilia) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            String jpql = "SELECT c FROM CadastroFamilia c WHERE c.nome = :nomeFamilia";
            List<CadastroFamilia> resultados = em.createQuery(jpql, CadastroFamilia.class)
                    .setParameter("nomeFamilia", nomeFamilia)
                    .getResultList();

            if (resultados.isEmpty()) {
                return null;
            } else {
                return resultados.get(0);
            }
        } catch (Exception ex) {
            System.err.println("Erro ao buscar o cadastro da família por nome: " + ex.getMessage());
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
        if (offset < 0 || limite <= 0) {
            throw new IllegalArgumentException("Offset e limite devem ser valores positivos.");
        }

        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("FROM CadastroFamilia", CadastroFamilia.class)
                    .setFirstResult(offset)
                    .setMaxResults(limite)
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
}
