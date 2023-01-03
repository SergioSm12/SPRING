package com.bolsadeideas.springboot.form.app.models.domain;

import jakarta.validation.constraints.NotNull;

public class usuario {

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
