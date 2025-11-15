package com.example.LevelUp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Ubicacion", schema = "LevelUpDB", uniqueConstraints = {
        @UniqueConstraint(name = "Id_usuario", columnNames = {"Id_usuario"})
})
public class Ubicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_ubicacion", nullable = false)
    private Integer id;

    @Column(name = "Calle", nullable = false, length = 20)
    private String calle;

    @Column(name = "Numero", nullable = false)
    private Integer numero;

    @Column(name = "Comuna", nullable = false, length = 20)
    private String comuna;

    @Column(name = "Region", nullable = false, length = 20)
    private String region;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Id_usuario", nullable = false)
    private Usuario idUsuario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

}