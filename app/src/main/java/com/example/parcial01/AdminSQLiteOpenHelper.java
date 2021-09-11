package com.example.parcial01;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table usuario (cedula text primary key, nombre text, apellido text, eps text, sintoma integer)");
        db.execSQL("create table usuario2 (cedula text primary key, nombre text, apellido text, edad integer, sexo text, nHemo text, correo text, positivo boolean)");
        Log.i("DB","create");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
