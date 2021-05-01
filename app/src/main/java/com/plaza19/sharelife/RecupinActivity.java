package com.plaza19.sharelife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import Utils.RecuperarContraseña;

public class RecupinActivity extends AppCompatActivity {

    public static int pin;
    private RecuperarContraseña recuperador;
    private TextView tv_dire_correo;
    private EditText edit_pin;
    private Button btn_cambiarContraseña;
    private String correo;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupin);
        tv_dire_correo = findViewById(R.id.textViewPinRecuPin);
        edit_pin = findViewById(R.id.edit_PinRecuPin);
        btn_cambiarContraseña = findViewById(R.id.btn_cambiarContraseña_recuPin);
        Intent i = getIntent();
        recuperador = new RecuperarContraseña();
        correo = i.getStringExtra("CORREO");
        recuperador.enviaCorreo(correo);
        tv_dire_correo.setText("Por favor introduce el pin enviado a: " + correo);
        auth = FirebaseAuth.getInstance();

        btn_cambiarContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RecupinActivity.this, "Se ha enviado un correo para reestablecer la contraseña", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(RecupinActivity.this, MainActivity.class);
                            startActivity(i);
                        }else {
                            Toast.makeText(RecupinActivity.this, "No se puede enviar el correo porque la cuenta no existe", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(RecupinActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                    }
                });

            }
        });

        edit_pin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edit_pin.getText().toString().matches(String.valueOf(pin))) {
                    btn_cambiarContraseña.setEnabled(true);
                }else {
                    btn_cambiarContraseña.setEnabled(false);
                }
            }
        });
    }
}