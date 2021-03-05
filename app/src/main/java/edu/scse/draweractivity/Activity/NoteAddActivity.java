package edu.scse.draweractivity.Activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import edu.scse.draweractivity.Adapter.FlagAdapter;
import edu.scse.draweractivity.R;
import edu.scse.draweractivity.entity.DataBaseHelper;
import edu.scse.draweractivity.ui.FlagLayout;
import edu.scse.draweractivity.ui.notes.NotesFragment;

import android.view.ViewGroup.LayoutParams;
public class NoteAddActivity extends AppCompatActivity {
    private String TAG="NoteAddActivity";
    //private LinearLayoutManager linearLayoutManager;
    private FlagLayout flagLayout;
    private String debugFlag[]={"我也不知道","为什么组件会出错"};
    private ImageButton         button_select,button_favorite,button_explain,
            button_picture,button_attachment,button_notification,button_back;
    private EditText editText_title,editText_body;
    private Spinner spinner_type;
    private String title,body;
    private String[] noteType;
    private static int position=0;
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase db;
    private void init(){
        dataBaseHelper=new DataBaseHelper(this);
        db =dataBaseHelper.getWritableDatabase();

        //recyclerView_add=(RecyclerView)findViewById(R.id.recyclerView_add);
        //flagLayout=(FlagLayout)findViewById(R.id.flagLayout_notesadd);
        {
            button_select = findViewById(R.id.button_add_select);
            button_favorite = findViewById(R.id.button_add_favorite);
            button_explain = findViewById(R.id.button_add_explain);
            button_picture = findViewById(R.id.button_add_picture);
            button_attachment = findViewById(R.id.button_add_attachment);
            button_notification = findViewById(R.id.button_add_notification);
            button_back = findViewById(R.id.button_add_back);
            editText_body = findViewById(R.id.editText_add_body);
            editText_title = findViewById(R.id.editText_add_title);
            spinner_type = findViewById(R.id.spinner_add_type);
        }//注册组件
        //点击事件
        button_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=editText_title.getText().toString();
                body=editText_body.getText().toString();
                noteType=getResources().getStringArray(R.array.type);
                Log.d(TAG, "title:"+title+",body:"+body+",type:"+noteType[position]);
                //将标题和正文内容写入数据库
                db.execSQL("insert into Notes values(null,?,null,?,null,?,null)",
                        new String[]{String.valueOf(position),body,title});
                //将标签内容写入数据库
                //获取才插入的内容的id
                Cursor cursor = db.query("Notes", null, null,
                        null, null, null, "ID DESC");
                if(cursor.moveToNext())
                {
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    // 这个id就是最大值
                    Log.d(TAG, "id:"+id);
                }

                Intent intent=new Intent(NoteAddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });//添加日记
        button_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "favorite");
            }
        });//喜爱
        button_explain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"explain");//这里如果多次调用initFlagLayout会导致测量出错
            }
        });//添加标签
        button_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"picture");
            }
        });//上传图片
        button_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"attachment");
            }
        });//
        button_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"notification");
            }
        });
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"back");
                Intent intent=new Intent(NoteAddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });//返回
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                position=pos;
                String[] type = getResources().getStringArray(R.array.type);
                //Log.d(TAG,"pos:"+pos+",type:"+type);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        initFlagLayout();
    }
    private void initFlagLayout() {
        flagLayout = (FlagLayout) findViewById(R.id.flagLayout_notesadd);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        for (int i = 0; i < debugFlag.length; i++) {
            int j=i;
            TextView view = new TextView(this);
            view.setText(debugFlag[i]);
            view.setTextColor(Color.WHITE);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.flag));
            flagLayout.addView(view, lp);
            //标签点击事件(点击标签跳入搜索结果界面,显示从数据库拉取的所有包含该tag的数据)
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG,"tag:"+debugFlag[j]);
                }
            });
        }
    } //界面实现，之前是用静态数组，准备实现动态显示，在输入标签后再显示
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_add_main);
        init();

        //GridLayoutManager girdLayoutManager=new GridLayoutManager(this,4);
        //recyclerView_add.setLayoutManager(girdLayoutManager);

        /*
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        recyclerView_add.setLayoutManager(linearLayoutManager);
        flagAdapter = new FlagAdapter(this);
        recyclerView_add.setAdapter(flagAdapter);//item之间存在巨大空白
        */
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(NoteAddActivity.this, MainActivity.class);
        startActivity(intent);
    }


}
