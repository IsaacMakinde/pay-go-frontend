package com.example.storeb.ui.recent;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecentActivityViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private final MutableLiveData<String> mText;

    public RecentActivityViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Recent Activity");
    }

    public MutableLiveData<String> getText() {
        return mText;
    }
}