package com.geoinformatica.wilsoncajisaca.diinlab.fragments.menusPrincipales;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.geoinformatica.wilsoncajisaca.diinlab.Api.apiRetrofit;
import com.geoinformatica.wilsoncajisaca.diinlab.Common.Events;
import com.geoinformatica.wilsoncajisaca.diinlab.Interface.MyjsonEvent;
import com.geoinformatica.wilsoncajisaca.diinlab.internet.SolicitudesJson;
import com.geoinformatica.wilsoncajisaca.diinlab.ui.LoginActivity;
import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.geoinformatica.wilsoncajisaca.diinlab.entidades.preferences;
import com.geoinformatica.wilsoncajisaca.diinlab.ui.NewEventActivity;
import com.geoinformatica.wilsoncajisaca.diinlab.utils.contactosEmergent;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class perfilFragment extends Fragment {

    ImageView photoFB;
    TextView fbNombre,txtcontactos,txteventos;
    String user_login_email="";
    String contactosT="";
    String eventosT="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.perfilfragment, container, false);
        getActivity().setTitle("Mi Perfil");
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        setHasOptionsMenu(true);

        txtcontactos=v.findViewById(R.id.txtcontactos);
        txteventos=v.findViewById(R.id.txteventosT);
        photoFB=v.findViewById(R.id.photoFB);

        fbNombre=v.findViewById(R.id.txtprofile);

        user_login_email=preferences.obtenerPreferenceString(getActivity(),preferences.USER_EMAIL_LOGIN);

        fbNombre.setText(contactosEmergent.StringsEvent(user_login_email));

        all_contacts_agendas();

        return v;
    }

    private void goLoginScreen() {

        preferences.savePreferenceBoolean(getContext(),false,preferences.PREFERENCE_ESTADO_BUTTOM);
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    public void all_contacts_agendas(){
        SolicitudesJson sj = new SolicitudesJson() {
            @Override
            public void solicitudCompletada(JSONObject j) {

                try {
                    String resultado=j.getString("principal");
                    if (resultado.equals("CC")){
                        JSONArray json = j.optJSONArray("datos");
                        try {

                            for (int i = 0; i < json.length(); i++) {

                                contactosT = json.getJSONObject(i).getString("contactos");
                                eventosT = json.getJSONObject(i).getString("eventos");

                                txtcontactos.setText(contactosT);
                                txteventos.setText(eventosT);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else {

                        txtcontactos.setText("0");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void solicitudErronea() {
                Toast.makeText(getContext(), "UPS!!", Toast.LENGTH_SHORT).show();
            }
        };
        String usuario = preferences.obtenerPreferenceString(getActivity(),preferences.USER_PREFERENCE_LOGIN);
        sj.solicitudJsonGET(getActivity(),SolicitudesJson.URL_GET_ALL_CONTACTS+usuario);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.logout, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Crea un diálogo de alerta sencillo
     * @return Nuevo diálogo
     */
    public void createDialogLogout() {

        new MaterialStyledDialog.Builder(getContext())
                .setTitle("¡Hasta Pronto!")
                .setDescription("¿Esta seguro de querer cerrar session?")
                .setIcon(R.drawable.exit)
                .setHeaderColor(R.color.endblue)
                .withIconAnimation(false)
                .withDialogAnimation(true)
                .setPositiveText("S I")
                .onPositive((dialog, which) -> goLoginScreen())
                .setNegativeText("C A N C E L A R")
                .onNegative((dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                //todo code

                createDialogLogout();

                break;
        }
        return true;
    }

}
