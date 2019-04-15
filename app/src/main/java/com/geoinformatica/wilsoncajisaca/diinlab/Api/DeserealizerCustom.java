package com.geoinformatica.wilsoncajisaca.diinlab.Api;

import com.geoinformatica.wilsoncajisaca.diinlab.Common.Events;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class DeserealizerCustom implements JsonDeserializer<Events> {

    @Override
    public Events deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        String id = json.getAsJsonObject().get("datos").getAsJsonObject().get("id").getAsString();
        String allday=json.getAsJsonObject().get("datos").getAsJsonObject().get("allday").getAsString();
        String titulo=json.getAsJsonObject().get("datos").getAsJsonObject().get("titulo").getAsString();
        String startime=json.getAsJsonObject().get("datos").getAsJsonObject().get("startime").getAsString();
        String endtime=json.getAsJsonObject().get("datos").getAsJsonObject().get("endtime").getAsString();
        String lugar=json.getAsJsonObject().get("datos").getAsJsonObject().get("lugar").getAsString();

        Events events=new Events(id,allday,titulo,startime,endtime,lugar);
        return events;
    }
}
