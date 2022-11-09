package com.example.softwarelll.Productos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.softwarelll.DescargarImagen;
import com.example.softwarelll.R;

public class ActividadDetallesProducto extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Intent DetallesProductos = getIntent();
        
        final Productos producto = (Productos) DetallesProductos.getSerializableExtra("producto");

        Button addToCartBtn = findViewById(R.id.addToCartBtn);

        TextView nombre = findViewById(R.id.productDetailsName);
        TextView precio = findViewById(R.id.productDetailsPrice);
        TextView categoria = findViewById(R.id.productDetailsCategory);
        TextView cantidad = findViewById(R.id.productDetailsQuantity);

        ImageView imagen = findViewById(R.id.productDetailsImage);

        final EditText newQuantity = findViewById(R.id.productDetailsNewQuantity);

        nombre.setText(producto.nombre);
        categoria.setText(producto.categoria);
        precio.setText(producto.precio.toString());
        cantidad.setText(producto.cantidad.toString());

        ProgressBar spinningWheel = findViewById(R.id.productDetailsProgressBar);
        new DescargarImagen(getApplicationContext()).download(imagen, spinningWheel, producto.imageUrl);

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    producto.setQuantity( Integer.valueOf(newQuantity.getText().toString()) );
                    onBackPressed();
                } catch (NumberFormatException numberFormatException) {
                    Toast.makeText(getApplicationContext(), "Cantidad Invalida!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

