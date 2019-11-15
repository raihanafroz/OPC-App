package com.example.hp.pollice.ui.addStation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class AddStationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddStationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is addStation fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}