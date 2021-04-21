package edu.scse.draweractivity.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.scse.draweractivity.Adapter.SettingAdapter;
import edu.scse.draweractivity.R;

public class UserHomeActivity extends AppCompatActivity {
    private Button button_back;
    private TextView textView_username,textView_useremail;
    private ImageView imageView_user;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SettingAdapter settingAdapter;
    private Intent intent;
    private List<String> list=new ArrayList<String>() {
        {
            this.add("修改密码");
        }
    };
    private String username;

    private void init(){

        button_back=findViewById(R.id.sharedPreferences_button);
        textView_useremail=findViewById(R.id.textView_user_email);
        textView_username=findViewById(R.id.textView_user_name);
        recyclerView=findViewById(R.id.recyclerView_user_setting);
        textView_username.setText(username);

        //退出登陆状态，删除sharedpreferences登陆信息
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("login", Context.MODE_PRIVATE);
                if (pref != null) {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.clear();
                    editor.apply();
                    Intent intent=new Intent(UserHomeActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);
        intent=getIntent();
        username=intent.getStringExtra("username");

        init();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        SettingAdapter settingAdapter = new SettingAdapter(this,list,R.layout.item_setting_list,R.id.item_list_setting);
        recyclerView.setAdapter(settingAdapter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }

}