package com.example.softwarelll;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.softwarelll.Productos.ListaProductos;

public class Confirmacion extends AppCompatActivity {

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        final Button confimarBtn = findViewById(R.id.confirmBtn);
        final Button cancelarBtn = findViewById(R.id.backBtn);
        final TextView totall = findViewById(R.id.totalText);
        final EditText direccion = findViewById(R.id.direccionedit);


        cancelarBtn.setOnClickListener(view -> {
            Intent listaProductos = new Intent(getBaseContext(), ListaProductos.class);
            startActivity(listaProductos);
        });
    }
}
