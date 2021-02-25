package edu.scse.draweractivity.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.scse.draweractivity.R;

public class LoginActivity extends AppCompatActivity {
    private EditText editText_username,editText_password;
    private Button button_login;
    private TextView textView_find,textView_register;

    private void init(){
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
                Log.d("TAG", "login");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        init();
    }


}
