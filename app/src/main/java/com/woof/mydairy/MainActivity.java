package com.woof.mydairy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import data.DatabaseHandler;
import model.MyDiary;

public class MainActivity extends AppCompatActivity {

    private EditText title;
    private EditText content;
    private DatabaseHandler dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbh = new DatabaseHandler(this);
        title = (EditText) findViewById(R.id.editTitle);
        content = (EditText) findViewById(R.id.daiaryEdit);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveToDB();
            }
        });


    }

    private void saveToDB() {
        MyDiary diary = new MyDiary();
        diary.setTitle(title.getText().toString().trim());//数据通过edittext传入，查询trim的含义，此部分获取数据，同时转化为字符串
        diary.setContent(content.getText().toString().trim());

        dbh.addDiary(diary);
        dbh.close();

        title.setText("");
        content.setText("");

        Intent i = new Intent(MainActivity.this, DisplayDiaryActivity.class);
        startActivity(i);
    }
}
