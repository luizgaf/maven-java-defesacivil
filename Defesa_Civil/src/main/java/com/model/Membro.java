package com.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "membro")
public class Membro {

    @Id
    @Column(name = "CPF", unique = true)
    private String CPF;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "numTelefone", nullable = false)
    private String numTelefone;

    @Column(name = "dataNasc", nullable = true)
    private LocalDate DataNasc;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "telEmergencia", nullable = false)
    private String numEmergencia;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Endereco_id", referencedColumnName = "idEndereco")
    private Endereco endereco;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CadastroFamilia_id", referencedColumnName = "idFamilia", nullable = true)
    private CadastroFamilia cadastroFamilia;

    // Novo atributo para armazenar a foto
    @Lob
    @Column(name = "foto")
    private byte[] foto;

    public Membro() {}

    public Membro(String CPF, String nome, String numTelefone, LocalDate DataNasc, String email, String numEmergencia, Endereco endereco, CadastroFamilia cadastroFamilia, byte[] foto) {
        this.CPF = CPF;
        this.nome = nome;
        this.numTelefone = numTelefone;
        this.DataNasc = DataNasc;
        this.email = email;
        this.numEmergencia = numEmergencia;
        this.endereco = endereco;
        this.cadastroFamilia = cadastroFamilia;
        this.foto = foto;
    }

    // Getters e Setters
    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumTelefone() {
        return numTelefone;
    }

    public void setNumTelefone(String numTelefone) {
        this.numTelefone = numTelefone;
    }

    public LocalDate getDataNasc() {
        return DataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        DataNasc = dataNasc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumEmergencia() {
        return numEmergencia;
    }

    public void setNumEmergencia(String numEmergencia) {
        this.numEmergencia = numEmergencia;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public CadastroFamilia getCadastroFamilia() {
        return cadastroFamilia;
    }

    public void setCadastroFamilia(CadastroFamilia cadastroFamilia) {
        this.cadastroFamilia = cadastroFamilia;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
