package com.utp.gp.inventarioSMP.controlador;

import com.utp.gp.inventarioSMP.servicio.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    
    @Autowired
    private UsuarioService userService;
    
    @GetMapping({ "/", "/inicio", ""})
    public String Inicio(Model model, @AuthenticationPrincipal User user){
        return "principal";
    }
}
