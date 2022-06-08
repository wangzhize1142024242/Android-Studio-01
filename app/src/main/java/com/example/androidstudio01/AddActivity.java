package com.example.androidstudio01;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.androidstudio01.Util.ImageUtil;
import com.example.androidstudio01.Util.ToastUtil;
import com.example.androidstudio01.bean.Note;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_TAKE = 1;

    private EditText etTitle,etContent,etDate,etTime;
    private NoteDbOpenHelper mNoteDbOpenHelper;
    private Uri imageUri;
    private ImageView ivAvatar;
    private Bitmap bitmap;
    private String imageBase64;
    private String imageToBase64;
    private String date;
    private String time;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);

        etDate = findViewById(R.id.et_data);
        etDate.setOnClickListener(this::DateonClick);

        etTime = findViewById(R.id.et_time);
        etTime.setOnClickListener(this::TimeonClick);
        mNoteDbOpenHelper = new NoteDbOpenHelper(this);

        initView();
        initData();
    }
    private  void initView(){
        ivAvatar = findViewById(R.id.iv_avatar);
    }
    private  void initData(){
        mNoteDbOpenHelper = new NoteDbOpenHelper(this);
    }

    @SuppressLint("ResourceType")

    public void DateonClick(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                date = year + "." + month + "." + day;
                etDate.setText(date);
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(this, onDateSetListener, year, month, day);
        dialog.getDatePicker().setMaxDate(new Date().getTime());
        dialog.show();
//        new DatePickerDialog(this,4,onDateSetListener,year,month,day).show();
    }

    public void TimeonClick(View view){
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                time = hour + ":" + minute;
                etTime.setText(time);
            }
        };
        new TimePickerDialog(this,3,onTimeSetListener,calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),true).show();
    }

    public void  add(View view){
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        String Date = etDate.getText().toString();
        String Time = etTime.getText().toString();
        String CheckBox = "false";
        String Postpone = "false";


        if (TextUtils.isEmpty(title)){
            ToastUtil.toastShort(this,"标题不能为空！");
            return;
        }

        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setCreatedTime(Time);
        note.setCreatedDate(Date);
        note.setCircumstance(CheckBox);
        note.setPostpone(Postpone);
        note.setTakePhoto(imageBase64);
        Log.d("aaaaas", imageBase64+"");
        Log.d("aaaaas", note.getTakePhoto()+"");


        long row = mNoteDbOpenHelper.insertData(note);
        if (row !=  -1){
            ToastUtil.toastShort(this,"添加成功");
            this.finish();
        }else {
            ToastUtil.toastShort(this,"添加失败");
        }


    }

    public void takePhoto(View view){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            doTake();
        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
        }
    }
    public void save(View view){
        SharedPreferences spfRecord = getSharedPreferences("spfRecord",MODE_PRIVATE);
        SharedPreferences.Editor edit = spfRecord.edit();
        edit.putString("image_64",imageBase64);
        edit.apply();
        this.finish();
    }
    public void getDataFromSpf(){
        SharedPreferences spfRecord = getSharedPreferences("spfRecord",MODE_PRIVATE);
        String image64 = spfRecord.getString("image_64","");

        ivAvatar.setImageBitmap(ImageUtil.base64ToImage(image64));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if(requestCode ==1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                doTake();
            }else{
                Toast.makeText(this,"你没有获得摄像头权限",Toast.LENGTH_LONG).show();
            }

        }
    }


    private void doTake() {
        File imageTemp = new File(getExternalCacheDir(),"imageOut.jpg");
        if (imageTemp.exists()){
            imageTemp.delete();
        }
        try{
            imageTemp.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT>=24){
            imageUri = FileProvider.getUriForFile(this,"com.example.androidstudio01.fileprovider",imageTemp);
        }else{
            imageUri = Uri.fromFile(imageTemp);
        }
        Intent intent = new Intent();
        intent.setAction("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent, REQUEST_CODE_TAKE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TAKE){
            if (resultCode == RESULT_OK){
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    ivAvatar.setImageBitmap(bitmap);
                    String imageToBase64 = ImageUtil.imageToBase64(bitmap);
                    Log.d("TAaG", imageToBase64+"");
                    imageBase64 = imageToBase64;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}