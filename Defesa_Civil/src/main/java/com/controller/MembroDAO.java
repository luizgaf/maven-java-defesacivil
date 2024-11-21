package com.controller;

import com.model.CadastroFamilia;
import com.model.Membro;
import com.model.Endereco;
import util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class MembroDAO {


    private boolean validarCPF(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            System.err.println("CPF inválido. Deve conter exatamente 11 números.");
            return false;
        }
        return true;
    }
    private boolean validarNumTelefone(String numTelefone) {
        if (numTelefone == null || !numTelefone.matches("\\d{11}")) {
            System.err.println("Número de telefone inválido. Deve conter exatamente 15 números.");
            return false;
        }
        return true;
    }

    private boolean validarTelEmergencia(String numEmergencia) {
        if (numEmergencia == null || !numEmergencia.matches("\\d{11}")) {
            System.err.println("Número de telefone inválido. Deve conter exatamente 15 números.");
            return false;
        }
        return true;
    }
    private boolean validarDataNasc(LocalDate DataNasc) {
        if (DataNasc == null) {
            System.err.println("Data de nascimento não pode ser nula.");
            return false;
        }
        return true;
    }
    private boolean validarEmail(String email) {
        if (email == null) {
            System.err.println("Email não pode ser nula.");
            return false;
        }
        return true;
    }

    private boolean validarNome(String nome) {
        if (nome == null || nome.trim().length() < 2) {
            System.err.println("Nome inválido. Deve conter pelo menos 2 caracteres.");
            return false;
        }
        return true;
    }

    private boolean validarCadastroFamilia(CadastroFamilia cadastroFamilia) {
        if (cadastroFamilia == null || cadastroFamilia.getIdFamilia() <= 0) {
            System.err.println("Cadastro de família inválido ou inexistente.");
            return false;
        }
        return true;
    }

    private boolean validarEndereco(Endereco endereco) {
        if (endereco == null) {
            System.err.println("Endereço não pode ser nulo.");
            return false;
        }
        return true;
    }

    private boolean verificarDuplicidadeCPF(String cpf) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(m) FROM Membro m WHERE m.cpf = :cpf", Long.class
            ).setParameter("cpf", cpf).getSingleResult();
            return count > 0;
        } catch (Exception ex) {
            System.err.println("Erro ao verificar duplicidade de CPF: " + ex.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    private boolean validarMembro(Membro membro) {
        if (membro == null) {
            System.err.println("Membro não pode ser nulo.");
            return false;
        }
        if (!validarCPF(membro.getCPF())) return false;
        if (!validarNome(membro.getNome())) return false;
//        if (!validarCadastroFamilia(membro.getCadastroFamilia())) return false;
        if (!validarEndereco(membro.getEndereco())) return false;
        if (!validarEmail(membro.getEmail())) return false;
//        if (!validarDataNasc(membro.getDataNasc())) return false;
        if (!validarNumTelefone(membro.getNumTelefone())) return false;
        if (!validarTelEmergencia(membro.getNumEmergencia())) return false;
        if (verificarDuplicidadeCPF(membro.getCPF())) {
            System.err.println("CPF já cadastrado para outro membro.");
            return false;
        }
        return true;
    }


    public Membro Salvar(Membro membro) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        if (!validarMembro(membro)) {
            return null;
        }
        try {
            transacao.begin();
            em.merge(membro);
            transacao.commit();
            return membro;
        } catch (Exception ex) {
            if (transacao.isActive()) {
                transacao.rollback();
            }
            System.err.println("Erro ao salvar membro da família: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public Membro Atualizar(Membro membro) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        if (!validarMembro(membro)) {
            return null;
        }
        try {
            transacao.begin();
            em.merge(membro);
            transacao.commit();
            return membro;
        } catch (Exception ex) {
            if (transacao.isActive()) {
                transacao.rollback();
            }
            System.err.println("Erro ao atualizar membro da família: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public Membro BuscarPorId(String cpf) {
        EntityManager em = JPAUtil.getEntityManager();

        if (cpf == null || !cpf.matches("\\d{11}")) {
            System.err.println("CPF inválido para busca.");
            return null;
        }
        try {
            return em.find(Membro.class, cpf);
        } catch (Exception ex) {
            System.err.println("Erro ao buscar membro pelo CPF: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public void Deletar(String cpf) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transacao = em.getTransaction();

        if (cpf == null || !cpf.matches("\\d{11}")) {
            System.err.println("CPF inválido para deleção.");
            return;
        }
        try {
            transacao.begin();
            Membro membro = em.find(Membro.class, cpf);
            if (membro != null) {
                em.remove(membro);
                transacao.commit();
                System.out.println("Membro deletado com sucesso.");
            } else {
                System.err.println("Membro não encontrado para o CPF informado.");
            }
        } catch (Exception ex) {
            if (transacao.isActive()) {
                transacao.rollback();
            }
            System.err.println("Erro ao deletar membro: " + ex.getMessage());
        } finally {
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
