package com.asynctask.eutd.rv;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcelable;
import android.support.annotation.IntegerRes;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ibrahem on 1/24/2017.
 */

public class SQL_DB extends SQLiteOpenHelper {
    public static final String DB_name = "ibrahem.db";
    public SQL_DB(Context context) {
        super(context, DB_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user (id INTEGER PRIMARY KEY AUTOINCREMENT , name TEXT , email TEXT ,NumberPhone TEXT , img TEXT, imgPath TEXT) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);

        Log.e("TAG onUpgrade : ", "oldVersion " + oldVersion + " newVersion : " + newVersion);

        /*switch(oldVersion) {
            case 1:
                //upgrade logic from version 1 to 2
            case 2:
                //upgrade logic from version 2 to 3
            case 3:
                //upgrade logic from version 3 to 4
                break;
            default:
                throw new IllegalStateException(
                        "onUpgrade() with unknown oldVersion " + oldVersion);
        }*/

        /*
        Notice the missing break statement in case 1 and 2. This is what I mean by incremental upgrade.

        Say if the old version is 2 and new version is 4, then the logic will upgrade the database from 2 to 3 and then to 4

        If old version is 3 and new version is 4, it will just run the upgrade logic for 3 to 4
        */



    }

    public boolean insertData(NatureModel model){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //contentValues.put("id" , model.getId());
        contentValues.put("name" , model.getTitle());
        contentValues.put("email" , model.getEmail());
        contentValues.put("NumberPhone" , model.getNumberPhone());

        if (model.getImageID() == 2) {
            contentValues.put("img", 2);
            contentValues.put("imgPath", model.getImageStr());
        }else {
            contentValues.put("img", model.getImageID());
            contentValues.put("imgPath", model.getImageStr());
        }

        long result = db.insert("user" , null,contentValues);

          if(result == -1)
               return false;
            else
               return  true;
    }

    public List<NatureModel> getAllRecord() {

        List<NatureModel> arrList = new ArrayList<NatureModel>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM user", null);


        Log.e("TAG : ", "SQL " + res.getColumnCount() + " " + res.getCount());

        if (res.getCount() == 0){

            GlobalData.CheckDB = true;
            return arrList;

        }
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            NatureModel model = new NatureModel();
            model.setId(Integer.parseInt(res.getString(0)));
            model.setTitle(res.getString(1));
            model.setEmail(res.getString(2));
            model.setNumberPhone(res.getString(3));
            model.setImageID(Integer.parseInt(res.getString(4)));
            model.setImageStr(res.getString(5));




            arrList.add(model);

            res.moveToNext();

        }

        return arrList;
    }
    public boolean update(NatureModel model){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id" , model.getId());
        contentValues.put("name" , model.getTitle());
        contentValues.put("email" , model.getEmail());
        contentValues.put("NumberPhone" , model.getNumberPhone());

        if (model.getImageID() == 2) {
            contentValues.put("img", 2);
            contentValues.put("imgPath", model.getImageStr());
        }else {
            contentValues.put("img", model.getImageID());
            contentValues.put("imgPath", model.getImageStr());
        }


          // db.update("user",contentValues ,"id = ?",new String[] {model.getId()} );

        long result = db.update("user",contentValues ,"id="+model.getId(),null );

        if(result == -1){
            return false ;
        }else {
            return true;
        }

    }


    public int delet(int id){
        SQLiteDatabase db = getWritableDatabase();

            String nId = id+"";
        // return db.delete("user","id= ?",new String[] {id});
        return db.delete("user","id="+nId,null);

    }
/*
    public MainActivity.model getRecord(String id){

        MainActivity.model model = new MainActivity.model();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM user where id=" + id ,null);


        Log.d("TAG Cursor", "getRecord() returned: " + res.getCount());

        res.getColumnCount();
        res.moveToFirst();
        while (res.isAfterLast() == false){

            String rowId = res.getString(0);
            String rowNmae = res.getString(1);
            String rowEmail = res.getString(2);

            model.email = rowEmail;
            model.id = rowId;
            model.name = rowNmae;

         //   arrList.add(rowId + " - " + rowNmae + " : " + rowEmail);

            res.moveToNext();

        }
        return model;

    }



     public int delet(String id){
         SQLiteDatabase db = getWritableDatabase();

        // return db.delete("user","id= ?",new String[] {id});
         return db.delete("user","id="+id,null);

     }

    public List<MainActivity.model> getAllRecordFromModel() {

        List<MainActivity.model> arrList = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM user", null);

        res.moveToFirst();
        while (res.isAfterLast() == false) {

            String rowId = res.getString(0);
            String rowNmae = res.getString(1);
            String rowEmail = res.getString(2);

            MainActivity.model model = new MainActivity.model();

            model.email = rowEmail;
            model.id = rowId;
            model.name = rowNmae;

            arrList.add(model);

            res.moveToNext();

        }

        return arrList;
    }
*/
}
