package com.example.loginapp.fragments;

import static android.view.View.VISIBLE;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.loginapp.R;
import com.example.loginapp.model.Topic;
import com.example.loginapp.model.TopicRepository;

import java.util.List;

public class DetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inf, ViewGroup c, Bundle savedInstanceState) {
        TopicRepository.init(getContext());
        View v = inf.inflate(R.layout.fragment_detail, c, false);
        LinearLayout ll = v.findViewById(R.id.llContent);
        String topicId = getArguments().getString("topicId");
        Topic t = TopicRepository.findById(topicId);

        if (t.subtopics != null && !t.subtopics.isEmpty()) {
            for (Topic.Subtopic s : t.subtopics) {
                Button btnSub = new Button(getContext());
                btnSub.setText(s.title);
                ll.addView(btnSub);
                btnSub.setOnClickListener(_v -> showSteps(ll, s.steps));
            }
        } else {
            showSteps(ll, t.steps);
        }
        return v;
    }

    private void showSteps(LinearLayout ll, List<Topic.Step> steps) {
        ll.removeAllViews();
        for (int i = 0; i < steps.size(); i++) {
            Topic.Step step = steps.get(i);

            // TextView do passo principal com ripple e seta
            TextView tv = new TextView(getContext());
            tv.setText((i + 1) + ".  " + step.instruction);
            tv.setTextSize(18);
            tv.setPadding(24, 24, 24, 24);
            tv.setTypeface(null, Typeface.BOLD);
            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand_more, 0); // Ícone seta (cria ou usa um drawable)
            tv.setCompoundDrawablePadding(16);
            tv.setClickable(true);
            tv.setFocusable(true);
            tv.setBackgroundResource(selectableItemBackground());
            tv.setTextColor(getResources().getColor(android.R.color.black));

            // Container para substeps, inicialmente escondido
            LinearLayout subContainer = new LinearLayout(getContext());
            subContainer.setOrientation(LinearLayout.VERTICAL);
            subContainer.setVisibility(View.GONE);
            subContainer.setPadding(32, 8, 8, 8);
            ll.addView(tv);
            ll.addView(subContainer);

            for (String sub : step.substeps) {
                TextView subTv = new TextView(getContext());
                subTv.setText("•  " + sub);
                subTv.setTextSize(16);
                subTv.setPadding(8, 4, 8, 4);
                subContainer.addView(subTv);
                subContainer.setVisibility(VISIBLE);
            }


        }
    }

    private void toggleViewVisibility(View v) {
        if (v.getVisibility() == VISIBLE) {
            v.animate().alpha(0.0f).setDuration(200).withEndAction(() -> v.setVisibility(View.GONE)).start();
        } else {
            v.setAlpha(0.0f);
            v.setVisibility(VISIBLE);
            v.animate().alpha(1.0f).setDuration(200).start();
        }
    }

    private int selectableItemBackground() {
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
        return typedValue.resourceId;
    }
}
