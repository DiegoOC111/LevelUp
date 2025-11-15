package com.example.LevelUp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Detalle_boleta", schema = "LevelUpDB", indexes = {
        @Index(name = "Id_Boleta", columnList = "Id_Boleta"),
        @Index(name = "Id_Producto", columnList = "Id_Producto")
})
public class DetalleBoleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Detalle", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Id_Boleta", nullable = false)
    private Boleta idBoleta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Id_Producto", nullable = false)
    private Producto idProducto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boleta getIdBoleta() {
        return idBoleta;
    }

    public void setIdBoleta(Boleta idBoleta) {
        this.idBoleta = idBoleta;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

}