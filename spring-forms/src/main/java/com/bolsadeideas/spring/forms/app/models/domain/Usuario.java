package com.bolsadeideas.spring.forms.app.models.domain;

import com.bolsadeideas.spring.forms.app.validation.IdentificadorRegex;
import com.bolsadeideas.spring.forms.app.validation.Requerido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
//import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class Usuario {
    //@Pattern(regexp = "[0-9]{2}[.][\\d]{3}[.][\\d]{3}[-][A-Z]{1}")
    @IdentificadorRegex
    private String identificador;
    //@NotEmpty
    @NotEmpty
    private String nombre;
    //@NotEmpty
    @NotEmpty
    private String apellido;
    @NotBlank
    @Size(min = 3, max = 8)
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    @Email(message = "Correo con formato incorrecto")
    private String email;

    @NotNull
    @Min(5)
    @Max(50)
    private Integer cuenta;

    @NotNull
    //@DateTimeFormat(pattern = "yyy-MM-dd")
    @Past
    private Date fechaNacimiento;

    @NotNull
    private Pais pais;

    @NotEmpty
    private List<Role> roles;

    private Boolean habilitar;

    @NotEmpty
    private String genero;

    private String valorSecreto;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

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

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Boolean getHabilitar() {
        return habilitar;
    }

    public void setHabilitar(Boolean habilitar) {
        this.habilitar = habilitar;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getValorSecreto() {
        return valorSecreto;
    }

    public void setValorSecreto(String valorSecreto) {
        this.valorSecreto = valorSecreto;
    }
}
