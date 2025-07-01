package com.example.loginapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.loginapp.Emergency_Activity;
import com.example.loginapp.R;
import com.example.loginapp.model.Topic;
import com.example.loginapp.model.TopicRepository;

import java.util.ArrayList;
import java.util.List;

public class PrimeirosSocorrosFragment extends Fragment {

    private ImageButton btnEmergency;
    private GridLayout gridCommon, gridAll;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_primeiros_socorros, container, false);

        btnEmergency = view.findViewById(R.id.btnEmergency);
        gridCommon = view.findViewById(R.id.gridCommon);
        gridAll = view.findViewById(R.id.gridAll);


        btnEmergency.setOnClickListener(v -> {
            if (getActivity() != null) {
                Intent intent = new Intent(getActivity(), Emergency_Activity.class);
                startActivity(intent);
            }
        });


        TopicRepository.init(getContext());
        List<Topic> allTopics = TopicRepository.getAllTopics();


        List<Topic> common = allTopics.subList(0, Math.min(6, allTopics.size()));
        List<Topic> others = allTopics.size() > 6 ? allTopics.subList(6, allTopics.size()) : new ArrayList<>();


        addButtonsToGrid(gridCommon, common);
        addButtonsToGrid(gridAll, others);

        return view;
    }

    private void addButtonsToGrid(GridLayout grid, List<Topic> topics) {
        grid.removeAllViews();
        for (Topic t : topics) {
            Button btn = new Button(getContext());
            btn.setText(t.title);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            btn.setLayoutParams(params);

            btn.setOnClickListener(v -> {
                Bundle args = new Bundle();
                args.putString("topicId", t.id);
                DetailFragment fragment = new DetailFragment();
                fragment.setArguments(args);
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment) // <- certifica-te que existe este ID na tua activity
                        .addToBackStack(null)
                        .commit();
            });

            grid.addView(btn);
        }
    }
}
