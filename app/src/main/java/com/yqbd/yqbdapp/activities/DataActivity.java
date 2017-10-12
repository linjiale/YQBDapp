package com.yqbd.yqbdapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.yqbd.yqbdapp.annotation.VoData;
import com.yqbd.yqbdapp.annotation.VoDataField;
import com.yqbd.yqbdapp.annotation.VoDataFields;
import com.yqbd.yqbdapp.utils.HttpUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public abstract class DataActivity extends AppCompatActivity {

    private Field voDataField = null;
    private Map<String, Field> voDataFieldMap = Maps.newHashMap();
    //private List<Field> valueFields;
    private Map<Field, Field> voDataFieldViewMap = Maps.newHashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVoData();
    }

    protected void initData(String path, Object object) {
        new Thread(new GetVoDataThread(path, object)).start();
    }

    class GetVoDataThread implements Runnable {
        String path;
        Object para;

        GetVoDataThread(String path, Object object) {
            this.path = path;
            this.para = object;
        }

        @Override
        public void run() {
            try {
                String result;
                if (para instanceof Map) {
                    result = HttpUtils.httpConnectByPost(path, (Map<String, String>) para);
                } else if (para instanceof String) {
                    result = HttpUtils.httpConnectByPost(path, (String) para);
                } else {
                    result = HttpUtils.httpConnectByPost(path, para);
                }
                Gson gson = new Gson();
                voDataField.set(DataActivity.this, gson.fromJson(result, voDataField.getType()));
                changeVoData();
            } catch (Exception e) {
                makeToast("连接错误");
            }
        }
    }

    protected void makeToast(final String content) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DataActivity.this, content, Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void bindVoData() {
        //Field voDataField = null;
        List<Field> fields = Lists.newArrayList(this.getClass().getDeclaredFields());
        for (Field field : fields) {
            if (field.isAnnotationPresent(VoData.class)) {
                voDataField = field;
                break;
            }
        }
        if (voDataField == null) return;
        voDataField.setAccessible(true);
        //Map<String, Field> voDataFieldMap = Maps.newHashMap();
        boolean isFields = this.getClass().isAnnotationPresent(VoDataFields.class);
        if (isFields) {
            for (Field field : fields) {
                if (TextView.class.isAssignableFrom(field.getType())) {
                    String typeName = field.getType().getSimpleName();
                    String fieldName = field.getName();
                    if (fieldName.endsWith(typeName)) {
                        voDataFieldMap.put(fieldName.substring(0, fieldName.lastIndexOf(typeName)), field);
                    } else {
                        voDataFieldMap.put(fieldName, field);
                    }
                }
            }
        } else {
            for (Field field : fields) {
                VoDataField v = field.getAnnotation(VoDataField.class);
                if (v != null) {
                    field.setAccessible(true);
                    if (v.value().isEmpty()) {
                        String fieldName = field.getName();
                        String typeName = field.getType().getSimpleName();
                        if (fieldName.endsWith(typeName)) {
                            voDataFieldMap.put(fieldName.substring(0, fieldName.lastIndexOf(typeName)), field);
                        } else {
                            voDataFieldMap.put(fieldName, field);
                        }
                    } else {
                        voDataFieldMap.put(v.value(), field);
                    }
                }
            }
        }
        List<Field> valueFields = Lists.newArrayList(voDataField.getType().getDeclaredFields());
        //Map<Field, Field> voDataFieldViewMap = Maps.newHashMap();
        for (Field field : valueFields) {
            field.setAccessible(true);
            Field f = voDataFieldMap.get(field.getName());
            if (f != null) {
                voDataFieldViewMap.put(field, f);
            }
        }
    }

    protected void changeVoData() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    OnVoDataChanged();
                } catch (Exception e) {
                    Toast.makeText(DataActivity.this, "连接错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void OnVoDataChanged() throws Exception {
        Object value = voDataField.get(this);
        if (value == null) return;
        for (Map.Entry<Field, Field> entry : voDataFieldViewMap.entrySet()) {
            Field field = entry.getValue();
            Method method = field.getType().getMethod("setText", CharSequence.class);
            Object s = entry.getKey().get(value);
            if (s != null) {
                method.invoke(field.get(this), s.toString());
            }
        }
    }


    protected Object getVoData() {
        try {
            Object vo = voDataField.get(this);
            if (vo == null) vo = voDataField.getType().newInstance();
            for (Map.Entry<Field, Field> entry : voDataFieldViewMap.entrySet()) {
                Field voDataField = entry.getKey();
                Field viewField = entry.getValue();
                Method method = viewField.getType().getMethod("getText");
                String value = method.invoke(viewField.get(this)).toString();
                Class cls = voDataField.getType();
                if (Integer.class.isAssignableFrom(cls)) {
                    voDataField.set(vo, Integer.parseInt(value));
                } else if (Long.class.isAssignableFrom(cls)) {
                    voDataField.set(vo, Long.parseLong(value));
                } else if (Double.class.isAssignableFrom(cls)) {
                    voDataField.set(vo, Double.parseDouble(value));
                } else {
                    voDataField.set(vo, value);
                }
            }
            voDataField.set(this, vo);
            return vo;
        } catch (Exception e) {
            Log.d(getLogTag(), "", e);
            return null;
        }
    }

    public String getLogTag() {
        return getClass().getSimpleName();
    }
}
