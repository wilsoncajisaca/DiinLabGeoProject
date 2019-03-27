package com.geoinformatica.wilsoncajisaca.diinlab.fragments.neurociencas;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.geoinformatica.wilsoncajisaca.diinlab.entidades.preferencesNeuroCiencia;

public class fragmentBtnInstitivos extends Fragment {

    LinearLayout llOrizontal;
    private CheckBox cb_retos,cb_confort,cb_trasender,cb_control,
            cb_reconocimiento,cb_placer,cb_seguridad,cb_dominacion,
            cb_logro,cb_pertenecer,cb_explorar,cb_libertad;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_discurso, container, false);

        Common(v);

        checkedC_BX();

        return v;
    }

    private void Common(View v){

        llOrizontal=v.findViewById(R.id.llOrizontal);
        cb_retos=v.findViewById(R.id.cb_retos);
        cb_confort=v.findViewById(R.id.cb_confort);
        cb_trasender=v.findViewById(R.id.cb_trasender);
        cb_control=v.findViewById(R.id.cb_control);
        cb_reconocimiento=v.findViewById(R.id.cb_reconocimiento);
        cb_placer=v.findViewById(R.id.cb_placer);
        cb_seguridad=v.findViewById(R.id.cb_seguridad);
        cb_dominacion=v.findViewById(R.id.cb_dominacion);
        cb_logro=v.findViewById(R.id.cb_logro);
        cb_pertenecer=v.findViewById(R.id.cb_pertenecer);
        cb_explorar=v.findViewById(R.id.cb_explorar);
        cb_libertad=v.findViewById(R.id.cb_libertad);

    }

    private void checkedC_BX(){

        cb_retos.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(buttonView.isChecked()){
                preferencesNeuroCiencia.savePreferenceString(getActivity(),"Retos",preferencesNeuroCiencia.PREFERENCE_cb_retos);
            }else {
                preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_cb_retos);
            }
        });
        cb_confort.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(buttonView.isChecked()){
                preferencesNeuroCiencia.savePreferenceString(getActivity(),"Confort",preferencesNeuroCiencia.PREFERENCE_cb_confort);
            }else {
                preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_cb_confort);
            }
        });
        cb_trasender.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(buttonView.isChecked()){
                preferencesNeuroCiencia.savePreferenceString(getActivity(),"Trasender",preferencesNeuroCiencia.PREFERENCE_cb_trasender);
            }else {
                preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_cb_trasender);
            }
        });
        cb_control.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(buttonView.isChecked()){
                preferencesNeuroCiencia.savePreferenceString(getActivity(),"Controlo",preferencesNeuroCiencia.PREFERENCE_cb_control);
            }else {
                preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_cb_control);
            }
        });
        cb_reconocimiento.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(buttonView.isChecked()){
                preferencesNeuroCiencia.savePreferenceString(getActivity(),"Reconocimiento",preferencesNeuroCiencia.PREFERENCE_cb_reconocimiento);
            }else {
                preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_cb_reconocimiento);
            }
        });
        cb_placer.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(buttonView.isChecked()){
                preferencesNeuroCiencia.savePreferenceString(getActivity(),"Placer",preferencesNeuroCiencia.PREFERENCE_cb_placer);
            }else {
                preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_cb_placer);
            }
        });
        cb_seguridad.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(buttonView.isChecked()){
                preferencesNeuroCiencia.savePreferenceString(getActivity(),"Seguridad",preferencesNeuroCiencia.PREFERENCE_cb_seguridad);
            }else {
                preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_cb_seguridad);
            }
        });
        cb_dominacion.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(buttonView.isChecked()){
                preferencesNeuroCiencia.savePreferenceString(getActivity(),"Dominacion",preferencesNeuroCiencia.PREFERENCE_cb_dominacion);
            }else {
                preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_cb_dominacion);
            }
        });
        cb_logro.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(buttonView.isChecked()){
                preferencesNeuroCiencia.savePreferenceString(getActivity(),"Logro",preferencesNeuroCiencia.PREFERENCE_cb_logro);
            }else {
                preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_cb_logro);
            }
        });
        cb_pertenecer.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(buttonView.isChecked()){
                preferencesNeuroCiencia.savePreferenceString(getActivity(),"Pertenecer",preferencesNeuroCiencia.PREFERENCE_cb_pertenecer);
            }else {
                preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_cb_pertenecer);
            }
        });
        cb_explorar.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(buttonView.isChecked()){
                preferencesNeuroCiencia.savePreferenceString(getActivity(),"Explorar",preferencesNeuroCiencia.PREFERENCE_cb_explorar);
            }else {
                preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_cb_explorar);
            }
        });
        cb_libertad.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(buttonView.isChecked()){
                preferencesNeuroCiencia.savePreferenceString(getActivity(),"Libertad",preferencesNeuroCiencia.PREFERENCE_cb_libertad);
            }else {
                preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_cb_libertad);
            }
        });

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {

            // If we are becoming invisible, then...
            if (!isVisibleToUser) {

                //Todo su codigo aqui

            }
        }
    }

}
