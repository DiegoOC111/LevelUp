package com.example.LevelUp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "Producto", schema = "LevelUpDB", indexes = {
        @Index(name = "Id_Prod", columnList = "Id_Prod")
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Producto", nullable = false)
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 20)
    private String nombre;

    @Column(name = "Descripcion", nullable = false, length = 200)
    private String descripcion;

    @Lob
    @Column(name = "Imagen_Url", nullable = false)
    private String imagenUrl;

    @Column(name = "Precio_bruto", nullable = false)
    private Integer precioBruto;

    @Column(name = "Stock", nullable = false)
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Id_Prod", nullable = false)
    private TipoProducto idProd;

    @Column(name = "Precio_total", nullable = false)
    private Integer precioTotal;

    @Column(name = "Valor_impuestos", nullable = false)
    private Integer valorImpuestos;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public Integer getPrecioBruto() {
        return precioBruto;
    }

    public void setPrecioBruto(Integer precioBruto) {
        this.precioBruto = precioBruto;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public TipoProducto getIdProd() {
        return idProd;
    }

    public void setIdProd(TipoProducto idProd) {
        this.idProd = idProd;
    }

    public Integer getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Integer precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Integer getValorImpuestos() {
        return valorImpuestos;
    }

    public void setValorImpuestos(Integer valorImpuestos) {
        this.valorImpuestos = valorImpuestos;
    }

}