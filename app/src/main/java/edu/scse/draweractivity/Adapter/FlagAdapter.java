package edu.scse.draweractivity.Adapter;
//暂时决定不用这个
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.scse.draweractivity.R;
import static android.content.ContentValues.TAG;

public class FlagAdapter extends RecyclerView.Adapter<FlagAdapter.ViewHolder>{
    private final LayoutInflater mLayoutInflaterInflater;
    private       String[]       mTitles=null;
    public FlagAdapter(Context context){
        this.mLayoutInflaterInflater=LayoutInflater.from(context);
        this.mTitles=new String[20];
        for (int i=0;i<20;i++){
            int index=i+1;
            mTitles[i]="item"+index;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button button;
        @SuppressLint("WrongViewCast")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setLayoutParams
                    (new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            button =(Button) itemView.findViewById(R.id.item_list_button);
        }
        public Button getActivity() {
            return button;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mLayoutInflaterInflater.from(parent.getContext()).inflate(R.layout.item_notes_list,parent,false);
        //view.setBackgroundColor(Color.RED);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FlagAdapter.ViewHolder holder, int position) {
        holder.button.setText(mTitles[position]);
    }

    @Override
    public int getItemCount() {
        return mTitles.length;
    }
}
