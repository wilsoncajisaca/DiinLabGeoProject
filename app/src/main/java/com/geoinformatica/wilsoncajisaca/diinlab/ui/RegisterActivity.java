package com.geoinformatica.wilsoncajisaca.diinlab.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.geoinformatica.wilsoncajisaca.diinlab.internet.VolleyRP;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class RegisterActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private Button register,irLogin;
    private ImageView register_facebook;
    private EditText email_register,location_register,pass_register,re_pass_register;
    private VolleyRP volley;
    private RequestQueue request;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTheme(R.style.AppTheme);

        callbackManager = CallbackManager.Factory.create();

        volley = VolleyRP.getInstance(this);
        request=volley.getRequestQueue();
        register=findViewById(R.id.register);
        irLogin=findViewById(R.id.irLogin);
        email_register=findViewById(R.id.email_register);
        location_register=findViewById(R.id.location_register);
        pass_register=findViewById(R.id.pass_register);
        re_pass_register=findViewById(R.id.re_pass_register);
        register_facebook=findViewById(R.id.register_facebook);

        irLogin.setOnClickListener(v -> goLoginScreen());

        register.setOnClickListener(v ->  {

                if (TextUtils.isEmpty(email_register.toString()) ||
                        TextUtils.isEmpty(location_register.toString())||
                        !android.util.Patterns.EMAIL_ADDRESS.matcher(email_register.getText().toString()).matches()){

                    Toast.makeText(RegisterActivity.this, "Email no valido", Toast.LENGTH_SHORT).show();

                }else if (pass_register.getText().toString().length()<=6||re_pass_register.getText().toString().length()<=6){

                        Toast.makeText(RegisterActivity.this,"La contraseña debe tener mas de 6 caracteres", Toast.LENGTH_SHORT).show();

                    }else if (!pass_register.getText().toString().equals(re_pass_register.getText().toString())){

                        Toast.makeText(RegisterActivity.this,"Las contraseñas deben coincidir" , Toast.LENGTH_SHORT).show();

                    }else {

                        String tipo="Este correo electronico ya se encuentra registrado";
                        String token=FirebaseInstanceId.getInstance().getToken();

                        if (token!=null){
                            setEmail(email_register.getText().toString(),location_register.getText().toString(),pass_register.getText().toString(),tipo,token);
                        }
                    }
        });

        register_facebook.setOnClickListener(v ->
                LoginManager.getInstance().logInWithReadPermissions(RegisterActivity.this,Arrays.asList("email"))
        );

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
                        requestEmail(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(RegisterActivity.this, "Inicio Cancelado", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(RegisterActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void requestEmail(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(currentAccessToken, (object, response) ->  {
                if (response.getError() != null) {
                    Toast.makeText(getApplicationContext(), response.getError().getErrorMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    String email = object.getString("email");
                    String Nombre = object.getString("first_name")+" "+object.getString("last_name");
                    String id = object.getString("id");
                    String tipo="Esta cuenta de Facebook ya se encuentra registrada";
                    String token=FirebaseInstanceId.getInstance().getToken();

                    if (token!=null){
                        setEmail(email,"No especificado",id,tipo,token);
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void setEmail(String email,String location, String pass, final String tipo,String token) {

//        String token=AccessToken.getCurrentAccessToken().getToken();

            String ubicacion=location.replace(" ","%20");

            logout();

            String urlinsert="http://68.183.136.223/Agenda/create_user.php?name="+email.toLowerCase().trim()+"" +
                    "&token="+pass+"&tokenFirebase="+token+"&location="+ubicacion;

            JsonObjectRequest solicitud = new JsonObjectRequest(urlinsert,null, response -> {

                    try {
                        String resultado=response.getString("users");

                        if (resultado.equals("error")){

                            //String error=datos.getString("datos");

                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setTitle("Error al registrarte");
                            builder.setMessage(tipo)
                                    .setPositiveButton("Ok", (dialog, which) ->dialog.dismiss()
                                    );
                            // Create the AlertDialog object and return it
                            builder.create();
                            builder.show();

                        }else {
                            //String ok=datos.getString("datos");

                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setTitle("Registro exitoso");
                            builder.setMessage("Listo acabas de registrarte correctamente")
                                    .setPositiveButton("Ok", (dialog, which) -> goLoginScreen());
                            // Create the AlertDialog object and return it
                            builder.create();
                            builder.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            },error ->
                    Toast.makeText(RegisterActivity.this, "Tuvimos un problema de conexión...", Toast.LENGTH_SHORT).show()
            );
            VolleyRP.addToQueue(solicitud,request,this,volley);

    }

    public void logout(){
        LoginManager.getInstance().logOut();
    }

    private void goLoginScreen() {

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //    public void prinkey(){
//        try {
//            PackageInfo info = getActivity().getPackageManager().getPackageInfo("com.geoinformatica.wilsoncajisaca.diinlab",PackageManager.GET_SIGNATURES);
//            for (Signature signature:info.signatures){
//                MessageDigest md =MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash",Base64.encodeToString(md.digest(),Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//    }

}
