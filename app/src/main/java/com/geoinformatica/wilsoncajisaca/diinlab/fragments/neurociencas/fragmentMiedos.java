package com.geoinformatica.wilsoncajisaca.diinlab.fragments.neurociencas;

import android.opengl.ETC1;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.geoinformatica.wilsoncajisaca.diinlab.entidades.preferencesNeuroCiencia;

import java.util.ArrayList;
import java.util.List;

public class fragmentMiedos extends Fragment {

    EditText miedo1,miedo2,miedo3,miedo4,miedo5,miedo6,miedo7,miedo8,miedo9,miedo10;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_miedos, container, false);

        miedo1=v.findViewById(R.id.miedo1);
        miedo2=v.findViewById(R.id.miedo2);
        miedo3=v.findViewById(R.id.miedo3);
        miedo4=v.findViewById(R.id.miedo4);
        miedo5=v.findViewById(R.id.miedo5);
        miedo6=v.findViewById(R.id.miedo6);
        miedo7=v.findViewById(R.id.miedo7);
        miedo8=v.findViewById(R.id.miedo8);
        miedo9=v.findViewById(R.id.miedo9);
        miedo10=v.findViewById(R.id.miedo10);

        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {

            // If we are becoming invisible, then...
            if (!isVisibleToUser) {

                if(TextUtils.isEmpty(miedo1.getText().toString())&&
                        TextUtils.isEmpty(miedo2.getText().toString())&&
                        TextUtils.isEmpty(miedo3.getText().toString())&&
                        TextUtils.isEmpty(miedo4.getText().toString())&&
                        TextUtils.isEmpty(miedo5.getText().toString())&&
                        TextUtils.isEmpty(miedo6.getText().toString())&&
                        TextUtils.isEmpty(miedo7.getText().toString())&&
                        TextUtils.isEmpty(miedo8.getText().toString())&&
                        TextUtils.isEmpty(miedo9.getText().toString())&&
                        TextUtils.isEmpty(miedo10.getText().toString())) {
                    Toast.makeText(getContext(), "Por favor describe al menos 1 miedo", Toast.LENGTH_LONG).show();
                }else {

                    preferencesNeuroCiencia.savePreferenceString(getActivity(),miedo1.getText().toString(),preferencesNeuroCiencia.PREFERENCE_txtMiedo1);
                    preferencesNeuroCiencia.savePreferenceString(getActivity(),miedo2.getText().toString(),preferencesNeuroCiencia.PREFERENCE_txtMiedo2);
                    preferencesNeuroCiencia.savePreferenceString(getActivity(),miedo3.getText().toString(),preferencesNeuroCiencia.PREFERENCE_txtMiedo3);
                    preferencesNeuroCiencia.savePreferenceString(getActivity(),miedo4.getText().toString(),preferencesNeuroCiencia.PREFERENCE_txtMiedo4);
                    preferencesNeuroCiencia.savePreferenceString(getActivity(),miedo5.getText().toString(),preferencesNeuroCiencia.PREFERENCE_txtMiedo5);
                    preferencesNeuroCiencia.savePreferenceString(getActivity(),miedo6.getText().toString(),preferencesNeuroCiencia.PREFERENCE_txtMiedo6);
                    preferencesNeuroCiencia.savePreferenceString(getActivity(),miedo7.getText().toString(),preferencesNeuroCiencia.PREFERENCE_txtMiedo7);
                    preferencesNeuroCiencia.savePreferenceString(getActivity(),miedo8.getText().toString(),preferencesNeuroCiencia.PREFERENCE_txtMiedo8);
                    preferencesNeuroCiencia.savePreferenceString(getActivity(),miedo9.getText().toString(),preferencesNeuroCiencia.PREFERENCE_txtMiedo9);
                    preferencesNeuroCiencia.savePreferenceString(getActivity(),miedo10.getText().toString(),preferencesNeuroCiencia.PREFERENCE_txtMiedo10);

                }
            }
        }
    }

}
