package com.example.androidstudio01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.androidstudio01.adpter.MyAdapter;
import com.example.androidstudio01.bean.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Note> mNotes;
    private MyAdapter mMyAdapter;
    private NoteDbOpenHelper mNoteNoteDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liebiao);

        initView();
        initData();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshDataFromDb();
    }

    private void refreshDataFromDb() {
        mNotes = getDataFromDB();
        mMyAdapter.refreshData(mNotes);
    }

    private void initEvent() {
        mMyAdapter = new MyAdapter(this,mNotes);

        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initData() {
        mNotes = new ArrayList<>();
        mNoteNoteDbOpenHelper = new NoteDbOpenHelper(this);

//        for (int i = 0;i<30;i++){
//            Note note = new Note();
//            note.setTitle("这是标题"+i);
//            note.setContent("这是内容"+i);
//            note.setCreatedTime(getCurrentTimeFormat());
//            mNotes.add(note);
//        }
        mNotes = getDataFromDB( );

    }

    private List<Note> getDataFromDB() {
        return mNoteNoteDbOpenHelper.queryAllFromDb();
    }


    private void initView() {
        mRecyclerView = findViewById(R.id.rlv);
    }

    public void add(View view){
        Intent intent = new Intent(ListActivity.this,AddActivity.class);
        startActivity(intent);
    }
}