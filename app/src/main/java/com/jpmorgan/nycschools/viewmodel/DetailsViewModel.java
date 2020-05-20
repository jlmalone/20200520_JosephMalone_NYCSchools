package com.jpmorgan.nycschools.viewmodel;

import androidx.databinding.Observable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jpmorgan.nycschools.model.DataRepository;
import com.jpmorgan.nycschools.model.SchoolStatistics;

public class DetailsViewModel  extends ViewModel implements Observable {

    public MutableLiveData<SchoolStatistics> schoolStatisticsMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> viewState = new MutableLiveData<>(0);

    private String mSelectedSchoolId;

    public void fetch(String schoolId)
    {
        mSelectedSchoolId = schoolId;
        DataRepository.getInstance().fetchSchoolScoreDetails(mSelectedSchoolId, schoolStatisticsMutableLiveData, viewState);
    }

    public void retry()
    {
        DataRepository.getInstance().fetchSchoolScoreDetails(mSelectedSchoolId, schoolStatisticsMutableLiveData, viewState);
    }

    public MutableLiveData<SchoolStatistics> getSchoolStatisticsMutableLiveData() {
        return schoolStatisticsMutableLiveData;
    }

    public void setSchoolStatisticsMutableLiveData(MutableLiveData<SchoolStatistics> schoolStatisticsMutableLiveData) {
        this.schoolStatisticsMutableLiveData = schoolStatisticsMutableLiveData;
    }

    public String getSelectedSchoolId() {
        return mSelectedSchoolId;
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

    }
}
