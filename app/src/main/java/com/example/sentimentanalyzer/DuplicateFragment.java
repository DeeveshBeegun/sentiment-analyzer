package com.example.sentimentanalyzer;

import static com.example.sentimentanalyzer.url.UrlConstant.DUPLICATE_API;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class DuplicateFragment extends Fragment {
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = container.getContext();
        View view = inflater.inflate(R.layout.fragment_duplicate, container, false);
        Button duplicateButton = (Button) view.findViewById(R.id.duplicate);

        duplicateButton.setOnClickListener(view1 -> {
            duplicate(view);
        });

        return view;
    }

    public void duplicate(View view) {
        TextInputLayout textDuplicateInput = (TextInputLayout) view.findViewById(R.id.duplicate_input);
        EditText textDuplicateInputTimes = (EditText) view.findViewById(R.id.editText1);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DUPLICATE_API,
                response -> {
                    setDuplicates(view, response);
                },
                error -> Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show())  {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("text", textDuplicateInput.getEditText().getText().toString());
                params.put("times", textDuplicateInputTimes.getText().toString());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void setDuplicates(View view, String text) {
        TextView textViewDuplicate = view.findViewById(R.id.duplicate_results_view);
        textViewDuplicate.setText(text);
    }




}