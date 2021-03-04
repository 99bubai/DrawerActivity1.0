package edu.scse.draweractivity.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import edu.scse.draweractivity.R;
import edu.scse.draweractivity.entity.DataBaseHelper;
import edu.scse.draweractivity.entity.NoteTitleData;
import edu.scse.draweractivity.entity.TitleFactory;
import edu.scse.draweractivity.ui.FlagLayout;

public class LocalNotes extends AppCompatActivity {
    private String      TAG="LocalNotes";
    private ImageButton button_edit,button_favorite,button_explain,button_notification,button_back;
    private TextView textView_title,textView_body;
    private FlagLayout flagLayout;
    private String intitle;
    private String inid;
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase db;
    private String noteType,title,textPath;
    private String[] flag=new String[100];

    private  void init(){
        button_back=findViewById(R.id.button_local_back);
        button_edit=findViewById(R.id.button_local_edit);
        button_explain=findViewById(R.id.button_local_explain);
        button_favorite=findViewById(R.id.button_local_favorite);
        button_notification=findViewById(R.id.button_local_notification);
        textView_body=findViewById(R.id.textView_local_body);
        textView_title=findViewById(R.id.textView_local_title);
        flagLayout=(FlagLayout)findViewById(R.id.flagLayout_local);
        //获取intent携带的数据

        textView_title.setText(title);
        textView_body.setText(textPath);

        initFlagLayout();
    }
    private void initFlagLayout() {
        flagLayout = (FlagLayout) findViewById(R.id.flagLayout_local);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        for (int i = 0; flag[i]!=null; i++) {
            int j=i;
            TextView view = new TextView(this);
            view.setText(flag[i]);
            view.setTextColor(Color.WHITE);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.flag));
            flagLayout.addView(view, lp);
            //标签点击事件(点击标签跳入搜索结果界面,显示从数据库拉取的所有包含该tag的数据)
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG,"tag:"+flag[j]);
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_local_main);

        Intent         intent = getIntent();
        intitle=intent.getStringExtra("title");
        inid= intent.getStringExtra("id");
        //想办法读取title和正文内容
        dataBaseHelper=new DataBaseHelper(this);
        db =dataBaseHelper.getReadableDatabase();
        String[] Notes=new String[]{"noteType","title","textPath"};
        String[] Flag=new String[]{"flag"};
        int flagcount;
        Cursor cursor_notes=db.query("Notes",Notes,"id=?",new String[]{inid},
                null,null,null);
        Cursor cursor_flag=db.query("flag",Flag,"id=?",new String[]{inid},
                null,null,null);
        cursor_notes.moveToFirst ();
        noteType=cursor_notes.getString(cursor_notes.getColumnIndex("noteType"));
        title=cursor_notes.getString(cursor_notes.getColumnIndex("title"));
        textPath=cursor_notes.getString(cursor_notes.getColumnIndex("textPath"));
        flagcount=cursor_flag.getCount();
        for (int i=0;i<flagcount;i++){
            if(cursor_flag.moveToFirst ()) {
                cursor_flag.move (i);
                flag[i]=cursor_flag.getString(cursor_flag.getColumnIndex("flag"));
            }
        }

        init();
        db.close();

    }
}