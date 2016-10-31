package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import model.MyDiary;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Woof on 10/29/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private final ArrayList<MyDiary> diaryList = new ArrayList<>();

    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //建表
        String CREATE_DIARY_TABLE = "CREATE TABLE "
                + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY, "
                + Constants.TITLE_NAME + " TEXT, "
                + Constants.CONTENT_NAME + " TEXT, "
                + Constants.DATE_NAME + " LONG);";
        db.execSQL(CREATE_DIARY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //表更新可以考虑用version进行控制
        //Drop表示重新建表
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }

    //向表中添加内容
    public void addDiary(MyDiary diary){
        //此处未添加this
        SQLiteDatabase db = this.getWritableDatabase();

        //类似于hashmap, 存入一组key 和 value
        ContentValues values = new ContentValues();
        values.put(Constants.TITLE_NAME, diary.getTitle());
        values.put(Constants.CONTENT_NAME, diary.getContent());
        values.put(Constants.DATE_NAME, System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME, null, values);
        Log.v("diary success", "yeah");
        db.close();
    }
    //获取所有日记
    public ArrayList<MyDiary> getDiary(){
        diaryList.clear();

        SQLiteDatabase db = this.getReadableDatabase();//此处this的意思

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{Constants.KEY_ID, Constants.TITLE_NAME, Constants.CONTENT_NAME, Constants.DATE_NAME}, null, null, null, null, Constants.DATE_NAME+ " DESC");//按照时间顺序返回constants.DATA_NAME 降序
        //循环游标
        if (cursor.moveToFirst()){
            do {

                MyDiary diary = new MyDiary();
                diary.setTitle(cursor.getString(cursor.getColumnIndex(Constants.TITLE_NAME)));
                diary.setContent(cursor.getString(cursor.getColumnIndex(Constants.CONTENT_NAME)));

                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String dateData = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.DATE_NAME))).getTime());

                diary.setRecordDate(dateData);

                diaryList.add(diary);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return diaryList;
    }
}
