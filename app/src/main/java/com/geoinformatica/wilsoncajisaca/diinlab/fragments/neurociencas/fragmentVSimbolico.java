package com.geoinformatica.wilsoncajisaca.diinlab.fragments.neurociencas;

import android.Manifest;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.geoinformatica.wilsoncajisaca.diinlab.entidades.preferencesNeuroCiencia;
import com.geoinformatica.wilsoncajisaca.diinlab.pdfGenerator.TemplatePDF;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class fragmentVSimbolico extends Fragment {

    private EditText txtValor;

    Button guardarNeuro;
    TemplatePDF templatePDF;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_valor_simbolico, container, false);

        new RxPermissions(this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M

                        Log.i("Ok","Se otorgo los permiso de lectura a la tarjeta");

                    } else {
                        Toast.makeText(getContext(), "No se otorgo los permisos", Toast.LENGTH_SHORT).show();
                    }
                });

        templatePDF=new TemplatePDF(getContext());
        txtValor=v.findViewById(R.id.txtValor);
        guardarNeuro=v.findViewById(R.id.guardarNeuro);

        guardarNeuro.setOnClickListener(v1 -> ValidarText());

        return v;
    }

    public void ValidarText(){
            if(txtValor.equals("")||txtValor.length()==0) {
                Toast.makeText(getContext(), "Por favor rellena todos los campos antes de continuar...", Toast.LENGTH_LONG).show();
            }else {
                preferencesNeuroCiencia.savePreferenceString(getActivity(),txtValor.getText().toString(),preferencesNeuroCiencia.PREFERENCE_txtValor);
                genPdf();
            }
    }

    private void genPdf(){

        String titulo =preferencesNeuroCiencia.obtenerPreferenceString(getActivity(),preferencesNeuroCiencia.PREFERENCE_NOMBRE_MODELO);

        if (TextUtils.isEmpty(titulo)) {

            new MaterialStyledDialog.Builder(getContext())
                    .setCancelable(false)
                    .setDescription("Se necesita obligatoriamente de un nombre de modelo. \n \"Primera pantalla -> Nombre del modelo\".")
                    .setIcon(R.drawable.obligatory)
                    .withIconAnimation(true)
                    .setPositiveText("A C E P T A R")
                    .onPositive((dialog, which) -> dialog.dismiss())
                    .show();
        }else {
            String title=titulo.replaceAll(" ","_");
            templatePDF.operDocument(title);
            templatePDF.addMetaData("Clientes","ventas","DiiNLab");
            templatePDF.addtitle(" - \""+titulo+"\"");
            templatePDF.closeDocument();

            DialogAgradecimiento();
            templatePDF.enviarPdf();
        }
    }

    private void DialogAgradecimiento() {
        new MaterialStyledDialog.Builder(getContext())
                .setTitle("Gracias!")
                .setCancelable(false)
                .setDescription("Se envio la informacion registrada a nuestro personal de diseño para generar un arte convincente.")
                .setIcon(R.drawable.checked)
                .withIconAnimation(true)
                .setPositiveText("A C E P T A R")
                .onPositive((dialog, which) -> {
                    templatePDF.abrirPdf();
                    dialog.dismiss();
                })
                .show();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {

            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                    if(txtValor.equals("")||txtValor.length()==0) {
                        Toast.makeText(getContext(), "Por favor rellena todos los campos antes de continuar", Toast.LENGTH_LONG).show();
                    }else {
                        preferencesNeuroCiencia.savePreferenceString(getActivity(),txtValor.getText().toString(),preferencesNeuroCiencia.PREFERENCE_txtValor);
                    }
            }
        }
    }

}
