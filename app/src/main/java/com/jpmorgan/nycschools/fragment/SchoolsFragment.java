package com.jpmorgan.nycschools.fragment;


import android.content.Context;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jpmorgan.nycschools.MainActivity;
import com.jpmorgan.nycschools.R;
import com.jpmorgan.nycschools.adapter.SchoolsAdapter;
import com.jpmorgan.nycschools.model.School;
import com.jpmorgan.nycschools.model.State;
import com.jpmorgan.nycschools.viewmodel.SchoolsViewModel;

import java.util.ArrayList;
import java.util.List;

public class SchoolsFragment extends Fragment {

    private SchoolsViewModel mSchoolsViewModel;

    private NavController navController;
    private RecyclerView recycler;
    private ProgressBar loader;
    private Group errorViewGroup;
    private TextView errorTv;
    private SchoolsAdapter schoolsAdapter;

    private SchoolsAdapter.ItemClickListener itemClickListener = new SchoolsAdapter.ItemClickListener() {
        @Override
        public void itemClicked(String id, String name, String address) {
            if (navController != null) {
                navController.navigate(SchoolsFragmentDirections.actionSchoolsToScores(id, name, address));
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schools_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loader = view.findViewById(R.id.progress_loader);
        errorViewGroup = view.findViewById(R.id.group_error_views);
        errorTv = view.findViewById(R.id.text_error);
        recycler = view.findViewById(R.id.recycler);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        view.findViewById(R.id.btn_try_again).setOnClickListener(v -> mSchoolsViewModel.retry());
        schoolsAdapter = new SchoolsAdapter(new ArrayList<>(), itemClickListener);
        recycler.setAdapter(schoolsAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSchoolsViewModel = new
                ViewModelProvider(getActivity()).get(SchoolsViewModel.class);
        mSchoolsViewModel.fetch();

        mSchoolsViewModel.schoolsMutableLiveData.observe(getViewLifecycleOwner(), new Observer<List<School>>() {
            @Override
            public void onChanged(List<School> schools) {
                if (schools != null && schools.size() > 0) {
                    schoolsAdapter.setSchools(schools);
                }
            }
        });

        mSchoolsViewModel.viewState.observe(getViewLifecycleOwner(), integer -> {
            if (integer == State.ERROR) {
                setError();
            } else if (integer == State.FINISHED) {
                setDisplay();
            } else {
                setLoading();
            }
        });
    }

    private void setError() {
        loader.setVisibility(View.GONE);
        recycler.setVisibility(View.INVISIBLE);
        errorViewGroup.setVisibility(View.VISIBLE);
    }

    private void setDisplay() {
        loader.setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);
        errorViewGroup.setVisibility(View.GONE);
    }

    private void setLoading() {
        loader.setVisibility(View.VISIBLE);
        recycler.setVisibility(View.INVISIBLE);
        errorViewGroup.setVisibility(View.GONE);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            navController = ((MainActivity) getActivity()).navController;
        }
    }
}