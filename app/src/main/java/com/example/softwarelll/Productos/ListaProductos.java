package com.example.softwarelll.Productos;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.softwarelll.Ordenar.Ordenar;
import com.example.softwarelll.R;

import java.util.ArrayList;

public class ListaProductos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products);

        final Button BotonOrdenar = findViewById(R.id.placeOrderBtn);

        BotonOrdenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //vuelve a la Actividad o Fragmento anterior al que te encuentras en el momento
                // todo depende de como lo hayas programado
                if (realizarPedido()) onBackPressed();
            }
        });

    }

    public boolean realizarPedido() {
        ProductosFragmento listaFragmentosProductos = (ProductosFragmento) getFragmentManager().findFragmentById(R.id.fragment);
        ListAdapter listaProductos =  listaFragmentosProductos.getListAdapter();
        Log.d("timbo.fl", listaFragmentosProductos.toString());

        ArrayList<String> ordenes = new ArrayList<String>();
        String nombre = getSharedPreferences("Login", MODE_PRIVATE).getString("Nombre", null);
        if (nombre == null) {
            throw new RuntimeException("Nombre de usuario nulo en realizar Pedido en ListProductsActivity");
        }

        Log.d("nirtson.timbo.nombre", nombre);

        for (int i = 0; i < listaProductos.getCount() ; i++) {
            Productos producto = (Productos) listaProductos.getItem(i);
            Log.d("nirtson.timbo.product", producto.toString());

            if (producto.cantidad > 0) {
                ordenes.add (
                        (String) new Ordenar(producto, nombre).addToDatabase().id
                );
            }
        }

        if (ordenes.size() > 0) return true;
        else {
            Toast.makeText(getBaseContext(), "No hay Productos en el Carrito!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

}

