package com.mak.newword.greendao.service;

import android.content.Context;

import com.mak.newword.greendao.DBManager;
import com.mak.newword.greendao.gen.DaoMaster;
import com.mak.newword.greendao.gen.DaoSession;
import com.mak.newword.greendao.gen.SentenceBeanDao;
import com.mak.newword.greendao.gen.UserBeanDao;
import com.mak.newword.greendao.gen.WordBeanDao;
import com.mak.newword.mvp.model.SentenceBean;
import com.mak.newword.mvp.model.UserBean;
import com.mak.newword.mvp.model.WordBean;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by jayson on 2019/4/8.
 * Content: 一个用户的数据库操作类
 */
public class UserService {

    private static UserService mInstance;
    private DBManager dbManager;

    public UserService(Context context) {
        this.dbManager = DBManager.getInstance(context);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static UserService getInstance(Context context) {
        if (mInstance == null) {
            synchronized (UserService.class) {
                if (mInstance == null) {
                    mInstance = new UserService(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 插入一条记录
     *
     * @param user
     */
    public void insertUser(UserBean user) {
        DaoMaster daoMaster = new DaoMaster(dbManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserBeanDao userDao = daoSession.getUserBeanDao();
        userDao.insert(user);
    }

    /**
     * 修改一条记录
     *
     * @param user
     */
    public void updateUser(UserBean user) {
        DaoMaster daoMaster = new DaoMaster(dbManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserBeanDao userDao = daoSession.getUserBeanDao();
        userDao.update(user);
    }


    /**
     * 查询个数
     *
     * @return
     */
    public long queryUserListCount() {
        DaoMaster daoMaster = new DaoMaster(dbManager.getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserBeanDao userDao = daoSession.getUserBeanDao();
        QueryBuilder<UserBean> qb = userDao.queryBuilder();
        return qb.count();
    }

    /**
     * 查询用户列表
     */
    public List<UserBean> queryUserList() {
        DaoMaster daoMaster = new DaoMaster(dbManager.getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserBeanDao userDao = daoSession.getUserBeanDao();
        QueryBuilder<UserBean> qb = userDao.queryBuilder();
        List<UserBean> list = qb.list();
        return list;
    }
}
