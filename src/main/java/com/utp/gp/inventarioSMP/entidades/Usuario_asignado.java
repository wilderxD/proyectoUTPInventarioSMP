package com.utp.gp.inventarioSMP.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "usuarios_asignados")
@Data
public class Usuario_asignado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty
    @Column(name = "codigo")
    private String codigo;
    
    @NotEmpty
    @Column(name = "nombre")
    private String nombre;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "oficina")
    @ToString.Exclude
    private Oficina oficina;
        
    @ManyToOne
    @JoinColumn(name = "codigo_equipo")
    @ToString.Exclude
    private Equipo equipo;
    
    @Column(name = "fecha_asignado", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha_asignado;
}

