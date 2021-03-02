package edu.scse.draweractivity.Activity;

import android.annotation.SuppressLint;
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
import edu.scse.draweractivity.ui.FlagLayout;
import android.view.ViewGroup.LayoutParams;


public class NoteAddActivity extends AppCompatActivity {
    private String TAG="NoteAddActivity";
    //private LinearLayoutManager linearLayoutManager;
    private FlagLayout flagLayout;
    private String debugFlag[]={"安卓","苹果","谷歌","三星","华为","vivo","魅族","微软","联想","小米","诺基亚"};
    private ImageButton         button_select,button_favorite,button_explain,
            button_picture,button_attachment,button_notification,button_back;
    private EditText editText_title,editText_body;
    private Spinner spinner_type;
    private String title,body;
    private String[] noteType;
    private static int position=0;

    private RecyclerView recyclerView_add;
    private FlagAdapter flagAdapter;
    private void init(){
        //recyclerView_add=(RecyclerView)findViewById(R.id.recyclerView_add);
        flagLayout=(FlagLayout)findViewById(R.id.flagLayout);
        button_select=findViewById(R.id.button_add_select);
        button_favorite=findViewById(R.id.button_add_favorite);
        button_explain=findViewById(R.id.button_add_explain);
        button_picture=findViewById(R.id.button_add_picture);
        button_attachment=findViewById(R.id.button_add_attachment);
        button_notification=findViewById(R.id.button_add_notification);
        button_back=findViewById(R.id.button_add_back);
        editText_body=findViewById(R.id.editText_add_body);
        editText_title=findViewById(R.id.editText_add_title);
        spinner_type=findViewById(R.id.spinner_add_type);
        //点击事件
        button_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=editText_title.getText().toString();
                body=editText_body.getText().toString();
                noteType=getResources().getStringArray(R.array.type);
                Log.d(TAG, "title:"+title+",body:"+body+",type:"+noteType[position]);
            }
        });
        button_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "favorite");
            }
        });
        button_explain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"explain");
            }
        });
        button_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"picture");
            }
        });
        button_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"attachment");
            }
        });
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
            }
        });
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
        flagLayout = (FlagLayout) findViewById(R.id.flagLayout);
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
    }
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


}
