package edu.scse.draweractivity.ui.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import edu.scse.draweractivity.R;

public class SettingFragment extends Fragment {
    private SettingViewModel settingViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        settingViewModel=new ViewModelProvider(this).get(SettingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        final TextView textView = root.findViewById(R.id.text_setting);
        settingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
