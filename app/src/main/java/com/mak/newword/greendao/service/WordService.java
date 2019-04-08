package com.mak.newword.greendao.service;

import android.content.Context;

import com.mak.newword.greendao.DBManager;
import com.mak.newword.greendao.gen.DaoMaster;
import com.mak.newword.greendao.gen.DaoSession;
import com.mak.newword.greendao.gen.WordBeanDao;
import com.mak.newword.mvp.model.WordBean;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by jayson on 2019/4/8.
 * Content: 一个单词的数据库操作类
 */
public class WordService {

    private static WordService mInstance;
    private DBManager dbManager;

    public WordService(Context context) {
        this.dbManager = DBManager.getInstance(context);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static WordService getInstance(Context context) {
        if (mInstance == null) {
            synchronized (WordService.class) {
                if (mInstance == null) {
                    mInstance = new WordService(context);
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
    public long insertWord(WordBean user) {
        DaoMaster daoMaster = new DaoMaster(dbManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        WordBeanDao userDao = daoSession.getWordBeanDao();
        return userDao.insert(user);
    }

    /**
     * 插入单词集合
     *
     * @param users
     */
    public void insertWordList(List<WordBean> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(dbManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        WordBeanDao userDao = daoSession.getWordBeanDao();
        userDao.insertInTx(users);
    }

    /**
     * 删除一条记录
     *
     * @param user
     */
    public void deleteWord(WordBean user) {
        DaoMaster daoMaster = new DaoMaster(dbManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        WordBeanDao userDao = daoSession.getWordBeanDao();
        userDao.delete(user);
    }

    /**
     * 查询单词列表
     */
    public List<WordBean> queryWordList(int position, int count) {
        DaoMaster daoMaster = new DaoMaster(dbManager.getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        WordBeanDao userDao = daoSession.getWordBeanDao();
        QueryBuilder<WordBean> qb = userDao.queryBuilder();
        qb.offset(position).limit(count);
        List<WordBean> list = qb.list();
        return list;
    }

    /**
     * 查询单词列表
     */
    public List<WordBean> queryWordList(String wordKey) {
        DaoMaster daoMaster = new DaoMaster(dbManager.getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        WordBeanDao userDao = daoSession.getWordBeanDao();
        QueryBuilder<WordBean> qb = userDao.queryBuilder();
        qb.where(WordBeanDao.Properties.Content.like(wordKey)).orderAsc(WordBeanDao.Properties.Content);
        List<WordBean> list = qb.list();
        return list;
    }
}
