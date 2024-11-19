package com.model;

import javax.persistence.*;

@Entity
@Table(name = "CadastroFamilia")
public class CadastroFamilia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFamilia")
    private int idFamilia;

    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoRisco_id", referencedColumnName = "idRisco", nullable = false) // Definindo a chave estrangeira
    private TipoRisco tipoRisco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoEmergencia_id", referencedColumnName = "idEmergencia", nullable = false) // Definindo a chave estrangeira
    private TipoEmergencia tipoEmergencia;


    public CadastroFamilia() {}

    public CadastroFamilia(int idFamilia, String descricao, TipoRisco tipoRisco, TipoEmergencia tipoEmergencia) {
        this.idFamilia = idFamilia;
        this.descricao = descricao;
        this.tipoRisco = tipoRisco;
        this.tipoEmergencia = tipoEmergencia;
    }

    public int getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(int idFamilia) {
        this.idFamilia = idFamilia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
}
