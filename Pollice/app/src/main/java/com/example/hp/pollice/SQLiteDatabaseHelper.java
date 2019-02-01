package com.example.hp.pollice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.File;
import java.sql.ResultSet;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {
    private static final String database_name="pollice";
    private static final String table_name="user";
    private static final int database_version=8;
    private static final String email="email";
    private static final String password="password";
    private static final String create_table="CREATE TABLE `"+table_name+"` ( `"+email+"` VARCHAR(40), `"+password+"` VARCHAR(40))";
    private static final String drop_table="drop table if exists "+table_name;
    private static final String check_table="SELECT * FROM "+table_name;
    private Context context;



    public SQLiteDatabaseHelper(Context context) {
        super(context, database_name, null, database_version);
        this.context=context;
    }
    SQLiteDatabase sd=this.getWritableDatabase();
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }


    public void drop(){
        try{
            sd.execSQL(drop_table);
            Toast.makeText(context,"Table droped", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void create(String uemail, String upassdord){
        boolean b=true;
        try{
            sd.execSQL(check_table);
            b=false;
            Toast.makeText(context, "Table found.",Toast.LENGTH_LONG).show();
        }catch(Exception e){}
        if(b) {
            try{
                sd.execSQL(create_table);
                Toast.makeText(context, "Database table created",Toast.LENGTH_LONG).show();
            }catch(Exception e){
                //Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        }
        Cursor cursor=sd.rawQuery("SELECT *FROM "+table_name+" WHERE "+email+" like '"+uemail+"' ",null);
        if(cursor.getCount()!=1) {
            ContentValues cv = new ContentValues();
            cv.put(email, uemail);
            cv.put(password, upassdord);
            Long id = sd.insert(table_name, null, cv);
            if (id == -1) {
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
            }
            Toast.makeText(context, "Data added", Toast.LENGTH_LONG).show();
        }
    }
    public Cursor check_user(){
        try{
            Cursor cursor=sd.rawQuery("SELECT *FROM "+table_name,null);
            return cursor;
        }catch(Exception e){
            return null;
        }
    }
}
