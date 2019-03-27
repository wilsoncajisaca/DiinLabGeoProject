package com.geoinformatica.wilsoncajisaca.diinlab.Common;

public class BusAgenda {

    public String tituloEvento;
    public String direccionEvento;
    public String fechaDesde;
    public String horaDesde;
    public String fechaHasta;
    public String horaHasta;
    public boolean alldayevento;
    public String descripcionevento;

    public BusAgenda(String tituloEvento, String direccionEvento, String fechaDesde, String horaDesde, String fechaHasta, String horaHasta, boolean alldayevento, String descripcionevento) {
        this.tituloEvento = tituloEvento;
        this.direccionEvento = direccionEvento;
        this.fechaDesde = fechaDesde;
        this.horaDesde = horaDesde;
        this.fechaHasta = fechaHasta;
        this.horaHasta = horaHasta;
        this.alldayevento = alldayevento;
        this.descripcionevento = descripcionevento;
    }

    public BusAgenda(String tituloEvento, String direccionEvento, String fechaDesde, String fechaHasta, boolean alldayevento, String descripcionevento) {
        this.tituloEvento = tituloEvento;
        this.direccionEvento = direccionEvento;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.alldayevento = alldayevento;
        this.descripcionevento = descripcionevento;
    }
}
