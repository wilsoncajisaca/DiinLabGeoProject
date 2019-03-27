package com.geoinformatica.wilsoncajisaca.diinlab.fragments.neurociencas;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.geoinformatica.wilsoncajisaca.diinlab.entidades.preferencesNeuroCiencia;

public class neuro_segmentacion extends Fragment {

    private CheckBox CB_ninos,CB_jovenes,CB_adultos,CB_senior;
    private CheckBox J_masculino,J_femenino,A_SuMujer,A_SuMama,A_Masculino;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_segmentacion, container, false);
        //Defino los parametros comunes de la vista XML...
        Common(v);
        //Cambio estado de los RB dependiendo de la seleccion C_BX...
        Estado();

        //Retornamos el parametro View...
        return v;
    }

    private void Common(View v){

        //Defino los C_BX...
        CB_ninos=v.findViewById(R.id.cb_ninos);
        CB_jovenes=v.findViewById(R.id.cb_jovenes);
        CB_adultos=v.findViewById(R.id.cb_adultos);
        CB_senior=v.findViewById(R.id.cb_senior);
        J_masculino=v.findViewById(R.id.cb_J_masculino);
        J_femenino=v.findViewById(R.id.cb_J_femenino);

        A_Masculino=v.findViewById(R.id.A_Masculino);
        A_SuMujer=v.findViewById(R.id.A_SuMujer);
        A_SuMama=v.findViewById(R.id.A_SuMama);

    }

    private void Estado() {

        CB_ninos.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isChecked()){
                preferencesNeuroCiencia.savePreferenceString(getActivity(),"Niños",preferencesNeuroCiencia.PREFERENCE_CB_ninos);
            }else {
                preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_CB_ninos);
            }
        });

        //Activo o descativo la seleccion de los RB de la seccion JOVENES
        CB_jovenes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(buttonView.isChecked()){

                preferencesNeuroCiencia.savePreferenceString(getActivity(),"Jovenes",preferencesNeuroCiencia.PREFERENCE_CB_jovenes);

                J_masculino.setEnabled(true);
                J_femenino.setEnabled(true);

                    J_masculino.setOnCheckedChangeListener((buttonView1, isChecked1) -> {
                        if (buttonView1.isChecked()){
                            preferencesNeuroCiencia.savePreferenceString(getActivity(),"J Masculinos",preferencesNeuroCiencia.PREFERENCE_J_masculino);
                        }else {
                            preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_J_masculino);
                        }
                    });
                    J_femenino.setOnCheckedChangeListener((buttonView1, isChecked1) -> {
                        if (buttonView1.isChecked()){
                            preferencesNeuroCiencia.savePreferenceString(getActivity(),"J Femeninas",preferencesNeuroCiencia.PREFERENCE_J_femenino);
                        }else {
                            preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_J_femenino);
                        }
                    });

            }else {
                J_masculino.setEnabled(false);
                J_femenino.setEnabled(false);

                preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_CB_jovenes);
                preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_J_masculino);
                preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_J_femenino);

            }
        });

        //Activo o descativo la seleccion de los RB de la seccion ADULTOS
        CB_adultos.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isChecked()){

                preferencesNeuroCiencia.savePreferenceString(getActivity(),"Adultos",preferencesNeuroCiencia.PREFERENCE_CB_adultos);

                A_SuMama.setEnabled(true);
                A_SuMujer.setEnabled(true);
                A_Masculino.setEnabled(true);

                A_SuMama.setOnCheckedChangeListener((buttonView1, isChecked1) -> {
                    if (buttonView1.isChecked()){
                        preferencesNeuroCiencia.savePreferenceString(getActivity(),"A Super Mama",preferencesNeuroCiencia.PREFERENCE_A_madre);
                    }else {
                        preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_A_madre);
                    }
                });

                A_SuMujer.setOnCheckedChangeListener((buttonView1, isChecked1) -> {
                    if (buttonView1.isChecked()){
                        preferencesNeuroCiencia.savePreferenceString(getActivity(),"A Super mujer",preferencesNeuroCiencia.PREFERENCE_A_mujer);
                    }else {
                        preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_A_mujer);
                    }
                });

                A_Masculino.setOnCheckedChangeListener((buttonView1, isChecked1) -> {
                    if (buttonView1.isChecked()){
                            preferencesNeuroCiencia.savePreferenceString(getActivity(),"A Masculino",preferencesNeuroCiencia.PREFERENCE_A_MASCULINO);
                    }else {
                        preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_A_MASCULINO);
                    }
                });

            }else {
                A_SuMama.setEnabled(false);
                A_SuMujer.setEnabled(false);
                A_Masculino.setEnabled(false);

                preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_A_MASCULINO);
                preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_A_mujer);
                preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_A_madre);
                preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_CB_adultos);

            }
        });

        CB_senior.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(buttonView.isChecked()){
                preferencesNeuroCiencia.savePreferenceString(getActivity(),"Senior",preferencesNeuroCiencia.PREFERENCE_CB_senior);
            }else {
                preferencesNeuroCiencia.savePreferenceString(getActivity(),null,preferencesNeuroCiencia.PREFERENCE_CB_senior);
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

                // TODO su codigo aqui
            }
        }
    }
}
