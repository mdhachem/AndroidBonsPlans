package com.example.mohameddhiahachem.bonplanapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mohameddhiahachem.bonplanapp.Entity.User;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "bonplan.db";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table user " +
                        "(id integer primary key , id_user integer, email text, password text,first_name text,last_name text, image text,address text,telephone text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    public boolean insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_user", user.getId());
        contentValues.put("email", user.getEmail());
        contentValues.put("password", user.getPassword());
        contentValues.put("first_name", user.getFirstName());
        contentValues.put("last_name", user.getLastName());
        contentValues.put("image", user.getImage());
        contentValues.put("address", user.getAddress());
        contentValues.put("telephone", user.getTelephone());
        db.insert("user", null, contentValues);
        return true;
    }


    public User getUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        String req = "select * from user ";
        Cursor cursor = db.rawQuery(req,null);
        if(cursor == null || cursor.getCount()==0) return null;
        cursor.moveToFirst();
        int id_user = cursor.getInt(1);
        String email = cursor.getString(2);
        String password = cursor.getString(3);
        String first_name = cursor.getString(4);
        String last_name = cursor.getString(5);
        String image = cursor.getString(6);
        String address = cursor.getString(7);
        String telephone = cursor.getString(8);
        User user = new User(id_user, email, password,first_name,last_name,image,address,telephone);
        cursor.close();

        return user;
    }

    public boolean isExist(){
        SQLiteDatabase db = this.getReadableDatabase();
        String req = "select * from user";
        Cursor cursor = db.rawQuery(req,null);
        if(cursor.getCount()== 0 ) return false;

        return true;
    }


    public void delete() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "delete from user";
        db.execSQL(query);
    }



}
