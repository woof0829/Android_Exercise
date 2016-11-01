package com.woof.mydairy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.widget.Button;
import android.widget.TextView;

public class DiaryDetailActivity extends AppCompatActivity {

    private TextView title, content, date;
    private Button deleteButton;

    public DiaryDetailActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_detail);
        title = (TextView) findViewById(R.id.detailsTitle);
        content = (TextView) findViewById(R.id.detailsContent);
        date = (TextView) findViewById(R.id.detailsDate);
        //deleteButton = (Button) findViewById(R.id.deleteButton);

    }
}
