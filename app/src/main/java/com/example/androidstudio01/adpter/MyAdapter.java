package com.example.androidstudio01.adpter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidstudio01.EditActivity;
import com.example.androidstudio01.NoteDbOpenHelper;
import com.example.androidstudio01.R;
import com.example.androidstudio01.Util.ImageUtil;
import com.example.androidstudio01.Util.ToastUtil;
import com.example.androidstudio01.bean.Note;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Note> mBeanList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private NoteDbOpenHelper mNoteDbOpenHelper;
    public static int flag = 0 ;


    public MyAdapter(Context context,List<Note> mBeanList){
        this.mBeanList = mBeanList;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mNoteDbOpenHelper = new NoteDbOpenHelper(mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Note note = mBeanList.get(position);
        holder.mTvTitle.setText(note.getTitle());
        holder.mTvContent.setText(note.getContent());
        holder.mTvTime.setText(note.getCreatedTime());
        holder.mTvDate.setText(note.getCreatedDate());
        holder.removeDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int row = mNoteDbOpenHelper.deleteFromDbById(note.getId());
                if (row > 0){
                    removeDelete(holder.getAdapterPosition());
                    ToastUtil.toastShort(mContext,"删除成功");
                }else{
                    ToastUtil.toastShort(mContext,"删除失败");
                }
            }
        });

        Log.d("askfd", note.getCircumstance()+"");
        if (note.getCircumstance().equals("true")){

            holder.mTvCheckBox.setChecked(true);
        }else{
            holder.mTvCheckBox.setChecked(false);
            if (note.getPostpone().equals("true")){
                holder.rlContainer.setBackgroundColor(Color.parseColor("#FF0000"));
            }else{
                holder.rlContainer.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        }

        if (note.getPostpone().equals("true")){
            holder.rlContainer.setBackgroundColor(Color.parseColor("#FF0000"));
            holder.mTvCheckBox.setChecked(false);
        }else{
            holder.rlContainer.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        holder.mTvCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(holder.mTvCheckBox.isChecked()){
                    ToastUtil.toastShort(mContext,"已完成");
                    Boolean checkBox = holder.mTvCheckBox.isChecked();
                    String CheckBox = checkBox.toString();
                    String Postpone = "false";
                    Log.d("emmmmmm", CheckBox+"");
                    note.setCircumstance(CheckBox);
                    note.setPostpone(Postpone);
                    mNoteDbOpenHelper.updateDate(note);
                }else{
                    ToastUtil.toastShort(mContext,"未完成");
                    Boolean checkBox = holder.mTvCheckBox.isChecked();
                    String CheckBox = checkBox.toString();
                    String Postpone = "false";
                    Log.d("emmmmmm", CheckBox+"");
                    note.setCircumstance(CheckBox);
                    note.setPostpone(Postpone);
                    mNoteDbOpenHelper.updateDate(note);
                }
            }
        });

        holder.rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag == 0){
                    if (note.getTakePhoto() != null){
                        Log.d("1234567", note.getTakePhoto()+"");
                        holder.mTvImage.setVisibility(View.VISIBLE);
                        holder.mTvImage.setImageBitmap(ImageUtil.base64ToImage(note.getTakePhoto()));
                        flag = 1;
                    }
                }else{
                    holder.mTvImage.setVisibility(View.GONE);
                    flag = 0;
                }
            }
        });

        holder.rlContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                长按弹出弹窗 进行编辑
                Intent intent = new Intent(mContext,EditActivity.class);
                intent.putExtra("note",note);
                Log.d("pic", note.getTakePhoto()+"");
                mContext.startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    public void  refreshData(List<Note> notes){
        this.mBeanList = notes;
        notifyDataSetChanged();
    }

    public void removeDelete(int pos){
        mBeanList.remove(pos);
        notifyItemRemoved(pos);
    }

    class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView mTvTitle;
        TextView mTvContent;
        TextView mTvTime;
        TextView mTvDate;
        Button removeDelete;
        ImageView mTvImage;
        CheckBox mTvCheckBox;
        ViewGroup rlContainer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.removeDelete=itemView.findViewById(R.id.tv_Delete);
            this.mTvTitle=itemView.findViewById(R.id.tv_title);
            this.mTvContent=itemView.findViewById(R.id.tv_content);
            this.mTvTime=itemView.findViewById(R.id.tv_time);
            this.mTvDate=itemView.findViewById(R.id.tv_date);
            this.mTvImage=itemView.findViewById(R.id.tv_image);
            this.mTvCheckBox=itemView.findViewById(R.id.tv_CheckBox);
            this.rlContainer=itemView.findViewById(R.id.rl_item_container);
        }
    }
}
