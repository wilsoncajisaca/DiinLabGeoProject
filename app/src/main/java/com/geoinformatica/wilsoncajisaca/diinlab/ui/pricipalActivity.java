package com.geoinformatica.wilsoncajisaca.diinlab.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.geoinformatica.wilsoncajisaca.diinlab.entidades.preferences;
import com.geoinformatica.wilsoncajisaca.diinlab.fragments.menusPrincipales.Fragment_ventas;
import com.geoinformatica.wilsoncajisaca.diinlab.fragments.menusPrincipales.NeuroCienciaTools;
import com.geoinformatica.wilsoncajisaca.diinlab.fragments.menusPrincipales.calendar_view_event;
import com.geoinformatica.wilsoncajisaca.diinlab.fragments.menusPrincipales.contactoFragment;
import com.geoinformatica.wilsoncajisaca.diinlab.fragments.menusPrincipales.perfilFragment;
import com.geoinformatica.wilsoncajisaca.diinlab.internet.VolleyRP;

import android.view.MenuItem;


public class pricipalActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private VolleyRP volley;

    final Fragment fragment2 = new calendar_view_event();
    final Fragment fragment1 = new contactoFragment();
    final Fragment fragment3 = new perfilFragment();
    final Fragment fragment4 = new NeuroCienciaTools();
    final Fragment fragment5 = new Fragment_ventas();
    final FragmentManager fm = getSupportFragmentManager();

    Fragment active = fragment5;

    public String user_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_pricipal);
        volley = VolleyRP.getInstance(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences.savePreferenceBooleanContact(pricipalActivity.this,true,preferences.PREFERENCE_ESTADO_SYNC);

//      loadFragment(new contactoFragment());

        user_login=preferences.obtenerPreferenceString(this,preferences.USER_PREFERENCE_LOGIN);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        navigation.setSelectedItemId(R.id.ads);

        fm.beginTransaction().add(R.id.fragment_container, fragment1, "1").hide(fragment1).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment5, "5").commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.perfil:
                fm.beginTransaction().hide(active).show(fragment3).commit();
                setTitle("Mi Perfil");
                active = fragment3;
                this.getSupportActionBar().show();
                return true;

            case R.id.agenda:
                fm.beginTransaction().hide(active).show(fragment2).commit();
                active = fragment2;
                setTitle("Agenda");
                this.getSupportActionBar().show();
                return true;

            case R.id.contactos:
                fm.beginTransaction().hide(active).show(fragment1).commit();
                active = fragment1;
                setTitle("Clientes");
                this.getSupportActionBar().show();
                return true;

            case R.id.about:
                fm.beginTransaction().hide(active).show(fragment4).commit();
                active = fragment4;
                setTitle("Contactanos");
                this.getSupportActionBar().hide();
                return true;

            case R.id.ads:
                fm.beginTransaction().hide(active).show(fragment5).commit();
                active = fragment5;
                setTitle("Ventas");
                this.getSupportActionBar().show();
                return true;
        }
        return false;
    }

//    private boolean loadFragment(Fragment fragment) {
//        //switching fragment
//        if (fragment != null) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragment_container, fragment,fragment.getClass().getName())
//                    .addToBackStack("F_MAIN")
//                    .commit();
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        Fragment fragment = null;
//
//        switch (item.getItemId()) {
//            case R.id.contactos:
//                fragment = new contactoFragment();
//                break;
//            case R.id.agenda:
//                fragment = new agendaFragment();
//                break;
//
//            case R.id.perfil:
//                fragment = new perfilFragment();
//                break;
//
//            case R.id.notificaciones:
//               //fragment = new calendar_eventos();
//
//                Intent intent =new Intent(pricipalActivity.this,CalendarActivity.class);
//                startActivity(intent);
//
//                break;
//        }
//        return loadFragment(fragment);
//    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}