package com.example.softwarelll.Ordenar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.softwarelll.DB.DatabaseManager;
import com.example.softwarelll.Usuarios;
import com.example.softwarelll.Productos.Productos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Ordenar {
    public String id;
    public String clienteId;
    public String fechaorden;
    public String estado;
    public String productoId;
    public Double precio;
    public Integer cantidad;

    private static DatabaseManager db;

    private static final String[] tbl_order_fields = {
            "orderId", "clienteId", "productoId", "fechaorden", "estado", "precio", "cantidad"
    };
    private static final int size = tbl_order_fields.length;

    public Ordenar(String s, String id, String clienteId, String productoId, String fechaorden, String estado, Double precio, Integer cantidad){
        this.id = id;
        this.clienteId = clienteId;
        this.productoId = productoId;
        this.fechaorden = fechaorden;
        this.estado = estado;

        this.precio = precio;
        this.cantidad = cantidad;
    }

    public Ordenar(Productos p, String nombre) {
        Log.d("nirtson.timbo.Order", nombre);
        Productos product = Productos.queryProductByName(p.nombre);
        Usuarios user = Usuarios.queryCustomerByUsername(nombre);
        this.id = "0";
        this.clienteId = user.id;
        this.productoId = product.id;
        Date now = Calendar.getInstance().getTime();
        this.fechaorden = now.toString();
        this.estado = estado;
        this.precio = product.precio;
        this.cantidad = product.cantidad;

    }

    public static void setDb(Context context) {
        db = new DatabaseManager(context);
        //db.deleteDatabase(context);
        //db.createDatabase(context);
        //initOrders();
    }

    @NonNull
    @Override
    public String toString() {
        Usuarios customer = Usuarios.queryCustomerById(this.clienteId);
        return String.format(Locale.CANADA,
                "Order %s by %s: $%.2f (%s)",
                this.id, customer.nombre, this.precio * this.cantidad, this.estado);
    }
    public String toStringLog() {
        return String.format("id: %s, clienteId: %s, productoId: %s, fechaorden: %s, estado: %s, cantidad: %s, precio: %s",
                this.id, this.clienteId, this.productoId, this.fechaorden, this.estado, this.cantidad, this.precio);
    }

    private String[] getRecord() {
        return new String[] { this.id, this.clienteId, this.productoId, this.fechaorden,
                this.precio.toString(), this.cantidad.toString()
        };
    }

    public Ordenar addToDatabase() {
        Log.d("nirtson.timbo", "creando orden" + db.toString() + this.toString());

        Long id = db.addRecord(new ContentValues(), DatabaseManager.tables[DatabaseManager.ORDER], tbl_order_fields, this.getRecord() );
        Log.d("nirtson.timbo", "Orden Creada " + id.toString());
        this.id = id.toString();
        return this;
    }

    public void cancel() {
        db.deleteRecord(DatabaseManager.tables[DatabaseManager.ORDER], tbl_order_fields[0], this.id );
    }

    public void deliver() {
        this.estado = estado;
        updateDatabase();
    }

    public void updateDatabase() {
        db.updateRecord(new ContentValues(), DatabaseManager.tables[DatabaseManager.ORDER], tbl_order_fields,  this.getRecord() );
    }

    public static Ordenar queryById(String id) {
        List ordenarList = db.queryTable(DatabaseManager.tables[DatabaseManager.ORDER], "orderId = ?", new String[] { id });
        ordenarList = convertDbResults(ordenarList);
        return (Ordenar) ordenarList.get(0);
    }

    @SuppressLint("LongLogTag")
    public static ArrayList<Ordenar> fetchOrders(String customerId) {
        List ordenarList = db.queryTable(DatabaseManager.tables[DatabaseManager.ORDER], "clienteId = ?", new String[] { customerId });

        Log.d("nirtson.timbo.customerId", ordenarList.toString());
        return convertDbResults(ordenarList);
    }

    @SuppressLint("LongLogTag")
    public static ArrayList<Ordenar> fetchOrders() {
        List ordenarList = db.getTable(DatabaseManager.tables[DatabaseManager.ORDER]);
        Log.d("nirtson.timbo.fetchOrder", ordenarList.size() + ", " + ordenarList.toString());
        return convertDbResults(ordenarList);
    }

    private static ArrayList<Ordenar> convertDbResults(List ordenarList) {
        ArrayList<Ordenar> orderArrayList = new ArrayList<Ordenar>();
        for (int i = 0; i < ordenarList.size() ; i++) {
            List l = (List) ordenarList.get(i);
            orderArrayList.add(new Ordenar(
                    l.get(Ordenar.size * i).toString(),
                    l.get(Ordenar.size * i + 1).toString(),
                    l.get(Ordenar.size * i + 2).toString(),
                    l.get(Ordenar.size * i + 3).toString(),
                    l.get(Ordenar.size * i + 4).toString(),
                    l.get(Ordenar.size * i + 5).toString(),
                    Double.valueOf(l.get(Ordenar.size * i + 6).toString()),
                    Integer.valueOf(l.get(Ordenar.size * i + 7).toString())
            ));
        }
        return orderArrayList;
    }
}
