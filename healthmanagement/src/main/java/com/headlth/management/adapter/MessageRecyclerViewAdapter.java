package com.headlth.management.adapter;


import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.headlth.management.R;
import com.headlth.management.entity.MessageList;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by abc on 2017/4/28.
 */
public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MyMessageViewHolder> {


    private List<MessageList.Message> messageListlist;
    private Activity activity;
    private Handler myhandler;
    private List<Integer> messageId;
    private List<String> messageMonth;

    public MessageRecyclerViewAdapter(List<MessageList.Message> messageListlist, Activity activity, Handler handler) {
        this.messageListlist = messageListlist;
        this.activity = activity;
        myhandler = handler;
        messageId = new ArrayList<>();
        messageMonth = new ArrayList<>();
     Log.i("messageListlist",messageListlist.size()+"");
    }

    @Override
    public MyMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_message, parent, false);


        return new MyMessageViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final MyMessageViewHolder holder, final int position) {
        final MessageList.Message message = messageListlist.get(position);
        //判断是否设置了监听器

        //为ItemView设置监听器
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("mOnItemClickListener", "mOnItemClickListener");
                Message message = Message.obtain();
                message.arg1 = position;
                message.arg2 = 0;
                myhandler.sendMessage(message);
            }
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i("mOnItemClickListener", "mOnItemLongClickListener");
                int position = holder.getLayoutPosition();
                Message message = Message.obtain();
                message.arg1 = position;
                message.arg2 = 1;
                myhandler.sendMessage(message);

                return true;
            }
        });

        if (!messageMonth.contains(message.CreateMonth)) {
            messageMonth.add(message.CreateMonth);
            messageId.add(message.ID);
        }
        if (messageId.contains(message.ID)) {
            holder.item_message_datelayout.setVisibility(View.VISIBLE);
        }else {
            holder.item_message_datelayout.setVisibility(View.GONE);
        }
        switch (message.MsgtypeId){
            case 0:
                holder.item_message_icon.setImageResource(R.drawable.mmsg);
                break;
            case 1:
                holder.item_message_icon.setImageResource(R.mipmap.news_coach);
                break;
            case 2:
                holder.item_message_icon.setImageResource(R.mipmap.news_doctor);
                break;

        }
        holder.item_message_date.setText(message.CreateMonth);
        holder.item_message_man.setText(message.Title);
        holder.item_message_time.setText(message.CreateTime);
        holder.item_message_content.setText(message.Content);

    }

    @Override
    public int getItemCount() {
        return messageListlist.size();
    }

}

class MyMessageViewHolder extends RecyclerView.ViewHolder {

    @ViewInject(R.id.item_message_datelayout)
    public LinearLayout item_message_datelayout;
    @ViewInject(R.id.item_message_date)
    public TextView item_message_date;
    @ViewInject(R.id.item_message_contentlayout)
    public LinearLayout item_message_contentlayout;
    @ViewInject(R.id.item_message_icon)
    public ImageView item_message_icon;

    @ViewInject(R.id.item_message_man)
    public TextView item_message_man;

    @ViewInject(R.id.item_message_time)
    public TextView item_message_time;
    @ViewInject(R.id.item_message_content)
    public TextView item_message_content;

    public MyMessageViewHolder(View itemView) {
        super(itemView);
        x.view().inject(this, itemView);
    }


}
