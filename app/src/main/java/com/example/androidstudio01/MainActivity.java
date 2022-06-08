package com.example.androidstudio01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.androidstudio01.adpter.MyAdapter;
import com.example.androidstudio01.bean.Note;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private RecyclerView mRecyclerView;
    private List<Note> mNotes;
    private MyAdapter mMyAdapter;
    private NoteDbOpenHelper mNoteNoteDbOpenHelper;
    private Note note;
    private String date;
    private String date_1;
    public static int flag = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initEvent();

        calendarView = (CalendarView) findViewById(R.id.calenderView);
        //calendarView 监听事件
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange( CalendarView view, int year, int month, int dayOfMonth) {
                //显示用户选择的日期
                month = month+1;
                Toast.makeText(MainActivity.this,year + "年" + month + "月" + dayOfMonth + "日",Toast.LENGTH_SHORT).show();
                date = year + "." + month + "." + dayOfMonth;
                date_1 = date.toString();
                Log.d("78910", date_1+"");
                if(flag == 0){
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mNotes = mNoteNoteDbOpenHelper.queryFromDbByCreate_Date(date_1);
                        mMyAdapter.refreshData(mNotes);
                        flag = 1;
                }else{
                    mRecyclerView.setVisibility(View.GONE);
                    flag = 0;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshDataFromDb();
    }

    private void refreshDataFromDb() {
        mNotes = mNoteNoteDbOpenHelper.queryFromDbByCreate_Date(date_1);
        mMyAdapter.refreshData(mNotes);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.STQH:
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
                break;
            case R.id.TJ:
                Toast.makeText(this,"已进行统计",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
    public void add(View view){
        Intent intent = new Intent(this,AddActivity.class);
        startActivity(intent);
    }
    private void initView() {
        mRecyclerView = findViewById(R.id.rlv_1);
    }

    private void initData() {
        mNotes = new ArrayList<>();
        mNoteNoteDbOpenHelper = new NoteDbOpenHelper(this);
        mNotes = getDataFromDB( );

    }

    private List<Note> getDataFromDB() {
        return mNoteNoteDbOpenHelper.queryAllFromDb();
//        return mNoteNoteDbOpenHelper.queryFromDbByCreate_Date(date_1);
    }

    private void initEvent() {
        mMyAdapter = new MyAdapter(this,mNotes);

        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

}
