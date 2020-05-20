package com.jpmorgan.nycschools.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.jpmorgan.nycschools.R;
import com.jpmorgan.nycschools.model.School;

public class SchoolViewHolder extends RecyclerView.ViewHolder {

    private ConstraintLayout rootCl;
    private TextView nameTv, addressTv, overviewTv;
    private School school;

    public SchoolViewHolder(@NonNull View itemView) {
        super(itemView);

        rootCl = itemView.findViewById(R.id.item_root_view);
        nameTv = itemView.findViewById(R.id.item_school_name_textview);
        addressTv = itemView.findViewById(R.id.item_address_textview);
        overviewTv = itemView.findViewById(R.id.item_overview_textview);
    }

    public void bind(@NonNull School school, View.OnClickListener clickListener) {
        nameTv.setText(school.getSchoolName());
        addressTv.setText(school.getPrimaryAddressLine1());
        overviewTv.setText(school.getOverviewParagraph());
        rootCl.setOnClickListener(clickListener);
    }
}
