package com.yqbd.yqbdapp.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
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
import com.yqbd.yqbdapp.utils.AsyncBitmapLoader;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by 佳乐 on 2017/7/17.
 */
public class RecommendTaskAdapter extends RecyclerView.Adapter<RecommendTaskAdapter.ViewHolder> implements View.OnClickListener {
    private AsyncBitmapLoader asyncBitmapLoader = AsyncBitmapLoader.asyncBitmapLoader;
    private List<TaskBean> myTaskList;
    private Activity activity;
    private OnItemClickListener mOnItemClickListener = null;

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recommendItemImage)
        ImageView companyImage;
        @BindView(R.id.textView)
        TextView nameText;
        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, TaskBean taskBean);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, myTaskList.get((int) v.getTag()));
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public RecommendTaskAdapter(List<TaskBean> myTaskList, Activity activity) {
        this.myTaskList = myTaskList;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_item, parent, false);
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
        holder.nameText.setText(taskItem.getTaskTitle());
        //String time = new Timestamp(taskItem.getPublishTime()).toString();
        //System.out.println(time);
        Bitmap bitmap = asyncBitmapLoader.loadBitmap(holder.companyImage, taskItem.getSimpleDrawingAddress(), holder.companyImage.getLayoutParams().width, holder.companyImage.getLayoutParams().height, new AsyncBitmapLoader.ImageCallBack() {
            @Override
            public void imageLoad(ImageView imageView, Bitmap bitmap) {
                // TODO Auto-generated method stub
                imageView.setImageBitmap(bitmap);
            }
        });
        if (bitmap != null) {
            holder.companyImage.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return myTaskList.size();
    }

}
