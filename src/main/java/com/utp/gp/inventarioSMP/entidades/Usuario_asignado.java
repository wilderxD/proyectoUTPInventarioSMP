package com.utp.gp.inventarioSMP.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "usuaios_asignados")
@Data
public class Usuario_asignado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty
    private String codigo;
    
    @NotEmpty
    private String nombre;
    
    @NotEmpty
    private int codigo_equipo;
    
    @NotEmpty
    private int oficina;
    
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha_asignado;

    public Usuario_asignado(Long id, String codigo, String nombre, int codigo_equipo, int oficina, Date fecha_asignado) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.codigo_equipo = codigo_equipo;
        this.oficina = oficina;
        this.fecha_asignado = fecha_asignado;
    }

    public Usuario_asignado() {
    }
    
    
    
}
