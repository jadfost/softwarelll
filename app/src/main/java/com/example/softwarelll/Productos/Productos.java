package com.example.softwarelll.Productos;

import android.content.ContentValues;
import android.content.Context;

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
            "productId", "nombre", "precio", "cantidad", "categoria", "imageUrl"
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

    @Override
    public String toString() {
        return  "Product.toString()>> id: " + this.id + ", nombre: " + this.nombre +
                ", categoria: " + this.categoria +
                ", precio: " + this.precio +
                ", cantidad: " + this.cantidad +
                ", imageUrl: " + this.imageUrl;
    }

    public static void initProducts() {
        db.recreateOneTable(DatabaseManager.PRODUCT);
        new Productos( "1", "Introduction to Algorithms, Hardcover – Jul 31 2009", 28.67, 0, "Libros",
                "https://images-na.ssl-images-amazon.com/images/I/51fgDX37U7L._SX442_BO1,204,203,200_.jpg")
                .addToDatabase();
        new Productos( "2", "Samsung Galaxy S9 64GB", 719.99, 0, "Celulares",
                "https://target.scene7.com/is/image/Target/53438666")
                .addToDatabase();
        new Productos( "3", "Logitech Wireless Keyboard (K360) - Black", 29.99, 0, "Accesorios de Computador",
                "https://multimedia.bbycastatic.ca/multimedia/products/500x500/102/10201/10201435.jpg")
                .addToDatabase();
        new Productos( "4", "Logitech M720 Triathlon Wireless Optical Mouse - Black", 49.99, 0, "Accesorios de Computador",
                "https://multimedia.bbycastatic.ca/multimedia/products/1500x1500/104/10482/10482681.jpg")
                .addToDatabase();
        new Productos("5", "Google Home Mini - Charcoal", 79.99, 0, "Hogar",
                "https://multimedia.bbycastatic.ca/multimedia/products/500x500/116/11615/11615336.jpg")
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

    public void setQuantity(Integer cantidad) {
        this.cantidad = cantidad;
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
            /*
            for (int j = 0; j < Product.size ; j++) {
                //Log.d("timbo.j " + j, l.get(j).toString());
                Log.d("timbo result", l.get(Product.size * i + j).toString());
            }
            Log.d("timbo.fetchProducts " + i, productList.get(i).toString());
            //productArrayList.add((Product) productList.get(i));
            */
        }
        return productArrayList;
        //return new ArrayList<Product>();
    }
}