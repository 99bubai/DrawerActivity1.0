package edu.scse.draweractivity.entity;

import android.content.ClipData;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import edu.scse.draweractivity.Activity.MainActivity;

public class TitleFactory {
    public static      String[] Notestitle=new String[1000],Notestype=new String[1000],Notesid=new String[1000];
    public static int      titlecount;
    private static DataBaseHelper dbHelper;

    public static void  queryDatabase(SQLiteDatabase db){
        //db=SQLiteDatabase.openOrCreateDatabase("/data/data/edu.scse.draweractivity/databases/test_db",null);
        String[] strings = new String[]{"title","id","noteType"};
        Cursor cursor = db.query ("Notes",strings,null,
                null,null,null,null);
        titlecount = cursor.getCount ();
        for (int i = 0; i < titlecount; i++) {
            if(cursor.moveToFirst ()) {
                cursor.move (i);
                Notestitle[i]=cursor.getString(cursor.getColumnIndex("title"));
                Notesid[i]=cursor.getString(cursor.getColumnIndex("id"));
                Notestype[i]=cursor.getString(cursor.getColumnIndex("noteType"));
            }
        }
        db.close();
    }
    public static List<NoteTitleData> createItem(SQLiteDatabase db){
        List<NoteTitleData> items = new ArrayList<>();
        queryDatabase (db);
        for (int i=0;i < titlecount;i++){
            String title = Notestitle[i];
            String id = Notesid[i];
            String type = Notestype[i];
            items.add(new NoteTitleData(title,id,type));
        }
        return items;
    }

}

