package com.example.sentimentanalyzer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sentimentanalyzer.resultFragment.SummarizerFragmentResult;

public class SummarizerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summarizer, container, false);

        Button summarizeButton = (Button) view.findViewById(R.id.summarize);

        summarizeButton.setOnClickListener(view1 -> {
            var fragment = new SummarizerFragmentResult();
            replaceFragment(fragment);
        });

        return view;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}