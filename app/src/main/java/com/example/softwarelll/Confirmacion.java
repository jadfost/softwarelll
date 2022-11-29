package com.example.softwarelll;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        final EditText direccion = findViewById(R.id.direccionedit);

        cancelarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginti();
                Toast.makeText(Confirmacion.this, "Compra Cancelada!", Toast.LENGTH_SHORT).show();
            }
        });

        confimarBtn.setOnClickListener(view -> {
            AlertDialog.Builder alertdialog = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
            alertdialog.setMessage("Tu pedido sera enviado a la direccion: " + direccion.getText().toString()
                    + ", llegara en Aproximandamente 30-50 Minutos. Desea Confirmar?")
                    .setCancelable(false)// Aqui defines que no se pueda dar atras (cancelar el dialog) el usuario debera seleccionar una opcion
                    .setPositiveButton(" Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            loginti();
                            Toast.makeText(Confirmacion.this, "Compra Realizada! Pronto Llegara tu pedido!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Confirmacion.this, "Oppss!", Toast.LENGTH_SHORT).show();
                        }
                    });
            AlertDialog mensaje = alertdialog.create();
            mensaje.show();
        });

    }

    public void loginti(){
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
    }
}
