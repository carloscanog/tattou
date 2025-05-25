package com.tattou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AngularController {

    @RequestMapping(value = {
        "/", 
        "/login", 
        "/dashboard", 
        "/perfil", 
        "/tatuajes", 
        "/clientes", 
        "/**/{path:[^\\.]*}" // ← solo rutas frontend sin punto
    })
    public String forwardAngularRoutes() {
        return "forward:/index.html";
    }

}
