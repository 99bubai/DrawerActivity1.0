package edu.scse.draweractivity.Activity;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.scse.draweractivity.Adapter.FileAdapter;
import edu.scse.draweractivity.R;
import edu.scse.draweractivity.entity.DataBaseHelper;
import edu.scse.draweractivity.entity.GetPathFromUri4kitkat;
import edu.scse.draweractivity.ui.FlagLayout;

import android.view.ViewGroup.LayoutParams;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
    private int favorite=0,notification=0;
    private RecyclerView recyclerView_file;
    private FileAdapter fileAdapter;
    private List<String> fileList=new ArrayList<String>();
    private List<String> urilist=new ArrayList<>();

    private void init(){
        dataBaseHelper=new DataBaseHelper(this);
        db =dataBaseHelper.getWritableDatabase();
        {
            recyclerView_file=findViewById(R.id.recyclerView_file);
            flagLayout = (FlagLayout) findViewById(R.id.flagLayout_notesadd);
            button_select = findViewById(R.id.button_add_select);
            button_favorite = findViewById(R.id.button_add_favorite);
            button_favorite.setImageResource(R.drawable.ic_add_favorite);
            button_explain = findViewById(R.id.button_add_explain);
            button_picture = findViewById(R.id.button_add_picture);
            button_attachment = findViewById(R.id.button_add_attachment);
            button_notification = findViewById(R.id.button_add_notification);
            button_notification.setImageResource(R.drawable.ic_add_notification);
            button_back = findViewById(R.id.button_add_back);
            editText_body = findViewById(R.id.editText_add_body);
            editText_title = findViewById(R.id.editText_add_title);
            spinner_type = findViewById(R.id.spinner_add_type);
        }//注册组件
        //点击事件
        {
        button_select.setOnClickListener(new View.OnClickListener() {
            int cursorback_id;
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
                    cursorback_id = cursor.getInt(cursor.getColumnIndex("id"));
                    // 这个id就是最大值
                    Log.d(TAG, "id:"+cursorback_id);
                }
                //将标签内容写入数据库（本地
                for(int p=0;debugFlag[p]!=null;p++){
                    db.execSQL("insert into Flag values(?,?)",new String[]{
                            String.valueOf(cursorback_id),debugFlag[p]});
                    Log.d(TAG, "debugFlag"+p+":"+debugFlag[p]);
                }
                for(int p=0;p<fileList.size();p++){
                    db.execSQL("insert into Files values(null,?,?)",new String[]{String.valueOf(cursorback_id),fileList.get(p)});
                    Log.d(TAG, "fileListAll: "+fileList.get(p));
                }
                debugposition=0;//static值复原

                //将标签内容写入数据库（网络同步

                //将喜爱状态写入本地数据库（这个数据需要网络同步

                //将闹钟状态写入本地数据库（这个数据不需要网络同步

                //将文件路径写入数据库



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
                if(notification==0){
                    button_notification.setImageResource(R.drawable.ic_add_notification_filling);
                    notification=1;
                    //自定义时间选择器，重写了cancel方法，cancel后更改图标
                    TimePickerDialogSelf timePickerDialog=new TimePickerDialogSelf(NoteAddActivity.this,
                            new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        }
                    },0,0,true);
                    timePickerDialog.show();
                }else if(notification==1){
                    button_notification.setImageResource(R.drawable.ic_add_notification);
                    notification=0;
                }
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
        }
        //initFlagLayout();

        LinearLayoutManager linearLayout=new LinearLayoutManager(NoteAddActivity.this);
        recyclerView_file.setLayoutManager(linearLayout);
        fileAdapter=new FileAdapter(NoteAddActivity.this,fileList);
        recyclerView_file.setAdapter(fileAdapter);

    }
    private void openAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK);//用action_pick才会返回带data的uri
        intent.setType("image/*");
        startActivityForResult(intent,1);//启动相册//需要加一个弹出框让用户选择相机
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
        super.onActivityResult(requestCode, resultCode, data);
        if (data==null) return;
        if(requestCode== 1){//相册返回
            Uri uri=data.getData();
            urilist.add(String.valueOf(uri));
            String string_uri=String.valueOf(uri);
            String url= GetPathFromUri4kitkat.getPath(NoteAddActivity.this,uri);
            fileList.add(url);
            Log.d(TAG, "Uri:"+string_uri);
            Log.d(TAG, "file: "+url);
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_add_main);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(fileList.size()!=0){
            fileAdapter=new FileAdapter(NoteAddActivity.this,fileList);
            recyclerView_file.setAdapter(fileAdapter);
        }//不是初次加载
        fileAdapter.setOnItemClickListener(new FileAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //设置intent的Action属性
                intent.setAction(Intent.ACTION_VIEW);
                //获取文件file的MIME类型
                String type = "image/jpeg";
                //设置intent的data和Type属性。
                //intent.setDataAndType(Uri.parse(fileList.get(position)), type);
                intent.setDataAndType(Uri.parse(urilist.get(position)), type);
                //跳转
                startActivity(intent);
            }
        });
        fileAdapter.setOnItemLongClickListener(new FileAdapter.OnItemLongClickListener() {
            @Override
            public void onClick(int position, View view) {
            }
        });

        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //重写时间选择器
    private final class TimePickerDialogSelf extends TimePickerDialog {
        public TimePickerDialogSelf(Context context, OnTimeSetListener listener, int hourOfDay, int minute, boolean is24HourView) {
            super(context, listener, hourOfDay, minute, is24HourView);
        }
        public TimePickerDialogSelf(Context context, int themeResId, OnTimeSetListener listener, int hourOfDay, int minute, boolean is24HourView) {
            super(context, themeResId, listener, hourOfDay, minute, is24HourView);
        }
        //重写cancel后事件，使图标改变
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which==BUTTON_NEGATIVE){
                button_notification.setImageResource(R.drawable.ic_add_notification);
                notification=0;
            }
            super.onClick(dialog, which);
        }

    }
}
