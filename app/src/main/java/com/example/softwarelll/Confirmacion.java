package com.example.softwarelll;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
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
        TextView totall = findViewById(R.id.txttotal);
        final EditText direccion = findViewById(R.id.direccionedit);

        Intent intents = getIntent();
        if(intents.hasExtra(Intent.EXTRA_TEXT)) {
            // Notas es mi entity
            totall = (TextView) intents.getSerializableExtra(Intent.EXTRA_TEXT);
        }
        cancelarBtn.setOnClickListener(view -> {
            Intent listaProductos = new Intent(getBaseContext(), ListaProductos.class);
            startActivity(listaProductos);

        });

        confimarBtn.setOnClickListener(view -> {
            AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
            alertdialog.setMessage("Tu pedido sera enviado a la direccion: " + direccion.getText().toString()
                    + ", llegara en Aproximandamente 30-50 Minutos. Desea Confirmar?")
                    .setCancelable(false)// Aqui defines que no se pueda dar atras (cancelar el dialog) el usuario debera seleccionar una opcion
                    .setPositiveButton(" Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // agrega aqui la funcion que quieres hacer con el boton positivo
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // agrega aqui la funcion que quieres hacer con el boton negativo
                        }
                    });
            AlertDialog mensaje = alertdialog.create();
            mensaje.show();
        });

    }
}
