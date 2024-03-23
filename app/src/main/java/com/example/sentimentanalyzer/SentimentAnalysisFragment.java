package com.example.sentimentanalyzer;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.sentimentanalyzer.url.UrlConstant.SENTI_API;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SentimentAnalysisFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sentiment_analysis, container, false);

        Button analyseButton = (Button) view.findViewById(R.id.analyse);

        analyseButton.setOnClickListener(view1 -> {
            try {
                analyzeText(view);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return view;
    }

    public void setProgressBarPercentage(int percentage, ProgressBar progressBar) {
        if(percentage < 0) {
            percentage = percentage * -1;
            progressBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            progressBar.setProgress(percentage);
        }
        else {
            progressBar.getProgressDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            progressBar.setProgress(percentage);
        }

    }

    public void analyzeText(View view) throws IOException {
        TextInputLayout textInputLayout = (TextInputLayout) view.findViewById(R.id.sentiment_analysis_input);
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SENTI_API, response -> {
            //Toast.makeText(view.getContext(), "Data added to api", Toast.LENGTH_SHORT).show();
            try {
                JSONObject respObj = new JSONObject(response);

                System.out.println(respObj);

                displayProgressBarDetails(view, (int)Double.parseDouble(respObj.getString("compound")),
                        (int)Double.parseDouble(respObj.getString("neg")),
                        (int)Double.parseDouble(respObj.getString("neu")),
                        (int)Double.parseDouble(respObj.getString("pos")));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(view.getContext(), "An error occurred", Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("text",  textInputLayout.getEditText().getText().toString());

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    @SuppressLint("SetTextI18n")
    public void displayProgressBarDetails(View view, int per_compound, int per_neg, int per_neu, int per_pos) {

        // compound
        ProgressBar progressBar_compound = (ProgressBar) view.findViewById(R.id.progressBar_compound);
        setProgressBarPercentage(per_compound, progressBar_compound);

        TextView textViewCompound = (TextView) view.findViewById(R.id.per_overall_text);
        textViewCompound.setText(per_compound + "%");

        // neutral
        ProgressBar progressBar_neu = (ProgressBar) view.findViewById(R.id.progressBar_neu);
        setProgressBarPercentage(per_neu, progressBar_neu);

        TextView textViewNeu = (TextView) view.findViewById(R.id.per_neu_text);
        textViewNeu.setText(per_neu + "%");

        // negative
        ProgressBar progressBar_neg = (ProgressBar) view.findViewById(R.id.progressBar_neg);
        setProgressBarPercentage(per_neg, progressBar_neg);

        TextView textViewNeg = (TextView) view.findViewById(R.id.per_neg_text);
        textViewNeg.setText(per_neg + "%");

        // positive
        ProgressBar progressBar_pos = (ProgressBar) view.findViewById(R.id.progressBar_pos);
        setProgressBarPercentage(per_pos, progressBar_pos);

        TextView textViewPos = (TextView) view.findViewById(R.id.per_pos_text);
        textViewPos.setText(per_pos + "%");
    }

}