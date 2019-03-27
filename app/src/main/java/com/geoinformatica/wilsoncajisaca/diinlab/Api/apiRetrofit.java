package com.geoinformatica.wilsoncajisaca.diinlab.Api;

import com.geoinformatica.wilsoncajisaca.diinlab.Common.Events;
import com.geoinformatica.wilsoncajisaca.diinlab.Interface.MyjsonEvent;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class apiRetrofit {

    public static final String BASE_URL="http://68.183.136.223/Agenda/";
    private static Retrofit retrofit=null;

    public static Retrofit getApi(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit setApiFile(){
        OkHttpClient okHttpClient =new OkHttpClient();
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

}
