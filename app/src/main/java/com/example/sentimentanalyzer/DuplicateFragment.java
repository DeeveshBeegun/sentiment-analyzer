package com.example.sentimentanalyzer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DuplicateFragment extends Fragment {

    TextInputLayout textInputLayout;
    Context context;


    String mlpApi = "https://machine-learning-playground-api.onrender.com/duplicate";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = container.getContext();

        View view = inflater.inflate(R.layout.fragment_duplicate, container, false);

        Button duplicateButton = (Button) view.findViewById(R.id.duplicate);

        duplicateButton.setOnClickListener(view1 -> {
            duplicate();
        });

        return view;


    }


    public void duplicate() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, mlpApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error getting response");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("text", "I am so happy");
                params.put("times", "3");

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }




}