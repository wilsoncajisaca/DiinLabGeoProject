package com.geoinformatica.wilsoncajisaca.diinlab.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.geoinformatica.wilsoncajisaca.diinlab.entidades.preferences;
import com.geoinformatica.wilsoncajisaca.diinlab.internet.VolleyRP;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private Button login,register;
    private EditText email_login,pass_login;
    private VolleyRP volley;
    private RequestQueue request;
    private RadioButton Rb_login;
    private boolean isdisableRB;
    private ImageView btn_facebook;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_login);

        if (preferences.obtenerPreferenceBoolean(this,preferences.PREFERENCE_ESTADO_BUTTOM)){
            Intent i=new Intent(LoginActivity.this,pricipalActivity.class);
            startActivity(i);
            finish();
        }

        callbackManager = CallbackManager.Factory.create();

        volley = VolleyRP.getInstance(this);
        request=volley.getRequestQueue();
        register=findViewById(R.id.registrate);
        register.setBackgroundColor(Color.TRANSPARENT);
        login=findViewById(R.id.login);
        email_login=findViewById(R.id.email_login);
        pass_login=findViewById(R.id.pass_login);
        btn_facebook=findViewById(R.id.btn_facebook);
        Rb_login=findViewById(R.id.RbLogin);

        isdisableRB=Rb_login.isChecked();
        Rb_login.setChecked(true);

        register.setOnClickListener(v -> goRegisterScreen());

        Rb_login.setOnClickListener(v ->  {
                if (isdisableRB){
                    Rb_login.setChecked(false);
                }
                isdisableRB=Rb_login.isChecked();
        });

        login.setOnClickListener(v ->  {
                if(email_login.getText().toString().length()==0 ||!android.util.Patterns.EMAIL_ADDRESS.matcher(email_login.getText().toString()).matches()||pass_login.getText().toString().length()<=6){
                    Toast.makeText(LoginActivity.this, R.string.datos_incorrectos, Toast.LENGTH_LONG).show();
                }else {
                    String tipo="FALSE";
                    setEmail(email_login.getText().toString().toLowerCase().trim(),pass_login.getText().toString(),tipo);
                }
        });

        btn_facebook.setOnClickListener(v ->
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,Arrays.asList("email")));

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
                        requestEmail(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Inicio Cancelado", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void requestEmail(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(currentAccessToken,(object, response) -> {
                if (response.getError() != null) {
                    Toast.makeText(getApplicationContext(), response.getError().getErrorMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    String email = object.getString("email");
                    String Nombre = object.getString("first_name")+" "+object.getString("last_name");
                    String id = object.getString("id");

                    String tipo="TRUE";
                    setEmail(email,id,tipo);

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void setEmail(final String email, String id, String tipo) {

//      String token=AccessToken.getCurrentAccessToken().getToken();
        logout();

        String token=FirebaseInstanceId.getInstance().getToken();

        String urlinsert="http://68.183.136.223/Agenda/login_user.php?" +
                "name="+email+"" +
                "&token="+id+"" +
                "&fb="+tipo+"" +
                "&tokenFirebase="+token;

        JsonObjectRequest solicitud = new JsonObjectRequest(urlinsert,null,response -> {

                try {
                    String resultado=response.getString("users");

                    if (resultado.equals("error")){

                        String error=response.getString("datos");

                        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setCancelable(false);
                        builder.setTitle("Fallo!");
                        builder.setMessage(error)
                                .setPositiveButton("Registrate", (dialog, which) ->  {
                                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                }).setNegativeButton("Ok", (dialog, which) -> dialog.dismiss());

                        // Create the AlertDialog object and return it
                        builder.create();
                        builder.show();

                    }else {

                        String ok=response.getString("datos");
                        String id1=ok.replace("[\"","");
                        String id2=id1.replace("\"]","");
                        //Toast.makeText(LoginActivity.this, ""+id2, Toast.LENGTH_SHORT).show();
                        goMainScreen(id2,email);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

        },error ->

                Toast.makeText(LoginActivity.this, "Tuvimos un problema de conexión...", Toast.LENGTH_SHORT).show());
                VolleyRP.addToQueue(solicitud,request,this,volley);

    }

    private void goRegisterScreen() {

        Intent intent = new Intent(this, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logout(){
        LoginManager.getInstance().logOut();
    }

    private void goMainScreen(String id, String email) {
        preferences.savePreferenceBoolean(LoginActivity.this,Rb_login.isChecked(),preferences.PREFERENCE_ESTADO_BUTTOM);
        preferences.savePreferenceString(LoginActivity.this,id,preferences.USER_PREFERENCE_LOGIN);
        preferences.savePreferenceString(LoginActivity.this,email,preferences.USER_EMAIL_LOGIN);

        Intent intent = new Intent(this, pricipalActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}