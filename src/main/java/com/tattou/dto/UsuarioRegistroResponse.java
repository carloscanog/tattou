package com.tattou.dto;

public class UsuarioRegistroResponse {

    private Long id;
    private String email;

    public UsuarioRegistroResponse(Long id, String email) {
        this.email = email;
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

}
