package com.mak.eword.mvp.model.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mak.eword.mvp.model.MeanBean;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jayson on 2019/4/8.
 * Content:greenDao的类型转换器(实体集合)
 */
public class MeanListConverer implements PropertyConverter<List<MeanBean>, String> {

    @Override
    public List<MeanBean> convertToEntityProperty(String databaseValue) {
        if (databaseValue != null) {
            Type type = new TypeToken<ArrayList<MeanBean>>() {
            }.getType();
            List<MeanBean> list = new Gson().fromJson(databaseValue, type);
            return list;
        }
        return null;
    }

    @Override
    public String convertToDatabaseValue(List<MeanBean> entityProperty) {
        if (entityProperty != null) {
            if (entityProperty.size() == 0) {
                return "";
            }
            return new Gson().toJson(entityProperty);
        }
        return null;
    }
}
