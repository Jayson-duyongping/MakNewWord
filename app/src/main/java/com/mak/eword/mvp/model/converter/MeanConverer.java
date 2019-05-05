package com.mak.eword.mvp.model.converter;

import com.google.gson.Gson;
import com.mak.eword.mvp.model.MeanBean;

import org.greenrobot.greendao.converter.PropertyConverter;


/**
 * Created by jayson on 2019/4/8.
 * Content:greenDao的类型转换器(实体)
 */
public class MeanConverer implements PropertyConverter<MeanBean, String> {

    @Override
    public MeanBean convertToEntityProperty(String databaseValue) {
        if (databaseValue != null) {
            return new Gson().fromJson(databaseValue, MeanBean.class);
        }
        return null;
    }

    @Override
    public String convertToDatabaseValue(MeanBean entityProperty) {
        if (entityProperty != null) {
            return new Gson().toJson(entityProperty);
        }
        return null;
    }
}
