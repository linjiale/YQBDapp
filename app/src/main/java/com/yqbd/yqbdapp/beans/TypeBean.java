package com.yqbd.yqbdapp.beans;

import com.yqbd.yqbdapp.tagview.widget.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 11022 on 2017/7/17.
 */
@Data
@NoArgsConstructor
public class TypeBean {
    private Integer typeId;
    private String typeName;

    public TypeBean(Tag tag) {
        this.typeId = tag.getId();
        this.typeName = tag.getTitle();
    }

    public TypeBean(JSONObject jsonObject) throws JSONException{
        this.typeId = jsonObject.getInt("typeId");
        this.typeName = jsonObject.getString("typeName");
    }
}
