package com.geoinformatica.wilsoncajisaca.diinlab.fragments.neurociencas;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.geoinformatica.wilsoncajisaca.diinlab.entidades.preferencesNeuroCiencia;
import com.geoinformatica.wilsoncajisaca.diinlab.utils.LevenshteinDistance;
import com.google.firebase.iid.FirebaseInstanceId;

public class modelar_nombre extends Fragment {

    public static EditText nombreModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_modelar, container, false);

        nombreModel=v.findViewById(R.id.nombreModel);

        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                if(TextUtils.isEmpty(nombreModel.getText().toString())) {
                    Toast.makeText(getContext(), "Por favor rellena todos los campos antes de continuar", Toast.LENGTH_LONG).show();
                }else {
                    preferencesNeuroCiencia.savePreferenceString(getActivity(),nombreModel.getText().toString(),
                            preferencesNeuroCiencia.PREFERENCE_NOMBRE_MODELO);
                }

            }
        }
    }
}
