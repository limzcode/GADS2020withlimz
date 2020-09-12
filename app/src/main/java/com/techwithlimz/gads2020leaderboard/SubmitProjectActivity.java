package com.techwithlimz.gads2020leaderboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SubmitProjectActivity extends AppCompatActivity {

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String githubLink;

    private TextInputEditText inputFirstName;
    private TextInputEditText inputLastName;
    private TextInputEditText inputEmail;
    private TextInputEditText inputGithubLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_submission);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        inputFirstName = findViewById(R.id.input_first_name);
        inputLastName = findViewById(R.id.input_last_name);
        inputEmail = findViewById(R.id.input_email);
        inputGithubLink = findViewById(R.id.input_github_link);
        MaterialButton buttonSubmit = findViewById(R.id.button_submit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputValidations();
            }
        });
    }

    private void inputValidations() {
        String cannotBeBlank = "Input cannot be blank";
        String mustBeValidEmail = "Enter a valid email";
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";

        firstName = Objects.requireNonNull(inputFirstName.getText()).toString();
        lastName = Objects.requireNonNull(inputLastName.getText()).toString();
        emailAddress = Objects.requireNonNull(inputEmail.getText()).toString();
        githubLink = Objects.requireNonNull(inputGithubLink.getText()).toString();
        if (firstName.isEmpty()) {
            inputFirstName.setError(cannotBeBlank);
            return;
        }
        if (lastName.isEmpty()) {
            inputLastName.setError(cannotBeBlank);
        }
        if (emailAddress.isEmpty()) {
            inputEmail.setError(cannotBeBlank);
        }
        if (githubLink.isEmpty()) {
            inputGithubLink.setError(cannotBeBlank);
        }
        if (!(emailAddress.matches((emailPattern)) || emailAddress.matches(emailPattern2))) {
            inputEmail.setError(mustBeValidEmail);
        }

        //Alert Dialog
        Rect displayRectangle = new Rect();
        Window window = SubmitProjectActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                SubmitProjectActivity.this, R.style.CustomDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View view = LayoutInflater
                .from(SubmitProjectActivity.this)
                .inflate(R.layout.dialog_confirm_submission, viewGroup, false);

        view.setMinimumWidth((int) (displayRectangle.width() * 1f));
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        MaterialButton approveButton = view.findViewById(R.id.confirm_project_submission_button);
        ImageButton cancelDialog = view.findViewById(R.id.cancel_submission_button);

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postProjectDetails();
                alertDialog.dismiss();
            }
        });
        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }

    private void postProjectDetails() {
        RequestQueue requestQueue = Volley.newRequestQueue(SubmitProjectActivity.this);
        String postUrl = "https://docs.google.com/forms/d/e/1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl" +
                "35cwZr2xyjIhaMAz8WChQ/formResponse";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Rect displayRectangle = new Rect();
                        Window window = SubmitProjectActivity.this.getWindow();
                        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                        final AlertDialog.Builder builder = new AlertDialog.Builder(
                                SubmitProjectActivity.this, R.style.CustomDialog);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View view = LayoutInflater.from(SubmitProjectActivity.this)
                                .inflate(R.layout.dialog_successful_submission, viewGroup,
                                        false);
                        view.setMinimumWidth((int) (displayRectangle.width() * 1f));
                        builder.setView(view);

                        final AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "==" + error);

                Rect displayRectangle = new Rect();
                Window window = SubmitProjectActivity.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                final AlertDialog.Builder builder = new AlertDialog.Builder(
                        SubmitProjectActivity.this, R.style.CustomDialog);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View view = LayoutInflater
                        .from(SubmitProjectActivity.this)
                        .inflate(R.layout.dialog_unsuccessful_submission, viewGroup, false);
                view.setMinimumWidth((int) (displayRectangle.width() * 1f));
                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.d("Response Code", "status==" + mStatusCode);
                return super.parseNetworkResponse(response);
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("entry.1824927963", emailAddress);
                params.put("entry.1877115667", firstName);
                params.put("entry.2006916086", lastName);
                params.put("entry.284483984", githubLink);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/x-www-form-urlencoded");
                return header;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}