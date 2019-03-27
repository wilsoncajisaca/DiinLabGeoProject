package com.geoinformatica.wilsoncajisaca.diinlab.pdfGenerator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.geoinformatica.wilsoncajisaca.diinlab.Api.apiRetrofit;
import com.geoinformatica.wilsoncajisaca.diinlab.Interface.MyjsonEvent;
import com.geoinformatica.wilsoncajisaca.diinlab.entidades.preferences;
import com.geoinformatica.wilsoncajisaca.diinlab.entidades.preferencesNeuroCiencia;
import com.geoinformatica.wilsoncajisaca.diinlab.fragments.informativos.FullScreenPDF;
import com.geoinformatica.wilsoncajisaca.diinlab.ui.PdfView;
import com.geoinformatica.wilsoncajisaca.diinlab.utils.resetSharePreferences;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TemplatePDF {
    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    String user_mail;
    private Paragraph paragraph;
    private Font fTitle=new Font(Font.FontFamily.TIMES_ROMAN,20,Font.BOLD);
    private Font fSubTitle=new Font(Font.FontFamily.TIMES_ROMAN,18,Font.BOLD);
    private Font fText=new Font(Font.FontFamily.TIMES_ROMAN,12,Font.BOLD);
    private Font fHighText=new Font(Font.FontFamily.TIMES_ROMAN,15,Font.BOLD,BaseColor.RED);
    resetSharePreferences resetSharePreferences;

    public TemplatePDF(Context context){
        this.context=context;
        user_mail=preferences.obtenerPreferenceString(context,preferences.USER_EMAIL_LOGIN);
    }

    public void operDocument(String title){
        createFile(title);
        try {
            document=new Document(PageSize.A4);
            pdfWriter=PdfWriter.getInstance(document,new FileOutputStream(pdfFile));
            document.open();
        }catch (Exception e){
            Log.e("openDocument",e.toString());
        }
    }

    public void createFile(String titulo){
        File folder=new File(Environment.getExternalStorageDirectory().toString(),"DiiNLabReportes/user"+user_mail);
        if (!folder.exists())
            folder.mkdir();
        pdfFile= new File(folder,titulo+".pdf");
    }

    public void closeDocument(){
        document.close();
    }

    public void addMetaData(String title,String subjet,String author) {
        document.addTitle(title);
        document.addAuthor(author);
        document.addSubject(subjet);
    }

    public void addtitle(String subTitle){
        try {
            paragraph=new Paragraph();
            addChildP(new Paragraph("Diinlab proyectos",fTitle));
            addChildP(new Paragraph("Geoinformatica"+subTitle,fSubTitle));
            addChildP(new Paragraph("Generado por: \""+user_mail+"\"",fText));
            paragraph.setSpacingAfter(15);
            document.add(paragraph);
            addSubMenus();
        } catch (DocumentException e) {
            Log.e("addTitles",e.toString());
        }
    }

    public void addSubMenus(){
        try {
            paragraph=new Paragraph();

            String ninos = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_CB_ninos);

            String J_titulo = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_CB_jovenes);
            String J_feme = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_J_femenino);
            String J_masc = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_J_masculino);

            String adultos = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_CB_adultos);
            String A_mujer = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_A_mujer);
            String A_madre = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_A_madre);
            String A_masculino = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_A_MASCULINO);
            String senior = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_CB_senior);

            String retos = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_cb_retos);
            String confort = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_cb_confort);
            String trasender = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_cb_trasender);
            String control = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_cb_control);
            String reconocimiento = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_cb_reconocimiento);
            String placer = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_cb_placer);
            String seguridad = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_cb_seguridad);
            String dominacion = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_cb_dominacion);
            String logro = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_cb_logro);
            String pertenecer = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_cb_pertenecer);
            String explorar = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_cb_explorar);
            String libertad = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_cb_libertad);

            String miedo1 = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_txtMiedo1);
            String miedo2 = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_txtMiedo2);
            String miedo3 = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_txtMiedo3);
            String miedo4 = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_txtMiedo4);
            String miedo5 = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_txtMiedo5);
            String miedo6 = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_txtMiedo6);
            String miedo7 = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_txtMiedo7);
            String miedo8 = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_txtMiedo8);
            String miedo9 = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_txtMiedo9);
            String miedo10 = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_txtMiedo10);

            String emocion = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_txtEmocion);

            String atencion = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_txtAtencion);

            String recordacion = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_txtRecordacion);

            String valorSimbolico = preferencesNeuroCiencia.obtenerPreferenceString(context,preferencesNeuroCiencia.PREFERENCE_txtValor);

            addChildSubmenus(new Paragraph("SEGMENTOS",fHighText));
            addChildSubmenus(new Paragraph(ninos));

            addChildSubmenus(new Paragraph(J_titulo));
            addChildSubmenus(new Paragraph(J_feme));
            addChildSubmenus(new Paragraph(J_masc));

            addChildSubmenus(new Paragraph(adultos));
            addChildSubmenus(new Paragraph(A_mujer));
            addChildSubmenus(new Paragraph(A_madre));
            addChildSubmenus(new Paragraph(A_masculino));
            addChildSubmenus(new Paragraph(senior));

            addChildSubmenus(new Paragraph("REPTIL",fHighText));
            addChildSubmenus(new Paragraph(retos));
            addChildSubmenus(new Paragraph(confort));
            addChildSubmenus(new Paragraph(trasender));
            addChildSubmenus(new Paragraph(control));
            addChildSubmenus(new Paragraph(reconocimiento));
            addChildSubmenus(new Paragraph(placer));
            addChildSubmenus(new Paragraph(seguridad));
            addChildSubmenus(new Paragraph(dominacion));
            addChildSubmenus(new Paragraph(logro));
            addChildSubmenus(new Paragraph(pertenecer));
            addChildSubmenus(new Paragraph(explorar));
            addChildSubmenus(new Paragraph(libertad));

            addChildSubmenus(new Paragraph("MIEDOS",fHighText));
            addChildSubmenus(new Paragraph(miedo1));
            addChildSubmenus(new Paragraph(miedo2));
            addChildSubmenus(new Paragraph(miedo3));
            addChildSubmenus(new Paragraph(miedo4));
            addChildSubmenus(new Paragraph(miedo5));
            addChildSubmenus(new Paragraph(miedo6));
            addChildSubmenus(new Paragraph(miedo7));
            addChildSubmenus(new Paragraph(miedo8));
            addChildSubmenus(new Paragraph(miedo9));
            addChildSubmenus(new Paragraph(miedo10));

            addChildSubmenus(new Paragraph("ATENCION",fHighText));
            addChildSubmenus(new Paragraph(atencion));

            addChildSubmenus(new Paragraph("EMOCION",fHighText));
            addChildSubmenus(new Paragraph(emocion));

            addChildSubmenus(new Paragraph("RECORDACION",fHighText));
            addChildSubmenus(new Paragraph(recordacion));

            addChildSubmenus(new Paragraph("Valor Simbolico",fHighText));
            addChildSubmenus(new Paragraph(valorSimbolico));

            paragraph.setSpacingAfter(20);
            document.add(paragraph);

            resetSharePreferences=new resetSharePreferences(context);
            resetSharePreferences.resetPreferences();

        }catch (DocumentException e){
            Log.e("addSubMenus",e.toString());
        }
    }

    private void addChildP(Paragraph childparagraph){
        childparagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childparagraph);
    }
    private void addChildSubmenus(Paragraph childparagraph){
        childparagraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.add(childparagraph);
    }

    public void addParagraph(String text){
        try {
            paragraph=new Paragraph(text,fText);
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            document.add(paragraph);
        } catch (DocumentException e) {
            Log.e("addParagraph",e.toString());
        }
    }

    public void enviarPdf(){

        PdfSendMail pdfSendMail=new PdfSendMail(context);
        pdfSendMail.sendMessage(pdfFile.getAbsolutePath());

    }

    public void abrirPdf(){

        android.support.v4.app.FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
        FullScreenPDF dialog = new FullScreenPDF();
        FragmentTransaction ft = manager.beginTransaction();
        dialog.show(ft, FullScreenPDF.TAG);

        Bundle b = new Bundle();
        b.putString("pdf", pdfFile.getAbsolutePath());
        b.putBoolean("exit", true);
        //initialize the dialog object
        dialog.setArguments(b);

    }

    public void enviarPdfGenerado() {

        String user_email=preferences.obtenerPreferenceString(context,preferences.USER_EMAIL_LOGIN);

        if (!pdfFile.exists()) writeToFile(pdfFile);

        MyjsonEvent service=apiRetrofit.setApiFile().create(MyjsonEvent.class);

        RequestBody requestBody =RequestBody.create(MediaType.parse("application/octet-stream"),pdfFile);
        MultipartBody.Part body =MultipartBody.Part.createFormData("Neurociencia" ,pdfFile.getName(),requestBody);
        String descriptionString ="Generado por "+ user_email;
        RequestBody description =RequestBody.create(MultipartBody.FORM,descriptionString);
        Call<ResponseBody> callable=service.uploadFile(description,body);
        callable.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Toast.makeText(context, ""+response.body().string(), Toast.LENGTH_SHORT).show();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.getStackTrace();
            }
        });
    }

    public void writeToFile(File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write("O rayos no se pudo generar el pdf correctamente...".getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void viewPDFee(){
        Intent intent =new Intent(context,PdfView.class);
        intent.putExtra("path",pdfFile.getAbsolutePath());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
