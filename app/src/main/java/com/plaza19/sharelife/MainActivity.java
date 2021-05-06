package com.plaza19.sharelife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.yanzhenjie.loading.dialog.LoadingDialog;

public class MainActivity extends AppCompatActivity {

    private TextView textViewRegistrar, cuentaOlvidada;
    private EditText edit_email, edit_password;
    private Button btn_sesion;
    private FirebaseAuth auth;
    private int intentos =0;
    private LoadingDialog loading_dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        edit_email = findViewById(R.id.edit_email_main);
        edit_password = findViewById(R.id.edit_password_main);
        btn_sesion = findViewById(R.id.btn_iniciarSesion);
        cuentaOlvidada = findViewById(R.id.texViewCuentaOlvidada);
        auth = FirebaseAuth.getInstance();
        loading_dialog = new LoadingDialog(MainActivity.this);


        textViewRegistrar = findViewById(R.id.texViewRegistrar);
        textViewRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(i);
            }
        });

        btn_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                auth.signInWithEmailAndPassword(edit_email.getText().toString(), edit_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(MainActivity.this, PrincipalActivity.class);
                            startActivity(i);
                            loading_dialog.show();
                        }else {
                            Toast.makeText(MainActivity.this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                            intentos ++;
                            if (intentos == 3) {
                                cuentaOlvidada.setVisibility(View.VISIBLE);
                                cuentaOlvidada.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        loading_dialog.show();
                                        Intent i = new Intent(MainActivity.this, RecupinActivity.class);
                                        i.putExtra("CORREO", edit_email.getText().toString()); //pasamos el email al que enviamos el pin
                                        startActivity(i);
                                    }
                                });
                            }
                        }
                    }
                });
                
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loading_dialog.hide();
    }

}