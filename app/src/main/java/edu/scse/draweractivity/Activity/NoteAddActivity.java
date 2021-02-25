package edu.scse.draweractivity.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import edu.scse.draweractivity.Adapter.FlagAdapter;
import edu.scse.draweractivity.R;

public class NoteAddActivity extends AppCompatActivity {
    private LinearLayoutManager linearLayoutManager;

    private RecyclerView recyclerView_add;
    private FlagAdapter flagAdapter;
    private void Findview(){
        recyclerView_add=(RecyclerView)findViewById(R.id.recyclerView_add);
    }
    private void Click(){}

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_add_main);
        Findview();

        //GridLayoutManager girdLayoutManager=new GridLayoutManager(this,4);
        //recyclerView_add.setLayoutManager(girdLayoutManager);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        recyclerView_add.setLayoutManager(linearLayoutManager);
        flagAdapter = new FlagAdapter(this);
        recyclerView_add.setAdapter(flagAdapter);//item之间存在巨大空白
    }

}
