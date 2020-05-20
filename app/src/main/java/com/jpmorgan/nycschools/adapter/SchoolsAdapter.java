package com.jpmorgan.nycschools.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jpmorgan.nycschools.R;
import com.jpmorgan.nycschools.model.School;
import com.jpmorgan.nycschools.view.SchoolViewHolder;

import java.util.List;

public class SchoolsAdapter extends RecyclerView.Adapter<SchoolViewHolder> {

    public interface ItemClickListener {
        void itemClicked(String id, String name, String address);
    }

    private ItemClickListener itemClickListener;

    private List<School> schools;

    public SchoolsAdapter(@NonNull List<School> schools, @NonNull ItemClickListener itemClickListener) {
        this.schools = schools;
        this.itemClickListener = itemClickListener;
    }

    public void setSchools(List<School> schools) {
        this.schools = schools;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SchoolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_school, parent, false);
        return new SchoolViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolViewHolder holder, int position) {
        holder.bind(schools.get(position), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null && schools.get(position) != null && schools.get(position).getDbn() != null) {
                    itemClickListener.itemClicked(schools.get(position).getDbn(), schools.get(position).getSchoolName(), schools.get(position).getPrimaryAddressLine1());
                } else {
                    //TODO error
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return schools.size();
    }
}
