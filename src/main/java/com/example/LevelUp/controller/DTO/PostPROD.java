package com.example.LevelUp.controller.DTO;

import com.example.LevelUp.model.TipoProducto;

public class PostPROD {
    private String nombre;
    private String descripcion;
    private String imagenUrl;
    private Integer precioBruto;
    private Integer stock;
    private Integer id_tipo;
    private Integer precioTotal;
    private Integer valorImpuestos;

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

    public Integer getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(Integer id_tipo) {
        this.id_tipo = id_tipo;
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
