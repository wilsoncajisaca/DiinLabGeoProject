package com.geoinformatica.wilsoncajisaca.diinlab.Common;

public class agenda {

    private String id_event;
    private String titulo;
    private String descripcion;
    private String ubicacion;
    private String fecha;
    private String cod_fecha;

    public agenda() {
    }

    public agenda(String id_event, String titulo, String descripcion, String ubicacion, String fecha,String cod_fecha) {
        this.id_event = id_event;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.cod_fecha=cod_fecha;
    }

    public String getId_event() {
        return id_event;
    }

    public void setId_event(String id_event) {
        this.id_event = id_event;
    }

    public String getCod_fecha() {
        return cod_fecha;
    }

    public void setCod_fecha(String cod_fecha) {
        this.cod_fecha = cod_fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
