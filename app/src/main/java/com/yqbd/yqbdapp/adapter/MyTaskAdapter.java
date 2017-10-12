package com.yqbd.yqbdapp.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.beans.TaskBean;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by 佳乐 on 2017/7/17.
 */
public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.ViewHolder> implements View.OnClickListener {
    private List<TaskBean> myTaskList;
    private Activity activity;
    private OnItemClickListener mOnItemClickListener = null;

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.companyImage)
        ImageView companyImage;
        @BindView(R.id.nameText)
        TextView nameText;
        @BindView(R.id.DescriptionText)
        TextView descriptionText;
        @BindView(R.id.timeText)
        TextView timeText;
        @BindView(R.id.statusText)
        TextView statusText;
        @BindView(R.id.commentButton)
        Button commentButton;
        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public MyTaskAdapter(List<TaskBean> myTaskList, Activity activity) {
        this.myTaskList = myTaskList;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_task_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TaskBean taskItem = myTaskList.get(position);
        Log.d(getClass().getSimpleName(), position + ":" + taskItem.toString());
        //System.out.println("name:" + taskItem.getTaskTitle());
        holder.view.setTag(position);
        //System.out.println("position:" + position);
        holder.descriptionText.setText(taskItem.getTaskDescription());
        holder.nameText.setText(taskItem.getTaskTitle());
        String time = new Timestamp(taskItem.getPublishTime()).toString();
        //System.out.println(time);
        holder.timeText.setText(time);
        switch (taskItem.getTaskStatus()) {
            case 0:
                holder.statusText.setText("未完成");
                holder.commentButton.setVisibility(View.GONE);
                break;
            case 1:
                holder.statusText.setText("进行中");
                holder.commentButton.setVisibility(View.GONE);
                break;
            case 2:
                holder.statusText.setText("已完成,待评价");
                holder.commentButton.setText("去评价");
                break;
            default:
                holder.statusText.setText("已结束");
                holder.commentButton.setVisibility(View.GONE);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return myTaskList.size();
    }

}
