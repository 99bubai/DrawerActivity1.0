package edu.scse.draweractivity.ui.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.scse.draweractivity.Adapter.SettingAdapter;
import edu.scse.draweractivity.Adapter.TitleAdapter;
import edu.scse.draweractivity.R;

public class SettingFragment extends Fragment {
    private SettingViewModel settingViewModel;
    private List<String> list=new ArrayList<String>(){
        {
            this.add("用户中心");
            this.add("权限管理"); }
        };
    private RecyclerView recyclerView;
    private EditText editText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_setting, container, false);

        //editText=root.findViewById(R.id.editText_search);

        recyclerView=(RecyclerView) root.findViewById(R.id.recyclerView_setting);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        SettingAdapter settingAdapter = new SettingAdapter(getActivity(),list,R.layout.item_setting_list,R.id.item_list_setting);
        recyclerView.setAdapter(settingAdapter);


        return root;
    }


}
