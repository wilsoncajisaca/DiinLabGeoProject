package com.geoinformatica.wilsoncajisaca.diinlab.Common;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class contactos{

    public String nombre;
    public String telefono;
    public String Correo;
    public String idContacto;
    public String idUser;

    private int viewType;

    public contactos(String nombre, String telefono, String correo, String idContacto, String idUser, int viewType) {
        this.nombre = nombre;
        this.telefono = telefono;
        Correo = correo;
        this.idContacto = idContacto;
        this.idUser = idUser;
        this.viewType = viewType;
    }

    public contactos(String idUser, String nombre, String telefono, String correo, String idContacto) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.Correo = correo;
        this.idContacto = idContacto;
        this.idUser = idUser;
    }

    public contactos() {
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getIdContacto() {
        return idContacto;
    }

    public void setIdContacto(String idContacto) {
        this.idContacto = idContacto;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

}
