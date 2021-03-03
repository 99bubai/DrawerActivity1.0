package edu.scse.draweractivity.entity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    private String TAG="DataBaseHelper";
    public DataBaseHelper(@Nullable Context context) {
        super(context, "test_db",null,3);//db文件
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table test_table(title text)";
        Log.i(TAG, "create Database------------->");
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "update Database------------->");
    }
}
