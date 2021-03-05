package edu.scse.draweractivity.ui.notes;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import edu.scse.draweractivity.Activity.LocalNotesActivity;
import edu.scse.draweractivity.Activity.MainActivity;
import edu.scse.draweractivity.Adapter.TitleAdapter;
import edu.scse.draweractivity.Activity.NoteAddActivity;
import edu.scse.draweractivity.R;
import edu.scse.draweractivity.entity.DataBaseHelper;
import edu.scse.draweractivity.entity.NoteTitleData;
import edu.scse.draweractivity.entity.TitleFactory;

public class NotesFragment extends Fragment {
    private NotesViewModel notesViewModel;
    private RecyclerView   recyclerView_home;
    private TitleAdapter         titleAdapter;
    private FloatingActionButton fab;
    private DataBaseHelper dataBaseHelper;
    private List<NoteTitleData> list = null;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //将定义的布局加载出来
        notesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        //点击fab跳转到NoteAddActivity
        fab=root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), NoteAddActivity.class);
                startActivity(intent);
            }
        });
        //得到一个可写的数据库
        dataBaseHelper=new DataBaseHelper(getActivity());
        SQLiteDatabase db =dataBaseHelper.getReadableDatabase();
        list= TitleFactory.createItem(db);//db在titlefactory的子方法中被关闭
        //recyclerView配置布局和adapter
        recyclerView_home = (RecyclerView) root.findViewById(R.id.recyclerView_home);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView_home.setLayoutManager(linearLayoutManager);
        titleAdapter = new TitleAdapter(getActivity(),list);
        recyclerView_home.setAdapter(titleAdapter);
        //item点击事件，实现adapter提供的接口
        titleAdapter.setOnItemClickListener(new TitleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(getContext(), LocalNotesActivity.class);
                intent.putExtra("id",list.get(position).getId());
                intent.putExtra("title",list.get(position).getTitle());
                startActivity(intent);
                //Toast.makeText(getActivity(),
                //        "click " + position+",id "+list.get(position).getId(), Toast.LENGTH_SHORT).show();
            }
        });
        //item长按事件
        titleAdapter.setOnItemLongClickListener(new TitleAdapter.OnItemLongClickListener() {
            @Override
            public void onClick(int position,View view) {
                PopupMenu popupMenu = new PopupMenu(getActivity(),view);
                popupMenu.getMenuInflater().inflate(R.menu.menu,popupMenu.getMenu());
                //点击事件
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        dataBaseHelper=new DataBaseHelper(getActivity());
                        SQLiteDatabase db =dataBaseHelper.getWritableDatabase();
                        db.delete("Notes","id=?",new String[]{list.get(position).getId()});
                        titleAdapter.removeNotes(position);//在adapter的remove方法中进行数据刷新
                        return true;
                    }
                });
                popupMenu.show();
                Toast.makeText(getActivity(), "long click " + position, Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }


    private void setListener(TitleAdapter titleAdapter){

    }


}