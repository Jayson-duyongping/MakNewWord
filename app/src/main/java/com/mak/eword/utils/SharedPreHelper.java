package com.mak.eword.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mak.eword.constant.StringConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by duyongping on 2018/4/3.
 * Content:保存信息配置类
 */

public class SharedPreHelper<T> {
    private SharedPreferences sharedPreferences;
    /*
     * 保存手机里面的名字
     */
    private SharedPreferences.Editor editor;

    private Gson gson;

    private static SharedPreHelper instance;

    public static SharedPreHelper getInstance(Context context) {
        if (instance == null) {
            return new SharedPreHelper(context, StringConstant.Share_FileName);
        }
        return instance;
    }


    public SharedPreHelper(Context context, String FILE_NAME) {
        gson = new Gson();
        sharedPreferences = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * 存储
     */
    public void put(String key, Object object) {
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.commit();
    }

    /**
     * 获取保存的数据
     */
    public Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return sharedPreferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultObject);
        } else {
            return sharedPreferences.getString(key, null);
        }
    }

    /**
     * 移除某个key值已经对应的值
     */
    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        editor.clear();
        editor.commit();
    }

    /**
     * 保存实体
     *
     * @param key
     * @param obj
     */
    public void saveBeanByFastJson(String key,
                                   Object obj) {
        String objString = gson.toJson(obj);// fastjson的方法，需要导包的
        editor.putString(key, objString).commit();
    }

    /**
     * @param key
     * @param clazz 这里传入一个类就是我们所需要的实体类(obj)
     * @return 返回我们封装好的该实体类(obj)
     */
    public <T> T getBeanByFastJson(String key,
                                   Class<T> clazz) {
        String objString = (String) get(key, "");
        if (TextUtils.isEmpty(objString)) {
            return null;
        }
        return gson.fromJson(objString, clazz);
    }

    /**
     * 查询某个key是否存在
     */
    public Boolean contain(String key) {
        return sharedPreferences.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

    /**
     * 保存List<String>
     *
     * @param key
     * @param datalist
     */
    public void putListSet(String key, List<T> datalist) {
        if (null == datalist)
            return;
        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        editor.clear();
        editor.putString(key, strJson);
        editor.commit();
    }

    /**
     * 获取List<String>
     *
     * @param key
     * @return
     */
    public List<T> getListSet(String key) {
        List<T> datalist = new ArrayList<>();
        String strJson = sharedPreferences.getString(key, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
        }.getType());
        return datalist;
    }
}
