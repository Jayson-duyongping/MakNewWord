package com.mak.newword.mvp.model.converter;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jayson on 2019/4/8.
 * Content:greenDao的类型转换器(字符串集合)
 */
public class StringListConverer implements PropertyConverter<List<String>, String> {
    private final String SPLIT = "##gd##";

    @Override
    public List<String> convertToEntityProperty(String databaseValue) {
        if (databaseValue != null) {
            return Arrays.asList(databaseValue.split(SPLIT));
        }
        return null;
    }

    @Override
    public String convertToDatabaseValue(List<String> entityProperty) {
        if (entityProperty != null) {
            if (entityProperty.size() == 0) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            sb.append(entityProperty.get(0));
            for (int i = 1; i < entityProperty.size(); i++) {
                sb.append(SPLIT).append(entityProperty.get(i));
            }
            return sb.toString();
        }
        return null;
    }
}
