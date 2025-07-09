package com.utp.gp.inventarioSMP.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String username;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "rol")
    private Rol rol;

    @NotEmpty
    private String status;

    @NotEmpty
    private String password;

    @Column(name = "fecha_modificacion", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha_modificacion;

    @PrePersist
    @PreUpdate
    protected void actualizarFechas() {
        if (this.fecha_modificacion == null) {
            this.fecha_modificacion = new Date();
        }
    }

    public Usuario(Long id, String username, Rol rol, String status, String password, Date fecha_modificacion) {
        this.id = id;
        this.username = username;
        this.rol = rol;
        this.status = status;
        this.password = password;
        this.fecha_modificacion = fecha_modificacion;
    }

    public Usuario() {
    }

}
