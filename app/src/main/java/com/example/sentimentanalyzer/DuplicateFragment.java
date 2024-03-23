package com.example.sentimentanalyzer;

import static com.example.sentimentanalyzer.url.UrlConstant.DUPLICATE_API;

import android.content.Context;
import android.content.Intent;
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

        Button copyButton = (Button) view.findViewById(R.id.copy_button);

        copyButton.setOnClickListener(view1 -> {
            copyContent(context);
        });

        Button shareButton = (Button) view.findViewById(R.id.share_button);

        shareButton.setOnClickListener(view1 -> {
            shareContent();
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

    public void copyContent(Context context) {
        TextView textView = (TextView) getView().findViewById(R.id.duplicate_results_view);

        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", textView.getText().toString());
        clipboard.setPrimaryClip(clip);
    }

    public void shareContent() {

        TextView textView = (TextView) getView().findViewById(R.id.duplicate_results_view);

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, textView.getText().toString());
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }




}