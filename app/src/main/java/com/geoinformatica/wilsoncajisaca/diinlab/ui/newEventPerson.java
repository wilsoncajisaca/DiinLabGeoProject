package com.geoinformatica.wilsoncajisaca.diinlab.ui;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.support.design.chip.ChipGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.geoinformatica.wilsoncajisaca.diinlab.Common.listInvitados;
import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.geoinformatica.wilsoncajisaca.diinlab.entidades.DatePickerFragment;
import com.geoinformatica.wilsoncajisaca.diinlab.utils.VerificationPackage;
import com.geoinformatica.wilsoncajisaca.diinlab.entidades.preferences;
import com.geoinformatica.wilsoncajisaca.diinlab.internet.VolleyRP;
import com.geoinformatica.wilsoncajisaca.diinlab.utils.contactosEmergent;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class newEventPerson extends AppCompatActivity {

    private TextView fechaDesdetxt,horaDesdetxt,fechaHastatxt,horaHastatxt;
    private String fechaDesde,horaDesde,fechaHasta,horaHasta,lugar_evento;
    private EditText txtNombreEvento, txtDescripcionEvento;
    private ImageView atras;
    private Button btnContinuar;
    private PlaceAutocompleteFragment autocompleteFragment;
    private CheckBox allday;
    List<listInvitados> listInvitadoslist;
    Date fechaInicial;
    contactosEmergent contactosEmergent;

    private VolleyRP volley;
    private RequestQueue request;
    VerificationPackage verificationPackage;

    ChipGroup chipGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        Bundle parametros = this.getIntent().getExtras();
        if (parametros != null) {
            String idPerson = parametros.getString("id");
            listInvitadoslist=new ArrayList<>();
            listInvitados invitado=new listInvitados(idPerson);
            //add into list array
            listInvitadoslist.add(invitado);
        }

        contactosEmergent=new contactosEmergent(this);

        verificationPackage=new VerificationPackage(this);
        if(!verificationPackage.GoogleCalendar()) verificationPackage.AppNoEncontrada();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtNombreEvento = findViewById(R.id.txtNombreEvento);
        txtDescripcionEvento = findViewById(R.id.txtEventoDescripcion);
        btnContinuar=findViewById(R.id.btncontinuar);
        btnContinuar.setBackgroundColor(Color.TRANSPARENT);
        btnContinuar.setText("G U A R D A R");

        chipGroup=findViewById(R.id.chip_group);

        fechaDesdetxt =findViewById(R.id.fecha_desde);
        horaDesdetxt=findViewById(R.id.hora_desde);
        fechaHastatxt =findViewById(R.id.fecha_hasta);
        horaHastatxt=findViewById(R.id.hora_hasta);

        allday=findViewById(R.id.allday);
        atras=findViewById(R.id.atras);

        volley = VolleyRP.getInstance(this);
        request=volley.getRequestQueue();
        fechaInicial=Calendar.getInstance().getTime();

        //alertDialog();
        iniciarHora(); //Setear hora inicial
        iniciarFecha(); //Setear fecha inicial

        atras.setOnClickListener(v -> onBackPressed());

        allday.setOnCheckedChangeListener((buttonView, isChecked) ->  {
            if(allday.isChecked()){
                horaDesdetxt.setVisibility(View.GONE);
                horaHastatxt.setVisibility(View.GONE);
            }else {
                horaDesdetxt.setVisibility(View.VISIBLE);
                horaHastatxt.setVisibility(View.VISIBLE);
            }
        });

        autocompleteFragment=(PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.place_autocomplete);
        autocompleteFragment.setHint("Ubicación");

        btnContinuar.setOnClickListener(v -> {

            String NomEvento=txtNombreEvento.getText().toString();
            String DescEvento=txtDescripcionEvento.getText().toString();

            if(allday.isChecked()){
                if(NomEvento.length()==0){

                    Toast.makeText(newEventPerson.this, "Favor revise que todos los campos esten correctos",
                            Toast.LENGTH_LONG).show();

                }else {
                    if(lugar_evento==null){ lugar_evento="";}if(DescEvento==null||TextUtils.isEmpty(DescEvento)){ DescEvento="";}
                    enviarEvento(NomEvento,DescEvento,lugar_evento,fechaDesde,horaDesde,fechaHasta,horaHasta,true);
                }
            }else {
                if (NomEvento.length()==0){

                    Toast.makeText(newEventPerson.this, "Favor revise que todos los campos esten correctos",
                            Toast.LENGTH_LONG).show();

                }else {
                    if(lugar_evento==null){ lugar_evento="";}if(DescEvento==null||TextUtils.isEmpty(DescEvento)){ DescEvento="";}
                    enviarEvento(NomEvento,DescEvento,lugar_evento,fechaDesde,horaDesde,fechaHasta,horaHasta,false);
                }
            }
        });

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                lugar_evento=place.getAddress().toString();
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(newEventPerson.this, "Ups! "+status, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enviarEvento(String nomEvento, String descEvento, String lugar_evento,
                              String fechaDesde, String horaDesde, String fechaHasta, String horaHasta,Boolean alldayE){

        btnContinuar.setEnabled(false);

        Gson gson=new Gson();
        String datos=gson.toJson(listInvitadoslist);
        String ubicacion=lugar_evento.replace(" ","%20");
        String nombreEve=nomEvento.replace(" ","%20");
        String descEve=descEvento.replace(" ","%20");
        String user_login=preferences.obtenerPreferenceString(this,preferences.USER_PREFERENCE_LOGIN);

        if(alldayE==true){

            String urlInsertEventAllDay="http://68.183.136.223/Agenda/inserteventoAllday.php"+
                    "?title="+nombreEve+
                    "&fechaI="+fechaDesde+
                    "&user_id="+user_login+
                    "&locate="+ubicacion+
                    "&description="+descEve+
                    "&fechaF="+fechaHasta;

            StringRequest stringRequest=new StringRequest(Request.Method.POST, urlInsertEventAllDay,
                    response ->  {

                        verificationPackage.addEventGoogleCalendar(alldayE,nomEvento,descEvento,lugar_evento,fechaDesde,horaDesde,fechaHasta,horaHasta);

                        Log.d("Ok","Evento añadido...");
                        ActividadesActivity.actualizaragenda(this);
                        Toast.makeText(this, "Genial se guardo tu evento...", Toast.LENGTH_SHORT).show();
                        //agendaFragment.actualizaragenda(getContext());
                        finish();
                    },
                    error ->  {
                        btnContinuar.setEnabled(true);
                        Toast.makeText(this, "Parece que tenemos problemas de conexion", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
            )
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String,String> param=new HashMap<>();
                    param.put("array",datos); // array is key which we will use on server side
                    return param;
                }
            };
            VolleyRP.addToQueue(stringRequest,request,this,volley);

        }else {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {

                Date horaD=format.parse(fechaDesde+" "+horaDesde);
                Date horaH=format.parse(fechaHasta+" "+horaHasta);

                String urlinsert="http://68.183.136.223/Agenda/InsertEventoPost.php" +
                        "?title="+nombreEve+"&fechaI="+fechaDesde+"&horaI="+ horaDesde +":00" +
                        "&user_id="+user_login+"&locate="+ubicacion+"&description="+descEve+
                        "&fechaF="+fechaHasta+"&horaF="+horaHasta+":00"+"&duracion="+contactosEmergent.getDiferencia(horaD,horaH);

                StringRequest stringRequest=new StringRequest(Request.Method.POST, urlinsert,
                        response ->  {

                            verificationPackage.addEventGoogleCalendar(alldayE,nomEvento,descEvento,lugar_evento,fechaDesde,horaDesde,fechaHasta,horaHasta);

                            finish();
                            Log.d("Ok","Evento añadido...");
                            ActividadesActivity.actualizaragenda(this);
                            Toast.makeText(this, "Genial se guardo tu evento...", Toast.LENGTH_SHORT).show();
                        },
                        error ->  {
                            btnContinuar.setEnabled(true);
                            Toast.makeText(this, "Parece que tenemos problemas de conexion", Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                            error.getMessage(); // when error come i will log it
                        }
                )
                {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String,String> param=new HashMap<>();
                        param.put("array",datos); // array is key which we will use on server side
                        return param;
                    }
                };
                VolleyRP.addToQueue(stringRequest,request,this,volley);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void iniciarHora() {

        final Calendar s = Calendar.getInstance();
        final int hora = s.get(Calendar.HOUR);
        final int minuto = s.get(Calendar.MINUTE);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm a");
        SimpleDateFormat envioHora = new SimpleDateFormat("HH:mm");
        horaDesdetxt.setText(format.format(c.getTime()));
        horaHastatxt.setText(format.format(c.getTime()));
        horaDesde=envioHora.format(c.getTime());
        horaHasta=envioHora.format(c.getTime());
        horaDesdetxt.setOnClickListener(v ->{
            TimePickerDialog recogerHora=new TimePickerDialog(newEventPerson.this,(view, hourOfDay, minute) ->
            {

                Calendar td = Calendar.getInstance();
                td.set(Calendar.HOUR_OF_DAY, hourOfDay);
                td.set(Calendar.MINUTE, minute);
                SimpleDateFormat formatT = new SimpleDateFormat("HH:mm a");
                horaDesdetxt.setText(formatT.format(td.getTime()));
                Toast.makeText(this, ""+hourOfDay, Toast.LENGTH_SHORT).show();
                horaDesde=hourOfDay + ":" + minute;

            },hora, minuto, false);
            recogerHora.show();
        });
        horaHastatxt.setOnClickListener(v -> {
            TimePickerDialog recogerHora=new TimePickerDialog(newEventPerson.this,(view, hourOfDay, minute) ->
            {
                Calendar th = Calendar.getInstance();
                th.set(Calendar.HOUR_OF_DAY, hourOfDay);
                th.set(Calendar.MINUTE, minute);
                SimpleDateFormat formatTH = new SimpleDateFormat("HH:mm a");
                horaHastatxt.setText(formatTH.format(th.getTime()));
                horaHasta=hourOfDay + ":" + minute;
            },hora, minuto, false);
            recogerHora.show();
        });
    }
    private void iniciarFecha() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format_ = new SimpleDateFormat("E MMM d yyyy");
        SimpleDateFormat envioF_ = new SimpleDateFormat("yyyy-MM-dd");
        fechaDesdetxt.setText(format_.format(c.getTime()));
        fechaHastatxt.setText(format_.format(c.getTime()));
        fechaDesde = envioF_.format(c.getTime());
        fechaHasta = envioF_.format(c.getTime());
        fechaDesdetxt.setOnClickListener(v ->{
            DatePickerFragment newFragment = DatePickerFragment.newInstance((view, year, month, dayOfMonth) ->  {
                Calendar g = Calendar.getInstance();
                g.set(year, month, dayOfMonth);

                fechaInicial=g.getTime();

                fechaDesdetxt.setText(format_.format(g.getTime()));
                fechaDesde = envioF_.format(g.getTime());

                fechaHastatxt.setText(format_.format(g.getTime()));
                fechaHasta = envioF_.format(g.getTime());

            });
            newFragment.show(newEventPerson.this.getSupportFragmentManager(), "DatePickerInFullD");
        });
        fechaHastatxt.setOnClickListener(v ->{
            DatePickerFragment newFragment = DatePickerFragment.newInstance((view, year, month, dayOfMonth) ->  {

                Calendar i = Calendar.getInstance();
                i.set(year, month, dayOfMonth);

                Date fechaH=i.getTime();

                if(fechaH.after(fechaInicial)) {

                    fechaHastatxt.setText(format_.format(i.getTime()));
                    fechaHasta = envioF_.format(i.getTime());

                }else {

                    fechaHastatxt.setText(format_.format(fechaInicial));
                    fechaHasta=envioF_.format(fechaInicial);

                }

            });
            newFragment.show(newEventPerson.this.getSupportFragmentManager(), "DatePickerInFullH");

        });
    }

}
