package com.yqbd.yqbdapp.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
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
import com.yqbd.yqbdapp.beans.UserInfoBean;
import com.yqbd.yqbdapp.utils.AsyncBitmapLoader;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;


public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder>  implements View.OnClickListener {
    private AsyncBitmapLoader asyncBitmapLoader = AsyncBitmapLoader.asyncBitmapLoader;
    private List<UserInfoBean> userInfoList;
    private Activity activity;
    private PersonAdapter.OnItemClickListener mOnItemClickListener = null;

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.person_name)
        TextView personName;

        @BindView(R.id.person_head_portrait)
        protected CircleImageView person_head_portraitView;
        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, UserInfoBean userInfoBean);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, userInfoList.get((int) v.getTag()));
        }
    }

    public void setOnItemClickListener(PersonAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public PersonAdapter(List<UserInfoBean> userInfoList, Activity activity) {
        this.userInfoList = userInfoList;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, parent, false);
        PersonAdapter.ViewHolder holder = new PersonAdapter.ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        UserInfoBean userInfoItem = userInfoList.get(position);

        holder.view.setTag(position);
        //System.out.println("position:" + position);
        holder.personName.setText(userInfoItem.getRealName());


        Bitmap bitmap1 = asyncBitmapLoader.loadBitmap(holder.person_head_portraitView, userInfoItem.getHeadPortrait(), holder.person_head_portraitView.getLayoutParams().width, holder.person_head_portraitView.getLayoutParams().height, new AsyncBitmapLoader.ImageCallBack() {
            @Override
            public void imageLoad(ImageView imageView, Bitmap bitmap) {
                // TODO Auto-generated method stub
                imageView.setImageBitmap(bitmap);
                //item.picture = bitmap;
            }
        });
        if (bitmap1 != null) {
            holder.person_head_portraitView.setImageBitmap(bitmap1);
        }

    }

    @Override
    public int getItemCount() {
        return userInfoList.size();
    }


}
