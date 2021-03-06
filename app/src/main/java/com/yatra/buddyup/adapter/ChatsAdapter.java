package com.yatra.buddyup.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yatra.buddyup.R;
import com.yatra.buddyup.model.Message;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author Sumit Kumar
 */

public class ChatsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new ViewHolder(inflater.inflate(R.layout.sender_msg, parent, false));
            case 1:
                return new ViewHolder(inflater.inflate(R.layout.receiver_msg, parent, false));
            case 2:
                return new ViewHolderForYatra(inflater.inflate(R.layout.yatra_msg_layout, parent, false));
            default:
                return new ViewHolder(inflater.inflate(R.layout.sender_msg, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getUser().getName().equals("YATRA")) {
            return 2;
        } else {
            if (!messages.get(position).getUser().getName().equals(ownerName)) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            final String message = messages.get(position).getMessageText();
            if (StringUtils.isNumeric(message)) {
                ((ViewHolder) holder).msg.setTextColor(ContextCompat.getColor(context, R.color.colorBlue));
                ((ViewHolder) holder).msg.setText(message);
                ((ViewHolder) holder).msg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + message));
                        context.startActivity(intent);
                    }
                });
            } else {
                ((ViewHolder) holder).msg.setTextColor(ContextCompat.getColor(context, R.color.message_color));
                ((ViewHolder) holder).msg.setText(messages.get(position).getMessageText());
                ((ViewHolder) holder).msg.setOnClickListener(null);
            }

            if (messages.get(position).getUser() != null) {
                Glide.with(context).load(messages.get(position).getUser().getImgURL()).into(((ViewHolder) holder).img);
            }
        } else {
            ((ViewHolderForYatra) holder).msg.setText(messages.get(position).getMessageText());
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

    static class ViewHolderForYatra extends RecyclerView.ViewHolder {

        ViewHolderForYatra(View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.msg);
        }

        TextView msg;
    }
}
