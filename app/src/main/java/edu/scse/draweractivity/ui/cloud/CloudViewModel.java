package edu.scse.draweractivity.ui.cloud;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CloudViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CloudViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is cloud fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}