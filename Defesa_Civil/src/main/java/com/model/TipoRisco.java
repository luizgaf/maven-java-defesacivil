package com.model;

import javax.persistence.*;

@Entity
@Table(name = "TipoRisco")
public class TipoRisco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRisco")
    private int idRisco;

    @Column(name = "categoria", nullable = false, length = 100)
    private String categoria;

    public TipoRisco() {}

    public TipoRisco(int idRisco, String categoria) {
        this.idRisco = idRisco;
        this.categoria = categoria;
    }

    public int getIdRisco() {
        return idRisco;
    }

    public void setIdRisco(int idRisco) {
        this.idRisco = idRisco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
