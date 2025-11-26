package com.example.LevelUp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "Tipo_despacho", schema = "LevelUpDB")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TipoDespacho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Despacho", nullable = false)
    private Integer id;

    @Column(name = "Nombre_despacho", nullable = false, length = 20)
    private String nombreDespacho;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreDespacho() {
        return nombreDespacho;
    }

    public void setNombreDespacho(String nombreDespacho) {
        this.nombreDespacho = nombreDespacho;
    }

}