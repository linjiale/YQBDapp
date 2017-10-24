package com.yqbd.yqbdapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.google.gson.Gson;
import com.yqbd.yqbdapp.actions.impl.BaseActionImpl;
import okhttp3.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpUtils {

    protected static final String path = "http://goout-1252946747.cossh.myqcloud.com";

    //private OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static String httpConnectByPost(String url, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        //把请求的内容字符串转换为json
        RequestBody body = RequestBody.create(JSON, json);
        //RequestBody formBody = new FormEncodingBuilder()

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        String result = response.body().string();


        return result;
    }

    public static String httpConnectByPost(String url, Map<String, String> map) throws IOException {
        OkHttpClient client = new OkHttpClient();

        FormBody.Builder formBody = new FormBody.Builder();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            formBody.add(entry.getKey(), entry.getValue());
        }

        Request request = new Request.Builder()
                .url(url)
                .post(formBody.build())
                .build();

        Response response = client.newCall(request).execute();

        //ResponseBody responseBody = response.body();

        String result = response.body().string();

        return result;
    }


    public static String httpConnectByPost(String url, Object o) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Gson gson = new Gson();
        //把请求的内容字符串转换为json
        RequestBody body = RequestBody.create(JSON, gson.toJson(o));
        //RequestBody formBody = new FormEncodingBuilder()

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        String result = response.body().string();
        //gson.fromJson(result,result);

        return result;
    }

    public static Bitmap getBitmapByAddress(String address, int reqWidth, int reqHeight) throws IOException {
        URL url = new URL(path + "/" + address);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
        connection.setConnectTimeout(6000);
        //连接设置获得数据流
        connection.setDoInput(true);
        //不使用缓存
        connection.setUseCaches(false);
        //得到数据流
        InputStream is = connection.getInputStream();
        byte[] data = readStream(is);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        int inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        System.out.println(inSampleSize);
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            if (heightRatio < 0) {
                inSampleSize = widthRatio;
            } else if (widthRatio < 0) {
                inSampleSize = heightRatio;
            } else {
                inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            }
        }
        return inSampleSize;
    }

    public static byte[] readStream(InputStream inStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;
    }


}