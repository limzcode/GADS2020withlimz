package com.techwithlimz.gads2020leaderboard.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.techwithlimz.gads2020leaderboard.R;
import com.techwithlimz.gads2020leaderboard.adapters.SkillIQAdapter;
import com.techwithlimz.gads2020leaderboard.models.SkillIQModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class SkillIQLeadersFragment extends Fragment {
    List<SkillIQModel> skillIQModelList;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater
                .inflate(R.layout.fragment_skill_iq_leaders, container, false);

        recyclerView = rootView.findViewById(R.id.skill_iq_recycler);
        skillIQModelList = new ArrayList<>();

        loadSkillIQLeaders();

        return rootView;
    }

    private void loadSkillIQLeaders() {
        String skillIQLeadersUrl = "https://gadsapi.herokuapp.com/api/skilliq";
        JsonArrayRequest request = new JsonArrayRequest(skillIQLeadersUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for (int i = 0; i < response.length(); i++) {

                    try {
                        jsonObject = response.getJSONObject(i);
                        SkillIQModel model = new SkillIQModel();
                        model.setName(jsonObject.getString("name"));
                        model.setCountry(jsonObject.getString("country"));
                        model.setScore(jsonObject.getInt("score"));
                        model.setBadgeUrl(jsonObject.getString("badgeUrl"));
                        skillIQModelList.add(model);

                        setupRecyclerView(skillIQModelList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(Objects.requireNonNull(getView()), "An Error occurred \n" + error,
                        Snackbar.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        requestQueue.add(request);
    }

    private void setupRecyclerView(List<SkillIQModel> skillIQModelList) {
        Collections.sort(skillIQModelList, new Comparator<SkillIQModel>() {
            @Override
            public int compare(SkillIQModel lhs, SkillIQModel rhs) {
                return Integer.compare( rhs.getScore(),lhs.getScore());
            }
        });

        SkillIQAdapter skillIQAdapter = new
                SkillIQAdapter(getContext(), skillIQModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(skillIQAdapter);
    }

}