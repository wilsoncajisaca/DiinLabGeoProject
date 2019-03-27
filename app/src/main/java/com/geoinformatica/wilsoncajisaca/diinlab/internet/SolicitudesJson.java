package com.geoinformatica.wilsoncajisaca.diinlab.internet;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by user on 15/06/2017. 15
 */

public abstract class SolicitudesJson {

    public final static String URL_GET_ALL_PHOTOS = "http://68.183.136.223/Agenda/viewFotos.php";
    public final static String URL_GET_CONTACTOS_BY_ID = "http://68.183.136.223/PdpDiiNLab_PHP/contactos_by_user_id.php?user_id=";
    public final static String URL_GET_EVENTO_BY_ID="http://68.183.136.223/PdpDiiNLab_PHP/carga_evento.php?id_calendar=";
    public final static String URL_GET_AGENDA_BY_PERSON="http://68.183.136.223/PdpDiiNLab_PHP/eventos_by_person.php?user_id=";
    public final static String URL_GET_ALL_CONTACTS = "http://68.183.136.223/PdpDiiNLab_PHP/total_contactos.php?user_id=";
    public final static String URL_GET_ALL_GUESTS= "http://68.183.136.223/PdpDiiNLab_PHP/consulta_invitados.php?user_id=";

    public abstract void solicitudCompletada(JSONObject json);
    public abstract void solicitudErronea();

    public SolicitudesJson(){}

    public void solicitudJsonGET(Context c,String URL){
        JsonObjectRequest solicitud = new JsonObjectRequest(URL,null,response -> {
                solicitudCompletada(response);

        },error -> {
                solicitudErronea();
        });
        VolleyRP.addToQueue(solicitud,VolleyRP.getInstance(c).getRequestQueue(),c,VolleyRP.getInstance(c));
    }

    public void solicitudJsonPOST(Context c, String URL, HashMap h){
        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST,URL,new JSONObject(h),response -> {
                solicitudCompletada(response);
        },error ->  solicitudErronea());
        VolleyRP.addToQueue(solicitud,VolleyRP.getInstance(c).getRequestQueue(),c,VolleyRP.getInstance(c));
    }

}
