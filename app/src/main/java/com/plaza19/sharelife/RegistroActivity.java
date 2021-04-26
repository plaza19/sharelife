package com.plaza19.sharelife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {

    private EditText edit_user,edit_email, edit_password, edit_password_confirm;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        edit_user = findViewById(R.id.edit_user);
        edit_email = findViewById(R.id.edit_email);
        edit_password = findViewById(R.id.edit_password);
        edit_password_confirm = findViewById(R.id.edit_password_confirm);
        btn_register = findViewById(R.id.button_register);


        edit_password_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //comprobamos que el correo sea valido

                if (checkMail() && checkUser() && checkPassword()) {
                    btn_register.setEnabled(true);
                    Toast.makeText(RegistroActivity.this, "activo", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(RegistroActivity.this, "desactivao", Toast.LENGTH_SHORT).show();
                checkMail();
                checkPassword();
                checkUser();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private boolean checkMail() {

        if (edit_email.getText().toString().matches(".*@.*\\..*")) {
            Toast.makeText(RegistroActivity.this, "mail bien", Toast.LENGTH_SHORT).show();
            return true;
        }else {
            Toast.makeText(RegistroActivity.this, "mail mal", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private boolean checkUser() {
        //TODO: CHECK USERS
        return true;
    }

    private boolean checkPassword() {
        if (edit_password.getText().toString().equals(edit_password_confirm.getText().toString())) {
            Toast.makeText(RegistroActivity.this, "contra bein", Toast.LENGTH_SHORT).show();
            return true;
        }else {
            Toast.makeText(RegistroActivity.this, "contra mal", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}