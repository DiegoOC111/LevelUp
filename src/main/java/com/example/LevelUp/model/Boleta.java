package com.example.LevelUp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Boleta", schema = "LevelUpDB", indexes = {
        @Index(name = "Usuario_Id_usuario", columnList = "Usuario_Id_usuario"),
        @Index(name = "Id_Despacho", columnList = "Id_Despacho")
})
public class Boleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Boleta", nullable = false)
    private Integer id;

    @Column(name = "Total_Bruto")
    private Integer totalBruto;

    @Column(name = "Total_Impuestos", nullable = false)
    private Integer totalImpuestos;

    @Column(name = "Total", nullable = false)
    private Integer total;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Usuario_Id_usuario", nullable = false)
    private Usuario usuarioIdUsuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Id_Despacho", nullable = false)
    private TipoDespacho idDespacho;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalBruto() {
        return totalBruto;
    }

    public void setTotalBruto(Integer totalBruto) {
        this.totalBruto = totalBruto;
    }

    public Integer getTotalImpuestos() {
        return totalImpuestos;
    }

    public void setTotalImpuestos(Integer totalImpuestos) {
        this.totalImpuestos = totalImpuestos;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Usuario getUsuarioIdUsuario() {
        return usuarioIdUsuario;
    }

    public void setUsuarioIdUsuario(Usuario usuarioIdUsuario) {
        this.usuarioIdUsuario = usuarioIdUsuario;
    }

    public TipoDespacho getIdDespacho() {
        return idDespacho;
    }

    public void setIdDespacho(TipoDespacho idDespacho) {
        this.idDespacho = idDespacho;
    }

}