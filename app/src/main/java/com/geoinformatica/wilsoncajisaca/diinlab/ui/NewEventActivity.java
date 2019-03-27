package com.geoinformatica.wilsoncajisaca.diinlab.ui;

import android.app.SharedElementCallback;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.button.MaterialButton;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.geoinformatica.wilsoncajisaca.diinlab.Common.BusAgenda;
import com.geoinformatica.wilsoncajisaca.diinlab.Common.BusFechaHora;
import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.geoinformatica.wilsoncajisaca.diinlab.entidades.DatePickerFragment;
import com.geoinformatica.wilsoncajisaca.diinlab.utils.VerificationPackage;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewEventActivity extends AppCompatActivity {

    private TextView fechaDesdetxt,horaDesdetxt,fechaHastatxt,horaHastatxt;
    private String fechaDesde,horaDesde,fechaHasta,horaHasta,lugar_evento;
    private EditText txtNombreEvento, txtDescripcionEvento;
    private ImageView atras;
    private Button btnContinuar;
    private PlaceAutocompleteFragment autocompleteFragment;
    private CheckBox allday;
    Date fechaInicial;
    VerificationPackage verificationPackage;
    Calendar mTimePress;

    MaterialButton btn_add_tag;
    ChipGroup chipGroup;
    TextView textInputChips;

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(BusFechaHora event) {

        mTimePress= event.mTime;

        EventBus.getDefault().removeStickyEvent(event);

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_new_event);

        verificationPackage=new VerificationPackage(this);
        if(!verificationPackage.GoogleCalendar()) verificationPackage.AppNoEncontrada();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtNombreEvento = findViewById(R.id.txtNombreEvento);
        txtDescripcionEvento = findViewById(R.id.txtEventoDescripcion);
        btnContinuar=findViewById(R.id.btncontinuar);
        btnContinuar.setBackgroundColor(Color.TRANSPARENT);

        btn_add_tag=findViewById(R.id.Agregar);
        chipGroup=findViewById(R.id.chip_group);
        textInputChips=findViewById(R.id.txtNewEtiqueta);

        fechaDesdetxt =findViewById(R.id.fecha_desde);
        horaDesdetxt=findViewById(R.id.hora_desde);
        fechaHastatxt =findViewById(R.id.fecha_hasta);
        horaHastatxt=findViewById(R.id.hora_hasta);
        fechaInicial=Calendar.getInstance().getTime();

        //openOneDayPicker();
        allday=findViewById(R.id.allday);
        atras=findViewById(R.id.atras);

        Handler handler = new Handler();
        Runnable r = () ->iniciarFecha();iniciarHora();
        handler.postDelayed(r, 75);

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

                        Toast.makeText(NewEventActivity.this, "Favor revise que todos los campos esten correctos",
                                Toast.LENGTH_LONG).show();

                    }else {
                        if(lugar_evento==null){ lugar_evento="";}if(DescEvento==null||TextUtils.isEmpty(DescEvento)){ DescEvento="";}
                        nuevoeventoallday(NomEvento,DescEvento,lugar_evento,fechaDesde,horaDesde,fechaHasta,horaHasta);
                    }
                }else {
                if (NomEvento.length()==0){

                    Toast.makeText(NewEventActivity.this, "Favor revise que todos los campos esten correctos",
                                Toast.LENGTH_LONG).show();

                    }else {
                    if(lugar_evento==null){ lugar_evento="";}if(DescEvento==null||TextUtils.isEmpty(DescEvento)){ DescEvento="";}
                        nuevoevento(NomEvento,DescEvento,lugar_evento,fechaDesde,horaDesde,fechaHasta,horaHasta);
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
                Toast.makeText(NewEventActivity.this, "Vaya acabamos de tener un problema! ", Toast.LENGTH_SHORT).show();
            }
        });

        btn_add_tag.setOnClickListener(v -> {
            String[] tags=textInputChips.getText().toString().split(" ");
            LayoutInflater inflater=LayoutInflater.from(NewEventActivity.this);

            for (String text:tags){
                Chip chip=(Chip)inflater.inflate(R.layout.item_chip,null,false);
                chip.setChecked(true);
                chip.setText(text);
                textInputChips.setText(null);
                chip.setOnCloseIconClickListener(v1 -> {
                    chipGroup.removeView(v1);
                });
                chipGroup.addView(chip);
            }
        });
    }

    private void iniciarHora() {

        Calendar s = Calendar.getInstance();
        int hora = s.get(Calendar.HOUR);
        int minuto = s.get(Calendar.MINUTE);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm a");
        SimpleDateFormat envioHora = new SimpleDateFormat("HH:mm");

            horaDesdetxt.setText(format.format(c.getTime()));
            horaHastatxt.setText(format.format(c.getTime()));
            horaDesde=envioHora.format(c.getTime());
            horaHasta=envioHora.format(c.getTime());

        horaDesdetxt.setOnClickListener(v ->{
                    TimePickerDialog recogerHora=new TimePickerDialog(NewEventActivity.this,(view, hourOfDay, minute) ->
                    {
                        Calendar td = Calendar.getInstance();
                        td.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        td.set(Calendar.MINUTE, minute);
                        SimpleDateFormat formatT = new SimpleDateFormat("HH:mm a");
                        horaDesdetxt.setText(formatT.format(td.getTime()));
                        horaDesde=hourOfDay + ":" + minute;

                    },hora, minuto, false);
                            recogerHora.show();
                });
        horaHastatxt.setOnClickListener(v -> {
            TimePickerDialog recogerHora=new TimePickerDialog(NewEventActivity.this,(view, hourOfDay, minute) ->
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
        SimpleDateFormat format = new SimpleDateFormat("E MMM d yyyy");
        SimpleDateFormat envioF = new SimpleDateFormat("yyyy-MM-dd");

        if(mTimePress!=null){
            fechaDesdetxt.setText(format.format(mTimePress.getTime()));
            fechaHastatxt.setText(format.format(mTimePress.getTime()));
            fechaDesde = envioF.format(mTimePress.getTime());
            fechaHasta = envioF.format(mTimePress.getTime());
        }else {
            fechaDesdetxt.setText(format.format(c.getTime()));
            fechaHastatxt.setText(format.format(c.getTime()));
            fechaDesde = envioF.format(c.getTime());
            fechaHasta = envioF.format(c.getTime());
        }

        fechaDesdetxt.setOnClickListener(v ->{
            DatePickerFragment newFragment = DatePickerFragment.newInstance((view, year, month, dayOfMonth) ->  {
                Calendar g = Calendar.getInstance();
                g.set(year, month, dayOfMonth);
                SimpleDateFormat format_ = new SimpleDateFormat("E MMM d yyyy");
                SimpleDateFormat envioF_ = new SimpleDateFormat("yyyy-MM-dd");
                fechaInicial=g.getTime();
                    fechaDesdetxt.setText(format_.format(g.getTime()));
                    fechaDesde = envioF_.format(g.getTime());
                    fechaHastatxt.setText(format_.format(g.getTime()));
                    fechaHasta = envioF_.format(g.getTime());
            });
            newFragment.show(NewEventActivity.this.getSupportFragmentManager(), "DatePickerInFullD");
        });

        fechaHastatxt.setOnClickListener(v ->{
            DatePickerFragment newFragment = DatePickerFragment.newInstance((view, year, month, dayOfMonth) ->  {
                Calendar i = Calendar.getInstance();
                i.set(year, month, dayOfMonth);
                SimpleDateFormat format_ = new SimpleDateFormat("E MMM d yyyy");
                SimpleDateFormat envioF_ = new SimpleDateFormat("yyyy-MM-dd");

                Date fechaH=i.getTime();

                if(fechaH.after(fechaInicial)) {
                    fechaHastatxt.setText(format_.format(i.getTime()));
                    fechaHasta = envioF_.format(i.getTime());}
                else{
                    fechaHastatxt.setText(format_.format(fechaInicial));
                    fechaHasta = envioF_.format(fechaInicial);}
            });
            newFragment.show(NewEventActivity.this.getSupportFragmentManager(), "DatePickerInFullH");
        });
    }

    private void nuevoevento(String tituloE,
                             String descripcionE,
                             String lugarE,
                             String fechaDesde,
                             String horaDesde,
                             String fechaHasta,
                             String horaHasta){

        StringBuilder result=new StringBuilder("");
        for (int i=0;i<chipGroup.getChildCount();i++)
        {
            Chip chip=(Chip)chipGroup.getChildAt(i);
            if(chip.isChecked())
                if(i<chipGroup.getChildCount()-1)
                    result.append(chip.getText()).append(", ");
                else
                    result.append(chip.getText());
        }

        Log.i("titulo",tituloE);
        Log.i("des",descripcionE);
        Log.i("Lugar",lugarE);
        Log.i("FecDesde",fechaDesde);
        Log.i("HorDesde",horaDesde);
        Log.i("FecHasta",fechaHasta);
        Log.i("HorHasta",horaHasta);

        EventBus.getDefault().postSticky(new BusAgenda(tituloE,lugarE,fechaDesde,horaDesde,fechaHasta,horaHasta,false,descripcionE));
        irAInvitados();
    }

    private void nuevoeventoallday(String tituloE,
                                   String descripcionE,
                                   String lugarE,
                                   String fechaDesde,
                                   String horaDesde,
                                   String fechaHasta,
                                   String horaHasta){

        StringBuilder result=new StringBuilder("");
        for (int i=0;i<chipGroup.getChildCount();i++)
        {
            Chip chip=(Chip)chipGroup.getChildAt(i);
            if(chip.isChecked())
                if(i<chipGroup.getChildCount()-1)
                    result.append(chip.getText()).append(", ");
                else
                    result.append(chip.getText());
        }

        Log.i("titulo",tituloE);
        Log.i("des",descripcionE);
        Log.i("Lugar",lugarE);
        Log.i("FecDesde",fechaDesde);
        Log.i("HorDesde",horaDesde);
        Log.i("FecHasta",fechaHasta);
        Log.i("HorHasta",horaHasta);

        EventBus.getDefault().postSticky(new BusAgenda(tituloE,lugarE,fechaDesde,horaDesde,fechaHasta,horaHasta,true,descripcionE));
        irAInvitados();
    }

    private void irAInvitados(){
        Intent i =new Intent(NewEventActivity.this,InvitadosActivity.class);
        startActivity(i);
        finish();
    }
}
