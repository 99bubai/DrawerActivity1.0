package edu.scse.draweractivity.Activity;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import edu.scse.draweractivity.Adapter.FlagAdapter;
import edu.scse.draweractivity.R;
import edu.scse.draweractivity.entity.DataBaseHelper;
import edu.scse.draweractivity.ui.FlagLayout;
import edu.scse.draweractivity.ui.notes.NotesFragment;

import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import java.io.File;

public class NoteAddActivity extends AppCompatActivity {
    private String TAG="NoteAddActivity";
    //private LinearLayoutManager linearLayoutManager;
    private FlagLayout flagLayout;
    private String debugFlag[]=new String[10];
    private ImageButton         button_select,button_favorite,button_explain,
            button_picture,button_attachment,button_notification,button_back;
    private EditText editText_title,editText_body;
    private Spinner spinner_type;
    private String title,body;
    private String[] noteType;
    private static int position=0;
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase db;
    private static int debugposition=0;
    private int favorite=0;
    private void init(){
        dataBaseHelper=new DataBaseHelper(this);
        db =dataBaseHelper.getWritableDatabase();

        {
            flagLayout = (FlagLayout) findViewById(R.id.flagLayout_notesadd);
            button_select = findViewById(R.id.button_add_select);
            button_favorite = findViewById(R.id.button_add_favorite);
            button_favorite.setImageResource(R.drawable.ic_add_favorite);
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
                db.execSQL("insert into Notes values(null,?,null,?,null,?,?,null)",
                        new String[]{String.valueOf(position),body,title,String.valueOf(favorite)});

                //获取才插入的内容的id
                Cursor cursor = db.query("Notes", null, null,
                        null, null, null, "ID DESC");
                if(cursor.moveToNext())
                {
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    // 这个id就是最大值
                    Log.d(TAG, "id:"+id);
                }
                //将标签内容写入数据库（网络同步

                //将喜爱状态写入本地数据库（这个数据需要网络同步

                //将闹钟状态写入本地数据库（这个数据不需要网络同步
                Intent intent=new Intent(NoteAddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });//添加日记
        button_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favorite==0){
                    button_favorite.setImageResource(R.drawable.ic_add_favorite_filling);
                    favorite=1;
                }else if(favorite==1){
                    button_favorite.setImageResource(R.drawable.ic_add_favorite);
                    favorite=0;
                }
                Log.d(TAG, "favorite");
            }
        });//喜爱//数据库里添加一列记录是否是喜爱笔记（等数据库一起实现
        button_explain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                lp.leftMargin = 5;lp.rightMargin = 5;lp.topMargin = 5;lp.bottomMargin = 5;
                TextView view = new TextView(NoteAddActivity.this);
                view.setTextColor(Color.WHITE);
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.flag));

                Log.d(TAG,"explain");
                final EditText editText=new EditText(NoteAddActivity.this);
                AlertDialog.Builder dialog=new AlertDialog.Builder(NoteAddActivity.this);
                dialog.setTitle("添加标签");
                dialog.setView(editText);
                dialog.setCancelable(true);//按返回键取消
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String string=editText.getText().toString();
                        debugFlag[debugposition]=string;

                        view.setText(debugFlag[debugposition++]);
                        flagLayout.addView(view, lp);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
            }
        });//添加标签
        button_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"picture");
                openAlbum();
            }
        });//上传图片
        button_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"attachment");
            }
        });//附件
        button_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"notification");
            }
        });//闹钟
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
        //initFlagLayout();
    }
    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,2);//启动相册
    }

    private void initFlagLayout() {
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
    } //初始化显示用,现在使用动态添加,暂时注释

    //接收返回消息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode== 2){
            Uri uri=data.getData();
            String url=String.valueOf(uri);
            Log.d(TAG, "onActivityResult: "+url);
            String[] proj = {MediaStore.Images.Media.DATA};

 Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
 actualimagecursor.moveToFirst();
String img_path = actualimagecursor.getString(actual_image_column_index);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_add_main);
        init();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
