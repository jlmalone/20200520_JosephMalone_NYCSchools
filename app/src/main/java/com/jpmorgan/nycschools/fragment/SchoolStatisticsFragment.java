package com.jpmorgan.nycschools.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.jpmorgan.nycschools.R;
import com.jpmorgan.nycschools.model.State;
import com.jpmorgan.nycschools.viewmodel.DetailsViewModel;

public class SchoolStatisticsFragment extends Fragment {

    private DetailsViewModel mViewModel;

    private ProgressBar loaderPb;
    private Group errorViewsGroup, layoutViewsGroup;
    private TextView nameTv, addressTv, writingScoreTv, readingScoreTv, mathScoreTv;
    private MaterialButton tryAgainBtn;
    private String mSchoolId;

    private String mName;
    private String mAddress;
    private TextView errorMessageTv;


    public static SchoolStatisticsFragment newInstance() {
        return new SchoolStatisticsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_school_scores, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            mSchoolId = SchoolStatisticsFragmentArgs.fromBundle(getArguments()).getId();
            mAddress = SchoolStatisticsFragmentArgs.fromBundle(getArguments()).getAddress();
            mName = SchoolStatisticsFragmentArgs.fromBundle(getArguments()).getName();
        }

        loaderPb = view.findViewById(R.id.school_scores_pb_loader);
        errorViewsGroup = view.findViewById(R.id.school_scores_group_error_views);
        layoutViewsGroup = view.findViewById(R.id.school_scores_group_layout_views);
        nameTv = view.findViewById(R.id.school_scores_tv_name);
        addressTv = view.findViewById(R.id.school_scores_tv_address);
        writingScoreTv = view.findViewById(R.id.school_scores_tv_score_reading_body);
        readingScoreTv = view.findViewById(R.id.school_scores_tv_score_writing_body);
        mathScoreTv = view.findViewById(R.id.school_scores_tv_score_math_body);
        errorMessageTv = view.findViewById(R.id.school_scores_tv_error);
        tryAgainBtn = view.findViewById(R.id.school_scores_btn_try_again);

        tryAgainBtn.setOnClickListener(v -> mViewModel.retry());

        nameTv.setText(mName);
        addressTv.setText(mAddress);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        if (activity != null) {
            mViewModel = new
                    ViewModelProvider(getActivity()).get(DetailsViewModel.class);
        }

        mViewModel.viewState.observe(getViewLifecycleOwner(), integer -> {
            if (integer == State.ERROR) {
                setError();
            } else if (integer == State.FINISHED) {
                setDisplay();
            } else {
                setLoading();
            }
        });

        mViewModel.schoolStatisticsMutableLiveData.observe(getViewLifecycleOwner(), schoolStatistics -> setDisplayData());
        mViewModel.fetch(mSchoolId);
    }

    private void setDisplayData() {
        mathScoreTv.setText(mViewModel.schoolStatisticsMutableLiveData.getValue().getSatMathAvgScore());
        writingScoreTv.setText(mViewModel.schoolStatisticsMutableLiveData.getValue().getSatWritingAvgScore());
        readingScoreTv.setText(mViewModel.schoolStatisticsMutableLiveData.getValue().getSatCriticalReadingAvgScore());

    }

    private void setDisplay() {

        errorViewsGroup.setVisibility(View.INVISIBLE);
        layoutViewsGroup.setVisibility(View.VISIBLE);
        tryAgainBtn.setVisibility(View.GONE);
        loaderPb.setVisibility(View.GONE);
    }

    private void setLoading() {
        loaderPb.setVisibility(View.VISIBLE);
        errorViewsGroup.setVisibility(View.GONE);
        layoutViewsGroup.setVisibility(View.GONE);
        tryAgainBtn.setVisibility(View.GONE);

    }

    private void setError() {
        loaderPb.setVisibility(View.GONE);
        errorViewsGroup.setVisibility(View.VISIBLE);
        layoutViewsGroup.setVisibility(View.GONE);
        tryAgainBtn.setVisibility(View.VISIBLE);

    }

}
