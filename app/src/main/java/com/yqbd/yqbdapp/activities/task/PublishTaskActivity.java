package com.yqbd.yqbdapp.activities.task;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.yqbd.yqbdapp.utils.ActionInject;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.actions.ITaskAction;
import com.yqbd.yqbdapp.actions.ITypeAction;
import com.yqbd.yqbdapp.activities.BaseActivity;
import com.yqbd.yqbdapp.annotation.Action;
import com.yqbd.yqbdapp.annotation.VoData;
import com.yqbd.yqbdapp.annotation.VoDataField;
import com.yqbd.yqbdapp.beans.ImageBean;
import com.yqbd.yqbdapp.beans.TaskBean;
import com.yqbd.yqbdapp.beans.TypeBean;
import com.yqbd.yqbdapp.callback.IActionCallBack;
import com.yqbd.yqbdapp.callback.IDataCallBack;
import com.yqbd.yqbdapp.fragments.datetimepicker.DatePickerFragment;
import com.yqbd.yqbdapp.tagview.widget.Tag;
import com.yqbd.yqbdapp.tagview.widget.TagListView;
import com.yqbd.yqbdapp.tagview.widget.TagView;
import com.yqbd.yqbdapp.utils.BaseJson;
import com.yqbd.yqbdapp.utils.BitmapUtils;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PublishTaskActivity extends BaseActivity implements View.OnClickListener, IDataCallBack, IActionCallBack {
    private final List<Tag> mTags = new ArrayList<Tag>();
    private List<TypeBean> typeBeans;
    private ImageBean imageBean;
    private Date date;

    @VoData
    private TaskBean taskBean;

    @BindView(R.id.activity_publish_task)
    LinearLayout activityPublishTask;

    @VoDataField
    @BindView(R.id.task_title)
    EditText taskTitle;

    @VoDataField
    @BindView(R.id.task_description)
    EditText taskDescription;

    @VoDataField
    @BindView(R.id.task_address)
    EditText taskAddress;

    @VoDataField
    @BindView(R.id.pay)
    EditText pay;

    @VoDataField
    @BindView(R.id.max_people_number)
    EditText maxPeopleNumber;

    @VoDataField
    @BindView(R.id.dealline_time)
    TextView deallineTime;

    @VoDataField
    @BindView(R.id.imageView)
    ImageView imageView;

    @VoDataField
    @BindView(R.id.tagview)
    TagListView tagview;

    //@Action
    private ITaskAction taskAction;

    @Action
    private ITypeAction typeAction;

    @Override
    public void OnSuccess(BaseJson baseJson) {
        typeBeans = baseJson.getBeanList(TypeBean[].class);
        setUpData();
        tagview.setTags(mTags);
        final boolean re = false;
        tagview.setOnTagClickListener(new TagListView.OnTagClickListener() {
            @Override
            public void onTagClick(TagView tagView, Tag tag) {
                if (tag.getOr()) {
                    tag.setOr(false);
                    tagView.setBackgroundResource(R.drawable.tag_checked_normal);
                    //Toast.makeText(getApplicationContext(), "您取消了" + tagView.getText().toString(), 2000).show();
                } else {
                    tag.setOr(true);
                    //Toast.makeText(getApplicationContext(), tagView.getText().toString() + "id" + tag.getId(), 2000).show();
                    tagView.setBackgroundResource(R.drawable.tag_checked_pressed);
                    tagView.setChecked(true);
                }
            }
        });
    }

    private void setUpData() {
        for (TypeBean typeBean : typeBeans) {
            Tag tag = new Tag();
            tag.setId(typeBean.getTypeId());
            tag.setChecked(true);
            tag.setTitle(typeBean.getTypeName());
            mTags.add(tag);
        }
    }

    @Override
    public void onFailed(BaseJson baseJson) {
        makeToast("Failed");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_task);
        ButterKnife.bind(this);
        initializeTop(true, "发布任务");
        taskAction = ActionInject.bindActionCallBack(this, ITaskAction.class, new IActionCallBack() {
            @Override
            public void OnSuccess(BaseJson baseJson) {
                makeToast("成功");
            }

            @Override
            public void onFailed(BaseJson baseJson) {
                makeToast("Failed");
            }
        });
        typeAction.getAllTypes();
    }

    @OnClick({R.id.dealline, R.id.task_publish})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.task_publish:
                TaskBean taskBean = (TaskBean) getVoData();
                taskBean.setDeadline(date.getTime());
                taskBean.setTypeBeans(mTags);
                Log.d(getLogTag(), taskBean.toString());
                taskAction.publishTaskByUser(taskBean);
                break;
            case R.id.dealline:
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                //调用show方法弹出对话框
                // 第一个参数为FragmentManager对象
                // 第二个为调用该方法的fragment的标签
                datePickerFragment.show(getSupportFragmentManager(), "date_picker");
                break;
            case R.id.upload_picture:
                getImageFromAlbum();
                break;
        }
    }

    @Override
    public void getData(Date date) {
        this.date = date;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
        deallineTime.setText(formatter.format(date));
    }

    protected void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            Uri uri = data.getData();
            //to do find the path of pic by uri
            Bitmap photo = null;
            String path = BitmapUtils.getImageAbsolutePath(this, uri);
            try {
                //photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                photo = BitmapUtils.revitionImageSize(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //photo = BitmapFactory.decodeStream(resolver.openInputStream(originalUri));
            imageView.setImageBitmap(photo);
            imageBean = new ImageBean(path, photo, false);
            //gridViewAdapter.addData(new ImageBean(path, photo, false));
        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
            Uri uri = data.getData();
            if (uri == null) {
                //use bundle to get data
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap photo = (Bitmap) bundle.get("data"); //get bitmap
                    //spath :生成图片取个名字和路径包含类型

                } else {
                    Toast.makeText(getApplicationContext(), "err****", Toast.LENGTH_LONG).show();
                    return;
                }
            } else {
                //to do find the path of pic by uri
            }
        }
    }

    public static boolean saveImage(Bitmap photo, String spath) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(spath, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
