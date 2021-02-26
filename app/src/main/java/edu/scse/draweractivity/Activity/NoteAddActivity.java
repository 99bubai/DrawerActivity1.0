package edu.scse.draweractivity.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import edu.scse.draweractivity.Adapter.FlagAdapter;
import edu.scse.draweractivity.R;

public class NoteAddActivity extends AppCompatActivity {
    private String TAG="NoteAddActivity";
    private LinearLayoutManager linearLayoutManager;
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
        recyclerView_add=(RecyclerView)findViewById(R.id.recyclerView_add);
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
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_add_main);
        init();

        //GridLayoutManager girdLayoutManager=new GridLayoutManager(this,4);
        //recyclerView_add.setLayoutManager(girdLayoutManager);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        recyclerView_add.setLayoutManager(linearLayoutManager);
        flagAdapter = new FlagAdapter(this);
        recyclerView_add.setAdapter(flagAdapter);//item之间存在巨大空白
    }

}
