package edu.scse.draweractivity.Activity;
//1.30 recycler view能够显示，下一步进行ui美化，加入点击实现页面跳转
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import edu.scse.draweractivity.Adapter.TitleAdapter;
import edu.scse.draweractivity.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private AppBarConfiguration mAppBarConfiguration;
    //控件
    private  static  int login_type;
    private Toolbar toolbar;//顶部菜单栏
    //private FloatingActionButton fab;//app_bar_main圆形添加键
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ImageView header_imageView;
    private TitleAdapter titleAdapter;

    private void init(){
        toolbar = findViewById(R.id.toolbar);
        //fab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //动态引用头和菜单，静态引用的话无法find头部布局中的组件
        navigationView.inflateHeaderView(R.layout.nav_header_main);
        navigationView.inflateMenu(R.menu.activity_main_drawer);
        //获取头部布局
        View navHeaderView = navigationView.getHeaderView(0);
        header_imageView=navHeaderView.findViewById(R.id.imageView);

        //点击后fragment的变化要这里配置
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_notes, R.id.nav_cloud, R.id.nav_remind,R.id.nav_setting)
                .setDrawerLayout(drawer)
                .build();

        header_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //读登陆缓存文件
                SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
                String username=sharedPreferences.getString("username","");
                String password=sharedPreferences.getString("password","");
                //发起服务器请求
                RequestBody requestBody= new FormBody.Builder()
                        .add("name",username)//111or112
                        .add("password",password)//111or112
                        .build();
                Request request=new Request.Builder().url("http://10.0.2.2:8081/login.php").post(requestBody).build();
                OkHttpClient okHttpClient=new OkHttpClient.Builder()
                        .connectTimeout(3,TimeUnit.SECONDS)
                        .writeTimeout(3, TimeUnit.SECONDS)
                        .readTimeout(3, TimeUnit.SECONDS)
                        .build();
                okHttpClient.newCall(request).enqueue(new Callback(){
                    //接收数据成功
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        Log.d(TAG, response.protocol() + " " +response.code() + " " + response.message());
                        Headers headers = response.headers();
                        String string=response.body().string();//response.body只能打开一次
                        Log.d(TAG, "onResponse: " + string);
                        try {
                            JSONObject json=new JSONObject(string);
                            Log.d(TAG, "json:"+json.getString("id"));

                            Intent intent=new Intent(MainActivity.this,UserHomeActivity.class);
                            startActivity(intent);
                        }
                        catch (JSONException e) {
                            Log.d(TAG,"fail");

                            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                    //接收数据失败
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());

                        Looper.prepare();//子线程中toast必须要加
                        Toast toast = Toast.makeText(MainActivity.this, "服务器未开放", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.LEFT,0, 0);
                        toast.show();
                        Looper.loop();
                    }
                });
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setSupportActionBar(toolbar);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {

    }
}