package edu.scse.draweractivity.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.scse.draweractivity.R;
import static android.content.ContentValues.TAG;

public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.ViewHolder> {
    private final LayoutInflater       mLayoutInflaterInflater;
    private String[] mTitles=null;
    public TitleAdapter(Context context){
        this.mLayoutInflaterInflater=LayoutInflater.from(context);
        this.mTitles=new String[20];
        for (int i=0;i<20;i++){
            int index=i+1;
            mTitles[i]="item"+index;
        }
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
        holder.textView.setText(mTitles[position]);
    }

    @Override
    public int getItemCount() {
        return mTitles.length;
    }
}
