package com.geoinformatica.wilsoncajisaca.diinlab.ui;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geoinformatica.wilsoncajisaca.diinlab.Common.agenda;
import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.geoinformatica.wilsoncajisaca.diinlab.adapters.AutoFitGridLayoutManager;
import com.geoinformatica.wilsoncajisaca.diinlab.adapters.agendaAdapter;
import com.geoinformatica.wilsoncajisaca.diinlab.entidades.preferences;
import com.geoinformatica.wilsoncajisaca.diinlab.internet.SolicitudesJson;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ActividadesActivity extends AppCompatActivity{

    public static List<agenda> agendaList,newList; //Conecta al adaptador
    public static agendaAdapter adapter;
    public static RecyclerView rvA;

    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";
    static LinearLayout noEvent;

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();
    String fechatex="",horatex="";

    //Variables para obtener la hora hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);
    private static String idPerson="",lugar_evento="";
    PlaceAutocompleteFragment place_autocomplete;
    EventBus bus=EventBus.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividades);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView titulo=findViewById(R.id.titulo);
        noEvent=findViewById(R.id.noEvent);

        Bundle parametros = this.getIntent().getExtras();
        if (parametros != null) {
            String nombre = parametros.getString("nombre");
            idPerson = parametros.getString("id");
            titulo.setText(nombre);
        }

        RelativeLayout atras=findViewById(R.id.atras);
        rvA=findViewById(R.id.Rvagenda);

        LinearLayoutManager lmA = new LinearLayoutManager(this);
        rvA.setLayoutManager(lmA);
        agendaList=new ArrayList<>();
        newList=new ArrayList<>();
        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);
        rvA.setLayoutManager(layoutManager);
        adapter=new agendaAdapter(agendaList,this);
        rvA.setAdapter(adapter);

        actualizaragenda(this);

        atras.setOnClickListener(v -> onBackPressed());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent i=new Intent(this,newEventPerson.class);
            i.putExtra("id",idPerson);
            startActivity(i);
        });
    }


    public static void actualizaragenda(Context context){

        agendaList.clear();

        SolicitudesJson sj = new SolicitudesJson() {
            @Override
            public void solicitudCompletada(JSONObject j) {

                try {
                    String resultado=j.getString("principal");
                    if (resultado.equals("CC")){

                        rvA.setVisibility(View.VISIBLE);
                        noEvent.setVisibility(View.GONE);

                        try {
                            String contactos=j.getString("datos");
                            JSONArray jsonArray=new JSONArray(contactos);

                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject js =jsonArray.getJSONObject(i);
                                agregaragenda(js.getString("id"),
                                        js.getString("titulo"),
                                        js.getString("descripcion"),
                                        js.getString("lugar"),
                                        js.getString("fecha"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else {
                        rvA.setVisibility(View.GONE);
                        noEvent.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void solicitudErronea() {
                Toast.makeText(context, "Ocurrio un error al momento de obtener la agenda", Toast.LENGTH_SHORT).show();
            }
        };
        String usuario = preferences.obtenerPreferenceString(context,preferences.USER_PREFERENCE_LOGIN);
        sj.solicitudJsonGET(context,SolicitudesJson.URL_GET_AGENDA_BY_PERSON+usuario+"&partner_id="+idPerson);
    }

    public static void agregaragenda(String id, final String titulo, String descripcion, String lugar,String fecha){
        final agenda agendaAtributos =new agenda();
        agendaAtributos.setId_event(id);
        agendaAtributos.setTitulo(titulo);
        agendaAtributos.setDescripcion(descripcion);
        agendaAtributos.setUbicacion(lugar);
        agendaAtributos.setFecha(fecha);
        agendaList.add(agendaAtributos);
        adapter.notifyDataSetChanged();
    }

    public void guardar_agenda(String nombre, String fecha, String hora,String description,String locate){

        String ubicacion=locate.replace(" ","%20");
        String nombreEve=nombre.replace(" ","%20");
        String descEve=description.replace(" ","%20");

        String user_login=preferences.obtenerPreferenceString(this,preferences.USER_PREFERENCE_LOGIN);
        String urlinsert="http://68.183.136.223/Agenda/" +
                "insert_evento.php?title="+nombreEve+"&fecha="+fecha+"&hora="+hora+":00&id="+idPerson+
                "&user_id="+user_login+"&locate="+ubicacion+"&description="+descEve;

        SolicitudesJson json =new SolicitudesJson() {
            @Override
            public void solicitudCompletada(JSONObject j) {

                try {
                    String resultado=j.getString("calendar");
                    if (resultado.equals("ok")){
                        Toast.makeText(ActividadesActivity.this, "Evento añadido", Toast.LENGTH_SHORT).show();
                        finalizar();

                    }else {
                        Toast.makeText(ActividadesActivity.this, "No se pudo agregar el evento", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void solicitudErronea() {
                Toast.makeText(ActividadesActivity.this, "Tuvimos un problema de conexion...", Toast.LENGTH_SHORT).show();
            }
        };
        json.solicitudJsonGET(this,urlinsert);
    }

    public void finalizar()
    {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
}
