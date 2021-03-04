package edu.scse.draweractivity.entity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    private String TAG="DataBaseHelper";
    public DataBaseHelper(@Nullable Context context) {
        super(context, "notes_db",null,3);//db文件
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_files =
                "create table  Files(" +
                        "fileId int primary key," +
                        "filePath text not null)";
        String sql_notes=
                "create table Notes(" +
                        "id int primary key, " +
                        "noteType char check(notetype='0' or notetype='1' or notetype='2'), " +
                        "picPath text, " +
                        "textPath text, " +
                        "voicePath text, " +
                        "title text, " +
                        "fileId int, " +
                        "FOREIGN KEY (fileId) REFERENCES Files(fileId) )";
        String sql_flag=
                "create table Flag(" +
                        "id int not null," +
                        "flag varchar(20) not null," +
                        "FOREIGN KEY (id) REFERENCES Notes(id) ON DELETE CASCADE ON UPDATE CASCADE)";
        db.execSQL(sql_files);
        db.execSQL(sql_notes);
        db.execSQL(sql_flag);
        Log.i(TAG, "create Database------------->");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "update Database------------->");
    }
}
