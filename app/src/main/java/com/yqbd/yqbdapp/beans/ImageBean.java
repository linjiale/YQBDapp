package com.yqbd.yqbdapp.beans;

import android.graphics.Bitmap;

/**
 * Created by 11022 on 2017/5/13.
 */
public class ImageBean {
    private String filePath;
    private Bitmap bitmap;
    private boolean isPick;

    public ImageBean() {
    }

    public ImageBean(String filePath, boolean isPick) {
        this.filePath = filePath;
        this.isPick = isPick;
    }

    public ImageBean(String filePath, Bitmap bitmap, boolean isPick) {
        this.filePath = filePath;
        this.bitmap = bitmap;
        this.isPick = isPick;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isPick() {
        return isPick;
    }

    public void setPick(boolean pick) {
        isPick = pick;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
