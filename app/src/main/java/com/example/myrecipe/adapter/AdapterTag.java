package com.example.myrecipe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipe.R;
import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.models.Tag;

import java.util.List;

public class AdapterTag extends RecyclerView.Adapter<AdapterTag.ViewHolder> {
    List<Tag> tags;
    OnListTagClickListener listener;
    public AdapterTag(List<Tag> tags, OnListTagClickListener listener){
        this.tags = tags;
        this.listener = listener;
    }


    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.piece_tag, parent, false);
        return new ViewHolder(view);

    }

    public void onBindViewHolder(ViewHolder viewHolder, int position){
        viewHolder.name.setText(tags.get(position).getName());
    }

    public int getItemCount(){
        return tags.size();
    }

    public void setData(List<Tag> tags) {
        this.tags = tags;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition());
                }
            });
            name = itemView.findViewById(R.id.tag_text);
        }
    }

    public interface OnListTagClickListener {
        void onClick(int position);
    }
}
