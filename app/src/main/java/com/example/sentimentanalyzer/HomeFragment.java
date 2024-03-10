package com.example.sentimentanalyzer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button sentimentAnalysisButton = (Button) view.findViewById(R.id.button1);
        Button summarizerButton = (Button) view.findViewById(R.id.button2);

        sentimentAnalysisButton.setOnClickListener(view1 -> {
            var fragment = new SentimentAnalysisFragment();
            replaceFragment(fragment);
        });

        summarizerButton.setOnClickListener(view2 -> {
            var fragment = new SummarizerFragment();
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