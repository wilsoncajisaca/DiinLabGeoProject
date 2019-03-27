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
                texto.setText("<html lang=\"es\">\n" +
                        "<head>\n" +
                        "\t<meta charset=\"utf-8\">\n" +
                        "\t<title>holi</title>\n" +
                        "</head>\n" +
                        "<body style=\"background-color: black \">\n" +
                        "\n" +
                        "<!--Copia desde aquí-->\n" +
                        "<table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                        "\t<tr>\n" +
                        "\t\t<td style=\"background-color: #ecf0f1; text-align: left; padding: 0\">\n" +
                        "\t\t\t<a href=\"https://www.facebook.com/PokemonTrujillo/\">\n" +
                        "\t\t\t\t<img width=\"20%\" style=\"display:block; margin: 1.5% 3%\" src=\"https://s16.postimg.org/arsbkbzlh/poketrainers.png\">\n" +
                        "\t\t\t</a>\n" +
                        "\t\t</td>\n" +
                        "\t</tr>\n" +
                        "\n" +
                        "\t<tr>\n" +
                        "\t\t<td style=\"padding: 0\">\n" +
                        "\t\t\t<img style=\"padding: 0; display: block\" src=\"https://s19.postimg.org/y5abc5ryr/alola_region.jpg\" width=\"100%\">\n" +
                        "\t\t</td>\n" +
                        "\t</tr>\n" +
                        "\t\n" +
                        "\t<tr>\n" +
                        "\t\t<td style=\"background-color: #ecf0f1\">\n" +
                        "\t\t\t<div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                        "\t\t\t\t<h2 style=\"color: #e67e22; margin: 0 0 7px\">Hola Poketrainer!</h2>\n" +
                        "\t\t\t\t<p style=\"margin: 2px; font-size: 15px\">\n" +
                        "\t\t\t\t\tSomos la comunidad Poketrainers Trujillo, una comunidad de Pokémon VGC que se encuentra en la ciudad de Trujillo Perú.<br>\n" +
                        "\t\t\t\t\tEstando próxima la salida de Pokémon Sol y Luna en la comunidad estamos realizando una serie de actividades que nos preparara para su llegada, así que los invitamos a formar parte de la comunidad y a acompañarnos en esta nueva aventura en la región de Alola, donde muchos pokemon y aventuras nos esperan!<br>\n" +
                        "\t\t\t\t\tEntre las actividades tenemos:</p>\n" +
                        "\t\t\t\t<ul style=\"font-size: 15px;  margin: 10px 0\">\n" +
                        "\t\t\t\t\t<li>Batallas amistosas.</li>\n" +
                        "\t\t\t\t\t<li>Torneos Oficiales.</li>\n" +
                        "\t\t\t\t\t<li>Intercambios de Pokémon.</li>\n" +
                        "\t\t\t\t\t<li>Actividades de integración.</li>\n" +
                        "\t\t\t\t\t<li>Muchas sorpresas más.</li>\n" +
                        "\t\t\t\t</ul>\n" +
                        "\t\t\t\t<div style=\"width: 100%;margin:20px 0; display: inline-block;text-align: center\">\n" +
                        "\t\t\t\t\t<img style=\"padding: 0; width: 200px; margin: 5px\" src=\"https://s19.postimg.org/np3e1b7pv/premier.jpg\">\n" +
                        "\t\t\t\t\t<img style=\"padding: 0; width: 200px; margin: 5px\" src=\"https://s19.postimg.org/ejzml6toz/banner_hoenn.png\">\n" +
                        "\t\t\t\t</div>\n" +
                        "\t\t\t\t<div style=\"width: 100%; text-align: center\">\n" +
                        "\t\t\t\t\t<a style=\"text-decoration: none; border-radius: 5px; padding: 11px 23px; color: white; background-color: #3498db\" href=\"https://www.facebook.com/PokemonTrujillo/\">Ir a la página</a>\t\n" +
                        "\t\t\t\t</div>\n" +
                        "\t\t\t\t<p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">Poketrainers Trujillo 2016</p>\n" +
                        "\t\t\t</div>\n" +
                        "\t\t</td>\n" +
                        "\t</tr>\n" +
                        "</table>\n" +
                        "<!--hasta aquí-->\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>");

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
