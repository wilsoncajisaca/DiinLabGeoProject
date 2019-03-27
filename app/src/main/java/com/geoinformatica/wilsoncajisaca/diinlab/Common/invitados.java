package com.geoinformatica.wilsoncajisaca.diinlab.Common;

public class invitados {

    private String id;
    private String nombre;
    private String celular;
    private String email;

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public invitados(String id, String nombre, String celular, String email) {
        this.id = id;
        this.nombre = nombre;
        this.celular = celular;
        this.email = email;
    }

    public invitados() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
