package com.techwithlimz.gads2020leaderboard.ui.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.techwithlimz.gads2020leaderboard.R;
import com.techwithlimz.gads2020leaderboard.adapters.LearningLeaderAdapter;
import com.techwithlimz.gads2020leaderboard.models.LearningLeadersModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class LearningLeadersFragment extends Fragment {
    private static final String learningLeadersUrl = "https://gadsapi.herokuapp.com/api/hours";
    private List<LearningLeadersModel> learningLeadersModelList;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater
                .inflate(R.layout.fragment_learning_leader, container, false);

        recyclerView = rootView.findViewById(R.id.learning_leader_recycler);
        learningLeadersModelList = new ArrayList<>();

        loadLearningLeaders();

        return rootView;
    }

    private void loadLearningLeaders() {

        JsonArrayRequest request = new JsonArrayRequest(learningLeadersUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        LearningLeadersModel model = new LearningLeadersModel();
                        model.setName(jsonObject.getString("name"));
                        model.setCountry(jsonObject.getString("country"));
                        model.setHours(jsonObject.getInt("hours"));
                        model.setBadgeUrl(jsonObject.getString("badgeUrl"));
                        learningLeadersModelList.add(model);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    setupRecyclerView(learningLeadersModelList);
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

    private void setupRecyclerView(List<LearningLeadersModel> learningLeadersModelList) {
        Collections.sort(learningLeadersModelList, new Comparator<LearningLeadersModel>() {
            @Override
            public int compare(LearningLeadersModel model, LearningLeadersModel t1) {
                return Integer.compare(t1.getHours(), model.getHours());
            }
        });

        LearningLeaderAdapter learningLeaderAdapter = new
                LearningLeaderAdapter(getContext(), learningLeadersModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(learningLeaderAdapter);

    }
}