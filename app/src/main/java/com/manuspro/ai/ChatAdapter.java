package com.manuspro.ai;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder> {

    private List<ChatMessage> messages;

    public ChatAdapter(List<ChatMessage> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        
        holder.senderName.setText(message.getSender());
        holder.messageText.setText(message.getMessage());
        holder.timestamp.setText(message.getFormattedTime());

        // Style based on sender
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.messageCard.getLayoutParams();
        
        if (message.isUser()) {
            params.gravity = Gravity.END;
            holder.messageCard.setCardBackgroundColor(Color.parseColor("#2563EB"));
            holder.senderName.setTextColor(Color.parseColor("#E0E7FF"));
            holder.messageText.setTextColor(Color.WHITE);
            holder.timestamp.setTextColor(Color.parseColor("#C7D2FE"));
        } else {
            params.gravity = Gravity.START;
            holder.messageCard.setCardBackgroundColor(Color.parseColor("#F3F4F6"));
            holder.senderName.setTextColor(Color.parseColor("#6B7280"));
            holder.messageText.setTextColor(Color.parseColor("#111827"));
            holder.timestamp.setTextColor(Color.parseColor("#9CA3AF"));
        }
        
        holder.messageCard.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        CardView messageCard;
        TextView senderName;
        TextView messageText;
        TextView timestamp;

        MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageCard = itemView.findViewById(R.id.messageCard);
            senderName = itemView.findViewById(R.id.senderName);
            messageText = itemView.findViewById(R.id.messageText);
            timestamp = itemView.findViewById(R.id.timestamp);
        }
    }
}
