package com.geoinformatica.wilsoncajisaca.diinlab.fragments.neurociencas;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.geoinformatica.wilsoncajisaca.diinlab.entidades.preferencesNeuroCiencia;
import com.geoinformatica.wilsoncajisaca.diinlab.utils.LevenshteinDistance;
import com.google.firebase.iid.FirebaseInstanceId;

public class fragmentEmocion extends Fragment {

    private EditText txtEmocion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_emociones, container, false);

        txtEmocion=v.findViewById(R.id.txtEmocion);

        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {

            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                    if(txtEmocion.equals("")||txtEmocion.length()==0) {
                        Toast.makeText(getContext(), "Por favor rellena todos los campos antes de continuar", Toast.LENGTH_LONG).show();
                    }else {
                        preferencesNeuroCiencia.savePreferenceString(getActivity(),txtEmocion.getText().toString(),preferencesNeuroCiencia.PREFERENCE_txtEmocion);
                    }
            }
        }
    }

}
