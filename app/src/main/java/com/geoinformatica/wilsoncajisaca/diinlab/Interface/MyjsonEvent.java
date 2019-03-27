package com.geoinformatica.wilsoncajisaca.diinlab.internet;

import com.geoinformatica.wilsoncajisaca.diinlab.Common.Events;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyjsonEvent {
    @GET("consulta_eventos.php")
    Call<List<Events>> newEvents(@Query("user_id") String user_id);
}