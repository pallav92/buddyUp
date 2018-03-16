package com.yatra.buddyup.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yatra.buddyup.R;
import com.yatra.buddyup.model.Message;

import java.util.List;

/**
 * @author Sumit Kumar
 */

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder>{

    List<Message> messages;
    private LayoutInflater inflater;
    private Context context;
    private String ownerName;

    public ChatsAdapter(Context context, List<Message> messages, String ownerName) {
        this.context = context;
        this.messages = messages;
        this.ownerName = ownerName;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ChatsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new ViewHolder(inflater.inflate(R.layout.sender_msg, parent, false));
            case 1:
                return new ViewHolder(inflater.inflate(R.layout.receiver_msg, parent, false));
            default:
                return new ViewHolder(inflater.inflate(R.layout.sender_msg, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(!messages.get(position).getUser().getName().equals(ownerName)) {
            return 0;
        }else {
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(ChatsAdapter.ViewHolder holder, int position) {
        holder.msg.setText(messages.get(position).getMessageText());
        if(messages.get(position).getUser() != null) {
            Glide.with(context).load(messages.get(position).getUser().getImgURL()).into(holder.img);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.msg);
            img = itemView.findViewById(R.id.img);
        }

        TextView msg;
        ImageView img;
    }
}
