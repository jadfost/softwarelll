package com.example.softwarelll.Productos;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.softwarelll.DescargarImagen;
import com.example.softwarelll.R;

public class AdaptadorProductos extends ArrayAdapter<Productos> {

    public double total = 0;

    public AdaptadorProductos(@NonNull Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(final int posicion, View convertirVista, @NonNull ViewGroup padre) {
        // Obtener el elemento de datos para esta posición
        final Productos producto = getItem(posicion);
        // Comprobar si se está reutilizando una vista existente; de lo contrario, inflar la vista
        if (convertirVista == null) {
            convertirVista = LayoutInflater.from(getContext()).inflate(R.layout.fragment_item, padre, false);
        }

        convertirVista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent productDetails = new Intent(getContext(), ActividadDetallesProducto.class);
                productDetails.putExtra("producto", producto);
                productDetails.putExtra("productPosition", posicion);
                productDetails.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                getContext().startActivity(productDetails);
            }
        });

        // Vista de búsqueda para la población de datos
        TextView tvNombre = convertirVista.findViewById(R.id.productName);
        TextView tvCantidad = convertirVista.findViewById(R.id.productQuantity);
        TextView tvPrecio = convertirVista.findViewById(R.id.productPrice);
        ImageView ivMiniatura = convertirVista.findViewById(R.id.productThumbnail);

        // Rellene los datos en la vista de plantilla usando el objeto de datos
        Log.d("nirtson.PRODUCT=", producto.toString());

        tvNombre.setText(producto.nombre);
        tvCantidad.setText(String.valueOf(producto.cantidad));
        tvPrecio.setText(String.valueOf(producto.precio * producto.cantidad));

        ProgressBar spinningWheel = convertirVista.findViewById(R.id.productProgressBar);
        new DescargarImagen(getContext()).download(ivMiniatura, spinningWheel, producto.imageUrl);

        // Devolver la vista completa para renderizar en pantalla
        return convertirVista;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        total = 0;
        for (int i = 0; i < getCount(); i++) {
            Productos producto = getItem(i);
            total += producto.cantidad * producto.precio;
        }
    }
}
