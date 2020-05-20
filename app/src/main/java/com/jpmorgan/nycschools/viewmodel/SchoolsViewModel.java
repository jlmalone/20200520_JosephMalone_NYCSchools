package com.jpmorgan.nycschools.viewmodel;

import androidx.databinding.Observable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jpmorgan.nycschools.model.DataRepository;
import com.jpmorgan.nycschools.model.School;

import java.util.List;

public class SchoolsViewModel extends ViewModel implements Observable {

    public MutableLiveData<List<School>> schoolsMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> viewState = new MutableLiveData<>(0);

    public void fetch() {
        DataRepository.getInstance().fetchSchools( schoolsMutableLiveData, viewState);
    }

    public void retry()
    {
        fetch();
    }

    @Override
    public void addOnPropertyChangedCallback(Observable.OnPropertyChangedCallback callback) {

    }

    @Override
    public void removeOnPropertyChangedCallback(Observable.OnPropertyChangedCallback callback) {

    }
}