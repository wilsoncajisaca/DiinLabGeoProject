package com.geoinformatica.wilsoncajisaca.diinlab.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.geoinformatica.wilsoncajisaca.diinlab.fragments.informativos.InvitadosFragment;

public class InvitadosActivity extends AppCompatActivity {

    final Fragment fragment1 = new InvitadosFragment();
    final FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitados);
        //evita el cierre si lo toca fuera de ella
        this.setFinishOnTouchOutside(false);
        //Abre el fragment de invitados
        fm.beginTransaction().add(R.id.fragment_container,fragment1).commit();
    }

}
