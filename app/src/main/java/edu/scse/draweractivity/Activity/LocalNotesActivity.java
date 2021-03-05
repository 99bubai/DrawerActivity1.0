package edu.scse.draweractivity.Activity;
//显示本地sqlite存储的notes内容
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
public class LocalNotesActivity extends AppCompatActivity {
    private String
            TAG="LocalNotes",
            intitle,inid,
            noteType,title,textPath;
    private String[]
            flag=new String[100];
    private ImageButton
            button_edit,button_favorite,button_explain,button_notification,button_back;
    private TextView
            textView_title,textView_body;
    private FlagLayout
            flagLayout;
    private DataBaseHelper
            dataBaseHelper;
    private SQLiteDatabase
            db;
    private  void init(){
        button_back=findViewById(R.id.button_local_back);
        button_edit=findViewById(R.id.button_local_edit);
        button_explain=findViewById(R.id.button_local_explain);
        button_favorite=findViewById(R.id.button_local_favorite);
        button_notification=findViewById(R.id.button_local_notification);
        textView_body=findViewById(R.id.textView_local_body);
        textView_title=findViewById(R.id.textView_local_title);
        flagLayout=(FlagLayout)findViewById(R.id.flagLayout_local);
        //显示获取到的标题和正文
        textView_title.setText(title);
        textView_body.setText(textPath);
        //初始化标签栏
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
        //向组件添加view
        for (int i = 0; flag[i]!=null; i++) {
            int j=i;
            TextView view = new TextView(this);
            view.setText(flag[i]);
            view.setTextColor(Color.WHITE);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.flag));
            flagLayout.addView(view, lp);
            //标签点击事件(点击标签跳入搜索结果界面,显示从数据库拉取的所有包含该tag的数据)还未实现
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
        //获取intent携带的数据，用来进行数据库查询
        Intent         intent = getIntent();
        intitle=intent.getStringExtra("title");
        inid= intent.getStringExtra("id");
        //数据库操作
        dataBaseHelper=new DataBaseHelper(this);
        db =dataBaseHelper.getReadableDatabase();
        String[] Notes=new String[]{"noteType","title","textPath"};
        String[] Flag=new String[]{"flag"};
        int flagcount;
        //查询id对应的数据的标题、正文、类型以及标签
        Cursor cursor_notes=db.query("Notes",Notes,"id=?",new String[]{inid},
                null,null,null);
        Cursor cursor_flag=db.query("flag",Flag,"id=?",new String[]{inid},
                null,null,null);
        //将查询的内容存入活动的参数中
        //notes表的数据
        cursor_notes.moveToFirst ();//注意：要用这一步不然接下来读取会报错
        noteType=cursor_notes.getString(cursor_notes.getColumnIndex("noteType"));
        title=cursor_notes.getString(cursor_notes.getColumnIndex("title"));
        textPath=cursor_notes.getString(cursor_notes.getColumnIndex("textPath"));
        //flag表的数据
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