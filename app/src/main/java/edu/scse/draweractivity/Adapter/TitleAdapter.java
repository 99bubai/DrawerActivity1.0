package edu.scse.draweractivity.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import edu.scse.draweractivity.R;
import edu.scse.draweractivity.entity.NoteTitleData;

public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.ViewHolder> {
    Context context;
    private final LayoutInflater       mLayoutInflaterInflater;
    private List<NoteTitleData> list;
    private OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;
    public TitleAdapter(Context context, List<NoteTitleData> list){
        this.context=context;
        this.list=list;
        this.mLayoutInflaterInflater=LayoutInflater.from(context);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            textView =(TextView) itemView.findViewById(R.id.item_list_text);
        }
        public TextView getActivity() {
            return textView;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mLayoutInflaterInflater.inflate(R.layout.item_home_list,parent,false);
        //view.setBackgroundColor(Color.RED);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            //设置监听
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            //设置监听
            @Override
            public boolean onLongClick(View v) {
                if (longClickListener != null) {
                    longClickListener.onClick(position,holder.itemView);
                }
                return true;
            }
        });
        NoteTitleData noteTitleData=list.get(position);
        holder.textView.setText(noteTitleData.getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeNotes(int postion) {
        list.remove(postion);
        notifyDataSetChanged();
    }

    //点击事件与长按事件接口

    //第一步 定义接口
    public interface OnItemClickListener {
        void onClick(int position);
    }
    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    //第一步 定义接口
    public interface OnItemLongClickListener {
        void onClick(int position,View view);
    }
    //第二步， 写一个公共的方法
    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

}
