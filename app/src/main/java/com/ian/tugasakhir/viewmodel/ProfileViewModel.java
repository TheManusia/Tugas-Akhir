package com.ian.tugasakhir.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {

    public ProfileViewModel() {
        MutableLiveData<String> mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

// --Commented out by Inspection START (6/16/2020 2:00 PM):
//    public LiveData<String> getText() {
//        return mText;
//    }
// --Commented out by Inspection STOP (6/16/2020 2:00 PM)
}