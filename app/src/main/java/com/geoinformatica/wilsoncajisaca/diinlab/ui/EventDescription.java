package com.geoinformatica.wilsoncajisaca.diinlab.ui;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geoinformatica.wilsoncajisaca.diinlab.Common.invitados;
import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.geoinformatica.wilsoncajisaca.diinlab.adapters.invitadosAdapter;
import com.geoinformatica.wilsoncajisaca.diinlab.entidades.preferences;
import com.geoinformatica.wilsoncajisaca.diinlab.internet.SolicitudesJson;
import com.geoinformatica.wilsoncajisaca.diinlab.utils.contactosEmergent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventDescription extends AppCompatActivity {

    String idEvent;
    private List<invitados> invitadosList;//Conecta al adaptador
    private invitadosAdapter adapter;
    RecyclerView rvA;

    private TextView txtTitle,
            txtdescription,
            txtinvitados,
            txtfechaD,
            txtfechaH,
            txtplace;
    MapView mMapView;
    CardView card_map;
    ImageView imgDescription;

    private LinearLayout carga_invitados, llinvitados;
    TextView txtMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.butomsheet_calendar_description);

//        Bundle parametros = this.getIntent().getExtras();
//        if (parametros != null) {
//            idEvent = parametros.getString("id");
//        }

        if(getIntent()!=null){
            idEvent = getIntent().getStringExtra("id");
        }

        txtTitle=findViewById(R.id.title);
        txtfechaD=findViewById(R.id.txtfechaD);
        txtfechaH=findViewById(R.id.txtfechaH);
        txtinvitados=findViewById(R.id.invitados);
        txtplace=findViewById(R.id.place);
        txtdescription=findViewById(R.id.description);
        mMapView=findViewById(R.id.mapa);
        card_map=findViewById(R.id.card_map);
        imgDescription=findViewById(R.id.imgDescription);
        txtMensaje=findViewById(R.id.txtMensaje);

        carga_invitados=findViewById(R.id.carga_invitados);
        llinvitados=findViewById(R.id.llinvitados);
        rvA= findViewById(R.id.RVinvited);

        //datos_evento();
        datosEvento();
        AllInvited();
    }

    private void AllInvited(){

        LinearLayoutManager lmA = new LinearLayoutManager(this);
        rvA.setLayoutManager(lmA);

        invitadosList=new ArrayList<>();

        adapter=new invitadosAdapter(invitadosList,this);

        rvA.setAdapter(adapter);
    }

    private void datosEvento(){
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm  d MMMM yyyy");
        //SimpleDateFormat format = new SimpleDateFormat("EEEE d ' de ' MMMM ' a las ' HH:mm ' del ' yyyy");
        SimpleDateFormat formatAllDay = new SimpleDateFormat("EEEE d ' de ' MMMM ' del ' yyyy");

        SolicitudesJson json=new SolicitudesJson() {
            @Override
            public void solicitudCompletada(JSONObject response) {
                try {
                    String resultado=response.getString("principal");

                    if (resultado.equals("CC")){

                        JSONArray datos = response.optJSONArray("datos");

                        //Toast.makeText(getContext(), ""+datos, Toast.LENGTH_SHORT).show();
                        String title=datos.getJSONObject(0).getString("name");
                        String StartTime=datos.getJSONObject(0).getString("startime");
                        String EndTime=datos.getJSONObject(0).getString("endtime");
                        String descripcion=datos.getJSONObject(0).getString("descripcion");
                        String lugar=datos.getJSONObject(0).getString("lugar");
                        String tododia=datos.getJSONObject(0).getString("allday");

                        if (tododia.equals("false")){

                            Date fechaStart=format1.parse(StartTime);
                            startTime.setTime(fechaStart);
                            Date fechaEnd=format1.parse(EndTime);
                            endTime.setTime(fechaEnd);

                            txtfechaD.setText(format.format(startTime.getTime()));
                            txtfechaH.setText(format.format(endTime.getTime()));

                            txtTitle.setText(contactosEmergent.StringsEvent(title));
                            if (TextUtils.isEmpty(descripcion)){
                                txtdescription.setVisibility(View.GONE);
                                imgDescription.setVisibility(View.GONE);
                            }
                            else txtdescription.setText(contactosEmergent.StringsEvent(descripcion));

                            if (TextUtils.isEmpty(lugar)){
                                txtplace.setText("Sin direccion");
                                card_map.setVisibility(View.GONE);
                            }
                            else {
                                txtplace.setText(lugar);
                                String[] latlong =  getLocationFromAddress(lugar).split(",");
                                double latitude = Double.parseDouble(latlong[0]);
                                double longitude = Double.parseDouble(latlong[1]);

                                if(mMapView != null){
                                    mMapView.onCreate(null);
                                    mMapView.onResume();
                                    mMapView.getMapAsync(googleMap -> CargaMapa(googleMap,latitude,longitude));
                                }
                            }

                        }else {

                            Date fechaStart=format2.parse(StartTime);
                            startTime.setTime(fechaStart);
                            Date fechaEnd=format2.parse(EndTime);
                            startTime.setTime(fechaEnd);
                            txtfechaD.setText(contactosEmergent.StringsEvent(formatAllDay.format(startTime.getTime())));
                            txtfechaH.setText(contactosEmergent.StringsEvent(formatAllDay.format(endTime.getTime())));

                            txtTitle.setText(contactosEmergent.StringsEvent(title));
                            if (TextUtils.isEmpty(descripcion)) {
                                txtdescription.setVisibility(View.GONE);
                                imgDescription.setVisibility(View.GONE);
                            }
                            else txtdescription.setText(contactosEmergent.StringsEvent(descripcion));

                            if (TextUtils.isEmpty(lugar)){
                                txtplace.setText("Sin direccion");
                                card_map.setVisibility(View.GONE);
                            }
                            else {
                                txtplace.setText(lugar);
                                String[] latlong =  getLocationFromAddress(lugar).split(",");
                                double latitude = Double.parseDouble(latlong[0]);
                                double longitude = Double.parseDouble(latlong[1]);

                                if(mMapView != null){
                                    mMapView.onCreate(null);
                                    mMapView.onResume();
                                    mMapView.getMapAsync(googleMap -> CargaMapa(googleMap,latitude,longitude));
                                }
                            }

                        }

                        //Cargo a los invitados, si esque el evento se cargo correctamente...
                        invitados();

                    }else {
                        Toast.makeText(EventDescription.this, "No se logro descargar los detalles del evento, intentalo mas tarde...", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void solicitudErronea() {

            }
        };
        json.solicitudJsonGET(this,SolicitudesJson.URL_GET_EVENTO_BY_ID+idEvent);
    }

    private void CargaMapa(GoogleMap googleMap, double latitude, double longitude){
        MapsInitializer.initialize(this);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude,longitude)));

        googleMap.getUiSettings().setScrollGesturesEnabled(false);
        googleMap.getUiSettings().setZoomGesturesEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        CameraPosition locate=CameraPosition.builder().target(new LatLng(latitude,longitude)).zoom(14).bearing(0).tilt(40).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(locate));
    }

    public void invitados(){
        SolicitudesJson sj = new SolicitudesJson() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    String resultado=j.getString("principal");
                    if (resultado.equals("CC")){
                        try {
                            carga_invitados.setVisibility(View.GONE);
                            llinvitados.setVisibility(View.VISIBLE);

                            String invitados=j.getString("datos");
                            JSONArray jsonArray=new JSONArray(invitados);

                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject js =jsonArray.getJSONObject(i);
                                agregarinvitados(js.getString("id"),
                                        js.getString("name"),
                                        js.getString("mobile"),
                                        js.getString("email"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else {
                        txtMensaje.setText("No se encontro invitados");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void solicitudErronea() {
                carga_invitados.setVisibility(View.GONE);
                llinvitados.setVisibility(View.VISIBLE);
                txtinvitados.setText("Ocurrio un problema al consultar los invitados");
            }
        };
        String usuario = preferences.obtenerPreferenceString(this,preferences.USER_PREFERENCE_LOGIN);
        sj.solicitudJsonGET(this,SolicitudesJson.URL_GET_ALL_GUESTS+usuario+"&calendar_id="+idEvent);
    }

    public void agregarinvitados(String id, String nombre,String mobile,String email){
        final invitados invitados_atributos =new invitados();
        invitados_atributos.setId(id);
        invitados_atributos.setNombre(nombre);
        invitados_atributos.setCelular(mobile);
        invitados_atributos.setEmail(email);
        invitadosList.add(invitados_atributos);
        adapter.notifyDataSetChanged();
    }

    public String getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            double lat = location.getLatitude();
            double lng = location.getLongitude();

            return lat + "," + lng;

        } catch (Exception e) {
            return null;
        }

    }

}
