package edu.scse.draweractivity.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.scse.draweractivity.R;

public class UserHomeActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);
        button=findViewById(R.id.sharedPreferences_button);

        button.setOnClickListener(new View.OnClickListener() {
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
    protected void onPause() {
        super.onPause();
        this.finish();
    }

}