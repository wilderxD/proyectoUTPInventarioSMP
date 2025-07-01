package com.utp.gp.inventarioSMP.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Table(name = "equipos")
@Data
public class Equipo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
        
    private String equipo_codigo;
    
    @NotEmpty
    private String equipo_descripcion;
    
    @NotEmpty
    private double valor;
    
    @NotEmpty
    private int tipo_moneda;
    
    @NotEmpty
    private String observacion;
    
    @NotEmpty
    private int categoria;
    
    @NotEmpty
    private int estado;
    
    @NotEmpty
    private int asignado;

    public Equipo(Long id, String equipo_codigo, String equipo_descripcion, double valor, int tipo_moneda, String observacion, int categoria, int estado, int asignado) {
        this.id = id;
        this.equipo_codigo = equipo_codigo;
        this.equipo_descripcion = equipo_descripcion;
        this.valor = valor;
        this.tipo_moneda = tipo_moneda;
        this.observacion = observacion;
        this.categoria = categoria;
        this.estado = estado;
        this.asignado = asignado;
    }

    public Equipo() {
    }    
}
