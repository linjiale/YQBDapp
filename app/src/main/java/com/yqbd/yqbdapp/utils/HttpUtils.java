package com.yqbd.yqbdapp.utils;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

public class HttpUtils {

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


}