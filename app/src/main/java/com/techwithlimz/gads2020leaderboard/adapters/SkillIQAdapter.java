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
import com.techwithlimz.gads2020leaderboard.models.SkillIQModel;

import java.util.List;

public class SkillIQAdapter extends RecyclerView.Adapter<SkillIQAdapter.SkillIQViewHolder> {

    private Context context;
    List<SkillIQModel> skillIQModel;
    RequestOptions requestOptions;


    public SkillIQAdapter(Context context, List<SkillIQModel> skillIQModel) {
        this.context =context;
        this.skillIQModel = skillIQModel;

        requestOptions = new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.skill_iq_badge)
                .error(R.drawable.skill_iq_badge);
    }

    @NonNull
    @Override
    public SkillIQViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.all_skill_iq_leaders, parent, false);
        return new SkillIQViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillIQViewHolder holder, int position) {
        String name = skillIQModel.get(position).getName();
        String badge = skillIQModel.get(position).getBadgeUrl();
        String country = skillIQModel.get(position).getCountry();
        int score = skillIQModel.get(position).getScore();
        String description = score + " skill IQ Score, " + country+".";

        Glide.with(context)
                .load(badge)
                .apply(requestOptions)
                .into(holder.skillIQLeadersBadge);
        holder.skillIQLeadersName.setText(name);
        holder.skillIQLeadersDescription.setText(description);
    }

    @Override
    public int getItemCount() {
        return skillIQModel.size();
    }

    public static class SkillIQViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView skillIQLeadersBadge;
        MaterialTextView skillIQLeadersName;
        MaterialTextView skillIQLeadersDescription;

        public SkillIQViewHolder(@NonNull View itemView) {
            super(itemView);
            skillIQLeadersBadge = itemView.findViewById(R.id.skill_iq_badge);
            skillIQLeadersName = itemView.findViewById(R.id.skill_iq_name);
            skillIQLeadersDescription = itemView.findViewById(R.id.skill_iq_description);
        }
    }
}
