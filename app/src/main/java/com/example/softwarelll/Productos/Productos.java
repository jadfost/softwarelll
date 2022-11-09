package com.example.softwarelll.Productos;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.softwarelll.DB.DatabaseManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Productos implements Serializable {
    public String id;
    public String nombre;
    public Double precio;
    public Integer cantidad;
    public String categoria;
    public String imageUrl;

    private static DatabaseManager db;
    public static final String[] tbl_product_fields = {
            "productoId", "nombre", "precio", "cantidad", "categoria", "imageUrl"
    };
    public static final int size = tbl_product_fields.length;

    public Productos(String id, String nombre, Double precio, Integer cantidad, String categoria, String imageUrl) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.categoria = categoria;
        this.imageUrl = imageUrl;
    }

    public Productos(List record) {
        this(record.get(0).toString(),
                record.get(1).toString(),
                Double.valueOf(record.get(2).toString()),
                Integer.valueOf(record.get(3).toString()),
                record.get(4).toString(),
                record.get(5).toString());
    }

    public static void setDb(Context context) {
        db = new DatabaseManager(context);
        initProducts();
    }

    @NonNull
    @Override
    public String toString() {
        return  "Product.toString()>> id: " + this.id +
                ", nombre: " + this.nombre +
                ", categoria: " + this.categoria +
                ", precio: " + this.precio +
                ", cantidad: " + this.cantidad +
                ", imageUrl: " + this.imageUrl;
    }

    public static void initProducts() {
        db.recreateOneTable(DatabaseManager.PRODUCT);
        new Productos( "1", "Huevo Rojo A X 30 SMN", 18670.0, 30, "Mercado",
                "https://exitocol.vtexassets.com/arquivos/ids/13315417-800-auto?v=637911315952000000&width=800&height=auto&aspect=true")
                .addToDatabase();
        new Productos( "2", "Gaseosa duo gratis gaseosa 1.5", 13300.0, 15, "Bebidas",
                "https://exitocol.vtexassets.com/arquivos/ids/9222577-800-auto?v=637637340986970000&width=800&height=auto&aspect=true")
                .addToDatabase();
        new Productos( "3", "Pasta Clásica Spaghetti X 1000 gr", 5040.0, 35, "Mercado",
                "https://exitocol.vtexassets.com/arquivos/ids/1976731-800-auto?v=637259153309900000&width=800&height=auto&aspect=true")
                .addToDatabase();
        new Productos( "4", "Aceite Vegetal FRESCAMPO 3000 ml", 30900.0, 12, "Mercado",
                "https://exitocol.vtexassets.com/arquivos/ids/13929565-800-auto?v=637944648796900000&width=800&height=auto&aspect=true")
                .addToDatabase();
        new Productos("5", "SIXPACK BEBIDA DE MALTA MINI PONY MALTA 1200 ml", 5990.0, 8, "Bebidas",
                "https://exitocol.vtexassets.com/arquivos/ids/13173851-800-auto?v=637899411098230000&width=800&height=auto&aspect=true")
                .addToDatabase();
    }

    private String[] getRecord() {
        return new String[] {
                this.id,
                this.nombre,
                String.valueOf(this.precio),
                String.valueOf(this.cantidad),
                this.categoria,
                this.imageUrl
        };
    }

    public static Productos queryProductByName(String nombre) {
        List productList = db.queryTable(DatabaseManager.tables[DatabaseManager.PRODUCT], "nombre = ?", new String[]{ nombre } );
        return new Productos(
                (List) productList.get(0)
        );
    }

    public static Productos queryProductById(String id) {
        List productList = db.queryTable(DatabaseManager.tables[DatabaseManager.PRODUCT], "productoId = ?", new String[] { id });
        return new Productos(
                (List) productList.get(0)
        );
    }


    public void addToDatabase() {
        db.addRecord(new ContentValues(), DatabaseManager.tables[DatabaseManager.PRODUCT], tbl_product_fields, this.getRecord() );
    }

    private void updateDatabase() {
        db.updateRecord(new ContentValues(), DatabaseManager.tables[DatabaseManager.PRODUCT], tbl_product_fields,  this.getRecord() );
    }

    public Integer getQuantity() {
        return cantidad;
    }

    public void setQuantity(Integer candid) {
        this.cantidad = candid;
        this.updateDatabase();
    }

    public static ArrayList<Productos> fetchProducts() {
        List productList = db.getTable(DatabaseManager.tables[DatabaseManager.PRODUCT]);
        ArrayList<Productos> productArrayList = new ArrayList<Productos>();
        for (int i = 0; i < productList.size() ; i++) {
            List l = (List) productList.get(i);
            productArrayList.add(new Productos(
                    l.get(Productos.size * i).toString(),
                    l.get(Productos.size * i + 1).toString(),
                    Double.valueOf(l.get(Productos.size * i + 2).toString()),
                    Integer.valueOf(l.get(Productos.size * i + 3).toString()),
                    l.get(Productos.size * i + 4).toString(),
                    l.get(Productos.size * i + 5).toString()
            ));
        }
        return productArrayList;
    }
}