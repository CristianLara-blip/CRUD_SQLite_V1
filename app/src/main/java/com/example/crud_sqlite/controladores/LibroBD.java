package com.example.crud_sqlite.controladores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.crud_sqlite.modelos.Libro;

import java.util.ArrayList;
import java.util.List;

public class LibroBD extends SQLiteOpenHelper implements ILibroBD {
    Context contexto;
    private List<Libro> librosList = new ArrayList<>();

    public LibroBD(@Nullable Context context, @Nullable String name,
                   @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        this.contexto = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase) {
        String sql = "CREATE TABLE libros (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "titulo TEXT, "+
                "subtitulo TEXT,"+
                "isbn TEXT,"+
                "autor TEXT,"+
                "anio INTEGER,"+
                "precio REAL )";
        sqliteDatabase.execSQL( sql);
        String insert = "INSERT INTO libros VALUES (null,"+
                "'Como porgranar en Java', "+
                "'Más de 80 ejemplos', " +
                "'987654321', "+
                "'Deitel & Deitel', 2008, 145888)";
        sqliteDatabase.execSQL( insert );
        insert = "INSERT INTO libros VALUES (null, "+
                "'El gran libro de Android',"+
                "'Android con ejemplos',"+
               "'123456789', " +
                "'Jesús Tomás G.', 2828, 135888)";
        sqliteDatabase.execSQL( insert );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public Libro elemento (int id) {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT FORM libros WHERE _id=" + id;
        Cursor cursor = database.rawQuery(sql, null);
        try {
            if (cursor.moveToNext())
                return extraerlibro(cursor);
            else
                return null;
        } catch (Exception e) {
            Log.d("TAG", "Error elmento (id) LibroBD" + e.getMessage() );
            throw e;
        } finally {
            if (cursor !=null )cursor.close();
        }
    }

    private Libro extraerlibro (Cursor cursor) {
        Libro libro = new Libro();
        libro.setId(cursor.getInt(0));
        libro.setTitulo(cursor.getString(1));
        libro.setSubtitulo(cursor.getString(2));
        libro.setIsbn(cursor.getString(3) );
        libro.setAuto(cursor.getString(4));
        libro.setAnioPublicacion ( cursor.getInt(5));
        libro.setPrecio(cursor.getDouble(6));
        return libro;

    }

    @Override
    public Libro elemento(String title) {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT FORM libros WHERE titulo='" + title + "'";
        Cursor cursor = database.rawQuery(sql, null);
        try {
            if (cursor.moveToNext())
                return extraerlibro(cursor);
            else
                return null;
        } catch (Exception e) {
            Log.d("TAG", "Error elmento (title) LibroBD" + e.getMessage() );
            throw e;
        } finally {
            if (cursor !=null )cursor.close();
        }
    }

    @Override
    public List<Libro> lista() {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM libros ORDER BY titulo ASC";
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()){
            do{
                librosList.add(
                        new Libro(cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getInt(5),
                                cursor.getDouble(6) )
                );
            }while(cursor.moveToNext());
        }
        cursor.close();
        return librosList;
    }

    @Override
    public void agregar(Libro book) {
        SQLiteDatabase database= getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("titulo", book.getTitulo());
        values.put("subtitulo", book.getTitulo());
        values.put("autor", book.getTitulo());
        values.put("isbn", book.getTitulo());
        values.put("anio", book.getTitulo());
        values.put("precio", book.getTitulo());
        database.insert("libros", null, values);
    }

    @Override
    public void actualizar(int id, Libro book) {
        SQLiteDatabase database= getWritableDatabase();
        String[] parametros =  {String.valueOf(id)};
        ContentValues values= new ContentValues();
        values.put("titulo", book.getTitulo());
        values.put("subtitulo", book.getTitulo());
        values.put("autor", book.getTitulo());
        values.put("isbn", book.getTitulo());
        values.put("anio", book.getTitulo());
        values.put("precio", book.getTitulo());
        database.update("libros", values, "_id=?", parametros);
    }

    @Override
    public void borrar(int id) {
        SQLiteDatabase database = getWritableDatabase();
        String[] parametros = {String.valueOf(id)};
        database.delete("libros", "_id=?", parametros);

    }

}
