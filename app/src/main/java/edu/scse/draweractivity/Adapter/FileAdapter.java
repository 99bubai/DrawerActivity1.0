package edu.scse.draweractivity.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.scse.draweractivity.Activity.NoteAddActivity;
import edu.scse.draweractivity.R;
//笔记添加界面，选择文件时变化的recycleview使用该类，尚未重写点击与长按事件
public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {
    Context context;
    private List<String> fileList;
    private final LayoutInflater layoutInflater;
    private OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;

    public FileAdapter(Context context, List<String> fileList) {
        this.context=context;
        this.fileList=fileList;
        this.layoutInflater=LayoutInflater.from(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=(TextView) itemView.findViewById(R.id.textView_file);
        }
        public TextView getActivity() {
            return textView;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.item_file_list,parent,false);
        ViewHolder root=new ViewHolder(view);
        return root;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onClick(position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longClickListener!=null){
                    longClickListener.onClick(position,holder.itemView);
                }
                return true;
            }
        });
        String string=fileList.get(position);
        holder.textView.setText(string);
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    //第一步 定义接口
    public interface OnItemClickListener {
        void onClick(int position);
    }
    //第二步， 写一个公共的方法
    public void setOnItemClickListener(FileAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
    //第一步 定义接口
    public interface OnItemLongClickListener {
        void onClick(int position,View view);
    }
    //第二步， 写一个公共的方法
    public void setOnItemLongClickListener(FileAdapter.OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

}
