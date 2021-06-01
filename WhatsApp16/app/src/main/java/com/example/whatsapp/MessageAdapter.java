package com.example.whatsapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>
{
    static int r = 0, s = 0, c=0;

    private List<Messages> userMessagesList;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;




    public MessageAdapter (List<Messages> userMessagesList)
    {
        this.userMessagesList = userMessagesList;

    }

    public class MessageViewHolder extends RecyclerView.ViewHolder
    {

        public TextView senderMessageText, receiverMessageText;
        public ImageView senderCorner, receiverCorner;
        public ImageView messageSenderPicture, messageReceiverPicture;
//        public CircleImageView receiverProfileImage;

        public MessageViewHolder(@NonNull View itemView)
        {
            super(itemView);

            senderMessageText = (TextView) itemView.findViewById(R.id.sender_message_text);
            receiverMessageText = (TextView) itemView.findViewById(R.id.receiver_message_text);
            senderCorner = (ImageView) itemView.findViewById(R.id.corner_of_sender);
            receiverCorner = (ImageView) itemView.findViewById(R.id.corner_of_receiver);
            messageReceiverPicture = itemView.findViewById(R.id.message_receiver_image_view);
            messageSenderPicture = itemView.findViewById(R.id.message_sender_image_view);
//            receiverProfileImage = (CircleImageView) itemView.findViewById(R.id.message_profile_image);
        }
    }



    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_messages_layout, viewGroup, false);

        mAuth = FirebaseAuth.getInstance();

        return new MessageViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder messageViewHolder, int i)
    {

//        HashMap<String,String> hs=new HashMap<String, String>();
        String messageSenderId = mAuth.getCurrentUser().getUid();
        Messages messages = userMessagesList.get(i);

        String fromUserID = messages.getFrom();
        String fromMessageType = messages.getType();

        usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(fromUserID);

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.hasChild("image"))
                {
                    String receiverImage = dataSnapshot.child("image").getValue().toString();

//                    Picasso.get().load(receiverImage).placeholder(R.drawable.profile_image).into(messageViewHolder.receiverProfileImage);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });


        messageViewHolder.receiverMessageText.setVisibility(View.GONE);
        messageViewHolder.senderMessageText.setVisibility(View.GONE);
        messageViewHolder.messageSenderPicture.setVisibility(View.GONE);//
        // messageViewHolder.messageSenderPicture.setVisibility(View.GONE);//
        messageViewHolder.messageReceiverPicture.setVisibility(View.GONE);//

        if (fromMessageType.equals("text"))
        {

//            messageViewHolder.receiverProfileImage.setVisibility(View.INVISIBLE);

            messageViewHolder.senderCorner.setVisibility(View.INVISIBLE);
            messageViewHolder.receiverCorner.setVisibility(View.INVISIBLE);


            if (fromUserID.equals(messageSenderId))
            {
                messageViewHolder.senderMessageText.setVisibility(View.VISIBLE);

                messageViewHolder.senderMessageText.setBackgroundResource(R.drawable.sender_messages_layout);
                messageViewHolder.senderMessageText.setTextColor(Color.BLACK);
                //messageViewHolder.senderMessageText.setText(messages.getMessage());
                messageViewHolder.senderMessageText.setText(messages.getMessage() + "\n \n" + messages.getTime());
//                String str=messages.getMessage();//
//                hs.put(messageSenderId,str);
                if (s == 0)
                {
                    messageViewHolder.senderCorner.setVisibility(View.VISIBLE);
                    s =1 ;
                }
                r = 0;
            }
            else
            {
//                messageViewHolder.receiverProfileImage.setVisibility(View.VISIBLE);
                messageViewHolder.receiverMessageText.setVisibility(View.VISIBLE);

                messageViewHolder.receiverMessageText.setBackgroundResource(R.drawable.receiver_messages_layout);
                messageViewHolder.receiverMessageText.setTextColor(Color.BLACK);
                //messageViewHolder.receiverMessageText.setText(messages.getMessage());
                messageViewHolder.receiverMessageText.setText(messages.getMessage() + "\n \n" + messages.getTime());
                if (r == 0)
                {
                    messageViewHolder.receiverCorner.setVisibility(View.VISIBLE);
                    r = 1;
                }
                s = 0;
            }

        }
        else if (fromMessageType.equals("image"))
        {

            messageViewHolder.senderCorner.setVisibility(View.INVISIBLE);
            messageViewHolder.receiverCorner.setVisibility(View.INVISIBLE);

            if (fromUserID.equals(messageSenderId))
            {
                messageViewHolder.messageSenderPicture.setVisibility(View.VISIBLE);

                Picasso.get().load(messages.getMessage()).into(messageViewHolder.messageSenderPicture);
            }
            else
            {
                messageViewHolder.messageReceiverPicture.setVisibility(View.VISIBLE);

                Picasso.get().load(messages.getMessage()).into(messageViewHolder.messageReceiverPicture);
            }
        }
    }



    @Override
    public int getItemCount()
    {
        return userMessagesList.size();
    }


}
