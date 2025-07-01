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
@Table(name = "usuarios")
@Data
public class Usuario {
    
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty
    private String username;
    
    @NotEmpty
    private int rol;
    
    @NotEmpty
    private String status;
    
    @NotEmpty
    private String password;
    
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha_modificacion;
    
    public Usuario(Long id, String username, int rol, String status, String password, Date fecha_modificacion) {
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
