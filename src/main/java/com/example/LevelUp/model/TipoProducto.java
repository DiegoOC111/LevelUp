package com.example.LevelUp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "Tipo_Producto", schema = "LevelUpDB")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TipoProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Prod", nullable = false)
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 20)
    private String nombre;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}