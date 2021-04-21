package edu.scse.draweractivity.Adapter;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import edu.scse.draweractivity.R;

//根据传入的layout与id构建item，只提供点击事件，用来给充当设定菜单的recycleview使用
public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder> {
    Context context;
    private final LayoutInflater       mLayoutInflaterInflater;
    private static int r_layout,r_id;
    private List<String> list;
    private OnItemClickListener listener;

    public SettingAdapter(Context context,List<String> list, int r_layout,int r_id){
        this.context=context;
        this.list=list;
        this.r_id=r_id;
        this.r_layout=r_layout;
        this.mLayoutInflaterInflater=LayoutInflater.from(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=(TextView) itemView.findViewById(r_id);
        }
        public TextView getActivity() {
            return textView;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= mLayoutInflaterInflater.inflate(r_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener !=null){
                    listener.onClick(position);
                }
                int positon=holder.getAdapterPosition();
                //测试
                Toast.makeText(v.getContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
            }
        });
        String string=list.get(position);
        holder.textView.setText(string);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    //点击事件
    //第一步 定义接口
    public interface OnItemClickListener {
        void onClick(int position);
    }
    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}