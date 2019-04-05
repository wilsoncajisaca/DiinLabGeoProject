package com.geoinformatica.wilsoncajisaca.diinlab.pdfGenerator;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.StrictMode;
import android.text.TextUtils;

import com.geoinformatica.wilsoncajisaca.diinlab.entidades.preferences;
import com.geoinformatica.wilsoncajisaca.diinlab.fragments.neurociencas.fragmentVSimbolico;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class PdfSendMail {

    final String EMAIL_DIINLAB="secretaria@geoinformatica.org";
    final String PASSWORD="Geoinformatica2018";
    Context mContext;

    Session session;

    public PdfSendMail(Context context){
        mContext=context;
    }

    public void sendMessage(String FileSource){

        String UserEmail=preferences.obtenerPreferenceString(mContext,preferences.USER_EMAIL_LOGIN);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try{
            session=Session.getDefaultInstance(properties(), new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL_DIINLAB,PASSWORD);
                }
            });

            if (session!=null){

                BodyPart texto = new MimeBodyPart();
                texto.setText("Modelo generado a partir del cliente: "+UserEmail);

                // Se compone el adjunto con la imagen
                BodyPart adjunto = new MimeBodyPart();
                adjunto.setDataHandler(
                        new DataHandler(new FileDataSource(FileSource)));
                adjunto.setFileName("NeuroCiencias.pdf");

                // Una MultiParte para agrupar texto y pdf Generado.
                MimeMultipart multiParte = new MimeMultipart();
                multiParte.addBodyPart(texto);
                multiParte.addBodyPart(adjunto);

                Message message =new MimeMessage(session);
                message.setFrom(new InternetAddress(EMAIL_DIINLAB));
                message.setSubject("DiiNLabApp PDF");
                message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("angelespinozav@gmail.com"));
                message.setContent(multiParte);
                Transport.send(message);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Properties properties(){

        Properties properties=new Properties();
        properties.put("mail.smtp.host","smtp.googlemail.com");
        properties.put("mail.smtp.socketFactory.port","465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp","465");

        return properties;
    }

//    public ProgressDialog progreso(String Title, String Message){
//        ProgressDialog progreso;
//        if(!TextUtils.isEmpty(Title)){
//            progreso= ProgressDialog.show(mContext, Title, Message, true);
//        }else {
//            progreso= ProgressDialog.show(mContext, null, Message, true);
//        }
//        return progreso;
//    }

}
