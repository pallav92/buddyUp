package com.yatra.buddyup.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yatra.buddyup.R;
import com.yatra.buddyup.model.User;

import java.util.List;

/**
 * @author Sumit Kumar
 */

public class ChatUserAdapter extends RecyclerView.Adapter<ChatUserAdapter.ViewHolder>{
    List<User> users;
    private LayoutInflater inflater;
    private Context context;

    public ChatUserAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ChatUserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChatUserAdapter.ViewHolder(inflater.inflate(R.layout.chat_user_adapter_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ChatUserAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(users.get(position).getImgURL()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
        }
        ImageView img;
    }
}
