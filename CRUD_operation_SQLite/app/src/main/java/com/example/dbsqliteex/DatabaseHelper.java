package com.example.dbsqliteex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "student_table";
    /*
    The above table will contain 4 columns id,name,surname and marks.
     */

//Columns of Student db
    public static final String col_1 = "ID";
    public static final String col_2 = "NAME";
    public static final String col_3 = "SURNAME";
    public static final String col_4 = "MARKS";

//This constructor will create Database with passed name as argument
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

    }

    //We will create table when onCreate method of this class is called
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       sqLiteDatabase.execSQL("CREATE TABLE " +TABLE_NAME +
                " (ID INTEGER PRIMARY KEY ,NAME TEXT NOT NULL,SURNAME TEXT,MARKS INTEGER NOT NULL);");
        //sqLiteDatabase.execSQL(("DROP TABLE " + TABLE_NAME));

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

onCreate(sqLiteDatabase);
    }

    public boolean insertData(String id,String name,String surname,String marks){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_1,id);
        contentValues.put(col_2,name); //put takes two arguments,1st -column name in which you want to insert,2nd - value that you want to insert
        contentValues.put(col_3,surname);
        contentValues.put(col_4,marks);
        long result = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);//insert takes three argument

        if(result== -1){
            return false;
        }
        else
            return true;
    }


    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM " +TABLE_NAME,
               null );

        return res;
    }

    public boolean updateData(
            String id,String name,String surname,String marks){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_1,id);
        contentValues.put(col_2,name); //put takes two arguments,1st -column name in which you want to insert,2nd - value that you want to insert
        contentValues.put(col_3,surname);
        contentValues.put(col_4,marks);

        /*
        Update function takes four arguments
        1) Table name
        2)Updated values
        3)Condition for update
        4)String array
         */
        long res = sqLiteDatabase.update(TABLE_NAME,contentValues,
                "ID = ?",new String[]{id});
        if(res==-1)
            return false;
        else
        return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Integer rows_affected;
        rows_affected = sqLiteDatabase.delete(TABLE_NAME,"ID=?",new String[]{id});//Here Integer return type of deleteData function bcoz delete function returns number of rows affected

        return rows_affected;
    }
}
