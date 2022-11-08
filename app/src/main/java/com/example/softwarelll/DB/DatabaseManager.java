package com.example.softwarelll.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {
    //
    private static final String DATABASE_NAME = "com.example.proyectosoftwarelll";
    private static final int DATABASE_VERSION = 17;
    //

    public static final String[] tables ={"tbl_customer", "tbl_product", "tbl_order"};
    public static final Integer CLIENTE = 0;
    public static final Integer PRODUCT = 1;
    public static final Integer ORDER = 2;

    private static final String[] tableCreatorString =
            {"CREATE TABLE " + DatabaseManager.tables[CLIENTE] + " (clienteId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, contrasena TEXT);",
                    "CREATE TABLE " + DatabaseManager.tables[PRODUCT] + " (productoId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, precio TEXT," +
                            " cantidad INTEGER, categoria TEXT, imageUrl TEXT);",
                    "CREATE TABLE " + DatabaseManager.tables[ORDER] + " (orderId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "clienteId INTEGER KEY REFERENCES tbl_customer(clienteId), " +
                            "productoId INTEGER KEY REFERENCES tbl_product(productoId), " +
                            "fechaorden DATE, estado TEXT," +
                            "precio FLOAT, cantidad INTEGER );"
                    ,};

    // constructor de clases
    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    //inicializa los nombres de las tablas de la base de datos y las sentencias DDL
    public void dbInitialize( )
    {
    }

    // Crear tablas
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Eliminar tablas existentes
        for (String table : tables) db.execSQL("DROP TABLE IF EXISTS " + table);
        // Crearlas
        for (int i=0;i<tables.length;i++)
            db.execSQL(tableCreatorString[i]);
    }

    public void recreateOneTable(int i) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + tables[i]);
        db.execSQL(tableCreatorString[i]);
        db.close();
    }

    //crear la base de datos
    public void createDatabase(Context context)
    {
        SQLiteDatabase mDatabase;
        mDatabase = context.openOrCreateDatabase(
                DATABASE_NAME,
                SQLiteDatabase.CREATE_IF_NECESSARY,
                null);

    }
    //eliminar la base de datos
    public void deleteDatabase(Context context)
    {
        context.deleteDatabase(DATABASE_NAME);
    }

    // Actualización de la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar tablas existentes
        for (String table : tables) db.execSQL("DROP TABLE IF EXISTS " + table);

        // Crear tablas de nuevo
        onCreate(db);
    }
    /////////////////////////
    // operaciones de base de datos
    /////////////////////////
    // Agregar un nuevo registro
    public long addRecord(ContentValues values, String tableName, String fields[], String record[]) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i=1;i<record.length;i++)
            values.put(fields[i], record[i]);
        // Insertar la fila
        long id = db.insert(tableName, null, values);
        db.close(); //cerrar la conexion a la base de datos
        return id;
    }


    // Leer todos los registros
    public List getTable(String tableName) {
        List table = new ArrayList(); //para almacenar todas las filas
        // Selecciona todos los registros
        String selectQuery = "SELECT  * FROM " + tableName;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList row=new ArrayList(); //para almacenar una fila
        //desplazarse por las filas y almacenar cada fila en un objeto de lista de matriz
        if (cursor.moveToFirst())
        {
            do
            { // por cada fila
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    row.add(cursor.getString(i));
                }

                //if (!table.contains(row)) {
                    table.add(row); //añadir fila a la lista
                //}

            } while (cursor.moveToNext());
        }
        cursor.close();

        // devuelve la tabla como una lista
        return table;
    }

    public List queryTable(String tableName, String queryWhere, String[] selectionArgs) {
        List table = new ArrayList(); //para almacenar todas las filas
        // Selecciona todos los registros
        String selectQuery = "SELECT  * FROM " + tableName + " WHERE " + queryWhere;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, selectionArgs);
        ArrayList row=new ArrayList(); //para almacenar una fila
        //desplazarse por las filas y almacenar cada fila en un objeto de lista de matriz
        if (cursor.moveToFirst())
        {
            do
            { // por cada fila
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    row.add(cursor.getString(i));
                }

                //if (!table.contains(row)) {
                table.add(row); //añadir fila a la lista
                //}

            } while (cursor.moveToNext());
        }
        cursor.close();

        // devuelve la tabla como una lista
        return table;
    }

    // Actualizar un registro
    public void updateRecord(ContentValues values, String tableName, String[] fields, String[] record) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i=1;i<record.length;i++)
            values.put(fields[i], record[i]);

        // actualizando fila con id dado = record[0]
        db.update(tableName, values, fields[0] + " = ?",
                new String[]{record[0]});
    }

    // Eliminar un registro con una identificación dada
    public void deleteRecord(String tableName, String idName, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, idName + " = ?",
                new String[] { id });
        db.close();
    }



}