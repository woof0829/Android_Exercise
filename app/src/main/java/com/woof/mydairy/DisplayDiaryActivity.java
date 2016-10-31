package com.woof.mydairy;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import data.DatabaseHandler;
import model.MyDiary;

import java.util.ArrayList;
import java.util.List;

public class DisplayDiaryActivity extends AppCompatActivity {
    private ArrayList<MyDiary> dbDiary = new ArrayList<>();
    private ListView listView;
    private DiaryAdapter diaryAdapter;
    private DatabaseHandler dbh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_diary);

        listView = (ListView) findViewById(R.id.diaryList);
        refreshData();
    }

    private void refreshData() {
        dbDiary.clear();
        dbh = new DatabaseHandler(getApplicationContext());
        ArrayList<MyDiary> diaryFromDB = dbh.getDiary();
        for (MyDiary aDiaryFromDB : diaryFromDB) {

            String title = aDiaryFromDB.getTitle();
            String content = aDiaryFromDB.getContent();
            String date = aDiaryFromDB.getRecordDate();

            MyDiary myDiary = new MyDiary();
            myDiary.setTitle(title);
            myDiary.setContent(content);
            myDiary.setRecordDate(date);

            dbDiary.add(myDiary);

        }
        dbh.close();

        diaryAdapter = new DiaryAdapter(DisplayDiaryActivity.this, R.layout.list_row, dbDiary);
        listView.setAdapter(diaryAdapter);
        diaryAdapter.notifyDataSetChanged();

    }


    public class DiaryAdapter extends ArrayAdapter<MyDiary>{
        Activity activity;
        int layoutResource;
        ArrayList<MyDiary> mData = new ArrayList<>();

        public DiaryAdapter(Activity act, int resource, ArrayList<MyDiary> data) {
            super(act, resource, data);
            activity = act;
            layoutResource = resource;
            mData = data;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public MyDiary getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public int getPosition(MyDiary item) {return super.getPosition(item);}

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //ViewHolder
            View row = convertView;
            ViewHolder holder = null;
            if (row == null || row.getTag() == null || holder == null){
                LayoutInflater inflater = LayoutInflater.from(activity);

                row = inflater.inflate(layoutResource, null);
                holder = new ViewHolder();

                holder.mTitle = (TextView) findViewById(R.id.title);
                holder.mDate = (TextView) findViewById(R.id.date);
                row.setTag(holder);
            }else {
                holder = (ViewHolder) row.getTag();
            }
            holder.myDiary = getItem(position);
            holder.mTitle.setText(holder.myDiary.getTitle());
            holder.mDate.setText(holder.myDiary.getRecordDate());
            return row;
        }

        /**
         * 创建ViewHolder内部类
         */
        class ViewHolder{
            TextView mContent;
            TextView mTitle;
            TextView mDate;
            MyDiary myDiary;
            int mId;
        }
    }
}

