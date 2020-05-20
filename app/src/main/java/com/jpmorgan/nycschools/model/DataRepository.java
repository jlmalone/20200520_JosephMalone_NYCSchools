package com.jpmorgan.nycschools.model;

import androidx.lifecycle.MutableLiveData;

import com.jpmorgan.nycschools.api.ApiManager;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DataRepository {

    private static DataRepository instance;

    private DataRepository() {
    }

    public static DataRepository getInstance() {
        if (instance == null) {
            instance = new DataRepository();
        }
        return instance;
    }

    public void fetchSchoolScoreDetails(String id, final MutableLiveData<SchoolStatistics> schoolStatistics, final MutableLiveData<Integer> state) {
        //TODO - would fetch cached
        state.setValue(State.LOADING);

        ApiManager.getInstance()
                .getService()
                .getSchoolDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        state.setValue(State.ERROR);
                    }
                })

                .doOnSuccess(new Consumer<List<SchoolStatistics>>() {
                    @Override
                    public void accept(List<SchoolStatistics> stats) throws Exception {
                        if(stats!=null && stats.size()>0) {
                            schoolStatistics.setValue(stats.get(0));
                            state.setValue(State.FINISHED);
                        }
                    }
                })
                .subscribe();
    }

    public void fetchSchools(final MutableLiveData<List<School>> schoolsLiveData, final MutableLiveData<Integer> state) {
        //TODO check cached result
        state.setValue(State.LOADING);

        ApiManager.getInstance()
                .getService()
                .getSchoolList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        state.setValue(State.ERROR);
                    }
                })
                .doOnSuccess(new Consumer<List<School>>() {
                    @Override
                    public void accept(List<School> schools) throws Exception {
                        state.setValue(State.FINISHED);
                        schoolsLiveData.setValue(schools);
                    }
                })
                .subscribe();
    }
}
