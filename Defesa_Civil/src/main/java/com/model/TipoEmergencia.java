package com.model;

import javax.persistence.*;

@Entity
@Table(name = "TipoEmergencia")
public class TipoEmergencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmergencia")
    private int idEmergencia;

    @Column(name = "categoria", nullable = false, length = 100)
    private String categoria;

    public TipoEmergencia() {}

    public TipoEmergencia(int idEmergencia, String categoria) {
        this.idEmergencia = idEmergencia;
        this.categoria = categoria;
    }

    public int getIdEmergencia() {
        return idEmergencia;
    }

    public void setIdEmergencia(int idEmergencia) {
        this.idEmergencia = idEmergencia;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
