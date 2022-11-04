package com.example.softwarelll;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.softwarelll.DB.DatabaseManager;

import java.util.List;

public class Usuarios {
    private static DatabaseManager db;
    public static final String[] tbl_customer_fields = {"clienteId", "nombre", "contrasena" };

    public String id;
    public String nombre;
    public String contrasena;

    public Usuarios(String id, String nombre, String contrasena){
        this.id = id;
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    private Usuarios(List record) {
        this(record.get(0).toString(),
                record.get(1).toString(),
                record.get(2).toString()
        );
    }

    public Usuarios(String[] record) {
        this.id = record[0];
        this.nombre = record[1];
        this.contrasena = record[2];
    }

    public String fullName() {
        return this.nombre;
    }

    protected static Usuarios queryUserByUsername(String tbl_name, String nombre) {
        List userList = db.queryTable(tbl_name, "nombre = ?", new String[]{ nombre } );
        Log.d("nirtson.timbo.queryUser", userList.toString());
        Log.d("nirtson.timbo.Clientes", db.getTable(DatabaseManager.tables[DatabaseManager.CLIENTE]).toString());
        if (userList.isEmpty()) throw new NullPointerException();
        else return new Usuarios(
                (List) userList.get(0)
        );
    }

    public static Usuarios queryCustomerByUsername(String nombre) {
        return queryUserByUsername(DatabaseManager.tables[DatabaseManager.CLIENTE], nombre);
    }

    @SuppressLint("LongLogTag")
    public static Usuarios queryCustomerById(String id) {
        List userList = db.queryTable(DatabaseManager.tables[DatabaseManager.CLIENTE], "customerId = ?", new String[] { id });
        Log.d("nirtson.timbo.clienteId", userList.toString());
        return new Usuarios( (List) userList.get(0));
    }

    public static void setDb(Context context) {
        db = new DatabaseManager(context);
    }

    private String[] getRecord() {
        return new String[] { this.id, this.nombre, this.contrasena};
    }

    public long addToDatabaseCustomer() {
        Long id = db.addRecord(new ContentValues(), DatabaseManager.tables[DatabaseManager.CLIENTE], tbl_customer_fields, this.getRecord());
        Log.d("nirtson.timbo", "Empleado creado! " + id.toString());
        return id;

    }
}