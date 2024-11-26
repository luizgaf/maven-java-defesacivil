package com.model;

import javax.persistence.*;

@Entity
@Table(name = "CadastroFamilia")
public class CadastroFamilia {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFamilia")
    private int idFamilia;

    @Column(name = "nomeFamilia", nullable = false, length = 100)
    private String nomeFamilia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TipoRisco_id", referencedColumnName = "idRisco", nullable = false) // Definindo a chave estrangeira
    private TipoRisco tipoRisco;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TipoEmergencia_id", referencedColumnName = "idEmergencia", nullable = false) // Definindo a chave estrangeira
    private TipoEmergencia tipoEmergencia;


    public CadastroFamilia() {}

    public CadastroFamilia(int idFamilia, String nomeFamilia, TipoRisco tipoRisco, TipoEmergencia tipoEmergencia) {
        this.idFamilia = idFamilia;
        this.nomeFamilia = nomeFamilia;
        this.tipoRisco = tipoRisco;
        this.tipoEmergencia = tipoEmergencia;

    }

    public int getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(int idFamilia) {
        this.idFamilia = idFamilia;
    }

    public String getNomeFamilia() {
        return nomeFamilia;
    }

    public void setNomeFamilia(String nomeFamilia) {
        this.nomeFamilia = nomeFamilia;
    }

    public TipoRisco getTipoRisco() {
        return tipoRisco;
    }

    public void setTipoRisco(TipoRisco tipoRisco) {
        this.tipoRisco = tipoRisco;
    }

    public TipoEmergencia getTipoEmergencia() {
        return tipoEmergencia;
    }

    public void setTipoEmergencia(TipoEmergencia tipoEmergencia) {
        this.tipoEmergencia = tipoEmergencia;
    }

    @Override
    public String toString() {
        return nomeFamilia;
    }
}
