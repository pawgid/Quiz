package com.example.pawe.quiz.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.pawe.quiz.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.welcomeTextView)
    TextView welcomeTextView;
    @BindView(R.id.welcomeButton)
    Button welcomeButton;

    @OnClick(R.id.welcomeButton)
    public void welcomeButtonClick() {
        startActivity(new Intent(MainActivity.this, QuizListActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}

