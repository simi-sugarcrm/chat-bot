package com.sprintweek.marvin.sugarassistant.mobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sprintweek.marvin.sugarassistant.mobile.Constants;
import com.sprintweek.marvin.sugarassistant.mobile.Model.BaseMessage;
import com.sprintweek.marvin.sugarassistant.mobile.Model.EntityMessage;
import com.sprintweek.marvin.sugarassistant.mobile.R;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<BaseMessage> mMessageList;

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            profileImage = (ImageView) itemView.findViewById(R.id.bot_image_message_profile);
        }

        void bind(EntityMessage message) {
            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(message.getFormattedCreatedAt());

            // Insert the profile image from the URL into the ImageView.
            //Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        }

        void bind(EntityMessage message) {
            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(message.getFormattedCreatedAt());
        }
    }


    public MessageListAdapter(Context context, List<BaseMessage> messageList) {
        mContext = context;
        mMessageList = messageList;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        EntityMessage message = (EntityMessage) mMessageList.get(position);

        if (message.getSender().getEntityId() != Constants.AI_ID) {
            // If the current user is the sender of the message
            return Constants.VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return Constants.VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        if (viewType == Constants.VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == Constants.VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EntityMessage message = (EntityMessage) mMessageList.get(position);
        if (holder.getItemViewType() == Constants.VIEW_TYPE_MESSAGE_SENT) {
            ((SentMessageHolder) holder).bind(message);
        } else if (holder.getItemViewType() == Constants.VIEW_TYPE_MESSAGE_RECEIVED) {
            ((ReceivedMessageHolder) holder).bind(message);
        }
    }
}