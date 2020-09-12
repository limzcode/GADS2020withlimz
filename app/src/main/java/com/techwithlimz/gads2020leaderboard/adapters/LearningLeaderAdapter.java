package com.techwithlimz.gads2020leaderboard.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textview.MaterialTextView;
import com.techwithlimz.gads2020leaderboard.R;
import com.techwithlimz.gads2020leaderboard.models.LearningLeadersModel;

import java.util.List;

public class LearningLeaderAdapter extends
        RecyclerView.Adapter<LearningLeaderAdapter.LearningLeadersViewHolder> {

    private Context context;
    List<LearningLeadersModel> learningLeadersList;
    RequestOptions requestOptions;

    public LearningLeaderAdapter(Context context, List<LearningLeadersModel> learningLeadersModel) {
        this.context = context;
        this.learningLeadersList = learningLeadersModel;

        requestOptions = new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.top_learner_badge)
                .error(R.drawable.top_learner_badge);
    }

    @NonNull
    @Override
    public LearningLeadersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.all_learning_leaders, parent, false);
        return new LearningLeadersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LearningLeadersViewHolder holder, int position) {

        String name = learningLeadersList.get(position).getName();
        String badge = learningLeadersList.get(position).getBadgeUrl();
        String country = learningLeadersList.get(position).getCountry();
        int hours = learningLeadersList.get(position).getHours();
        String description = hours + " learning hours, " + country;

        Glide.with(context)
                .load(badge)
                .apply(requestOptions)
                .into(holder.learningLeadersBadge);
        holder.learningLeadersName.setText(name);
        holder.learningLeadersDescription.setText(description);

    }

    @Override
    public int getItemCount() {
        return learningLeadersList.size();
    }


    public static class LearningLeadersViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView learningLeadersBadge;
        MaterialTextView learningLeadersName;
        MaterialTextView learningLeadersDescription;

        public LearningLeadersViewHolder(@NonNull View itemView) {
            super(itemView);

            learningLeadersBadge = itemView.findViewById(R.id.top_learner_badge);
            learningLeadersName = itemView.findViewById(R.id.top_learner_name);
            learningLeadersDescription = itemView.findViewById(R.id.top_learner_description);
        }
    }
}
