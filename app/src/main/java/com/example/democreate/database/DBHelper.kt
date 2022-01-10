package com.example.democreate.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.democreate.model.LatLongModel
import com.example.democreate.model.LocationModel

class DBHelper(context: Context?) : SQLiteOpenHelper(context, "LocationManagement.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL("create table Location" + "(id INTEGER primary key AUTOINCREMENT location_id TEXT ,locationName TEXT ,lat TEXT,long TEXT  )")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists Location")
    }

    fun insertDataLocation(locationName: String? , lat : Double? , long : Double? ): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put("locationName", locationName)
        contentValues.put("lat", lat)
        contentValues.put("long", long)
      //  contentValues.put("latLng", latLng)
        val result = db.insert("Location", null, contentValues)
        return if (result == -1L) false else true
    }

    @SuppressLint("Range")
    fun getData(): ArrayList<LocationModel> {

        val locationModelClassArrayList: ArrayList<LocationModel> = ArrayList<LocationModel>()

        val selectQuery = "SELECT  * FROM Location"

        val db = this.readableDatabase
        val c: Cursor = db.rawQuery(selectQuery, null)
        if (c.moveToFirst()) {
            do {
                val locationModelClass = LocationModel(
                    c.getString(c.getColumnIndex("id")),
                    c.getString(c.getColumnIndex("locationName"))
                )
                locationModelClassArrayList.add(locationModelClass)
            } while (c.moveToNext())

        }
        return locationModelClassArrayList
    }

    fun updateLocation(  locationName : String?){

        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name",locationName)

    }

    @SuppressLint("Range")
    fun getLetLong(): ArrayList<LatLongModel>? {
        val locationModelClassArrayList: ArrayList<LatLongModel> = ArrayList<LatLongModel>()

        val selectQuery = "SELECT  * FROM Location"

        val db = this.readableDatabase
        val c: Cursor = db.rawQuery(selectQuery, null)
        if (c.moveToFirst()) {
            do {


                val locationModelClass = LatLongModel(
                    c.getInt(c.getColumnIndex("id")) ,
                   c.getDouble(c.getColumnIndex("lat")),
                   c.getDouble(c.getColumnIndex("long"))
                )
                locationModelClassArrayList.add(locationModelClass)
            } while (c.moveToNext())

        }
        return locationModelClassArrayList
    }

    fun deleteData(id : String){

        var db : SQLiteDatabase = this.writableDatabase

        db.delete("Location", "id=?", arrayOf(id));
        db.close();


    }

}