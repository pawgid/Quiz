package com.example.pawe.quiz.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pawe.quiz.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Paweł on 2018-02-17.
 */

public class QuizListActivity extends FragmentActivity {

    @BindView(R.id.quizSelectTextView)
    TextView quizSelectTextView;

    @BindView(R.id.quizSelectListView)
    ListView quizSelectListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_list);
        ButterKnife.bind(this);
    }
}
