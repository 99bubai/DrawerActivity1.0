package edu.scse.draweractivity.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import edu.scse.draweractivity.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;

public class LoginActivity extends AppCompatActivity {
    private String TAG="LoginActivity";
    private EditText editText_username,editText_password;
    private Button button_login;
    private TextView textView_find,textView_register;
    private void init(Context context){
        editText_username=findViewById(R.id.editText_login_username);
        editText_password=findViewById(R.id.editText_login_password);
        button_login=findViewById(R.id.button_login_login);
        textView_find=findViewById(R.id.textView_login_find);
        textView_register=findViewById(R.id.textView_login_register);
        textView_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("TAG", "register");
            }
        });
        textView_find.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("TAG", "find");
            }
        });
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=editText_username.getText().toString();
                String password=editText_password.getText().toString();
                gotoLogin(username,password,context);
                //Log.d("TAG", "username:"+username+",pas:"+password);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        init(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }


    private void gotoLogin(String username, String password, Context context) {
       /*RequestBody requestBody=new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return MediaType.parse("text/x-markdown; charset=utf-8");
            }
            @Override
            public void writeTo(@NotNull BufferedSink bufferedSink) throws IOException {
                bufferedSink.writeUtf8("OKhttp");
            }
        };*/
        RequestBody requestBody= new FormBody.Builder()
                .add("name",username)//111or112
                .add("password",password)//111or112
                .build();
        Request request=new Request.Builder().url("http://10.0.2.2:8081/login.php").post(requestBody).build();
        OkHttpClient okHttpClient=new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                //for (int i = 0; i < headers.size(); i++) {
                //  Log.d(TAG, headers.name(i) + ":" + headers.value(i)); }
                String string=response.body().string();//response.body只能打开一次
                Log.d(TAG, "onResponse: " + string);
                try {
                    JSONObject json=new JSONObject(string);
                    Log.d(TAG, "json:"+json.getString("id"));
                    //将登陆的信息存入xml文件
                    SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("username",json.getString("userName"));
                    editor.putString("password",json.getString("userPassword"));
                    editor.apply();

                    Intent intent=new Intent(LoginActivity.this,UserHomeActivity.class);
                    startActivity(intent);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Looper.prepare();//这是干嘛的不知道
                    Toast toast=Toast.makeText(context,"密码或账户错误",Toast.LENGTH_SHORT);//不能子线程直接toast
                    toast.show();
                    Looper.loop();
                }
            }
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }
        });

        /*
        String url = "http://10.0.2.2:8081";//模拟器把localhost试别为本身
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });*/

    }

}
