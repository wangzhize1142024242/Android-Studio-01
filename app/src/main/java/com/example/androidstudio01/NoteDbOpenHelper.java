package com.example.androidstudio01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.androidstudio01.bean.Note;

import java.util.ArrayList;
import java.util.List;


public class NoteDbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "noteSQLite.db";
    private static final String TABLE_NAME_NOTE = "note";
    private static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME_NOTE +
            "(id integer primary key autoincrement," +
            "title text," +
            "content text," +
            "create_date text," +
            "create_time text," +
            "circumstance text," +
            "postpone text," +
            "takePhoto text)";
    private static final String DROP_TABLE_SQL = "drop table "+TABLE_NAME_NOTE;

    public NoteDbOpenHelper(Context context) {
        super(context,DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_SQL);
//        sqLiteDatabase.execSQL(DROP_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public long insertData(Note note){
        SQLiteDatabase db =getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",note.getTitle());
        values.put("content",note.getContent());
        values.put("create_time",note.getCreatedTime());
        values.put("create_date",note.getCreatedDate());
        values.put("takePhoto",note.getTakePhoto());
        values.put("circumstance",note.getCircumstance());
        values.put("postpone",note.getPostpone());
        Log.d("geta", note.getTitle()+"");
        return db.insert(TABLE_NAME_NOTE,null,values);
    }

    public int deleteFromDbById(String id){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME_NOTE,"id like ?",new String[]{id});
    }


    public int updateDate(Note note){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title",note.getTitle());
        values.put("content",note.getContent());
        values.put("create_time",note.getCreatedTime());
        values.put("create_date",note.getCreatedDate());
        values.put("takePhoto",note.getTakePhoto());
        values.put("circumstance",note.getCircumstance());
        values.put("postpone",note.getPostpone());

        return db.update(TABLE_NAME_NOTE,values,"id like ?",new String[]{note.getId()});
    }

    public List<Note> queryFromDbByCreate_Date(String create_date){
        SQLiteDatabase db = getWritableDatabase();
        List<Note> noteList = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME_NOTE,null,"create_date like ?",new String[]{"%"+create_date+"%"},null,null,null);

        if (cursor !=null){
            while (cursor.moveToNext()){
                String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String title =cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                String createTime = cursor.getString(cursor.getColumnIndexOrThrow("create_time"));
                String createDate = cursor.getString(cursor.getColumnIndexOrThrow("create_date"));
                String takePhoto = cursor.getString(cursor.getColumnIndexOrThrow("takePhoto"));
                String circumstance = cursor.getString(cursor.getColumnIndexOrThrow("circumstance"));
                String postpone = cursor.getString(cursor.getColumnIndexOrThrow("postpone"));

                Note note = new Note();
                note.setId(id);
                note.setTitle(title);
                note.setContent(content);
                note.setCreatedTime(createTime);
                note.setCreatedDate(createDate);
                note.setTakePhoto(takePhoto);
                note.setCircumstance(circumstance);
                note.setPostpone(postpone);

                noteList.add(note);
            }
            cursor.close();
        }
        return noteList;
    }

    public List<Note> queryAllFromDb(){

        SQLiteDatabase db = getWritableDatabase();
        List<Note> noteList = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME_NOTE,null,null,null,null,null,null);

        if (cursor !=null){
            while (cursor.moveToNext()){
                String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String title =cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                String createTime = cursor.getString(cursor.getColumnIndexOrThrow("create_time"));
                String createDate = cursor.getString(cursor.getColumnIndexOrThrow("create_date"));
                String takePhoto = cursor.getString(cursor.getColumnIndexOrThrow("takePhoto"));
                String circumstance = cursor.getString(cursor.getColumnIndexOrThrow("circumstance"));
                String postpone = cursor.getString(cursor.getColumnIndexOrThrow("postpone"));

                Note note = new Note();
                note.setId(id);
                note.setTitle(title);
                note.setContent(content);
                note.setCreatedTime(createTime);
                note.setCreatedDate(createDate);
                note.setTakePhoto(takePhoto);
                note.setCircumstance(circumstance);
                note.setPostpone(postpone);

                noteList.add(note);
            }
            cursor.close();
        }
        return noteList;
    }

}