package com.utp.gp.inventarioSMP.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private String tipoMoneda;
    
    @NotEmpty
    private String observacion;
    
    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "categorias")
    private Categoria categoria;
    
    @NotEmpty
    private int estado;
        
    @ManyToOne
    @JoinColumn(name = "usuarios_asignados")
    private Usuario_asignado asignado;

    public Equipo(Long id, String equipo_codigo, String equipo_descripcion, double valor, String tipoMoneda, String observacion, Categoria categoria, int estado, Usuario_asignado usuario_asignado) {
        this.id = id;
        this.equipo_codigo = equipo_codigo;
        this.equipo_descripcion = equipo_descripcion;
        this.valor = valor;
        this.tipoMoneda = tipoMoneda;
        this.observacion = observacion;
        this.categoria = categoria;
        this.estado = estado;
        this.asignado = usuario_asignado;
    }

    public Equipo() {
    }    
}
