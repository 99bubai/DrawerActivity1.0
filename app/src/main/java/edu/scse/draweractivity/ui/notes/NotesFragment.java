package edu.scse.draweractivity.ui.notes;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.scse.draweractivity.Adapter.TitleAdapter;
import edu.scse.draweractivity.Activity.NoteAddActivity;
import edu.scse.draweractivity.R;
public class NotesFragment extends Fragment {
    private NotesViewModel notesViewModel;
    private RecyclerView   recyclerView_home;
    private TitleAdapter         titleAdapter;
    private FloatingActionButton fab;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);//将定义的布局加载出来
        //final TextView textView = root.findViewById(R.id.text_home);
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

        //recyclerView配置布局和adapter
        recyclerView_home = (RecyclerView) root.findViewById(R.id.recyclerView_home);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView_home.setLayoutManager(linearLayoutManager);
        titleAdapter = new TitleAdapter(getActivity());
        recyclerView_home.setAdapter(titleAdapter);
        return root;
    }
}