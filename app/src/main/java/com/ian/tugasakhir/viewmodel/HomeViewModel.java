package com.ian.tugasakhir.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

// --Commented out by Inspection START (6/16/2020 2:00 PM):
//    public LiveData<String> getText() {
//        return mText;
//    }
// --Commented out by Inspection STOP (6/16/2020 2:00 PM)
}