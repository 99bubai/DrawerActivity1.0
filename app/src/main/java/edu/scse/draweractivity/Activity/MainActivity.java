package edu.scse.draweractivity.Activity;
//1.30 recycler view能够显示，下一步进行ui美化，加入点击实现页面跳转
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import edu.scse.draweractivity.R;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private AppBarConfiguration mAppBarConfiguration;
    //控件
    private Toolbar toolbar;//顶部菜单栏
    //private FloatingActionButton fab;//app_bar_main圆形添加键
    private DrawerLayout drawer;
    private NavigationView navigationView;//侧边菜单
    private ImageView header_imageView;

    private void Findview(){
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
    }//控件统一实例化
    private void Click(){
        header_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进行判断，未登录跳转至login，登陆跳转至home，暂时先写login
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }//统一注册点击事件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Findview();
        Click();
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