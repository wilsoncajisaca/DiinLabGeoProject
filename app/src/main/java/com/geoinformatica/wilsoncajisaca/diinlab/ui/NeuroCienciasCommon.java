package com.geoinformatica.wilsoncajisaca.diinlab;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.geoinformatica.wilsoncajisaca.diinlab.adapters.ViewPagerAdapter;
import com.geoinformatica.wilsoncajisaca.diinlab.fragments.neurociencas.fragmentAtencion;
import com.geoinformatica.wilsoncajisaca.diinlab.fragments.neurociencas.fragmentBtnInstitivos;
import com.geoinformatica.wilsoncajisaca.diinlab.fragments.neurociencas.fragmentEmocion;
import com.geoinformatica.wilsoncajisaca.diinlab.fragments.neurociencas.fragmentMiedos;
import com.geoinformatica.wilsoncajisaca.diinlab.fragments.neurociencas.fragmentRecordacion;
import com.geoinformatica.wilsoncajisaca.diinlab.fragments.neurociencas.fragmentVSimbolico;
import com.geoinformatica.wilsoncajisaca.diinlab.fragments.neurociencas.modelar_nombre;
import com.geoinformatica.wilsoncajisaca.diinlab.fragments.neurociencas.neuro_segmentacion;

public class NeuroCienciasCommon extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_principal);

        viewPager=findViewById(R.id.view_pager);
        tabLayout =findViewById(R.id.tabLayout);

        ViewPagerAdapter adapter =new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new modelar_nombre());
        adapter.AddFragment(new neuro_segmentacion());
        adapter.AddFragment(new fragmentBtnInstitivos());
        adapter.AddFragment(new fragmentMiedos());
        adapter.AddFragment(new fragmentAtencion());
        adapter.AddFragment(new fragmentEmocion());
        adapter.AddFragment(new fragmentRecordacion());
        adapter.AddFragment(new fragmentVSimbolico());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
