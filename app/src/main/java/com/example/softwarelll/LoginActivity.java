package com.example.softwarelll;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.softwarelll.DB.Ordenar;
import com.example.softwarelll.DB.Usuarios;
import com.example.softwarelll.Productos.ListaProductos;
import com.example.softwarelll.Productos.Productos;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button loginBtn = findViewById(R.id.confirmBtn);

        final EditText usernameET = findViewById(R.id.usernameLoginScreen);
        final EditText passwordET = findViewById(R.id.direccionedit);

        Usuarios.setDb(getApplicationContext());
        Ordenar.setDb(getBaseContext());

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameInput = usernameET.getText().toString();
                String passwordInput = passwordET.getText().toString();

                if (login(usernameInput, passwordInput, false)) {
                    // load initial product settings
                    Productos.setDb(getBaseContext());
                    Intent listProduct = new Intent(getBaseContext(), ListaProductos.class);
                    startActivity(listProduct);
                }
            }
        });
    }

    private boolean login(String usernameInput, String passwordInput, boolean isClerk) {
        Usuarios savedUser;
        try {
            savedUser = Usuarios.queryCustomerByUsername(usernameInput);
            if (usernameInput.equals(savedUser.nombre)) {
                if (passwordInput.equals(savedUser.contrasena)) {
                    savePreferences(savedUser.nombre, isClerk);
                    return true;
                } else {
                    Toast.makeText(LoginActivity.this, "Contraseña Invalida", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "Usuario Invalido", Toast.LENGTH_SHORT).show();
            }
            return false;

        } catch ( NullPointerException e) {
            if (isClerk) {
                Toast.makeText(LoginActivity.this, "No existe el usuario", Toast.LENGTH_SHORT).show();
            } else return login(usernameInput, passwordInput, true);
            return false;
        }
    }

    private void savePreferences(String nombre, Boolean isClerk) {

        SharedPreferences preference = getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString("Nombre", nombre);
        editor.putBoolean("isClerk", isClerk);
        editor.apply();
    }

}